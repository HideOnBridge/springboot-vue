package com.company.project.vo.resp;

import com.company.project.entity.SysRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * UserOwnRoleRespVO
 * @author mc
 * @version V1.0
 * @date 2021/2/18
 */
@Data
public class UserOwnRoleRespVO {
    @ApiModelProperty("所有角色集合")
    private List<SysRole> allRole;
    @ApiModelProperty(value = "用户所拥有角色集合")
    private List<String> ownRoles;
}
