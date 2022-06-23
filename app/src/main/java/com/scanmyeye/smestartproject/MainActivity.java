package com.scanmyeye.smestartproject;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.scanmyeye.sdk.eyedetection_p2.AppCommons;
import com.scanmyeye.sdk.eyedetection_p2.SMESdk;
import com.scanmyeye.sdk.eyedetection_p2.StarterActivity;
import com.scanmyeye.sdk.eyedetection_p2.interfaces.SMESdkEventListener;
import com.scanmyeye.sdk.eyedetection_p2.model.SMEAnalysis;
import com.scanmyeye.sdk.eyedetection_p2.model.SMEError;

public class MainActivity extends AppCompatActivity implements SMESdkEventListener {

    ActivityResultLauncher<Intent> scanCataractLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), SMESdk.shared());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SMESdk.init("eyJjdXN0b21lcl9pZCI6MSwicmVnaXN0ZXJlZF9lbWFpbCI6InBhcnRoLmFiaHlhbmthckBnbWFpbC5jb20ifQ==");
        SMESdk.shared().setEventListener(this);
        //SMESdk.shared().startCataractScan(this, "1227", scanCataractLauncher);
    }

    @Override
    public void onSDKEventUpdated(SMEError error, SMEAnalysis analysis) {
        if (error != null) {
            System.out.println("error\t"+error);
        } else {
            AppCommons.AnalysisStatus status = analysis.getStatus();
            if (status == AppCommons.AnalysisStatus.Success) {

                SMESdk.shared().showResultsActivity(analysis, MainActivity.this);

            } else {

                System.out.println("status "+analysis.getStatus());
            }
        }
    }
}