package com.company.project.controller;

import cn.hutool.core.util.StrUtil;

import com.company.project.entity.FileDocument;
import com.company.project.service.FileService;
import com.company.project.utils.MD5Util;
import com.company.project.vo.ResponseModel;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class FileController {
    @Autowired
    private FileService fileService;

    private String serverAddress = "localhost";

    private String serverPort = "8888";

    @RequestMapping(value = "/indexList")
    @RequiresPermissions("upload")
    public String index(Model model) {
        // 展示最新二十条数据
        model.addAttribute("files", fileService.listFilesByPage(0, 20));
        return "index";
    }

    /**
     * 分页查询文件
     */
    @GetMapping("files/{pageIndex}/{pageSize}")
    @ResponseBody
    public List<FileDocument> listFilesByPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
        return fileService.listFilesByPage(pageIndex, pageSize);
    }

    /**
     * 获取文件信息
     */
    @GetMapping("files/{id}")
    //@ResponseBody
    @RequiresPermissions("upload")
    public String serveFile(@PathVariable String id,Model model) throws UnsupportedEncodingException {

        Optional<FileDocument> file = fileService.getFileById(id);
        System.out.println("file : " + file);

        if (file.isPresent()) {
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + new String(file.get().getName().getBytes("utf-8"),"ISO-8859-1"))
//                    .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
//                    .header(HttpHeaders.CONTENT_LENGTH, file.get().getSize() + "").header("Connection", "close")
//                    .body(file.get().getContent());
            model.addAttribute("infor",file.get().getContent());
            return "file/fileInfor";
        } else {
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
            return "";
        }

    }

    /**
     * 在线显示文件
     */
    @GetMapping("/view")
    @ResponseBody
    @RequiresPermissions("upload")
    public ResponseEntity<Object> serveFileOnline(@RequestParam("id") String id) {
        Optional<FileDocument> file = fileService.getFileById(id);
        System.out.println("file :::: " + file);

        if (file.isPresent()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "fileName=" + file.get().getName())
                    .header(HttpHeaders.CONTENT_TYPE, file.get().getContentType())
                    .header(HttpHeaders.CONTENT_LENGTH, file.get().getSize() + "").header("Connection", "close")
                    .header(HttpHeaders.CONTENT_LENGTH , file.get().getSize() + "")
                    .body(file.get().getContent());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not found");
        }


    }

    /**
     * 上传
     */
    @PostMapping("/upload")
    @RequiresPermissions("upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        try {
            FileDocument fileDocument = new FileDocument();
            fileDocument.setName(file.getOriginalFilename());
            fileDocument.setSize(file.getSize());
            fileDocument.setContentType(file.getContentType());
            fileDocument.setUploadDate(new Date());
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            fileDocument.setSuffix(suffix);
            fileDocument.setMd5(MD5Util.getMD5(file.getInputStream()));
            //将文件存入gridFs
            String gridfsId = fileService.uploadFileToGridFS(file.getInputStream() , file.getContentType());
            fileDocument.setGridfsId(gridfsId);
            fileDocument.setContent(new String(file.getBytes(),"UTF-8"));
            System.out.println(" 文件内容: " + new String(file.getBytes(),"UTF-8"));
            fileDocument = fileService.saveFile(fileDocument);
            System.out.println(fileDocument);
        } catch (IOException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Your " + file.getOriginalFilename() + " is wrong!");
            return "redirect:/indexList";
        }

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/indexList";
    }

//    /**
//     * 上传接口
//     */
//    @PostMapping("/upload")
//    @ResponseBody
//    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
//        FileDocument returnFile = null;
//        try {
//            FileDocument fileDocument = new FileDocument();
//            fileDocument.setName(file.getOriginalFilename());
//            fileDocument.setSize(file.getSize());
//            fileDocument.setContentType(file.getContentType());
//            fileDocument.setUploadDate(new Date());
//            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//            fileDocument.setSuffix(suffix);
//            fileDocument.setMd5(MD5Util.getMD5(file.getInputStream()));
//            //将文件存入gridFs
//            String gridfsId = fileService.uploadFileToGridFS(file.getInputStream() , file.getContentType());
//            fileDocument.setGridfsId(gridfsId);
//            returnFile = fileService.saveFile(fileDocument);
//            String path = "//" + serverAddress + ":" + serverPort + "/view/" + returnFile.getId();
//            return ResponseEntity.status(HttpStatus.OK).body(path);
//
//        } catch (IOException | NoSuchAlgorithmException ex) {
//            ex.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
//        }
//
//    }



    /**
     * 删除文件
     */
    @GetMapping("/delete")
    @RequiresPermissions("delete")
    @ResponseBody
    public ResponseModel deleteFile(@RequestParam("id") String id) {
        ResponseModel model = ResponseModel.getInstance();
        if(!StrUtil.isEmpty(id)){
            fileService.removeFile(id);
            model.setCode(ResponseModel.Success);
            model.setMessage("删除成功");
        }else {
            model.setMessage("请传入文件id");
        }
        return model;
    }
}
