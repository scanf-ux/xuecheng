package com.xuecheng.content;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.model.dto.QueryCourseParamDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CourseBaseMapperTests {

    @Autowired
    CourseBaseInfoService courseBaseInfoService;
    @Test
     void testCourseBaseMapper() {

        QueryCourseParamDto queryCourseParamsDto = new QueryCourseParamDto();
        queryCourseParamsDto.setCourseName("测试");
        queryCourseParamsDto.setAuditStatus("202004");
        queryCourseParamsDto.setPublishStatus("203001");

        PageParams pageParams=new PageParams();
        pageParams.setPageNo(1L);
        pageParams.setPageSize(10L);
        long companyId=111111L;
        PageResult<CourseBase> pageResult =courseBaseInfoService.queryCourseBaseList(companyId,pageParams,queryCourseParamsDto);
        System.out.println(pageResult);

    }
}
