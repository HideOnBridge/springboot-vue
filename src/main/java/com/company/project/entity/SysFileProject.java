package com.company.project.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;

/**
 * 项目project
 * @author mc
 * @version V1.0
 * @date 2021/2/18
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class SysFileProject extends BaseEntity implements Serializable {

    @TableId
    private String id;

    @TableField(value = "projectName")
    private String projectName;  //项目名称

    @TableField(value = "projectID")
    private String projectID;    //项目ID

    @TableField(fill = FieldFill.INSERT,value = "create_time")
    private Date createTime;












}
