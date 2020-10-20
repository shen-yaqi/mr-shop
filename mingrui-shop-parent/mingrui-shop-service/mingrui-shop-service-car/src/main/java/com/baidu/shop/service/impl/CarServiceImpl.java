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

        //需要一个feign接口
        //可以通过skuId查询sku信息

        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtConfig.getPublicKey());

            //通过userid和skuid获取商品数据
            Car redisCar = redisRepository.getHash(MRshopConstant.USER_GOODS_CAR_PRE + userInfo.getId()
                    , car.getSkuId() + "", Car.class);

            Car saveCar = null;
            //true: num += num
            //false:
            log.debug("通过key : {} ,skuid : {} 获取到的数据为 : {}",MRshopConstant.USER_GOODS_CAR_PRE + userInfo.getId(),car.getSkuId(),redisCar);
            if(ObjectUtil.isNotNull(redisCar)){//原来的用户购物车中有当前要添加到购物车中的商品
                redisCar.setNum(car.getNum() + redisCar.getNum());
                saveCar = redisCar;
//                redisRepository.setHash(MRshopConstant.USER_GOODS_CAR_PRE + userInfo.getId()
//                        ,car.getSkuId() + "", JSONUtil.toJsonString(redisCar));
                log.debug("当前用户购物车中有将要新增的商品，重新设置num : {}" , redisCar.getNum());
            }else{//当前用户购物车中没有将要新增的商品信息
                Result<SkuEntity> skuResult = goodsFeign.getSkuBySkuId(car.getSkuId());
                if (skuResult.getCode() == 200) {
                    SkuEntity skuEntity = skuResult.getData();
                    car.setTitle(skuEntity.getTitle());
                    car.setImage(StringUtil.isNotEmpty(skuEntity.getImages()) ?
                            skuEntity.getImages().split(",")[0] : "");
                    //
                    Map<String, Object> stringObjectMap = JSONUtil.toMap(skuEntity.getOwnSpec());
                    //key:id
                    //value: 规格参数值
                    //遍历map
                    //feign调用通过paramId查询info的接口
                    //重新组装map
                    //将map转为json字符串

                    car.setOwnSpec(skuEntity.getOwnSpec());
                    car.setPrice(Long.valueOf(skuEntity.getPrice()));
                    car.setUserId(userInfo.getId());
                    saveCar = car;
//                    redisRepository.setHash(MRshopConstant.USER_GOODS_CAR_PRE + userInfo.getId()
//                            ,car.getSkuId() + "", JSONUtil.toJsonString(car));
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
