package com.example.puzzlegame15.Model;

public class HighScoreModel {

    private int score_id;
    private int user_id;
    private int time;
    private int move;
    //  private String userName;

    public HighScoreModel(int score_id, int user_id, int time, int move) {
        this.score_id = score_id;
        this.user_id = user_id;
        this.time = time;
        this.move = move;
        //   this.userName = userName;
    }

    public int getScore_id() {
        return score_id;
    }

    public void setScore_id(int score_id) {
        this.score_id = score_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

}
