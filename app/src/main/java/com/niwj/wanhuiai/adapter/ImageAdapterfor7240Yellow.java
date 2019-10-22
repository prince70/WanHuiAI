package com.niwj.wanhuiai.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.niwj.wanhuiai.R;
import com.niwj.wanhuiai.entity.DetectReport;
import com.niwj.wanhuiai.entity.ImageDetect7240Yellow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by prince on 2019/9/25.
 * 适配器
 */
public class ImageAdapterfor7240Yellow extends BaseAdapter {
    private List<ImageDetect7240Yellow> dataList = new ArrayList<>();
    private Activity mActivity;

    public ImageAdapterfor7240Yellow(List<ImageDetect7240Yellow> dataList, Activity mActivity) {
        this.dataList = dataList;
        this.mActivity = mActivity;
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.gridview_item, null);

            holder.img = convertView.findViewById(R.id.img);
            holder.iv_result = convertView.findViewById(R.id.iv_result);
            holder.tv_color = convertView.findViewById(R.id.tv_color);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_color.setText(
                dataList.get(i).getRgbmulti().getR1() + " " + dataList.get(i).getRgbmulti().getG1() + " " + dataList.get(i).getRgbmulti().getB1() + "\n"
                        + dataList.get(i).getRgbmulti().getR2() + " " + dataList.get(i).getRgbmulti().getG2() + " " + dataList.get(i).getRgbmulti().getB2() + "\n"
                        + dataList.get(i).getRgbmulti().getR3() + " " + dataList.get(i).getRgbmulti().getG3() + " " + dataList.get(i).getRgbmulti().getB3() + "\n"
                        + dataList.get(i).getRgbmulti().getR4() + " " + dataList.get(i).getRgbmulti().getG4() + " " + dataList.get(i).getRgbmulti().getB4() + "\n"
                        + dataList.get(i).getRgbmulti().getR5() + " " + dataList.get(i).getRgbmulti().getG5() + " " + dataList.get(i).getRgbmulti().getB5());

        holder.img.setImageBitmap(dataList.get(i).getBitmap());
        if (dataList.get(i).isPass()) {
            holder.iv_result.setImageResource(R.mipmap.pass);
        } else {
//          TODO 报告保存 并发出提示  时间？次序？ 怎么区分不同批 => 时间
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
            String t = format.format(new Date());
//            Log.e("msg", t);
            Log.e("不通过", "getView: " + (i + 1));

            DetectReport detectReport = new DetectReport();
            detectReport.setTime(t);
            detectReport.setFailNumber("" + (i + 1));
            detectReport.save();

            holder.iv_result.setImageResource(R.mipmap.failed);
        }
        getBitmapColorAndDrawRectangle(dataList.get(i).getBitmap(), holder.img);

        return convertView;
    }

    class ViewHolder {
        ImageView img;
        ImageView iv_result;
        TextView tv_color;

    }

    private void getBitmapColorAndDrawRectangle(Bitmap bitmap, ImageView imageView) {
        int width_difference = bitmap.getWidth() / 5;
        int height_difference = bitmap.getHeight() / 5;

        int w = bitmap.getWidth() / 10;
        int h = bitmap.getHeight() / 10;

   /*     int[] leftValue = {(int) (width_difference * 1.5 - 5), (int) (width_difference * 1.5 - 5), width_difference * 2 - 5, (int) (width_difference * 2.5 - 5), (int) (width_difference * 2.5 - 5)};
        int[] topValue = {height_difference - 5, height_difference * 3 - 5, height_difference * 2 - 5, height_difference - 5, height_difference * 3 - 5};
        int[] rightValue = {(int) (width_difference * 1.5 + 10), (int) (width_difference * 1.5 + 10), width_difference * 2 + 10, (int) (width_difference * 2.5 + 10), (int) (width_difference * 2.5 + 10)};
        int[] bottomValue = {height_difference + 10, height_difference * 3 + 10, height_difference * 2 + 10, height_difference + 10, height_difference * 3 + 10};

*/

        /**
         * 适合一整盘锁  摆锁需要方正一些   位置可能还需要适当调整
         */
    /*    int[] leftValue = {(int) (width_difference - 2.5),
                (int) (width_difference - 2.5),
                (int) (width_difference * 2 - 2.5),
                (int) (width_difference * 3 - 2.5),
                (int) (width_difference * 3 - 2.5)};

        int[] topValue = {(int) (height_difference - 2.5),
                (int) (height_difference * 3 - 2.5),
                (int) (height_difference * 2 - 2.5),
                (int) (height_difference - 2.5),
                (int) (height_difference * 3 - 2.5)};

        int[] rightValue = {(width_difference + 5),
                (width_difference + 5),
                width_difference * 2 + 5,
                (width_difference * 3 + 5),
                (width_difference * 3 + 5)};

        int[] bottomValue = {height_difference + 5,
                height_difference * 3 + 5,
                height_difference * 2 + 5,
                height_difference + 5,
                height_difference * 3 + 5};*/

//        int[] leftValue = {(int) (width_difference - 5),
//                (int) (width_difference - 5),
//                (int) (width_difference * 2 - 5),
//                (int) (width_difference * 3 - 5),
//                (int) (width_difference * 3 - 5)};
//
//        int[] topValue = {(int) (height_difference - 5),
//                (int) (height_difference * 3 - 5),
//                (int) (height_difference * 2 - 5),
//                (int) (height_difference - 5),
//                (int) (height_difference * 3 - 5)};
//
//        int[] rightValue = {(width_difference + 10),
//                (width_difference + 10),
//                width_difference * 2 + 10,
//                (width_difference * 3 + 10),
//                (width_difference * 3 + 10)};
//
//        int[] bottomValue = {height_difference + 10,
//                height_difference * 3 + 10,
//                height_difference * 2 + 10,
//                height_difference + 10,
//                height_difference * 3 + 10};

       /* int[] leftValue = {
                 (width_difference ),
                 (width_difference ),
                 (width_difference * 2 ),
                 (width_difference * 3 ),
                 (width_difference * 3 )};

        int[] topValue = {
                 (height_difference ),
                 (height_difference * 3 ),
                 (height_difference * 2 ),
                 (height_difference ),
                 (height_difference * 3 )};

        int[] rightValue = {(width_difference + 15),
                (width_difference + 15),
                width_difference * 2 + 15,
                (width_difference * 3 + 15),
                (width_difference * 3 + 15)};

        int[] bottomValue = {height_difference + 15,
                height_difference * 3 + 15,
                height_difference * 2 + 15,
                height_difference + 15,
                height_difference * 3 + 15};*/


        int[] leftValue = {
                (width_difference +10),
                (width_difference +10),
                (width_difference * 2 +10),
                (width_difference * 3 +10),
                (width_difference * 3 +10)};

        int[] topValue = {
                (height_difference ),
                (height_difference * 3 ),
                (height_difference * 2 ),
                (height_difference ),
                (height_difference * 3 )};

        int[] rightValue = {(width_difference + 25),
                (width_difference + 25),
                width_difference * 2 + 25,
                (width_difference * 3 + 25),
                (width_difference * 3 + 25)};

        int[] bottomValue = {height_difference + 15,
                height_difference * 3 + 15,
                height_difference * 2 + 15,
                height_difference + 15,
                height_difference * 3 + 15};

/**
 *  1   4
 *    3
 *  2   5
 */

/*        int[] leftValue = {
                 (width_difference - w),
                 (width_difference - w),
                 (width_difference * 2 - w),
                 (width_difference * 3 - w),
                 (width_difference * 3 - w)};

        int[] topValue = {
                 (height_difference -h),
                 (height_difference * 3 -h),
                 (height_difference * 2 -h),
                 (height_difference -h),
                 (height_difference * 3 -h)};

        int[] rightValue = {
                (width_difference + w),
                (width_difference + w),
                width_difference * 2 + w,
                (width_difference * 3 + w),
                (width_difference * 3 + w)};

        int[] bottomValue = {
                height_difference + h,
                height_difference * 3 + h,
                height_difference * 2 + h,
                height_difference + h,
                height_difference * 3 + h};*/

        int left, top, right, bottom;//左上角(left,top)  右下角(right,bottom)
        Bitmap copy = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(copy);
        Paint paint = new Paint();
        for (int i = 0; i < 5; i++) {
            left = leftValue[i];
            top = topValue[i];
            right = rightValue[i];
            bottom = bottomValue[i];


            paint.setColor(Color.GREEN);
            paint.setStyle(Paint.Style.STROKE);//不填充
            paint.setStrokeWidth(2);//线的宽度
            canvas.drawRect(left, top, right, bottom, paint);

        }

        imageView.setImageBitmap(copy);
    }

}

