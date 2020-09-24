package com.hniecs.mainserver.dao;

import com.hniecs.mainserver.entity.Rules;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleDaoTest {
    @Resource
    private RuleDao ruleDao;

    @Test
    public void testQuery() throws Exception {
        System.out.println(ruleDao.all());
    }

    @Test
    public void testadd() throws Exception {
        System.out.println(
            ruleDao.addNew(
                Rules.SUPPER_ADMIN.getR()
            )
        );
    }
}
