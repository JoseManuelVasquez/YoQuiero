package casa.com.yoquiero.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import casa.com.yoquiero.R;
import casa.com.yoquiero.interfaces.IMain;
import casa.com.yoquiero.presenter.MainPresenter;
import casa.com.yoquiero.tools.Tools;

public class MainActivity extends AppCompatActivity implements IMain.View, View.OnClickListener {

    /* Views */
    private ImageView ivStart;
    private TextView tvStart;

    /* Main Presenter */
    private IMain.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Presenter */
        presenter = new MainPresenter(this);

        /* Views */
        ivStart = findViewById(R.id.ivStart);
        tvStart = findViewById(R.id.tvStart);

        /* Listeners */
        ivStart.setOnClickListener(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        /* Animation */
        Tools.playAnimation(ivStart, R.drawable.main_button, this);
    }

    @Override
    public void goToWanna() {
        Intent goToWanna = new Intent(MainActivity.this, WannaActivity.class);
        startActivity(goToWanna);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ivStart:
                presenter.goToWanna();
                break;
        }
    }
}
