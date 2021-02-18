package com.company.project.controller;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
* @author machao
* @version V1.0
* @date 2021/2/4
*/
@Api(tags = "验证码相关")
@RestController
@Slf4j
@RequestMapping("/sys")
public class KaptchaController {
    /**
     * 获取验证码图片 test
     * Gets captcha code.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException the io exception
     */
    @GetMapping("/getVerify")
    @ApiOperation(value = "获取验证码",notes = "不需要权限")
    public void getCaptchaCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48);
        captcha.setLen(2);
        CaptchaUtil.out(captcha, request, response);
    }

}
