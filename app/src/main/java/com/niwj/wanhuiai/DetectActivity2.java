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

import com.niwj.wanhuiai.adapter.ImageAdapterfor7240Yellow;
import com.niwj.wanhuiai.entity.ImageDetect7240Yellow;
import com.niwj.wanhuiai.entity.ImagePiece;
import com.niwj.wanhuiai.entity.RGBMULTI7240Yellow;
import com.niwj.wanhuiai.utils.CountDownHelper;
import com.niwj.wanhuiai.utils.ImageSplitter;

/**
 * Created by Administrator on 2019/9/24.
 */
public class DetectActivity2 extends AppCompatActivity {
    private static final String TAG = "DetectActivity";

    private ImageView iv_show;
    private TextView tv_count;

    private GridView gv;
    private List<ImageDetect7240Yellow> list = new ArrayList<>();
    private ImageAdapterfor7240Yellow adapter;
    private List<RGBMULTI7240Yellow> rgbList = new ArrayList<>();

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
        CountDownHelper helper = new CountDownHelper(tv_count, "0", 3, 1);
        helper.setOnFinishListener(new CountDownHelper.OnFinishListener() {
            @Override
            public void finish() {
                DetectActivity2.this.finish();
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
                    switch (i) {
                        case 0:
                            list.add(new ImageDetect7240Yellow(split.get(0).bitmap, JudgePassOrFailed(rgbList.get(0),
                                    191, 159, 15,
                                    189, 159, 13,
                                    194, 163, 10,
                                    199, 167, 9,
                                    196, 168, 11,
                                    5, 4, 2,
                                    5, 3, 7,
                                    6, 4, 3,
                                    6, 5, 2,
                                    6, 3, 2), rgbList.get(0)));
                            break;
                        case 1:
                            list.add(new ImageDetect7240Yellow(split.get(1).bitmap, JudgePassOrFailed(rgbList.get(1),
                                    199, 173, 17,
                                    197, 168, 19,
                                    202, 176, 13,
                                    202, 180, 13,
                                    203, 177, 16,
                                    8, 7, 3,
                                    3, 3, 4,
                                    6, 2, 4,
                                    5, 5, 3,
                                    5, 4, 3), rgbList.get(1)));
                            break;
                        case 2:
                            list.add(new ImageDetect7240Yellow(split.get(2).bitmap, JudgePassOrFailed(rgbList.get(2),
                                    200, 172, 18,
                                    191, 163, 21,
                                    199, 169, 13,
                                    201, 174, 13,
                                    198, 169, 13,
                                    7, 6, 3,
                                    3, 3, 3,
                                    6, 5, 4,
                                    6, 6, 2,
                                    5, 2, 2), rgbList.get(2)));
                            break;
                        case 3:
                            list.add(new ImageDetect7240Yellow(split.get(3).bitmap, JudgePassOrFailed(rgbList.get(3),
                                    217, 192, 13,
                                    215, 192, 17,
                                    218, 195, 9,
                                    220, 197, 9,
                                    218, 192, 7,
                                    5, 5, 5,
                                    3, 4, 2,
                                    4, 3, 3,
                                    4, 6, 2,
                                    5, 4, 2), rgbList.get(3)));
                            break;
                        case 4:
                            list.add(new ImageDetect7240Yellow(split.get(4).bitmap, JudgePassOrFailed(rgbList.get(4),
                                    226, 221, 10,
                                    217, 193, 13,
                                    226, 203, 10,
                                    236, 213, 11,
                                    224, 199, 10,
                                    5, 10, 3,
                                    3, 7, 3,
                                    5, 8, 4,
                                    6, 11, 3,
                                    3, 8, 2), rgbList.get(4)));
                            break;
                        case 5:
                            list.add(new ImageDetect7240Yellow(split.get(5).bitmap, JudgePassOrFailed(rgbList.get(5),
                                    240, 210, 8,
                                    226, 201, 14,
                                    235, 209, 10,
                                    242, 215, 11,
                                    226, 202, 10,
                                    7, 10, 2,
                                    5, 10, 3,
                                    7, 10, 2,
                                    9, 9, 4,
                                    6, 9, 3), rgbList.get(5)));
                            break;
                        case 6:
                            list.add(new ImageDetect7240Yellow(split.get(6).bitmap, JudgePassOrFailed(rgbList.get(6),
                                    237, 212, 8,
                                    221, 195, 10,
                                    226, 201, 6,
                                    234, 208, 6,
                                    218, 192, 6,
                                    8, 11, 1,
                                    8, 13, 3,
                                    8, 11, 3,
                                    8, 12, 1,
                                    8, 9, 2), rgbList.get(6)));
                            break;
                        case 7:
                            list.add(new ImageDetect7240Yellow(split.get(7).bitmap, JudgePassOrFailed(rgbList.get(7),
                                    237, 212, 14,
                                    225, 200, 11,
                                    229, 204, 10,
                                    232, 206, 7,
                                    220, 193, 10,
                                    8, 9, 3,
                                    10, 9, 4,
                                    9, 10, 2,
                                    10, 10, 2,
                                    9, 10, 3), rgbList.get(7)));
                            break;
                        case 8:
                            list.add(new ImageDetect7240Yellow(split.get(8).bitmap, JudgePassOrFailed(rgbList.get(8),
                                    222, 195, 9,
                                    217, 186, 17,
                                    219, 192, 6,
                                    220, 192, 4,
                                    203, 189, 14,
                                    6, 6, 3,
                                    6, 9, 5,
                                    5, 6, 2,
                                    6, 6, 2,
                                    19, 3, 4), rgbList.get(8)));
                            break;
                        case 9:
                            list.add(new ImageDetect7240Yellow(split.get(9).bitmap, JudgePassOrFailed(rgbList.get(9),
                                    194, 163, 10,
                                    188, 154, 24,
                                    192, 163, 12,
                                    196, 166, 14,
                                    194, 163, 14,
                                    3, 3, 4,
                                    3, 6, 8,
                                    4, 5, 2,
                                    4, 3, 3,
                                    6, 5, 5), rgbList.get(9)));
                            break;
                        case 10:
                            list.add(new ImageDetect7240Yellow(split.get(10).bitmap, JudgePassOrFailed(rgbList.get(10),
                                    199, 170, 23,
                                    190, 161, 20,
                                    197, 168, 15,
                                    200, 173, 14,
                                    197, 168, 13,
                                    5, 5, 8,
                                    2, 4, 2,
                                    5, 4, 4,
                                    6, 6, 2,
                                    6, 4, 4), rgbList.get(10)));
                            break;
                        case 11:
                            list.add(new ImageDetect7240Yellow(split.get(11).bitmap, JudgePassOrFailed(rgbList.get(11),
                                    200, 175, 15,
                                    197, 169, 20,
                                    200, 175, 17,
                                    204, 177, 17,
                                    199, 177, 20,
                                    5, 6, 6,
                                    2, 3, 2,
                                    5, 4, 5,
                                    6, 5, 3,
                                    5, 3, 4), rgbList.get(11)));
                            break;
                        case 12:
                            list.add(new ImageDetect7240Yellow(split.get(12).bitmap, JudgePassOrFailed(rgbList.get(12),
                                    213, 186, 19,
                                    212, 183, 18,
                                    215, 189, 15,
                                    217, 189, 16,
                                    217, 189, 15,
                                    4, 3, 3,
                                    3, 2, 4,
                                    3, 3, 4,
                                    3, 3, 3,
                                    0, 3, 2), rgbList.get(12)));
                            break;
                        case 13:
                            list.add(new ImageDetect7240Yellow(split.get(13).bitmap, JudgePassOrFailed(rgbList.get(13),
                                    208, 179, 15,
                                    204, 176, 20,
                                    205, 179, 17,
                                    211, 181, 20,
                                    206, 179, 19,
                                    5, 5, 4,
                                    4, 2, 3,
                                    6, 2, 3,
                                    6, 4, 3,
                                    6, 2, 4), rgbList.get(13)));
                            break;
                        case 14:
                            list.add(new ImageDetect7240Yellow(split.get(14).bitmap, JudgePassOrFailed(rgbList.get(14),
                                    212, 190, 18,
                                    216, 190, 20,
                                    218, 193, 17,
                                    217, 189, 15,
                                    215, 188, 15,
                                    3, 3, 3,
                                    4, 5, 3,
                                    4, 3, 5,
                                    5, 5, 2,
                                    4, 4, 3), rgbList.get(14)));
                            break;
                        case 15:
                            list.add(new ImageDetect7240Yellow(split.get(15).bitmap, JudgePassOrFailed(rgbList.get(15),
                                    213, 187, 15,
                                    210, 182, 18,
                                    210, 185, 14,
                                    210, 184, 13,
                                    208, 182, 14,
                                    5, 6, 2,
                                    4, 5, 4,
                                    6, 4, 2,
                                    4, 6, 2,
                                    4, 3, 4), rgbList.get(15)));
                            break;
                        case 16:
                            list.add(new ImageDetect7240Yellow(split.get(16).bitmap, JudgePassOrFailed(rgbList.get(16),
                                    203, 175, 15,
                                    199, 169, 14,
                                    200, 171, 12,
                                    203, 175, 11,
                                    197, 168, 10,
                                    7, 7, 2,
                                    4, 4, 2,
                                    6, 3, 2,
                                    8, 8, 2,
                                    4, 4, 2), rgbList.get(16)));
                            break;
                        case 17:
                            list.add(new ImageDetect7240Yellow(split.get(17).bitmap, JudgePassOrFailed(rgbList.get(17),
                                    211, 185, 12,
                                    210, 181, 11,
                                    213, 185, 8,
                                    215, 185, 9,
                                    215, 185, 9,
                                    4, 4, 4,
                                    4, 2, 2,
                                    5, 4, 2,
                                    4, 2, 2,
                                    4, 3, 2), rgbList.get(17)));
                            break;
                        case 18:
                            list.add(new ImageDetect7240Yellow(split.get(18).bitmap, JudgePassOrFailed(rgbList.get(18),
                                    190, 159, 20,
                                    190, 156, 18,
                                    192, 160, 10,
                                    196, 162, 11,
                                    194, 163, 12,
                                    7, 6, 9,
                                    4, 2, 2,
                                    4, 3, 2,
                                    6, 3, 2,
                                    6, 3, 2), rgbList.get(18)));
                            break;
//                            ,,,,
                        case 19:
                            list.add(new ImageDetect7240Yellow(split.get(19).bitmap, JudgePassOrFailed(rgbList.get(19),
                                    206, 172, 23,
                                    212, 168, 30,
                                    207, 175, 12,
                                    208, 175, 13,
                                    209, 179, 14,
                                    4, 2, 6,
                                    13, 2, 2,
                                    4, 3, 2,
                                    6, 2, 3,
                                    5, 3, 3), rgbList.get(19)));
                            break;
                        case 20:
                            list.add(new ImageDetect7240Yellow(split.get(20).bitmap, JudgePassOrFailed(rgbList.get(20),
                                    207, 175, 20,
                                    202, 168, 20,
                                    211, 183, 18,
                                    208, 179, 19,
                                    213, 185, 21,
                                    3, 3, 3,
                                    3, 3, 3,
                                    5, 2, 3,
                                    6, 2, 4,
                                    5, 3, 3), rgbList.get(20)));
                            break;
                        case 21:
                            list.add(new ImageDetect7240Yellow(split.get(21).bitmap, JudgePassOrFailed(rgbList.get(21),
                                    203, 174, 18,
                                    200, 176, 24,
                                    206, 178, 20,
                                    205, 175, 20,
                                    208, 179, 20,
                                    5, 4, 6,
                                    3, 3, 3,
                                    4, 2, 3,
                                    5, 2, 5,
                                    5, 2, 3), rgbList.get(21)));
                            break;
                        case 22:
                            list.add(new ImageDetect7240Yellow(split.get(22).bitmap, JudgePassOrFailed(rgbList.get(22),
                                    198, 167, 19,
                                    194, 164, 28,
                                    194, 166, 21,
                                    196, 166, 19,
                                    194, 165, 20,
                                    6, 5, 4,
                                    6, 5, 4,
                                    6, 2, 6,
                                    6, 3, 3,
                                    5, 3, 3), rgbList.get(22)));
                            break;
                        case 23:
                            list.add(new ImageDetect7240Yellow(split.get(23).bitmap, JudgePassOrFailed(rgbList.get(23),
                                    210, 182, 19,
                                    214, 188, 26,
                                    214, 186, 19,
                                    210, 181, 17,
                                    213, 186, 22,
                                    5, 2, 6,
                                    5, 3, 6,
                                    5, 2, 3,
                                    4, 2, 5,
                                    6, 2, 2), rgbList.get(23)));
                            break;
                        case 24:
                            list.add(new ImageDetect7240Yellow(split.get(24).bitmap, JudgePassOrFailed(rgbList.get(24),
                                    215, 185, 20,
                                    213, 187, 20,
                                    217, 191, 17,
                                    214, 183, 16,
                                    214, 186, 16,
                                    3, 2, 3,
                                    4, 5, 4,
                                    4, 2, 3,
                                    5, 2, 3,
                                    7, 3, 2), rgbList.get(24)));
                            break;
                        case 25:
                            list.add(new ImageDetect7240Yellow(split.get(25).bitmap, JudgePassOrFailed(rgbList.get(25),
                                    208, 180, 16,
                                    206, 178, 16,
                                    204, 177, 12,
                                    207, 177, 14,
                                    203, 176, 16,
                                    6, 3, 4,
                                    5, 2, 2,
                                    4, 4, 2,
                                    4, 3, 3,
                                    4, 2, 3), rgbList.get(25)));
                            break;
                        case 26:
                            list.add(new ImageDetect7240Yellow(split.get(26).bitmap, JudgePassOrFailed(rgbList.get(26),
                                    199, 175, 18,
                                    202, 176, 19,
                                    204, 177, 15,
                                    205, 179, 15,
                                    205, 178, 14,
                                    5, 3, 2,
                                    5, 2, 3,
                                    4, 4, 2,
                                    4, 3, 2,
                                    4, 3, 2), rgbList.get(26)));
                            break;
                        case 27:
                            list.add(new ImageDetect7240Yellow(split.get(27).bitmap, JudgePassOrFailed(rgbList.get(27),
                                    195, 164, 19,
                                    191, 160, 22,
                                    194, 162, 11,
                                    196, 165, 13,
                                    195, 165, 12,
                                    5, 4, 5,
                                    2, 2, 2,
                                    7, 2, 2,
                                    6, 5, 2,
                                    6, 3, 2), rgbList.get(27)));
                            break;
//                            ,,,
                        case 28:
                            list.add(new ImageDetect7240Yellow(split.get(28).bitmap, JudgePassOrFailed(rgbList.get(28),
                                    208, 175, 14,
                                    204, 171, 14,
                                    205, 175, 14,
                                    209, 175, 13,
                                    208, 177, 15,
                                    4, 5, 4,
                                    3, 3, 4,
                                    5, 2, 2,
                                    6, 2, 3,
                                    5, 2, 4), rgbList.get(28)));
                            break;
//                            ,,,
                        case 29:
                            list.add(new ImageDetect7240Yellow(split.get(29).bitmap, JudgePassOrFailed(rgbList.get(29),
                                    216, 187, 21,
                                    214, 184, 21,
                                    219, 193, 18,
                                    222, 191, 16,
                                    222, 196, 19,
                                    4, 2, 5,
                                    2, 2, 2,
                                    4, 2, 5,
                                    4, 2, 5,
                                    5, 3, 4), rgbList.get(29)));
                            break;
//                            ,,,
                        case 30:
                            list.add(new ImageDetect7240Yellow(split.get(30).bitmap, JudgePassOrFailed(rgbList.get(30),
                                    216, 186, 26,
                                    216, 186, 26,
                                    222, 197, 19,
                                    225, 195, 21,
                                    226, 201, 24,
                                    8, 5, 4,
                                    2, 2, 2,
                                    5, 2, 4,
                                    5, 4, 7,
                                    5, 2, 5), rgbList.get(30)));
                            break;
                        case 31:
                            list.add(new ImageDetect7240Yellow(split.get(31).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    225, 196, 16,
                                    229, 205, 19,
                                    229, 203, 16,
                                    232, 206, 16,
                                    230, 205, 15,
                                    3, 2, 5,
                                    5, 2, 3,
                                    4, 2, 4,
                                    8, 9, 3,
                                    7, 4, 5), rgbList.get(31)));
                            break;
                        case 32:
                            list.add(new ImageDetect7240Yellow(split.get(32).bitmap, JudgePassOrFailed(rgbList.get(32),
                                    218, 189, 21,
                                    219, 194, 22,
                                    220, 194, 17,
                                    219, 189, 16,
                                    218, 192, 15,
                                    5, 2, 6,
                                    3, 3, 4,
                                    5, 2, 3,
                                    5, 2, 2,
                                    6, 3, 2), rgbList.get(32)));
                            break;
                        case 33:
                            list.add(new ImageDetect7240Yellow(split.get(33).bitmap, JudgePassOrFailed(rgbList.get(33),
                                    214, 183, 19,
                                    219, 188, 20,
                                    213, 185, 17,
                                    213, 183, 16,
                                    214, 186, 20,
                                    4, 2, 2,
                                    8, 4, 2,
                                    4, 3, 2,
                                    5, 2, 2,
                                    5, 3, 2), rgbList.get(33)));
                            break;
                        case 34:
                            list.add(new ImageDetect7240Yellow(split.get(34).bitmap, JudgePassOrFailed(rgbList.get(34),
                                    199, 169, 17,
                                    199, 168, 21,
                                    197, 166, 16,
                                    198, 166, 18,
                                    196, 165, 21,
                                    7, 3, 4,
                                    5, 2, 4,
                                    7, 3, 4,
                                    6, 4, 2,
                                    6, 2, 2), rgbList.get(34)));
                            break;
                        case 35:
                            list.add(new ImageDetect7240Yellow(split.get(35).bitmap, JudgePassOrFailed(rgbList.get(35),
                                    202, 171, 15,
                                    204, 174, 18,
                                    204, 174, 16,
                                    203, 174, 13,
                                    204, 172, 14,
                                    4, 2, 3,
                                    5, 2, 3,
                                    6, 2, 3,
                                    4, 3, 5,
                                    5, 4, 2), rgbList.get(35)));
                            break;
                        case 36:
                            list.add(new ImageDetect7240Yellow(split.get(36).bitmap, JudgePassOrFailed(rgbList.get(36),
                                    204, 173, 21,
                                    197, 167, 24,
                                    203, 175, 16,
                                    203, 174, 13,
                                    204, 176, 13,
                                    7, 4, 7,
                                    6, 5, 6,
                                    5, 2, 4,
                                    7, 4, 2,
                                    7, 4, 5), rgbList.get(36)));
                            break;
                        case 37:
                            list.add(new ImageDetect7240Yellow(split.get(37).bitmap, JudgePassOrFailed(rgbList.get(37),
                                    207, 177, 20,
                                    203, 174, 25,
                                    208, 180, 16,
                                    209, 182, 17,
                                    211, 183, 20,
                                    7, 5, 9,
                                    5, 2, 5,
                                    5, 4, 3,
                                    7, 3, 2,
                                    5, 2, 4), rgbList.get(37)));
                            break;
//                            ,,,
                        case 38:
                            list.add(new ImageDetect7240Yellow(split.get(38).bitmap, JudgePassOrFailed(rgbList.get(38),
                                    209, 183, 22,
                                    210, 178, 26,
                                    215, 186, 23,
                                    215, 187, 19,
                                    215, 189, 19,
                                    4, 3, 5,
                                    2, 2, 7,
                                    4, 2, 3,
                                    6, 3, 4,
                                    5, 3, 2), rgbList.get(38)));
                            break;
//                            ,,,
                        case 39:
                            list.add(new ImageDetect7240Yellow(split.get(39).bitmap, JudgePassOrFailed(rgbList.get(39),
                                    226, 196, 18,
                                    226, 196, 18,
                                    226, 200, 16,
                                    227, 200, 12,
                                    226, 201, 15,
                                    7, 4, 5,
                                    3, 3, 3,
                                    5, 2, 3,
                                    6, 4, 5,
                                    7, 3, 2), rgbList.get(39)));
                            break;
                        case 40:
                            list.add(new ImageDetect7240Yellow(split.get(40).bitmap, JudgePassOrFailed(rgbList.get(40),
                                    223, 196, 19,
                                    218, 195, 18,
                                    220, 193, 16,
                                    226, 197, 22,
                                    221, 196, 19,
                                    6, 5, 3,
                                    5, 2, 2,
                                    5, 2, 3,
                                    7, 6, 7,
                                    6, 2, 4), rgbList.get(40)));
                            break;
                        case 41:
                            list.add(new ImageDetect7240Yellow(split.get(41).bitmap, JudgePassOrFailed(rgbList.get(41),
                                    219, 192, 19,
                                    217, 193, 18,
                                    218, 190, 19,
                                    218, 191, 19,
                                    218, 191, 20,
                                    7, 4, 5,
                                    3, 3, 2,
                                    5, 2, 3,
                                    9, 6, 4,
                                    7, 3, 4), rgbList.get(41)));
                            break;
                        case 42:
                            list.add(new ImageDetect7240Yellow(split.get(42).bitmap, JudgePassOrFailed(rgbList.get(42),
                                    219, 193, 20,
                                    219, 195, 21,
                                    219, 193, 19,
                                    217, 190, 19,
                                    214, 190, 23,
                                    5, 4, 3,
                                    3, 2, 4,
                                    5, 2, 4,
                                    6, 3, 2,
                                    8, 5, 3), rgbList.get(42)));
                            break;
                        case 43:
                            list.add(new ImageDetect7240Yellow(split.get(43).bitmap, JudgePassOrFailed(rgbList.get(43),
                                    220, 195, 20,
                                    221, 198, 21,
                                    221, 197, 21,
                                    219, 194, 20,
                                    220, 195, 24,
                                    4, 2, 6,
                                    6, 2, 4,
                                    7, 2, 4,
                                    5, 3, 4,
                                    7, 3, 4), rgbList.get(43)));
                            break;
                        case 44:
                            list.add(new ImageDetect7240Yellow(split.get(44).bitmap, JudgePassOrFailed(rgbList.get(44),
                                    207, 178, 17,
                                    209, 179, 20,
                                    207, 178, 17,
                                    208, 178, 16,
                                    209, 177, 16,
                                    7, 2, 3,
                                    4, 3, 2,
                                    6, 3, 2,
                                    5, 3, 3,
                                    6, 4, 2), rgbList.get(44)));
                            break;
                        case 45:
                            list.add(new ImageDetect7240Yellow(split.get(45).bitmap, JudgePassOrFailed(rgbList.get(45),
                                    209, 177, 18,
                                    210, 181, 28,
                                    212, 185, 17,
                                    214, 185, 17,
                                    219, 191, 18,
                                    4, 6, 5,
                                    2, 2, 3,
                                    4, 4, 3,
                                    5, 2, 3,
                                    7, 5, 4), rgbList.get(45)));
                            break;
                        case 46:
                            list.add(new ImageDetect7240Yellow(split.get(46).bitmap, JudgePassOrFailed(rgbList.get(46),
                                    210, 181, 18,
                                    206, 177, 27,
                                    210, 184, 21,
                                    212, 185, 23,
                                    213, 189, 21,
                                    5, 2, 5,
                                    5, 2, 3,
                                    5, 2, 2,
                                    6, 3, 3,
                                    6, 3, 2), rgbList.get(46)));
                            break;
                        case 47:
                            list.add(new ImageDetect7240Yellow(split.get(47).bitmap, JudgePassOrFailed(rgbList.get(47),
                                    206, 176, 19,
                                    214, 181, 27,
                                    210, 182, 15,
                                    211, 182, 17,
                                    212, 186, 16,
                                    6, 3, 7,
                                    13, 8, 2,
                                    5, 4, 3,
                                    7, 4, 3,
                                    6, 3, 2), rgbList.get(47)));
                            break;
                        case 48:
                            list.add(new ImageDetect7240Yellow(split.get(48).bitmap, JudgePassOrFailed(rgbList.get(48),
                                    211, 183, 16,
                                    206, 181, 30,
                                    214, 187, 15,
                                    218, 189, 14,
                                    216, 188, 16,
                                    7, 6, 6,
                                    2, 4, 6,
                                    5, 2, 3,
                                    7, 7, 3,
                                    6, 2, 3), rgbList.get(48)));
                            break;
                        case 49:
                            list.add(new ImageDetect7240Yellow(split.get(49).bitmap, JudgePassOrFailed(rgbList.get(49),
                                    222, 191, 15,
                                    217, 195, 24,
                                    221, 197, 14,
                                    223, 197, 14,
                                    220, 197, 15,
                                    7, 4, 5,
                                    5, 4, 7,
                                    7, 2, 2,
                                    7, 8, 2,
                                    4, 4, 3), rgbList.get(49)));
                            break;
                        case 50:
                            list.add(new ImageDetect7240Yellow(split.get(50).bitmap, JudgePassOrFailed(rgbList.get(50),
                                    221, 189, 11,
                                    218, 188, 15,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    9, 6, 4,
                                    4, 3, 2,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(50)));
                            break;
                        case 51:
                            list.add(new ImageDetect7240Yellow(split.get(51).bitmap, JudgePassOrFailed(rgbList.get(51),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(51)));
                            break;
                        case 52:
                            list.add(new ImageDetect7240Yellow(split.get(52).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(52)));
                            break;
                        case 53:
                            list.add(new ImageDetect7240Yellow(split.get(53).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(53)));
                            break;
                        case 54:
                            list.add(new ImageDetect7240Yellow(split.get(54).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(54)));
                            break;
                        case 55:
                            list.add(new ImageDetect7240Yellow(split.get(55).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(55)));
                            break;
                        case 56:
                            list.add(new ImageDetect7240Yellow(split.get(56).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(56)));
                            break;
                        case 57:
                            list.add(new ImageDetect7240Yellow(split.get(57).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(57)));
                            break;
                        case 58:
                            list.add(new ImageDetect7240Yellow(split.get(58).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(58)));
                            break;
                        case 59:
                            list.add(new ImageDetect7240Yellow(split.get(59).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(59)));
                            break;
                        case 60:
                            list.add(new ImageDetect7240Yellow(split.get(60).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(60)));
                            break;
                        case 61:
                            list.add(new ImageDetect7240Yellow(split.get(61).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(61)));
                            break;
                        case 62:
                            list.add(new ImageDetect7240Yellow(split.get(62).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(62)));
                            break;
                        case 63:
                            list.add(new ImageDetect7240Yellow(split.get(63).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(63)));
                            break;
                        case 64:
                            list.add(new ImageDetect7240Yellow(split.get(64).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(64)));
                            break;
                        case 65:
                            list.add(new ImageDetect7240Yellow(split.get(65).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(65)));
                            break;
                        case 66:
                            list.add(new ImageDetect7240Yellow(split.get(66).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(66)));
                            break;
                        case 67:
                            list.add(new ImageDetect7240Yellow(split.get(67).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(67)));
                            break;
                        case 68:
                            list.add(new ImageDetect7240Yellow(split.get(68).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(68)));
                            break;
                        case 69:
                            list.add(new ImageDetect7240Yellow(split.get(69).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(69)));
                            break;
                        case 70:
                            list.add(new ImageDetect7240Yellow(split.get(70).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(70)));
                            break;
                        case 71:
                            list.add(new ImageDetect7240Yellow(split.get(71).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(71)));
                            break;
                        case 72:
                            list.add(new ImageDetect7240Yellow(split.get(72).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(72)));
                            break;
                        case 73:
                            list.add(new ImageDetect7240Yellow(split.get(73).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(73)));
                            break;
                        case 74:
                            list.add(new ImageDetect7240Yellow(split.get(74).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(74)));
                            break;
                        case 75:
                            list.add(new ImageDetect7240Yellow(split.get(75).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(75)));
                            break;
                        case 76:
                            list.add(new ImageDetect7240Yellow(split.get(76).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(76)));
                            break;
                        case 77:
                            list.add(new ImageDetect7240Yellow(split.get(77).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(77)));
                            break;
                        case 78:
                            list.add(new ImageDetect7240Yellow(split.get(78).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(78)));
                            break;
                        case 79:
                            list.add(new ImageDetect7240Yellow(split.get(79).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(79)));
                            break;
                        case 80:
                            list.add(new ImageDetect7240Yellow(split.get(80).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(80)));
                            break;
                        case 81:
                            list.add(new ImageDetect7240Yellow(split.get(81).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(81)));
                            break;
                        case 82:
                            list.add(new ImageDetect7240Yellow(split.get(82).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(82)));
                            break;
                        case 83:
                            list.add(new ImageDetect7240Yellow(split.get(83).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(83)));
                            break;
                        case 84:
                            list.add(new ImageDetect7240Yellow(split.get(84).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(84)));
                            break;
                        case 85:
                            list.add(new ImageDetect7240Yellow(split.get(85).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(85)));
                            break;
                        case 86:
                            list.add(new ImageDetect7240Yellow(split.get(86).bitmap, JudgePassOrFailed(rgbList.get(31),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(86)));
                            break;
                        case 87:
                            list.add(new ImageDetect7240Yellow(split.get(87).bitmap, JudgePassOrFailed(rgbList.get(87),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(87)));
                            break;
                        case 88:
                            list.add(new ImageDetect7240Yellow(split.get(88).bitmap, JudgePassOrFailed(rgbList.get(88),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(88)));
                            break;
                        case 89:
                            list.add(new ImageDetect7240Yellow(split.get(89).bitmap,JudgePassOrFailed(rgbList.get(89),
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0,
                                    0, 0, 0), rgbList.get(89)));
                            break;


                    }
//                    list.add(new ImageDetect7240Yellow(split.get(i).bitmap, judgePass(rgbList.get(i)), mmm));

                }
            }

            adapter = new ImageAdapterfor7240Yellow(list, this);
            gv.setNumColumns(9);
            gv.setAdapter(adapter);


        }

    }

    private List<String> getColorFromAdapter(Bitmap bitmap) {
        int width_difference = bitmap.getWidth() / 5;
        int height_difference = bitmap.getHeight() / 5;
        List<Bitmap> bitmapList = new ArrayList<>();
//        Bitmap bitmap1 = Bitmap.createBitmap(bitmap, width_difference - 5, height_difference - 5, 10, 10);
//        Bitmap bitmap2 = Bitmap.createBitmap(bitmap, width_difference - 5, height_difference * 3 - 5, 10, 10);
//        Bitmap bitmap3 = Bitmap.createBitmap(bitmap, width_difference * 2 - 5, height_difference * 2 - 5, 10, 10);
//        Bitmap bitmap4 = Bitmap.createBitmap(bitmap, width_difference * 3 - 5, height_difference - 5, 10, 10);
//        Bitmap bitmap5 = Bitmap.createBitmap(bitmap, width_difference * 3 - 5, height_difference * 3 - 5, 10, 10);
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap, width_difference + 10, height_difference, 10, 10);
        Bitmap bitmap2 = Bitmap.createBitmap(bitmap, width_difference + 10, height_difference * 3, 10, 10);
        Bitmap bitmap3 = Bitmap.createBitmap(bitmap, width_difference * 2 + 10, height_difference * 2, 10, 10);
        Bitmap bitmap4 = Bitmap.createBitmap(bitmap, width_difference * 3 + 10, height_difference, 10, 10);
        Bitmap bitmap5 = Bitmap.createBitmap(bitmap, width_difference * 3 + 10, height_difference * 3, 10, 10);
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
     * @param R5Standard
     * @param G5Standard
     * @param B5Standard
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
     * @param R5Difference
     * @param G5Difference
     * @param B5Difference
     * @return
     */
    private boolean JudgePassOrFailed(RGBMULTI7240Yellow RgbMulti,
                                      int R1Standard, int G1Standard, int B1Standard,
                                      int R2Standard, int G2Standard, int B2Standard,
                                      int R3Standard, int G3Standard, int B3Standard,
                                      int R4Standard, int G4Standard, int B4Standard,
                                      int R5Standard, int G5Standard, int B5Standard,
                                      int R1Difference, int G1Difference, int B1Difference,
                                      int R2Difference, int G2Difference, int B2Difference,
                                      int R3Difference, int G3Difference, int B3Difference,
                                      int R4Difference, int G4Difference, int B4Difference,
                                      int R5Difference, int G5Difference, int B5Difference) {

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

        boolean b5 = (RgbMulti.getR5() - R5Standard >= -R5Difference && RgbMulti.getR5() - R5Standard <= R5Difference)
                && (RgbMulti.getG5() - G5Standard >= -G5Difference && RgbMulti.getG5() - G5Standard <= G5Difference)
                && (RgbMulti.getB5() - B5Standard >= -B5Difference && RgbMulti.getB5() - B5Standard <= B5Difference);

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
        return pass;
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
