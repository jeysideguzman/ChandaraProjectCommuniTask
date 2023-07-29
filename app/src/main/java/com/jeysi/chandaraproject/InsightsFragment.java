package com.jeysi.chandaraproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jeysi.chandaraproject.insightsact.eco1Activity;
import com.jeysi.chandaraproject.insightsact.eco2Activity;
import com.jeysi.chandaraproject.insightsact.eco3Activity;
import com.jeysi.chandaraproject.insightsact.educ1Activity;
import com.jeysi.chandaraproject.insightsact.educ2Activity;
import com.jeysi.chandaraproject.insightsact.educ3Activity;
import com.jeysi.chandaraproject.insightsact.food1Activity;
import com.jeysi.chandaraproject.insightsact.food2Activity;
import com.jeysi.chandaraproject.insightsact.food3Activity;
import com.jeysi.chandaraproject.insightsact.govid1Activity;
import com.jeysi.chandaraproject.insightsact.govid2Activity;
import com.jeysi.chandaraproject.insightsact.govid3Activity;


public class InsightsFragment extends Fragment {


    ImageButton edu1Btn;
    FirebaseAuth firebaseAuth;

    public InsightsFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_insights, container, false);

        // Find the button by its ID
        Button educ1Btn = view.findViewById(R.id.educ1Btn);

        // Set up click listener for the button
        educ1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to navigate to the next activity
                Intent intent = new Intent(getActivity(), educ1Activity.class);
                startActivity(intent);
            }
        });

        Button educ2Btn = view.findViewById(R.id.educ2Btn);

        // Set up click listener for the button
        educ2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to navigate to the next activity
                Intent intent = new Intent(getActivity(), educ2Activity.class);
                startActivity(intent);
            }
        });

        Button educ3Btn = view.findViewById(R.id.educ3Btn);

        // Set up click listener for the button
        educ3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to navigate to the next activity
                Intent intent = new Intent(getActivity(), educ3Activity.class);
                startActivity(intent);
            }
        });

        Button govid1Btn = view.findViewById(R.id.govid1Btn);

        // Set up click listener for the button
        govid1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to navigate to the next activity
                Intent intent = new Intent(getActivity(), govid1Activity.class);
                startActivity(intent);
            }
        });

        Button govid2Btn = view.findViewById(R.id.govid2Btn);

        // Set up click listener for the button
        govid2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to navigate to the next activity
                Intent intent = new Intent(getActivity(), govid2Activity.class);
                startActivity(intent);
            }
        });

        Button govid3Btn = view.findViewById(R.id.govid3Btn);

        // Set up click listener for the button
        govid3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to navigate to the next activity
                Intent intent = new Intent(getActivity(), govid3Activity.class);
                startActivity(intent);
            }
        });

        Button food1Btn = view.findViewById(R.id.food1Btn);

        // Set up click listener for the button
        food1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to navigate to the next activity
                Intent intent = new Intent(getActivity(), food1Activity.class);
                startActivity(intent);
            }
        });

        Button food2Btn = view.findViewById(R.id.food2Btn);

        // Set up click listener for the button
        food2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to navigate to the next activity
                Intent intent = new Intent(getActivity(), food2Activity.class);
                startActivity(intent);
            }
        });

        Button food3Btn = view.findViewById(R.id.food3Btn);

        // Set up click listener for the button
        food3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to navigate to the next activity
                Intent intent = new Intent(getActivity(), food3Activity.class);
                startActivity(intent);
            }
        });

        Button eco1Btn = view.findViewById(R.id.eco1Btn);

        // Set up click listener for the button
        eco1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to navigate to the next activity
                Intent intent = new Intent(getActivity(), eco1Activity.class);
                startActivity(intent);
            }
        });

        Button eco2Btn = view.findViewById(R.id.eco2Btn);

        // Set up click listener for the button
        eco2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to navigate to the next activity
                Intent intent = new Intent(getActivity(), eco2Activity.class);
                startActivity(intent);
            }
        });

        Button eco3Btn = view.findViewById(R.id.eco3Btn);

        // Set up click listener for the button
        eco3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to navigate to the next activity
                Intent intent = new Intent(getActivity(), eco3Activity.class);
                startActivity(intent);
            }
        });



        return view;
    }
    /*inflate opt menu*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflate enu
        inflater.inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_addpost).setVisible(false); // hide add post btn
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true); // to show menu option in fragment
        super.onCreate(savedInstanceState);
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

    /*hadle menu item click*/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //get item id
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            firebaseAuth.signOut();
            checkUserStatus();
        }
        if (id == R.id.action_addpost) {
            startActivity(new Intent(getActivity(), AddPostActivity.class));

        }


        return super.onOptionsItemSelected(item);

    }


}