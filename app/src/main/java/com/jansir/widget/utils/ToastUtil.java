package com.jansir.widget.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * author: jansir.
 * package: com.jansir.widget.utils.
 * date: 2019/6/6.
 */
public class ToastUtil {

    public static void showToast(Context context,String message){
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
