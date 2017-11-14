package cn.com.ziquan.lib.dialog;

import android.content.Context;

/**
 * Created by Administrator on 2017/11/14.
 */

public class DialogAgent {

    // 上下文
    private Context mContext;

    // 实际的LibDialog对象
    private LibDialog mLibDialog;


    /**
     * 获得DialogAgent实例（不是单例模式）
     *
     * @param context
     * @return
     */
    public static DialogAgent getInstance(Context context) {
        return new DialogAgent(context);
    }

    /**
     * 私有构造方法
     */
    private DialogAgent(Context context) {
        mContext = context;
    }

    /**
     * 创建指定类型的对话框
     *
     * @param type
     * @return
     */
    public DialogAgent create(int type) {
        mLibDialog = new LibDialog(mContext, this);
        mLibDialog.setType(type);
        return this;
    }

    /**
     * 设置对话框显示的内容
     *
     * @param contentText
     * @return
     */
    public DialogAgent setContent(String contentText) {
        mLibDialog.setContent(contentText);
        return this;
    }

    /**
     * 设置确认按钮文本
     *
     * @param text
     * @return
     */
    public DialogAgent setPositiveBtn(String text) {
        return this.setPositiveBtn(text, null);
    }

    /**
     * 设置确认按钮点击事件监听器
     *
     * @param listener
     * @return
     */
    public DialogAgent setPositiveBtn(LibDialog.OnClickListener listener) {
        return this.setPositiveBtn(null, listener);
    }

    /**
     * 设置确认按钮文本和点击事件监听器
     *
     * @param text
     * @param listener
     * @return
     */
    public DialogAgent setPositiveBtn(String text, LibDialog.OnClickListener listener) {
        mLibDialog.setPositiveBtn(text, listener);
        return this;
    }

    /**
     * 设置取消按钮文本
     *
     * @param text
     * @return
     */
    public DialogAgent setNegativeBtn(String text) {
        return this.setNegativeBtn(text, null);
    }

    /**
     * 设置取消按钮的点击事件监听器
     *
     * @param listener
     * @return
     */
    public DialogAgent setNegativeBtn(LibDialog.OnClickListener listener) {
        return this.setNegativeBtn(null, listener);
    }

    /**
     * 设置取消按钮文本和点击事件监听器
     *
     * @param text
     * @param listener
     * @return
     */
    public DialogAgent setNegativeBtn(String text, LibDialog.OnClickListener listener) {
        mLibDialog.setNegativeBtn(text, listener);
        return this;
    }

    /**
     * 显示对话框
     *
     * @return
     */
    public DialogAgent showDialog() {
        mLibDialog.showDialog();
        return this;
    }

    /**
     * 更新对话框内容（需要运行在UI线程）
     *
     * @param contentText
     */
    public void updateContent(String contentText) {
        mLibDialog.updateContext(contentText);
    }

    /**
     * 隐藏对话框
     */
    public void dismiss() {
        if (mLibDialog != null) {
            mLibDialog.dismiss();
        }
    }

    /**
     * 隐藏并销毁对话框
     */
    public void dismissAndDestroy() {
        if (mLibDialog != null) {
            mLibDialog.dismiss();
            mLibDialog = null;
        }
    }
}
