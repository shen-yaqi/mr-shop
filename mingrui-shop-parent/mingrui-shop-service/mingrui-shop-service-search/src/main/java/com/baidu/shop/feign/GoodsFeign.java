package com.baidu.shop.feign;

import com.baidu.shop.service.GoodsService;
import org.springframework.cloud.openfeign.FeignClient;


import java.util.List;

/**
 * @ClassName GoodsFeign
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2020/9/16
 * @Version V1.0
 **/
@FeignClient(contextId = "GoodsService", value = "xxx-service")
public interface GoodsFeign extends GoodsService {
}
