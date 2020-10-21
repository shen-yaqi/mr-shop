package com.baidu.shop.business;

import com.baidu.shop.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
/**
 * @ClassName OrderService
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2020/10/21
 * @Version V1.0
 **/
@Api(tags = "订单接口")
public interface OrderService {

    @ApiOperation(value = "创建订单")
    @PostMapping(value = "order/createOrder")
    Result<Long> createOrder();
}
