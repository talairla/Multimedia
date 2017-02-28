package com.example.teo.multimedia;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.MediaController;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener, MediaController.MediaPlayerControl, View.OnTouchListener {

    MediaPlayer mediaPlayer;
    MediaController mediaController;
    SurfaceView videoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton ibPlay = (ImageButton) findViewById(R.id.ib_play);
        ImageButton ibPause = (ImageButton) findViewById(R.id.ib_pause);
        ImageButton ibStop = (ImageButton) findViewById(R.id.ib_stop);
        videoView = (SurfaceView) findViewById(R.id.surfaceView);
        ibPlay.setOnClickListener(this);
        ibPause.setOnClickListener(this);
        ibStop.setOnClickListener(this);
        videoView.setOnTouchListener(this);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(this, Uri.parse("https://www.w3schools.com/html/horse.ogg"));
            mediaPlayer.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }
        //mediaPlayer = MediaPlayer.create(this,R.raw.of2_02);

        mediaController = new MediaController(this);
        mediaController.setMediaPlayer(this);
        mediaController.setEnabled(true);
        mediaController.setAnchorView(videoView);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_play:
                if(mediaPlayer.isPlaying()){
                    Toast.makeText(this,"Ya en reproducción",Toast.LENGTH_SHORT).show();
                }else {
                    mediaPlayer.start();
                }
                break;
            case R.id.ib_pause:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }else {
                    Toast.makeText(this,"El reproductor no está reproduciendo",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ib_stop:
                if(mediaPlayer != null && mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    try {
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }

    }

    @Override
    public void start() {
        if(mediaPlayer.isPlaying()){
            Toast.makeText(this,"Ya en reproducción",Toast.LENGTH_SHORT).show();
        }else {
            mediaPlayer.start();
        }
    }

    @Override
    public void pause() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }else {
            Toast.makeText(this,"El reproductor no está reproduciendo",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return mediaPlayer.getAudioSessionId();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.surfaceView){
            if ( mediaController != null){
                mediaController.show();
            }
        }
        return false;
    }
}
