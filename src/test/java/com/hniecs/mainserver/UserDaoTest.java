package com.hniecs.mainserver;

import com.hniecs.mainserver.dao.UserDao;
import com.hniecs.mainserver.entity.UserEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @desc      用户dao层测试类 UserDaoTest.java
 * @author    yijie
 * @date      2020-09-13
 * @logs[0]   yijie 2020-09-13 创建了文件UserDaoTest.java
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void testInsert() throws Exception {
        userDao.insert(new UserEntity("aa1", "aa1"));
        userDao.insert(new UserEntity("bb1", "bb1"));
        userDao.insert(new UserEntity("cc1", "cc1"));

        Assert.assertEquals(3, userDao.getSimpleUsers().size());
    }

    @Test
    public void testQuery() throws Exception {
        List<UserEntity> users = userDao.getSimpleUsers();
        System.out.println(users.toString());
    }

    @Test
    public void testUpdate() throws Exception {
        UserEntity user = userDao.getUserSimpleById(9L);
        System.out.println(user.toString());
        user.setUserName("neo");
        userDao.update(user);
        Assert.assertTrue(("neo".equals(userDao.getUserSimpleById(9L).getUserName())));
    }
}
