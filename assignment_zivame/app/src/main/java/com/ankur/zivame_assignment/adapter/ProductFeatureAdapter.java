package com.ankur.zivame_assignment.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ankur.zivame_assignment.R;
import com.ankur.zivame_assignment.model.FeatureValuesPOJO;

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
    private GridLayoutManager.SpanSizeLookup spanSizeLookup;
    public ProductFeatureAdapter(Context context, final ArrayList<FeatureValuesPOJO> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
        if (spanSizeLookup==null){
            spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (mDataList.get(position).isDescription())
                    return 3;
                    else
                        return 1;
                }
            };
        }
    }

    /**
     * <p>ProductFeatureChildVH - have one child with name and one divider
     * will supply initialize view to plug and display </p>
     */
    private class ProductFeatureChildVH extends RecyclerView.ViewHolder{
        TextView childName;
        View divider;
        ImageView pointer;
        ProductFeatureChildVH(View itemView) {
            super(itemView);
            childName = itemView.findViewById(R.id.pf_child_name);
            divider = itemView.findViewById(R.id.pf_child_divider);
            pointer = itemView.findViewById(R.id.pf_pointer_up);
        }
    }

    private class ProductDescriptionChildVH extends RecyclerView.ViewHolder{
        TextView description;
        ImageView close;
        ProductDescriptionChildVH(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.pf_description);
            close = itemView.findViewById(R.id.pf_close_description);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case VIEW_TYPE_ITEM:
                View convertView = LayoutInflater.from(context).inflate(R.layout.child_view_product_features,parent,false) ;
                return new ProductFeatureChildVH(convertView);
            case VIEW_TYPE_DESCRIPTION:
                convertView = LayoutInflater.from(context).inflate(R.layout.child_view_description,parent,false) ;
                return new ProductDescriptionChildVH(convertView);
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
                featureInstance.pointer.setVisibility(View.VISIBLE);

            }
            else {
                featureInstance.childName.setTextColor(context.getResources().getColor(android.R.color.holo_blue_dark));
                featureInstance.pointer.setVisibility(View.INVISIBLE);
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
                    addDescription(holder.getAdapterPosition());
                }
            });
        }else if (holder instanceof ProductDescriptionChildVH){
            final ProductDescriptionChildVH descriptionInstance = (ProductDescriptionChildVH) holder;
            descriptionInstance.description.setText(mDataList.get(holder.getAdapterPosition()).getDescription());
            descriptionInstance.close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDataList.remove(holder.getAdapterPosition());
                    for (FeatureValuesPOJO tempObj : mDataList){
                        tempObj.setSelect(false);
                    }
                    description = null;
                    notifyDataSetChanged();
                }
            });
        }


    }

    /**
     * <p>toggleClickEvent - use to toggle clicked to un clicked and vice-versa</p>
     * @param position to check set item selected if no item selected
     *                 else un select all items
     */
    private void toggleClickEvent(int position) {
        if(mDataList.get(position).isSelect())// deselect if views already open
        {
            mDataList.get(position).setSelect(false);
        }
        else { //deselect all selected view and then select current
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
        if (mDataList.get(position).isDescription())
            return VIEW_TYPE_DESCRIPTION;
        else
        return VIEW_TYPE_ITEM;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        GridLayoutManager viewManager = (GridLayoutManager) recyclerView.getLayoutManager();
        viewManager.setSpanSizeLookup(spanSizeLookup);
        super.onAttachedToRecyclerView(recyclerView);
    }


    private FeatureValuesPOJO description=null;

    /**
     * <p>addDescription- method to show the description view inside grid layout
     * it will open description view if not open else will hide view if
     * already open same view</p>
     * @param position of clicked view item from grid
     */
    private void addDescription(int position)
    {
        String details=mDataList.get(position).getDescription();
        if(description!=null)//checking with the temporary object holder if som viw is open or not
        {
            int lastClickedPosition=-1 ; //for checking current clicked item is already clicked or not
            int lastDescriptionPosition=-1; //for removing open view if that is already open
            lastClickedPosition=description.getDescriptionFor();
            lastDescriptionPosition=description.getDescriptionPosition();
            mDataList.remove(lastDescriptionPosition);
            description=null;
            notifyDataSetChanged();
            if(lastClickedPosition==position) //
            {
                return;
            }else if(position>lastDescriptionPosition) //
            {
                position=position-1;
            }
        }
        int tempPosition=(position/3)+1; //checking which row is selected
        int newDesPosition=tempPosition*3; //position on which new description view will add
        boolean isLast=false;
        if(newDesPosition>mDataList.size())//checking inserted position is last or not
        {
            isLast=true;
            newDesPosition=mDataList.size();
        }
        description=new FeatureValuesPOJO();
        description.setDescription(true);
        description.setDescriptionPosition(newDesPosition);
        description.setDescriptionFor(position);
        description.setDescription(details);
        if(isLast) //if adding in last position
        {
            mDataList.add(description);
        }else //else adding description on other position than last
        {
            mDataList.add(newDesPosition,description);
        }
        notifyDataSetChanged();
    }
}
