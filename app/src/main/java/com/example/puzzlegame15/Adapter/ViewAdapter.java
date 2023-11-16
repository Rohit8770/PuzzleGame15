package com.example.puzzlegame15.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.puzzlegame15.Model.HighScoreModel;
import com.example.puzzlegame15.R;
import com.example.puzzlegame15.Table.PuzzleHandler;

import java.util.ArrayList;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.HistoryViewHolder>{

    Context context;
    ArrayList<HighScoreModel> scoreModels;

    public ViewAdapter(Context context, ArrayList<HighScoreModel> scoreModels) {
        this.context = context;
        this.scoreModels = scoreModels;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.view_item_file,parent,false);
        return new HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        PuzzleHandler puzzleHandler =new PuzzleHandler(context);
        HighScoreModel highScoreModel= scoreModels.get(position);
        holder.txt_User_id.setText(String.valueOf(highScoreModel.getUser_id()));
        holder.txt_Time.setText(String.valueOf(highScoreModel.getTime()));
        holder.txt_Moves.setText(String.valueOf(highScoreModel.getMove()));
     //   holder.txt_userName.setText(String.valueOf(highScoreModel.getUserName()));
    }

    @Override
    public int getItemCount() {
        return scoreModels.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView txt_User_id,txt_Time,txt_Moves,txt_userName;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_User_id=itemView.findViewById(R.id.txt_User_id);
            txt_Time=itemView.findViewById(R.id.txt_Time);
            txt_Moves=itemView.findViewById(R.id.txt_Moves);
            txt_userName=itemView.findViewById(R.id.txt_userName);
        }
    }
}
