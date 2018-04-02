package com.wangnan.currentactivity.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

/**
 * @ClassName: ActivityUtil
 * @Description: Activity跳转工具类
 * @Author wangnan7
 * @Date: 2018/4/1
 */

public class ActivityUtil {

    /**
     * 跳转系统悬浮窗授权页
     *
     * @param context
     */
    public static void turnToOverlayPermission(Context context) {
        // Android 6.0以上需要配置
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            context.startActivity(intent);
        }
    }

    /**
     * 跳转通知授权页
     *
     * @param context
     */
    public static void turnToNotifyPermission(Context context) {
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Android 8.0以上Intent跳转配置
            intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Android 5.0以上Intent配置
            intent = new Intent("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else {
            // Android 5.0以下Intent配置
            intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        }
        context.startActivity(intent);
    }

    /**
     * 去授权"辅助服务"
     *
     * @param context
     */
    public static void toAuthAccessibilityService(Context context) {
        Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
        context.startActivity(intent);
    }
}
