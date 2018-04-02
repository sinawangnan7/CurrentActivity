package com.wangnan.currentactivity.util;

import android.support.v7.app.AppCompatActivity;

import com.wangnan.currentactivity.widget.CustomDialog;

/**
 * @ClassName: DialogUtil
 * @Description: 对话框工具类
 * @Author wangnan7
 * @Date: 2018/4/1
 */

public class DialogUtil {

    /**
     * 显示"悬浮窗权限"提醒对话框
     *
     * @param activity
     */
    public static void showOverlayAlertDialog(final AppCompatActivity activity) {
        CustomDialog.showInstance(
                activity,
                "需要开启【悬浮窗权限】",
                "取消",
                new CustomDialog.CancleCallback() {
                    @Override
                    public void onCancle() {

                    }
                },
                "去开启",
                new CustomDialog.ConfirmCallback() {
                    @Override
                    public void onConfirm() {
                        ActivityUtil.turnToOverlayPermission(activity);
                    }
                });
    }

    /**
     * 显示"辅助服务"提醒对话框
     *
     * @param activity
     * @param msg      消息内容
     * @param cancle   取消按钮文本
     * @param confirm  确认按钮文本
     */
    public static void showAccessibilityServiceAlertDialog(final AppCompatActivity activity, String msg, String cancle, String confirm) {
        CustomDialog.showInstance(
                activity,
                msg,
                cancle,
                new CustomDialog.CancleCallback() {
                    @Override
                    public void onCancle() {

                    }
                },
                confirm,
                new CustomDialog.ConfirmCallback() {
                    @Override
                    public void onConfirm() {
                        ActivityUtil.toAuthAccessibilityService(activity);
                    }
                });
    }
}
