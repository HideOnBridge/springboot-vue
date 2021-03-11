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
public class SysFileCollects extends BaseEntity implements Serializable {
    @TableId
    private String id;  //id

    @TableField("fileName")
    private String fileName; //文件名字

    @TableField("fileId")
    private String fileId;  //文件编号

    @TableField("projectName")
    private String projectName; //文件归属项目名称

    @TableField(fill = FieldFill.INSERT,value = "create_time")
    private Date createTime;  //创建时间

    @TableField(fill = FieldFill.UPDATE,value = "update_time")
    private Date updateTime;  //创建时间

    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;  //是否删除

    @TableField("userid")
    private String userid;  //用户Id

    @TableField("remarks")
    private String remarks;  //备注信息string

}
