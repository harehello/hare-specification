package com.hare.jpa.example;

import com.hare.jpa.HareSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;

/**
 * ClassName: HareSpecTest
 * Package: com.hare.jpa.example
 * Description: 示例
 *
 * @Author: wang cheng
 * @Create: 2023/3/22
 * @Version: v1.0
 **/
public class HareSpecTest {


    public void test() {

        Specification specification = new HareSpecification<UserDO>()
                .eq("username", "Hare")
                .or()
                .eq("username", "Tony")
                .andOr()
                .eq("nickname", "Hare")
                .eq("nickname", "Tony");


        // username = 'Hare' or username = 'Tony' and (nickname = 'Hare' or nickname = 'Tony')



    }
}
