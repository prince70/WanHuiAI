package com.niwj.wanhuiai;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/10/7.
 */
public class Test {

    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String t=format.format(new Date());
        System.out.println(t);

        List<String> datas = new ArrayList<>();
        datas.add("倪");
        datas.add("伟");
        datas.add("金");

        System.out.println(datas);

        Date date = new Date();
        String s = date.toString();
        System.out.println(s+"\n    8456589654");

    }


}
