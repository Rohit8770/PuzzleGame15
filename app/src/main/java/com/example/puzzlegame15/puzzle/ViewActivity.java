package com.example.puzzlegame15.puzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.puzzlegame15.Adapter.HighScoreAdapter;
import com.example.puzzlegame15.Adapter.ViewAdapter;
import com.example.puzzlegame15.Model.HighScoreModel;
import com.example.puzzlegame15.R;
import com.example.puzzlegame15.Table.PuzzleHandler;
import com.example.puzzlegame15.Utils.Tools;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    ImageView imgBack,imgPerson;
    TextView txName1;
    RecyclerView rcvView;
    PuzzleHandler puzzleHandler;
    ViewAdapter viewAdapter;
    ArrayList<HighScoreModel > highScoreModel;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        imgPerson=findViewById(R.id.imgPerson);
        txName1=findViewById(R.id.txName1);
        rcvView=findViewById(R.id.rcvView);
        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        int UserId = getIntent().getIntExtra("userId", 0);
        String Name = getIntent().getStringExtra("Name");
        String image = getIntent().getStringExtra("Image");
        txName1.setText(Name);
        if (image != null && !image.isEmpty()) {
            Tools.displayImage(ViewActivity.this, imgPerson, image);
        }


        //Toast.makeText(this, ""+UserId, Toast.LENGTH_SHORT).show();

        puzzleHandler=new PuzzleHandler(ViewActivity.this);
        highScoreModel = puzzleHandler.getUserHistory(UserId);
        viewAdapter = new ViewAdapter(ViewActivity.this,highScoreModel);
        rcvView.setLayoutManager(new LinearLayoutManager(ViewActivity.this));
        rcvView.setAdapter(viewAdapter);


    }
}