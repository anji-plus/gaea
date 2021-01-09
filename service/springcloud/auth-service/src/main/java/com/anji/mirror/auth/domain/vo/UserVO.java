package com.anji.mirror.auth.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserVO implements Serializable {

    private static final long serialVersionUID=1L;

    /** 主键 */
      private Long userId;

    /** 登陆名 */
    private String loginName;

    /** 密码 */
    private String password;

    /** 真实姓名 */
    private String realName;

    /** 手机号码 */
    private String phone;

    /** 用户邮箱 */
    private String email;

    /** 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG */
    private Integer enableFlag;

    /**  0--未删除 1--已删除 DIC_NAME=DEL_FLAG */
    private Integer deleteFlag;

    private String remark;

    /** 推荐人 */
    private String recommender;

    /** 最后一次登陆时间 */
    private LocalDateTime lastLoginTime;

    /** 最后一次登录IP */
    private String lastLoginIp;

    /** 创建人 */
    private String createdBy;

    /** 创建时间 */
    private LocalDateTime createdTime;

    /** 更新人 */
    private String updatedBy;

    /** 更新时间 */
    private LocalDateTime updatedTime;

    /************************** 以下为非原表字段 ****************************/
    /** 用户登录凭据 */
    private String token;

    /** 用户所属组织 */
    private List<Long> orgIdLists;

    /** 该用户拥有的权限，map.key=菜单code:按钮code map.val=orgIdList */
    private Map<String,List<Long>> authorityWithOrgIds;

    /** 该用户拥有的权限，map.key=菜单code:按钮code map.val=orgCodeList */
    private Map<String,List<String>> authorityWithOrgCodes;

    /**行为验证码二次校验参数*/
    private String captchaVerification;

    /** 用户所拥有的组织角色组合 */
    private List<String> orgRoleIds;

    /**修改密码时旧密码*/
    private String oldPassword;

    /**修改密码时确认密码*/
    private String confirmPassword;

}
