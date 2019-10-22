package com.niwj.wanhuiai;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.niwj.wanhuiai.adapter.ImageAdapterfor7240YellowTwo;
import com.niwj.wanhuiai.entity.ImageDetect7240YellowTwo;
import com.niwj.wanhuiai.entity.ImagePiece;
import com.niwj.wanhuiai.entity.RGBMULTI7240YellowTwo;
import com.niwj.wanhuiai.utils.CountDownHelper;
import com.niwj.wanhuiai.utils.ImageSplitter;

/**
 * Created by Administrator on 2019/9/24.
 */
public class DetectActivityTwo extends AppCompatActivity {
    private static final String TAG = "DetectActivity";

    private ImageView iv_show;
    private TextView tv_count;

    private GridView gv;
    private List<ImageDetect7240YellowTwo> list = new ArrayList<>();
    private ImageAdapterfor7240YellowTwo adapter;
    private List<RGBMULTI7240YellowTwo> rgbList = new ArrayList<>();

    private Bitmap bitmap;

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
//        EndOfCountDown();


    }

    private void EndOfCountDown() {
        CountDownHelper helper = new CountDownHelper(tv_count, "0", 10, 1);
        helper.setOnFinishListener(new CountDownHelper.OnFinishListener() {
            @Override
            public void finish() {
                DetectActivityTwo.this.finish();
            }
        });

        helper.start();
    }


    private void initView() {
        iv_show = findViewById(R.id.iv_show);
        tv_count = findViewById(R.id.tv_count);
        gv = findViewById(R.id.gv);

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EndOfCountDown();
            }
        });
    }

    private void initData() {

        list.clear();
        rgbList.clear();
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

                    /**
                     * 这个颜色是每一个裁切的图的5点颜色值
                     */
                    RGBMULTI7240YellowTwo mmm = new RGBMULTI7240YellowTwo(r1, g1, b1, r2, g2, b2, r3, g3, b3, r4, g4, b4);
                    mmm.save();

                    rgbList.add(mmm);
                    /**
                     * TODO 在这里手动添加误差范围允许值
                     */
//                    switch (i) {
//                        case 0:
//                            list.add(new ImageDetect7240YellowTwo(split.get(0).bitmap, JudgePassOrFailed2(rgbList.get(0),
//                                    175, 130, 7,
//                                    169, 125, 7,
//                                    172, 130, 9,
//                                    168, 124, 7,
//                                    5, 5, 3,
//                                    3, 4, 3,
//                                    5, 5, 3,
//                                    3, 3, 3), rgbList.get(0)));
//                            break;
//                        case 1:
//                            list.add(new ImageDetect7240YellowTwo(split.get(1).bitmap, JudgePassOrFailed2(rgbList.get(1),
//                                    205, 166, 9,
//                                    195, 155, 6,
//                                    209, 171, 10,
//                                    197, 157, 7,
//                                    3, 3, 4,
//                                    3, 3, 4,
//                                    3, 4, 4,
//                                    3, 3, 4), rgbList.get(1)));
//                            break;
//                        case 2:
//                            list.add(new ImageDetect7240YellowTwo(split.get(2).bitmap, JudgePassOrFailed2(rgbList.get(2),
//                                    217, 178, 8,
//                                    211, 169, 10,
//                                    216, 178, 10,
//                                    210, 170, 9,
//                                    4, 5, 4,
//                                    4, 5, 3,
//                                    3, 5, 4,
//                                    3, 5, 4), rgbList.get(2)));
//                            break;
//                        case 3:
//                            list.add(new ImageDetect7240YellowTwo(split.get(3).bitmap, JudgePassOrFailed2(rgbList.get(3),
//                                    223, 192, 12,
//                                    217, 184, 12,
//                                    222, 192, 11,
//                                    217, 186, 14,
//                                    3, 3, 3,
//                                    3, 3, 3,
//                                    3, 3, 3,
//                                    3, 3, 3), rgbList.get(3)));
//                            break;
//                        case 4:
//                            list.add(new ImageDetect7240YellowTwo(split.get(4).bitmap, JudgePassOrFailed2(rgbList.get(4),
//                                    221, 188, 13,
//                                    219, 186, 13,
//                                    219, 187, 13,
//                                    220, 187, 13,
//                                    3, 3, 3,
//                                    3, 3, 4,
//                                    3, 3, 3,
//                                    3, 4, 4), rgbList.get(4)));
//                            break;
//                        case 5:
//                            list.add(new ImageDetect7240YellowTwo(split.get(5).bitmap, JudgePassOrFailed2(rgbList.get(5),
//                                    220, 187, 14,
//                                    219, 185, 13,
//                                    217, 184, 11,
//                                    218, 185, 13,
//                                    4, 4, 4,
//                                    3, 4, 4,
//                                    3, 4, 5,
//                                    3, 4, 6), rgbList.get(5)));
//                            break;
//                        case 6:
//                            list.add(new ImageDetect7240YellowTwo(split.get(6).bitmap, JudgePassOrFailed2(rgbList.get(6),
//                                    214, 176, 11,
//                                    219, 180, 13,
//                                    214, 176, 12,
//                                    218, 179, 12,
//                                    3, 3, 4,
//                                    3, 4, 4,
//                                    3, 3, 5,
//                                    3, 3, 3), rgbList.get(6)));
//                            break;
//                        case 7:
//                            list.add(new ImageDetect7240YellowTwo(split.get(7).bitmap, JudgePassOrFailed2(rgbList.get(7),
//                                    210, 171, 12,
//                                    207, 168, 11,
//                                    208, 168, 12,
//                                    205, 167, 10,
//                                    4, 3, 3,
//                                    3, 3, 3,
//                                    3, 3, 3,
//                                    3, 4, 5), rgbList.get(7)));
//                            break;
//                        case 8:
//                            list.add(new ImageDetect7240YellowTwo(split.get(8).bitmap, JudgePassOrFailed2(rgbList.get(8),
//                                    201, 160, 10,
//                                    199, 156, 8,
//                                    201, 160, 10,
//                                    197, 153, 7,
//                                    4, 4, 4,
//                                    4, 4, 4,
//                                    4, 4, 3,
//                                    3, 4, 3), rgbList.get(8)));
//                            break;
//                        case 9:
//                            list.add(new ImageDetect7240YellowTwo(split.get(9).bitmap, JudgePassOrFailed2(rgbList.get(9),
//                                    171, 126, 7,
//                                    172, 129, 9,
//                                    169, 126, 9,
//                                    169, 127, 9,
//                                    3, 3, 3,
//                                    3, 4, 3,
//                                    4, 5, 4,
//                                    3, 3, 3), rgbList.get(9)));
//                            break;
//                        case 10:
//                            list.add(new ImageDetect7240YellowTwo(split.get(10).bitmap, JudgePassOrFailed2(rgbList.get(10),
//                                    194, 151, 10,
//                                    193, 149, 10,
//                                    198, 156, 12,
//                                    195, 153, 9,
//                                    4, 4, 4,
//                                    3, 4, 3,
//                                    3, 3, 3,
//                                    4, 4, 4), rgbList.get(10)));
//                            break;
//                        case 11:
//                            list.add(new ImageDetect7240YellowTwo(split.get(11).bitmap, JudgePassOrFailed2(rgbList.get(11),
//                                    211, 171, 9,
//                                    220, 183, 11,
//                                    211, 171, 7,
//                                    222, 185, 9,
//                                    3, 3, 3,
//                                    3, 4, 3,
//                                    4, 3, 3,
//                                    3, 3, 3), rgbList.get(11)));
//                            break;
//                        case 12:
//                            list.add(new ImageDetect7240YellowTwo(split.get(12).bitmap, JudgePassOrFailed2(rgbList.get(12),
//                                    219, 183, 11,
//                                    228, 192, 16,
//                                    222, 186, 11,
//                                    230, 194, 15,
//                                    4, 4, 3,
//                                    4, 4, 3,
//                                    4, 4, 3,
//                                    4, 4, 6), rgbList.get(12)));
//                            break;
//                        case 13:
//                            list.add(new ImageDetect7240YellowTwo(split.get(13).bitmap, JudgePassOrFailed2(rgbList.get(13),
//                                    233, 201, 17,
//                                    251, 219, 16,
//                                    232, 201, 15,
//                                    253, 222, 19,
//                                    4, 4, 5,
//                                    3, 3, 5,
//                                    4, 4, 3,
//                                    3, 3, 4), rgbList.get(13)));
//                            break;
//                        case 14:
//                            list.add(new ImageDetect7240YellowTwo(split.get(14).bitmap, JudgePassOrFailed2(rgbList.get(14),
//                                    227, 192, 12,
//                                    240, 207, 16,
//                                    227, 194, 12,
//                                    239, 208, 14,
//                                    3, 3, 3,
//                                    3, 3, 6,
//                                    3, 3, 4,
//                                    5, 3, 6), rgbList.get(14)));
//                            break;
//                        case 15:
//                            list.add(new ImageDetect7240YellowTwo(split.get(15).bitmap, JudgePassOrFailed2(rgbList.get(15),
//                                    227, 196, 16,
//                                    242, 212, 20,
//                                    227, 195, 15,
//                                    240, 210, 24,
//                                    3, 3, 3,
//                                    3, 3, 5,
//                                    3, 3, 4,
//                                    4, 3, 7), rgbList.get(15)));
//                            break;
//                        case 16:
//                            list.add(new ImageDetect7240YellowTwo(split.get(16).bitmap, JudgePassOrFailed2(rgbList.get(16),
//                                    214, 176, 13,
//                                    221, 186, 20,
//                                    210, 173, 12,
//                                    216, 178, 15,
//                                    3, 3, 4,
//                                    4, 4, 6,
//                                    3, 4, 3,
//                                    3, 3, 5), rgbList.get(16)));
//                            break;
//                        case 17:
//                            list.add(new ImageDetect7240YellowTwo(split.get(17).bitmap, JudgePassOrFailed2(rgbList.get(17),
//                                    191, 148, 9,
//                                    190, 146, 8,
//                                    189, 144, 9,
//                                    186, 142, 7,
//                                    3, 3, 3,
//                                    3, 4, 3,
//                                    3, 4, 4,
//                                    4, 4, 4), rgbList.get(17)));
//                            break;
//                        case 18:
//                            list.add(new ImageDetect7240YellowTwo(split.get(18).bitmap, JudgePassOrFailed2(rgbList.get(18),
//                                    179, 140, 10,
//                                    184, 146, 10,
//                                    175, 137, 11,
//                                    181, 143, 11,
//                                    4, 4, 3,
//                                    5, 4, 3,
//                                    3, 3, 4,
//                                    3, 3, 3), rgbList.get(18)));
//                            break;
////                            ,,,,
//                        case 19:
//                            list.add(new ImageDetect7240YellowTwo(split.get(19).bitmap, JudgePassOrFailed2(rgbList.get(19),
//                                    192, 149, 10,
//                                    194, 150, 10,
//                                    197, 154, 13,
//                                    199, 157, 10,
//                                    4, 4, 3,
//                                    5, 4, 3,
//                                    3, 4, 3,
//                                    4, 4, 3), rgbList.get(19)));
//                            break;
//                        case 20:
//                            list.add(new ImageDetect7240YellowTwo(split.get(20).bitmap, JudgePassOrFailed2(rgbList.get(20),
//                                    228, 193, 16,
//                                    232, 197, 17,
//                                    233, 200, 15,
//                                    238, 205, 20,
//                                    3, 3, 3,
//                                    4, 5, 4,
//                                    3, 4, 3,
//                                    4, 3, 3), rgbList.get(20)));
//                            break;
//                        case 21:
//                            list.add(new ImageDetect7240YellowTwo(split.get(21).bitmap, JudgePassOrFailed2(rgbList.get(21),
//                                    243, 213, 22,
//                                    246, 216, 27,
//                                    246, 215, 22,
//                                    251, 220, 29,
//                                    3, 3, 4,
//                                    3, 3, 6,
//                                    3, 3, 5,
//                                    4, 3, 5), rgbList.get(21)));
//                            break;
//                        case 22:
//                            list.add(new ImageDetect7240YellowTwo(split.get(22).bitmap, JudgePassOrFailed2(rgbList.get(22),
//                                    252, 224, 18,
//                                    254, 227, 24,
//                                    254, 230, 20,
//                                    253, 228, 18,
//                                    3, 4, 3,
//                                    3, 3, 8,
//                                    3, 3, 3,
//                                    3, 4, 4), rgbList.get(22)));
//                            break;
//                        case 23:
//                            list.add(new ImageDetect7240YellowTwo(split.get(23).bitmap, JudgePassOrFailed2(rgbList.get(23),
//                                    253, 224, 21,
//                                    253, 224, 20,
//                                    253, 225, 22,
//                                    253, 225, 21,
//                                    3, 3, 6,
//                                    3, 3, 6,
//                                    3, 3, 4,
//                                    3, 3, 5), rgbList.get(23)));
//                            break;
//                        case 24:
//                            list.add(new ImageDetect7240YellowTwo(split.get(24).bitmap, JudgePassOrFailed2(rgbList.get(24),
//                                    250, 218, 22,
//                                    251, 219, 22,
//                                    251, 219, 26,
//                                    251, 219, 22,
//                                    4, 4, 4,
//                                    3, 3, 4,
//                                    3, 3, 5,
//                                    3, 3, 3), rgbList.get(24)));
//                            break;
//                        case 25:
//                            list.add(new ImageDetect7240YellowTwo(split.get(25).bitmap, JudgePassOrFailed2(rgbList.get(25),
//                                    229, 194, 14,
//                                    221, 187, 14,
//                                    220, 185, 14,
//                                    212, 176, 12,
//                                    3, 3, 4,
//                                    3, 3, 4,
//                                    5, 4, 3,
//                                    5, 4, 3), rgbList.get(25)));
//                            break;
//                        case 26:
//                            list.add(new ImageDetect7240YellowTwo(split.get(26).bitmap, JudgePassOrFailed2(rgbList.get(26),
//                                    186, 141, 10,
//                                    184, 139, 9,
//                                    182, 137, 9,
//                                    180, 135, 9,
//                                    5, 6, 4,
//                                    3, 4, 3,
//                                    4, 4, 3,
//                                    3, 3, 3), rgbList.get(26)));
//                            break;
//                        case 27:
//                            list.add(new ImageDetect7240YellowTwo(split.get(27).bitmap, JudgePassOrFailed2(rgbList.get(27),
//                                    184, 140, 11,
//                                    184, 139, 10,
//                                    184, 139, 11,
//                                    184, 140, 13,
//                                    3, 3, 3,
//                                    3, 3, 3,
//                                    3, 4, 3,
//                                    3, 3, 3), rgbList.get(27)));
//                            break;
////                            ,,,
//                        case 28:
//                            list.add(new ImageDetect7240YellowTwo(split.get(28).bitmap, JudgePassOrFailed2(rgbList.get(28),
//                                    195, 151, 11,
//                                    191, 147, 10,
//                                    198, 154, 12,
//                                    193, 149, 10,
//                                    3, 3, 3,
//                                    3, 3, 3,
//                                    4, 3, 3,
//                                    4, 3, 4), rgbList.get(28)));
//                            break;
////                            ,,,
//                        case 29:
//                            list.add(new ImageDetect7240YellowTwo(split.get(29).bitmap, JudgePassOrFailed2(rgbList.get(29),
//                                    229, 193, 16,
//                                    221, 185, 15,
//                                    232, 197, 17,
//                                    227, 193, 14,
//                                    3, 3, 3,
//                                    3, 3, 5,
//                                    3, 3, 3,
//                                    3, 4, 4), rgbList.get(29)));
//                            break;
////                            ,,,
//                        case 30:
//                            list.add(new ImageDetect7240YellowTwo(split.get(30).bitmap, JudgePassOrFailed2(rgbList.get(30),
//                                    250, 219, 21,
//                                    239, 207, 16,
//                                    252, 223, 22,
//                                    243, 212, 16,
//                                    4, 3, 7,
//                                    3, 3, 8,
//                                    3, 4, 7,
//                                    4, 3, 5), rgbList.get(30)));
//                            break;
//                        case 31:
//                            list.add(new ImageDetect7240YellowTwo(split.get(31).bitmap, JudgePassOrFailed2(rgbList.get(31),
//                                    247, 213, 18,
//                                    236, 201, 15,
//                                    247, 214, 21,
//                                    237, 205, 17,
//                                    3, 3, 5,
//                                    3, 3, 6,
//                                    4, 3, 7,
//                                    4, 3, 5), rgbList.get(31)));
//                            break;
//                        case 32:
//                            list.add(new ImageDetect7240YellowTwo(split.get(32).bitmap, JudgePassOrFailed2(rgbList.get(32),
//                                    244, 213, 20,
//                                    230, 201, 14,
//                                    243, 213, 22,
//                                    230, 200, 16,
//                                    3, 3, 4,
//                                    3, 4, 5,
//                                    3, 3, 7,
//                                    3, 3, 4), rgbList.get(32)));
//                            break;
//                        case 33:
//                            list.add(new ImageDetect7240YellowTwo(split.get(33).bitmap, JudgePassOrFailed2(rgbList.get(33),
//                                    230, 196, 19,
//                                    219, 183, 16,
//                                    229, 195, 19,
//                                    218, 182, 16,
//                                    4, 3, 3,
//                                    3, 3, 6,
//                                    4, 3, 3,
//                                    4, 3, 5), rgbList.get(33)));
//                            break;
//                        case 34:
//                            list.add(new ImageDetect7240YellowTwo(split.get(34).bitmap, JudgePassOrFailed2(rgbList.get(34),
//                                    212, 176, 13,
//                                    195, 157, 9,
//                                    206, 168, 13,
//                                    191, 152, 11,
//                                    5, 3, 5,
//                                    3, 4, 3,
//                                    4, 3, 3,
//                                    3, 4, 3), rgbList.get(34)));
//                            break;
//                        case 35:
//                            list.add(new ImageDetect7240YellowTwo(split.get(35).bitmap, JudgePassOrFailed2(rgbList.get(35),
//                                    175, 132, 12,
//                                    170, 128, 9,
//                                    169, 126, 10,
//                                    168, 126, 12,
//                                    3, 3, 3,
//                                    3, 3, 3,
//                                    4, 4, 3,
//                                    3, 3, 3), rgbList.get(35)));
//                            break;
//                        case 36:
//                            list.add(new ImageDetect7240YellowTwo(split.get(36).bitmap, JudgePassOrFailed2(rgbList.get(36),
//                                    171, 128, 10,
//                                    164, 119, 9,
//                                    171, 126, 11,
//                                    165, 121, 12,
//                                    4, 4, 3,
//                                    5, 4, 3,
//                                    4, 3, 3,
//                                    5, 3, 3), rgbList.get(36)));
//                            break;
//                        case 37:
//                            list.add(new ImageDetect7240YellowTwo(split.get(37).bitmap, JudgePassOrFailed2(rgbList.get(37),
//                                    187, 141, 9,
//                                    182, 135, 8,
//                                    188, 141, 10,
//                                    182, 136, 10,
//                                    3, 3, 3,
//                                    3, 3, 3,
//                                    3, 3, 4,
//                                    3, 3, 3), rgbList.get(37)));
//                            break;
////                            ,,,
//                        case 38:
//                            list.add(new ImageDetect7240YellowTwo(split.get(38).bitmap, JudgePassOrFailed2(rgbList.get(38),
//                                    205, 162, 12,
//                                    192, 148, 10,
//                                    206, 164, 11,
//                                    195, 152, 11,
//                                    3, 3, 3,
//                                    3, 4, 3,
//                                    3, 3, 3,
//                                    3, 3, 4), rgbList.get(38)));
//                            break;
////                            ,,,
//                        case 39:
//                            list.add(new ImageDetect7240YellowTwo(split.get(39).bitmap, JudgePassOrFailed2(rgbList.get(39),
//                                    208, 167, 14,
//                                    194, 150, 10,
//                                    211, 170, 16,
//                                    198, 154, 12,
//                                    3, 3, 5,
//                                    3, 3, 3,
//                                    3, 3, 6,
//                                    3, 3, 4), rgbList.get(39)));
//                            break;
//                        case 40:
//                            list.add(new ImageDetect7240YellowTwo(split.get(40).bitmap, JudgePassOrFailed2(rgbList.get(40),
//                                    216, 182, 18,
//                                    203, 164, 14,
//                                    213, 178, 14,
//                                    204, 165, 17,
//                                    3, 3, 5,
//                                    3, 4, 5,
//                                    3, 3, 7,
//                                    3, 3, 5), rgbList.get(40)));
//                            break;
//                        case 41:
//                            list.add(new ImageDetect7240YellowTwo(split.get(41).bitmap, JudgePassOrFailed2(rgbList.get(41),
//                                    211, 171, 8,
//                                    201, 158, 10,
//                                    209, 171, 10,
//                                    198, 155, 8,
//                                    3, 4, 5,
//                                    3, 3, 3,
//                                    3, 3, 5,
//                                    3, 5, 4), rgbList.get(41)));
//                            break;
//                        case 42:
//                            list.add(new ImageDetect7240YellowTwo(split.get(42).bitmap, JudgePassOrFailed2(rgbList.get(42),
//                                    201, 161, 13,
//                                    192, 149, 12,
//                                    199, 159, 14,
//                                    190, 149, 13,
//                                    3, 3, 4,
//                                    4, 4, 5,
//                                    7, 5, 4,
//                                    5, 4, 4), rgbList.get(42)));
//                            break;
//                        case 43:
//                            list.add(new ImageDetect7240YellowTwo(split.get(43).bitmap, JudgePassOrFailed2(rgbList.get(43),
//                                    178, 138, 15,
//                                    168, 126, 11,
//                                    171, 133, 13,
//                                    165, 121, 11,
//                                    4, 3, 5,
//                                    3, 3, 3,
//                                    5, 4, 3,
//                                    6, 5, 7), rgbList.get(43)));
//                            break;
//                        case 44:
//                            list.add(new ImageDetect7240YellowTwo(split.get(44).bitmap, JudgePassOrFailed2(rgbList.get(44),
//                                    163, 119, 12,
//                                    161, 115, 11,
//                                    161, 117, 12,
//                                    157, 114, 9,
//                                    3, 3, 3,
//                                    3, 4, 3,
//                                    6, 3, 3,
//                                    3, 4, 3), rgbList.get(44)));
//                            break;
//                        case 45:
//                            list.add(new ImageDetect7240YellowTwo(split.get(45).bitmap, JudgePassOrFailed2(rgbList.get(45),
//                                    164, 119, 9,
//                                    160, 116, 8,
//                                    163, 118, 9,
//                                    162, 117, 10,
//                                    5, 4, 3,
//                                    5, 5, 4,
//                                    3, 5, 3,
//                                    4, 5, 3), rgbList.get(45)));
//                            break;
//                        case 46:
//                            list.add(new ImageDetect7240YellowTwo(split.get(46).bitmap, JudgePassOrFailed2(rgbList.get(46),
//                                    160, 118, 8,
//                                    154, 112, 8,
//                                    161, 119, 11,
//                                    157, 116, 10,
//                                    3, 3, 3,
//                                    5, 5, 3,
//                                    4, 5, 5,
//                                    5, 5, 3), rgbList.get(46)));
//                            break;
//                        case 47:
//                            list.add(new ImageDetect7240YellowTwo(split.get(47).bitmap, JudgePassOrFailed2(rgbList.get(47),
//                                    180, 140, 13,
//                                    173, 131, 11,
//                                    181, 140, 14,
//                                    173, 132, 12,
//                                    5, 7, 4,
//                                    3, 4, 3,
//                                    3, 3, 3,
//                                    6, 5, 5), rgbList.get(47)));
//                            break;
//                        case 48:
//                            list.add(new ImageDetect7240YellowTwo(split.get(48).bitmap, JudgePassOrFailed2(rgbList.get(48),
//                                    183, 138, 14,
//                                    177, 132, 11,
//                                    181, 137, 11,
//                                    178, 134, 12,
//                                    3, 4, 3,
//                                    4, 4, 4,
//                                    4, 4, 4,
//                                    4, 6, 4), rgbList.get(48)));
//                            break;
//                        case 49:
//                            list.add(new ImageDetect7240YellowTwo(split.get(49).bitmap, JudgePassOrFailed2(rgbList.get(49),
//                                    201, 162, 14,
//                                    192, 152, 12,
//                                    201, 161, 13,
//                                    192, 152, 11,
//                                    3, 3, 5,
//                                    4, 5, 4,
//                                    4, 4, 4,
//                                    3, 4, 3), rgbList.get(49)));
//                            break;
//                        case 50:
//                            list.add(new ImageDetect7240YellowTwo(split.get(50).bitmap, JudgePassOrFailed2(rgbList.get(50),
//                                    198, 155, 13,
//                                    191, 147, 9,
//                                    196, 154, 12,
//                                    191, 147, 14,
//                                    3, 3, 4,
//                                    5, 4, 4,
//                                    4, 3, 6,
//                                    3, 3, 3), rgbList.get(50)));
//                            break;
//                        case 51:
//                            list.add(new ImageDetect7240YellowTwo(split.get(51).bitmap, JudgePassOrFailed2(rgbList.get(51),
//                                    189, 147, 15,
//                                    183, 139, 12,
//                                    185, 144, 13,
//                                    180, 137, 12,
//                                    3, 4, 5,
//                                    3, 4, 3,
//                                    3, 4, 4,
//                                    3, 4, 3), rgbList.get(51)));
//                            break;
//                        case 52:
//                            list.add(new ImageDetect7240YellowTwo(split.get(52).bitmap, JudgePassOrFailed2(rgbList.get(52),
//                                    163, 124, 13,
//                                    159, 121, 12,
//                                    161, 119, 13,
//                                    158, 120, 13,
//                                    3, 3, 4,
//                                    5, 4, 4,
//                                    3, 4, 3,
//                                    4, 4, 3), rgbList.get(52)));
//                            break;
//                        case 53:
//                            list.add(new ImageDetect7240YellowTwo(split.get(53).bitmap, JudgePassOrFailed2(rgbList.get(53),
//                                    168, 125, 12,
//                                    170, 125, 9,
//                                    166, 123, 11,
//                                    167, 123, 9,
//                                    4, 4, 4,
//                                    5, 4, 3,
//                                    5, 5, 4,
//                                    6, 5, 4), rgbList.get(53)));
//                            break;
//                        case 54:
//                            list.add(new ImageDetect7240YellowTwo(split.get(54).bitmap, JudgePassOrFailed2(rgbList.get(54),
//                                    160, 112, 7,
//                                    156, 106, 5,
//                                    158, 111, 6,
//                                    157, 109, 7,
//                                    4, 3, 3,
//                                    5, 4, 3,
//                                    5, 5, 4,
//                                    4, 3, 3), rgbList.get(54)));
//                            break;
//                        case 55:
//                            list.add(new ImageDetect7240YellowTwo(split.get(55).bitmap, JudgePassOrFailed2(rgbList.get(55),
//                                    150, 107, 10,
//                                    148, 102, 8,
//                                    147, 104, 8,
//                                    145, 104, 7,
//                                    5, 6, 5,
//                                    3, 5, 3,
//                                    5, 6, 4,
//                                    5, 4, 3), rgbList.get(55)));
//                            break;
//                        case 56:
//                            list.add(new ImageDetect7240YellowTwo(split.get(56).bitmap, JudgePassOrFailed2(rgbList.get(56),
//                                    175, 127, 9,
//                                    178, 129, 11,
//                                    175, 126, 9,
//                                    176, 128, 9,
//                                    3, 3, 3,
//                                    4, 4, 3,
//                                    4, 4, 3,
//                                    4, 3, 3), rgbList.get(56)));
//                            break;
//                        case 57:
//                            list.add(new ImageDetect7240YellowTwo(split.get(57).bitmap, JudgePassOrFailed2(rgbList.get(57),
//                                    181, 138, 12,
//                                    179, 135, 11,
//                                    181, 138, 12,
//                                    181, 138, 11,
//                                    4, 5, 5,
//                                    4, 4, 5,
//                                    3, 5, 3,
//                                    4, 4, 3), rgbList.get(57)));
//                            break;
//                        case 58:
//                            list.add(new ImageDetect7240YellowTwo(split.get(58).bitmap, JudgePassOrFailed2(rgbList.get(58),
//                                    189, 145, 12,
//                                    188, 144, 6,
//                                    188, 144, 10,
//                                    191, 148, 11,
//                                    4, 4, 6,
//                                    4, 4, 4,
//                                    4, 4, 3,
//                                    3, 4, 3), rgbList.get(58)));
//                            break;
//                        case 59:
//                            list.add(new ImageDetect7240YellowTwo(split.get(59).bitmap, JudgePassOrFailed2(rgbList.get(59),
//                                    178, 136, 9,
//                                    179, 137, 8,
//                                    178, 136, 11,
//                                    178, 136, 9,
//                                    5, 4, 3,
//                                    3, 5, 3,
//                                    6, 4, 3,
//                                    4, 5, 4), rgbList.get(59)));
//                            break;
//                        case 60:
//                            list.add(new ImageDetect7240YellowTwo(split.get(60).bitmap, JudgePassOrFailed2(rgbList.get(60),
//                                    174, 133, 12,
//                                    176, 134, 11,
//                                    171, 128, 12,
//                                    172, 129, 10,
//                                    3, 4, 4,
//                                    5, 5, 3,
//                                    4, 4, 4,
//                                    4, 5, 3), rgbList.get(60)));
//                            break;
//                        case 61:
//                            list.add(new ImageDetect7240YellowTwo(split.get(61).bitmap, JudgePassOrFailed2(rgbList.get(61),
//                                    165, 123, 14,
//                                    168, 128, 12,
//                                    163, 122, 12,
//                                    168, 128, 15,
//                                    3, 3, 3,
//                                    4, 5, 4,
//                                    5, 5, 4,
//                                    4, 4, 4), rgbList.get(61)));
//                            break;
//                        case 62:
//                            list.add(new ImageDetect7240YellowTwo(split.get(62).bitmap, JudgePassOrFailed2(rgbList.get(62),
//                                    163, 120, 9,
//                                    169, 124, 9,
//                                    162, 116, 10,
//                                    166, 119, 8,
//                                    5, 5, 4,
//                                    3, 4, 4,
//                                    4, 3, 3,
//                                    4, 5, 4), rgbList.get(62)));
//                            break;
//                        case 63:
//                            list.add(new ImageDetect7240YellowTwo(split.get(63).bitmap, JudgePassOrFailed2(rgbList.get(63),
//                                    158, 110, 11,
//                                    156, 107, 7,
//                                    156, 108, 10,
//                                    157, 108, 9,
//                                    4, 4, 3,
//                                    4, 4, 3,
//                                    4, 4, 3,
//                                    3, 3, 3), rgbList.get(63)));
//                            break;
//                        case 64:
//                            list.add(new ImageDetect7240YellowTwo(split.get(64).bitmap, JudgePassOrFailed2(rgbList.get(64),
//                                    167, 122, 9,
//                                    168, 124, 9,
//                                    167, 122, 10,
//                                    169, 127, 11,
//                                    2, 5, 2,
//                                    3, 5, 2,
//                                    4, 6, 3,
//                                    4, 4, 4), rgbList.get(64)));
//                            break;
//                        case 65:
//                            list.add(new ImageDetect7240YellowTwo(split.get(65).bitmap, JudgePassOrFailed2(rgbList.get(65),
//                                    181, 132, 12,
//                                    179, 131, 5,
//                                    180, 131, 12,
//                                    182, 134, 10,
//                                    3, 4, 3,
//                                    3, 3, 3,
//                                    3, 6, 3,
//                                    4, 4, 4), rgbList.get(65)));
//                            break;
//                        case 66:
//                            list.add(new ImageDetect7240YellowTwo(split.get(66).bitmap, JudgePassOrFailed2(rgbList.get(66),
//                                    192, 148, 11,
//                                    192, 149, 10,
//                                    190, 147, 10,
//                                    194, 151, 12,
//                                    4, 5, 3,
//                                    3, 3, 3,
//                                    4, 4, 3,
//                                    4, 4, 4), rgbList.get(66)));
//                            break;
//                        case 67:
//                            list.add(new ImageDetect7240YellowTwo(split.get(67).bitmap, JudgePassOrFailed2(rgbList.get(67),
//                                    188, 144, 13,
//                                    194, 149, 8,
//                                    187, 145, 12,
//                                    193, 151, 9,
//                                    3, 5, 3,
//                                    4, 4, 3,
//                                    3, 3, 3,
//                                    3, 3, 3), rgbList.get(67)));
//                            break;
//                        case 68:
//                            list.add(new ImageDetect7240YellowTwo(split.get(68).bitmap, JudgePassOrFailed2(rgbList.get(68),
//                                    189, 145, 12,
//                                    196, 153, 10,
//                                    186, 143, 12,
//                                    194, 151, 8,
//                                    2, 4, 3,
//                                    3, 4, 4,
//                                    5, 5, 5,
//                                    3, 4, 3), rgbList.get(68)));
//                            break;
//                        case 69:
//                            list.add(new ImageDetect7240YellowTwo(split.get(69).bitmap, JudgePassOrFailed2(rgbList.get(69),
//                                    172, 131, 13,
//                                    179, 134, 9,
//                                    171, 128, 13,
//                                    176, 133, 11,
//                                    4, 3, 3,
//                                    3, 5, 3,
//                                    4, 4, 4,
//                                    5, 4, 3), rgbList.get(69)));
//                            break;
//                        case 70:
//                            list.add(new ImageDetect7240YellowTwo(split.get(70).bitmap, JudgePassOrFailed2(rgbList.get(70),
//                                    166, 124, 13,
//                                    173, 131, 12,
//                                    164, 122, 14,
//                                    173, 131, 13,
//                                    4, 5, 3,
//                                    4, 4, 4,
//                                    3, 5, 3,
//                                    4, 4, 4), rgbList.get(70)));
//                            break;
//                        case 71:
//                            list.add(new ImageDetect7240YellowTwo(split.get(71).bitmap, JudgePassOrFailed2(rgbList.get(71),
//                                    172, 124, 9,
//                                    175, 129, 7,
//                                    166, 120, 7,
//                                    172, 127, 9,
//                                    3, 4, 3,
//                                    3, 4, 3,
//                                    3, 4, 3,
//                                    3, 3, 3), rgbList.get(71)));
//                            break;
//                        case 72:
//                            list.add(new ImageDetect7240YellowTwo(split.get(72).bitmap, JudgePassOrFailed2(rgbList.get(72),
//                                    157, 109, 10,
//                                    156, 110, 5,
//                                    157, 109, 9,
//                                    156, 112, 7,
//                                    4, 3, 3,
//                                    3, 4, 3,
//                                    3, 3, 3,
//                                    3, 3, 3), rgbList.get(72)));
//                            break;
//                        case 73:
//                            list.add(new ImageDetect7240YellowTwo(split.get(73).bitmap, JudgePassOrFailed2(rgbList.get(73),
//                                    174, 129, 12,
//                                    172, 128, 7,
//                                    174, 129, 11,
//                                    173, 132, 9,
//                                    3, 3, 3,
//                                    3, 4, 3,
//                                    3, 3, 4,
//                                    4, 3, 5), rgbList.get(73)));
//                            break;
//                        case 74:
//                            list.add(new ImageDetect7240YellowTwo(split.get(74).bitmap, JudgePassOrFailed2(rgbList.get(74),
//                                    156, 114, 10,
//                                    167, 127, 14,
//                                    155, 113, 11,
//                                    167, 127, 14,
//                                    3, 4, 3,
//                                    4, 3, 3,
//                                    4, 4, 4,
//                                    5, 5, 4), rgbList.get(74)));
//                            break;
//                        case 75:
//                            list.add(new ImageDetect7240YellowTwo(split.get(75).bitmap, JudgePassOrFailed2(rgbList.get(75),
//                                    203, 159, 10,
//                                    206, 163, 8,
//                                    203, 160, 11,
//                                    206, 165, 9,
//                                    4, 3, 3,
//                                    4, 4, 3,
//                                    3, 3, 3,
//                                    3, 3, 3), rgbList.get(75)));
//                            break;
//                        case 76:
//                            list.add(new ImageDetect7240YellowTwo(split.get(76).bitmap, JudgePassOrFailed2(rgbList.get(76),
//                                    197, 155, 10,
//                                    201, 164, 12,
//                                    197, 156, 10,
//                                    201, 165, 10,
//                                    4, 4, 3,
//                                    4, 4, 3,
//                                    3, 3, 3,
//                                    3, 3, 3), rgbList.get(76)));
//                            break;
//                        case 77:
//                            list.add(new ImageDetect7240YellowTwo(split.get(77).bitmap, JudgePassOrFailed2(rgbList.get(77),
//                                    186, 144, 11,
//                                    194, 154, 9,
//                                    184, 141, 13,
//                                    195, 154, 13,
//                                    3, 4, 3,
//                                    3, 3, 3,
//                                    3, 3, 4,
//                                    3, 3, 3), rgbList.get(77)));
//                            break;
//                        case 78:
//                            list.add(new ImageDetect7240YellowTwo(split.get(78).bitmap, JudgePassOrFailed2(rgbList.get(78),
//                                    168, 127, 12,
//                                    182, 142, 10,
//                                    163, 121, 13,
//                                    180, 140, 15,
//                                    3, 4, 3,
//                                    4, 4, 4,
//                                    4, 4, 4,
//                                    4, 3, 3), rgbList.get(78)));
//                            break;
//                        case 79:
//                            list.add(new ImageDetect7240YellowTwo(split.get(79).bitmap, JudgePassOrFailed2(rgbList.get(79),
//                                    194, 154, 12,
//                                    202, 161, 12,
//                                    193, 154, 14,
//                                    199, 157, 12,
//                                    3, 3, 4,
//                                    3, 4, 2,
//                                    3, 3, 3,
//                                    3, 3, 3), rgbList.get(79)));
//                            break;
//                        case 80:
//                            list.add(new ImageDetect7240YellowTwo(split.get(80).bitmap, JudgePassOrFailed2(rgbList.get(80),
//                                    156, 114, 10,
//                                    166, 124, 9,
//                                    153, 111, 10,
//                                    162, 120, 12,
//                                    3, 3, 3,
//                                    5, 6, 5,
//                                    3, 4, 3,
//                                    3, 3, 3), rgbList.get(80)));
//                            break;
//                        case 81:
//                            list.add(new ImageDetect7240YellowTwo(split.get(81).bitmap, JudgePassOrFailed2(rgbList.get(81),
//                                    179, 132, 9,
//                                    185, 137, 10,
//                                    180, 133, 9,
//                                    182, 137, 7,
//                                    5, 4, 3,
//                                    4, 5, 3,
//                                    5, 4, 3,
//                                    5, 6, 3), rgbList.get(81)));
//                            break;
//                        case 82:
//                            list.add(new ImageDetect7240YellowTwo(split.get(82).bitmap, JudgePassOrFailed2(rgbList.get(82),
//                                    188, 142, 8,
//                                    192, 146, 8,
//                                    188, 143, 8,
//                                    193, 149, 11,
//                                    4, 4, 3,
//                                    3, 3, 3,
//                                    4, 3, 3,
//                                    4, 3, 3), rgbList.get(82)));
//                            break;
//                        case 83:
//                            list.add(new ImageDetect7240YellowTwo(split.get(83).bitmap, JudgePassOrFailed2(rgbList.get(83),
//                                    196, 154, 9,
//                                    203, 162, 11,
//                                    196, 154, 10,
//                                    204, 164, 13,
//                                    5, 5, 4,
//                                    4, 4, 3,
//                                    4, 4, 4,
//                                    5, 4, 3), rgbList.get(83)));
//                            break;
//                        case 84:
//                            list.add(new ImageDetect7240YellowTwo(split.get(84).bitmap, JudgePassOrFailed2(rgbList.get(84),
//                                    214, 175, 12,
//                                    216, 179, 11,
//                                    214, 176, 14,
//                                    218, 182, 14,
//                                    3, 3, 3,
//                                    4, 4, 3,
//                                    3, 3, 4,
//                                    3, 3, 3), rgbList.get(84)));
//                            break;
//                        case 85:
//                            list.add(new ImageDetect7240YellowTwo(split.get(85).bitmap, JudgePassOrFailed2(rgbList.get(85),
//                                    214, 177, 14,
//                                    223, 187, 11,
//                                    214, 177, 12,
//                                    221, 185, 12,
//                                    3, 3, 3,
//                                    3, 4, 3,
//                                    4, 4, 3,
//                                    4, 4, 4), rgbList.get(85)));
//                            break;
//                        case 86:
//                            list.add(new ImageDetect7240YellowTwo(split.get(86).bitmap, JudgePassOrFailed2(rgbList.get(86),
//                                    216, 183, 13,
//                                    223, 192, 13,
//                                    214, 180, 12,
//                                    222, 189, 13,
//                                    4, 5, 2,
//                                    3, 2, 3,
//                                    4, 4, 3,
//                                    2, 3, 3), rgbList.get(86)));
//                            break;
//                        case 87:
//                            list.add(new ImageDetect7240YellowTwo(split.get(87).bitmap, JudgePassOrFailed2(rgbList.get(87),
//                                    208, 168, 13,
//                                    215, 178, 11,
//                                    208, 167, 13,
//                                    214, 176, 13,
//                                    4, 5, 5,
//                                    4, 4, 4,
//                                    4, 4, 4,
//                                    4, 4, 4), rgbList.get(87)));
//                            break;
//                        case 88:
//                            list.add(new ImageDetect7240YellowTwo(split.get(88).bitmap, JudgePassOrFailed2(rgbList.get(88),
//                                    198, 160, 13,
//                                    207, 170, 14,
//                                    193, 154, 12,
//                                    202, 164, 12,
//                                    3, 3, 3,
//                                    4, 3, 3,
//                                    4, 5, 3,
//                                    5, 4, 3), rgbList.get(88)));
//                            break;
//                        case 89:
//                            list.add(new ImageDetect7240YellowTwo(split.get(89).bitmap, JudgePassOrFailed2(rgbList.get(89),
//                                    183, 145, 16,
//                                    192, 154, 14,
//                                    180, 141, 11,
//                                    188, 150, 10,
//                                    5, 6, 5,
//                                    4, 3, 3,
//                                    3, 3, 4,
//                                    5, 5, 3), rgbList.get(89)));
//                            break;
//
//
//                    }
                    switch (i) {
                        case 0:
                            list.add(new ImageDetect7240YellowTwo(split.get(0).bitmap, JudgePassOrFailed2(rgbList.get(0),
                                    175, 130, 7,
                                    169, 125, 7,
                                    172, 130, 9,
                                    168, 124, 7), rgbList.get(0)));
                            break;
                        case 1:
                            list.add(new ImageDetect7240YellowTwo(split.get(1).bitmap, JudgePassOrFailed2(rgbList.get(1),
                                    205, 166, 9,
                                    195, 155, 6,
                                    209, 171, 10,
                                    197, 157, 7), rgbList.get(1)));
                            break;
                        case 2:
                            list.add(new ImageDetect7240YellowTwo(split.get(2).bitmap, JudgePassOrFailed2(rgbList.get(2),
                                    217, 178, 8,
                                    211, 169, 10,
                                    216, 178, 10,
                                    210, 170, 9), rgbList.get(2)));
                            break;
                        case 3:
                            list.add(new ImageDetect7240YellowTwo(split.get(3).bitmap, JudgePassOrFailed2(rgbList.get(3),
                                    223, 192, 12,
                                    217, 184, 12,
                                    222, 192, 11,
                                    217, 186, 14), rgbList.get(3)));
                            break;
                        case 4:
                            list.add(new ImageDetect7240YellowTwo(split.get(4).bitmap, JudgePassOrFailed2(rgbList.get(4),
                                    221, 188, 13,
                                    219, 186, 13,
                                    219, 187, 13,
                                    220, 187, 13), rgbList.get(4)));
                            break;
                        case 5:
                            list.add(new ImageDetect7240YellowTwo(split.get(5).bitmap, JudgePassOrFailed2(rgbList.get(5),
                                    220, 187, 14,
                                    219, 185, 13,
                                    217, 184, 11,
                                    218, 185, 13), rgbList.get(5)));
                            break;
                        case 6:
                            list.add(new ImageDetect7240YellowTwo(split.get(6).bitmap, JudgePassOrFailed2(rgbList.get(6),
                                    214, 176, 11,
                                    219, 180, 13,
                                    214, 176, 12,
                                    218, 179, 12), rgbList.get(6)));
                            break;
                        case 7:
                            list.add(new ImageDetect7240YellowTwo(split.get(7).bitmap, JudgePassOrFailed2(rgbList.get(7),
                                    210, 171, 12,
                                    207, 168, 11,
                                    208, 168, 12,
                                    205, 167, 10), rgbList.get(7)));
                            break;
                        case 8:
                            list.add(new ImageDetect7240YellowTwo(split.get(8).bitmap, JudgePassOrFailed2(rgbList.get(8),
                                    201, 160, 10,
                                    199, 156, 8,
                                    201, 160, 10,
                                    197, 153, 7), rgbList.get(8)));
                            break;
                        case 9:
                            list.add(new ImageDetect7240YellowTwo(split.get(9).bitmap, JudgePassOrFailed2(rgbList.get(9),
                                    171, 126, 7,
                                    172, 129, 9,
                                    169, 126, 9,
                                    169, 127, 9), rgbList.get(9)));
                            break;
                        case 10:
                            list.add(new ImageDetect7240YellowTwo(split.get(10).bitmap, JudgePassOrFailed2(rgbList.get(10),
                                    194, 151, 10,
                                    193, 149, 10,
                                    198, 156, 12,
                                    195, 153, 9), rgbList.get(10)));
                            break;
                        case 11:
                            list.add(new ImageDetect7240YellowTwo(split.get(11).bitmap, JudgePassOrFailed2(rgbList.get(11),
                                    211, 171, 9,
                                    220, 183, 11,
                                    211, 171, 7,
                                    222, 185, 9), rgbList.get(11)));
                            break;
                        case 12:
                            list.add(new ImageDetect7240YellowTwo(split.get(12).bitmap, JudgePassOrFailed2(rgbList.get(12),
                                    219, 183, 11,
                                    228, 192, 16,
                                    222, 186, 11,
                                    230, 194, 15), rgbList.get(12)));
                            break;
                        case 13:
                            list.add(new ImageDetect7240YellowTwo(split.get(13).bitmap, JudgePassOrFailed2(rgbList.get(13),
                                    233, 201, 17,
                                    251, 219, 16,
                                    232, 201, 15,
                                    253, 222, 19), rgbList.get(13)));
                            break;
                        case 14:
                            list.add(new ImageDetect7240YellowTwo(split.get(14).bitmap, JudgePassOrFailed2(rgbList.get(14),
                                    227, 192, 12,
                                    240, 207, 16,
                                    227, 194, 12,
                                    239, 208, 14), rgbList.get(14)));
                            break;
                        case 15:
                            list.add(new ImageDetect7240YellowTwo(split.get(15).bitmap, JudgePassOrFailed2(rgbList.get(15),
                                    227, 196, 16,
                                    242, 212, 20,
                                    227, 195, 15,
                                    240, 210, 24), rgbList.get(15)));
                            break;
                        case 16:
                            list.add(new ImageDetect7240YellowTwo(split.get(16).bitmap, JudgePassOrFailed2(rgbList.get(16),
                                    214, 176, 13,
                                    221, 186, 20,
                                    210, 173, 12,
                                    216, 178, 15), rgbList.get(16)));
                            break;
                        case 17:
                            list.add(new ImageDetect7240YellowTwo(split.get(17).bitmap, JudgePassOrFailed2(rgbList.get(17),
                                    191, 148, 9,
                                    190, 146, 8,
                                    189, 144, 9,
                                    186, 142, 7), rgbList.get(17)));
                            break;
                        case 18:
                            list.add(new ImageDetect7240YellowTwo(split.get(18).bitmap, JudgePassOrFailed2(rgbList.get(18),
                                    179, 140, 10,
                                    184, 146, 10,
                                    175, 137, 11,
                                    181, 143, 11), rgbList.get(18)));
                            break;
//                            ,,,,
                        case 19:
                            list.add(new ImageDetect7240YellowTwo(split.get(19).bitmap, JudgePassOrFailed2(rgbList.get(19),
                                    192, 149, 10,
                                    194, 150, 10,
                                    197, 154, 13,
                                    199, 157, 10), rgbList.get(19)));
                            break;
                        case 20:
                            list.add(new ImageDetect7240YellowTwo(split.get(20).bitmap, JudgePassOrFailed2(rgbList.get(20),
                                    228, 193, 16,
                                    232, 197, 17,
                                    233, 200, 15,
                                    238, 205, 20), rgbList.get(20)));
                            break;
                        case 21:
                            list.add(new ImageDetect7240YellowTwo(split.get(21).bitmap, JudgePassOrFailed2(rgbList.get(21),
                                    243, 213, 22,
                                    246, 216, 27,
                                    246, 215, 22,
                                    251, 220, 29), rgbList.get(21)));
                            break;
                        case 22:
                            list.add(new ImageDetect7240YellowTwo(split.get(22).bitmap, JudgePassOrFailed2(rgbList.get(22),
                                    252, 224, 18,
                                    254, 227, 24,
                                    254, 230, 20,
                                    253, 228, 18), rgbList.get(22)));
                            break;
                        case 23:
                            list.add(new ImageDetect7240YellowTwo(split.get(23).bitmap, JudgePassOrFailed2(rgbList.get(23),
                                    253, 224, 21,
                                    253, 224, 20,
                                    253, 225, 22,
                                    253, 225, 21), rgbList.get(23)));
                            break;
                        case 24:
                            list.add(new ImageDetect7240YellowTwo(split.get(24).bitmap, JudgePassOrFailed2(rgbList.get(24),
                                    250, 218, 22,
                                    251, 219, 22,
                                    251, 219, 26,
                                    251, 219, 22), rgbList.get(24)));
                            break;
                        case 25:
                            list.add(new ImageDetect7240YellowTwo(split.get(25).bitmap, JudgePassOrFailed2(rgbList.get(25),
                                    229, 194, 14,
                                    221, 187, 14,
                                    220, 185, 14,
                                    212, 176, 12), rgbList.get(25)));
                            break;
                        case 26:
                            list.add(new ImageDetect7240YellowTwo(split.get(26).bitmap, JudgePassOrFailed2(rgbList.get(26),
                                    186, 141, 10,
                                    184, 139, 9,
                                    182, 137, 9,
                                    180, 135, 9), rgbList.get(26)));
                            break;
                        case 27:
                            list.add(new ImageDetect7240YellowTwo(split.get(27).bitmap, JudgePassOrFailed2(rgbList.get(27),
                                    184, 140, 11,
                                    184, 139, 10,
                                    184, 139, 11,
                                    184, 140, 13), rgbList.get(27)));
                            break;
//                            ,,,
                        case 28:
                            list.add(new ImageDetect7240YellowTwo(split.get(28).bitmap, JudgePassOrFailed2(rgbList.get(28),
                                    195, 151, 11,
                                    191, 147, 10,
                                    198, 154, 12,
                                    193, 149, 10), rgbList.get(28)));
                            break;
//                            ,,,
                        case 29:
                            list.add(new ImageDetect7240YellowTwo(split.get(29).bitmap, JudgePassOrFailed2(rgbList.get(29),
                                    229, 193, 16,
                                    221, 185, 15,
                                    232, 197, 17,
                                    227, 193, 14), rgbList.get(29)));
                            break;
//                            ,,,
                        case 30:
                            list.add(new ImageDetect7240YellowTwo(split.get(30).bitmap, JudgePassOrFailed2(rgbList.get(30),
                                    250, 219, 21,
                                    239, 207, 16,
                                    252, 223, 22,
                                    243, 212, 16), rgbList.get(30)));
                            break;
                        case 31:
                            list.add(new ImageDetect7240YellowTwo(split.get(31).bitmap, JudgePassOrFailed2(rgbList.get(31),
                                    247, 213, 18,
                                    236, 201, 15,
                                    247, 214, 21,
                                    237, 205, 17), rgbList.get(31)));
                            break;
                        case 32:
                            list.add(new ImageDetect7240YellowTwo(split.get(32).bitmap, JudgePassOrFailed2(rgbList.get(32),
                                    244, 213, 20,
                                    230, 201, 14,
                                    243, 213, 22,
                                    230, 200, 16), rgbList.get(32)));
                            break;
                        case 33:
                            list.add(new ImageDetect7240YellowTwo(split.get(33).bitmap, JudgePassOrFailed2(rgbList.get(33),
                                    230, 196, 19,
                                    219, 183, 16,
                                    229, 195, 19,
                                    218, 182, 16), rgbList.get(33)));
                            break;
//                            ,,,,
                        case 34:
                            list.add(new ImageDetect7240YellowTwo(split.get(34).bitmap, JudgePassOrFailed(rgbList.get(34),
                                    212, 176, 13,
                                    195, 157, 9,
                                    206, 168, 13,
                                    191, 152, 11,20,20,20,10,10,10,10,10,10,10,10,10), rgbList.get(34)));
                            break;
                        case 35:
                            list.add(new ImageDetect7240YellowTwo(split.get(35).bitmap, JudgePassOrFailed2(rgbList.get(35),
                                    175, 132, 12,
                                    170, 128, 9,
                                    169, 126, 10,
                                    168, 126, 12), rgbList.get(35)));
                            break;
                        case 36:
                            list.add(new ImageDetect7240YellowTwo(split.get(36).bitmap, JudgePassOrFailed2(rgbList.get(36),
                                    171, 128, 10,
                                    164, 119, 9,
                                    171, 126, 11,
                                    165, 121, 12), rgbList.get(36)));
                            break;
                        case 37:
                            list.add(new ImageDetect7240YellowTwo(split.get(37).bitmap, JudgePassOrFailed2(rgbList.get(37),
                                    187, 141, 9,
                                    182, 135, 8,
                                    188, 141, 10,
                                    182, 136, 10), rgbList.get(37)));
                            break;
//                            ,,,
                        case 38:
                            list.add(new ImageDetect7240YellowTwo(split.get(38).bitmap, JudgePassOrFailed2(rgbList.get(38),
                                    205, 162, 12,
                                    192, 148, 10,
                                    206, 164, 11,
                                    195, 152, 11), rgbList.get(38)));
                            break;
//                            ,,,
                        case 39:
                            list.add(new ImageDetect7240YellowTwo(split.get(39).bitmap, JudgePassOrFailed2(rgbList.get(39),
                                    208, 167, 14,
                                    194, 150, 10,
                                    211, 170, 16,
                                    198, 154, 12), rgbList.get(39)));
                            break;
                        case 40:
                            list.add(new ImageDetect7240YellowTwo(split.get(40).bitmap, JudgePassOrFailed2(rgbList.get(40),
                                    216, 182, 18,
                                    203, 164, 14,
                                    213, 178, 14,
                                    204, 165, 17), rgbList.get(40)));
                            break;
                        case 41:
                            list.add(new ImageDetect7240YellowTwo(split.get(41).bitmap, JudgePassOrFailed2(rgbList.get(41),
                                    211, 171, 8,
                                    201, 158, 10,
                                    209, 171, 10,
                                    198, 155, 8), rgbList.get(41)));
                            break;
                        case 42:
                            list.add(new ImageDetect7240YellowTwo(split.get(42).bitmap, JudgePassOrFailed2(rgbList.get(42),
                                    201, 161, 13,
                                    192, 149, 12,
                                    199, 159, 14,
                                    190, 149, 13), rgbList.get(42)));
                            break;
                        case 43:
                            list.add(new ImageDetect7240YellowTwo(split.get(43).bitmap, JudgePassOrFailed2(rgbList.get(43),
                                    178, 138, 15,
                                    168, 126, 11,
                                    171, 133, 13,
                                    165, 121, 11), rgbList.get(43)));
                            break;
                        case 44:
                            list.add(new ImageDetect7240YellowTwo(split.get(44).bitmap, JudgePassOrFailed2(rgbList.get(44),
                                    163, 119, 12,
                                    161, 115, 11,
                                    161, 117, 12,
                                    157, 114, 9), rgbList.get(44)));
                            break;
                        case 45:
                            list.add(new ImageDetect7240YellowTwo(split.get(45).bitmap, JudgePassOrFailed2(rgbList.get(45),
                                    164, 119, 9,
                                    160, 116, 8,
                                    163, 118, 9,
                                    162, 117, 10), rgbList.get(45)));
                            break;
                        case 46:
                            list.add(new ImageDetect7240YellowTwo(split.get(46).bitmap, JudgePassOrFailed2(rgbList.get(46),
                                    160, 118, 8,
                                    154, 112, 8,
                                    161, 119, 11,
                                    157, 116, 10), rgbList.get(46)));
                            break;
                        case 47:
                            list.add(new ImageDetect7240YellowTwo(split.get(47).bitmap, JudgePassOrFailed2(rgbList.get(47),
                                    180, 140, 13,
                                    173, 131, 11,
                                    181, 140, 14,
                                    173, 132, 12), rgbList.get(47)));
                            break;
                        case 48:
                            list.add(new ImageDetect7240YellowTwo(split.get(48).bitmap, JudgePassOrFailed2(rgbList.get(48),
                                    183, 138, 14,
                                    177, 132, 11,
                                    181, 137, 11,
                                    178, 134, 12), rgbList.get(48)));
                            break;
                        case 49:
                            list.add(new ImageDetect7240YellowTwo(split.get(49).bitmap, JudgePassOrFailed2(rgbList.get(49),
                                    201, 162, 14,
                                    192, 152, 12,
                                    201, 161, 13,
                                    192, 152, 11), rgbList.get(49)));
                            break;
                        case 50:
                            list.add(new ImageDetect7240YellowTwo(split.get(50).bitmap, JudgePassOrFailed2(rgbList.get(50),
                                    198, 155, 13,
                                    191, 147, 9,
                                    196, 154, 12,
                                    191, 147, 14), rgbList.get(50)));
                            break;
                        case 51:
                            list.add(new ImageDetect7240YellowTwo(split.get(51).bitmap, JudgePassOrFailed2(rgbList.get(51),
                                    189, 147, 15,
                                    183, 139, 12,
                                    185, 144, 13,
                                    180, 137, 12), rgbList.get(51)));
                            break;
                        case 52:
                            list.add(new ImageDetect7240YellowTwo(split.get(52).bitmap, JudgePassOrFailed2(rgbList.get(52),
                                    163, 124, 13,
                                    159, 121, 12,
                                    161, 119, 13,
                                    158, 120, 13), rgbList.get(52)));
                            break;
                        case 53:
                            list.add(new ImageDetect7240YellowTwo(split.get(53).bitmap, JudgePassOrFailed2(rgbList.get(53),
                                    168, 125, 12,
                                    170, 125, 9,
                                    166, 123, 11,
                                    167, 123, 9), rgbList.get(53)));
                            break;
                        case 54:
                            list.add(new ImageDetect7240YellowTwo(split.get(54).bitmap, JudgePassOrFailed2(rgbList.get(54),
                                    160, 112, 7,
                                    156, 106, 5,
                                    158, 111, 6,
                                    157, 109, 7), rgbList.get(54)));
                            break;
                        case 55:
                            list.add(new ImageDetect7240YellowTwo(split.get(55).bitmap, JudgePassOrFailed2(rgbList.get(55),
                                    150, 107, 10,
                                    148, 102, 8,
                                    147, 104, 8,
                                    145, 104, 7), rgbList.get(55)));
                            break;
                        case 56:
                            list.add(new ImageDetect7240YellowTwo(split.get(56).bitmap, JudgePassOrFailed2(rgbList.get(56),
                                    175, 127, 9,
                                    178, 129, 11,
                                    175, 126, 9,
                                    176, 128, 9), rgbList.get(56)));
                            break;
                        case 57:
                            list.add(new ImageDetect7240YellowTwo(split.get(57).bitmap, JudgePassOrFailed2(rgbList.get(57),
                                    181, 138, 12,
                                    179, 135, 11,
                                    181, 138, 12,
                                    181, 138, 11), rgbList.get(57)));
                            break;
                        case 58:
                            list.add(new ImageDetect7240YellowTwo(split.get(58).bitmap, JudgePassOrFailed2(rgbList.get(58),
                                    189, 145, 12,
                                    188, 144, 6,
                                    188, 144, 10,
                                    191, 148, 11), rgbList.get(58)));
                            break;
                        case 59:
                            list.add(new ImageDetect7240YellowTwo(split.get(59).bitmap, JudgePassOrFailed2(rgbList.get(59),
                                    178, 136, 9,
                                    179, 137, 8,
                                    178, 136, 11,
                                    178, 136, 9), rgbList.get(59)));
                            break;
                        case 60:
                            list.add(new ImageDetect7240YellowTwo(split.get(60).bitmap, JudgePassOrFailed2(rgbList.get(60),
                                    174, 133, 12,
                                    176, 134, 11,
                                    171, 128, 12,
                                    172, 129, 10), rgbList.get(60)));
                            break;
                        case 61:
                            list.add(new ImageDetect7240YellowTwo(split.get(61).bitmap, JudgePassOrFailed2(rgbList.get(61),
                                    165, 123, 14,
                                    168, 128, 12,
                                    163, 122, 12,
                                    168, 128, 15), rgbList.get(61)));
                            break;
                        case 62:
                            list.add(new ImageDetect7240YellowTwo(split.get(62).bitmap, JudgePassOrFailed2(rgbList.get(62),
                                    163, 120, 9,
                                    169, 124, 9,
                                    162, 116, 10,
                                    166, 119, 8), rgbList.get(62)));
                            break;
                        case 63:
                            list.add(new ImageDetect7240YellowTwo(split.get(63).bitmap, JudgePassOrFailed2(rgbList.get(63),
                                    158, 110, 11,
                                    156, 107, 7,
                                    156, 108, 10,
                                    157, 108, 9), rgbList.get(63)));
                            break;
                        case 64:
                            list.add(new ImageDetect7240YellowTwo(split.get(64).bitmap, JudgePassOrFailed2(rgbList.get(64),
                                    167, 122, 9,
                                    168, 124, 9,
                                    167, 122, 10,
                                    169, 127, 11), rgbList.get(64)));
                            break;
                        case 65:
                            list.add(new ImageDetect7240YellowTwo(split.get(65).bitmap, JudgePassOrFailed2(rgbList.get(65),
                                    181, 132, 12,
                                    179, 131, 5,
                                    180, 131, 12,
                                    182, 134, 10), rgbList.get(65)));
                            break;
                        case 66:
                            list.add(new ImageDetect7240YellowTwo(split.get(66).bitmap, JudgePassOrFailed2(rgbList.get(66),
                                    192, 148, 11,
                                    192, 149, 10,
                                    190, 147, 10,
                                    194, 151, 12), rgbList.get(66)));
                            break;
                        case 67:
                            list.add(new ImageDetect7240YellowTwo(split.get(67).bitmap, JudgePassOrFailed2(rgbList.get(67),
                                    188, 144, 13,
                                    194, 149, 8,
                                    187, 145, 12,
                                    193, 151, 9), rgbList.get(67)));
                            break;
                        case 68:
                            list.add(new ImageDetect7240YellowTwo(split.get(68).bitmap, JudgePassOrFailed2(rgbList.get(68),
                                    189, 145, 12,
                                    196, 153, 10,
                                    186, 143, 12,
                                    194, 151, 8), rgbList.get(68)));
                            break;
                        case 69:
                            list.add(new ImageDetect7240YellowTwo(split.get(69).bitmap, JudgePassOrFailed2(rgbList.get(69),
                                    172, 131, 13,
                                    179, 134, 9,
                                    171, 128, 13,
                                    176, 133, 11), rgbList.get(69)));
                            break;
                        case 70:
                            list.add(new ImageDetect7240YellowTwo(split.get(70).bitmap, JudgePassOrFailed2(rgbList.get(70),
                                    166, 124, 13,
                                    173, 131, 12,
                                    164, 122, 14,
                                    173, 131, 13), rgbList.get(70)));
                            break;
                        case 71:
                            list.add(new ImageDetect7240YellowTwo(split.get(71).bitmap, JudgePassOrFailed2(rgbList.get(71),
                                    172, 124, 9,
                                    175, 129, 7,
                                    166, 120, 7,
                                    172, 127, 9), rgbList.get(71)));
                            break;
                        case 72:
                            list.add(new ImageDetect7240YellowTwo(split.get(72).bitmap, JudgePassOrFailed2(rgbList.get(72),
                                    157, 109, 10,
                                    156, 110, 5,
                                    157, 109, 9,
                                    156, 112, 7), rgbList.get(72)));
                            break;
                        case 73:
                            list.add(new ImageDetect7240YellowTwo(split.get(73).bitmap, JudgePassOrFailed2(rgbList.get(73),
                                    174, 129, 12,
                                    172, 128, 7,
                                    174, 129, 11,
                                    173, 132, 9), rgbList.get(73)));
                            break;
                        case 74:
                            list.add(new ImageDetect7240YellowTwo(split.get(74).bitmap, JudgePassOrFailed2(rgbList.get(74),
                                    156, 114, 10,
                                    167, 127, 14,
                                    155, 113, 11,
                                    167, 127, 14), rgbList.get(74)));
                            break;
                        case 75:
                            list.add(new ImageDetect7240YellowTwo(split.get(75).bitmap, JudgePassOrFailed2(rgbList.get(75),
                                    203, 159, 10,
                                    206, 163, 8,
                                    203, 160, 11,
                                    206, 165, 9), rgbList.get(75)));
                            break;
                        case 76:
                            list.add(new ImageDetect7240YellowTwo(split.get(76).bitmap, JudgePassOrFailed2(rgbList.get(76),
                                    197, 155, 10,
                                    201, 164, 12,
                                    197, 156, 10,
                                    201, 165, 10), rgbList.get(76)));
                            break;
                        case 77:
                            list.add(new ImageDetect7240YellowTwo(split.get(77).bitmap, JudgePassOrFailed2(rgbList.get(77),
                                    186, 144, 11,
                                    194, 154, 9,
                                    184, 141, 13,
                                    195, 154, 13), rgbList.get(77)));
                            break;
                        case 78:
                            list.add(new ImageDetect7240YellowTwo(split.get(78).bitmap, JudgePassOrFailed2(rgbList.get(78),
                                    168, 127, 12,
                                    182, 142, 10,
                                    163, 121, 13,
                                    180, 140, 15), rgbList.get(78)));
                            break;
                        case 79:
                            list.add(new ImageDetect7240YellowTwo(split.get(79).bitmap, JudgePassOrFailed2(rgbList.get(79),
                                    194, 154, 12,
                                    202, 161, 12,
                                    193, 154, 14,
                                    199, 157, 12), rgbList.get(79)));
                            break;
                        case 80:
                            list.add(new ImageDetect7240YellowTwo(split.get(80).bitmap, JudgePassOrFailed2(rgbList.get(80),
                                    156, 114, 10,
                                    166, 124, 9,
                                    153, 111, 10,
                                    162, 120, 12), rgbList.get(80)));
                            break;
                        case 81:
                            list.add(new ImageDetect7240YellowTwo(split.get(81).bitmap, JudgePassOrFailed2(rgbList.get(81),
                                    179, 132, 9,
                                    185, 137, 10,
                                    180, 133, 9,
                                    182, 137, 7), rgbList.get(81)));
                            break;
                        case 82:
                            list.add(new ImageDetect7240YellowTwo(split.get(82).bitmap, JudgePassOrFailed2(rgbList.get(82),
                                    188, 142, 8,
                                    192, 146, 8,
                                    188, 143, 8,
                                    193, 149, 11), rgbList.get(82)));
                            break;
                        case 83:
                            list.add(new ImageDetect7240YellowTwo(split.get(83).bitmap, JudgePassOrFailed2(rgbList.get(83),
                                    196, 154, 9,
                                    203, 162, 11,
                                    196, 154, 10,
                                    204, 164, 13), rgbList.get(83)));
                            break;
                        case 84:
                            list.add(new ImageDetect7240YellowTwo(split.get(84).bitmap, JudgePassOrFailed2(rgbList.get(84),
                                    214, 175, 12,
                                    216, 179, 11,
                                    214, 176, 14,
                                    218, 182, 14), rgbList.get(84)));
                            break;
                        case 85:
                            list.add(new ImageDetect7240YellowTwo(split.get(85).bitmap, JudgePassOrFailed2(rgbList.get(85),
                                    214, 177, 14,
                                    223, 187, 11,
                                    214, 177, 12,
                                    221, 185, 12), rgbList.get(85)));
                            break;
                        case 86:
                            list.add(new ImageDetect7240YellowTwo(split.get(86).bitmap, JudgePassOrFailed2(rgbList.get(86),
                                    216, 183, 13,
                                    223, 192, 13,
                                    214, 180, 12,
                                    222, 189, 13), rgbList.get(86)));
                            break;
                        case 87:
                            list.add(new ImageDetect7240YellowTwo(split.get(87).bitmap, JudgePassOrFailed2(rgbList.get(87),
                                    208, 168, 13,
                                    215, 178, 11,
                                    208, 167, 13,
                                    214, 176, 13), rgbList.get(87)));
                            break;
                        case 88:
                            list.add(new ImageDetect7240YellowTwo(split.get(88).bitmap, JudgePassOrFailed2(rgbList.get(88),
                                    198, 160, 13,
                                    207, 170, 14,
                                    193, 154, 12,
                                    202, 164, 12), rgbList.get(88)));
                            break;
                        case 89:
                            list.add(new ImageDetect7240YellowTwo(split.get(89).bitmap, JudgePassOrFailed2(rgbList.get(89),
                                    183, 145, 16,
                                    192, 154, 14,
                                    180, 141, 11,
                                    188, 150, 10), rgbList.get(89)));
                            break;


                    }
//                    list.add(new ImageDetect7240YellowTwo(split.get(i).bitmap, judgePass(rgbList.get(i)), mmm));

                }
            }

            adapter = new ImageAdapterfor7240YellowTwo(list, this);
            gv.setNumColumns(9);
            gv.setAdapter(adapter);


        }

    }

    /**
     * 如果读取的颜色范围大，会不会改变的因素就越大呢
     * @param bitmap
     * @return
     */
    private List<String> getColorFromAdapter(Bitmap bitmap) {
        int width_difference = bitmap.getWidth() / 7;
        int height_difference = bitmap.getHeight() / 4;
        List<Bitmap> bitmapList = new ArrayList<>();
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap, width_difference - 10, height_difference, 10, 10);
        Bitmap bitmap2 = Bitmap.createBitmap(bitmap, width_difference - 10, height_difference * 3, 10, 10);
        Bitmap bitmap3 = Bitmap.createBitmap(bitmap, width_difference * 2 - 10, height_difference, 10, 10);
        Bitmap bitmap4 = Bitmap.createBitmap(bitmap, width_difference * 2 - 10, height_difference * 3, 10, 10);
        bitmapList.add(bitmap1);
        bitmapList.add(bitmap2);
        bitmapList.add(bitmap3);
        bitmapList.add(bitmap4);

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

    private boolean JudgePassOrFailed2(RGBMULTI7240YellowTwo RgbMulti,
                                      int R1Standard, int G1Standard, int B1Standard,
                                      int R2Standard, int G2Standard, int B2Standard,
                                      int R3Standard, int G3Standard, int B3Standard,
                                      int R4Standard, int G4Standard, int B4Standard) {

        boolean pass;

//        TODO 测量出标准值   有待修改
        boolean b1 = (RgbMulti.getR1() - R1Standard >= -8 && RgbMulti.getR1() - R1Standard <= 8)
                  && (RgbMulti.getG1() - G1Standard >= -8 && RgbMulti.getG1() - G1Standard <= 8)
                  && (RgbMulti.getB1() - B1Standard >= -8 && RgbMulti.getB1() - B1Standard <= 8);

        boolean b2 = (RgbMulti.getR2() - R2Standard >= -8 && RgbMulti.getR2() - R2Standard <= 8)
                  && (RgbMulti.getG2() - G2Standard >= -8 && RgbMulti.getG2() - G2Standard <= 8)
                  && (RgbMulti.getB2() - B2Standard >= -8 && RgbMulti.getB2() - B2Standard <= 8);

        boolean b3 = (RgbMulti.getR3() - R3Standard >= -8 && RgbMulti.getR3() - R3Standard <= 8)
                  && (RgbMulti.getG3() - G3Standard >= -8 && RgbMulti.getG3() - G3Standard <= 8)
                  && (RgbMulti.getB3() - B3Standard >= -8 && RgbMulti.getB3() - B3Standard <= 8);

        boolean b4 = (RgbMulti.getR4() - R4Standard >= -8 && RgbMulti.getR4() - R4Standard <= 8)
                  && (RgbMulti.getG4() - G4Standard >= -8 && RgbMulti.getG4() - G4Standard <= 8)
                  && (RgbMulti.getB4() - B4Standard >= -8 && RgbMulti.getB4() - B4Standard <= 8);


        if (b1) {
            pass = true;
        } else if (b2) {
            pass = true;
        } else if (b3) {
            pass = true;
        } else pass = b4;
        return pass;


//        boolean b1 = (RgbMulti.getR1() - R1Standard >= -R1Difference && RgbMulti.getR1() - R1Standard <= R1Difference)
//                && (RgbMulti.getG1() - G1Standard >= -G1Difference && RgbMulti.getG1() - G1Standard <= G1Difference)
//                && (RgbMulti.getB1() - B1Standard >= -B1Difference && RgbMulti.getB1() - B1Standard <= B1Difference);
//
//        boolean b2 = (RgbMulti.getR2() - R2Standard >= -R2Difference && RgbMulti.getR2() - R2Standard <= R2Difference)
//                && (RgbMulti.getG2() - G2Standard >= -G2Difference && RgbMulti.getG2() - G2Standard <= G2Difference)
//                && (RgbMulti.getB2() - B2Standard >= -B2Difference && RgbMulti.getB2() - B2Standard <= B2Difference);
//
//        boolean b3 = (RgbMulti.getR3() - R3Standard >= -R3Difference && RgbMulti.getR3() - R3Standard <= R3Difference)
//                && (RgbMulti.getG3() - G3Standard >= -G3Difference && RgbMulti.getG3() - G3Standard <= G3Difference)
//                && (RgbMulti.getB3() - B3Standard >= -B3Difference && RgbMulti.getB3() - B3Standard <= B3Difference);
//
//        boolean b4 = (RgbMulti.getR4() - R4Standard >= -R4Difference && RgbMulti.getR4() - R4Standard <= R4Difference)
//                && (RgbMulti.getG4() - G4Standard >= -G4Difference && RgbMulti.getG4() - G4Standard <= G4Difference)
//                && (RgbMulti.getB4() - B4Standard >= -B4Difference && RgbMulti.getB4() - B4Standard <= B4Difference);
//
//
//        if (b1 && b2) {
//            pass = true;
//        } else if (b1 && b3) {
//            pass = true;
//        } else if (b1 && b4) {
//            pass = true;
//        } else if (b2 && b3) {
//            pass = true;
//        } else pass = b2 && b4;
//        return pass;
    }


    /**
     * 每个位置的颜色标准值都不一样，建立不同的标准
     *
     * @param RgbMulti
     * @param R1Standard
     * @param G1Standard
     * @param B1Standard
     * @param R2Standard
     * @param G2Standard
     * @param B2Standard
     * @param R3Standard
     * @param G3Standard
     * @param B3Standard
     * @param R4Standard
     * @param G4Standard
     * @param B4Standard
     * @param R1Difference
     * @param G1Difference
     * @param B1Difference
     * @param R2Difference
     * @param G2Difference
     * @param B2Difference
     * @param R3Difference
     * @param G3Difference
     * @param B3Difference
     * @param R4Difference
     * @param G4Difference
     * @param B4Difference
     * @return
     */
    private boolean JudgePassOrFailed(RGBMULTI7240YellowTwo RgbMulti,
                                      int R1Standard, int G1Standard, int B1Standard,
                                      int R2Standard, int G2Standard, int B2Standard,
                                      int R3Standard, int G3Standard, int B3Standard,
                                      int R4Standard, int G4Standard, int B4Standard,
                                      int R1Difference, int G1Difference, int B1Difference,
                                      int R2Difference, int G2Difference, int B2Difference,
                                      int R3Difference, int G3Difference, int B3Difference,
                                      int R4Difference, int G4Difference, int B4Difference) {

        boolean pass;

//        TODO 测量出标准值   有待修改
        boolean b1 = (RgbMulti.getR1() - R1Standard >= -R1Difference && RgbMulti.getR1() - R1Standard <= R1Difference)
                && (RgbMulti.getG1() - G1Standard >= -G1Difference && RgbMulti.getG1() - G1Standard <= G1Difference)
                && (RgbMulti.getB1() - B1Standard >= -B1Difference && RgbMulti.getB1() - B1Standard <= B1Difference);

        boolean b2 = (RgbMulti.getR2() - R2Standard >= -R2Difference && RgbMulti.getR2() - R2Standard <= R2Difference)
                && (RgbMulti.getG2() - G2Standard >= -G2Difference && RgbMulti.getG2() - G2Standard <= G2Difference)
                && (RgbMulti.getB2() - B2Standard >= -B2Difference && RgbMulti.getB2() - B2Standard <= B2Difference);

        boolean b3 = (RgbMulti.getR3() - R3Standard >= -R3Difference && RgbMulti.getR3() - R3Standard <= R3Difference)
                && (RgbMulti.getG3() - G3Standard >= -G3Difference && RgbMulti.getG3() - G3Standard <= G3Difference)
                && (RgbMulti.getB3() - B3Standard >= -B3Difference && RgbMulti.getB3() - B3Standard <= B3Difference);

        boolean b4 = (RgbMulti.getR4() - R4Standard >= -R4Difference && RgbMulti.getR4() - R4Standard <= R4Difference)
                && (RgbMulti.getG4() - G4Standard >= -G4Difference && RgbMulti.getG4() - G4Standard <= G4Difference)
                && (RgbMulti.getB4() - B4Standard >= -B4Difference && RgbMulti.getB4() - B4Standard <= B4Difference);


        if (b1) {
            pass = true;
        } else if (b2) {
            pass = true;
        } else if (b3) {
            pass = true;
        } else pass = b4;
        return pass;


//        boolean b1 = (RgbMulti.getR1() - R1Standard >= -R1Difference && RgbMulti.getR1() - R1Standard <= R1Difference)
//                  && (RgbMulti.getG1() - G1Standard >= -G1Difference && RgbMulti.getG1() - G1Standard <= G1Difference)
//                  && (RgbMulti.getB1() - B1Standard >= -B1Difference && RgbMulti.getB1() - B1Standard <= B1Difference);
//
//        boolean b2 = (RgbMulti.getR2() - R2Standard >= -R2Difference && RgbMulti.getR2() - R2Standard <= R2Difference)
//                  && (RgbMulti.getG2() - G2Standard >= -G2Difference && RgbMulti.getG2() - G2Standard <= G2Difference)
//                  && (RgbMulti.getB2() - B2Standard >= -B2Difference && RgbMulti.getB2() - B2Standard <= B2Difference);
//
//        boolean b3 = (RgbMulti.getR3() - R3Standard >= -R3Difference && RgbMulti.getR3() - R3Standard <= R3Difference)
//                  && (RgbMulti.getG3() - G3Standard >= -G3Difference && RgbMulti.getG3() - G3Standard <= G3Difference)
//                  && (RgbMulti.getB3() - B3Standard >= -B3Difference && RgbMulti.getB3() - B3Standard <= B3Difference);
//
//        boolean b4 = (RgbMulti.getR4() - R4Standard >= -R4Difference && RgbMulti.getR4() - R4Standard <= R4Difference)
//                  && (RgbMulti.getG4() - G4Standard >= -G4Difference && RgbMulti.getG4() - G4Standard <= G4Difference)
//                  && (RgbMulti.getB4() - B4Standard >= -B4Difference && RgbMulti.getB4() - B4Standard <= B4Difference);
//
//
//        if (b1 && b2) {
//            pass = true;
//        } else if (b1 && b3) {
//            pass = true;
//        } else if (b1 && b4) {
//            pass = true;
//        } else if (b2 && b3) {
//            pass = true;
//        } else pass = b2 && b4;
//        return pass;
    }


    /**
     * 获得图片的颜色值方法
     * offset是目标内存的起始地址的偏移量，stride是目标内存中隔多少个Pixels再写下一行；
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
