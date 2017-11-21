package zw.org.nbsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.Counsellor;
import zw.org.nbsz.business.domain.Donor;
import zw.org.nbsz.business.util.AppUtil;

public class DeclarationStep6Activity extends BaseActivity implements View.OnClickListener {

    private CheckBox acknowledge;
    private Button next;
    private Donor holder;
    private Counsellor counsellor;
    private String donorNumber;
    private Donor item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declaration_step6);
        Intent intent = getIntent();
        holder = (Donor) intent.getSerializableExtra("holder");
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNumber = intent.getStringExtra("donorNumber");
        acknowledge = (CheckBox) findViewById(R.id.acknowledge);
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        setSupportActionBar(createToolBar("NSBZ - Consent To Donate > Declaration"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(donorNumber != null && ! donorNumber.isEmpty()){
            item = Donor.findByDonorNumber(donorNumber);
        }else {
            item = new Donor();
        }
    }

    @Override
    public void onClick(View view) {
        if(acknowledge.isChecked()){
            Intent intent = new Intent(getApplicationContext(), DeclarationStep7Activity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            startActivity(intent);
            finish();
        }else{
            AppUtil.createShortNotification(getApplicationContext(), "Sorry, this response is required");
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, DeclarationStep5Activity.class);
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
