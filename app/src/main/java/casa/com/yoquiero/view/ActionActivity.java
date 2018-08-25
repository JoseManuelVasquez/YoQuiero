package casa.com.yoquiero.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

import casa.com.yoquiero.R;
import casa.com.yoquiero.constants.Constants;
import casa.com.yoquiero.interfaces.IAction;
import casa.com.yoquiero.presenter.ActionPresenter;
import casa.com.yoquiero.tools.Tools;

public class ActionActivity extends AppCompatActivity implements IAction.View, View.OnClickListener, View.OnTouchListener {

    /* Views */
    private TextView tvTitle, tvDescription;
    private LinearLayout layoutImage;
    private ImageView ivEdit, ivDelete, ivRecord, ivPlay, ivClear;

    /* Title Action */
    private String titleAction;

    public boolean playPressed;

    /* Presenter Action */
    private IAction.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        /* Buttons Pressed */
        playPressed = false;

        /* Views */
        tvTitle = findViewById(R.id.tvActionTitle);
        layoutImage = findViewById(R.id.layoutImage);
        tvDescription = findViewById(R.id.tvActionDesc);
        ivEdit = findViewById(R.id.editAction);
        ivDelete = findViewById(R.id.deleteAction);
        ivRecord = findViewById(R.id.micAction);
        ivPlay = findViewById(R.id.playAction);
        ivClear = findViewById(R.id.clearAction);

        /* Listeners */
        ivEdit.setOnClickListener(this);
        ivDelete.setOnClickListener(this);
        ivRecord.setOnTouchListener(this);
        ivPlay.setOnClickListener(this);
        ivClear.setOnClickListener(this);

        /* Action title */
        titleAction = getIntent().getStringExtra(Constants.ACTION_INFO_TITLE);

        /* Presenter */
        presenter = new ActionPresenter(this);

        /* Search action on database */
        presenter.getActionData(titleAction);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == RESULT_OK)
        {
            /* Update data */
            titleAction = data.getExtras().getString(Constants.EXTRA_RESULT_TITLE_ACTION);

            /* Search action on database */
            presenter.getActionData(titleAction);
        }
    }

    @Override
    public void goToModify(String title, String urlImage, String description) {
        Intent goToModify = new Intent(ActionActivity.this, ModifyActionActivity.class);
        goToModify.putExtra(Constants.EXTRA_MODIFY_TITLE_ACTION, title);
        goToModify.putExtra(Constants.EXTRA_MODIFY_IMAGE_ACTION, urlImage);
        goToModify.putExtra(Constants.EXTRA_MODIFY_DESC_ACTION, description);
        startActivityForResult(goToModify, Constants.REQUEST_CODE_MODIFY_ACTION);
    }

    @Override
    public void showTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void showImage(String urlImage) {
        /* Set an image background to Button */
        Picasso.get()
                .load(new File(urlImage))
                .resize(550, 550)
                .centerCrop()
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        layoutImage.setBackground(new BitmapDrawable(getResources(), bitmap));
                    }
                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {}
                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {}
                });
    }

    @Override
    public void showDescription(String description) {
        tvDescription.setText(description);
    }

    @Override
    public void showAudioIcon() {
        ivRecord.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPlay() {
        Tools.fadeInAnimation(ivPlay, this);
    }

    @Override
    public void showClear() {
        Tools.fadeInAnimation(ivClear, this);
    }

    @Override
    public void showStopAnimation()
    {
        Tools.playAnimation(ivPlay, R.drawable.play_button_reverse, this);
    }

    @Override
    public void hidePlay() {
        Tools.fadeOutAnimation(ivPlay, this);
    }

    @Override
    public void hideClear() {
        Tools.fadeOutAnimation(ivClear, this);
    }

    @Override
    public void onClick(View view){
        switch(view.getId())
        {
            case R.id.editAction:
                presenter.modifyAction(titleAction);
                break;
            case R.id.deleteAction:
                Tools.playAnimation(view, R.drawable.delete_button, this);
                showDialog();
                break;
            case R.id.playAction:
                if(!playPressed)
                {
                    Tools.playAnimation(view, R.drawable.play_button, this);
                    presenter.playAudio();
                }
                else
                {
                    presenter.stopAudio();
                }
                break;
            case R.id.clearAction:
                presenter.clearAudio();
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        switch(motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                Tools.playAnimation(view, R.drawable.mic_button, this);
                presenter.startRecordingAudio(titleAction);
                return true;
            case MotionEvent.ACTION_UP:
                Tools.playAnimation(view, R.drawable.mic_button_reverse, this);
                if((motionEvent.getEventTime() - motionEvent.getDownTime()) > 500)
                    presenter.stopRecordingAudio();
                else
                    Toast.makeText(this, getString(R.string.action_recording_error), Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        presenter.requestPermissionTask(requestCode, permissions, grantResults);
    }

    /**
     * Method for showing a warning
     */
    private void showDialog()
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        presenter.deleteAction(titleAction);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        /* Dialog properties */
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.action_title_dialog))
                .setMessage(getString(R.string.action_text_dialog))
                .setIcon(R.drawable.delete_button)
                .setPositiveButton(getString(R.string.action_yes_dialog), dialogClickListener)
                .setNegativeButton(getString(R.string.action_no_dialog), dialogClickListener);
        AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_style;
        dialog.show();
    }
}
