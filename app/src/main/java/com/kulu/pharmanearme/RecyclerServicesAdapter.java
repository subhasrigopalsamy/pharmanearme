package com.kulu.pharmanearme;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kulu.pharmanearme.models.PharmacyServicesDetails;

/**
 * Created by Subha on 13/07/2017.
 */

public class RecyclerServicesAdapter extends Adapter<RecyclerServicesAdapter.ViewHolder> {
    private PharmacyServicesDetails.Services[] mDataset;
    private Context mContext;

    public RecyclerServicesAdapter(Context context) {
        mContext = context;
    }

    public void setData(PharmacyServicesDetails.Services[] dataset){
        mDataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.listitem_services, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mServiceName.setText(mDataset[position].getServiceName());
        //holder.mServiceIcon.setImageBitmap(mDataset[position].getService_icon_bitmap());

    }


    @Override
    public int getItemCount() {
        return mDataset.length;
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mServiceName;
        private ImageView mServiceIcon;

        ViewHolder(View itemView) {
            super(itemView);
            mServiceName = (TextView) itemView.findViewById(R.id.service_name);
            mServiceIcon = (ImageView) itemView.findViewById(R.id.service_icon);
        }
    }


}
