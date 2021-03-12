package com.company.project.controller;

import com.company.project.common.aop.annotation.BrowsingAnnotation;
import com.company.project.service.UserProjectService;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 视图
* @author machao
* @version V1.1
* @date 2021/2/4
*/
@Api(tags = "视图")
@Controller
@RequestMapping("/index")
public class IndexController {

    @Resource
    private UserProjectService userProjectService;

    @GetMapping("/login")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return "redirect:/index/home";
        }
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/users/password")
    public String updatePassword() {
        return "users/update_password";
    }

    @GetMapping("/users/info")
    public String userDetail(Model model) {
        model.addAttribute("flagType", "edit");
        return "users/user_edit";
    }

    @GetMapping("/menus")
    public String menusList() {

        return "menus/menu_list";
    }

    @GetMapping("/roles")
    public String roleList() {
        return "roles/role_list";
    }

    @GetMapping("/users")
    public String userList() {
        return "users/user_list";
    }

    @GetMapping("/logs")
    public String logList() {
        return "logs/log_list";
    }

    @GetMapping("/depts")
    public String deptList() {
        return "depts/dept_list";
    }

    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }

    @GetMapping("/404")
    public String error404() {
        return "error/404";
    }

    @GetMapping("/500")
    public String error405() {
        return "error/500";
    }

    @GetMapping("/main")
    public String indexHome() {
        return "main";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/build")
    public String build() {
        return "build";
    }

    @GetMapping("/sysContent")
    public String sysContent() {
        return "syscontent/list";
    }

    @GetMapping("/sysDict")
    public String sysDict() {
        return "sysdict/list";
    }

    @GetMapping("/sysGenerator")
    public String sysGenerator() {
        return "generator/list";
    }

    @GetMapping("/sysJob")
    public String sysJob() {
        return "sysjob/list";
    }

    @GetMapping("/sysJobLog")
    public String sysJobLog() {
        return "sysjoblog/list";
    }

    @GetMapping("/sysFiles")
    public String sysFiles() {
        return "sysfiles/list";
    }
    @GetMapping("/data")
    public String fileUpload(){
        return "dataUpload/data_list";
    }
    @GetMapping("/project")
    public String sysProject(HttpSession session){
        return "project/project_list";
    }
    @GetMapping("/project/add")
    public String sysProjectAdd(){
        return "project/project_add";
    }
    @GetMapping("/dataBox")
    public String sysData(){
        return "dataBox/box_home";
    }
    @GetMapping("/collect")
    public String collection(){
        return "collect/collect_list";
    }

    //双击
    @GetMapping("/clickFile")
    @BrowsingAnnotation(sign = "2")
    public String clickFile(@RequestParam String fileName){
        System.out.println("测试数据-----> " + fileName);
        return "fileDetail/fileDetail";
    }

    @GetMapping("/clickUser")
    @BrowsingAnnotation(sign = "1")
    public String clickUser(@RequestParam String username, HttpServletRequest request){
        request.getSession().setAttribute("param",username);
        System.out.println("测试数据-----> " + username);
        return "userDetail/userDetail";
    }

    @GetMapping("/sysBrowsing")
    public String sysBrowsing(){
        return "browsing/browsing_list";
    }

}
