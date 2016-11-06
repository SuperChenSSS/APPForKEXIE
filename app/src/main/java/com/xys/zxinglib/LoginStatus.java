package com.xys.zxinglib;
import java.io.Serializable;
/**
 * Created by SuperChen on 2016/11/6.
 */

public class LoginStatus implements Serializable{
    private String errno;
    private String info;
    private String data;

    public LoginStatus(String errno,String info,String data){
        this.errno = errno;
        this.info = info;
        this.data = data;
    }
    public String getErrno(){
        return errno;
    }
    public void setErrno(String errno){
        this.errno = errno;
    }
    public String getInfo(){
        return info;
    }
    public void setInfo(String info){
        this.info = info;
    }
    public String getData(){
        return data;
    }
    public void setData(String data){
        this.data = data;
    }
}
