package com.example.absolute.learnanimals.ui;

import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.absolute.learnanimals.R;
import com.example.absolute.learnanimals.model.Puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PuzzleGameActivity extends AppCompatActivity {

    private TextView mToolbarTextView;

    private RelativeLayout mCoverContainer;
    private ImageView mResultImageView;
    private TextView mProgressTextView;
    private ImageView mFullImageView, mPartialImageView;
    private Button mFirstAnswerButton, mSecondAnswerButton, mThirdAnswerButton;
    private List<Puzzle> mPuzzles= new ArrayList<>();
    private int mCurrentPosition;
    private ImageView mNextImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_game);

        findViews();
        setupToolbar();
        prepareData();
        populateUI();

        mFirstAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAnswerChosen((Button)view);
                mFirstAnswerButton.setEnabled(false);
                mSecondAnswerButton.setEnabled(false);
                mThirdAnswerButton.setEnabled(false);
                mSecondAnswerButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_rec_disabled_answer));
                mThirdAnswerButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_rec_disabled_answer));
            }
        });
        mSecondAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAnswerChosen((Button)view);
                mFirstAnswerButton.setEnabled(false);
                mSecondAnswerButton.setEnabled(false);
                mThirdAnswerButton.setEnabled(false);
                mFirstAnswerButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_rec_disabled_answer));
                mThirdAnswerButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_rec_disabled_answer));
            }
        });
        mThirdAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAnswerChosen((Button)view);
                mFirstAnswerButton.setEnabled(false);
                mSecondAnswerButton.setEnabled(false);
                mThirdAnswerButton.setEnabled(false);
                mSecondAnswerButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_rec_disabled_answer));
                mFirstAnswerButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_rec_disabled_answer));
            }
        });

    }

    private void setupToolbar() {
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        if (getSupportActionBar()!=null)getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Sansation-Regular.ttf");
        mToolbarTextView.setTypeface(face);
    }

    private void prepareData() {
        mPuzzles = new ArrayList<>();
        mPuzzles.add(new Puzzle(R.drawable.bear,getString(R.string.bear),new String[]{getString(R.string.bear),getString(R.string.duck),getString(R.string.wolf)}));
        mPuzzles.add(new Puzzle(R.drawable.bee,getString(R.string.bee), new String[]{getString(R.string.bee),getString(R.string.lion),getString(R.string.tiger)}));
        mPuzzles.add(new Puzzle(R.drawable.cat,getString(R.string.cat), new String[]{getString(R.string.cat),getString(R.string.dog),getString(R.string.horse)}));
        mPuzzles.add(new Puzzle(R.drawable.chicken,getString(R.string.chicken),new String[]{getString(R.string.chicken),getString(R.string.elephant),getString(R.string.duck)}));
        mPuzzles.add(new Puzzle(R.drawable.cow,getString(R.string.cow),new String[]{getString(R.string.cat),getString(R.string.cow),getString(R.string.pig)}));
        mPuzzles.add(new Puzzle(R.drawable.dog,getString(R.string.dog),new String[]{getString(R.string.bear),getString(R.string.dog),getString(R.string.wolf)}));
        mPuzzles.add(new Puzzle(R.drawable.duck, getString(R.string.duck), new String[]{getString(R.string.dog),getString(R.string.duck),getString(R.string.pig)}));
        mPuzzles.add(new Puzzle(R.drawable.eagle, getString(R.string.eagle), new String[]{getString(R.string.eagle),getString(R.string.pig),getString(R.string.sheep)}));
        mPuzzles.add(new Puzzle(R.drawable.elephant,getString(R.string.elephant),new String[]{getString(R.string.cow),getString(R.string.elephant),getString(R.string.horse)}));
        mPuzzles.add(new Puzzle(R.drawable.horse, getString(R.string.horse), new String[]{getString(R.string.horse), getString(R.string.bear), getString(R.string.tiger)}));
        Collections.shuffle(mPuzzles);
    }

    private void findViews() {
        mToolbarTextView =  findViewById(R.id.text_toolbar);
        mCoverContainer = findViewById(R.id.cover);
        mResultImageView = findViewById(R.id.image_result);
        mProgressTextView = findViewById(R.id.text_progress);
        mFullImageView = findViewById(R.id.image_complete_animal);
        mPartialImageView = findViewById(R.id.image_animal_part);
        mFirstAnswerButton = findViewById(R.id.button_first_answer);
        mSecondAnswerButton = findViewById(R.id.button_second_answer);
        mThirdAnswerButton = findViewById(R.id.button_third_answer);
        mNextImageView = findViewById(R.id.image_next);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onNext(View view) {
        if (mCurrentPosition==mPuzzles.size()-1){
            //end
            showDialogRetry();
        }else {
            mCurrentPosition++;
            populateUI();
            mFirstAnswerButton.setEnabled(true);
            mSecondAnswerButton.setEnabled(true);
            mThirdAnswerButton.setEnabled(true);
        }
    }

    private void showDialogRetry() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.dialog_game_over_puzzle, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setView(deleteDialogView);
        deleteDialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                deleteDialog.dismiss();
                Collections.shuffle(mPuzzles);
                mCurrentPosition = 0;
                populateUI();
            }
        });
        deleteDialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
                finish();
            }
        });

        deleteDialog.show();
        deleteDialog.setCancelable(false);
        deleteDialog.setCanceledOnTouchOutside(false);
    }

    private void populateUI(){
        mCoverContainer.setVisibility(View.GONE);
        mProgressTextView.setText(String.format(getString(R.string.title_animal_count),
                mCurrentPosition + 1,
                mPuzzles.size()));
        mFirstAnswerButton.setText(mPuzzles.get(mCurrentPosition).getAnswers()[0]);
        mSecondAnswerButton.setText(mPuzzles.get(mCurrentPosition).getAnswers()[1]);
        mThirdAnswerButton.setText(mPuzzles.get(mCurrentPosition).getAnswers()[2]);
        Glide.with(this).load(mPuzzles.get(mCurrentPosition).getImageRes())
                .into(mFullImageView);
        mThirdAnswerButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_rounded_rectangle));
        mSecondAnswerButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_rounded_rectangle));
        mFirstAnswerButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_rounded_rectangle));
        mPartialImageView.setVisibility(View.VISIBLE);
    }

    private void onAnswerChosen(Button chosen){
        mCoverContainer.setVisibility(View.VISIBLE);
        mResultImageView.setVisibility(View.VISIBLE);
        if (chosen.getText().toString().toLowerCase().equals(mPuzzles.get(mCurrentPosition).getAnimalName())){
            //right
            chosen.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_rounded_rectangle));
            Glide.with(this).load(R.drawable.right).into(mResultImageView);
        }else{
            //wrong
            chosen.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_rec_wrong ));
            Glide.with(this).load(R.drawable.wrong).into(mResultImageView);
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCoverContainer.setVisibility(View.GONE);
                mResultImageView.setVisibility(View.GONE);
                mFullImageView.setVisibility(View.VISIBLE);
                mPartialImageView.setVisibility(View.GONE);
            }
        },500);
    }
}
