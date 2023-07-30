package com.jeysi.chandaraproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jeysi.chandaraproject.adapter.AdapterUser;
import com.jeysi.chandaraproject.models.ModelUser;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {

    RecyclerView recyclerView;
    AdapterUser adapterUsers;
    List<ModelUser> userList;

    //firebase auth
    FirebaseAuth firebaseAuth;

    public UsersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        //init
        firebaseAuth = FirebaseAuth.getInstance();
        //init recyclerview
        recyclerView = view.findViewById(R.id.users_recyclerView);
        //set properties
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        //init user list
        userList = new ArrayList<>();


        //get all users
        getAllUsers();


        return view;
    }

    private void getAllUsers(){

        //get current user
        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        //get path of database named 'Users" contains user info
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        //get all data from path

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){

                    ModelUser modelUser = ds.getValue(ModelUser.class);

                    //get all users
                    if (!modelUser.getUid().equals(fuser.getUid())){
                        userList.add(modelUser);
                    }
                    //adapter
                    adapterUsers = new AdapterUser(getActivity(), userList);
                    //set adapter to recycler
                    recyclerView.setAdapter(adapterUsers);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void searchUsers(String query) {

        //get current user
        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        //get path of database named 'Users" contains user info
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        //get all data from path

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){

                    ModelUser modelUser = ds.getValue(ModelUser.class);

                    //get all search users
                    if (!modelUser.getUid().equals(fuser.getUid())){

                        if (modelUser.getName().toLowerCase().contains(query.toLowerCase()) ||
                                modelUser.getEmail().toLowerCase().contains(query.toLowerCase())){

                            userList.add(modelUser);

                        }

                    }
                    //adapter
                    adapterUsers = new AdapterUser(getActivity(), userList);
                    //refresh adapter
                    adapterUsers.notifyDataSetChanged();
                    //set adapter to recycler
                    recyclerView.setAdapter(adapterUsers);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();

        }

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true); // to show menu option in fragment
        super.onCreate(savedInstanceState);
    }
    /*inflate opt menu*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflate enu
        inflater.inflate(R.menu.menu_main, menu);

        //hide addpost icon from this fragment
        menu.findItem(R.id.action_addpost).setVisible(false);

        //search view
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        //search listerner

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //called when user press search button from keyboard
                //if search query is not empty then search
                if (!TextUtils.isEmpty(s.trim())){
                    //search text contains text, search it
                    searchUsers(s);

                }
                else {
                    //search text empty, get all users
                    getAllUsers();

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //called whwener user press any single letter
                //if search query is not empty then search
                if (!TextUtils.isEmpty(s.trim())){
                    //search text contains text, search it
                    searchUsers(s);

                }
                else {
                    //search text empty, get all users
                    getAllUsers();

                }
                return false;
            }
        });


        super.onCreateOptionsMenu(menu, inflater);
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
        else if (id == R.id.action_settings) {
            //go to settings act
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            
        }
        return super.onOptionsItemSelected(item);

    }
}