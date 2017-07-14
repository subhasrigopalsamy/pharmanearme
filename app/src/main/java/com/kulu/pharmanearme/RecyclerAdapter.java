package com.kulu.pharmanearme;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kulu.pharmanearme.models.PharmacyDetails;

/**
 * Created by Subha on 13/07/2017.
 */

public class RecyclerAdapter extends Adapter<RecyclerAdapter.ViewHolder> {
    private PharmacyDetails.Pharmacy[] mDataset;
    private Context mContext;

    public RecyclerAdapter(Context context) {
        mContext = context;
    }

    public void setData(PharmacyDetails.Pharmacy[] dataset){
        mDataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.listitem_pharmacy, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mPharmaName.setText(mDataset[position].getPlaceTitle());
        holder.mPharmaDist.setText(mContext.getString(R.string.pharma_dist).replace("%d",
                String.valueOf(mDataset[position].getDistance())));
    }


    @Override
    public int getItemCount() {
        return mDataset.length;
    }




    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mPharmaName;
        private TextView mPharmaDist;



        ViewHolder(View itemView) {
            super(itemView);

            mPharmaName = (TextView) itemView.findViewById(R.id.pharmacy_name);
            mPharmaDist =(TextView) itemView.findViewById(R.id.pharmacy_dist);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent i = PharmacyServicesActivity.createIntent(mContext,mDataset[getAdapterPosition()].getPlaceID()
                    ,mDataset[getAdapterPosition()].getPlaceTitle());
                    mContext.startActivity(i);
                }
            });
        }
    }


}
