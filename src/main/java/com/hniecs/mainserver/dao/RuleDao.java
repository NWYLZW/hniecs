package com.hniecs.mainserver.dao;

import com.hniecs.mainserver.entity.Rules;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @desc    用户规则表 RuleDao.java
 * @author  yijie
 * @date    2020-09-25 05:40
 * @logs[0] 2020-09-25 05:40 yijie  创建了 RuleDao.java 文件
 */
@Mapper
public interface RuleDao {
    /**
     * 获取权限组列表
     * @return  权限组实体
     */
    @Select("select * from rule")
    List<Rules.RuleEntity> all();
    /**
     * 通过id获得某个权限组信息
     * @param   id 权限组id
     * @return  权限组实体
     */
    @Select("select * from rule where id=#{id}")
    Rules.RuleEntity getById(Long id);
    /**
     * 通过权限组id 判断某个权限是否存在
     * @param   id 权限组id
     * @return  0 不存在，1 存在
     */
    @Select("select count(1) from rule where id=#{id}")
    int have(Long id);

    /**
     * 新增一个权限组
     * @param   rule 权限组实体
     */
    @Insert(
        "insert into " +
            "rule(name, permissions) " +
            "values(#{name}, #{permissions})"
    )
    int addNew(Rules.RuleEntity rule);

    /**
     * 更新一个权限组
     * @param   rule    权限组实体
     */
    @Update(
        "update rule " +
            "set name=#{name}, permissions=#{permissions} " +
            "where id=#{id}"
    )
    int update(Rules.RuleEntity rule);
}
