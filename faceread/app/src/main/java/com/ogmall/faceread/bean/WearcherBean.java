package com.ogmall.faceread.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/3/18.
 */

public class WearcherBean {
    public Result data;
    public static class Result{
        public String wendu;
        public List<ForgistBean> forecast;
    }
    public static class ForgistBean{
        public String type;
    }
}
