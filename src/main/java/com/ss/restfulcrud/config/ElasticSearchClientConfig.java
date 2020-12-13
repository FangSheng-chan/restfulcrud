package com.ss.restfulcrud.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//1.找对象
//2、放到spring中待用！
//3、如果是springboot 分析源码
//
@Configuration
public class ElasticSearchClientConfig {

    //spring <bean id="restHighLevelClient" class="RestHighLevelClient">
    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
        return client;
    }
}
