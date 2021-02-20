package com.company.project.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
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

    @NotBlank(message = "项目名称不可以为空!!")
    private String projectName;  //项目名称

    @NotBlank(message = "项目ID不可以为空!!")
    private String projectID;    //项目ID

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;












}
