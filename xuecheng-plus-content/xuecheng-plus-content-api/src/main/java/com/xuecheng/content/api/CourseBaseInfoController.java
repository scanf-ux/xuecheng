package com.xuecheng.content.api;

import com.xuecheng.base.exception.ValidationGroups;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.*;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import com.xuecheng.content.service.CourseCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: xuecheng-plus-project
 * @description: 课程信息类
 * @author: xiejie
 * @create: 2023-04-12 14:28
 **/

@Slf4j
@RestController
@Api(value = "课程信息管理接口", tags = "课程信息管理接口")
public class CourseBaseInfoController {
    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    @Autowired
    CourseCategoryService courseCategoryService;

    @ApiOperation("课程查询接口")
    @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamDto queryCourseParams) {
        Long companyId=1234566777L;
        PageResult<CourseBase> pageResult = courseBaseInfoService.queryCourseBaseList(companyId,pageParams,queryCourseParams);
        return pageResult;
    }
    @ApiOperation("添加课程")
    @PostMapping("/course")
    public CourseBaseInfoDto courseBaseInfoDto(@RequestBody @Validated({ValidationGroups.Insert.class}) AddCourseDto addCourseDto){
        Long companyId=1234566777L;
        CourseBaseInfoDto courseBase = courseBaseInfoService.createCourseBase(companyId, addCourseDto);
        return  courseBase;
    }

    @ApiOperation("根据课程id查询课程基础信息")
    @RequestMapping(value = "/{courseId}",method = RequestMethod.GET)
    public CourseBaseInfoDto getCourseBaseById(@PathVariable("courseId") Long courseId){
        CourseBaseInfoDto courseBaseById = courseBaseInfoService.getCourseBaseInfo(courseId);
        return courseBaseById;
    }
    @ApiOperation("修改课程")
    @RequestMapping(value = "/course",method = RequestMethod.PUT)
    public CourseBaseInfoDto modifyCourseBase(@RequestBody @Validated EditCourseDto editCourseDto){
        //机构id，由于认证系统没有上线暂时硬编码
        Long companyId = 1232141425L;
        return courseBaseInfoService.updateCourseBase(companyId,editCourseDto);

    }
}
