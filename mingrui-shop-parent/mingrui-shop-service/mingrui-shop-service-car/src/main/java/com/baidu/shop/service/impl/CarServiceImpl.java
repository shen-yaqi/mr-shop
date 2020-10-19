package com.baidu.shop.service.impl;

import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.Car;
import com.baidu.shop.redis.repository.RedisRepository;
import com.baidu.shop.service.CarService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
/**
 * @ClassName CarServiceImpl
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2020/10/19
 * @Version V1.0
 **/
@RestController
public class CarServiceImpl extends BaseApiService implements CarService {

    @Autowired
    private RedisRepository redisRepository;

    @Override
    public Result<JSONObject> addCar(Car car) {

        //需要一个feign接口
        //可以通过skuId查询sku信息

        System.out.println("=========");
        return this.setResultSuccess();
    }
}
