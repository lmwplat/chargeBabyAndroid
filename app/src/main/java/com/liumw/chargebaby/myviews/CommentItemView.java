package com.liumw.chargebaby.myviews;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.liumw.chargebaby.R;
import com.liumw.chargebaby.entity.CommentVo;
import com.liumw.chargebaby.entity.ReplyVo;

/**
 * Created by liumw on 2016/8/18 0018.
 */
public class CommentItemView extends LinearLayout implements View.OnClickListener{
    private int mPosition;
    private CommentVo mData;

  //  private ImageView mPortraitView;
    private TextView tv_comment_info;
    private TextView tv_comment_author;
    private TextView tv_comment_created_at;
    private LinearLayout reply_layout;
    private View mMoreView;

    private PopupWindow mMorePopupWindow;
    private int mShowMorePopupWindowWidth;
    private int mShowMorePopupWindowHeight;

    private OnCommentListener mCommentListener;

    public CommentItemView(Context context) {
        super(context);
    }

    public CommentItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public interface OnCommentListener {
        void onComment(int position);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        //mPortraitView = (ImageView) findViewById(R.id.tv_comment_portrait);
        tv_comment_info = (TextView) findViewById(R.id.tv_comment_info);
        tv_comment_author = (TextView) findViewById(R.id.tv_comment_author);
        tv_comment_created_at = (TextView) findViewById(R.id.tv_comment_created_at);
        reply_layout = (LinearLayout) findViewById(R.id.ll_reply_layout);
        mMoreView = findViewById(R.id.tv_comment_more_btn);
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public void setCommentListener(OnCommentListener l) {
        this.mCommentListener = l;
    }

    public void setData(CommentVo data) {

        mData = data;

        //mPortraitView.setImageResource(data.getPortraitId());
        tv_comment_author.setText(data.getAuthor());
        tv_comment_info.setText(data.getInfo());

        updateComment();

        mMoreView.setOnClickListener(this);
    }

    /**
     * 弹出点赞和评论框
     *
     * @param moreBtnView
     */
    private void showMore(View moreBtnView) {

        if (mMorePopupWindow == null) {

            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View content = li.inflate(R.layout.layout_more, null, false);

            mMorePopupWindow = new PopupWindow(content, ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mMorePopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mMorePopupWindow.setOutsideTouchable(true);
            mMorePopupWindow.setTouchable(true);

            content.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            mShowMorePopupWindowWidth = content.getMeasuredWidth();
            mShowMorePopupWindowHeight = content.getMeasuredHeight();

            View parent = mMorePopupWindow.getContentView();

           // TextView like = (TextView) parent.findViewById(R.id.like);
            TextView comment = (TextView) parent.findViewById(R.id.comment);

            // 点赞的监听器
            comment.setOnClickListener(this);
        }

        if (mMorePopupWindow.isShowing()) {
            mMorePopupWindow.dismiss();
        } else {
            int heightMoreBtnView = moreBtnView.getHeight();

            mMorePopupWindow.showAsDropDown(moreBtnView, -mShowMorePopupWindowWidth,
                    -(mShowMorePopupWindowHeight + heightMoreBtnView) / 2);
        }
    }

    private void updateComment() {
        if (mData.hasReply()) {

            tv_comment_created_at.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.bg_top);
            reply_layout.removeAllViews();
            reply_layout.setVisibility(View.VISIBLE);

            for (ReplyVo c : mData.getReplyVoList()) {
                TextView t = new TextView(getContext());
                t.setLayoutParams(new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)));
                t.setBackgroundColor(getResources().getColor(R.color.colorCommentLayoutBg));
                t.setTextSize(16);
                t.setPadding(5, 2, 0, 3);
                t.setLineSpacing(3, (float) 1.5);
                t.setText(c.getInfo());
                reply_layout.addView(t);
            }
        } else {
            tv_comment_created_at.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            reply_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.tv_comment_more_btn) {
            showMore(v);
        } else if (id == R.id.comment) {
            if (mCommentListener != null) {
                mCommentListener.onComment(mPosition);

                if (mMorePopupWindow != null && mMorePopupWindow.isShowing()) {
                    mMorePopupWindow.dismiss();
                }
            }
        }
    }

    public int getPosition() {
        return mPosition;
    }

    public void addComment() {
        updateComment();
    }
}
