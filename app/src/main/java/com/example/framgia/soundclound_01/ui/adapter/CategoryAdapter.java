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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> mListCategories;
    private LayoutInflater mLayoutInflater;
    private ClickListener mClickListener;

    public CategoryAdapter(List<Category> list, Context context, ClickListener clickListener) {
        mListCategories = list;
        mLayoutInflater = LayoutInflater.from(context);
        mClickListener = clickListener;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =
            mLayoutInflater.inflate(R.layout.item_recycleview_category, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        holder.bindData(mListCategories.get(position));
    }

    @Override
    public int getItemCount() {
        return mListCategories != null ? mListCategories.size() : 0;
    }

    public interface ClickListener {
        void onItemClick(View view, int position);
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener {
        @BindView(R.id.text_title_category)
        TextView mCategoryTitle;
        @BindView(R.id.image_category)
        ImageView mImageCategory;

        public CategoryViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        public void bindData(Category category) {
            if (category == null) return;
            mCategoryTitle.setText(category.getCategoryTitle());
            mImageCategory.setImageResource(category.getCategoryImage());
        }

        @Override
        public void onClick(View view) {
            if (mClickListener == null) return;
            mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
