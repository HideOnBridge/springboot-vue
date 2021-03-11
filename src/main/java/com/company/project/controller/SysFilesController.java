package com.company.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.project.common.aop.annotation.LogAnnotation;
import com.company.project.common.utils.DataResult;
import com.company.project.entity.*;
import com.company.project.mapper.SysUserMapper;
import com.company.project.service.*;
import com.company.project.vo.page.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SysFilesController {
    @Resource
    private SysFilesService sysFilesService;
    @Resource
    private SysProjectsFilesService sysProjectsFilesService;
    @Resource
    SysUserMapper sysUserMapper;
    @Resource
    private SysFileCollectsService sysFileCollectsService;


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

//------------------------------------------------------------------------------------------------------------------------------------------//
//    @ApiOperation(value = "文件列表",notes = "文件列表，需要data:list权限")
//    @PostMapping("data/getFiles")
//    @RequiresPermissions("sys:data:list")
//    public DataResult getFiles(@RequestBody SysFilesEntity sysFiles){
//        System.out.println("开始执行查询  --> " + sysFiles.getLimit() + "---" + sysFiles.getPage());
//        List<FileDocument> fileDocuments = fileService.listFilesByPage(sysFiles.getPage(), sysFiles.getLimit()); //分页记录数
//        PageVo pageVo = new PageVo(fileDocuments,sysFiles.getPage(),sysFiles.getLimit());
//        List<FileDocument> lists = fileService.findAll(); //总记录数
//        int PageCount = lists.size()/sysFiles.getLimit();
//        if(lists.size() % sysFiles.getLimit() !=0 ){
//            PageCount++;
//        }
//        pageVo.setTotal(lists.size());
//        pageVo.setPages(PageCount);
//        return DataResult.success(pageVo);
//    }

    @ApiOperation(value = "文件列表",notes = "项目对应的文件列表，需要data:list权限")
    @PostMapping("data/getFiles")
    @RequiresPermissions("sys:data:list")
    public DataResult getFiles(@RequestBody SysProjectsFiles sys, @RequestParam("pid") String pid, @RequestParam("username") String username){
        SysUser sysUser = sysUserMapper.selectByUsername(username);
        Page page = new Page(sys.getPage(), sys.getLimit());
        LambdaQueryWrapper<SysProjectsFiles> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysProjectsFiles::getProject_id, pid);
        queryWrapper.orderByDesc(SysProjectsFiles::getCreateTime);
        List<SysProjectsFiles>  list= sysProjectsFilesService.page(page, queryWrapper).getRecords();
        for(int s = 0;s < list.size();s++){
            SysProjectsFiles file = list.get(s);
            String fid = list.get(s).getId();
            SysFileCollects sysFileCollects = sysFileCollectsService.selectIsCollects(sysUser.getId(), pid, fid);
            System.out.println("entity ------> " + sysFileCollects);
            if(sysFileCollects != null){
                file.setIsCollects("true");
            }else {
                file.setIsCollects("false");
            }

        }
        Page lastPage = sysProjectsFilesService.page(page, queryWrapper);
        lastPage.setRecords(list);
        System.out.println("分页数据------> " +  lastPage.getRecords());


        return DataResult.success(lastPage);
    }

    @ApiOperation(value = "项目--> 新增文件")
    @PostMapping("/add")
    @RequiresPermissions(value = "sysFiles:add")
    public DataResult addFile(@RequestParam(value = "file") MultipartFile file, @RequestParam("pid") String pid, @RequestParam("uploadUser") String uploadUser) {
        //判断文件是否空
        if (file == null || file.getOriginalFilename() == null || "".equalsIgnoreCase(file.getOriginalFilename().trim())) {
            return DataResult.fail("文件为空");
        }
        String fileName = file.getOriginalFilename();
        SysProjectsFiles sysProjectsFiles = new SysProjectsFiles();
        sysProjectsFiles.setUploadUser(uploadUser);
        sysProjectsFiles.setFileName(fileName);
        sysProjectsFiles.setProject_id(pid);
        sysProjectsFilesService.save(sysProjectsFiles);
        return DataResult.success();
    }

    @ApiOperation(value = "项目管理", notes = "文件备注")
    @PostMapping("/remarks")
    @LogAnnotation(title ="文件备注", action = "项目下文价备注")
    public DataResult fileRemarks(@RequestBody String value){
        log.info("文件信息 ----> " + value);
        String [] params = value.split("#");
        SysProjectsFiles sysProjectsFiles = new SysProjectsFiles();
        sysProjectsFiles.setUploadUser(params[1]);
        sysProjectsFiles.setId(params[0]);
        sysProjectsFiles.setRemarks(params[2]);
        sysProjectsFilesService.remarks(sysProjectsFiles);
        return DataResult.success();
    }

    @ApiOperation(value = "项目管理", notes = "文件收藏")
    @PostMapping("/collects")
    @LogAnnotation(title ="文件收藏", action = "项目下文价收藏")
    public DataResult fileCollects(@RequestBody String value){
        String [] params = value.split("#");
        SysUser sysUser = sysUserMapper.selectByUsername(params[1]);
        SysFileCollects sysFileCollects = new SysFileCollects();
        sysFileCollects.setUserid(sysUser.getId());
        sysFileCollects.setFileId(params[0]);
        sysFileCollects.setProjectName(params[2]);
        sysFileCollects.setFileName(params[3]);
        sysFileCollectsService.insert(sysFileCollects);
        return DataResult.success();
    }
}
