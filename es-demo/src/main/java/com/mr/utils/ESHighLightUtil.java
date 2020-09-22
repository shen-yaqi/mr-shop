package com.mr.utils;

import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName ESHighLightUtil
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2020/9/14
 * @Version V1.0
 **/
public class ESHighLightUtil<T> {

    // 有效代码
    // 要求 至少写过多少万行的有效代码

    //构建高亮字段buiilder
    //... 可以传一个参数,也可以穿多个参数-->在方法不确定要穿多少个参数的时候...,这些参数必须得是相同类型的
    //... 到最后会生成一个Array
    public static HighlightBuilder getHighlightBuilder(String ...highLightField){

        HighlightBuilder highlightBuilder = new HighlightBuilder();

        //highLightField --> {"title,"brand","category"}-->["title","brand","category"]
        Arrays.asList(highLightField).forEach(hlf -> {
            HighlightBuilder.Field field = new HighlightBuilder.Field(hlf);

            field.preTags("<font style='color:#e4393c'>");
            field.postTags("</font>");

            highlightBuilder.field(field);
        });

        return highlightBuilder;
    }

    //将返回的内容替换成高亮
    public static <T> List<SearchHit<T>> getHighLightHit(List<SearchHit<T>> list){

        return list.stream().map(hit -> {
            //得到高亮字段
            Map<String, List<String>> highlightFields = hit.getHighlightFields();

            highlightFields.forEach((key,value) -> {
                try {
                    //得到传递参数的类型
                    T content = hit.getContent();//当前文档 T为当前文档类型

                    //content.getClass()获取当前文档类型,并且得到排序字段的set方法 set T itle
                    Method method = content.getClass().getMethod("set" + String.valueOf(key.charAt(0)).toUpperCase() + key.substring(1),String.class);

                    //执行set方法并赋值
                    method.invoke(content,value.get(0));
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            });

            return hit;
        }).collect(Collectors.toList());

    }
}
