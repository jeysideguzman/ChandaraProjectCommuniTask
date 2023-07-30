package com.jeysi.chandaraproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.jeysi.chandaraproject.notifications.Token;

public class DashboardActivity extends AppCompatActivity {

    //firebase auth
    FirebaseAuth firebaseAuth;
    ActionBar actionBar;
    String mUID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //action bar and title
        actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");

        //init
        firebaseAuth = FirebaseAuth.getInstance();

        //bottom nav
        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnItemSelectedListener(selectedListener);

        actionBar.setTitle("Home");
        HomeFragment fragment1 = new HomeFragment();
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.content, fragment1, "");
        ft1.commit();

        checkUserStatus();

        //update token



    }

    private BottomNavigationView.OnItemSelectedListener selectedListener =
            new BottomNavigationView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    // Handle item click
                    if (item.getItemId() == R.id.nav_home) {
                        // Home fragment transaction
                        actionBar.setTitle("Home");
                        HomeFragment fragment1 = new HomeFragment();
                        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                        ft1.replace(R.id.content, fragment1, "");
                        ft1.commit();
                        return true;


                    } else if (item.getItemId() == R.id.nav_profile) {
                        // Profile fragment transaction
                        actionBar.setTitle("Profile");
                        ProfileFragment fragment2 = new ProfileFragment();
                        FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                        ft2.replace(R.id.content, fragment2, "");
                        ft2.commit();
                        return true;


                    } else if (item.getItemId() == R.id.nav_users) {
                        // Users fragment transaction
                        actionBar.setTitle("Users");
                        UsersFragment fragment3 = new UsersFragment();
                        FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                        ft3.replace(R.id.content, fragment3, "");
                        ft3.commit();
                        return true;
                    } else if (item.getItemId() == R.id.nav_insights) {
                        // Users fragment transaction
                        actionBar.setTitle("Insights");
                        InsightsFragment fragment4 = new InsightsFragment();
                        FragmentTransaction ft4 = getSupportFragmentManager().beginTransaction();
                        ft4.replace(R.id.content, fragment4, "");
                        ft4.commit();
                        return true;
                    } else if (item.getItemId() == R.id.nav_communiday) {
                        // Users fragment transaction
                        actionBar.setTitle("CommuniDay");
                        CommunidayFragment fragment5 = new CommunidayFragment();
                        FragmentTransaction ft5 = getSupportFragmentManager().beginTransaction();
                        ft5.replace(R.id.content, fragment5, "");
                        ft5.commit();
                        return true;

                    }

                    return false;
                };
            };





    private void checkUserStatus(){

        //get user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){

            //user stay signed in

            //set email of logged in user
            //mProfileTv.setText(user.getEmail());
            mUID = user.getUid();

            SharedPreferences sp = getSharedPreferences("SP_USER", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("Current_USERID", mUID);
            editor.apply();

            updateToken();

        } else {
            startActivity(new Intent(DashboardActivity.this, MainActivity.class));
            finish();

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {
        //CHECK onstart
        checkUserStatus();
        super.onStart();
    }

    /*inflate opt menu*/


    //OPTIONAL(MESSAGING NOTIF)
    private void updateToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            String token = task.getResult();
                            // Save the token in the database for the current user
                            // Add your code here to save the token to the database
                            // You can use the mUID variable to get the current user's UID
                            if (mUID != null) {
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens");
                                Token mToken = new Token(token);
                                ref.child(mUID).setValue(mToken);
                            }
                        } else {
                            // Handle the error if token retrieval fails
                        }
                    }
                });
    }


    @Override
    protected void onResume() {
        checkUserStatus();
        super.onResume();
    }
    /*hadle menu item click*/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //get item id
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            firebaseAuth.signOut();
            checkUserStatus();
        }
        return super.onOptionsItemSelected(item);

    }
}