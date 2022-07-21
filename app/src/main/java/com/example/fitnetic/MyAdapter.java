package com.example.fitnetic;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Users> usersArrayList;
    //Context context2;
    public MyAdapter(Context context, ArrayList<Users> usersArrayList, Context applicationContext) {
        this.context = context;
        this.usersArrayList = usersArrayList;
        //this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);





    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        final Users temp = usersArrayList.get(position);
        Users user = usersArrayList.get(position);
        holder.Name.setText(user.Name);
        holder.Qualification.setText(user.Qualification);
        holder.Experience.setText(user.Experience);
        holder.Fees.setText(user.Fees);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,profile.class);
                intent.putExtra("Name",temp.getName());
                intent.putExtra("Qualification",temp.getQualification());
                intent.putExtra("Experience",temp.getExperience());
                intent.putExtra("Fees",temp.getFees());
                intent.putExtra("Time",temp.getTime());
                intent.putExtra("Past Work",temp.getPastwork());
                intent.putExtra("Achievements",temp.getAchivements());
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }

        });




    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        TextView Name,Experience,Fees,Qualification;





        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.rec_name);
            Qualification = itemView.findViewById(R.id.rec_qualification);
            Experience = itemView.findViewById(R.id.rec_exp);
            Fees = itemView.findViewById(R.id.rec_fees);


        }
    }
}
