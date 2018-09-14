package com.techiespace.projects.hark;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class EvaluateClipActivity extends YouTubeBaseActivity {

    TextView score;
    TextView instruction;
    EditText usrTranscript;
    TextView textviewUsrTranscript;
    TextView textviewOriginalTranscript;
    LinearLayout linearlayoutCompareTranscripts;
    Date startTime;
    Date stopTime;
    TextView timeTextView;
    String originalXMLTranscript = "";
    String originalTranscript = "";
    String videoId;

    LinearLayout parentForEditTextView;

    Button minusTenButton;
    Button minusFiveButton;
    Button plusFiveButton;
    Button plusTenButton;
    Button pausePlayButton;

    Button evaluateButton;

    private Handler mHandler = null;
    YouTubePlayer mPlayer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_clip);
        instruction = findViewById(R.id.textview_instruction);
        parentForEditTextView = findViewById(R.id.linearlayout_parent_for_edittextview);

        minusTenButton = findViewById(R.id.button_time_minus_ten);
        minusFiveButton = findViewById(R.id.button_time_minus_five);
        plusFiveButton = findViewById(R.id.button_time_plus_five);
        plusTenButton = findViewById(R.id.button_time_plus_ten);
        pausePlayButton = findViewById(R.id.button_pause_play);

        timeTextView = findViewById(R.id.textview_time);

        usrTranscript = findViewById(R.id.edittext_transcript);
        evaluateButton = findViewById(R.id.button_evaluate);

        videoId = getIntent().getStringExtra("id");
        String stopPoints = getIntent().getStringExtra("stop_points");
        String[] stopPointsArr = stopPoints.split(" ");
        instruction.setText("Type what you hear from " + stopPointsArr[0] + " to " + stopPointsArr[1]);
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            startTime = sdf.parse(stopPointsArr[0]);
            stopTime = sdf.parse(stopPointsArr[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final YouTubePlayerView youtubePlayerView = findViewById(R.id.youtubePlayerView);

        playVideo(videoId, youtubePlayerView);

        mHandler = new Handler();

        //Download transcript
        new DownloadFileFromURL().execute("http://video.google.com/timedtext?lang=en&v=" + videoId);
    }

    public void playVideo(final String videoId, YouTubePlayerView youTubePlayerView) {
        youTubePlayerView.initialize("AIzaSyAk86K59BVMbKdAR-eB5kb0fc2mTkt7eMw",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(final YouTubePlayer.Provider provider,
                                                        final YouTubePlayer youTubePlayer, boolean b) {
                        if (youTubePlayer == null) return;
                        mPlayer = youTubePlayer;
                        displayCurrentTime();
                        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                        //youTubePlayer.cueVideo(videoId, stopArr[0]);
                        youTubePlayer.loadVideo(videoId, (int) startTime.getTime());
                        //youTubePlayer.play();
                        youTubePlayer.setPlayerStateChangeListener(mPlayerStateChangeListener);
                        youTubePlayer.setPlaybackEventListener(mPlaybackEventListener);

                        //onClickListeners for Playback
                        minusTenButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                youTubePlayer.seekRelativeMillis(-10000);
                            }
                        });
                        minusFiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                youTubePlayer.seekRelativeMillis(-5000);
                            }
                        });
                        plusFiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                youTubePlayer.seekRelativeMillis(5000);
                            }
                        });
                        plusTenButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                youTubePlayer.seekRelativeMillis(10000);
                            }
                        });
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                    }
                });
    }

    private void displayCurrentTime() { //TODO: https://stackoverflow.com/questions/4794484/calculate-text-size-according-to-width-of-text-area
        if (null == mPlayer) return;
        String formattedTime = formatTime(mPlayer.getCurrentTimeMillis());
        timeTextView.setText(formattedTime);
        if (mPlayer.getCurrentTimeMillis() < (int) startTime.getTime()) {
            mPlayer.seekToMillis((int) startTime.getTime());
        } else if (mPlayer.getCurrentTimeMillis() > (int) stopTime.getTime()) {
            mPlayer.seekToMillis((int) stopTime.getTime());
            mPlayer.pause();
        }
    }

    private String formatTime(int millis) {
        int seconds = millis / 1000;
        int minutes = seconds / 60;
        int hours = minutes / 60;

        return (hours == 0 ? "" : hours + ":") + String.format("%02d:%02d", minutes % 60, seconds % 60);
    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            displayCurrentTime();
            mHandler.postDelayed(this, 100);
        }
    };
    YouTubePlayer.PlaybackEventListener mPlaybackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {
        }

        @Override
        public void onPaused() {
            mHandler.removeCallbacks(runnable);
        }

        @Override
        public void onPlaying() {
            mHandler.postDelayed(runnable, 100);
            displayCurrentTime();
        }

        @Override
        public void onSeekTo(int arg0) {
            mHandler.postDelayed(runnable, 100);
        }

        @Override
        public void onStopped() {
            mHandler.removeCallbacks(runnable);
        }
    };

    YouTubePlayer.PlayerStateChangeListener mPlayerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }

        @Override
        public void onLoaded(String arg0) {
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
        }

        @Override
        public void onVideoStarted() {
        }
    };

    public void evaluate(View view) {
        //layout change

        score = findViewById(R.id.textview_accuracy);

        textviewUsrTranscript = findViewById(R.id.textview_usr_transcript);
        textviewOriginalTranscript = findViewById(R.id.textview_original_transcript);
        linearlayoutCompareTranscripts = findViewById(R.id.linearlayout_compare_transcripts);

        EvaluateClip evaluateClip = new EvaluateClip(originalTranscript, usrTranscript.getText().toString());
        score.setText("Accuracy: " + evaluateClip.evaluate());

        parentForEditTextView.removeView(usrTranscript);
        textviewOriginalTranscript.setText(originalTranscript);
        textviewUsrTranscript.setText(usrTranscript.getText().toString());
        linearlayoutCompareTranscripts.setVisibility(View.VISIBLE);
        mPlayer.pause();
        evaluateButton.setVisibility(View.GONE);
    }

    public void DownloadFiles() {

        try {
            URL u = new URL("http://video.google.com/timedtext?lang=en&v=" + videoId);
            URLConnection dataConnection = u.openConnection();
            BufferedReader r = new BufferedReader(
                    new InputStreamReader(dataConnection.getInputStream()));
            String line;
            while ((line = r.readLine()) != null) {
                originalXMLTranscript += line + "\n";
            }
            Log.e("test", "DownloadFiles: " + originalXMLTranscript);

        } catch (MalformedURLException mue) {
            Log.e("SYNC getUpdate", "malformed url error", mue);
        } catch (IOException ioe) {
            Log.e("SYNC getUpdate", "io error", ioe);
        } catch (SecurityException se) {
            Log.e("SYNC getUpdate", "security error", se);
        }
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            DownloadFiles();
            ParseXML parseXML = new ParseXML(originalXMLTranscript, startTime, stopTime);
            try {
                originalTranscript = parseXML.parseXML();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}