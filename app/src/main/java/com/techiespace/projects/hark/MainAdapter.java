package com.techiespace.projects.hark;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> implements View.OnClickListener {

    Context context;
    String[] mainList;

    public MainAdapter(Context context, String[] mainList) {
        this.context = context;
        this.mainList = mainList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.textView.setText(mainList[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent intent = new Intent(context, ListClipsActivity.class);
                switch (position) {
                    case 0:
                        intent.putExtra("difficulty", "Rookie");
                        break;
                    case 1:
                        intent.putExtra("difficulty", "Beginner");
                        break;
                    case 2:
                        intent.putExtra("difficulty", "Intermediate");
                        break;
                    case 3:
                        intent.putExtra("difficulty", "Advanced");
                        break;
                    case 4:
                        intent.putExtra("difficulty", "Rap");
                        break;
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mainList.length;
    }

    @Override
    public void onClick(View v) {
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public MyViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textview_main);
        }
    }
}