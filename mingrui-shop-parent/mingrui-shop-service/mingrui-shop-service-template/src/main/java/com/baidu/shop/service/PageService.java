package com.baidu.shop.service;

import java.util.Map;

/**
 * @ClassName PageService
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2020/9/23
 * @Version V1.0
 **/
public interface PageService {
    Map<String, Object> getPageInfoBySpuId(Integer spuId);
}
