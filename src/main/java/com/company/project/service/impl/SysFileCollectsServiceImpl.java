package com.company.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.entity.SysFileCollects;
import com.company.project.entity.SysUser;
import com.company.project.mapper.SysFileCollectsMapper;
import com.company.project.mapper.SysFilesMapper;
import com.company.project.mapper.SysUserMapper;
import com.company.project.service.SysFileCollectsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysFileCollectsServiceImpl extends ServiceImpl<SysFileCollectsMapper, SysFileCollects> implements SysFileCollectsService {
    @Resource
    SysFileCollectsMapper sfcoMapper;
    @Resource
    SysUserMapper sysUserMapper;

    @Override
    public void insert(SysFileCollects sfc) {

        sfcoMapper.insert(sfc);

    }

    @Override
    public void delete(String username, String id) {
        SysUser sysUser = sysUserMapper.selectByUsername(username);
        LambdaUpdateWrapper<SysFileCollects> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(SysFileCollects::getId,id).eq(SysFileCollects::getUserid,sysUser.getId()).set(SysFileCollects::getDeleted, 0);

        sfcoMapper.update(null,lambdaUpdateWrapper);
    }

    @Override
    public void remarksCollects(SysFileCollects sfc) {
        SysUser sysUser = sysUserMapper.selectByUsername(sfc.getUserid());
        LambdaUpdateWrapper<SysFileCollects> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(SysFileCollects::getId,sfc.getId()).eq(SysFileCollects::getUserid, sysUser.getId()).set(SysFileCollects::getRemarks, sfc.getRemarks());
        sfcoMapper.update(null, lambdaUpdateWrapper);

    }

    @Override
    public SysFileCollects selectIsCollects(String uid, String pid, String fid) {
        SysFileCollects sysFileCollects = sfcoMapper.selectIsCollects(uid, pid, fid);
        return sysFileCollects;
    }
}
