package com.niwj.wanhuiai.entity;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2019/7/9.
 */
public class ImageDetect7240Yellow {
    private Bitmap bitmap;
    private boolean IsPass;
    private RGBMULTI7240Yellow rgbmulti;

    public RGBMULTI7240Yellow getRgbmulti() {
        return rgbmulti;
    }

    public void setRgbmulti(RGBMULTI7240Yellow rgbmulti) {
        this.rgbmulti = rgbmulti;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public boolean isPass() {
        return IsPass;
    }

    public void setPass(boolean pass) {
        IsPass = pass;
    }

    public ImageDetect7240Yellow(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public ImageDetect7240Yellow(Bitmap bitmap, boolean isPass, RGBMULTI7240Yellow rgbmulti) {
        this.bitmap = bitmap;
        IsPass = isPass;
        this.rgbmulti = rgbmulti;
    }

    public ImageDetect7240Yellow(Bitmap bitmap, boolean isPass) {
        this.bitmap = bitmap;
        IsPass = isPass;
    }
}
