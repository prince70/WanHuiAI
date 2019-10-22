package com.niwj.wanhuiai.utils;

import android.graphics.Bitmap;

import com.niwj.wanhuiai.entity.ImagePiece;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片切割工具类
 */
public class ImageSplitter {

    /**
     * 图片切割   按长宽等比例分割
     *
     * @param bitmap 导入图片
     * @param x      x轴切割
     * @param y      y轴切割
     * @return
     */
    public static List<ImagePiece> split(Bitmap bitmap, int x, int y) {
        List<ImagePiece> pieces = new ArrayList<ImagePiece>();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int pieceWidth = width / x;
        int pieceHeight = height / y;
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                ImagePiece image = new ImagePiece();
                image.index = j + i * x;
                int xValue = j * pieceWidth;
                int yValue = i * pieceHeight;
                image.bitmap = Bitmap.createBitmap(bitmap, xValue, yValue,
                        pieceWidth, pieceHeight);
                pieces.add(image);
            }
        }
        return pieces;
    }
}
