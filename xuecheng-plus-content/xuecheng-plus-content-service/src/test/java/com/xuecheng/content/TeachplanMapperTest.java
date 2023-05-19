package com.xuecheng.content;

import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.model.dto.TeachplanDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @program: xuecheng-plus-project
 * @description: ches
 * @author: xiejie
 * @create: 2023-04-12 15:20
 **/
@SpringBootTest
public class TeachplanMapperTest {

    @Autowired
    TeachplanMapper teachplanMapper;

    @Test
    void teach(){
        List<TeachplanDto> teachplanDtos = teachplanMapper.selectTreeNodes(117);
        System.out.println(teachplanDtos);
    }
}
