package com.example.ahmed.jobtask;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmed.jobtask.credentials.Account;
import com.example.ahmed.jobtask.credentials.ApiClient;
import com.example.ahmed.jobtask.credentials.ApiInterface;
import com.example.ahmed.jobtask.credentials.Credential;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String CLIENT_ID = "46v3htox13uookw4o8c8gs44oggocgos88804oggggkwss8o04";
    public static final String CLIENT_SECRET = "4jm5k8h9vxmokkssw4wkcsgs0cws0kow0w48s8gc80cwc404g0";
    private static final String TAG = "accessToken";
    static String accessToken;
    static Integer adminId;
    TextView textView;
    EditText username, email;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        submitButton = findViewById(R.id.submit_button);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String usernameText, emailText;

                usernameText = username.getText().toString();
                emailText = email.getText().toString();

                if (usernameText.isEmpty() || emailText.isEmpty()) {
                    Toast.makeText(MainActivity.this, "please enter username and email", Toast.LENGTH_SHORT).show();
                    return;
                }
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

                        if (response.code() != 200 || response.code()==400) {
                            Log.d(TAG, "onResponse1: error");
                            Toast.makeText(MainActivity.this, "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                            return;
                        }
//                Log.i(TAG, "onResponse: id = " + response.body());
                        Log.d(TAG, "onResponse: rresults = " + response);
//                Log.d(TAG, "onResponse: message = " + response.message());
                        accessToken = "Authorization: Bearer " + response.body().getAccessToken();

                        Log.d(TAG, "onResponse: accessToken = " + accessToken);
//                final String refreshToken = response.body().getRefreshToken();

                /*CREATE USER ADMIN*/

                        Call<Account> accountCall = apiService.createAdmin(accessToken, usernameText, emailText, "Ahmed.mido2010", "en_US", "true");
                        accountCall.enqueue(new Callback<Account>() {
                            @Override
                            public void onResponse(Call<Account> call, Response<Account> response) {
                                Log.i(TAG, "onResponse: headers = " + response.headers());
                                if (accessToken == null) {
                                    Log.i(TAG, "onResponse: need accessToken");
                                    return;
                                }
                                Log.i(TAG, "onResponse: response = " + response);

                                if (response.code() != 201 && response.code()==400) {
                                    ProductDetails.errorCode = response.code();
//                                    if(ProductDetails.mToast == null) ProductDetails.mToast.show();
                                    return;
                                } else if (response.code() == 400) {
                            /*GET THE ADMIN*/
                                    Log.i(TAG, "onResponse: 400");
                                }
                                adminId = response.body().getId();
                                Log.i(TAG, "onResponse: adminId = " + adminId);
                            }

                            @Override
                            public void onFailure(Call<Account> call, Throwable t) {

                            }
                        });

                /*END CREATE USER ADMIN*/

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
//                                textView.append(accessToken + "\n");
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
                FragmentManager fragmentManager =getSupportFragmentManager();
                FrontEnd frontEnd = new FrontEnd();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frame, frontEnd);
                transaction.commit();
            }
        });





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

            if (accessToken != null) {

                FrontEnd frontEnd = new FrontEnd();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frame, frontEnd);
                transaction.commit();

            } else {
                Toast.makeText(this, "Wait for Auth.", Toast.LENGTH_SHORT).show();
                return false;
            }

        } else if (id == R.id.nav_back) {



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
