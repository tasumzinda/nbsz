package zw.org.nbsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.Donor;
import zw.org.nbsz.business.domain.util.YesNo;
import zw.org.nbsz.business.util.AppUtil;

public class ConsentToUpdateDetailsActivity extends BaseActivity implements View.OnClickListener {

    private RadioGroup acknowledge;
    private RadioButton selected;
    private Button next;
    private Donor holder;
    private String donorNumber;
    private Donor item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consent_to_update_details);
        Intent intent = getIntent();
        donorNumber = intent.getStringExtra("donorNumber");
        holder = (Donor) intent.getSerializableExtra("holder");
        acknowledge = (RadioGroup) findViewById(R.id.acknowledge);
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        setSupportActionBar(createToolBar("NBSZ - Update Details"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(donorNumber != null && ! donorNumber.isEmpty()){
            item = Donor.findByDonorNumber(donorNumber);
        }else {
            item = new Donor();
        }
    }

    @Override
    public void onClick(View view) {
        int selectedItem = acknowledge.getCheckedRadioButtonId();
        selected = (RadioButton) findViewById(selectedItem);
        if(holder == null){
            holder = new Donor();
        }
        if(selected != null){
            Intent intent;
            if(selected.getText().equals("No")){
                intent = new Intent(this, HealthAssessmentActivity.class);
                intent.putExtra("donorNumber", donorNumber);
                holder.consentToUpdate = YesNo.NO;
                intent.putExtra("holder", holder);
                startActivity(intent);
                finish();
            }else{
                if(selected.getText().equals("Yes")){
                    intent = new Intent(this, DonorAddContactDetailsActivity.class);
                    holder.consentToUpdate = YesNo.YES;
                    intent.putExtra("donorNumber", donorNumber);
                    intent.putExtra("holder", holder);
                    startActivity(intent);
                    finish();
                }
            }
        }else {
            AppUtil.createShortNotification(this, "Sorry, response is required");
        }

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("donorNumber", donorNumber);
        startActivity(intent);
        finish();
    }

    @Override
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
