package zw.org.nbsz.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import zw.org.nbsz.R;
import zw.org.nbsz.business.util.AppUtil;

public class UrlConfigActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.url)
    EditText url;
    @BindView(R.id.login)
    Button save;
    EditText [] fields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_config);
        ButterKnife.bind(this);
        setSupportActionBar(createToolBar("Configure URL"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fields = new EditText[]{url};
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(validate(fields)){
            AppUtil.savePreferences(this, AppUtil.LOCAL_URL, url.getText().toString());
            AppUtil.createShortNotification(this, "Saved url!");
        }
    }
}
