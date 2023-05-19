package com.xuecheng.content;

import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @program: xuecheng-plus-project
 * @description: 测试CatetoryMapper的类
 * @author: xiejie
 * @create: 2023-04-11 10:24
 **/
@SpringBootTest
public class CourseCategoryMapperTest {

    @Autowired
    CourseCategoryMapper courseCategoryMapper;
    @Test
    void testqueryTreeNodes() {
        List<CourseCategoryTreeDto> categoryTreeDtos = courseCategoryMapper.selectTreeNodes("1");
        System.out.println(categoryTreeDtos);
    }

}
