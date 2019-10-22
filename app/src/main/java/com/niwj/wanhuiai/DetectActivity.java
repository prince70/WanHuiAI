package com.niwj.wanhuiai;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.niwj.wanhuiai.adapter.ImageAdapterfor7240Yellow;
import com.niwj.wanhuiai.entity.ImageDetect7240Yellow;
import com.niwj.wanhuiai.entity.ImagePiece;
import com.niwj.wanhuiai.entity.RGBMULTI7240Yellow;
import com.niwj.wanhuiai.utils.CountDownHelper;
import com.niwj.wanhuiai.utils.ImageSplitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/9/24.
 */
public class DetectActivity extends AppCompatActivity {
    private static final String TAG = "DetectActivity";

    private ImageView iv_show;
    private TextView tv_count;

    private GridView gv;
    private List<ImageDetect7240Yellow> list = new ArrayList<>();
    private ImageAdapterfor7240Yellow adapter;
    private List<RGBMULTI7240Yellow> rgbList = new ArrayList<>();

    private Bitmap bitmap;

    private Button btn_start;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect);
        initView();

        Intent intent = getIntent();
        if (intent != null) {
            Uri data = intent.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data);
            } catch (IOException e) {
                e.printStackTrace();
            }
            iv_show.setImageURI(data);
        }
        initData();

        /**
         * 启动倒计时
         */
        EndOfCountDown();


    }

    private void EndOfCountDown() {
        CountDownHelper helper = new CountDownHelper(tv_count, "0", 10, 1);
        helper.setOnFinishListener(new CountDownHelper.OnFinishListener() {
            @Override
            public void finish() {
                DetectActivity.this.finish();
            }
        });

        helper.start();
    }


    private void initView() {
        iv_show = findViewById(R.id.iv_show);
        tv_count = findViewById(R.id.tv_count);
        gv = findViewById(R.id.gv);
        btn_start=findViewById(R.id.btn_start);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EndOfCountDown();
            }
        });
    }

    private void initData() {
        Log.e(TAG, "initData: 清空前list.size()"+list.size() );
        Log.e(TAG, "initData: 清空前rgbList.size()"+rgbList.size() );


        list.clear();
        rgbList.clear();

        Log.e(TAG, "initData: 清空后list.size()"+list.size() );
        Log.e(TAG, "initData: 清空后rgbList.size()"+rgbList.size() );
        if (bitmap != null) {
            final List<ImagePiece> split = ImageSplitter.split(bitmap, 9, 10);
            for (int i = 0; i < split.size(); i++) {
                List<String> colorFromAdapter = getColorFromAdapter(split.get(i).bitmap);
                if (colorFromAdapter != null) {
                    int r1 = Integer.valueOf(colorFromAdapter.get(0).substring(2, 4), 16);
                    int g1 = Integer.valueOf(colorFromAdapter.get(0).substring(4, 6), 16);
                    int b1 = Integer.valueOf(colorFromAdapter.get(0).substring(6, 8), 16);

                    int r2 = Integer.valueOf(colorFromAdapter.get(1).substring(2, 4), 16);
                    int g2 = Integer.valueOf(colorFromAdapter.get(1).substring(4, 6), 16);
                    int b2 = Integer.valueOf(colorFromAdapter.get(1).substring(6, 8), 16);

                    int r3 = Integer.valueOf(colorFromAdapter.get(2).substring(2, 4), 16);
                    int g3 = Integer.valueOf(colorFromAdapter.get(2).substring(4, 6), 16);
                    int b3 = Integer.valueOf(colorFromAdapter.get(2).substring(6, 8), 16);

                    int r4 = Integer.valueOf(colorFromAdapter.get(3).substring(2, 4), 16);
                    int g4 = Integer.valueOf(colorFromAdapter.get(3).substring(4, 6), 16);
                    int b4 = Integer.valueOf(colorFromAdapter.get(3).substring(6, 8), 16);

                    int r5 = Integer.valueOf(colorFromAdapter.get(4).substring(2, 4), 16);
                    int g5 = Integer.valueOf(colorFromAdapter.get(4).substring(4, 6), 16);
                    int b5 = Integer.valueOf(colorFromAdapter.get(4).substring(6, 8), 16);

                    /**
                     * 这个颜色是每一个裁切的图的5点颜色值
                     */
                    RGBMULTI7240Yellow mmm = new RGBMULTI7240Yellow(r1, g1, b1, r2, g2, b2, r3, g3, b3, r4, g4, b4, r5, g5, b5);
                    mmm.save();

                    rgbList.add(mmm);
                    /**
                     * TODO 在这里手动添加误差范围允许值
                     */
          /*          switch (i){
                        case 0:
                            list.add(new ImageDetect7240Yellow(split.get(0).bitmap, judgePass(rgbList.get(0)), mmm));
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                        case 6:
                            break;
                        case 7:
                            break;
                        case 8:
                            break;
                        case 9:
                            break;
                        case 10:
                            break;
                        case 11:
                            break;
                        case 12:
                            break;
                        case 13:
                            break;
                        case 14:
                            break;
                        case 15:
                            break;
                        case 16:
                            break;
                        case 17:
                            break;
                        case 18:
                            break;
                        case 19:
                            break;
                        case 20:
                            break;
                        case 21:
                            break;
                        case 22:
                            break;
                        case 23:
                            break;
                        case 24:
                            break;
                        case 25:
                            break;
                        case 26:
                            break;
                        case 27:
                            break;
                        case 28:
                            break;
                        case 29:
                            break;
                        case 30:
                            break;
                        case 31:
                            break;
                        case 32:
                            break;
                        case 33:
                            break;
                        case 34:
                            break;
                        case 35:
                            break;
                        case 36:
                            break;
                        case 37:
                            break;
                        case 38:
                            break;
                        case 39:
                            break;
                        case 40:
                            break;
                        case 41:
                            break;
                        case 42:
                            break;
                        case 43:
                            break;
                        case 44:
                            break;
                        case 45:
                            break;
                        case 46:
                            break;
                        case 47:
                            break;
                        case 48:
                            break;
                        case 49:
                            break;
                        case 50:
                            break;
                        case 51:
                            break;
                        case 52:
                            break;
                        case 53:
                            break;
                        case 54:
                            break;
                        case 55:
                            break;
                        case 56:
                            break;
                        case 57:
                            break;
                        case 58:
                            break;
                        case 59:
                            break;
                        case 60:
                            break;
                        case 61:
                            break;
                        case 62:
                            break;
                        case 63:
                            break;
                        case 64:
                            break;
                        case 65:
                            break;
                        case 66:
                            break;
                        case 67:
                            break;
                        case 68:
                            break;
                        case 69:
                            break;
                        case 70:
                            break;
                        case 71:
                            break;
                        case 72:
                            break;
                        case 73:
                            break;
                        case 74:
                            break;
                        case 75:
                            break;
                        case 76:
                            break;
                        case 77:
                            break;
                        case 78:
                            break;
                        case 79:
                            break;
                        case 80:
                            break;
                        case 81:
                            break;
                        case 82:
                            break;
                        case 83:
                            break;
                        case 84:
                            break;
                        case 85:
                            break;
                        case 86:
                            break;
                        case 87:
                            break;
                        case 88:
                            break;
                        case 89:
                            break;
                        case 90:
                            break;

                    }*/
                    list.add(new ImageDetect7240Yellow(split.get(i).bitmap, judgePass(rgbList.get(i)), mmm));

                }
            }
            Log.e(TAG, "initData: 加载前list.size()"+list.size() );
            Log.e(TAG, "initData: 加载前rgbList.size()"+rgbList.size() );

            adapter = new ImageAdapterfor7240Yellow(list, this);
            gv.setNumColumns(9);
            gv.setAdapter(adapter);


        }

    }

    private List<String> getColorFromAdapter(Bitmap bitmap) {
        int width_difference = bitmap.getWidth() / 4;
        int height_difference = bitmap.getHeight() / 4;
        List<Bitmap> bitmapList = new ArrayList<>();
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap, width_difference - 5, height_difference - 5, 10, 10);
        Bitmap bitmap2 = Bitmap.createBitmap(bitmap, width_difference - 5, height_difference * 3 - 5, 10, 10);
        Bitmap bitmap3 = Bitmap.createBitmap(bitmap, width_difference * 2 - 5, height_difference * 2 - 5, 10, 10);
        Bitmap bitmap4 = Bitmap.createBitmap(bitmap, width_difference * 3 - 5, height_difference - 5, 10, 10);
        Bitmap bitmap5 = Bitmap.createBitmap(bitmap, width_difference * 3 - 5, height_difference * 3 - 5, 10, 10);
        bitmapList.add(bitmap1);
        bitmapList.add(bitmap2);
        bitmapList.add(bitmap3);
        bitmapList.add(bitmap4);
        bitmapList.add(bitmap5);

        List<String> colors = new ArrayList<>();
        String s = null;
        for (int i = 0; i < bitmapList.size(); i++) {
            ArrayList<Integer> picturePixel = getPicturePixel(bitmapList.get(i));
            //计数相同颜色数量并保存
            HashMap<Integer, Integer> color2 = new HashMap<>();
            for (Integer color : picturePixel) {
                if (color2.containsKey(color)) {
                    Integer integer = color2.get(color);
                    integer++;
                    color2.remove(color);
                    color2.put(color, integer);

                } else {
                    color2.put(color, 1);
                }
            }
            //挑选数量最多的颜色
            Iterator iter = color2.entrySet().iterator();//迭代器
            int count = 0;
            int color = 0;
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                int value = (int) entry.getValue();
                if (count < value) {
                    count = value;
                    color = (int) entry.getKey();
                }

            }
            if (color == 0) {
                return null;
            }
            s = Integer.toHexString(color);
            colors.add(s);
        }

        return colors;


    }

    /**
     * 能不能加误差值 以传参的方式   每个位置都测出范围， 就不需要放锁板在旁边
     *
     * @param RgbMulti
     * @return
     */
    private boolean judgePass(RGBMULTI7240Yellow RgbMulti) {
        boolean pass = false;
        if (rgbList != null) {
//            Log.e(TAG, "judgePass: " + rgbList.size());

//            取出第一个做对比
            RGBMULTI7240Yellow rgbmulti = rgbList.get(0);


            boolean b1 = (rgbmulti.getR1() - RgbMulti.getR1() <= 0);

            boolean b2 = (rgbmulti.getR2() - RgbMulti.getR2() <= 0);

            boolean b3 = (rgbmulti.getR3() - RgbMulti.getR3() <= 0);

            boolean b4 = (rgbmulti.getR4() - RgbMulti.getR4() <= 0);

            boolean b5 = (rgbmulti.getR5() - RgbMulti.getR5() <= 0);


            if (b1 && b2 && b3) {
                pass = true;
            } else if (b1 && b2 && b4) {
                pass = true;
            } else if (b1 && b2 && b5) {
                pass = true;
            } else if (b1 && b3 && b4) {
                pass = true;
            } else if (b1 && b3 && b5) {
                pass = true;
            } else if (b1 && b4 && b5) {
                pass = true;
            } else if (b2 && b3 && b4) {
                pass = true;
            } else if (b2 && b3 && b5) {
                pass = true;
            } else if (b2 && b4 && b5) {
                pass = true;
            } else pass = b3 && b4 && b5;

        }
        return pass;
    }


    /**
     * 获得图片的颜色值方法
     *
     * @param bitmap
     */

    private ArrayList<Integer> getPicturePixel(Bitmap bitmap) {

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // 保存所有的像素的数组，图片宽×高
        int[] pixels = new int[width * height];

        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        ArrayList<Integer> rgb = new ArrayList<>();
        for (int i = 0; i < pixels.length; i++) {
            int clr = pixels[i];
            int red = (clr & 0x00ff0000) >> 16; // 取高两位
            int green = (clr & 0x0000ff00) >> 8; // 取中两位
            int blue = clr & 0x000000ff; // 取低两位
//            Log.d("tag", "r=" + red + ",g=" + green + ",b=" + blue);
            int color = Color.rgb(red, green, blue);
            //除去白色和黑色
            if (color != Color.WHITE && color != Color.BLACK) {
                rgb.add(color);

            }

        }

        return rgb;
    }

}
