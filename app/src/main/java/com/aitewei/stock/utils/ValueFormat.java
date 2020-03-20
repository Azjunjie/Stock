package com.aitewei.stock.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:    数值格式化
 * Author:         张俊杰
 * CreateDate:     2020/3/16 下午9:28
 * Version:        1.0
 */
public class ValueFormat {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static DecimalFormat format = new DecimalFormat(".00");

    public static String formatMoney(double money) {
        return format.format(money);
    }

    public static String formatRate(double rate) {
        return format.format(rate) + "%";
    }

    public static String formatDate(String dateStr) {
        if (TextUtils.isEmpty(dateStr)) {
            return "--";
        }
        return dateFormat.format(new Date(Long.valueOf(dateStr.substring(6, 19))));
    }

}
