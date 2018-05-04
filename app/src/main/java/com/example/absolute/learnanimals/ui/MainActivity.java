package com.example.absolute.learnanimals.ui;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.absolute.learnanimals.R;
import com.example.absolute.learnanimals.adapters.AnimalPicturesAdapter;
import com.example.absolute.learnanimals.adapters.LanguagesAdapter;
import com.example.absolute.learnanimals.model.Animal;
import com.example.absolute.learnanimals.utils.NetworkUtils;
import com.example.absolute.learnanimals.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.animation.SpringForce.DAMPING_RATIO_LOW_BOUNCY;
import static android.support.animation.SpringForce.STIFFNESS_LOW;
import static android.support.animation.SpringForce.STIFFNESS_VERY_LOW;

public class MainActivity extends AppCompatActivity {

    private List<String> mLanguagesList = new ArrayList<>();
    private List<Integer> mFlagsList = new ArrayList<>();

    private RecyclerView mRecyclerView;

    private LanguagesAdapter mAdapter;
    private CircleImageView mChosenFlag;
    private PrefUtils mPrefUtils;
    private RelativeLayout mRoot;
    private GridView mAnimalsGrid;
    private View mClickView;

    private AnimalPicturesAdapter mAnimalAdapter;
    private CircleImageView mVolumeImageView;

    private int mBackCount=0;
    private TextView mToolbarTextView;
    private Locale mLocale;
    private Toolbar mToolbar;
    private CircleImageView mMatchesImageView;
    private CircleImageView mPuzzleImageView;
    private CircleImageView mGamesImageView;
    private RelativeLayout mContainerBlack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setupToolbar();
        setupCurrentLanguage();
        loadLanguagesList();
        loadGrid();

        mChosenFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRecyclerView.getVisibility()==View.VISIBLE) mRecyclerView.setVisibility(View.GONE);
                else mRecyclerView.setVisibility(View.VISIBLE);
            }
        });
        mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRecyclerView.getVisibility()==View.VISIBLE) mRecyclerView.setVisibility(View.GONE);
            }
        });
        mClickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRecyclerView.getVisibility()==View.VISIBLE) mRecyclerView.setVisibility(View.GONE);
            }
        });
        mContainerBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRecyclerView.getVisibility()==View.VISIBLE) mRecyclerView.setVisibility(View.GONE);
            }
        });
        mVolumeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogVolume();
            }
        });
        mMatchesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkUtils.isNetworkConnected(MainActivity.this)) {
                    Intent openGameActivity=
                            new Intent(MainActivity.this, GameActivity.class);
                    startActivity(openGameActivity);
                }
                else Toast.makeText(MainActivity.this,"Internet connection is required.",Toast.LENGTH_SHORT)
                        .show();

            }
        });
        mPuzzleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGameActivity=
                        new Intent(MainActivity.this, PuzzleGameActivity.class);
                startActivity(openGameActivity);
            }
        });
        mContainerBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateGameViewReverse(mMatchesImageView,-190,-110);
                animateGameViewReverse(mPuzzleImageView,-190,90);
                mContainerBlack.setVisibility(View.GONE);
                Glide.with(MainActivity.this).load(R.drawable.games)
                        .into(mGamesImageView);
            }
        });

        mContainerBlack.setVisibility(View.GONE);
    }

    private void loadLanguagesList() {
        mLanguagesList.add(getString(R.string.english));
        mLanguagesList.add(getString(R.string.spanish));
        mLanguagesList.add(getString(R.string.french));
        mLanguagesList.add(getString(R.string.italian));
        mLanguagesList.add(getString(R.string.romanian));

        mFlagsList.add(R.drawable.gb);
        mFlagsList.add(R.drawable.es);
        mFlagsList.add(R.drawable.fr);
        mFlagsList.add(R.drawable.it);
        mFlagsList.add(R.drawable.ro);

        mRecyclerView.setVisibility(View.GONE);

        mAdapter = new LanguagesAdapter(mLanguagesList, mFlagsList,
                new LanguagesAdapter.OnLanguageItemClickListener() {
                    @Override
                    public void onItemClick(int res) {
                        mPrefUtils.saveLanguageRes(res);
                        mChosenFlag.setImageResource(res);
                        mRecyclerView.setVisibility(View.GONE);

                        switch (res){
                            case R.drawable.gb:
                                setLocale("gb");
                                break;
                            case R.drawable.es:
                                setLocale("es");
                                break;
                            case R.drawable.fr:
                                setLocale("fr");
                                break;
                            case R.drawable.ro:
                                setLocale("ro");
                                break;
                            case R.drawable.it:
                                setLocale("it");
                                break;
                        }
                        loadGrid();

                    }
                });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setupCurrentLanguage() {
        mPrefUtils = new PrefUtils(this);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(this).load(mPrefUtils.getLanguageRes())
                .apply(requestOptions).into(mChosenFlag);
        int res = mPrefUtils.getLanguageRes();
        switch (res){
            case R.drawable.gb:
                setLocale("gb");
                break;
            case R.drawable.es:
                setLocale("es");
                break;
            case R.drawable.fr:
                setLocale("fr");
                break;
            case R.drawable.ro:
                setLocale("ro");
                break;
            case R.drawable.it:
                setLocale("it");
                break;
        }
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Sansation-Regular.ttf");
        mToolbarTextView.setTypeface(face);
    }

    private void showDialogVolume() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.dialog_volume, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setView(deleteDialogView);
        deleteDialogView.findViewById(R.id.text_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
            }
        });
        final SeekBar volumeBar = deleteDialogView.findViewById(R.id.seekbar_volume);
        volumeBar.setProgress(mPrefUtils.getVolume());

        deleteDialogView.findViewById(R.id.text_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                mPrefUtils.saveVolume(volumeBar.getProgress());
                mAnimalAdapter.notifyDataSetChanged();
                deleteDialog.dismiss();
            }
        });

        deleteDialog.show();
    }

    public void onGameClick(final View view) {
        if (mContainerBlack.getVisibility()==View.GONE){
            animateGameView2(mMatchesImageView,-190,-110);
            animateGameView2(mPuzzleImageView,-190,90);
            mContainerBlack.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.drawable.ic_close_white_24dp)
                   .into(mGamesImageView);
        }else{
            animateGameViewReverse(mMatchesImageView,-190,-110);
            animateGameViewReverse(mPuzzleImageView,-190,90);
            mContainerBlack.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.games)
                    .into(mGamesImageView);
        }

    }

    @Override
    public void onBackPressed() {
        mBackCount++;
        if (mBackCount==1){
            Toast.makeText(this,"Tap back again to exit the app.",Toast.LENGTH_SHORT)
                    .show();
        }else{
            mBackCount=0;
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBackCount=0;
        mPrefUtils = new PrefUtils(this);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(this).load(mPrefUtils.getLanguageRes())
                .apply(requestOptions).into(mChosenFlag);

        animateGameViewReverse(mMatchesImageView,-190,-110);
        animateGameViewReverse(mPuzzleImageView,-190,90);
        mContainerBlack.setVisibility(View.GONE);
        Glide.with(this).load(R.drawable.games)
                .into(mGamesImageView);

    }

    public void setLocale(String lang) {
        mLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = mLocale;
        res.updateConfiguration(conf, dm);
    }

    private void loadGrid(){
        mAnimalAdapter = new AnimalPicturesAdapter(this, new Animal[]{
                new Animal(getString(R.string.cat), R.drawable.cat, R.raw.cat),
                new Animal(getString(R.string.dog), R.drawable.dog, R.raw.dog),
                new Animal(getString(R.string.elephant), R.drawable.elephant, R.raw.elephant),
                new Animal(getString(R.string.leopard), R.drawable.leopard, R.raw.leopard),
                new Animal(getString(R.string.lion), R.drawable.lion, R.raw.lion),
                new Animal(getString(R.string.monkey), R.drawable.monkey, R.raw.monkey),
                new Animal(getString(R.string.wolf), R.drawable.wolf, R.raw.wolf),
                new Animal(getString(R.string.tiger),R.drawable.tiger, R.raw.tiger),
                new Animal(getString(R.string.snake),R.drawable.snake, R.raw.snake),
                new Animal(getString(R.string.pig), R.drawable.pig, R.raw.pig),
                new Animal(getString(R.string.horse), R.drawable.horse, R.raw.horse),
                new Animal(getString(R.string.sheep), R.drawable.sheep, R.raw.sheep),
                new Animal(getString(R.string.chicken),R.drawable.chicken,R.raw.chicken),
                new Animal(getString(R.string.seal),R.drawable.seal,R.raw.seal),
                new Animal(getString(R.string.cow), R.drawable.cow, R.raw.cow),
                new Animal(getString(R.string.duck),R.drawable.duck, R.raw.duck)
        });
        mAnimalsGrid.setAdapter(mAnimalAdapter);
    }

    private void animateGameView2(final View view, int positiony, int positionx){
        final SpringAnimation springAnim =
                new SpringAnimation(view, DynamicAnimation.TRANSLATION_Y, positiony)
                        .setStartVelocity(90f).setStartValue(-mGamesImageView.getHeight()/2);
        springAnim.getSpring().setDampingRatio(DAMPING_RATIO_LOW_BOUNCY);
        springAnim.getSpring().setStiffness(STIFFNESS_VERY_LOW);
        springAnim.start(); //bounce up

        final SpringAnimation springAnim2 =
                new SpringAnimation(view, DynamicAnimation.TRANSLATION_X,positionx)
                        .setStartVelocity(90f).setStartValue(-mGamesImageView.getWidth()/2);
        springAnim2.getSpring().setDampingRatio(DAMPING_RATIO_LOW_BOUNCY);

        springAnim2.getSpring().setStiffness(STIFFNESS_VERY_LOW);
        springAnim2.start();

        ValueAnimator anim = ValueAnimator.ofInt(view.getMeasuredHeight(), 103*mGamesImageView.getMeasuredHeight());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = val/100;
                layoutParams.width = val/100;
                view.setLayoutParams(layoutParams);
            }
        });
        anim.setDuration(100);
        anim.start();
    }

    private void findViews(){
        mToolbar = findViewById(R.id.toolbar);
        mToolbarTextView =  findViewById(R.id.text_toolbar);
        mChosenFlag = findViewById(R.id.image_chosen_lang);
        mRoot = findViewById(R.id.root);
        mAnimalsGrid = findViewById(R.id.animalsgrid);
        mClickView = findViewById(R.id.clickview);
        mRecyclerView = findViewById(R.id.languages_recyclerview);
        mVolumeImageView = findViewById(R.id.image_set_volume);

        mMatchesImageView = findViewById(R.id.image_matches_game);
        mPuzzleImageView = findViewById(R.id.image_puzzle_game);
        mGamesImageView = findViewById(R.id.gamesimage);
        mContainerBlack = findViewById(R.id.containerblack);
    }

    private void animateGameViewReverse(final CircleImageView view, int positiony, int positionx) {
        final SpringAnimation springAnim =
                new SpringAnimation(view, DynamicAnimation.TRANSLATION_Y, -positiony)
                        .setStartVelocity(60f);
        springAnim.start();

        final SpringAnimation springAnim2 =
                new SpringAnimation(view, DynamicAnimation.TRANSLATION_X,-positionx)
                        .setStartVelocity(60f);

        springAnim2.start();
    }
}
