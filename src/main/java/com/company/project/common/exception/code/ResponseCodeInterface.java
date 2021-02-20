package com.company.project.common.exception.code;

/**
 * ResponseCodeInterface
 * @author mc
 * @version V1.0
 * @date 2021/2/18
 */
public interface ResponseCodeInterface {
    /**
     * 获取code
     *
     * @return code
     */
    int getCode();

    /**
     * 获取信息
     *
     * @return msg
     */
    String getMsg();
}
