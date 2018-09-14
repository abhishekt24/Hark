package com.techiespace.projects.hark;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SectionsActivity extends AppCompatActivity {

    String stopPoints;
    String videoId;
    String[] stopPointsArr;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sections);
        videoId = getIntent().getStringExtra("id");
        stopPoints = getIntent().getStringExtra("stop_points");
        stopPointsArr = stopPoints.split(" ");
        intent = new Intent(this, EvaluateClipActivity.class);
        intent.putExtra("id", videoId);
    }

    public void onClickSection1(View view) {
        intent.putExtra("stop_points", stopPointsArr[0] + " " + stopPointsArr[1]);
        this.startActivity(intent);
    }

    public void onClickSection2(View view) {
        intent.putExtra("stop_points", stopPointsArr[2] + " " + stopPointsArr[3]);
        this.startActivity(intent);
    }

    public void onClickSection3(View view) {
        intent.putExtra("stop_points", stopPointsArr[4] + " " + stopPointsArr[5]);
        this.startActivity(intent);
    }

    public void onClickSection4(View view) {
        intent.putExtra("stop_points", stopPointsArr[6] + " " + stopPointsArr[7]);
        this.startActivity(intent);
    }

    public void onClickSection5(View view) {
        intent.putExtra("stop_points", stopPointsArr[8] + " " + stopPointsArr[9]);
        this.startActivity(intent);
    }

    public void onClickSection6(View view) {
        intent.putExtra("stop_points", stopPointsArr[10] + " " + stopPointsArr[11]);
        this.startActivity(intent);
    }

    public void onClickSectionTest(View view) {
        intent.putExtra("stop_points", stopPointsArr[12] + " " + stopPointsArr[13]);
        this.startActivity(intent);
    }
}
