package com.eternity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        webView = findViewById(R.id.webView);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);
        webView.requestFocus();
        webView.getSettings().setLightTouchEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);
        // enable Web Storage: localStorage, sessionStorage
        webView.getSettings().setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                webView.setVisibility(View.INVISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                webView.loadUrl("javascript:(function() { " +
                        "document.getElementById('header').style.display='none'; " +
                        "document.getElementById('footer').style.display='none';"+
                        "var x = document.getElementById('titlebar');"+
                        "if(x.classList!='listing-titlebar')" +
                        "x.style.display='none';"+
                        "})()");
                webView.setVisibility(View.VISIBLE);
            }
        });
        String cookies = CookieManager.getInstance().getCookie("http://www.feignuk.com");
        cookies=(cookies==null)?" ":cookies;
        if(!cookies.contains("logged")) {
            webView.loadUrl("http://www.feignuk.com/my-profile");
        }
        else {
            webView.loadUrl("http://www.feignuk.com/");
        }



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id==R.id.nav_categories)
        {
            String cookies = CookieManager.getInstance().getCookie("http://www.feignuk.com");
            cookies=(cookies==null)?" ":cookies;
            if(!cookies.contains("logged")) {
                webView.loadUrl("http://www.feignuk.com/my-profile");
            }
            else {
                webView.loadUrl("http://www.feignuk.com/");
            }
        }
        else if(id==R.id.nav_myprofile)
        {
            webView.loadUrl("http://www.feignuk.com/my-profile");
        }
        else if(id==R.id.nav_bookings)
        {
            String cookies = CookieManager.getInstance().getCookie("http://www.feignuk.com");
            cookies=(cookies==null)?" ":cookies;
            if(!cookies.contains("logged")) {
                webView.loadUrl("http://www.feignuk.com/my-profile");
            }
            else {
                webView.loadUrl("http://www.feignuk.com/my-bookings/");
            }
        }
        else if(id==R.id.nav_logout)
        {
            android.webkit.CookieManager cookieManager = CookieManager.getInstance();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
                    // a callback which is executed when the cookies have been removed
                    @Override
                    public void onReceiveValue(Boolean aBoolean) {
                        Log.d("Cookie", "Cookie removed: " + aBoolean);
                    }
                });
            }
            else cookieManager.removeAllCookie();
            webView.loadUrl("http://www.feignuk.com/my-profile");

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

