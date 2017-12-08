package zw.org.nbsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.*;
import zw.org.nbsz.business.util.AppUtil;
import zw.org.nbsz.business.util.DateUtil;
import zw.org.nbsz.business.util.Log;

import java.util.Date;

public class DonatedBloodActivity extends BaseActivity implements View.OnClickListener{

    private RadioGroup registeredDonor;
    private RadioButton selected;
    private Button next;
    private Donor holder;
    private Counsellor counsellor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donated_blood);
        registeredDonor = (RadioGroup) findViewById(R.id.registered_donor);
        Intent intent = getIntent();
        holder = (Donor) intent.getSerializableExtra("holder");
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        String download = intent.getStringExtra("download");
        if(download != null && ! download.isEmpty()){
            downloadDonors();
        }
        for(User m : User.getAll()){
            Log.d("User", m.name);
        }
        for(BankStaff m : BankStaff.getAll()){
            Log.d("Bankstaff", m.name);
        }
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        setSupportActionBar(createToolBar("NSBZ - Donor Enrolment"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(SpecialNotes.getAll().isEmpty()){
            SpecialNotes s = new SpecialNotes();
            s.name = "Severe Faint";
            s.save();
            SpecialNotes s1 = new SpecialNotes();
            s1.name = "Failed attempt";
            s1.save();
            SpecialNotes s2 = new SpecialNotes();
            s2.name = "Haematoma";
            s2.save();
            SpecialNotes s3 = new SpecialNotes();
            s3.name = "Insufficient Volume";
            s3.save();
        }

    }

    @Override
    public void onClick(View view) {
        int selectedId = registeredDonor.getCheckedRadioButtonId();
        selected = (RadioButton) findViewById(selectedId);
        Intent intent;
        if(selected.getText().equals("No")){
            //intent = new Intent(getApplicationContext(), DonorProfileActivity.class);
            intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            startActivity(intent);
            finish();
        }else{
            intent = new Intent(getApplicationContext(), SearchDonorActivity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, SelectCollectSiteActivity.class);
        //intent.putExtra("donorNumber", donorNumber);
        startActivity(intent);
        finish();
    }
}
