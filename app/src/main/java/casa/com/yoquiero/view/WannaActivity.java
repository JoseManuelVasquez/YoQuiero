package casa.com.yoquiero.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

import casa.com.yoquiero.R;
import casa.com.yoquiero.constants.Constants;
import casa.com.yoquiero.interfaces.IWanna;
import casa.com.yoquiero.presenter.WannaPresenter;

public class WannaActivity extends AppCompatActivity implements IWanna.View, View.OnClickListener {

    /* Views */
    private ConstraintLayout layoutJoke;
    private GridLayout layoutActions;
    private FloatingActionButton fab;

    /* Wanna Presenter */
    private IWanna.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wanna);

        /* Views */
        layoutJoke = findViewById(R.id.layoutJoke);
        layoutActions = findViewById(R.id.layoutActions);
        fab = findViewById(R.id.fabAddAction);

        /* Listeners */
        fab.setOnClickListener(this);

        /* Presenter */
        presenter = new WannaPresenter(this);

        /* At first we search on database all the actions */
        //presenter.showCurrentActions();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        layoutActions.removeAllViews();
        presenter.showCurrentActions();
    }

    /**
     * New Activity for adding an action
     */
    @Override
    public void goToAddAction() {
        Intent goToAddAction = new Intent(WannaActivity.this, AddActionActivity.class);
        startActivity(goToAddAction);
    }

    /**
     * New Activity for showing an action
     */
    @Override
    public void showAction(String title) {
        Intent goToAction = new Intent(WannaActivity.this, ActionActivity.class);
        goToAction.putExtra(Constants.ACTION_INFO_TITLE, title);
        startActivity(goToAction);
    }

    /**
     * New action in the actions list
     */
    @Override
    public void addActionView(String title, String urlImage) {
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 250;
        params.height = 550;
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED,1f);
        params.setGravity(Gravity.FILL);
        params.setMargins(10,0,0,10);

        final Button btnAction = new Button(this);
        btnAction.setLayoutParams(params);
        btnAction.setText(title);
        btnAction.setTextColor(getResources().getColor(R.color.white));

        /* Set an image background to Button */
        Picasso.get()
                .load(new File(urlImage))
                .resize(550, 550)
                .centerCrop()
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        btnAction.setBackground(new BitmapDrawable(getResources(), bitmap));
                    }
                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {}
                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {}
                });

        /* The action performed by each button */
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showAction(btnAction.getText().toString());
            }
        });

        layoutActions.addView(btnAction);
    }

    @Override
    public void showEmptyJoke()
    {
        layoutJoke.setVisibility(ViewGroup.VISIBLE);
        layoutActions.setVisibility(ViewGroup.GONE);
    }

    @Override
    public void hideEmptyJoke()
    {
        layoutJoke.setVisibility(ViewGroup.GONE);
        layoutActions.setVisibility(ViewGroup.VISIBLE);
    }

    /**
     * onClickListener
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.fabAddAction:
                presenter.addNewAction();
                break;
        }
    }
}
