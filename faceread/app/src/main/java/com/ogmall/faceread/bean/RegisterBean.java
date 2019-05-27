package com.ogmall.faceread.bean;

/**
 * Created by Administrator on 2019/3/18.
 * {"code":200,"message":"success","data":{"expire":604800,"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1OSIsImlhdCI6MTU1MjkyNzM0NiwiZXhwIjoxNTUzNTMyMTQ2fQ.Usxw0FKXOlbQ5AQKzgkD0rVZN65fh1k2CDwlAPTuiLgLX68GDJD6bDQm3xy0VFbzCYW_60A1BpG02KBQoH9kpg"},"version":"1.0.0","mobile":""}

 */

public class RegisterBean {
    public int code;
    public String message;
    public Result data;
    public static class Result{
        public long expire;
        public String token;
    }
}
