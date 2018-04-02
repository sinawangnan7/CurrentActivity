package com.wangnan.currentactivity.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wangnan.currentactivity.R;
import com.wangnan.currentactivity.service.MAccessibilityService;
import com.wangnan.currentactivity.ui.window.WindowViewContainer;
import com.wangnan.currentactivity.util.ActivityUtil;
import com.wangnan.currentactivity.util.DialogUtil;
import com.wangnan.currentactivity.util.PermissionUtil;


/**
 * @ClassName: MainActivity
 * @Description: 主界面
 * @Author wangnan7
 * @Date: 2018/4/1
 */

public class MainActivity extends AppCompatActivity {

    /**
     * 当前Activity静态引用
     */
    public static MainActivity mActivity;

    private LinearLayout mSwitchLL; // 打开/关闭悬浮窗（根布局）
    private TextView mSwitchTV; // 打开/关闭悬浮窗（提示文本）

    private View mHintTV; // 权限提示

    private LinearLayout mOverlayLL; // 悬浮窗权限提示（根布局）
    private TextView mOverlayTV; // 悬浮窗权限（提示文本）
    private SwitchCompat mOverlaySC; // 悬浮窗权限（开关按钮）

    private LinearLayout mNotifyLL; // 通知栏权限提示（根布局）
    private TextView mNotifyTV; // 通知栏权限（提示文本）
    private SwitchCompat mNotifySC; // 通知栏权限（开关按钮）

    private View mCloseV; // 关闭辅助服务按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initViewListener();
        initData();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mSwitchLL = findViewById(R.id.ll_switch);
        mSwitchTV = findViewById(R.id.tv_switch);
        mHintTV = findViewById(R.id.tv_hint);
        mOverlayLL = findViewById(R.id.ll_overlay);
        mOverlayTV = findViewById(R.id.tv_overlay);
        mOverlaySC = findViewById(R.id.sc_overlay);
        mNotifyLL = findViewById(R.id.ll_notify);
        mNotifyTV = findViewById(R.id.tv_notify);
        mNotifySC = findViewById(R.id.sc_notify);
        mCloseV = findViewById(R.id.tv_close);
    }

    /**
     * 初始化视图监听器
     */
    private void initViewListener() {
        // "显示/关闭悬浮窗"点击监听
        mSwitchLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchWindow();
            }
        });
        // "悬浮窗权限"点击监听
        mOverlaySC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.turnToOverlayPermission(MainActivity.this);
            }
        });
        // "通知栏权限"点击监听
        mNotifySC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.turnToNotifyPermission(MainActivity.this);
            }
        });
        // "关闭辅助服务"点击监听
        mCloseV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.toAuthAccessibilityService(MainActivity.this);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        // 当前Activity静态引用赋值
        mActivity = this;
        // 检查是否有悬浮窗权限，没有给出弹框提醒
        if (!PermissionUtil.hasOverlayPermission(this)) {
            DialogUtil.showOverlayAlertDialog(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    /**
     * 更新界面UI
     */
    public void updateUI() {
        // 设置"打开/关闭悬浮窗"提示
        boolean isShow = WindowViewContainer.getInstance(this).getWinodwViewShowState();
        mSwitchTV.setText(isShow ? R.string.string_service_close : R.string.string_service_start);
        // 设置"悬浮窗权限"显示样式（6.0以下默认不需要请求该权限）
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mOverlayLL.setVisibility(View.GONE);
        } else {
            boolean overlay = PermissionUtil.hasOverlayPermission(this);
            mOverlayTV.setText(Html.fromHtml(getString(overlay ? R.string.string_service_overlay_tip2 : R.string.string_service_overlay_tip1)));
            mOverlaySC.setChecked(overlay);
            mOverlayLL.setVisibility(View.VISIBLE);
        }
        // 设置"通知权限"显示样式（4.4以下默认不需要请求该权限）
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            mNotifyLL.setVisibility(View.GONE);
            mHintTV.setVisibility(View.GONE);
        } else {
            boolean notify = PermissionUtil.hasNotifyPermission(this);
            mNotifyTV.setText(Html.fromHtml(getString(notify ? R.string.string_service_notify_tip2 : R.string.string_service_notify_tip1)));
            mNotifySC.setChecked(notify);
            mNotifyLL.setVisibility(View.VISIBLE);
        }
        // 设置"关闭辅助服务按钮"的显示和隐藏
        if (PermissionUtil.getServiceState(this, MAccessibilityService.SERVCE_NAME)){
            mCloseV.setVisibility(View.VISIBLE);
        } else {
            mCloseV.setVisibility(View.GONE);
        }
    }

    /**
     * 显示/隐藏窗口视图
     */
    private void switchWindow() {
        // 检查是否有悬浮窗权限，没有给出弹框提醒
        if (!PermissionUtil.hasOverlayPermission(this)) {
            DialogUtil.showOverlayAlertDialog(this);
            return;
        }
        // 检查用户是否已授权开启"辅助功能"
        if (!PermissionUtil.getServiceState(this, MAccessibilityService.SERVCE_NAME)) {
            DialogUtil.showAccessibilityServiceAlertDialog(this, "【打开悬浮窗】需要去【辅助功能】开启", "取消", "去开启");
            return;
        }
        // 切换视图显示状态
        WindowViewContainer.getInstance(this).switchWindowView();
        updateUI();
    }

    @Override
    protected void onDestroy() {
        // 当前Activity静态引用赋空
        mActivity = null;
        super.onDestroy();
    }

}
