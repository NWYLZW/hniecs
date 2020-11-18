package com.hniecs.mainserver.model;

import com.hniecs.mainserver.dao.InvitationCodeDao;
import com.hniecs.mainserver.entity.InvitationCodeEntity;
import com.hniecs.mainserver.entity.user.UserEntity;
import com.hniecs.mainserver.exception.CommonExceptions;
import com.hniecs.mainserver.exception.UserExceptions;
import com.hniecs.mainserver.tool.CommonUseStrings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.w3c.dom.ranges.RangeException;

import javax.annotation.Resource;
import java.util.*;

/**
 * @desc    InvitationCodeModel.java
 * @author  yijie
 * @date    2020-09-13 18:57
 * @logs[0] 2020-09-13 18:57 yijie 创建了InvitationCodeModel.java文件
 * @logs[1] 2020-09-15 15:51 yijie 完善一些内容
 * @logs[2] 2020-09-25 03:01 yijie 重构代码，修改返回值获取方式，补充注释，预留TODO
 * @logs[3] 2020-11-18 12:56 yijie 重构代码
 */
@Repository
@Slf4j
public class InvitationCodeModel {
    @Resource
    private InvitationCodeDao invitationCodeDao;

    /**
     * 根据邀请码数据，将邀请码插到数据库中
     * @param user                  用户实体
     * @param availableInviteCount  能邀请的用户个数
     * @param invitationCodes       邀请码数组
     * @param tagName               邀请码标签
     */
    public Map<String, Integer> addInvitationCodes(
        UserEntity user, int availableInviteCount, String tagName, List<String> invitationCodes
    ) {
        // 操作信息
        int succeedCount = 0;
        int failureCount = 0;
        InvitationCodeEntity ic = new InvitationCodeEntity();
        ic.setMtime(new Date());
        ic.setCtime(new Date());
        ic.setCreateUserId(user.getId());
        ic.setAvailableInviteCount(availableInviteCount);
        ic.setTagName(tagName);
        ic.setStatus(0);

        for (String invitationCode : invitationCodes) {
            ic.setInvitationCode(invitationCode);
            try {
                invitationCodeDao.addNew(ic);
                succeedCount++;
            } catch (Exception e) {
                e.printStackTrace();
                failureCount++;
            }
        }
        if(succeedCount == 0) {
            throw CommonExceptions.INTERNAL_SERVER_ERROR.exception;
        }

        int finalSucceedCount = succeedCount;
        int finalFailureCount = failureCount;
        return new HashMap<>(){{
            put("successCount", finalSucceedCount);
            put("failureCount", finalFailureCount);
        }};
    }

    /**
     * 使用一个邀请码实体
     * @param invitationCode    邀请码实体
     */
    public void useInvitationCode(InvitationCodeEntity invitationCode) {
        int count = invitationCode.getAvailableInviteCount();
        if(count > 0) {
            invitationCode.setAvailableInviteCount(count - 1);
            this.updateInvitationCode(invitationCode);
        }else {
            throw UserExceptions.INVITATION_CODE_COUNT_0.exception;
        }
    }

    /**
     * 删除一个邀请码
     * @param id    邀请码id
     */
    public void deleteById(Long id) {
        InvitationCodeEntity ice = new InvitationCodeEntity();
        ice.setId(id);
        ice.setStatus(-1);
        ice.setMtime(new Date());
        try {
            if (invitationCodeDao.update(ice) == 0) {
                throw CommonExceptions.INTERNAL_SERVER_ERROR.exception;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw CommonExceptions.INTERNAL_SERVER_ERROR.exception;
        }
    }

    /**
     * 更新邀请码信息
     * @param invitationCode    邀请码实体
     */
    public void updateInvitationCode(InvitationCodeEntity invitationCode) {
        // 保证对外的唯一一致性
        if (invitationCode.getStatus() != null && invitationCode.getStatus() == -1) {
            throw new RangeException((short) 0, "该接口不支持将邀请码删除的功能");
        }

        try {
            invitationCode.setMtime(new Date());
            if (invitationCodeDao.update(invitationCode) == 0) {
                throw CommonExceptions.INTERNAL_SERVER_ERROR.exception;
            }
        } catch (Exception e) {
            throw CommonExceptions.INTERNAL_SERVER_ERROR.exception;
        }
    }

    /**
     * 进行详细的邀请码查询
     * @param tagName           邀请码标签名
     * @param creatorName       创建者用户名
     * @param invitationCode    邀请码内容
     * @return 满足条件的邀请码实体列表
     */
    public List<InvitationCodeEntity> getInvitationCodeList(
        String creatorName, String tagName, String invitationCode
    ) {
        HashMap<String, String> searchConditions = new HashMap<>(){{
            put("creatorName", creatorName);
            put("tagName", tagName);
            put("invitationCode", invitationCode);
        }};
        searchConditions.forEach((key, value) -> {
            if (value == null || value.equals("")) {
                searchConditions.put(key, "%");
            } else {
                searchConditions.put(key, "%" + value + "%");
            }
        });

        try {
            return invitationCodeDao.getInvitationCodes(
                searchConditions.get("creatorName"),
                searchConditions.get("tagName"),
                searchConditions.get("invitationCode")
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw CommonExceptions.INTERNAL_SERVER_ERROR.exception;
        }
    }

    /**
     * 查询某邀请码是否能够使用
     * @param invitationCode    邀请码内容
     */
    public InvitationCodeEntity findAbleUse(String invitationCode) {
        InvitationCodeEntity ic = invitationCodeDao
            .getOne(
                InvitationCodeDao.columnName.invitation_code,
                invitationCode
            );

        if( ic != null && ic.getStatus() != 2 && ic.getAvailableInviteCount() > 0) {
            return ic;
        }else {
            return null;
        }
    }

    /**
     * 判断id邀请码是否存在
     * @param id 邀请码id
     */
    public boolean have(long id){
        InvitationCodeEntity invitationCodeEntity = invitationCodeDao.getOne(
            InvitationCodeDao.columnName.id, Long.toString(id)
        );
        if (invitationCodeEntity == null) {
            return false;
        }
        return invitationCodeEntity.getStatus() != -1;
    }

    /**
     * 返回所有的不重复不为空的tagName
     * @return tagName数组
     */
    public List<String> getTagName(){
        try {
            return invitationCodeDao.getTagNameList();
        } catch (Exception e) {
            e.printStackTrace();
            throw CommonExceptions.INTERNAL_SERVER_ERROR.exception;
        }
    }
}
