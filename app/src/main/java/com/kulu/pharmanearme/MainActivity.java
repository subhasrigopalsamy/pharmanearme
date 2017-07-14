package com.kulu.pharmanearme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.kulu.pharmanearme.models.PharmacyDetails;
import com.kulu.pharmanearme.network.ApiCallHandler;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    ProgressBar mProgressBar;
    RecyclerAdapter mAdapter;
    RecyclerView mRecyclerView;
    DisposableObserver<PharmacyDetails> mDisposableObserver=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.GONE);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerAdapter(this);


        if(mDisposableObserver==null)
            mDisposableObserver = getPharmaData();

    }

    private DisposableObserver<PharmacyDetails> getPharmaData(){
        ApiCallHandler apicall = new ApiCallHandler();
        return apicall.getPharamcyList("https://api.84r.co/locations?lat=51.657&long=0.121&radius=50000.0&unit=km")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<PharmacyDetails>(){
                    @Override
                    public void onNext(PharmacyDetails details) {
                        PharmacyDetails.Pharmacy[] pharma = details.getAllPharmacy();
                        mAdapter.setData(pharma);
                        mRecyclerView.setAdapter(mAdapter);

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    @Override
    protected void onStop() {
        if(mDisposableObserver != null && !mDisposableObserver.isDisposed()){
            mDisposableObserver.dispose();
        }
        super.onStop();
    }
}
