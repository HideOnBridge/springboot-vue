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
public class SysProjectsFiles extends BaseEntity implements Serializable {

    @TableId
    private String id;

    @TableField("fileName")
    private String fileName;


    @TableField("project_id")
    private String project_id;

    @TableField(fill = FieldFill.INSERT,value = "create_time")
    private Date createTime;

    @TableField("uploadUser")
    private String uploadUser;

    @TableField("remarks")
    private String remarks;

    @TableField(exist = false)
    private String isCollects;
}
