package com.developes.musicplayer;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    Button btn_next,btn_previous,btn_pause;
    TextView songTextLable;
    SeekBar songSeekbar;

    static MediaPlayer myMediaPayer;
    int position;
    String sname;
    ArrayList<File>mySongs;
    Thread updateseekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        btn_next=(Button)findViewById(R.id.next);
        btn_previous=(Button)findViewById(R.id.previous);
        btn_pause=(Button)findViewById(R.id.pause);

        songTextLable = (TextView)findViewById(R.id.mySonglable);

        songSeekbar = (SeekBar)findViewById(R.id.seekbar);

        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        updateseekBar = new Thread(){

            @Override
            public void run() {

                int totalDuration = myMediaPayer.getDuration();
                int currentPosition= 0;

                while (currentPosition<totalDuration){
                    try{
                        sleep(500);
                        currentPosition = myMediaPayer.getCurrentPosition();
                        songSeekbar.setProgress(currentPosition);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };

        if(myMediaPayer!=null){
            myMediaPayer.stop();
            myMediaPayer.release();
        }

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        mySongs=(ArrayList)bundle.getParcelableArrayList("songs");

        sname = mySongs.get(position).getName().toString();

        String songName = i.getStringExtra("songname");

        songTextLable.setText(songName);
        songTextLable.setSelected(true);

        position = bundle.getInt("pos",0);

        Uri u = Uri.parse(mySongs.get(position).toString());

        myMediaPayer = MediaPlayer.create(getApplicationContext(),u);

        myMediaPayer.start();
        songSeekbar.setMax(myMediaPayer.getDuration());


        updateseekBar.start();

        songSeekbar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary),PorterDuff.Mode.MULTIPLY);
        songSeekbar.getThumb().setColorFilter(getResources().getColor(R.color.colorPrimary),PorterDuff.Mode.SRC_IN);

        songSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                myMediaPayer.seekTo(seekBar.getProgress());
            }
        });


        btn_pause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                songSeekbar.setMax(myMediaPayer.getDuration());

                if(myMediaPayer.isPlaying()){
                    btn_pause.setBackgroundResource(R.drawable.ic_play_button_svgrepo_com);
                    myMediaPayer.pause();
                }
                else
                {
                    btn_pause.setBackgroundResource(R.drawable.ic_pause_svgrepo_com);
                    myMediaPayer.start();
                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    myMediaPayer.stop();
                    myMediaPayer.release();
                    position = ((position+1)%mySongs.size());

                    Uri u =  Uri.parse(mySongs.get(position).toString());

                    myMediaPayer = MediaPlayer.create(getApplicationContext(),u);

                    sname = mySongs.get(position).getName().toString();
                    songTextLable.setText(sname);

                    myMediaPayer.start();
            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myMediaPayer.stop();
                myMediaPayer.release();
                position = ((position-1)<0)?(mySongs.size()-1):(position-1);

                Uri u =  Uri.parse(mySongs.get(position).toString());

                myMediaPayer = MediaPlayer.create(getApplicationContext(),u);

                sname = mySongs.get(position).getName().toString();
                songTextLable.setText(sname);

                myMediaPayer.start();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()== android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
