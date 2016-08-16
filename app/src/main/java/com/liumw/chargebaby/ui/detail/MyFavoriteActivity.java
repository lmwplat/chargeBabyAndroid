package com.liumw.chargebaby.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.liumw.chargebaby.R;
import com.liumw.chargebaby.base.Application;
import com.liumw.chargebaby.entity.Charge;
import com.liumw.chargebaby.utils.LoginInfoUtils;
import com.liumw.chargebaby.vo.Favorite;
import com.liumw.chargebaby.vo.UserInfo;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_my_favorite)
public class MyFavoriteActivity extends AppCompatActivity {
    private static final String TAG = "MyFavoriteActivity";
    @ViewInject(R.id.my_favorite_back)
    ImageView my_favorite_back;
    @ViewInject(R.id.my_favorite__recycler_view)
    private RecyclerView my_favorite__recycler_view;

    private List<Favorite> mList = new ArrayList<>();
    private SharedPreferences sp;
    private FavoriteAdapter mAdapter;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        my_favorite__recycler_view.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new FavoriteAdapter();
        my_favorite__recycler_view.setAdapter(mAdapter);

        //从共享参数获取数据
        userInfo = LoginInfoUtils.getLoginInfo(this);
        if (userInfo != null){
            mList = userInfo.getFavoriteList();
        }
    }

    @Event(value={R.id.my_favorite_back},type=View.OnClickListener.class)
    private void onClick(View view){
        //必须为private
        switch (view.getId()) {
            case R.id.my_favorite_back:
                Log.e(TAG, "退出");
                finish();
                break;

            default:
                break;
        }
    }


    class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

        @Override
        public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FavoriteViewHolder(LayoutInflater.from(MyFavoriteActivity.this).inflate(R.layout.item_my_favorite, parent, false));
        }

        @Override
        public void onBindViewHolder(FavoriteViewHolder holder, int position) {
            final Favorite item = mList.get(position);
            holder.tvName.setText(item.getName());
            holder.tvArea.setText(item.getArea());
            holder.tvAddress.setText(item.getAddress());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ChargeDetailActivity.class);
                    intent.putExtra("chargeNo", item.getChargeNo());
                    intent.putExtra("isFavorited", true);

                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class FavoriteViewHolder extends RecyclerView.ViewHolder {

            TextView tvName;

            TextView tvArea;

            TextView tvAddress;

            public FavoriteViewHolder(View itemView) {
                super(itemView);
                tvName = (TextView) itemView.findViewById(R.id.tv_my_favorite_name);
                tvArea = (TextView) itemView.findViewById(R.id.tv_my_favorite_area);
                tvAddress = (TextView) itemView.findViewById(R.id.tv_my_favorite_address);
            }
        }
    }
}
