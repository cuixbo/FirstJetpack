package com.example.firstapp.utils;

import android.graphics.Paint;
import android.util.Log;

import com.github.lzyzsd.circleprogress.DonutProgress;

import java.lang.reflect.Field;

public class ReflectUtil {

    public static void roundCap(DonutProgress progress) {
        try {
            Field field = progress.getClass().getDeclaredField("finishedPaint");
            field.setAccessible(true);
            Paint paint = (Paint) field.get(progress);
            if (paint != null) {
                paint.setStrokeCap(Paint.Cap.ROUND);
            }
            field.set(progress, paint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
