package zw.org.nbsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import it.sephiroth.android.library.widget.HListView;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.Counsellor;
import zw.org.nbsz.business.domain.Donor;
import zw.org.nbsz.business.domain.util.YesNo;

public class HealthAssessmentStep4Activity extends BaseActivity implements View.OnClickListener {

    private HListView injection;
    private HListView beenIll;
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
        setContentView(R.layout.activity_health_assessment_step4);
        Intent intent = getIntent();
        holder = (Donor) intent.getSerializableExtra("holder");
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNumber = intent.getStringExtra("donorNumber");
        beenIll = (HListView) findViewById(R.id.been_ill);
        injection = (HListView) findViewById(R.id.injection);
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        fields = new HListView[]{beenIll, injection};
        adapter = new ArrayAdapter<>(this, R.layout.check_box_item, YesNo.values());
        beenIll.setAdapter(adapter);
        beenIll.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        beenIll.setItemsCanFocus(false);
        injection.setAdapter(adapter);
        injection.setItemsCanFocus(false);
        injection.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter.notifyDataSetChanged();
        setSupportActionBar(createToolBar("NBSZ - SECTION A"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(donorNumber != null && ! donorNumber.isEmpty() && holder.beenIll != null){
            item = Donor.findByDonorNumber(donorNumber);
            YesNo item =  holder.beenIll;
            int count = adapter.getCount();
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    beenIll.setItemChecked(i, true);
                }
            }
            item = holder.injection;
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    injection.setItemChecked(i, true);
                }
            }
        }else if(holder.beenIll != null){
            YesNo item =  holder.beenIll;
            int count = adapter.getCount();
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    beenIll.setItemChecked(i, true);
                }
            }
            item = holder.injection;
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    injection.setItemChecked(i, true);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(validateListView(fields)){
            holder.beenIll = getBeenIll();
            holder.injection = getInjection();
            Intent intent = new Intent(this, HealthAssessmentStep5Activity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onBackPressed(){
        holder.beenIll = getBeenIll();
        holder.injection = getInjection();
        Intent intent = new Intent(this, HealthAssessmentStep3Activity.class);
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

    private YesNo getBeenIll(){
        YesNo b = null;
        for(int i = 0; i < beenIll.getCount(); i++){
            if(beenIll.isItemChecked(i)){
                b = adapter.getItem(i);
            }
        }
        return b;
    }

    private YesNo getInjection(){
        YesNo a = null;
        for(int i = 0; i < injection.getCount(); i++){
            if(injection.isItemChecked(i)){
                a = adapter.getItem(i);
            }
        }
        return a;
    }
}
