package com.techiespace.projects.hark;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.techiespace.projects.hark.databinding.ActivitySectionsBinding;

public class SectionsActivity extends AppCompatActivity {

    String stopPoints;
    String videoId;
    String[] stopPointsArr;
    Intent intent;
    String originalXMLTranscript = "";

    ActivitySectionsBinding sectionsActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sections);
        videoId = getIntent().getStringExtra("id");
        stopPoints = getIntent().getStringExtra("stop_points");
        originalXMLTranscript = getIntent().getStringExtra("xml_transcript");
        stopPointsArr = stopPoints.split(" ");
        intent = new Intent(this, EvaluateClipActivity.class);
        intent.putExtra("id", videoId);
        intent.putExtra("xml_transcript", originalXMLTranscript);
        SectionsViewModel sectionsViewModel = ViewModelProviders.of(this, new SectionsViewModelFactory(this.getApplication(), videoId)).get(SectionsViewModel.class);
        sectionsActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_sections);
        sectionsActivityBinding.setLifecycleOwner(this);
        sectionsActivityBinding.setSectionsViewModel(sectionsViewModel);
        setContentView(sectionsActivityBinding.getRoot());
        /*sectionsViewModel.getAccuracyLiveData().observe(SectionsActivity.this, new Observer<List<Clips>>() {
            @Override
            public void onChanged(@Nullable List<Clips> s) {
                CardView cardView1 = findViewById(R.id.bankcardId1);
                TextView acc1 = cardView1.findViewById(R.id.textview_accuracy);
                acc1.setText("1%");
            }
        });*/
    }

    public void onClickSection1(View view) {
        intent.putExtra("stop_points", stopPointsArr[0] + " " + stopPointsArr[1]);
        intent.putExtra("sec_id", 1);
        this.startActivity(intent);
    }

    public void onClickSection2(View view) {
        intent.putExtra("stop_points", stopPointsArr[2] + " " + stopPointsArr[3]);
        intent.putExtra("sec_id", 2);
        this.startActivity(intent);
    }

    public void onClickSection3(View view) {
        intent.putExtra("stop_points", stopPointsArr[4] + " " + stopPointsArr[5]);
        intent.putExtra("sec_id", 3);
        this.startActivity(intent);
    }

    public void onClickSection4(View view) {
        intent.putExtra("stop_points", stopPointsArr[6] + " " + stopPointsArr[7]);
        intent.putExtra("sec_id", 4);
        this.startActivity(intent);
    }

    public void onClickSection5(View view) {
        intent.putExtra("stop_points", stopPointsArr[8] + " " + stopPointsArr[9]);
        intent.putExtra("sec_id", 5);
        this.startActivity(intent);
    }

    public void onClickSection6(View view) {
        intent.putExtra("stop_points", stopPointsArr[10] + " " + stopPointsArr[11]);
        intent.putExtra("sec_id", 6);
        this.startActivity(intent);
    }

    public void onClickSectionTest(View view) {
        intent.putExtra("stop_points", stopPointsArr[12] + " " + stopPointsArr[13]);
        intent.putExtra("sec_id", 7);
        this.startActivity(intent);
    }
}
