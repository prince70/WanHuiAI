package com.niwj.wanhuiai.entity;

import org.litepal.crud.LitePalSupport;

/**
 * Created by Administrator on 2019/10/9.
 */
public class DetectReport extends LitePalSupport {
    private int id;
    private String time;
    private String FailNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFailNumber() {
        return FailNumber;
    }

    public void setFailNumber(String failNumber) {
        FailNumber = failNumber;
    }

    @Override
    public String toString() {
        return "DetectReport{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", FailNumber='" + FailNumber + '\'' +
                '}';
    }
}
