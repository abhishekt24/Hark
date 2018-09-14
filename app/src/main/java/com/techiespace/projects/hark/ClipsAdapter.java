package com.techiespace.projects.hark;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techiespace.projects.hark.databinding.ClipRowBinding;
import com.techiespace.projects.hark.db.ClipDatabase;
import com.techiespace.projects.hark.db.Clips;

import java.util.List;

public class ClipsAdapter extends RecyclerView.Adapter<ClipsAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    List<Clips> clipsList;

    public ClipsAdapter(Context context) {
        this.context = context;
    }

    public void setClipsList(List<Clips> clipsList) {
        this.clipsList = clipsList;
        notifyDataSetChanged(); //This was the sole reason for bug #1 - No data displayed on first run of app
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ClipDatabase db = ClipDatabase.getDatabase(context);
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ClipRowBinding clipRowBinding = ClipRowBinding.inflate(
                layoutInflater, parent, false);

        return new MyViewHolder(clipRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Clips clips = clipsList.get(position);
        holder.bind(clips);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SectionsActivity.class);
                intent.putExtra("id", clipsList.get(holder.getAdapterPosition()).getClipID());
                intent.putExtra("stop_points", clipsList.get(holder.getAdapterPosition()).getStopPoints());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (clipsList != null) {
            return clipsList.size();
        } else
            return 0;
    }

    @Override
    public void onClick(View view) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ClipRowBinding binding;

        public MyViewHolder(ClipRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Clips clips) {
            binding.setVariable(BR.clipList, clips);
            binding.executePendingBindings();
        }
    }
}
