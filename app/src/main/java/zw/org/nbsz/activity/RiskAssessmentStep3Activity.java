package zw.org.nbsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import zw.org.nbsz.business.util.AppUtil;

public class RiskAssessmentStep3Activity extends BaseActivity implements View.OnClickListener {

    private HListView accidentalExposureToBlood;
    private HListView beenTattooedOrPierced;
    private HListView victiomOfSexualAbuse;
    private Button next;
    private Donor holder;
    private Counsellor counsellor;
    private ArrayAdapter<YesNo> adapter;
    private HListView[] fields;
    private String donorNumber;
    private Donor item;
    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_assessment_step3);
        Intent intent = getIntent();
        holder = (Donor) intent.getSerializableExtra("holder");
        Log.d("Donor", AppUtil.createGson().toJson(holder));
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNumber = intent.getStringExtra("donorNumber");
        id = intent.getLongExtra("id", 0L);
        accidentalExposureToBlood = (HListView) findViewById(R.id.accidental_exposure);
        beenTattooedOrPierced = (HListView) findViewById(R.id.been_tattooed);
        victiomOfSexualAbuse = (HListView) findViewById(R.id.victim_of_sexual_abuse);
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        fields = new HListView[]{accidentalExposureToBlood, beenTattooedOrPierced, victiomOfSexualAbuse};
        adapter = new ArrayAdapter<>(this, R.layout.check_box_item, YesNo.values());
        accidentalExposureToBlood.setAdapter(adapter);
        accidentalExposureToBlood.setItemsCanFocus(false);
        accidentalExposureToBlood.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        beenTattooedOrPierced.setAdapter(adapter);
        beenTattooedOrPierced.setItemsCanFocus(false);
        beenTattooedOrPierced.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        victiomOfSexualAbuse.setAdapter(adapter);
        victiomOfSexualAbuse.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        victiomOfSexualAbuse.setItemsCanFocus(false);
        adapter.notifyDataSetChanged();
        setSupportActionBar(createToolBar("NBSZ - SECTION B"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(donorNumber != null && ! donorNumber.isEmpty() && holder.accidentalExposureToBlood != null){
            item = Donor.findByDonorNumber(donorNumber);
            YesNo item =  holder.accidentalExposureToBlood;
            int count = adapter.getCount();
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    accidentalExposureToBlood.setItemChecked(i, true);
                }
            }

            item = holder.beenTattooedOrPierced;
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    beenTattooedOrPierced.setItemChecked(i, true);
                }
            }
            item = holder.victimOfSexualAbuse;
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    victiomOfSexualAbuse.setItemChecked(i, true);
                }
            }
        }else if(holder.accidentalExposureToBlood != null){
            YesNo item =  holder.accidentalExposureToBlood;
            int count = adapter.getCount();
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    accidentalExposureToBlood.setItemChecked(i, true);
                }
            }
            item = holder.beenTattooedOrPierced;
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    beenTattooedOrPierced.setItemChecked(i, true);
                }
            }
            item = holder.victimOfSexualAbuse;
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    victiomOfSexualAbuse.setItemChecked(i, true);
                }
            }
        }else {
            item = new Donor();
        }
    }

    @Override
    public void onClick(View view) {
        if(validateListView(fields)){
            holder.accidentalExposureToBlood = getAccidentalExposureToBlood();
            holder.beenTattooedOrPierced = getBeenTattooedOrPierced();
            holder.victimOfSexualAbuse = getVictiomOfSexualAbuse();
            Intent intent = new Intent(this, RiskAssessmentStep6Activity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            intent.putExtra("id", id);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onBackPressed(){
        holder.accidentalExposureToBlood = getAccidentalExposureToBlood();
        holder.beenTattooedOrPierced = getBeenTattooedOrPierced();
        holder.victimOfSexualAbuse = getVictiomOfSexualAbuse();
        Intent intent = new Intent(this, RiskAssessmentStep2Activity.class);
        intent.putExtra("holder", holder);
        intent.putExtra("counsellor", counsellor);
        intent.putExtra("donorNumber", donorNumber);
        intent.putExtra("id", id);
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

    private YesNo getAccidentalExposureToBlood(){
        YesNo b = null;
        for(int i = 0; i < accidentalExposureToBlood.getCount(); i++){
            if(accidentalExposureToBlood.isItemChecked(i)){
                b = adapter.getItem(i);
            }
        }
        return b;
    }



    private YesNo getBeenTattooedOrPierced(){
        YesNo a = null;
        for(int i = 0; i < beenTattooedOrPierced.getCount(); i++){
            if(beenTattooedOrPierced.isItemChecked(i)){
                a = adapter.getItem(i);
            }
        }
        return a;
    }

    private YesNo getVictiomOfSexualAbuse(){
        YesNo a = null;
        for(int i = 0; i < victiomOfSexualAbuse.getCount(); i++){
            if(victiomOfSexualAbuse.isItemChecked(i)){
                a = adapter.getItem(i);
            }
        }
        return a;
    }
}
