package com.example.puzzlegame15.puzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.puzzlegame15.Adapter.HighScoreAdapter;
import com.example.puzzlegame15.Adapter.UserAdapter;
import com.example.puzzlegame15.Model.PuzzleModel;
import com.example.puzzlegame15.R;
import com.example.puzzlegame15.Table.PuzzleHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    SwipeRefreshLayout txSwipe;
    FloatingActionButton btnAddData;
    RecyclerView rcvUser;
    PuzzleHandler puzzleHandler;
    UserAdapter userAdapter;
    ArrayList<PuzzleModel> puzzleModelArrayLists;
    LinearLayout btnHigh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        rcvUser = findViewById(R.id.rcvUser);
        btnAddData = findViewById(R.id.btnAddData);
        txSwipe=findViewById(R.id.txSwipe);
        btnHigh=findViewById(R.id.btnHigh);

        txSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                puzzleModelArrayLists = puzzleHandler.getAllUsers();
                userAdapter.updateData(puzzleModelArrayLists);
                txSwipe.setRefreshing(false);
            }
        });
        btnHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(UserActivity.this, HighScoreActivity.class);
                startActivity(i);
            }
        });

        puzzleHandler = new PuzzleHandler(this);
        puzzleModelArrayLists = puzzleHandler.getAllUsers();  // Retrieve data here

        // Initialize the userAdapter after retrieving data
        userAdapter = new UserAdapter(UserActivity.this, puzzleModelArrayLists);

        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(UserActivity.this);
        rcvUser.setLayoutManager(layoutManager);
        rcvUser.setAdapter(userAdapter);
    }

}

