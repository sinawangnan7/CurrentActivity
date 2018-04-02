package com.wangnan.currentactivity.widget;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.wangnan.currentactivity.R;

/**
 * @ClassName: CustomDialog
 * @Description: 自定义对话框
 * @Author wangnan7
 * @Date: 2018/4/1
 */

public class CustomDialog extends DialogFragment {

    private int screenWidth; // 屏幕宽度
    private int screenHeight; // 屏幕高度

    private View rootView;
    private TextView mMessageTV; // 消息
    private TextView mCancleTV; // 取消按钮
    private View mDividerV; // 按钮分割线（取消和确认按钮的分割线）
    private TextView mConfirmTV; // 确认按钮

    private CancleCallback mCancleCallback; // "取消按钮"点击回调
    private ConfirmCallback mConfirmCallback; // "确认按钮"点击回调
    private BackCallback mBackCallback; // "back键"点击回调

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        rootView = inflater.inflate(R.layout.lay_custom_dialog, container, false);
        init();
        initView();
        initData();
        return rootView;
    }

    /**
     * 初始化
     */
    private void init() {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mMessageTV = rootView.findViewById(R.id.tv_message);
        mCancleTV = rootView.findViewById(R.id.tv_cancle);
        mDividerV = rootView.findViewById(R.id.v_divider);
        mConfirmTV = rootView.findViewById(R.id.tv_confirm);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Bundle arguments = getArguments();
        CharSequence message = arguments.getCharSequence("message"); // 消息
        CharSequence cancle = arguments.getCharSequence("cancle"); // 取消按钮
        CharSequence confirm = arguments.getCharSequence("confirm"); // 确认按钮

        // 设置消息
        if (!TextUtils.isEmpty(message)) {
            mMessageTV.setText(message);
        } else {
            mMessageTV.setVisibility(View.GONE);
        }

        // 设置"取消按钮"文本
        if (!TextUtils.isEmpty(cancle)) {
            mCancleTV.setText(cancle);
        } else {
            mCancleTV.setVisibility(View.GONE);
            mDividerV.setVisibility(View.GONE);
        }

        // 设置"确认按钮"文本
        if (!TextUtils.isEmpty(confirm)) {
            mConfirmTV.setText(confirm);
        } else {
            mConfirmTV.setVisibility(View.GONE);
            mDividerV.setVisibility(View.GONE);
        }

        // 设置"取消按钮"点击监听
        if (!TextUtils.isEmpty(cancle)) {
            mCancleTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCancleCallback != null) {
                        mCancleCallback.onCancle();
                    }
                    dismiss();
                }
            });
        }

        // 设置"确认按钮"点击监听
        if (!TextUtils.isEmpty(confirm)) {
            mConfirmTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mConfirmCallback != null) {
                        mConfirmCallback.onConfirm();
                    }
                    dismiss();
                }
            });
        }

        // 设置"Back键"点击监听
        if (mBackCallback != null) {
            getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return mBackCallback.onBackPressed();
                    } else {
                        return false;
                    }
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(screenWidth, screenHeight);
    }


    // "取消按钮"点击回调接口
    public interface CancleCallback {
        void onCancle();
    }

    // "确认按钮"点击回调接口
    public interface ConfirmCallback {
        void onConfirm();
    }

    // Back按键回调
    public interface BackCallback {
        /**
         * @return true：消费点击事件 false ：不消费点击事件
         */
        boolean onBackPressed();
    }

    /**
     * 显示Dialog对话框
     *
     * @param activity
     * @param message         消息文本
     * @param cancle          "取消按钮"文本
     * @param cancleCallback  "取消按钮"点击回调
     * @param confirm         "确认按钮"文本
     * @param confirmCallback "确认按钮"点击回调
     * @return
     */
    public static CustomDialog showInstance(AppCompatActivity activity, CharSequence message, CharSequence cancle, CancleCallback cancleCallback, CharSequence confirm, ConfirmCallback confirmCallback) {
        return showInstance(activity, message, cancle, cancleCallback, confirm, confirmCallback, null);
    }

    /**
     * 显示Dialog对话框
     *
     * @param activity
     * @param message         消息文本
     * @param cancle          "取消按钮"文本
     * @param cancleCallback  "取消按钮"点击回调
     * @param confirm         "确认按钮"文本
     * @param confirmCallback "确认按钮"点击回调
     * @param backCallback    "Back键"点击回调
     * @return
     */
    public static CustomDialog showInstance(AppCompatActivity activity, CharSequence message, CharSequence cancle, CancleCallback cancleCallback, CharSequence confirm, ConfirmCallback confirmCallback, BackCallback backCallback) {
        CustomDialog dialog = new CustomDialog();
        Bundle bundle = new Bundle();
        bundle.putCharSequence("message", message);
        bundle.putCharSequence("cancle", cancle);
        dialog.mCancleCallback = cancleCallback;
        bundle.putCharSequence("confirm", confirm);
        dialog.mConfirmCallback = confirmCallback;
        dialog.mBackCallback = backCallback;
        dialog.setArguments(bundle);
        dialog.show(activity.getSupportFragmentManager(), "");
        return dialog;
    }
}
