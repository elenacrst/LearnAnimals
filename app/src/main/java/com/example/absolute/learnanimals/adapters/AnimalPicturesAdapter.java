package com.example.absolute.learnanimals.adapters;

/**
 * Created by Absolute on 4/18/2018.
 */

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.absolute.learnanimals.R;
import com.example.absolute.learnanimals.model.Animal;
import com.example.absolute.learnanimals.ui.MainActivity;
import com.example.absolute.learnanimals.utils.PrefUtils;

public class AnimalPicturesAdapter extends BaseAdapter {

    private final Context mContext;
    private final Animal[] animals;
    private MediaPlayer mediaPlayer;

    private static final String TAG= "adapters.AnimalPictureAdapter";

    public AnimalPicturesAdapter(Context context, Animal[] animals) {
        this.mContext = context;
        this.animals = animals;
    }

    @Override
    public int getCount() {
        return animals.length;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Animal animal = animals[position];

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.item_animal_pic, null);
        }

        final ImageView imageView = convertView.findViewById(R.id.image_animal);

        Glide.with(mContext).load(animal.getRes()).into(imageView);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(mContext, animal.getSoundRawRes());

                showDialogPicture(animal.getRes(),animal.getName(), animal.getSoundRawRes());

                ((MainActivity)mContext).findViewById(R.id.languages_recyclerview)
                        .setVisibility(View.GONE);

            }
        });
        return convertView;
    }

    private void playSound(final Context context, final int fileName) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        PrefUtils prefUtils = new PrefUtils((MainActivity)context);
        if (audioManager != null) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                    audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)*prefUtils.getVolume()/100,
                    AudioManager.FLAG_PLAY_SOUND);
        }

        Log.wtf(TAG,fileName+" sound file");
         if (mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer = null;
        }

        mediaPlayer= MediaPlayer.create(context, fileName);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you

    }

    private void showDialogPicture(int res, String name, final int sound) {
        LayoutInflater factory = LayoutInflater.from(mContext);
        final View deleteDialogView = factory.inflate(R.layout.dialog_animal, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(mContext).create();
        deleteDialog.setView(deleteDialogView);

        if (deleteDialog.getWindow()!=null){
            deleteDialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialog;
            Log.wtf(TAG,"not null window");
        }else Log.wtf(TAG,"null window");


        ImageView imageView = deleteDialogView.findViewById(R.id.image_rounded_animal);

        imageView.setBackgroundDrawable(mContext.getResources().getDrawable(res));


        TextView nameTextView = deleteDialogView.findViewById(R.id.text_animal_name);
        nameTextView.setText(name.toUpperCase());

        ImageView replayImage = deleteDialogView.findViewById(R.id.image_replay);
        replayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer!=null){
                    mediaPlayer.release();
                    mediaPlayer = null;
                }

                mediaPlayer= MediaPlayer.create(mContext,sound);
                mediaPlayer.start(); // no need to call prepare(); create() does that for you
            }
        });

        deleteDialog.show();
    }

}