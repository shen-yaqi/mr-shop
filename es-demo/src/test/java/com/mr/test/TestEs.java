package com.mr.test;

import com.mr.RunTestEsApplication;
import com.mr.entity.GoodsEntity;
import com.mr.repostory.GoodsEsRepository;
import com.mr.utils.ESHighLightUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.Max;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightUtils;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.HighlightQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName TestEs
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2020/9/14
 * @Version V1.0
 **/
//让测试在Spring容器环境下执行
@RunWith(SpringRunner.class)
//声明启动类,当测试方法运行的时候会帮我们自动启动容器
@SpringBootTest(classes = { RunTestEsApplication.class})
public class TestEs {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Resource
    private GoodsEsRepository goodsEsRepository;

    /*
   创建索引
    */
    @Test
    public void createGoodsIndex(){
        //operations
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(IndexCoordinates.of("wangzehui"));

        if(!indexOperations.exists()){
            indexOperations.create();//创建索引
        }

        //indexOperations.exists() 判断索引是否存在
        System.out.println(indexOperations.exists()?"索引创建成功":"索引创建失败");
    }

    /*
    创建映射
     */
    @Test
    public void createGoodsMapping(){

        //此构造函数会检查有没有索引存在,如果没有则创建该索引,如果有则使用原来的索引
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(GoodsEntity.class);
        //indexOperations.create();
        //indexOperations.createMapping();//创建映射,不调用此函数也可以创建映射,这就是高版本的强大之处
        System.out.println("映射创建成功" + indexOperations.exists());
    }

    /*
删除索引
 */
    @Test
    public void deleteGoodsIndex(){


        //创建,删除索引的时候 elasticsearchRestTemplate
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(GoodsEntity.class);

        indexOperations.delete();
        System.out.println("索引删除成功");
    }

    /*
新增文档
 */
    @Test
    public void saveData(){

        //新增 删除 更新文档(数据) --> repository
        GoodsEntity entity = new GoodsEntity();
        entity.setId(1L);
        entity.setBrand("三星");
        entity.setCategory("手机");
        entity.setImages("xiaomi.jpg");
        entity.setPrice(10000D);
        entity.setTitle("小米5");

        //也可以做新增 删除 保存操作
        //elasticsearchRestTemplate.save(entity);

        //不支持自定义查询

        //save 可以做新增,也可以做修改(更新)
        //通过id没有查找到数据的话-->新增操作
        //通过id能查询到数据的话-->更新操作
        goodsEsRepository.save(entity);

        //goodsEsRepository.delete(entity);

        System.out.println("新增成功");
    }

    /*
    查询所有
     */
    @Test
    public void searchAll(){
        //查询总条数
        long count = goodsEsRepository.count();
        System.out.println(count);
        //查询所有数据
        Iterable<GoodsEntity> all = goodsEsRepository.findAll();
        all.forEach(goods -> {
            System.out.println(goods);
        });

        List byPriceBetween = goodsEsRepository.findByPriceBetween(3000D, 4000D);
        System.out.println(byPriceBetween);


//        List<GoodsEntity> test = goodsEsRepository.findByTitle("小米5");
//
//        List<GoodsEntity> byTitleAndBrand = goodsEsRepository.findByTitleAndBrand("小米5", "大米");
//
//        System.out.println(test);
    }

    @Test
    public void batchAdd(){

        GoodsEntity entity = new GoodsEntity();
        entity.setId(2L);
        entity.setBrand("苹果");
        entity.setCategory("手机");
        entity.setImages("pingguo.jpg");
        entity.setPrice(5000D);
        entity.setTitle("iphone11手机");

        GoodsEntity entity2 = new GoodsEntity();
        entity2.setId(3L);
        entity2.setBrand("三星");
        entity2.setCategory("手机");
        entity2.setImages("sanxing.jpg");
        entity2.setPrice(3000D);
        entity2.setTitle("w2019手机");

        GoodsEntity entity3 = new GoodsEntity();
        entity3.setId(4L);
        entity3.setBrand("华为");
        entity3.setCategory("手机");
        entity3.setImages("huawei.jpg");
        entity3.setPrice(4000D);
        entity3.setTitle("华为mate30手机");

        goodsEsRepository.saveAll(Arrays.asList(entity,entity2,entity3));
        System.out.println("批量新增成功");
    }

    /**
     * 高级查询
     */
    @Test
    public void search(){

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //Collection List和Set集合的顶级接口
        //Collection
        //Collections.

        //queryBuilder.withQuery(QueryBuilders.matchQuery("title","小米aaa手机"));

        //BoolQueryBuilder must = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("title", "小米手机"));

        queryBuilder.withQuery(
                QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("title","小米"))
        );

        SearchHits<GoodsEntity> search = elasticsearchRestTemplate.search(queryBuilder.build(), GoodsEntity.class);

        List<SearchHit<GoodsEntity>> searchHits = search.getSearchHits();

        searchHits.stream().forEach(hit -> {
            GoodsEntity entity = hit.getContent();
            System.out.println(entity);
        });

    }

    @Test
    public void highLightSearch(){
        //构建高级查询
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        //构建高亮查询
        HighlightBuilder builder = new HighlightBuilder();
        //HighlightBuilder.Field 调用HighlightBuilder的静态内部类Field <em></em>
        //设置高亮查询字段
        HighlightBuilder.Field title = new HighlightBuilder.Field("title");
        //设置高亮标签
        title.preTags("<font style='color:#e4393c'>");
        title.postTags("</font>");
        builder.field(title);

        //高亮查询
        queryBuilder.withHighlightBuilder(builder);

        queryBuilder.withQuery(
                QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("title","小米手机"))
                        //price >= 1000 and price <= 3000
                        .must(QueryBuilders.rangeQuery("price").gte(1000).lte(3000))
        );

        SearchHits<GoodsEntity> search = elasticsearchRestTemplate.search(queryBuilder.build(), GoodsEntity.class);

        List<SearchHit<GoodsEntity>> searchHits = search.getSearchHits();

        List<GoodsEntity> goodsList = searchHits.stream().map(hit -> {
            GoodsEntity entity = hit.getContent();

            //通过字段名获取高亮查询结果
            List<String> title1 = hit.getHighlightField("title");

            entity.setTitle(title1.get(0));
            return entity;
        }).collect(Collectors.toList());

        System.out.println(goodsList);

    }

    @Test
    public void highLightUtilsTest(){

        //构建高级查询
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        //高亮查询
        queryBuilder.withHighlightBuilder(ESHighLightUtil.getHighlightBuilder("title"));

        queryBuilder.withQuery(
                QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("title","小米手机"))
                        //price >= 1000 and price <= 3000
                        .must(QueryBuilders.rangeQuery("price").gte(1000).lte(10000))
        );

        //
        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));

        //分页PageRequest-->Pageable的实现类.of(当前页 -1 ,分页显示多少条数据);
        //在es中第一页的值0
        queryBuilder.withPageable(PageRequest.of(2-1,2));

        SearchHits<GoodsEntity> search = elasticsearchRestTemplate.search(queryBuilder.build(), GoodsEntity.class);

        List<SearchHit<GoodsEntity>> searchHits = search.getSearchHits();

        List<SearchHit<GoodsEntity>> highLightHit = ESHighLightUtil.getHighLightHit(searchHits);
        System.out.println(highLightHit);
        List<GoodsEntity> collect = highLightHit.stream().map(searchHit -> {
            GoodsEntity content = searchHit.getContent();
            return content;
        }).collect(Collectors.toList());

    }

    @Test
    public void testBucket(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        queryBuilder.addAggregation(
                AggregationBuilders.terms("brand_agg").field("brand")
        );

        SearchHits<GoodsEntity> search = elasticsearchRestTemplate.search(queryBuilder.build(), GoodsEntity.class);

        //String类型可以转byte类型吗?
        //怎么转?
        //byte类型可以转string类型吗?
        //怎么转?

        /*byte[] bytes = "AAA".getBytes();
        try {
            String a  = new String(key,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/

        Aggregations aggregations = search.getAggregations();

        //terms 是Aggregation的实现类
        //Aggregation brand_agg = aggregations.get("brand_agg");/
        Terms terms = aggregations.get("brand_agg");

        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        buckets.forEach(bucket -> {
            System.out.println(bucket.getKeyAsString() + ":" + bucket.getDocCount());
        });
        System.out.println(search);

    }

    /*
聚合函数
 */
    @Test
    public void searchAggMethod(){

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.addAggregation(
                AggregationBuilders.terms("brand_agg")
                        .field("brand")
                        //聚合函数
                        .subAggregation(AggregationBuilders.max("max_price").field("price"))
        );

        SearchHits<GoodsEntity> search = elasticsearchRestTemplate.search(queryBuilder.build(), GoodsEntity.class);

        Aggregations aggregations = search.getAggregations();

        Terms terms = aggregations.get("brand_agg");

        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        buckets.forEach(bucket -> {
            System.out.println(bucket.getKeyAsString() + ":" + bucket.getDocCount());
//
//            ArrayList<Object> objects = new ArrayList<>();
//            //Object name:max_price value:3000.00
//            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
//
//            objects.forEach(obj -> {
//
//                stringObjectHashMap.put(obj.getName(),obj.value);
//            });

            //获取聚合
            Aggregations aggregations1 = bucket.getAggregations();
            //得到map
            Map<String, Aggregation> map = aggregations1.asMap();
            //Max 接口继承了 SingleValue接口 SingleValue有多个子接口
            Max max_price = (Max) map.get("max_price");
            System.out.println(max_price.getValue());
        });
    }

}
