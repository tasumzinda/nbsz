package zw.org.nbsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import it.sephiroth.android.library.widget.HListView;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.Counsellor;
import zw.org.nbsz.business.domain.Donor;
import zw.org.nbsz.business.domain.util.Gender;
import zw.org.nbsz.business.domain.util.YesNo;

public class HealthAssessmentStep5Activity extends BaseActivity implements View.OnClickListener {

    private HListView receivedBloodTransfusion;
    //private HListView sufferedFromMalaria;
    private HListView pregnant;
    private HListView breastFeeding;
    private TextView breastFeedingLabel;
    private TextView pregnantLabel;
    private Button next;
    private Donor holder;
    private Counsellor counsellor;
    private ArrayAdapter<YesNo> adapter;
    private HListView[] fields;
    private String donorNumber;
    private Donor item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_assessment_step5);
        Intent intent = getIntent();
        holder = (Donor) intent.getSerializableExtra("holder");
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNumber = intent.getStringExtra("donorNumber");
        receivedBloodTransfusion = (HListView) findViewById(R.id.received_blood_transfusion);
        pregnant = (HListView) findViewById(R.id.pregnant);
        breastFeeding = (HListView) findViewById(R.id.breast_feeding);
        pregnantLabel = (TextView) findViewById(R.id.pregnant_label);
        breastFeedingLabel = (TextView) findViewById(R.id.breast_feeding_label);
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        fields = new HListView[]{receivedBloodTransfusion};
        adapter = new ArrayAdapter<>(this, R.layout.check_box_item, YesNo.values());
        receivedBloodTransfusion.setAdapter(adapter);
        receivedBloodTransfusion.setItemsCanFocus(false);
        receivedBloodTransfusion.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        pregnant.setAdapter(adapter);
        pregnant.setItemsCanFocus(false);
        pregnant.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        breastFeeding.setAdapter(adapter);
        breastFeeding.setItemsCanFocus(false);
        breastFeeding.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter.notifyDataSetChanged();
        if(holder.gender != null && holder.gender.equals(Gender.F)){
            pregnantLabel.setVisibility(View.VISIBLE);
            pregnant.setVisibility(View.VISIBLE);
            breastFeeding.setVisibility(View.VISIBLE);
            breastFeedingLabel.setVisibility(View.VISIBLE);
        }
        setSupportActionBar(createToolBar("NBSZ - SECTION A"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(donorNumber != null && ! donorNumber.isEmpty() && holder.receivedBloodTransfusion != null){
            item =Donor.findByDonorNumber(donorNumber);
            YesNo item = holder.receivedBloodTransfusion;
            int count = adapter.getCount();
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    receivedBloodTransfusion.setItemChecked(i, true);
                }
            }
            item = holder.pregnant;
            count = adapter.getCount();
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    pregnant.setItemChecked(i, true);
                }
            }
            item = holder.breastFeeding;
            count = adapter.getCount();
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    breastFeeding.setItemChecked(i, true);
                }
            }
        }else if(holder.receivedBloodTransfusion != null){
            YesNo item = holder.receivedBloodTransfusion;
            int count = adapter.getCount();
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    receivedBloodTransfusion.setItemChecked(i, true);
                }
            }
            item = holder.pregnant;
            count = adapter.getCount();
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    pregnant.setItemChecked(i, true);
                }
            }
            item = holder.breastFeeding;
            count = adapter.getCount();
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    breastFeeding.setItemChecked(i, true);
                }
            }
        }else {
            item = new Donor();
        }
    }

    @Override
    public void onClick(View view) {
        if(validateListView(fields)){
            holder.receivedBloodTransfusion = getReceivedBloodTransfusion();
            if(holder.gender != null && holder.gender.equals(Gender.F)){
                holder.pregnant = getPregnant();
                holder.breastFeeding = getBreastFeeding();
            }
            Intent intent = new Intent(this, RiskAssessmentActivity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onBackPressed(){
        holder.receivedBloodTransfusion = getReceivedBloodTransfusion();
        if(holder.gender != null && holder.gender.equals(Gender.F)){
            holder.pregnant = getPregnant();
            holder.breastFeeding = getBreastFeeding();
        }
        Intent intent = new Intent(this, HealthAssessmentStep4Activity.class);
        intent.putExtra("holder", holder);
        intent.putExtra("counsellor", counsellor);
        intent.putExtra("donorNumber", donorNumber);
        startActivity(intent);
        finish();
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

    private YesNo getReceivedBloodTransfusion(){
        YesNo a = null;
        for(int i = 0; i < receivedBloodTransfusion.getCount(); i++){
            if(receivedBloodTransfusion.isItemChecked(i)){
                a = adapter.getItem(i);
            }
        }
        return a;
    }

    private YesNo getPregnant(){
        YesNo a = null;
        for(int i = 0; i < pregnant.getCount(); i++){
            if(pregnant.isItemChecked(i)){
                a = adapter.getItem(i);
            }
        }
        return a;
    }

    private YesNo getBreastFeeding(){
        YesNo a = null;
        for(int i = 0; i < breastFeeding.getCount(); i++){
            if(breastFeeding.isItemChecked(i)){
                a = adapter.getItem(i);
            }
        }
        return a;
    }
}
