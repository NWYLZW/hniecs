package com.hniecs.mainserver.dao;

import com.hniecs.mainserver.entity.Rules;
import com.hniecs.mainserver.entity.user.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
     * 新增一个权限组
     * @param   rule 权限组实体
     * @return 操作成功行数
     */
    @Insert(
        "insert into " +
            "user(id, name, permissions) " +
            "values(#{id}, #{name}, #{permissions})"
    )
    int addNew(Rules.RuleEntity rule);
}
