package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.*;
import com.xuecheng.content.model.po.CourseBase;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


public interface CourseBaseInfoService {

    /**
     * @description 课程查询接口
     * @param pageParams 分页参数
     * @param queryCourseParamsDto 条件条件
     * @return com.xuecheng.base.model.PageResult<com.xuecheng.content.model.po.CourseBase>
     */

    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto);

    /**
     * @description 添加课程基本信息
     * @param companyId  教学机构id
     * @param addCourseDto  课程基本信息
     * @return com.xuecheng.content.model.dto.CourseBaseInfoDto
     * @author Mr.M
     * @date 2022/9/7 17:51
     */
    CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);

    CourseBaseInfoDto getCourseBaseById(Long courseId);

    CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto editCourseDto);
    
}
