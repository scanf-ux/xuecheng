package com.xuecheng.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mr.M
 * @version 1.0
 * @description 分页查询结果模型类
 * @date 2023/2/11 15:40
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<CourseBase> implements Serializable {

    // 数据列表
    private List<CourseBase> items;

    //总记录数
    private long counts;

    //当前页码
    private long page;

    //每页记录数
    private long pageSize;


}
