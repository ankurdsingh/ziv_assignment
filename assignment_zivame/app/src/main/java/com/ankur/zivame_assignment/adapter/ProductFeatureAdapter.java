package com.ankur.zivame_assignment.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ankur.zivame_assignment.R;
import com.ankur.zivame_assignment.model.FeatureValuesPOJO;
import com.ankur.zivame_assignment.util.Utility;

import java.util.ArrayList;

/**
 * Created by ankur on 9/5/18.
 * <h2>ProductFeatureAdapter</h2>
 * <p>adapter class to show all product feature in a grid</p>
 */

public class ProductFeatureAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<FeatureValuesPOJO> mDataList;
    private final int VIEW_TYPE_ITEM =0,VIEW_TYPE_DESCRIPTION =1;
//    private GridLayoutManager.SpanSizeLookup spanSizeLookup;
    public ProductFeatureAdapter(Context context, final ArrayList<FeatureValuesPOJO> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
        /*if (spanSizeLookup==null){
            spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (mDataList.get(position).isDescription())
                    return 3;
                    else
                        return 1;
                }
            };
        }*/
    }

    /**
     * <p>ProductFeatureChildVH - have one child with name and one divider
     * will supply initialize view to plug and display </p>
     */
    private class ProductFeatureChildVH extends RecyclerView.ViewHolder{
        TextView childName;
        View divider;
        ProductFeatureChildVH(View itemView) {
            super(itemView);
            childName = itemView.findViewById(R.id.pf_child_name);
            divider = itemView.findViewById(R.id.pf_child_divider);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case VIEW_TYPE_ITEM:
                View convertView = LayoutInflater.from(context).inflate(R.layout.child_view_product_features,parent,false) ;
                return new ProductFeatureChildVH(convertView);
                default:
                    return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProductFeatureChildVH){
            final ProductFeatureChildVH featureInstance = (ProductFeatureChildVH) holder;

            featureInstance.childName.setText(mDataList.get(position).getName());
            if (mDataList.get(position).isSelect()){
                featureInstance.childName.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
            }
            else {
                featureInstance.childName.setTextColor(context.getResources().getColor(android.R.color.holo_blue_dark));
            }
            if (mDataList.get(position).isDivider()) {
                featureInstance.divider.setVisibility(View.VISIBLE);
            }else {
                featureInstance.divider.setVisibility(View.GONE);
            }

            featureInstance.childName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleClickEvent(holder.getAdapterPosition());
                    FeatureValuesPOJO tempInstance = mDataList.get(holder.getAdapterPosition());
                    Utility.showAlert(context,tempInstance.getName(),tempInstance.getDescription(),context.getString(R.string.btn_text));
                    ProductFeatureAdapter.this.notifyDataSetChanged();
                }
            });
        }
    }

    /**
     * <p>toggleClickEvent - use to toggle clicked to un clicked and vice-versa</p>
     * @param position to check set item selected if no item selected
     *                 else unselect all items
     */
    private void toggleClickEvent(int position) {
        if(mDataList.get(position).isSelect()){
            mDataList.get(position).setSelect(false);
        }
        else {
            for (FeatureValuesPOJO tempObj : mDataList){
                tempObj.setSelect(false);
            }
            mDataList.get(position).setSelect(true);
        }
    }

    @Override
    public int getItemCount() {
//        Log.d("TAG","getItemCount -"+mDataList.size());
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position==-1)
            return VIEW_TYPE_DESCRIPTION;
        else
        return VIEW_TYPE_ITEM;
    }

   /* @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        GridLayoutManager viewManager = (GridLayoutManager) recyclerView.getLayoutManager();
        viewManager.setSpanSizeLookup(spanSizeLookup);
        super.onAttachedToRecyclerView(recyclerView);
    }*/
}
