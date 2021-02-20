package com.company.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.project.common.utils.DataResult;
import com.company.project.entity.FileDocument;
import com.company.project.entity.SysFilesEntity;
import com.company.project.entity.SysUser;
import com.company.project.service.FileService;
import com.company.project.service.SysFilesService;
import com.company.project.vo.page.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.security.krb5.internal.PAData;

import javax.annotation.Resource;
import java.util.List;


/**
 * 文件上传
 *
 * @author
 * @version V1.0
 * @date
 */
@RestController
@RequestMapping("/sysFiles")
@Api(tags = "文件管理")
public class SysFilesController {
    @Resource
    private SysFilesService sysFilesService;
    @Autowired
    private FileService fileService;

    @ApiOperation(value = "新增")
    @PostMapping("/upload")
    @RequiresPermissions(value = "sysFiles:add")
    public DataResult add(@RequestParam(value = "file") MultipartFile file) {
        //判断文件是否空
        if (file == null || file.getOriginalFilename() == null || "".equalsIgnoreCase(file.getOriginalFilename().trim())) {
            return DataResult.fail("文件为空");
        }
        return sysFilesService.saveFile(file);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/delete")
    @RequiresPermissions("sysFiles:delete")
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids) {
        sysFilesService.removeByIdsAndFiles(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "查询分页数据")
    @PostMapping("/listByPage")
    @RequiresPermissions("sysFiles:list")
    public DataResult findListByPage(@RequestBody SysFilesEntity sysFiles) {
        Page page = new Page(sysFiles.getPage(), sysFiles.getLimit());  //.eq(SysFilesEntity::getFileName,"激活码1.txt")
        IPage<SysFilesEntity> iPage = sysFilesService.page(page, Wrappers.<SysFilesEntity>lambdaQuery().orderByDesc(SysFilesEntity::getCreateDate));
        System.out.println("文件分页数据 ---> 记录值总数:"
                + iPage.getRecords().size() + "---> 每页几条记录：" +  iPage.getSize() + "--当前页" +
                 iPage.getCurrent() + "总页数" + iPage.getPages() + "总条数" + iPage.getTotal() );
        return DataResult.success(iPage);
    }


    @ApiOperation(value = "文件列表",notes = "文件列表，需要data:list权限")
    @PostMapping("data/getFiles")
    @RequiresPermissions("sys:data:list")
    public DataResult getFiles(@RequestBody SysFilesEntity sysFiles){
        System.out.println("开始执行查询  --> " + sysFiles.getLimit() + "---" + sysFiles.getPage());
        List<FileDocument> fileDocuments = fileService.listFilesByPage(sysFiles.getPage(), sysFiles.getLimit()); //分页记录数
        PageVo pageVo = new PageVo(fileDocuments,sysFiles.getPage(),sysFiles.getLimit());
        List<FileDocument> lists = fileService.findAll(); //总记录数
        int PageCount = lists.size()/sysFiles.getLimit();
        if(lists.size() % sysFiles.getLimit() !=0 ){
            PageCount++;
        }
        pageVo.setTotal(lists.size());
        pageVo.setPages(PageCount);
        return DataResult.success(pageVo);
    }


}
