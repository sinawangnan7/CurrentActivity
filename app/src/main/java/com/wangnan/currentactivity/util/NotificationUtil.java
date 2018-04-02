package com.wangnan.currentactivity.util;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.wangnan.currentactivity.R;
import com.wangnan.currentactivity.receiver.MAccessibilityServiceReceiver;
import com.wangnan.currentactivity.ui.activity.MainActivity;

/**
 * @ClassName: NotificationUtil
 * @Description: 通知栏工具类
 * @Author wangnan7
 * @Date: 2018/4/1
 */

public class NotificationUtil {

    /**
     * 渠道ID (Android 8.0以上使用)
     */
    public static final String CHANNEL_ID = "Channel_01";

    /**
     * 渠道名 (Android 8.0以上使用)
     */
    public static final String CHANNEL_NAME = "Channel1";

    /**
     * 根据Android不同版本获取Notification实例
     *
     * @param context
     * @return
     */
    public static Notification getNotificationByVersion(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return getONotification(context);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return getNNotification(context);
        } else {
            return getNotification(context);
        }
    }

    /**
     * 获取Android O以上Notification对象
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static Notification getONotification(Context context) {
        // 创建通知建造者
        Notification.Builder builder = new Notification.Builder(context, null);
        // 设置通知小图标
        builder.setSmallIcon(R.mipmap.ic_launcher);
        // 设置渠道ID
        builder.setChannelId(CHANNEL_ID);
        // 设置自定义视图
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.lay_custom_notification);
        // 设置自定义视图点击意图（传递给"MAccessibilityServiceReceiver，显示/隐藏悬浮窗、关闭悬浮窗）
        remoteViews.setOnClickPendingIntent(R.id.tv_switch, PendingIntent.getBroadcast(context, 0, new Intent(MAccessibilityServiceReceiver.SWITCH_ACTION), 0));
        remoteViews.setOnClickPendingIntent(R.id.tv_close, PendingIntent.getBroadcast(context, 0, new Intent(MAccessibilityServiceReceiver.CLOSE_ACTION), 0));
        // 设置自定义视图
        builder.setCustomContentView(remoteViews);
        // 构建Notification实例并返回
        return builder.build();
    }

    /**
     * 获取Android N以上Notification对象 (不包含Android O及其之上)
     */
    private static Notification getNNotification(Context context) {
        // 创建通知建造者
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, null);
        // 设置通知小图标
        builder.setSmallIcon(R.mipmap.ic_launcher);
        // 设置自定义视图
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.lay_custom_notification);
        // 设置自定义视图点击意图（传递给"MAccessibilityServiceReceiver，显示/隐藏悬浮窗、关闭悬浮窗）
        remoteViews.setOnClickPendingIntent(R.id.tv_switch, PendingIntent.getBroadcast(context, 0, new Intent(MAccessibilityServiceReceiver.SWITCH_ACTION), 0));
        remoteViews.setOnClickPendingIntent(R.id.tv_close, PendingIntent.getBroadcast(context, 0, new Intent(MAccessibilityServiceReceiver.CLOSE_ACTION), 0));
        // 设置自定义视图
        builder.setCustomContentView(remoteViews);
        // 构建Notification实例并返回
        return builder.build();
    }

    /**
     * 获取Android 7.0以下Notification对象
     */
    private static Notification getNotification(Context context) {
        // 创建通知建造者
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, null);
        // 设置通知小图标
        builder.setSmallIcon(R.mipmap.ic_launcher);
        // 设置标题
        builder.setContentTitle(context.getString(R.string.app_name));
        // 设置消息内容
        builder.setContentText(context.getString(R.string.service_running));
        // 设置内容意图（跳转"辅助功能"）
        builder.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS), 0));
        // 构建Notification实例并返回
        return builder.build();
    }
}
