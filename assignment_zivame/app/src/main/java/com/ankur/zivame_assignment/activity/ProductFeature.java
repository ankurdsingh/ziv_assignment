package com.ankur.zivame_assignment.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.ankur.zivame_assignment.R;
import com.ankur.zivame_assignment.adapter.ProductFeatureAdapter;
import com.ankur.zivame_assignment.callbacks.ProductFeatureCallback;
import com.ankur.zivame_assignment.callbacks.ProgressBarCallback;
import com.ankur.zivame_assignment.model.FeatureValuesPOJO;
import com.ankur.zivame_assignment.presenter.ProductFeaturePresenter;

import java.util.ArrayList;

import static com.ankur.zivame_assignment.Constants.ApplicationConstant.PRODUCT_FEATURE_GRID_COLUMNS;

/**
 * Created by ankur on 8/5/18.
 * <h1>ProductFeature</h1>
 * <p>class will display product features list and their description
 * on click of specific feature</p>
 */

public class ProductFeature extends AppCompatActivity implements ProgressBarCallback,ProductFeatureCallback{
    private ProductFeaturePresenter productFeaturePresenter;
    private ProgressBar progressBarLoadingFeture;
    private ArrayList<FeatureValuesPOJO> featureDataList;
    private ProductFeatureAdapter featuresAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_feature);
        init();
        initView();
    }

    /**
     * <p>init- method to initialize all data member of the class</p>
     */
    private void init() {
        featureDataList = new ArrayList<>();
        productFeaturePresenter = new ProductFeaturePresenter(ProductFeature.this);
    }

    /**
     * <p>initView - method to initialize add view related variable in activity</p>
     */
    private void initView() {
        progressBarLoadingFeture = findViewById(R.id.pf_progress_bar);
        RecyclerView recyclerViewProductFeature = findViewById(R.id.pf_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(ProductFeature.this,PRODUCT_FEATURE_GRID_COLUMNS);
        recyclerViewProductFeature.setLayoutManager(layoutManager);
        featuresAdapter = new ProductFeatureAdapter(ProductFeature.this,featureDataList);
        recyclerViewProductFeature.setAdapter(featuresAdapter);
        productFeaturePresenter.getData();
    }

    @Override
    public void showProgressBar() {
        progressBarLoadingFeture.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBarLoadingFeture.setVisibility(View.GONE);

    }

    @Override
    public void featureList(ArrayList<FeatureValuesPOJO> featuresList) {
        this.featureDataList.clear();
        this.featureDataList.addAll(featuresList);
//        Log.d("TAG","getItemCount -"+featuresList.size());
        featuresAdapter.notifyItemInserted(0);
    }
}
