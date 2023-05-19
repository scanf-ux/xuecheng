package com.xuecheng.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.model.po.CourseCategory;

import java.util.List;

/**
 * @program: xuecheng-plus-project
 * @description: 数据字典接口
 * @author: xiejie
 * @create: 2023-04-11 09:28
 **/
public interface CourseCategoryService extends IService<CourseCategory> {
    public List<CourseCategoryTreeDto> queryTreeNodes(String id);
}
