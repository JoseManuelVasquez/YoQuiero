package casa.com.yoquiero.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.filter.entity.ImageFile;

import java.util.ArrayList;
import java.util.Iterator;

import casa.com.yoquiero.R;
import casa.com.yoquiero.constants.Constants;
import casa.com.yoquiero.interfaces.IModifyAction;
import casa.com.yoquiero.presenter.ModifyActionPresenter;

import static com.vincent.filepicker.activity.BaseActivity.IS_NEED_FOLDER_LIST;

public class ModifyActionActivity extends AppCompatActivity implements IModifyAction.View, View.OnClickListener {

    /* Views */
    private EditText etActionName, etActionDescription;
    private Button btnActionImage, saveData;
    private TextView tvPathImage;

    /* Action Data */
    private String actionName, actionImageUrl, actionDescription, previousActionName;

    /* AddAction Presenter */
    private IModifyAction.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_action);

        /* Presenter */
        presenter = new ModifyActionPresenter(this);

        /* Views */
        etActionName = findViewById(R.id.etAddActionTitle);
        btnActionImage = findViewById(R.id.btnAddActionImage);
        etActionDescription = findViewById(R.id.etAddActionDescription);
        saveData = findViewById(R.id.btnSaveData);
        tvPathImage = findViewById(R.id.tvShowActionImagePath);

        /* Listeners */
        btnActionImage.setOnClickListener(this);
        saveData.setOnClickListener(this);

        /* Previous data */
        previousActionName = getIntent().getStringExtra(Constants.EXTRA_MODIFY_TITLE_ACTION);
        actionName = previousActionName;
        actionImageUrl = getIntent().getStringExtra(Constants.EXTRA_MODIFY_IMAGE_ACTION);
        actionDescription = getIntent().getStringExtra(Constants.EXTRA_MODIFY_DESC_ACTION);

        /* We set the previous data for each view */
        etActionName.setText(actionName);
        tvPathImage.setText(actionImageUrl);
        etActionDescription.setText(actionDescription);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode)
        {
            case Constants.REQUEST_CODE_PICK_IMAGE:
                if(resultCode == RESULT_OK)
                {
                    ArrayList<ImageFile> listImage = data.getParcelableArrayListExtra(Constants.RESULT_PICK_IMAGE);
                    Iterator<ImageFile> iteratorImage = listImage.iterator();

                    /* We just need 1st File */
                    String filePath = "";
                    if(iteratorImage.hasNext())
                        filePath = iteratorImage.next().getPath();

                    actionImageUrl = filePath;
                    tvPathImage.setText(actionImageUrl);
                }
                break;
        }

    }

    @Override
    public void showDataError()
    {
        setResult(RESULT_CANCELED);
        finish();
        Toast.makeText(this, getString(R.string.action_error_title), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnSaveData:
                actionName = etActionName.getText().toString();
                actionDescription = etActionDescription.getText().toString();

                presenter.modifyActionData(previousActionName, actionName, actionImageUrl, actionDescription);
                getIntent().putExtra(Constants.EXTRA_RESULT_TITLE_ACTION, actionName);
                setResult(RESULT_OK, getIntent());

                finish();
                break;
            case R.id.btnAddActionImage:
                Intent intent = new Intent(this, ImagePickActivity.class);
                intent.putExtra(Constants.MAX_NUMBER, 9);
                intent.putExtra(IS_NEED_FOLDER_LIST, true);
                startActivityForResult(intent, Constants.REQUEST_CODE_PICK_IMAGE);
                break;
        }
    }
}
