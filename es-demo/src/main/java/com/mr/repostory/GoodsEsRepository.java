package com.mr.repostory;

import com.mr.entity.GoodsEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @ClassName GoodsEsRepository
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2020/9/14
 * @Version V1.0
 **/
public interface GoodsEsRepository extends ElasticsearchRepository<GoodsEntity,Long> {

    List<GoodsEntity> findByTitle(String title);

    List<GoodsEntity> findByTitleAndBrand(String title,String brand);

    List findByPriceBetween(Double start,Double end);

}
