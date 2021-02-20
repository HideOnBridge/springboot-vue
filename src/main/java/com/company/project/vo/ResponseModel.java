package com.company.project.vo;

/**
 * ResponseModel
 * @author mc
 * @version V1.0
 * @date 2021/2/18
 */
public class ResponseModel {
    public static final String Success = "success";
    public static final String Fail = "fail";

    private String code = "fail";
    private String message = "";
    private String data;

    //私有构造函数，此类不允许手动实例化，需要调用getInstance()获取实例
    private ResponseModel() {
    }

    /**
     * 返回默认的实例
     * @return
     */
    public static ResponseModel getInstance() {
        ResponseModel model = new ResponseModel();
        model.setCode(ResponseModel.Fail);
        return model;
    }

    public static String getSuccess() {
        return Success;
    }

    public static String getFail() {
        return Fail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
