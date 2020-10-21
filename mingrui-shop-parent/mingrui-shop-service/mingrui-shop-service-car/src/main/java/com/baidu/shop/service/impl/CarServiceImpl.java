package com.baidu.shop.service.impl;

import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.config.JwtConfig;
import com.baidu.shop.constant.MRshopConstant;
import com.baidu.shop.dto.Car;
import com.baidu.shop.dto.UserInfo;
import com.baidu.shop.entity.SkuEntity;
import com.baidu.shop.feign.GoodsFeign;
import com.baidu.shop.redis.repository.RedisRepository;
import com.baidu.shop.service.CarService;
import com.baidu.shop.utils.JSONUtil;
import com.baidu.shop.utils.JwtUtils;
import com.baidu.shop.utils.ObjectUtil;
import com.baidu.shop.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CarServiceImpl
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2020/10/19
 * @Version V1.0
 **/
@RestController
@Slf4j
public class CarServiceImpl extends BaseApiService implements CarService {

    @Autowired
    private RedisRepository redisRepository;

    @Autowired
    private GoodsFeign goodsFeign;

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public Result<List<Car>> updateNum(Long skuId, Integer type, String token) {

        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtConfig.getPublicKey());
            Car redisCar = redisRepository.getHash(MRshopConstant.USER_GOODS_CAR_PRE + userInfo.getId()
                    , skuId + "", Car.class);
//            if(type == 1){
//                redisCar.setNum(redisCar.getNum() + 1);
//            }else{
//                redisCar.setNum(redisCar.getNum() - 1);
//            }
            redisCar.setNum(type == MRshopConstant.CAR_OPERATION_INCREMENT ? redisCar.getNum() + 1 : redisCar.getNum() - 1);
            redisRepository.setHash(MRshopConstant.USER_GOODS_CAR_PRE + userInfo.getId()
                    ,skuId + "", JSONUtil.toJsonString(redisCar));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this.setResultSuccess();
    }

    @Override
    public Result<List<Car>> getCurrentUserGoodsCar(String token) {
        //获取当前登录用户信息
        //通过用户id获取购物车数据-->Map<String,String> skuid,car
        try {
            List<Car> cars = new ArrayList<>();
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtConfig.getPublicKey());
            Map<String, String> goodsCarMap = redisRepository.getHash(MRshopConstant.USER_GOODS_CAR_PRE + userInfo.getId());
            goodsCarMap.forEach((key,value) -> {
                Car car = JSONUtil.toBean(value, Car.class);
                cars.add(car);
            });
            return this.setResultSuccess(cars);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.setResultError("内部错误");
    }

    @Override
    public Result<JSONObject> mergeCar(String clientCarList, String token) {

        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(clientCarList);
        List<Car> carList = com.alibaba.fastjson.JSONObject.parseArray(jsonObject.get("clientCarList").toString(), Car.class);

        carList.stream().forEach(car -> {
            this.addCar(car,token);
        });

        return this.setResultSuccess();
    }

    @Override
    public Result<JSONObject> addCar(Car car ,String token) {

        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtConfig.getPublicKey());

            Car redisCar = redisRepository.getHash(MRshopConstant.USER_GOODS_CAR_PRE + userInfo.getId()
                    , car.getSkuId() + "", Car.class);

            Car saveCar = null;
            log.debug("通过key : {} ,skuid : {} 获取到的数据为 : {}",MRshopConstant.USER_GOODS_CAR_PRE + userInfo.getId(),car.getSkuId(),redisCar);
            if(ObjectUtil.isNotNull(redisCar)){//原来的用户购物车中有当前要添加到购物车中的商品
                redisCar.setNum(car.getNum() + redisCar.getNum());

                saveCar = redisCar;

                log.debug("当前用户购物车中有将要新增的商品，重新设置num : {}" , redisCar.getNum());
            }else{//当前用户购物车中没有将要新增的商品信息
                Result<SkuEntity> skuResult = goodsFeign.getSkuBySkuId(car.getSkuId());
                if (skuResult.getCode() == 200) {
                    SkuEntity skuEntity = skuResult.getData();
                    car.setTitle(skuEntity.getTitle());
                    car.setImage(StringUtil.isNotEmpty(skuEntity.getImages()) ?
                            skuEntity.getImages().split(",")[0] : "");

                    car.setOwnSpec(skuEntity.getOwnSpec());
                    car.setPrice(Long.valueOf(skuEntity.getPrice()));
                    car.setUserId(userInfo.getId());

                    saveCar = car;

                    log.debug("新增商品到购物车redis,KEY : {} , skuid : {} , car : {}",MRshopConstant.USER_GOODS_CAR_PRE + userInfo.getId(),car.getSkuId(),JSONUtil.toJsonString(car));
                }
            }

            redisRepository.setHash(MRshopConstant.USER_GOODS_CAR_PRE + userInfo.getId()
                            ,car.getSkuId() + "", JSONUtil.toJsonString(saveCar));
            log.debug("新增到redis数据成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.setResultSuccess();
    }
}
