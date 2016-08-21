package com.liumw.chargebaby.ui.indicate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.liumw.chargebaby.R;
import com.liumw.chargebaby.base.AppConstants;
import com.liumw.chargebaby.base.ChargeApplication;
import com.liumw.chargebaby.base.ChargeConstants;
import com.liumw.chargebaby.entity.Charge;
import com.liumw.chargebaby.entity.CommentVo;
import com.liumw.chargebaby.entity.ReplyVo;
import com.liumw.chargebaby.myviews.MyListView;
import com.liumw.chargebaby.ui.detail.LoginActivity;
import com.liumw.chargebaby.ui.detail.MyFavoriteActivity;
import com.liumw.chargebaby.utils.DateUtils;
import com.liumw.chargebaby.utils.ListViewUtils;
import com.liumw.chargebaby.vo.Json;
import com.liumw.chargebaby.vo.UserInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.fragment_dianpin)
public class DianpinFragment extends Fragment {
    private static final String TAG = "DianpinFragment";


    private ChargeApplication app;
    private UserInfo userInfo;
    private Context context;
    private String chargeNo;
    private Charge charge;
    Json json = new Json();

    @ViewInject(R.id.ll_comment)
    private View mCommentView;
    @ViewInject(R.id.my_dianping_recycler_view)
    private RecyclerView my_dianping_recycler_view;
    @ViewInject(R.id.review_comment_edit)
    private EditText review_comment_edit;
    @ViewInject(R.id.bt_comment_submit)
    private Button bt_comment_submit;

    private CommentAdapter mAdapter;


    private List<CommentVo> commentVoList = new ArrayList<CommentVo>();
    private List<ReplyVo> replyVoList = new ArrayList<ReplyVo>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return x.view().inject(this,inflater,container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chargeNo = ((IndicatorFragmentActivity)getActivity()).getChargeNo();


        my_dianping_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CommentAdapter();
        my_dianping_recycler_view.setAdapter(mAdapter);


        findAllComment();
    }

    @Event(type = View.OnClickListener.class,value = R.id.bt_comment_submit)
    private void comentSubmitOnClick(View view){
        String str = review_comment_edit.getText().toString();
        addCommet(str);


    }

    /**
     * 添加评论
     * @param info
     */
    private void addCommet(String info) {
        app = (ChargeApplication)getActivity().getApplication();
        userInfo = app.getUserInfo();
        String url = AppConstants.SERVER + AppConstants.ACTION_ADD_COMMENT;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("authorId", userInfo.getId().toString());
        params.addBodyParameter("chargeNo", chargeNo);
        params.addBodyParameter("info", info);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                json = JSON.parseObject(result, Json.class);
                if (!json.isSuccess()){
                    Log.e(TAG, "评价失败" + json.getMsg());
                }else{
                    commentVoList.clear();
                    String jsonString = JSON.toJSONString(json.getObj());
                    List<CommentVo> all = JSON.parseArray(jsonString, CommentVo.class);
                    for (CommentVo c : all){
                        commentVoList.add(c);
                    }
                    mAdapter.notifyDataSetChanged();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(review_comment_edit,InputMethodManager.SHOW_FORCED);
                    imm.hideSoftInputFromWindow(review_comment_edit.getWindowToken(), 0); //强制隐藏键盘
                    review_comment_edit.setText(null);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(TAG, "onError" );
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {

                Log.e(TAG, "onCancelled" );
            }

            @Override
            public void onFinished() {
                Log.e(TAG, "onFinished" );
            }
        });

    }

    private void findAllComment(){
        String url = AppConstants.SERVER + AppConstants.ACTION_FIND_ALL_COMMENT;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("chargeNo", chargeNo);
        x.http().post(params, new Callback.CommonCallback<String>() {
        @Override
        public void onSuccess(String result) {
            json = JSON.parseObject(result, Json.class);
            if (!json.isSuccess()){
                Log.e(TAG, "登录失败" + json.getMsg());
            }else{

            String jsonString = JSON.toJSONString(json.getObj());
            List<CommentVo> all = JSON.parseArray(jsonString, CommentVo.class);
            for (CommentVo c : all){
                commentVoList.add(c);
            }
            mAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            Log.e(TAG, "onError" );
        }

        @Override
        public void onCancelled(Callback.CancelledException cex) {

            Log.e(TAG, "onCancelled" );
        }

        @Override
        public void onFinished() {
            Log.e(TAG, "onFinished" );
        }
    });
    }

    public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

        private final int COMMENT_VIEW = 1000;
        private final int REPLY_VIEW = 1001;

        public CommentAdapter() {
        }

        @Override
        public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CommentViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_comment, parent, false));
        }

        @Override
        public void onBindViewHolder(final CommentViewHolder holder, int position) {

            CommentVo item = commentVoList.get(position);
            holder.tvCommentAuthor.setText(item.getAuthor());
            holder.tvCommentInfo.setText(item.getInfo());
            holder.tvCommentCreatedAt.setText(DateUtils.formatDateSimple(item.getCreateTime()));

           /* if (item.getReplyVoList().size() > 0){
                holder.llReplyLayout.setVisibility(View.VISIBLE);
                ReplyAdapter adapter = new ReplyAdapter(getActivity());
                holder.lvCommentReply.setAdapter(adapter);
                ListViewUtils.setListViewHeightBasedOnChildren(holder.lvCommentReply);

            }*/

        }

        @Override
        public int getItemCount() {
            return commentVoList.size();
        }

        class CommentViewHolder extends RecyclerView.ViewHolder {

            ImageView tvCommentPortrait;

            TextView tvCommentAuthor;

            TextView tvCommentInfo;

            TextView tvCommentCreatedAt;

            LinearLayout llReplyLayout;

            MyListView lvCommentReply;

            public CommentViewHolder(View itemView) {
                super(itemView);
                tvCommentPortrait = (ImageView) itemView.findViewById(R.id.tv_comment_portrait);
                tvCommentAuthor = (TextView) itemView.findViewById(R.id.tv_comment_author);
                tvCommentInfo = (TextView) itemView.findViewById(R.id.tv_comment_info);
                tvCommentCreatedAt = (TextView) itemView.findViewById(R.id.tv_comment_created_at);
                llReplyLayout = (LinearLayout) itemView.findViewById(R.id.ll_reply_layout);
                lvCommentReply = (MyListView) itemView.findViewById(R.id.lv_comment_reply);


            }
        }

        public final class ViewHolder{
            public TextView tvCommentReplyAuthor;
            public TextView tvCommentReplyInfo;
        }

        public class ReplyAdapter extends BaseAdapter {

            private LayoutInflater mInflater;


            public ReplyAdapter(Context context){
                this.mInflater = LayoutInflater.from(context);
            }
            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return replyVoList.size();
            }


            @Override
            public Object getItem(int arg0) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public long getItemId(int arg0) {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                ViewHolder holder = null;
                if (convertView == null) {
                    holder=new ViewHolder();
                    convertView = mInflater.inflate(R.layout.item_comment_reply, null);
                    holder.tvCommentReplyAuthor = (TextView)convertView.findViewById(R.id.tv_comment_reply_author);
                    holder.tvCommentReplyInfo = (TextView)convertView.findViewById(R.id.tv_comment_reply_info);


                    convertView.setTag(holder);

                }else {

                    holder = (ViewHolder)convertView.getTag();
                }


                holder.tvCommentReplyAuthor.setText(replyVoList.get(position).getAuthor());
                holder.tvCommentReplyInfo.setText(replyVoList.get(position).getInfo());

                return convertView;
            }

        }
    }




}
