package com.company.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.entity.SysFileCollects;

/**
 * 文件收藏service
 */
public interface SysFileCollectsService extends IService<SysFileCollects> {
    /**
     * 新增收藏
     * @param sfc
     */
    void insert(SysFileCollects sfc);


    /**
     * 删除
     * @param username
     * @param id
     */
    void delete(String username, String id);


    /**
     *  备注
     * @param sfc
     */
    void remarksCollects(SysFileCollects sfc);


    /**
     * 是否收藏
     * @return
     */
    SysFileCollects selectIsCollects(String uid, String pid, String fid);



}
