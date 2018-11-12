package com.zx.shark.model;


import java.sql.Date;
import java.util.List;

public class MyException extends Exception{
    private Date date;
    private String message;  //错误消息
    private String className;  //错误的类
    private String methodName;  //错误的方法
    private List<String> args;   //错误的参数

    public MyException(Date date ,String message, String className, String methodName, List<String> args) {
        this.message = message;
        this.className = className;
        this.methodName = methodName;
        this.args = args;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }
}
