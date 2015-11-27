package com.water.example;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.water.amraudiorecorder.AMRAudioRecorder;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private TextView mRecordingTime;

    private AMRAudioRecorder mRecorder;
    private EasyTimer mAudioTimeLabelUpdater;
    private int mRecordTimeInterval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecordingTime = (TextView)findViewById(R.id.recordingTime);
    }

    public void viewOnClick(View view) {
        switch (view.getId()) {

            case R.id.toggleRecord:

                if (mRecorder == null) {

                    resetRecording();

                    String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                    String recordingDirectory = sdcardPath + "/wtrecorder/";
                    File dir = new File(recordingDirectory);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    mRecorder = new AMRAudioRecorder(recordingDirectory);
                    mRecorder.start();

                    ((ImageButton) view).setImageResource(R.drawable.ic_pause);

                    mAudioTimeLabelUpdater = new EasyTimer(1000, new EasyTimer.CallBack() {
                        @Override
                        public void execute() {
                            int time = mRecordTimeInterval;

                            int min = time / 60,sec = time % 60;

                            String minStr = min < 10 ? "0"+min : ""+min;
                            String secStr = sec < 10 ? "0"+sec : ""+sec;

                            mRecordingTime.setText(minStr + ":"+secStr);

                            mRecordTimeInterval ++;
                        }
                    });
                }
                else {
                    if (mRecorder.isRecording()) {
                        ((ImageButton) view).setImageResource(R.drawable.ic_play);

                        mAudioTimeLabelUpdater.stop();
                        mRecorder.pause();
                    }
                    else {
                        ((ImageButton) view).setImageResource(R.drawable.ic_pause);

                        mRecorder.resume();
                        mAudioTimeLabelUpdater.restart();
                    }
                }

                break;

            case R.id.done:

                if (mRecorder == null) {
                    return;
                }

                mRecorder.stop();

                Intent intent = new Intent(this,PlaybackActivity.class);
                intent.putExtra("audioFilePath",mRecorder.getAudioFilePath());
                startActivity(intent);

                resetRecording();

                break;

            case R.id.trash:

                resetRecording();

                break;
        }
    }

    private void resetRecording () {
        if (mAudioTimeLabelUpdater != null) {
            mAudioTimeLabelUpdater.stop();
            mAudioTimeLabelUpdater = null;
        }

        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder = null;
        }

        mRecordTimeInterval = 0;
        mRecordingTime.setText("00:00");

        ((ImageButton)findViewById(R.id.toggleRecord)).setImageResource(R.drawable.ic_play);
    }
}
