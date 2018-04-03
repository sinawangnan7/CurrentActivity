package com.wangnan.currentactivity.ui.window;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.wangnan.currentactivity.R;

/**
 * @ClassName: WindowViewContainer
 * @Description: 窗口视图容器
 * @Author wangnan7
 * @Date: 2018/4/1
 */

public class WindowViewContainer {

    /** ********************************    单例模式    *********************************************/

    /***
     * 窗口视图容器（引用变量）
     */
    private static WindowViewContainer mCustomWindowView;

    /**
     * 私有构造器
     */
    private WindowViewContainer(Context context) {
        mContext = context;
        initView(context);
    }

    /**
     * 获取窗口视图容器
     */
    public static synchronized WindowViewContainer getInstance(Context context) {
        if (mCustomWindowView == null) {
            mCustomWindowView = new WindowViewContainer(context);
        }
        return mCustomWindowView;
    }

    /*******************************************************************************************/

    private Context mContext;

    /**
     * 窗口文本视图（显示包名+类名）
     */
    private TextView mTextView;

    /**
     * 窗口布局参数
     */
    private WindowManager.LayoutParams mParams;

    /**
     * 窗口管理器
     */
    private WindowManager mWindowManager;

    /**
     * 是否已添加窗口
     */
    private boolean isAdded;

    /**
     * 是否处于显示状态
     */
    private boolean isShow;

    /**
     * 初始化视图
     *
     * @param context
     */
    private void initView(Context context) {
        mTextView = (TextView) LayoutInflater.from(context).inflate(R.layout.lay_window, null);
    }

    /**
     * 添加窗口视图
     */
    public void addWindowView() {
        // 如果窗口已添加直接返回
        if (isAdded) {
            return;
        }
        addView();
    }

    /**
     * 添加窗口视图
     */
    private void addView() {
        // 创建布局参数
        mParams = new WindowManager.LayoutParams();
        // 获取窗口管理器
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        // 设置类型
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Android O 以上，使用TYPE_APPLICATION_OVERLAY弹窗类型
            mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            // Android O 以下，使用TYPE_SYSTEM_ALERT弹窗类型
            mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        // 设置标签（FLAG_NOT_FOCUSABLE表示窗口不会获取焦点；FLAG_NOT_TOUCHABLE表示窗口不会接收Touch事件，即将Touch事件向下层分发）
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        // 设置位图模式 （PixelFormat.RGBA_8888可以使背景透明。不设置默认PixelFormat.OPAQUE，即不透明）
        mParams.format = PixelFormat.RGBA_8888;
        // 设置分布位置（距左对齐 + 距顶对齐）
        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        // 设置布局宽/高为自适应
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        // 添加TextView
        mWindowManager.addView(mTextView, mParams);
        // 记录视图已被添加、显示
        isAdded = true;
        isShow = true;
    }

    /**
     * 更新窗口视图
     *
     * @param text 显示内容
     */
    public void updateWindowView(String text) {
        if (isAdded) {
            mTextView.setText(text);
            // 防止某些低版本的手机（或模拟器）按Back键应用退出时，Window窗口被移除无法恢复
            try {
                addView();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 移除窗口视图
     */
    public void removeWindowView() {
        if (!isAdded) {
            return;
        }
        mWindowManager.removeView(mTextView);
        isAdded = false;
        isShow = false;
    }

    /**
     * 开/关窗口视图（隐藏/显示窗口视图）
     */
    public void switchWindowView() {
        if (isAdded) {
            isShow = !isShow;
            mTextView.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
        }
    }

    /**
     * 获取窗口视图的显示状态
     */
    public boolean getWinodwViewShowState() {
        return isAdded && isShow;
    }

    /**
     * 销毁视图容器
     */
    public void destory() {
        removeWindowView();
        mCustomWindowView = null;
    }
}
