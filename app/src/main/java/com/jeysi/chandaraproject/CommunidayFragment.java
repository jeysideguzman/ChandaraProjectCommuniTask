package com.jeysi.chandaraproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CommunidayFragment extends Fragment {

    private CalendarView calendarView;
    private EditText editText;
    private Button button;
    private String stringDateSelected;
    private DatabaseReference databaseReference;
    private LinearLayout tasksLayout;
    private LinearLayout remindersLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_communiday, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        clickListener();
        loadEvents();
    }

    private void init(View view) {
        calendarView = view.findViewById(R.id.calendarView);
        editText = view.findViewById(R.id.editText);
        button = view.findViewById(R.id.button);
        tasksLayout = view.findViewById(R.id.tasksLayout);
        remindersLayout = view.findViewById(R.id.remindersLayout);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                stringDateSelected = Integer.toString(i) + Integer.toString(i1 + 1) + Integer.toString(i2);
            }
        });
    }

    private void clickListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ensure that stringDateSelected is not null before proceeding
                if (stringDateSelected != null) {
                    databaseReference = FirebaseDatabase.getInstance().getReference("Calendar").child(getFragmentUniqueId());
                    String eventText = editText.getText().toString();
                    databaseReference.child(stringDateSelected).push().setValue(eventText);
                } else {
                    // Handle the case when stringDateSelected is null
                    editText.setText("No date selected");
                }
            }
        });
    }

    private void loadEvents() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Calendar").child(getFragmentUniqueId());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tasksLayout.removeAllViews();
                remindersLayout.removeAllViews();

                for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                    String date = dateSnapshot.getKey();
                    for (DataSnapshot eventSnapshot : dateSnapshot.getChildren()) {
                        String event = eventSnapshot.getValue(String.class);
                        addEventToLayout(tasksLayout, event);
                        addEventToLayout(remindersLayout, date + ": " + event);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addEventToLayout(LinearLayout layout, String event) {
        TextView textView = new TextView(getContext());
        textView.setText(event);
        textView.setTextSize(18);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 5, 0, 0);
        textView.setLayoutParams(layoutParams);

        layout.addView(textView);
    }

    // Generate a unique identifier for each fragment instance using its hashCode
    private String getFragmentUniqueId() {
        return String.valueOf(hashCode());
    }
}