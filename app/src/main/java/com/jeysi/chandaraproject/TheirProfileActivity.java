package com.jeysi.chandaraproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.jeysi.chandaraproject.adapter.AdapterPost;
import com.jeysi.chandaraproject.models.ModelPost;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TheirProfileActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    RecyclerView postRecyclerView;
    ImageView avatarTv, coverTv;
    TextView nameTv, emailTv, phonenoTv;
    List<ModelPost> postList;
    AdapterPost adapterPost;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_their_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //init views
        avatarTv = findViewById(R.id.avatarIv);
        coverTv = findViewById(R.id.coverTv);
        nameTv = findViewById(R.id.nameTv);
        emailTv = findViewById(R.id.emailTv);
        phonenoTv = findViewById(R.id.phonenoTv);
        postRecyclerView = findViewById(R.id.recyclerview_posts);

        firebaseAuth = FirebaseAuth.getInstance();

        //get uid clicked user to retrieve his posts
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");

        //..//
        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("uid").equalTo(uid);
        query.addValueEventListener(new ValueEventListener()    {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //check until required data
                for (DataSnapshot ds : snapshot.getChildren()){

                    //get data
                    String name = ""+ds.child("name").getValue();
                    String email = ""+ds.child("email").getValue();
                    String phone = ""+ds.child("phone").getValue();
                    String image = ""+ds.child("image").getValue();
                    String cover = ""+ds.child("cover").getValue();

                    //set data
                    nameTv.setText(name);
                    emailTv.setText(email);
                    phonenoTv.setText(phone);

                    try {
                        //image received/set
                        Picasso.get().load(image).into(avatarTv);
                    }
                    catch (Exception e) {
                        //any exception while getting image
                        Picasso.get().load(R.drawable.ic_addimage).into(avatarTv);
                    }try {
                        //image received/set
                        Picasso.get().load(cover).into(coverTv);
                    }
                    catch (Exception e) {
                        //any exception while getting image

                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        postList = new ArrayList<>();

        checkUserStatus();
        loadHisPost();

    }

    private void loadHisPost() {

        //linear layout for rv
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //show newest post
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        //set layout to rv
        postRecyclerView.setLayoutManager(layoutManager);

        //init posts list
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
        //query to load post
        Query query = ref.orderByChild("uid").equalTo(uid);
        //get all data from ref
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    ModelPost myPosts = ds.getValue(ModelPost.class);

                    //add to list
                    postList.add(myPosts);
                    //adapter
                    adapterPost = new AdapterPost(TheirProfileActivity.this, postList);
                    //set adapter to rv
                    postRecyclerView.setAdapter(adapterPost);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TheirProfileActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void searchHisPosts(final String searchQuery){

        //linear layout for rv
        LinearLayoutManager layoutManager = new LinearLayoutManager(TheirProfileActivity.this);
        //show newest post
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        //set layout to rv
        postRecyclerView.setLayoutManager(layoutManager);

        //init posts list
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
        //query to load post
        Query query = ref.orderByChild("uid").equalTo(uid);
        //get all data from ref
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    ModelPost myPosts = ds.getValue(ModelPost.class);

                    if (myPosts.getpTitle().toLowerCase().contains(searchQuery.toLowerCase()) ||
                            myPosts.getpDescr().toLowerCase().contains(searchQuery.toLowerCase())){

                        //add to list
                        postList.add(myPosts);
                    }


                    //adapter
                    adapterPost = new AdapterPost(TheirProfileActivity.this, postList);
                    //set adapter to rv
                    postRecyclerView.setAdapter(adapterPost);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TheirProfileActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
    private void checkUserStatus(){

        //get user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            //user stay signed in
            //set email of logged in user
            //mProfileTv.setText(user.getEmail());

        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();

        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_addpost).setVisible(false); // hide add post btn


        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //called when user press search ntm
                if (!TextUtils.isEmpty(query)){
                    //search
                    searchHisPosts(query);

                }
                else {
                    loadHisPost();

                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //called when user type any letter
                if (!TextUtils.isEmpty(newText)){
                    //search
                    searchHisPosts(newText);

                }
                else {
                    loadHisPost();

                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            firebaseAuth.signOut();
            checkUserStatus();
        }
        return super.onOptionsItemSelected(item);
    }
}