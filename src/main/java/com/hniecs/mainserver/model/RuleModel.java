package com.hniecs.mainserver.model;

import com.hniecs.mainserver.dao.RuleDao;
import com.hniecs.mainserver.entity.Rules;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @desc    RuleModel.java
 * @author  yijie
 * @date    2020-10-04 12:52
 * @logs[0] 2020-10-04 12:52 yijie  创建了 RuleModel.java 文件
 */
@Slf4j
@Component
public class RuleModel {
    @Resource
    RuleDao ruleDao;

    @Transactional
    public void addAll () {
        for (Rules rule : Rules.values()) {
            Rules.RuleEntity r = rule.getR();
            try {
                if (ruleDao.have(r.getId()) == 0) {
                    try {
                        ruleDao.addNew(r);
                    } catch (Exception e) {
                        log.error("添加权限组时出现了错误", e);
                    }
                } else {
                    try {
                        ruleDao.update(r);
                    } catch (Exception e) {
                        log.error("更新权限组时出现了错误", e);
                    }
                }
            } catch (Exception e) {
                log.error("搜索是否存在相同权限值时出现了错误", e);
            }
        }
    }
}
