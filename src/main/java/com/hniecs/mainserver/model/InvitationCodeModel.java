package com.hniecs.mainserver.model;

import com.hniecs.mainserver.dao.InvitationCodeDao;
import com.hniecs.mainserver.entity.InvitationCodeEntity;
import com.hniecs.mainserver.entity.user.UserEntity;
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
     * @param data                  返回数据
     */
    public String addInvitationCodes(
        UserEntity user, int availableInviteCount, String tagName, List<String> invitationCodes,
        HashMap data) {
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
                failureCount++;
                log.error("插入失败！");
            }
        }

        // 加入结果
        data.put("successCount", succeedCount);
        data.put("failureCount", failureCount);

        if(succeedCount == 0) {
            return "邀请码全部生成失败";
        }else {
            return "0";
        }
    }

    /**
     * 使用一个邀请码实体
     * @param invitationCode    邀请码实体
     */
    public String useInvitationCode(InvitationCodeEntity invitationCode) {
        int count = invitationCode.getAvailableInviteCount();
        if(count > 0) {
            invitationCode.setAvailableInviteCount(count - 1);
            String message = this.updateInvitationCode(invitationCode);
            if (message.equals("0")) {
                return message;
            } else {
                return "邀请码使用出现了未知错误";
            }
        }else {
            return "邀请码可用次数已用尽";
        }
    }

    /**
     * 删除一个邀请码
     * @param id    邀请码id
     */
    public String deleteById(Long id) {
        InvitationCodeEntity ice = new InvitationCodeEntity();
        ice.setId(id);
        ice.setStatus(-1);
        try {
            if (invitationCodeDao.update(ice) == 0) {
                return "删除邀请码失败";
            } else {
                return "0";
            }
        } catch (Exception e) {
            return CommonUseStrings.SERVER_FAILED.S;
        }
    }

    /**
     * 更新邀请码信息
     * @param invitationCode    邀请码实体
     */
    public String updateInvitationCode(InvitationCodeEntity invitationCode) {
        // 保证对外的唯一一致性
        if (invitationCode.getStatus() == -1) {
            throw new RangeException((short) 0, "该接口不支持将邀请码删除的功能");
        }
        try {
            // TODO 将mtime修改为当前时间
            if (invitationCodeDao.update(invitationCode) == 0) {
                return "更新邀请码失败";
            } else {
                return "0";
            }
        } catch (Exception e) {
            return CommonUseStrings.SERVER_FAILED.S;
        }
    }

    /**
     * 进行详细的邀请码查询
     * @param tagName           邀请码标签名
     * @param creatorName       创建者用户名
     * @param invitationCode    邀请码内容
     * @param returnData        返回数据
     * @return 满足条件的邀请码实体列表
     */
    public String getInvitationCodeList(
        String creatorName, String tagName, String invitationCode,
        List<InvitationCodeEntity> returnData
    ) {
        HashMap<String, String> searchConditions = new HashMap<String, String>(){{
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
            List<InvitationCodeEntity> invitationCodeEntities = invitationCodeDao.getInvitationCodes(
                searchConditions.get("creatorName"),
                searchConditions.get("tagName"),
                searchConditions.get("invitationCode")
            );
            returnData.addAll(
                invitationCodeEntities
            );
            return "0";
        } catch (Exception e) {
            log.error("服务器错误", e);
            return "服务器错误";
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
     * @param tagNameList tagName数组
     */
    public String getTagName(List<String> tagNameList){
        try {
            tagNameList.addAll(invitationCodeDao.getTagNameList());
            return "0";
        } catch (Exception e) {
            log.error("获取标签名列表时异常", e);
            return CommonUseStrings.SERVER_FAILED.S;
        }
    }
}
