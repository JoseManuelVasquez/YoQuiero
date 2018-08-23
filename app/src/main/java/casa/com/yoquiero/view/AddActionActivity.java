package casa.com.yoquiero.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vincent.filepicker.activity.AudioPickActivity;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.filter.entity.AudioFile;
import com.vincent.filepicker.filter.entity.ImageFile;

import java.util.ArrayList;
import java.util.Iterator;

import static com.vincent.filepicker.activity.AudioPickActivity.IS_NEED_RECORDER;
import static com.vincent.filepicker.activity.BaseActivity.IS_NEED_FOLDER_LIST;
import static com.vincent.filepicker.activity.ImagePickActivity.IS_NEED_CAMERA;

import casa.com.yoquiero.R;
import casa.com.yoquiero.constants.Constants;
import casa.com.yoquiero.interfaces.IAddAction;
import casa.com.yoquiero.presenter.AddActionPresenter;

public class AddActionActivity extends AppCompatActivity implements IAddAction.View, View.OnClickListener {

    /* Views */
    private EditText etActionName, etActionDescription;
    private Button btnActionImage, saveData;
    private TextView tvPathImage;

    /* Action Data */
    private String actionName = "", actionImageUrl = "", actionDescription = "";

    /* AddAction Presenter */
    private IAddAction.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_action);

        /* Presenter */
        presenter = new AddActionPresenter(this);

        /* Views */
        etActionName = findViewById(R.id.etAddActionTitle);
        btnActionImage = findViewById(R.id.btnAddActionImage);
        etActionDescription = findViewById(R.id.etAddActionDescription);
        saveData = findViewById(R.id.btnSaveData);
        tvPathImage = findViewById(R.id.tvShowActionImagePath);

        /* Listeners */
        btnActionImage.setOnClickListener(this);
        saveData.setOnClickListener(this);
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
    public void showDataError() {
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

                presenter.addActionData(actionName, actionImageUrl, actionDescription);
                finish();
                break;
            case R.id.btnAddActionImage:
                Intent intent1 = new Intent(this, ImagePickActivity.class);
                intent1.putExtra(Constants.MAX_NUMBER, 9);
                intent1.putExtra(IS_NEED_FOLDER_LIST, true);
                startActivityForResult(intent1, Constants.REQUEST_CODE_PICK_IMAGE);
                break;
        }
    }
}
