package com.travelerbuddy.feri.travelersbuddy;



import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Pomozni.SideMenuAdapter;
import Pomozni.SideMenuItem;

public class MainActivity extends AppCompatActivity {

   private DrawerLayout mDrawerLayout;
   private ListView listview;
   private  ActionBarDrawerToggle mDrawerToggle;
   private SideMenuAdapter adapter;
   private Toolbar toolbar;
   private WebView web;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        listview = (ListView)findViewById(android.R.id.list);

        List<SideMenuItem> items = new ArrayList<>();
        items.add(new SideMenuItem(R.string.potov, R.mipmap.potov));
        items.add(new SideMenuItem(R.string.izbiraPoti, R.mipmap.ic_action));
        items.add(new SideMenuItem(R.string.prevozi, R.mipmap.car));
        items.add(new SideMenuItem(R.string.hoteli,R.mipmap.hoteli));
        items.add(new SideMenuItem(R.string.dogodki, R.mipmap.dogodki));
        items.add(new SideMenuItem(R.string.kovcek, R.mipmap.kovcek));
        items.add(new SideMenuItem(R.string.vreme, R.mipmap.vreme));
        items.add(new SideMenuItem(R.string.valuta, R.mipmap.valuta));



        adapter = new SideMenuAdapter(getApplicationContext(),items);

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int editedPosition = position + 1;
                prikaziFragment(position);
                SideMenuItem item = (SideMenuItem) parent.getItemAtPosition(position);
                toolbar.setTitle(item.getIme());
                mDrawerLayout.closeDrawer(listview);
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close){
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
                invalidateOptionsMenu();
                syncState();
            }
            public void onDrawerOpened(View v){
                super.onDrawerOpened(v);
                invalidateOptionsMenu();
                syncState();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.content_frame, new IzbiraPotiFragment());
        tx.commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home: {
                if (mDrawerLayout.isDrawerOpen(listview)){
                    mDrawerLayout.closeDrawer(listview);
                } else {
                    mDrawerLayout.openDrawer(listview);
                }
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        listview.setAdapter(adapter);

    }

    public void prikaziFragment(int position){

        Fragment fragment = null;

        switch(position){

            case 0:
                //fragment = new MojaPotovanjaFragment();
                break;
            case 1:
                fragment = new IzbiraPotiFragment();
                break;
            case 2:
                //fragment = new PrevoziFragment();
                break;
            case 3:
                fragment = new HoteliFragment();
                break;
            case 4:
                fragment = new DogodkiFragment();
                break;
            case 5:
                fragment = new KovcekFragment();
                break;
            case 6:
                //fragment = new VremeFragment();
                break;
            case 7:
                fragment = new ValuteFragment();
                break;
            default:
                fragment = new IzbiraPotiFragment();
                break;

        }

        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment).commit();

            listview.setItemChecked(position, true);
            listview.setSelection(position);
        }else{
            Log.d("MENJAVA FRAGMENTA", "NAPAKA");
        }


    }


    @Override
    public void onBackPressed() {

        web = (WebView) findViewById(R.id.webView);

        if (web.copyBackForwardList().getCurrentIndex() > 0) {
            web.goBack();
        }
        else {
            // Your exit alert code, or alternatively line below to finish
            super.onBackPressed(); // finishes activity
        }
    }

}
