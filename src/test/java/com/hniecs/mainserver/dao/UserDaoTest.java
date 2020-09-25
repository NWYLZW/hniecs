package com.hniecs.mainserver.dao;

import com.hniecs.mainserver.entity.user.UserEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @desc      用户dao层测试类 UserDaoTest.java
 * @author    yijie
 * @date      2020-09-13
 * @logs[0]   yijie 2020-09-13 创建了文件UserDaoTest.java
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {
    @Resource
    private UserDao userDao;

    @Test
    public void testInsert() throws Exception {
        UserEntity userEntity=new UserEntity("chenmou","123456789");
        userEntity.setCtime(new Date());
        userDao.addNew(userEntity);

        Assert.assertEquals(3, userDao.getSimpleUsers().size());
    }

    @Test
    public void testQuery() throws Exception {
        UserEntity user = userDao.getById(17L);
        System.out.println(user.getDetail().getRealName());
    }

    @Test
    public void testUpdate() throws Exception {
        UserEntity user = userDao.getSimpleById(17L);
        user.setPassword("yijie");
        userDao.updateById(user);
        System.out.println(user.toString());
        Assert.assertTrue(("yijie".equals(userDao.getSimpleById(17L).getUserName())));
    }

    public static void main(String[] args) {
        String pattern="\\W";
        String string="1@*23a1s5d6a4s56d1a3";
        Pattern pa= Pattern.compile(pattern);
        Matcher matcher=pa.matcher(string);
        matcher.find();
        System.out.println(matcher.end());
    }
}
