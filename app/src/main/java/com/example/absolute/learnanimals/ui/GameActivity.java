package com.example.absolute.learnanimals.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.absolute.learnanimals.R;
import com.example.absolute.learnanimals.adapters.MatchesGridAdapter;
import com.example.absolute.learnanimals.model.Animal;
import com.example.absolute.learnanimals.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private static String mFirstSelectedImage, mSecondSelectedImage;
    private static String mFirstTag, mSecondTag;
    private static GridView mGridMatches;
    private static boolean mClickEnabled;
    private static int mPairsCount=0;

    private TextView mToolbarTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        setupToolbar();

        mGridMatches = findViewById(R.id.gridview_matches);
        mClickEnabled = true;
        if (NetworkUtils.isNetworkConnected(this)) loadGrid(this);
        else Toast.makeText(this,"Internet connection is required.",Toast.LENGTH_SHORT)
                .show();

    }

    public static void setSelectedImage(String res, String tag, final Context context) {
        if (mClickEnabled) {
            mClickEnabled = false;
            ImageView imageViewQuestion = mGridMatches.findViewWithTag(tag)
                    .findViewById(R.id.image_match);
            ImageView imageViewAnimal = mGridMatches.findViewWithTag(tag)
                    .findViewById(R.id.image_match_animal);
            imageViewQuestion.setVisibility(View.INVISIBLE);
            imageViewAnimal.setVisibility(View.VISIBLE);
            if (mFirstSelectedImage==null) {
                selectFirstCard(res, tag);
            } else {
                if (tag.equals(mFirstTag)) {
                    mClickEnabled = true;
                    return;
                }
                mSecondSelectedImage = res;
                mSecondTag = tag;
                if (mFirstSelectedImage.equals(mSecondSelectedImage) && !mFirstTag.equals(mSecondTag)) {
                    //disappear
                    onCorrectPair();

                } else {
                    //question marks again
                    onWrongPair();

                }
            }
            if (mPairsCount == 9) {
                showGameOverDialog(context);
            }
        }

    }

    private static void showGameOverDialog(final Context context) {
        LayoutInflater factory = LayoutInflater.from(context);
        final View deleteDialogView = factory.inflate(R.layout.dialog_game_over, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
        deleteDialog.setView(deleteDialogView);
        deleteDialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                deleteDialog.dismiss();
                if (NetworkUtils.isNetworkConnected(context)) loadGrid(context);
                else{
                    Toast.makeText(context,
                            "Internet connection is required.",
                            Toast.LENGTH_SHORT)
                            .show();
                }
                mFirstSelectedImage = null;
                mSecondSelectedImage = null;
                mFirstTag = "";
                mSecondTag = "";
                mPairsCount = 0;
            }
        });
        deleteDialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
                ((GameActivity) context).finish();
            }
        });

        deleteDialog.show();
        deleteDialog.setCancelable(false);
        deleteDialog.setCanceledOnTouchOutside(false);
    }

    private static void onWrongPair() {
        Handler handler = new Handler();
        mGridMatches.setEnabled(false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ImageView imageViewQuestion1 = mGridMatches.findViewWithTag(mFirstTag)
                        .findViewById(R.id.image_match);
                ImageView imageViewAnimal1 = mGridMatches.findViewWithTag(mFirstTag)
                        .findViewById(R.id.image_match_animal);
                imageViewQuestion1.setVisibility(View.VISIBLE);
                imageViewAnimal1.setVisibility(View.INVISIBLE);
                ImageView imageViewQuestion2 = mGridMatches.findViewWithTag(mSecondTag).findViewById(R.id.image_match);
                ImageView imageViewAnimal2 = mGridMatches.findViewWithTag(mSecondTag)
                        .findViewById(R.id.image_match_animal);

                imageViewQuestion2.setVisibility(View.VISIBLE);
                imageViewAnimal2.setVisibility(View.INVISIBLE);

                mFirstTag = "";
                mSecondTag = "";
                mFirstSelectedImage = null;
                mSecondSelectedImage = null;
                mGridMatches.setEnabled(true);
                mClickEnabled = true;
            }
        }, 500);
    }

    private static void onCorrectPair() {
        mPairsCount++;
        Handler handler = new Handler();
        mGridMatches.setEnabled(false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mGridMatches.findViewWithTag(mFirstTag).setVisibility(View.INVISIBLE);
                mGridMatches.findViewWithTag(mSecondTag).setVisibility(View.INVISIBLE);
                mFirstTag = "";
                mSecondTag = "";
                mFirstSelectedImage = null;
                mSecondSelectedImage = null;
                mGridMatches.setEnabled(true);
                mClickEnabled = true;

            }
        }, 500);
    }

    private static void selectFirstCard(String res, String tag) {
        mFirstSelectedImage = res;
        mFirstTag = tag;
        mClickEnabled = true;
    }

    private static void loadGrid(Context context) {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal("tiger1","https://i.imgur.com/5z0g9qM.png"));
        animals.add(new Animal("tiger2","https://i.imgur.com/5z0g9qM.png"));
        animals.add(new Animal("fish1","https://i.imgur.com/Eyhvz5u.png"));
        animals.add(new Animal("fish2","https://i.imgur.com/Eyhvz5u.png"));
        animals.add(new Animal("cat1","https://i.imgur.com/GR7nW71.png"));
        animals.add(new Animal("cat2","https://i.imgur.com/GR7nW71.png"));
        animals.add(new Animal("frog1","https://i.imgur.com/MHBJusl.png"));
        animals.add(new Animal("frog2","https://i.imgur.com/MHBJusl.png"));
        animals.add(new Animal("monkey1","https://i.imgur.com/jLn1BBF.png"));
        animals.add(new Animal("monkey2","https://i.imgur.com/jLn1BBF.png"));
        animals.add(new Animal("pig1","https://i.imgur.com/Q4FvSWp.png"));
        animals.add(new Animal("pig2","https://i.imgur.com/Q4FvSWp.png"));
        animals.add(new Animal("lion1","https://i.imgur.com/mkwhdSn.png"));
        animals.add(new Animal("lion2","https://i.imgur.com/mkwhdSn.png"));
        animals.add(new Animal("bear1","https://i.imgur.com/O2gHeVh.png"));
        animals.add(new Animal("bear2","https://i.imgur.com/O2gHeVh.png"));
        animals.add(new Animal("elephant1","https://i.imgur.com/d8YKUv4.png"));
        animals.add(new Animal("elephant2","https://i.imgur.com/d8YKUv4.png"));

        MatchesGridAdapter matchesGridAdapter = new MatchesGridAdapter(context, animals);
        mGridMatches.setAdapter(matchesGridAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_help:
                showDialogHelp();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    private void showDialogHelp() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.dialog_help, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setView(deleteDialogView);

        deleteDialogView.findViewById(R.id.text_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
            }
        });

        deleteDialog.show();
    }

    private void setupToolbar(){
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        if (getSupportActionBar()!=null)getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbarTextView =  findViewById(R.id.text_toolbar);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Sansation-Regular.ttf");
        mToolbarTextView.setTypeface(face);
    }

}
