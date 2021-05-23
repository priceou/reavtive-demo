package com.reactivedemo.demo01;

import com.reactivedemo.demo01.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Demo01ApplicationTests {

    @Test
    void contextLoads() {
        User u = new User();
        u.setName("jack");
        System.out.println(u.getName());
    }

}
