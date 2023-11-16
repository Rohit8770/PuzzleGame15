package com.example.puzzlegame15.puzzle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.puzzlegame15.Adapter.HighScoreAdapter;
import com.example.puzzlegame15.Adapter.UserAdapter;
import com.example.puzzlegame15.Model.HighScoreModel;
import com.example.puzzlegame15.R;
import com.example.puzzlegame15.Table.PuzzleHandler;
import com.example.puzzlegame15.Utils.SharedPreference;

import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity {

    RecyclerView rcvHigh;
    ImageView imgback;
    SwipeRefreshLayout SwipeHigh;
    HighScoreAdapter highScoreAdapter;
    ArrayList<HighScoreModel > highScoreModels;
    PuzzleHandler puzzleHandler;
    ArrayList<HighScoreModel> scoreModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        rcvHigh=findViewById(R.id.rcvHigh);
        imgback=findViewById(R.id.imgback);
        SwipeHigh=findViewById(R.id.SwipeHigh);
        puzzleHandler=new PuzzleHandler(this);

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });


        puzzleHandler=new PuzzleHandler(HighScoreActivity.this);
        scoreModel = puzzleHandler.getAllScores(10);
        highScoreAdapter = new HighScoreAdapter(HighScoreActivity.this,scoreModel);
        rcvHigh.setLayoutManager(new LinearLayoutManager(HighScoreActivity.this));
        rcvHigh.setAdapter(highScoreAdapter);

    }
}