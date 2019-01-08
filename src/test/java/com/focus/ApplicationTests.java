package com.focus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description：
 * @Author：shadow
 * @Date：ceate in 14:18 2018/12/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Configuration
@ActiveProfiles("test")
public class ApplicationTests {
    @Test
    public void test() {

    }
}
