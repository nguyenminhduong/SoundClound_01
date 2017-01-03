package com.example.framgia.soundclound_01.ui.main.category;

import android.support.annotation.NonNull;

import com.example.framgia.soundclound_01.data.model.Category;

import java.util.ArrayList;
import java.util.List;

import static com.example.framgia.soundclound_01.utils.Const.Resource.IMAGE_CATEGORY;

public class CategoryPresenter implements CategoryContract.Presenter {
    private CategoryContract.View mCategoryView;

    public CategoryPresenter(@NonNull CategoryContract.View categoryView) {
        mCategoryView = categoryView;
        mCategoryView.setPresenter(this);
    }

    @Override
    public void start() {
        mCategoryView.start();
    }

    @Override
    public void getCategory(String[] categoryNames, String[] categoryParams) {
        if (categoryNames == null || categoryParams == null) {
            mCategoryView.showGetCategoryError();
            return;
        }
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < categoryNames.length; i++) {
            Category category = new Category();
            category.setCategoryTitle(categoryNames[i]);
            category.setCategoryParam(categoryParams[i]);
            category.setCategoryImage(IMAGE_CATEGORY[i]);
            categories.add(category);
        }
        mCategoryView.showCategory(categories);
    }
}
