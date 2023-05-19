package com.xuecheng.content.feignclient;

import com.xuecheng.content.po.CourseIndex;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @program: xuecheng-plus-project
 * @description:
 * @author: xiejie
 * @create: 2023-05-19 21:03
 **/
@Slf4j
@Component
public class SearchServiceClientFallbackFactory implements FallbackFactory<SearchServiceClient>{
    @Override
    public SearchServiceClient create(Throwable throwable) {
        return new SearchServiceClient() {
            @Override
            public Boolean add(CourseIndex courseIndex) {
                log.debug("方式二：熔断处理，熔断异常：{}", throwable.getMessage());
                return null;
            }
        };
    }
}
