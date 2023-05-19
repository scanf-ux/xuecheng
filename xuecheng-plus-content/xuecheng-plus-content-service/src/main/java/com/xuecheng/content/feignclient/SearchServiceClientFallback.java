package com.xuecheng.content.feignclient;

import com.xuecheng.content.po.CourseIndex;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @program: xuecheng-plus-project
 * @description:
 * @author: xiejie
 * @create: 2023-05-19 21:02
 **/
@Slf4j
@Component
public class SearchServiceClientFallback implements SearchServiceClient{

    @Override
    public Boolean add(CourseIndex courseIndex) {
        log.debug("方式一：熔断处理，无法获取异常");
        return null;
    }
}
