package com.example.puzzlegame15.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.puzzlegame15.Model.PuzzleModel;
import com.example.puzzlegame15.R;
import com.example.puzzlegame15.Table.PuzzleHandler;
import com.example.puzzlegame15.Utils.Tools;
import com.example.puzzlegame15.puzzle.PuzzleActivity;
import com.example.puzzlegame15.puzzle.UserActivity;
import com.example.puzzlegame15.puzzle.ViewActivity;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserDataHolder>{

    Context context;
     private  ArrayList<PuzzleModel> puzzleModelArrayList;
     Tools tools;


    public UserAdapter(Context context, ArrayList<PuzzleModel> puzzleModelArrayList) {
        this.context = context;
        this.puzzleModelArrayList = puzzleModelArrayList;
    }
    public void updateData(ArrayList<PuzzleModel> puzzleModelArrayList1) {
        puzzleModelArrayList.clear();
        puzzleModelArrayList.addAll(puzzleModelArrayList1);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.user_item_file,parent,false);
        return  new UserDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserDataHolder holder, int position) {
        tools = new Tools();
        PuzzleHandler puzzleHandler =new PuzzleHandler(holder.itemView.getContext());
        PuzzleModel puzzleModel= puzzleModelArrayList.get(position);
        holder.txName.setText(puzzleModel.getName());
        Tools.displayImage(context, holder.imgtx, puzzleModel.getImage());

        holder.txPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PuzzleActivity.class);
                int userId = (int) puzzleModel.getId(); // Check the value of userId
                i.putExtra("userId", userId);
          //      Toast.makeText(context, "User ID: " + userId, Toast.LENGTH_SHORT).show();
                i.putExtra("Name", puzzleModel.getName());
                i.putExtra("Image", puzzleModel.getImage());
                context.startActivity(i);
            }
        });

        holder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, ViewActivity.class);
                int userId = (int) puzzleModel.getId(); // Check the value of userId
                i.putExtra("userId", userId);
                Toast.makeText(context, ""+puzzleModel.getId(), Toast.LENGTH_SHORT).show();
                i.putExtra("Name",puzzleModel.getName());
                i.putExtra("Image", puzzleModel.getImage());
                context.startActivity(i);
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog =new AlertDialog.Builder(context);
                alertDialog.setTitle("Alert!!");
                alertDialog.setMessage("Are you sure, you want to delete " +puzzleModel.getName() );
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long puzzleId = puzzleModelArrayList.get(position).getId();
                        PuzzleHandler puzzleHandler = new PuzzleHandler(context);

                        // Call the deleteUser method from the handler
                        puzzleHandler.deleteUser(puzzleId);

                        // Remove the item from the list
                        puzzleModelArrayList.remove(position);
                        notifyDataSetChanged();
                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
     return puzzleModelArrayList.size();
    }

    public class UserDataHolder extends RecyclerView.ViewHolder {
        ImageView imgtx,imgView,imgDelete;
        TextView txName,txPlay;
        public UserDataHolder(@NonNull View itemView) {
            super(itemView);
            imgtx=itemView.findViewById(R.id.imgtx);
            txName=itemView.findViewById(R.id.txName);
            txPlay=itemView.findViewById(R.id.txPlay);
            imgView=itemView.findViewById(R.id.imgView);
            imgDelete=itemView.findViewById(R.id.imgDelete);
        }
    }
}
