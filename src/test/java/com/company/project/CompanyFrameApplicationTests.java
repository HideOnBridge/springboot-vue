package com.company.project;

import com.company.project.service.UserProjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyFrameApplicationTests {
    @Resource
    private UserProjectService service;

    @Test
    public void contextLoads() {

    }

}
