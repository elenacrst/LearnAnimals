package com.example.absolute.learnanimals.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.absolute.learnanimals.R;

import java.util.List;

/**
 * Created by Absolute on 4/17/2018.
 */

public class LanguagesAdapter extends RecyclerView.Adapter<LanguagesAdapter.MyViewHolder> {

    private List<String> mNames;
    private List<Integer> mFlags;
    private final OnLanguageItemClickListener mListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView flag;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.text_lang_name);
            flag = view.findViewById(R.id.image_lang_flag);
        }
    }

    public LanguagesAdapter(List<String> names, List<Integer> flags,
                            OnLanguageItemClickListener listener) {
        this.mNames = names;
        this.mFlags = flags;
        this.mListener = listener;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_language, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.name.setText(mNames.get(position));
        holder.flag.setImageResource(mFlags.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mListener.onItemClick(mFlags.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public interface OnLanguageItemClickListener {
        void onItemClick(int res);
    }
}