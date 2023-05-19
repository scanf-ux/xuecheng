package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseMarket;
import com.xuecheng.content.service.CourseBaseInfoService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @program: xuecheng-plus-project
 * @description: 课程信息管理业务接口实现类
 * @author: xiejie
 * @create: 2023-04-10 18:00
 **/

@Slf4j
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {


    @Autowired
    CourseBaseMapper courseBaseMapper;

    @Autowired
    CourseMarketMapper courseMarketMapper;

    @Autowired
    CourseCategoryMapper courseCategoryMapper;


    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto) {
        LambdaQueryWrapper<CourseBase> courseBaseLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //构建查询条件，根据课程审核状态查询
        courseBaseLambdaQueryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getCourseName()), CourseBase::getName, queryCourseParamsDto.getCourseName());
        //构建查询条件，根据课程名称查询
        courseBaseLambdaQueryWrapper.like(StringUtils.isNotEmpty(queryCourseParamsDto.getAuditStatus()), CourseBase::getAuditStatus, queryCourseParamsDto.getAuditStatus());
        //构建查询条件，根据课程发布状态查询
        //todo:根据课程发布状态查询
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        Page<CourseBase> courseBasePage = courseBaseMapper.selectPage(page, courseBaseLambdaQueryWrapper);
        List<CourseBase> courseBaseList = courseBasePage.getRecords();
        Long totle = courseBasePage.getTotal();
        PageResult<CourseBase> pageResult = new PageResult<>(courseBaseList, totle, pageParams.getPageNo(), pageParams.getPageSize());

        return pageResult;
    }

    @Transactional
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto) {
        //合法性校验
        if (StringUtils.isBlank(dto.getName())) {
            throw new XueChengPlusException("课程名称为空");
        }

        if (StringUtils.isBlank(dto.getMt())) {
            throw new XueChengPlusException("课程分类为空");
        }

        if (StringUtils.isBlank(dto.getSt())) {
            throw new XueChengPlusException("课程分类为空");
        }

        if (StringUtils.isBlank(dto.getGrade())) {
            throw new XueChengPlusException("课程等级为空");
        }

        if (StringUtils.isBlank(dto.getTeachmode())) {
            throw new XueChengPlusException("教育模式为空");
        }

        if (StringUtils.isBlank(dto.getUsers())) {
            throw new XueChengPlusException("适应人群");
        }

        if (StringUtils.isBlank(dto.getCharge())) {
            throw new XueChengPlusException("收费规则为空");
        }


        //新增对象
        CourseBase courseBaseNew = new CourseBase();
        //将填写的课程信息赋值给新增对象
        BeanUtils.copyProperties(dto, courseBaseNew);
        //设置审核状态
        courseBaseNew.setAuditStatus("202002");
        //设置发布状态
        courseBaseNew.setStatus("203001");
        //机构id
        courseBaseNew.setCompanyId(companyId);
        courseBaseNew.setCompanyName("hhhhhhhh");
        //添加时间
        courseBaseNew.setCreateDate(LocalDateTime.now());
        //插入课程基本信息表
        int insert = courseBaseMapper.insert(courseBaseNew);
        if (insert <= 0) {
            throw new RuntimeException("新增课程基本信息失败");
        }
        //向课程营销表保存课程营销信息
        CourseMarket courseMarket=new CourseMarket();
        BeanUtils.copyProperties(dto,courseMarket);
        Long courseBaseId=courseBaseNew.getId();
        courseMarket.setId(courseBaseId);
        saveCourseMarket(courseMarket);
        //todo:查询课程基本信息及营销信息并返回
        CourseBaseInfoDto courseBaseInfo = getCourseBaseInfo(courseBaseId);
        return courseBaseInfo;
    }

    @Override
    public CourseBaseInfoDto getCourseBaseById(Long courseId) {
        return getCourseBaseInfo(courseId);
    }

    @Override
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto) {
        //合法性校验

        //课程id
        Long courseId = dto.getId();
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if(courseBase==null){
            XueChengPlusException.cast("课程不存在");
        }

        //校验本机构只能修改本机构的课程
        if(!courseBase.getCompanyId().equals(companyId)){
            XueChengPlusException.cast("本机构只能修改本机构的课程");
        }

        CourseBase courseBase1 = new CourseBase();
        CourseMarket courseMarket=new CourseMarket();
        BeanUtils.copyProperties(dto,courseBase1);
        courseBase1.setChangeDate(LocalDateTime.now());
        BeanUtils.copyProperties(dto,courseMarket);
        int update=courseBaseMapper.updateById(courseBase1);
        int update1=saveCourseMarket(courseMarket);
        if((update+update1)<=1){
            throw new RuntimeException("修改课程基本信息失败");
        }
        return this.getCourseBaseInfo(courseId);
    }

    //查询课程信息
    public CourseBaseInfoDto getCourseBaseInfo(Long courseId){

        //从课程基本表查询
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase==null){
            return null;
        }
        //从课程营销表查询
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);

        //组装在一起
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase,courseBaseInfoDto);
        BeanUtils.copyProperties(courseMarket,courseBaseInfoDto);
        //todo :课程分类名称设置到coursecourseBaseInfoDto

        return  courseBaseInfoDto;
    }
    //单独写一个方法保存营销星系，存在则更新，不存在则添加
    private  int saveCourseMarket(CourseMarket courseMarketNew){
        //参数的合法性校验
        String charge=courseMarketNew.getCharge();
        if ("201001".equals(charge)) {
            BigDecimal price = BigDecimal.valueOf(courseMarketNew.getPrice());
            if (StringUtils.isEmpty(String.valueOf(price))) {
                throw new XueChengPlusException("收费课程价格不能为空");
            }
            courseMarketNew.setPrice(courseMarketNew.getPrice().floatValue());
        }

        CourseMarket courseMarket=courseMarketMapper.selectById(courseMarketNew.getId());
        if(courseMarket==null){
            int insert=courseMarketMapper.insert(courseMarketNew);
            return insert;
        }else {
            BeanUtils.copyProperties(courseMarketNew,courseMarket);
            courseMarket.setId(courseMarket.getId());
            int i=courseMarketMapper.updateById(courseMarket);
            return i;
        }
    }
}
