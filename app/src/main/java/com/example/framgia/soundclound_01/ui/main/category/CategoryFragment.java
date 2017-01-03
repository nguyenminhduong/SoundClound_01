package com.example.framgia.soundclound_01.ui.main.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.framgia.soundclound_01.R;
import com.example.framgia.soundclound_01.data.model.Category;
import com.example.framgia.soundclound_01.ui.adapter.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryFragment extends Fragment implements CategoryContract.View {
    @BindView(R.id.recycler_view_category)
    RecyclerView mRecyclerViewCategory;
    private CategoryContract.Presenter mCategoryPresenter;
    private CategoryAdapter mCategoryAdapter;
    private List<Category> mCategories = new ArrayList<>();
    private String[] mCategoryName;
    private String[] mCategoryParam;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        setPresenter(new CategoryPresenter(this));
        ButterKnife.bind(this, view);
        mCategoryPresenter.start();
        return view;
    }

    @Override
    public void setPresenter(CategoryContract.Presenter presenter) {
        mCategoryPresenter = presenter;
    }

    @Override
    public void start() {
        mCategoryAdapter = new CategoryAdapter(mCategories, getActivity());
        mRecyclerViewCategory.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager =
            new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.item_col));
        mRecyclerViewCategory.setLayoutManager(gridLayoutManager);
        mRecyclerViewCategory.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewCategory.setAdapter(mCategoryAdapter);
        mCategoryName = getResources().getStringArray(R.array.category_name);
        mCategoryParam = getResources().getStringArray(R.array.category_param);
        mCategoryPresenter.getCategory(mCategoryName, mCategoryParam);
    }

    @Override
    public void showCategory(List<Category> list) {
        if (list == null) return;
        mCategories.addAll(list);
        mCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showGetCategoryError() {
    }
}
