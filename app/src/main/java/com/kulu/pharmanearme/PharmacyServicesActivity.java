package com.kulu.pharmanearme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kulu.pharmanearme.models.PharmacyServicesDetails;
import com.kulu.pharmanearme.network.ApiCallHandler;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Subha on 14/07/2017.
 */

public class PharmacyServicesActivity extends AppCompatActivity {

    String url=null;

    ProgressBar mProgressBar;
    RecyclerServicesAdapter mAdapter;
    RecyclerView mRecyclerView;
    DisposableObserver<PharmacyServicesDetails> mDisposableObserver=null;

    public static Intent createIntent(Context context, String placeID, String placeName){
       Intent i = new Intent(context,PharmacyServicesActivity.class);
        i.putExtra("pharma_id",placeID);
        i.putExtra("pharma_name",placeName);
        return i;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        url = "https://api.84r.co/pharmacies/"+getIntent().getStringExtra("pharma_id")+"/services";

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

      //  mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
      //  mProgressBar.setIndeterminate(true);
       // mProgressBar.setVisibility(View.GONE);

        TextView mPharma_name = (TextView) findViewById(R.id.pharmacy_name);
        mPharma_name.setText(getIntent().getStringExtra("pharma_name"));

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerServicesAdapter(this);


        if(mDisposableObserver==null)
            mDisposableObserver = getServicesData();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public DisposableObserver<PharmacyServicesDetails> getServicesData() {
        ApiCallHandler apiCall = new ApiCallHandler();
        return apiCall.getServicesList(url)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<PharmacyServicesDetails>() {
                    @Override
                    public void onNext(@NonNull PharmacyServicesDetails details) {
                        PharmacyServicesDetails.Services[] service = details.getAllServices();
                        mAdapter.setData(service);
                        mRecyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
