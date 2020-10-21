package com.baidu.shop.service;

import com.baidu.shop.base.Result;
import com.baidu.shop.dto.Car;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @ClassName CarService
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2020/10/19
 * @Version V1.0
 **/
@Api(tags = "购物车接口")
public interface CarService {

    @ApiOperation(value = "添加商品到购物车")
    @PostMapping(value = "car/addCar")
    Result<JSONObject> addCar(@RequestBody Car car, @CookieValue(value = "MRSHOP_TOKEN") String token);

    @ApiOperation(value = "购物车合并")
    @PostMapping(value = "car/mergeCar")
    Result<JSONObject> mergeCar(@RequestBody String clientCarList, @CookieValue(value = "MRSHOP_TOKEN") String token);

    @ApiOperation(value = "获取当前用户的购物车数据")
    @GetMapping(value = "car/getCurrentUserGoodsCar")
    Result<List<Car>> getCurrentUserGoodsCar(@CookieValue(value = "MRSHOP_TOKEN") String token);

    @ApiOperation(value = "获取当前用户的购物车数据")
    @GetMapping(value = "car/updateNum")
    Result<List<Car>> updateNum(Long skuId,Integer type,@CookieValue(value = "MRSHOP_TOKEN") String token);
}
