package cn.com.ziquan.lib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/11/14.
 */

public class LibDialog extends Dialog {

    /**
     * 警告对话框
     */
    public static final int DIALOG_TYPE_ALERT = 0x01;

    /**
     * 确认对话框
     */
    public static final int DIALOG_TYPE_CONFIRM = 0x02;

    /**
     * 等待对话框
     */
    public static final int DIALOG_TYPE_WAITING = 0x03;

    /**
     * 确认按钮代码
     */
    public static final int BTN_POSITIVE = 0x01;

    /**
     * 取消按钮代码
     */
    public static final int BTN_NEGATIVE = 0x02;

    // 对话框类型
    private int mDialogType = -1;

    // 对话框显示的内容
    private String mContentText;

    // 确认按钮文本
    private String mPositiveBtnText;

    // 确认按钮点击事件监听器
    private OnClickListener mPositiveBtnClickListener;

    // 取消按钮文本
    private String mNegativeBtnText;

    // 取消按钮点击事件监听器
    private OnClickListener mNegativeBtnClickListener;

    // 对话框内容的显示控件
    private TextView tvContentText;

    // 确认按钮
    private Button btnPositive;

    // 取消按钮
    private Button btnNegative;

    // 代理对象
    private DialogAgent mDialogAgent;

    /**
     * 构造方法
     *
     * @param context
     */
    protected LibDialog(@NonNull Context context, DialogAgent agent) {
        super(context, R.style.BaseDialogStyle);
        this.mDialogAgent = agent;

    }

    /**
     * 这个方法在show方法之后调用
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 根据对话框类型设置对应的布局
        switch (mDialogType) {
            case LibDialog.DIALOG_TYPE_ALERT:
                setContentView(R.layout.layout_alert_dialog);
                tvContentText = (TextView) findViewById(R.id.tv_content);
                btnPositive = (Button) findViewById(R.id.btn_positive);
                btnNegative = null;
                break;
            case LibDialog.DIALOG_TYPE_CONFIRM:
                setContentView(R.layout.layout_confirm_dialog);
                tvContentText = (TextView) findViewById(R.id.tv_content);
                btnPositive = (Button) findViewById(R.id.btn_positive);
                btnNegative = (Button) findViewById(R.id.btn_negative);
                break;
            case LibDialog.DIALOG_TYPE_WAITING:
                setContentView(R.layout.layout_waiting_dialog);
                tvContentText = (TextView) findViewById(R.id.tv_content);
                btnPositive = null;
                btnNegative = null;
                break;
        }
        //点击对话框以外区域不能使对话框消失
        setCanceledOnTouchOutside(false);
        // 设置点击返回键不能使对话框消失
        setCancelable(false);

        // 设置内容
        if (tvContentText != null) {
            if (mContentText != null) {
                tvContentText.setText(mContentText);
            }
        }

        // 设置确认按钮
        if (btnPositive != null) {
            if (mPositiveBtnText != null) {
                btnPositive.setText(mPositiveBtnText);
            }
            btnPositive.setOnClickListener(mDefaultBtnClickListener);
        }

        // 设置取消按钮
        if (btnNegative != null) {
            if (mNegativeBtnText != null) {
                btnNegative.setText(mNegativeBtnText);
            }
            btnNegative.setOnClickListener(mDefaultBtnClickListener);
        }
    }

    /**
     * 设置对话框类型
     *
     * @param type 对话框的类型，值可能为：
     *             LibDialog.DIALOG_TYPE_ALERT；
     *             LibDialog.DIALOG_TYPE_CONFIRM；
     *             LibDialog.DIALOG_TYPE_PROCESS
     * @return
     */
    protected LibDialog setType(int type) {
        this.mDialogType = type;
        return this;
    }

    /**
     * 设置要显示的对话框内容
     *
     * @param contentText
     * @return
     */
    protected LibDialog setContent(String contentText) {
        this.mContentText = contentText;
        return this;
    }

    /**
     * 更新对话框内容（注意：需要运行在UI线程）
     *
     * @param contentText
     */
    protected void updateContext(String contentText) {
        mContentText = contentText;
        if (tvContentText != null) {
            tvContentText.setText(mContentText);
        }
    }

    /**
     * 设置确认按钮文本和点击事件监听器
     *
     * @param text
     * @param listener
     * @return
     */
    protected LibDialog setPositiveBtn(String text, OnClickListener listener) {
        this.mPositiveBtnText = text;
        this.mPositiveBtnClickListener = listener;
        return this;
    }

    /**
     * 设置取消按钮文本和点击事件监听器
     *
     * @param text
     * @param listener
     * @return
     */
    protected LibDialog setNegativeBtn(String text, OnClickListener listener) {
        this.mNegativeBtnText = text;
        this.mNegativeBtnClickListener = listener;
        return this;
    }

    /**
     * 显示对话框
     *
     * @return
     */
    protected LibDialog showDialog() {
        this.show();
        return this;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        this.mDialogAgent = null;
    }

    /**
     * 默认的按钮点击事件：关闭对话框
     */
    private View.OnClickListener mDefaultBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (R.id.btn_positive == v.getId()) {
                if (mPositiveBtnClickListener != null) {
                    mPositiveBtnClickListener.onClick(mDialogAgent, BTN_POSITIVE);
                } else {
                    LibDialog.this.dismiss();
                }
            } else if (R.id.btn_negative == v.getId()) {
                if (mNegativeBtnClickListener != null) {
                    mNegativeBtnClickListener.onClick(mDialogAgent, BTN_NEGATIVE);
                } else {
                    LibDialog.this.dismiss();
                }
            }
        }
    };

    /**
     * LibDialog底部按钮点击事件监听器
     */
    public interface OnClickListener {
        /**
         * LibDialog底部按钮被点击时回调方法.
         *
         * @param agent
         * @param which
         */
        void onClick(DialogAgent agent, int which);
    }
}
