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

public class HealthAssessmentStep3Activity extends BaseActivity implements View.OnClickListener {

    private HListView beenToDentist;
    private HListView takenAntibiotics;
    private Button next;
    private Donor holder;
    private Counsellor counsellor;
    private HListView[] fields;
    private ArrayAdapter<YesNo> adapter;
    private String donorNumber;
    private Donor item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_assessment_step3);
        Intent intent = getIntent();
        holder = (Donor) intent.getSerializableExtra("holder");
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNumber = intent.getStringExtra("donorNumber");
        beenToDentist = (HListView) findViewById(R.id.been_to_dentist);
        takenAntibiotics = (HListView) findViewById(R.id.taken_antibiotics);
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        fields = new HListView[]{beenToDentist, takenAntibiotics};
        adapter = new ArrayAdapter<>(this, R.layout.check_box_item, YesNo.values());
        beenToDentist.setAdapter(adapter);
        beenToDentist.setItemsCanFocus(false);
        beenToDentist.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        takenAntibiotics.setAdapter(adapter);
        takenAntibiotics.setItemsCanFocus(false);
        takenAntibiotics.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter.notifyDataSetChanged();
        setSupportActionBar(createToolBar("NBSZ - SECTION A"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(donorNumber != null && ! donorNumber.isEmpty() && holder.beenToDentist != null){
            item = Donor.findByDonorNumber(donorNumber);
            YesNo item =  holder.beenToDentist;
            int count = adapter.getCount();
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    beenToDentist.setItemChecked(i, true);
                }
            }
            item = holder.takenAntibiotics;
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    takenAntibiotics.setItemChecked(i, true);
                }
            }
        }else if(holder.beenToDentist != null){
            YesNo item =  holder.beenToDentist;
            int count = adapter.getCount();
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    beenToDentist.setItemChecked(i, true);
                }
            }
            item = holder.takenAntibiotics;
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    takenAntibiotics.setItemChecked(i, true);
                }
            }
        }else{
            item = new Donor();
        }
    }

    @Override
    public void onClick(View view) {
        if(validateListView(fields)){
            holder.beenToDentist = getBeenToDentist();
            holder.takenAntibiotics = getTakenAntibiotics();
            Intent intent = new Intent(this, HealthAssessmentStep4Activity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onBackPressed(){
        holder.beenToDentist = getBeenToDentist();
        holder.takenAntibiotics = getTakenAntibiotics();
        Intent intent = new Intent(this, HealthAssessmentStep2Activity.class);
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

    private YesNo getBeenToDentist(){
        YesNo b = null;
        for(int i = 0; i < beenToDentist.getCount(); i++){
            if(beenToDentist.isItemChecked(i)){
                b = adapter.getItem(i);
            }
        }
        return b;
    }

    private YesNo getTakenAntibiotics(){
        YesNo a = null;
        for(int i = 0; i < takenAntibiotics.getCount(); i++){
            if(takenAntibiotics.isItemChecked(i)){
                a = adapter.getItem(i);
            }
        }
        return a;
    }
}
