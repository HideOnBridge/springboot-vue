package com.company.project.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class SysBrowsingUserHistory extends BaseEntity implements Serializable {

    @TableId
    private String id;

    @TableField("projectName")
    private String projectName;

    @TableField("browseUserId")  //浏览的用户ID,为了名字重复时区分
    private String browseUserId;

    @TableField("username")
    private String username;  //浏览的用户ID

    @TableField("sex")
    private String sex;

    @TableField("IDType")
    private String IDType;

    @TableField("IDcard")
    private String IDcard;

    @TableField("phone")
    private String phone;

    @TableField("area")
    private String area;

    @TableField("birthday")
    private String birthday;

    @TableField(fill = FieldFill.INSERT,value = "create_time")
    private Date createTime;  //创建时间

    @TableField("remarks")
    private String remarks;

    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;

    @TableField("nowBrowsUserID")
    private String nowBrowsUserID;




}
