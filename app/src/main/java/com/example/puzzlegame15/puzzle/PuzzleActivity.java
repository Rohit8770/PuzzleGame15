package com.example.puzzlegame15.puzzle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.puzzlegame15.R;
import com.example.puzzlegame15.Table.PuzzleHandler;
import com.example.puzzlegame15.Utils.Tools;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class PuzzleActivity extends AppCompatActivity {

    TextView textViewTime, textViewStep, TextViewName;
    CircleImageView ImageId;
    GridLayout group;
    Button btnShuffle,btnStop,btnWin;
    private Button[][] buttons;
    private int[] tiles;
    private int emptyX = 2;
    private int emptyY = 2;
    private  int stepCount =0;
    private  int timeCount=0;
    private  boolean isTimeRunning;
    private  boolean isFirstMove=true;
    private boolean isGamePaused = false;
    private Timer timer;
    PuzzleHandler puzzleHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        textViewTime = findViewById(R.id.txTimer);
        textViewStep = findViewById(R.id.txMove);
        TextViewName = findViewById(R.id.TextViewName);

        ImageId = findViewById(R.id.ImageId);
        group=findViewById(R.id.group);
        btnShuffle=findViewById(R.id.btnShuffle);
        btnStop=findViewById(R.id.btnStop);
        btnWin=findViewById(R.id.btnWin);


        String Name = getIntent().getStringExtra("Name");
        String image = getIntent().getStringExtra("Image");

        TextViewName.setText(Name);
        if (image != null && !image.isEmpty()) {
            Tools.displayImage(PuzzleActivity.this, ImageId, image);
        }

        btnStop.setEnabled(false);
        btnShuffle.setEnabled(false);


        btnWin.setVisibility(View.GONE);
        btnWin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                solvePuzzle();
                Toast.makeText(PuzzleActivity.this, "Solved", Toast.LENGTH_SHORT).show();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isGamePaused) {
                    isGamePaused = false;
                    btnStop.setText("PAUSE");
                    loadTimer();
                    for (int i = 0; i < group.getChildCount(); i++) {
                        buttons[i / 3][i % 3].setClickable(true);
                    }
                } else {
                    isGamePaused = true;
                    btnStop.setText("RESUME");
                    if (timer != null) {
                        timer.cancel();
                    }
                    for (int i = 0; i < group.getChildCount(); i++) {
                        buttons[i / 3][i % 3].setClickable(false);
                    }
                }
            }
        });

        loadViews();
        loadNumbers();
        generateNumbers();
        loadDataToView();

    }

    private void loadViews() {
        buttons = new Button[3][3];

        for (int i = 0; i < group.getChildCount(); i++) {
            int x = i / 3;
            int y = i % 3;
            buttons[x][y] = (Button) group.getChildAt(i);
        }
        btnShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateNumbers();
                loadDataToView();
            }
        });
    }

    private void loadTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeCount++;
                setTime(timeCount);
            }
        }, 1000, 1000);
    }

    private void setTime(int timeCount) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int second = timeCount % 60;
                int minute = timeCount / 60;

                textViewTime.setText(String.format("Time: %02d:%02d", minute, second));
            }
        });
    }

    private void loadNumbers() {
        tiles = new int[9];
        for (int i = 0; i < group.getChildCount() - 1; i++) {
            tiles[i] = i + 1;
        }
    }

    private boolean isSolvable() {
        int countInversion = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < i; j++) {
                if (tiles[j] > tiles[i] && tiles[i] != 0 && tiles[j] != 0) {
                    countInversion++;
                }
            }
        }
        return countInversion % 2 == 0;
    }

    private void generateNumbers() {
        int n = 9;
        Random random = new Random();
        for (int i = 0; i < n - 1; i++) {
            tiles[i] = i + 1;
        }
        tiles[n - 1] = 0; // Set the last tile as empty
        int lastIndex = n - 1;

        for (int i = 0; i < n - 1; i++) {
            int randomIndex = random.nextInt(n - 1);
            int temp = tiles[i];
            tiles[i] = tiles[randomIndex];
            tiles[randomIndex] = temp;

            if (tiles[i] == 0) {
                emptyX = i / 3;
                emptyY = i % 3;
            }

            if (tiles[randomIndex] == 0) {
                emptyX = randomIndex / 3;
                emptyY = randomIndex % 3;
            }
        }

        if (!isSolvable()) {
            int temp = tiles[lastIndex - 1];
            tiles[lastIndex - 1] = tiles[lastIndex - 2];
            tiles[lastIndex - 2] = temp;
        }
    }

    private void loadDataToView() {
        for (int i = 0; i < group.getChildCount(); i++) {
            int x = i / 3;
            int y = i % 3;
            buttons[x][y].setText(String.valueOf(tiles[i]));
            buttons[x][y].setBackgroundColor(ContextCompat.getColor(this, android.R.color.background_light));
        }
        buttons[emptyX][emptyY].setText("");
        buttons[emptyX][emptyY].setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
    }

/*    public void buttonClick(View view) {
        if (!isGamePaused) {
            Button button = (Button) view;
            String tag = button.getTag().toString();
            int x = Character.getNumericValue(tag.charAt(0)); // Extract row from the tag
            int y = Character.getNumericValue(tag.charAt(1)); // Extract column from the tag

            if (isFirstMove) {
                isFirstMove = false;
                btnStop.setEnabled(true);
                btnShuffle.setEnabled(true);
                loadTimer();
            }

            if ((Math.abs(emptyX - x) == 1 && emptyY == y) || (Math.abs(emptyY - y) == 1 && emptyX == x)) {
                // Calculate the translation values
                float slideDistanceX = (x - emptyX) * button.getWidth();
                float slideDistanceY = (y - emptyY) * button.getHeight();

                // Animate the button's movement
                button.animate()
                        .translationXBy(slideDistanceX)
                        .translationYBy(slideDistanceY)
                        .setDuration(200)
                        .withEndAction(() -> {
                            // After animation, update button text and background
                            buttons[emptyX][emptyY].setText(button.getText().toString());
                            buttons[emptyX][emptyY].setBackgroundResource(android.R.drawable.btn_default);
                            button.setText("");
                            button.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                            emptyX = x;
                            emptyY = y;
                            stepCount++;
                            textViewStep.setText("Moves: " + stepCount);
                            checkWin();
                        });
            }
        }
    }*/


    public void buttonClick(View view) {
        if (!isGamePaused) {
            Button button = (Button) view;
            String tag = button.getTag().toString();
            int x = Character.getNumericValue(tag.charAt(0)); // Extract row from the tag
            int y = Character.getNumericValue(tag.charAt(1)); // Extract column from the tag

            if (isFirstMove) {
                isFirstMove = false;
                btnStop.setEnabled(true);
                btnShuffle.setEnabled(true);
                loadTimer();
            }

            if ((Math.abs(emptyX - x) == 1 && emptyY == y) || (Math.abs(emptyY - y) == 1 && emptyX == x)) {
                buttons[emptyX][emptyY].setText(button.getText().toString());
                buttons[emptyX][emptyY].setBackgroundResource(android.R.drawable.btn_default);
                button.setText("");
                button.setBackgroundColor(ContextCompat.getColor(this, com.google.android.material.R.color.design_default_color_background));
                emptyX = x;
                emptyY = y;
                stepCount++;
                textViewStep.setText("Moves: " + stepCount);
                checkWin();
            }
        }
    }
    private void checkWin() {
        puzzleHandler = new PuzzleHandler(PuzzleActivity.this);
        boolean isWin = false;
        if (emptyX == 2 && emptyY == 2) {
            for (int i = 0; i < group.getChildCount() - 1; i++) {
                if (!buttons[i / 3][i % 3].getText().toString().equals(String.valueOf(i + 1))) {
                    isWin = false;
                    break;
                } else {
                    isWin = true;
                }
            }
        }
        if (isWin) {

            Toast.makeText(this, "Win!!!\nMoves: " + stepCount + "\nTime Taken: " + timeCount, Toast.LENGTH_SHORT).show();
            String UserName = TextViewName.getText().toString();
            int UserId = getIntent().getIntExtra("userId",0);
            Toast.makeText(this, ""+UserId, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "UserName: "+ UserName + UserId, Toast.LENGTH_SHORT).show();
            puzzleHandler.insertScore(UserId, stepCount, timeCount,UserName);

            for (int i = 0; i < group.getChildCount(); i++) {
                buttons[i / 3][i % 3].setClickable(false);
                timer.cancel();
                btnStop.setEnabled(false);
                btnShuffle.setEnabled(false);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Congratulations!");
                alertDialog.setMessage("You won the game!\n" +
                        "Moves: " + stepCount + "\n" +
                        "Time Taken: " + String.format("%02d:%02d", timeCount / 60, timeCount % 60));


                alertDialog.setPositiveButton("OK", (dialog, which) -> {
                    // Handle the OK button click, if needed.
                    dialog.dismiss();
                });
                alertDialog.create().show();
            }
        }
    }
}