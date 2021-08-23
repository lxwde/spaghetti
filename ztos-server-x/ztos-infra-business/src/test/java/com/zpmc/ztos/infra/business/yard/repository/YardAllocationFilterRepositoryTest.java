package com.zpmc.ztos.infra.business.yard.repository;

import com.zpmc.ztos.infra.business.DummyApp;
import com.zpmc.ztos.infra.business.yard.YardAllocationFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DummyApp.class)
public class YardAllocationFilterRepositoryTest {

    @Autowired
    private YardAllocationFilterRepository repo;

    @Test
    public void test() {
        List<YardAllocationFilter> all = repo.findAll();
        System.out.println(all);
    }
}