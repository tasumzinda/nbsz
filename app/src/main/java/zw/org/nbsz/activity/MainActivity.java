package zw.org.nbsz.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.BankStaff;
import zw.org.nbsz.business.domain.CollectSite;
import zw.org.nbsz.business.domain.Counsellor;
import zw.org.nbsz.business.domain.Donor;
import zw.org.nbsz.business.util.AppUtil;
import zw.org.nbsz.business.util.Log;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private CheckBox acknowledge;
    private Button next;
    private Donor holder;
    private Counsellor counsellor;
    private String donorNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        /*String download = intent.getStringExtra("download");
        if(download != null && ! download.isEmpty()){
            downloadDonors();
        }*/
        holder = (Donor) intent.getSerializableExtra("holder");
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNumber = intent.getStringExtra("donorNumber");
        acknowledge = (CheckBox) findViewById(R.id.acknowledge);
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        setSupportActionBar(createToolBar("NSBZ - Donor Enrolment"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        if(acknowledge.isChecked()){
            if(donorNumber != null && ! donorNumber.isEmpty()){
                Intent intent = new Intent(getApplicationContext(), ConsentToUpdateDetailsActivity.class);
                intent.putExtra("holder", holder);
                intent.putExtra("counsellor", counsellor);
                intent.putExtra("donorNumber", donorNumber);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(getApplicationContext(), DonorProfileActivity.class);
                intent.putExtra("holder", holder);
                intent.putExtra("counsellor", counsellor);
                intent.putExtra("donorNumber", donorNumber);
                startActivity(intent);
                finish();
            }

        }else{
            AppUtil.createShortNotification(getApplicationContext(), "Sorry, this response is required");
        }
    }

    @Override
    public void onBackPressed(){
        if(donorNumber != null && ! donorNumber.isEmpty()){
            Intent intent = new Intent(getApplicationContext(), DonorDetailsActivity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(getApplicationContext(), DonatedBloodActivity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            startActivity(intent);
            finish();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
