package com.company.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.company.project.entity.SysFileCollects;
import org.apache.ibatis.annotations.Select;

public interface SysFileCollectsMapper extends BaseMapper<SysFileCollects> {

    @Select("select * from sys_file_collects where userid = #{uid} and projectName = #{pid} and fileId = #{fid} and deleted = 1")
    SysFileCollects selectIsCollects(String uid, String pid, String fid);
}
