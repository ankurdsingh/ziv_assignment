package com.ankur.zivame_assignment.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ankur.zivame_assignment.activity.ProductFeature;
import com.ankur.zivame_assignment.callbacks.ProductFeatureCallback;
import com.ankur.zivame_assignment.callbacks.ProgressBarCallback;
import com.ankur.zivame_assignment.model.FeatureValuesPOJO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.ankur.zivame_assignment.Constants.ApplicationConstant.JSON_DESCRIPTION_KEY;
import static com.ankur.zivame_assignment.Constants.ApplicationConstant.JSON_FILE_NAME;
import static com.ankur.zivame_assignment.Constants.ApplicationConstant.JSON_NAME_KEY;
import static com.ankur.zivame_assignment.Constants.ApplicationConstant.JSON_VALUES_KEY;
import static com.ankur.zivame_assignment.Constants.ApplicationConstant.PRODUCT_FEATURE_GRID_COLUMNS;

/**
 * Created by ankur on 9/5/18.
 * <h2>ProductFeaturePresenter</h2>
 * <p>Helper class of ProductFeature activity all the business logic will place here</p>
 */

public class ProductFeaturePresenter {
    private static final String TAG = ProductFeaturePresenter.class.getSimpleName();
    private Context context;
    private ProductFeatureCallback productFeatureCallback;
    private ProgressBarCallback progressBarCallback;
    public ProductFeaturePresenter(Context context) {
        this.context =context;
        this.productFeatureCallback = (ProductFeature)context;
        this.progressBarCallback = (ProductFeature)context;
    }

    /**
     * <p>getData -  method will start background thread which read data and
     * parse and return a list of product features</p>
     */
    public void getData(){
        new ProductFeaturePresenter.ReadFile().execute(JSON_FILE_NAME);
    }

    /**
     * <p>ReadFile class will be execute in background and use to read json data from file - Features.json
     * after reading data. It will be save in String then from String parse it to json object
     * and then convert this json object it to FeatureValuesPOJO type of objects and add it to featuresList.
     * </p>
     */
    private class ReadFile extends AsyncTask<String,Void,ArrayList<FeatureValuesPOJO>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarCallback.showProgressBar();
        }

        @Override
        protected ArrayList<FeatureValuesPOJO> doInBackground(String... strings) {
            String json = null;
            try {
                InputStream jsonInputStream = context.getAssets().open(JSON_FILE_NAME);
                int size = jsonInputStream.available();
                byte[] buffer = new byte[size];
                jsonInputStream.read(buffer);
                jsonInputStream.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Log.d(TAG,"JSON LOAD - "+json);
            //return json;
            ArrayList<FeatureValuesPOJO> featuresList = new ArrayList<>();

            if (json == null){
                return featuresList;
            }
            try {
                JSONObject obj = new JSONObject(json);
                JSONArray valuesJsonArray = obj.getJSONArray(JSON_VALUES_KEY);
                for (int valueIndex = 0 ; valueIndex<valuesJsonArray.length(); valueIndex++){
                    JSONObject valuesEntity = valuesJsonArray.getJSONObject(valueIndex);
                    final  FeatureValuesPOJO featureValuesPOJO = new FeatureValuesPOJO();
                    featureValuesPOJO.setName(valuesEntity.getString(JSON_NAME_KEY));
                    featureValuesPOJO.setDescription(valuesEntity.getString(JSON_DESCRIPTION_KEY));
                    featuresList.add(featureValuesPOJO);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d(TAG,"featuresList - "+featuresList);
            return featuresList;
        }

        @Override
        protected void onPostExecute(ArrayList<FeatureValuesPOJO> featuresList) {
            super.onPostExecute(featuresList);
            //Rearranging list for showing/hiding divider
            for (int listIndex =0 ; listIndex<featuresList.size() ; listIndex++){
                if((listIndex+1)%PRODUCT_FEATURE_GRID_COLUMNS==0 || (listIndex+1)==featuresList.size()){
                    featuresList.get(listIndex).setDivider(false);
                }else {
                    featuresList.get(listIndex).setDivider(true);
                }
            }
            productFeatureCallback.featureList(featuresList);
            progressBarCallback.hideProgressBar();
        }
    }
}
