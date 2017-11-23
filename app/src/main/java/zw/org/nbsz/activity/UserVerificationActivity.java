package zw.org.nbsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.Counsellor;
import zw.org.nbsz.business.domain.Donor;
import zw.org.nbsz.business.domain.util.DonateDefer;
import zw.org.nbsz.business.domain.util.YesNo;

public class UserVerificationActivity extends BaseActivity implements View.OnClickListener {

    private EditText passcode;
    private Button next;
    private Donor holder;
    private Counsellor counsellor;
    private String donorNumber;
    private Donor item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_verification);
        Intent intent = getIntent();
        holder = (Donor) intent.getSerializableExtra("holder");
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNumber = intent.getStringExtra("donorNumber");
        passcode = (EditText) findViewById(R.id.passcode);
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        setSupportActionBar(createToolBar("NBSZ - Verify User"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(donorNumber != null && ! donorNumber.isEmpty()){
            item = Donor.findByDonorNumber(donorNumber);
        }else {
            item = new Donor();
        }
    }

    @Override
    public void onClick(View view) {
        if(validate()){
            Intent intent = new Intent(this, RiskAssessmentActivity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            startActivity(intent);
            finish();

        }
    }

    public boolean validate(){
        boolean isValid = true;
        if(passcode.getText().toString().isEmpty()){
            passcode.setError(getResources().getString(R.string.required_field_error));
            isValid = false;
        }else{
            passcode.setError(null);
        }

        if( ! passcode.getText().toString().isEmpty()){
            if( ! passcode.getText().toString().equalsIgnoreCase("1234")){
                passcode.setError(getResources().getString(R.string.authentication_error));
                isValid = false;
            }else {
                passcode.setError(null);
            }
        }
        return isValid;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, DonorFinalActivity.class);
        intent.putExtra("holder", holder);
        intent.putExtra("counsellor", counsellor);
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
