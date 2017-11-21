package zw.org.nbsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.*;
import zw.org.nbsz.business.util.Log;

public class CounsellorDetailsActivity extends BaseActivity implements View.OnClickListener {

    private EditText name;
    private EditText address;
    private EditText phoneNumber;
    private Donor holder;
    private Counsellor counsellor;
    private Button next;
    private String donorNumber;
    private Donor item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsellor_details);
        Intent intent = getIntent();
        holder = (Donor) intent.getSerializableExtra("holder");
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNumber = intent.getStringExtra("donorNumber");
        name = (EditText) findViewById(R.id.name);
        address = (EditText) findViewById(R.id.address);
        phoneNumber = (EditText) findViewById(R.id.phone_number);
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        setSupportActionBar(createToolBar("NBSZ - Add Doctor / Counsellor Details"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(donorNumber != null && ! donorNumber.isEmpty()){
            item = Donor.findByDonorNumber(donorNumber);
            if(item.counsellor != null){
                name.setText(item.counsellor.name);
                address.setText(item.counsellor.address);
                phoneNumber.setText(item.counsellor.phoneNumber);
                counsellor = item.counsellor;
            }else {
                counsellor = new Counsellor();
            }

        }else if(counsellor != null){
            name.setText(counsellor.name);
            address.setText(counsellor.address);
            phoneNumber.setText(counsellor.phoneNumber);
        }else{
            counsellor = new Counsellor();
            item = new Donor();
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, HealthAssessmentActivity.class);
        counsellor.name = name.getText().toString();
        counsellor.address = address.getText().toString();
        counsellor.phoneNumber = phoneNumber.getText().toString();
        intent.putExtra("counsellor", counsellor);
        intent.putExtra("holder", holder);
        intent.putExtra("donorNumber", donorNumber);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, DonorAddContactDetailsActivity.class);
        counsellor.name = name.getText().toString();
        counsellor.address = address.getText().toString();
        counsellor.phoneNumber = phoneNumber.getText().toString();
        intent.putExtra("counsellor", counsellor);
        intent.putExtra("holder", holder);
        intent.putExtra("donorNumber", donorNumber);
        startActivity(intent);
        finish();
    }
}
