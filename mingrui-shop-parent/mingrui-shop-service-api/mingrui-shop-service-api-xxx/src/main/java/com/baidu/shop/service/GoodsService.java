package com.baidu.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SkuDTO;
import com.baidu.shop.dto.SpuDTO;
import com.baidu.shop.entity.SpuDetailEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @ClassName GoodsService
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2020/9/7
 * @Version V1.0
 **/
@Api(tags = "商品接口")
public interface GoodsService {

    @ApiOperation(value = "获取spu信息")
    @GetMapping(value = "goods/getSpuInfo")
    Result<List<SpuDTO>> getSpuInfo(SpuDTO spuDTO);

    @ApiOperation(value = "保存商品信息")
    @PostMapping(value = "goods/save")
    Result<JSONObject> addInfo(@RequestBody SpuDTO spuDTO);

    @ApiOperation(value = "通过spuId获取spu-detail信息")
    @GetMapping(value = "goods/getDetailBySpuId")
    Result<SpuDetailEntity> getDetailBySpuId(Integer spuId);

    @ApiOperation(value = "获取sku信息")
    @GetMapping(value = "goods/getSkuBySpuId")
    Result<List<SkuDTO>> getSkuBySpuId(Integer spuId);

    @ApiOperation(value = "修改商品信息")
    @PutMapping(value = "goods/save")
    Result<JSONObject> editInfo(@RequestBody SpuDTO spuDTO);
}
