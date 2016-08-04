package com.liumw.chargebaby.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.liumw.chargebaby.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    @ViewInject(R.id.iv_back)
    private ImageView ivBack;
    @ViewInject(R.id.et_search)
    private EditText etSearch;
    @ViewInject(R.id.tv_search)
    private TextView tvSearch;
    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        x.view().inject(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SearchAdapter());
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

    class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

        @Override
        public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SearchViewHolder(LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_search, parent, false));
        }

        @Override
        public void onBindViewHolder(SearchViewHolder holder, int position) {
            holder.t.setText(position + "");
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class SearchViewHolder extends RecyclerView.ViewHolder {

            TextView t;

            public SearchViewHolder(View itemView) {
                super(itemView);
                t = (TextView) itemView.findViewById(R.id.tv_title);
            }
        }
    }

}
