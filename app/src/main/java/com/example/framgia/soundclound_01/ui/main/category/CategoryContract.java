package com.example.framgia.soundclound_01.ui.main.category;

import com.example.framgia.soundclound_01.BasePresenter;
import com.example.framgia.soundclound_01.BaseView;
import com.example.framgia.soundclound_01.data.model.Category;

import java.util.List;

public interface CategoryContract {
    interface View extends BaseView<Presenter> {
        void showCategory(List<Category> list);
        void showGetCategoryError();
    }

    interface Presenter extends BasePresenter {
        void getCategory(String[] categoryName, String[] categoryParam);
    }
}
