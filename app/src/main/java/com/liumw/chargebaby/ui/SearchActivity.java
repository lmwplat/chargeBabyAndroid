package com.liumw.chargebaby.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.liumw.chargebaby.R;
import com.liumw.chargebaby.db.DBManager;
import com.liumw.chargebaby.entity.Charge;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    @ViewInject(R.id.iv_back)
    private ImageView ivBack;
    @ViewInject(R.id.et_search)
    private EditText etSearch;
    @ViewInject(R.id.tv_search)
    private TextView tvSearch;
    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;

    private List<Charge> mList = new ArrayList<>();

    private DbManager mDb;

    private SearchAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        x.view().inject(this);
        initListener();
        DbManager.DaoConfig daoConfig= DBManager.getDaoConfig();
        mDb = x.getDb(daoConfig);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchAdapter();
        recyclerView.setAdapter(mAdapter);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void initListener() {
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info = etSearch.getText().toString();
                if(TextUtils.isEmpty(info)) {
                    return;
                }
                try {
                    //String tmp =
                    mList = mDb.selector(Charge.class).where("area","like","%" + info + "%").or("name","like", "%" + info + "%").findAll();
                    mAdapter.notifyDataSetChanged();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

        @Override
        public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SearchViewHolder(LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_search, parent, false));
        }

        @Override
        public void onBindViewHolder(SearchViewHolder holder, int position) {
            final Charge item = mList.get(position);
            holder.tvName.setText(item.getName());
            holder.tvArea.setText(item.getArea());
            holder.tvDetail.setText(item.getDetail());
            holder.tvAddress.setText(item.getAddress());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("data", item);
                    setResult(101, intent);
                    finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class SearchViewHolder extends RecyclerView.ViewHolder {

            TextView tvName;

            TextView tvArea;

            TextView tvDetail;

            TextView tvAddress;

            public SearchViewHolder(View itemView) {
                super(itemView);
                tvName = (TextView) itemView.findViewById(R.id.tv_name);
                tvArea = (TextView) itemView.findViewById(R.id.tv_area);
                tvDetail = (TextView) itemView.findViewById(R.id.tv_detail);
                tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
            }
        }
    }

}
