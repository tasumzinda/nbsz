package zw.org.nbsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.Counsellor;
import zw.org.nbsz.business.domain.Donor;
import zw.org.nbsz.business.domain.util.ReasonForTesting;

public class ReasonForTestingActivity extends BaseActivity implements View.OnClickListener {

    private ListView reasonForTesting;
    private Button next;
    private ArrayAdapter<ReasonForTesting> adapter;
    private Donor holder;
    private Counsellor counsellor;
    private String donorNumber;
    private Donor item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reason_for_testing);
        Intent intent = getIntent();
        holder = (Donor) intent.getSerializableExtra("holder");
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNumber = intent.getStringExtra("donorNumber");
        reasonForTesting = (ListView) findViewById(R.id.list);
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        adapter = new ArrayAdapter<>(this, R.layout.check_box_item, ReasonForTesting.values());
        reasonForTesting.setAdapter(adapter);
        reasonForTesting.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        reasonForTesting.setItemsCanFocus(false);
        if(donorNumber != null && ! donorNumber.isEmpty() && holder.reasonForTesting != null){
            item = Donor.findByDonorNumber(donorNumber);
            ReasonForTesting item = holder.reasonForTesting;
            int count = adapter.getCount();
            for(int i = 0; i < count; i++){
                ReasonForTesting current = adapter.getItem(i);
                if(current.equals(item)){
                    reasonForTesting.setItemChecked(i, true);
                }
            }
        }else if(holder.reasonForTesting != null){
            ReasonForTesting item = holder.reasonForTesting;
            int count = adapter.getCount();
            for(int i = 0; i < count; i++){
                ReasonForTesting current = adapter.getItem(i);
                if(current.equals(item)){
                    reasonForTesting.setItemChecked(i, true);
                }
            }
        }else {
            item = new Donor();
        }
        setSupportActionBar(createToolBar("NBSZ - Risk Assessment"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        holder.reasonForTesting = getReasonForTesting();
        Intent intent = new Intent(this, RiskAssessmentStep2Activity.class);
        intent.putExtra("holder", holder);
        intent.putExtra("counsellor", counsellor);
        intent.putExtra("donorNumber", donorNumber);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        holder.reasonForTesting = getReasonForTesting();
        Intent intent = new Intent(this, RiskAssessmentActivity.class);
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

    public ReasonForTesting getReasonForTesting(){
        ReasonForTesting item = null;
        for(int i = 0; i < reasonForTesting.getCount(); i++){
            if(reasonForTesting.isItemChecked(i)){
                item = adapter.getItem(i);
            }
        }
        return item;
    }
}
