package com.kxtx.dubbo.monitor.domain.example;

import java.io.Serializable;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/4/11
 */
public class JSONResult implements Serializable {

    private String message;
    private int code = -1;
    private String messageCode;
    private Object body;

    public JSONResult() {
    }

    public JSONResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "JSONResult{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", messageCode='" + messageCode + '\'' +
                ", body=" + body +
                '}';
    }
}
