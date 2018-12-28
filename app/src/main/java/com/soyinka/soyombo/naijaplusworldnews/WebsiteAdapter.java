package com.soyinka.soyombo.naijaplusworldnews;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SHOW on 12/26/2018.
 */

public class WebsiteAdapter extends RecyclerView.Adapter<WebsiteAdapter.GridViewHolder> {

    private ArrayList<NewsDetails> mWebsitesAndNames;
    static Context mContext;
    private InterstitialAd mInterstitialAd;



    public WebsiteAdapter(Context context, ArrayList<NewsDetails> websites, InterstitialAd interstitialAd){
        mContext = context;
        mWebsitesAndNames = websites;
        mInterstitialAd= interstitialAd;
    }


    @Override
    public WebsiteAdapter.GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_recycler;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        GridViewHolder viewHolder = new GridViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WebsiteAdapter.GridViewHolder holder, int position) {
        final NewsDetails websites = mWebsitesAndNames.get(position);

        final String webUrl = websites.getWebsite();

        //load backdrop poster
        Picasso.with(mContext)
                .load(websites.getResourceId())
                .placeholder(R.drawable.world1)
                .error(R.drawable.world1)
                .into(holder.webView);

        holder.name.setText(websites.getNames());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()){
                    mInterstitialAd.show();
                } else {
                    startIntent(webUrl);
                }
            }
        });

        holder.webView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()){
                    mInterstitialAd.show();
                } else {
                    startIntent(webUrl);
                }
            }
        });

        mInterstitialAd.setAdListener( new AdListener(){
            @Override
            public void onAdClosed() {
                startIntent(webUrl);
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
    }

    private void startIntent(String webUrl){
        mContext.startActivity(new Intent(mContext, WebActivity.class)
                .putExtra("Website", webUrl ));
    }

    @Override
    public int getItemCount() {
        return mWebsitesAndNames.size();
    }

    public static class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView webView;
        TextView name;
        private  ProgressBar progressBar;
        private  View transaperentView;

        Context context = mContext.getApplicationContext();

        public GridViewHolder(View itemView){
            super (itemView);

            webView = itemView.findViewById(R.id.web_view);
            name = itemView.findViewById(R.id.news_provider);
        }
    }

}
