package com.techiespace.projects.hark;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.techiespace.projects.hark.databinding.ClipRowBinding;
import com.techiespace.projects.hark.db.ClipDatabase;
import com.techiespace.projects.hark.db.Clips;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class ClipsAdapter extends RecyclerView.Adapter<ClipsAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    List<Clips> clipsList;

    String originalXMLTranscript = "";
    private ProgressDialog pDialog;

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
                new DownloadFileFromURL(context,
                        clipsList.get(holder.getAdapterPosition()).getClipID(),
                        clipsList.get(holder.getAdapterPosition()).getStopPoints()).execute("http://video.google.com/timedtext?lang=en&v=",
                        clipsList.get(holder.getAdapterPosition()).getClipID(),
                        clipsList.get(holder.getAdapterPosition()).getStopPoints());
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

    public void DownloadFiles(String url) {

        try {
            URL u = new URL(url);
            URLConnection dataConnection = u.openConnection();
            BufferedReader r = new BufferedReader(
                    new InputStreamReader(dataConnection.getInputStream()));
            String line;
            while ((line = r.readLine()) != null) {
                originalXMLTranscript += line + "\n";
            }
            Log.e("test", "Download Files: " + originalXMLTranscript);

        } catch (MalformedURLException mue) {
            Log.e("SYNC getUpdate", "malformed url error", mue);
        } catch (IOException ioe) {
            Log.e("SYNC getUpdate", "io error", ioe);
        } catch (SecurityException se) {
            Log.e("SYNC getUpdate", "security error", se);
        }
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        //Context context;
        String id;
        String stopPoints;

        private DownloadFileFromURL(Context context, String id, String stopPoints) {
            //this.context = context.getApplicationContext();
            this.id = id;
            this.stopPoints = stopPoints;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("Starting download");
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Loading... Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);   //Warning: irritates user on slow internet connection(no option to retry).
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            originalXMLTranscript = ""; //reset string when coming out of another video and going into another
            DownloadFiles(strings[0] + strings[1]);
            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            System.out.println("Downloaded");
            if ((pDialog != null) && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (originalXMLTranscript.equals(""))
                Toast.makeText(context, "Unable to retrieve data from Internet", Toast.LENGTH_SHORT).show();
            else {
                if (!originalXMLTranscript.equals("")) {
                    Intent intent = new Intent(context, SectionsActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("stop_points", stopPoints);
                    intent.putExtra("xml_transcript", originalXMLTranscript);
                    context.startActivity(intent);
                }
            }
        }
    }
}
