package com.niwj.wanhuiai.entity;


import org.litepal.crud.LitePalSupport;

/**
 * Created by Administrator on 2019/8/20.
 */
public class RGBMULTI7240YellowTwo extends LitePalSupport {

    private int id;
    private int r1;
    private int g1;
    private int b1;
    private int r2;
    private int g2;
    private int b2;
    private int r3;
    private int g3;
    private int b3;
    private int r4;
    private int g4;
    private int b4;


    public RGBMULTI7240YellowTwo(int r1, int g1, int b1, int r2, int g2, int b2, int r3, int g3, int b3, int r4, int g4, int b4) {
        this.r1 = r1;
        this.g1 = g1;
        this.b1 = b1;
        this.r2 = r2;
        this.g2 = g2;
        this.b2 = b2;
        this.r3 = r3;
        this.g3 = g3;
        this.b3 = b3;
        this.r4 = r4;
        this.g4 = g4;
        this.b4 = b4;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getR1() {
        return r1;
    }

    public void setR1(int r1) {
        this.r1 = r1;
    }

    public int getG1() {
        return g1;
    }

    public void setG1(int g1) {
        this.g1 = g1;
    }

    public int getB1() {
        return b1;
    }

    public void setB1(int b1) {
        this.b1 = b1;
    }

    public int getR2() {
        return r2;
    }

    public void setR2(int r2) {
        this.r2 = r2;
    }

    public int getG2() {
        return g2;
    }

    public void setG2(int g2) {
        this.g2 = g2;
    }

    public int getB2() {
        return b2;
    }

    public void setB2(int b2) {
        this.b2 = b2;
    }

    public int getR3() {
        return r3;
    }

    public void setR3(int r3) {
        this.r3 = r3;
    }

    public int getG3() {
        return g3;
    }

    public void setG3(int g3) {
        this.g3 = g3;
    }

    public int getB3() {
        return b3;
    }

    public void setB3(int b3) {
        this.b3 = b3;
    }

    public int getR4() {
        return r4;
    }

    public void setR4(int r4) {
        this.r4 = r4;
    }

    public int getG4() {
        return g4;
    }

    public void setG4(int g4) {
        this.g4 = g4;
    }

    public int getB4() {
        return b4;
    }

    public void setB4(int b4) {
        this.b4 = b4;
    }

    @Override
    public String toString() {
        return "RGBMULTI7240YellowTwo{" +
                "id=" + id +
                ", r1=" + r1 +
                ", g1=" + g1 +
                ", b1=" + b1 +
                ", r2=" + r2 +
                ", g2=" + g2 +
                ", b2=" + b2 +
                ", r3=" + r3 +
                ", g3=" + g3 +
                ", b3=" + b3 +
                ", r4=" + r4 +
                ", g4=" + g4 +
                ", b4=" + b4 +
                '}';
    }
}
