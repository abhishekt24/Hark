package com.techiespace.projects.hark;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.techiespace.projects.hark.db.ClipDatabase;
import com.techiespace.projects.hark.db.ClipsDao;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EvaluateClipActivity extends YouTubeBaseActivity {

    TextView score;
    TextView instructionTime;
    TextView instructionWords;
    EditText usrTranscript;
    TextView textviewUsrTranscript;
    TextView textviewOriginalTranscript;
    LinearLayout linearlayoutCompareTranscripts;
    int startTime;
    int stopTime;
    TextView timeTextView;
    String originalXMLTranscript = "";
    String originalTranscript = "";
    String videoId;
    private static final Executor executor = Executors.newFixedThreadPool(2);

    LinearLayout parentForEditTextView;

    Button minusTenButton;
    Button minusFiveButton;
    Button plusFiveButton;
    Button plusTenButton;
    Button pausePlayButton;

    Button evaluateButton;

    private Handler mHandler = null;
    YouTubePlayer mPlayer;
    int sectionId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_clip);
        instructionTime = findViewById(R.id.textview_instruction);
        instructionWords = findViewById(R.id.textview_instruction_words);
        parentForEditTextView = findViewById(R.id.linearlayout_parent_for_edittextview);

        minusTenButton = findViewById(R.id.button_time_minus_ten);
        minusFiveButton = findViewById(R.id.button_time_minus_five);
        plusFiveButton = findViewById(R.id.button_time_plus_five);
        plusTenButton = findViewById(R.id.button_time_plus_ten);

        timeTextView = findViewById(R.id.textview_time);

        usrTranscript = findViewById(R.id.edittext_transcript);
        evaluateButton = findViewById(R.id.button_evaluate);

        videoId = getIntent().getStringExtra("id");
        sectionId = getIntent().getIntExtra("sec_id", 0);
        String stopPoints = getIntent().getStringExtra("stop_points");
        String[] stopPointsArr = stopPoints.split(" ");
        String[] simpleStopPointArr = convMilliToHuman(stopPointsArr);
        instructionTime.setText("Type what you hear from " + simpleStopPointArr[0] + " to " + simpleStopPointArr[1]);
        startTime = Integer.parseInt(stopPointsArr[0]);
        stopTime = Integer.parseInt(stopPointsArr[1]);
        final YouTubePlayerView youtubePlayerView = findViewById(R.id.youtubePlayerView);

        playVideo(videoId, youtubePlayerView);

        mHandler = new Handler();

        originalXMLTranscript = getIntent().getStringExtra("xml_transcript");
        ParseXML parseXML = new ParseXML(originalXMLTranscript, startTime, stopTime);
        try {
            originalTranscript = parseXML.parseXML();
            String[] originalTranscriptWordsArr = originalTranscript.split(" ");
            instructionWords.setText(originalTranscriptWordsArr[0] + " ... " + originalTranscriptWordsArr[originalTranscriptWordsArr.length - 1]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        //Download transcript
        //new DownloadFileFromURL().execute();
    }

    public String[] convMilliToHuman(String[] stopPointsArr) {
        float startMilli = Float.parseFloat(stopPointsArr[0]);
        int startMin = (int) startMilli / (60 * 1000);
        int startSec = (int) (startMilli / 1000) % 60;
        float stopMilli = Float.parseFloat(stopPointsArr[1]);
        int stopMin = (int) stopMilli / (60 * 1000);
        int stopSec = (int) (stopMilli / 1000) % 60;
        String startMinStr = startMin < 10 ? "0" + startMin : "" + startMin;
        String stopMinStr = stopMin < 10 ? "" + "0" + stopMin : "" + stopMin;
        String startSecStr = startSec < 10 ? "0" + startSec : "" + startSec;
        String stopSecStr = stopSec < 10 ? "0" + stopSec : "" + stopSec;
        return new String[]{startMinStr + ":" + startSecStr, stopMinStr + ":" + stopSecStr};
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
                        youTubePlayer.loadVideo(videoId, startTime);
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
        if (mPlayer.getCurrentTimeMillis() < startTime) {
            mPlayer.seekToMillis(startTime);
        } else if (mPlayer.getCurrentTimeMillis() > stopTime) {
            mPlayer.seekToMillis(stopTime);
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
        //TODO: implement better logic for checking internet connectivity for slow connections.
        if (originalTranscript == null || originalTranscript.trim().isEmpty()) {
            evaluateButton.setText("Please wait...");
            evaluateButton.setEnabled(false);
            //new DownloadFileFromURL().execute();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (originalTranscript == null || originalTranscript.trim().isEmpty()) {
                        Toast.makeText(EvaluateClipActivity.this, "Couldn't fetch transcript.\nIs your internet working?", Toast.LENGTH_LONG).show();
                        evaluateButton.setText("Evaluate");
                        evaluateButton.setEnabled(true);
                    } else {
                        evaluateFun();
                    }
                }
            }, 5000);
        }
        if (originalTranscript != null && !originalTranscript.trim().isEmpty())
            evaluateFun();
    }

    public void evaluateFun() {
        //layout change
        score = findViewById(R.id.textview_accuracy);
        textviewUsrTranscript = findViewById(R.id.textview_usr_transcript);
        textviewOriginalTranscript = findViewById(R.id.textview_original_transcript);
        linearlayoutCompareTranscripts = findViewById(R.id.linearlayout_compare_transcripts);

        EvaluateClip evaluateClip = new EvaluateClip(originalTranscript, usrTranscript.getText().toString());
        final String accuracy = String.valueOf(evaluateClip.evaluate());
        score.setText("Accuracy: " + accuracy + "%");

        parentForEditTextView.removeView(usrTranscript);
        textviewOriginalTranscript.setText(originalTranscript);
        textviewUsrTranscript.setText(usrTranscript.getText().toString());
        linearlayoutCompareTranscripts.setVisibility(View.VISIBLE);
        mPlayer.pause();
        evaluateButton.setVisibility(View.GONE);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                ClipsDao clipsDao = ClipDatabase.getDatabase(EvaluateClipActivity.this).clipsDao();
                String[] accArr = clipsDao.findAccById(videoId).split(" ");
                accArr[sectionId] = accuracy;
                String updateAcc = Arrays.toString(accArr)
                        .replaceAll(",", "")
                        .replaceAll("[\\[\\]]", "");
                clipsDao.updateAcc(updateAcc, videoId);
            }
        });
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
            Log.e("test", "Download Files: " + originalXMLTranscript);

        } catch (MalformedURLException mue) {
            Log.e("SYNC getUpdate", "malformed url error", mue);
        } catch (IOException ioe) {
            Log.e("SYNC getUpdate", "io error", ioe);
        } catch (SecurityException se) {
            Log.e("SYNC getUpdate", "security error", se);
        }
    }

    public void showTip(View view) {
        final Dialog tipDialog = new Dialog(this);
        tipDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        tipDialog.setCancelable(true);
        tipDialog.setCanceledOnTouchOutside(true);
        tipDialog.setContentView(R.layout.protip_layout);
        tipDialog.show();
        Button cancelDialogButton = tipDialog.findViewById(R.id.button_cancel);
        cancelDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipDialog.cancel();
            }
        });
    }

    public void showInstructions(View view) {
        final Dialog instDialog = new Dialog(this);
        instDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        instDialog.setCancelable(true);
        instDialog.setCanceledOnTouchOutside(true);
        instDialog.setContentView(R.layout.instructions_layout);
        instDialog.show();
        Button cancelDialogButton = instDialog.findViewById(R.id.button_cancel);
        cancelDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instDialog.cancel();
            }
        });
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