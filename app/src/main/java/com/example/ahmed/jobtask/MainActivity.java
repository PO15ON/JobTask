package com.example.ahmed.jobtask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView textView;

    public static final String CLIENT_ID = "46v3htox13uookw4o8c8gs44oggocgos88804oggggkwss8o04";
    public static final String CLIENT_SECRET = "4jm5k8h9vxmokkssw4wkcsgs0cws0kow0w48s8gc80cwc404g0";

    String accessToken;
    private static final String TAG = "accessToken";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*Start Retrofit Request*/

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Credential> getAccessToken = apiService.getAccessToken(CLIENT_ID,
                CLIENT_SECRET,
                "password",
                "api@example.com",
                "api");

        getAccessToken.enqueue(new Callback<Credential>() {
            @Override
            public void onResponse(Call<Credential> call, final Response<Credential> response) {
                accessToken = response.body().getAccessToken();
                Log.d(TAG, "onResponse: accessToken = " + accessToken);
//                final String refreshToken = response.body().getRefreshToken();

                /*Refresh the Token After it Expires*/

                final Handler handler = new Handler();
                final int delay = response.body().getExpiresIn()*1000; //milliseconds

                handler.postDelayed(new Runnable(){
                    public void run(){
                        //do something
                        Call<Credential> refreshAccessToken1 = apiService.refreshAccessToken(CLIENT_ID,
                                CLIENT_SECRET,
                                "refresh_token",
                                response.body().getRefreshToken());

                        refreshAccessToken1.enqueue(new Callback<Credential>() {
                            @Override
                            public void onResponse(Call<Credential> call, Response<Credential> response1) {
//                                Log.d(TAG, "onResponse: AccessToken = " + accessToken);
                                Log.d(TAG, "onResponse: new AccessToken = " + response1.body().getAccessToken());
//                                response1.body().setAccessToken(response1.body().getAccessToken());
                                accessToken = response1.body().getAccessToken();
                                textView.append(accessToken + "\n");
                            }

                            @Override
                            public void onFailure(Call<Credential> call, Throwable t) {
                                Log.e(TAG, "onFailure: ", t);
                            }
                        });
                        handler.postDelayed(this, delay);
                    }
                }, delay);

                /*End refresh AccessToken*/

            }

            @Override
            public void onFailure(Call<Credential> call, Throwable t) {
                Log.e(TAG, "onFailure: " +  t.toString() );
            }
        });

        /*Start UI View*/




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

        FragmentManager fragmentManager =getSupportFragmentManager();

        if (id == R.id.nav_front) {

            FrontEnd frontEnd = new FrontEnd();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frame, frontEnd);
            transaction.commit();

        } else if (id == R.id.nav_back) {



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
