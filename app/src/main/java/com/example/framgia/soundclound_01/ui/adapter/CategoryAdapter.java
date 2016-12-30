package com.example.framgia.soundclound_01.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framgia.soundclound_01.R;
import com.example.framgia.soundclound_01.data.model.Category;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private List<Category> mListCategories;
    private LayoutInflater mLayoutInflater;

    public CategoryAdapter(List<Category> list, Context context) {
        mListCategories = list;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =
            mLayoutInflater.inflate(R.layout.item_recycleview_category, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bindData(mListCategories.get(position));
    }

    @Override
    public int getItemCount() {
        return mListCategories != null ? mListCategories.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_title_category)
        TextView mCategoryTitle;
        @BindView(R.id.image_category)
        ImageView mImageCategory;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindData(Category category) {
            if (category == null) return;
            mCategoryTitle.setText(category.getCategoryTitle());
            mImageCategory.setImageResource(category.getCategoryImage());
        }
    }
}
