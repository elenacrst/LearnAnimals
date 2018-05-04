package com.example.absolute.learnanimals.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.absolute.learnanimals.R;
import com.example.absolute.learnanimals.model.Animal;
import com.example.absolute.learnanimals.ui.GameActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Absolute on 4/23/2018.
 */

public class MatchesGridAdapter extends BaseAdapter {

    private Context mContext;
    private List<Animal> animals = new ArrayList<>();

    public MatchesGridAdapter(Context context, List<Animal> animals) {
        this.mContext = context;
        Collections.shuffle(animals);
        this.animals.addAll(animals);
    }

    @Override
    public int getCount() {
        return animals.size();
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

        final Animal animal = animals.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.item_match_animal, null);
        }
        Glide.with(mContext).load("https://i.imgur.com/GCup1vQ.png")
                .into((ImageView)convertView.findViewById(R.id.image_match));
        Glide.with(mContext).load(animal.getImageLink())
                .into((ImageView)convertView.findViewById(R.id.image_match_animal));
        convertView.findViewById(R.id.image_match).setVisibility(View.VISIBLE);
        convertView.findViewById(R.id.image_match_animal).setVisibility(View.INVISIBLE);

        convertView.setTag(animal.getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameActivity.setSelectedImage(animal.getImageLink(), view.getTag().toString(), mContext);

            }
        });
        return convertView;
    }

}
