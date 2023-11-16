package com.example.puzzlegame15.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.puzzlegame15.Model.HighScoreModel;
import com.example.puzzlegame15.Model.PuzzleModel;
import com.example.puzzlegame15.R;
import com.example.puzzlegame15.Table.PuzzleHandler;
import com.google.protobuf.StringValue;

import java.util.ArrayList;
import java.util.List;

public class HighScoreAdapter extends RecyclerView.Adapter<HighScoreAdapter.HighDataHolder>{

    Context context;
    ArrayList<HighScoreModel > highScoreModelList;

    public HighScoreAdapter(Context context, ArrayList<HighScoreModel> highScoreModelList) {
        this.context = context;
        this.highScoreModelList = highScoreModelList;
    }

    @NonNull
    @Override
    public HighDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.high_item_file,parent,false);
        return new HighDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HighDataHolder holder, int position) {
        PuzzleHandler puzzleHandler =new PuzzleHandler(context);
        HighScoreModel highScoreModel= highScoreModelList.get(position);


        holder.tx_User_id.setText(String.valueOf(highScoreModel.getUser_id()));
        holder.tx_Time.setText(String.valueOf(highScoreModel.getTime()));
        holder.tx_Moves.setText(String.valueOf(highScoreModel.getMove()));
     //   holder.tx_UserName.setText(String.valueOf(highScoreModel.getUserName()));
    }

    @Override
    public int getItemCount() {
        return highScoreModelList.size();
    }

    public  class HighDataHolder extends RecyclerView.ViewHolder {
        TextView tx_User_id,tx_Time,tx_Moves,tx_UserName;
        public HighDataHolder(@NonNull View itemView) {
            super(itemView);

            tx_User_id=itemView.findViewById(R.id.tx_User_id);
            tx_Time=itemView.findViewById(R.id.tx_Time);
            tx_Moves=itemView.findViewById(R.id.tx_Moves);
            tx_UserName=itemView.findViewById(R.id.tx_UserName);

        }
    }
}
