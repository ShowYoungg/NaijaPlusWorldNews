package com.soyinka.soyombo.naijaplusworldnews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

import static android.support.v7.widget.DividerItemDecoration.HORIZONTAL;
import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class NewsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<NewsDetails> websitesAndNames;
    private ArrayList<NewsDetails> worldWebsites;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager layoutManager;
    private InterstitialAd interstitialAd;
    private int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544-3347511713");
        AdView mAdView = findViewById(R.id.adView);
        mAdView.loadAd(new AdRequest.Builder().build());

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.loadAd(new AdRequest.Builder().build());



        String[] ws = {"www.presstv.ir", "www.bbc.com/news", "www.abc.net.au/news", "www.edition.cnn.com", "www.aljazeera.com/news", "www.scmp.com/news"};
        websitesAndNames = new ArrayList<>();
        worldWebsites = new ArrayList<>();

        Intent intent = getIntent();
        if (intent != null){
            i = intent.getIntExtra("news", -1);
        }

        worldWebsites.add( new NewsDetails("www.presstv.ir", "PressTV", R.drawable.presstv));
        worldWebsites.add( new NewsDetails("www.bbc.com/news", "BBC News", R.drawable.bbc_news));
        worldWebsites.add( new NewsDetails("www.abc.net.au", "ABC News", R.drawable.abc));
        worldWebsites.add( new NewsDetails("www.edition.cnn.com", "CNN", R.drawable.cnn));
        worldWebsites.add( new NewsDetails("www.aljazeera.com/news",  "Aljazeera", R.drawable.aljazeera));
        worldWebsites.add( new NewsDetails("www.scmp.com/news", "South China News", R.drawable.southchina));


        websitesAndNames.add( new NewsDetails("punchng.com", "Punch", R.drawable.punch1));
        websitesAndNames.add( new NewsDetails("www.vanguardngr.com", "Vanguard", R.drawable.vanguardlogo));
        websitesAndNames.add( new NewsDetails("www.thisdaylive.com", "This Day", R.drawable.thisdaylogo));
        websitesAndNames.add( new NewsDetails("thenatonlineng.net", "The Nation", R.drawable.nation1));
        websitesAndNames.add( new NewsDetails("www.tribune.com.ng", "Tribune", R.drawable.tribune));
        websitesAndNames.add( new NewsDetails("www.ngrguardiannew.com", "Nigeria Guardian", R.drawable.local5));
        websitesAndNames.add( new NewsDetails("www.newtelegraphng.com", "New Telegraph", R.drawable.newtelegraph));
        websitesAndNames.add( new NewsDetails("leadership.ng", "Leadership", R.drawable.leadership));
        websitesAndNames.add( new NewsDetails("sunnewsonline.com", "Sun", R.drawable.anonymous2));
        websitesAndNames.add( new NewsDetails("nigerianpilot.com", "Nigerian Pilot", R.drawable.local5));
        websitesAndNames.add( new NewsDetails("www.legit.ng", "Naij", R.drawable.naij));
        websitesAndNames.add( new NewsDetails("www.news24.com", "News24", R.drawable.punch));
        websitesAndNames.add( new NewsDetails("www.channelstv.com", "Channels TV News", R.drawable.channels));
        websitesAndNames.add( new NewsDetails("tell.ng", "Tell Magazine", R.drawable.background1));
        websitesAndNames.add( new NewsDetails("www.businessdayonline.com", "Business Day", R.drawable.businessday));
        websitesAndNames.add( new NewsDetails("dailytimes.ng", "Daily Times", R.drawable.thisdaylogo));
        websitesAndNames.add( new NewsDetails("www.nationalmirroronline.net", "National Mirror", R.drawable.annonymous1));
        websitesAndNames.add( new NewsDetails("www.dailytrust.com.ng", "Daily Trust", R.drawable.anonymous2));
        websitesAndNames.add( new NewsDetails("www.compassnewspaper.org", "Compass", R.drawable.compass));

        recyclerView = findViewById(R.id.web_recycler_view);

        if (i == 2){
            populateAdapter(worldWebsites);
        } else {
            populateAdapter(websitesAndNames);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void populateAdapter( ArrayList<NewsDetails> arrayList) {

        layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        WebsiteAdapter websiteAdapter = new WebsiteAdapter(this, arrayList, interstitialAd);
        recyclerView.setAdapter(websiteAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(this, VERTICAL);
        recyclerView.addItemDecoration(decoration);

        DividerItemDecoration decoration2 = new DividerItemDecoration(this, HORIZONTAL);
        recyclerView.addItemDecoration(decoration2);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.naija) {
            populateAdapter(websitesAndNames);
        } else if (id == R.id.world) {
            populateAdapter(worldWebsites);
        } else if (id == R.id.bbc) {
            startActivity(new Intent(this, WebActivity.class)
                    .putExtra("Website", "www.bbc.com/news" ));
        } else if (id == R.id.cnn) {
            startActivity(new Intent(this, WebActivity.class)
                    .putExtra("Website", "www.edition.cnn.com" ));
        } else if (id == R.id.presstv) {
            startActivity(new Intent(this, WebActivity.class)
                    .putExtra("Website", "www.presstv.ir" ));
        } else if (id == R.id.aljazeera) {
            startActivity(new Intent(this, WebActivity.class)
                    .putExtra("Website", "www.aljazeera.com/news" ));
        } else if (id == R.id.abc) {
            startActivity(new Intent(this, WebActivity.class)
                    .putExtra("Website", "www.abc.net.au" ));

        } else if (id == R.id.southchina) {
            startActivity(new Intent(this, WebActivity.class)
                    .putExtra("Website", "www.scmp.com/news" ));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
