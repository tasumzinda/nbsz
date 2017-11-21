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
import zw.org.nbsz.business.domain.util.YesNoNA;

public class RiskAssessmentStep6Activity extends BaseActivity implements View.OnClickListener {

    private HListView monogamousRelationship;
    private HListView sufferedFromNightSweats;
    private HListView sexWithSomeoneWithUnknownBackground;
    private Button next;
    private Donor holder;
    private Counsellor counsellor;
    private ArrayAdapter<YesNo> adapter;
    private HListView[] fields;
    private String donorNumber;
    private Donor item;
    private ArrayAdapter<YesNoNA> yesNoNAArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_assessment_step6);
        Intent intent = getIntent();
        holder = (Donor) intent.getSerializableExtra("holder");
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNumber = intent.getStringExtra("donorNumber");
        sufferedFromNightSweats = (HListView) findViewById(R.id.suffered_from_night_sweats);
        monogamousRelationship = (HListView) findViewById(R.id.change_in_marital_status);
        sexWithSomeoneWithUnknownBackground = (HListView) findViewById(R.id.casual_sex);
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        fields = new HListView[]{sufferedFromNightSweats, monogamousRelationship, sexWithSomeoneWithUnknownBackground};
        adapter = new ArrayAdapter<>(this, R.layout.check_box_item, YesNo.values());
        sufferedFromNightSweats.setAdapter(adapter);
        sufferedFromNightSweats.setItemsCanFocus(false);
        sufferedFromNightSweats.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        sexWithSomeoneWithUnknownBackground.setAdapter(adapter);
        sexWithSomeoneWithUnknownBackground.setItemsCanFocus(false);
        sexWithSomeoneWithUnknownBackground.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter.notifyDataSetChanged();
        yesNoNAArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, YesNoNA.values());
        monogamousRelationship.setAdapter(yesNoNAArrayAdapter);
        monogamousRelationship.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        monogamousRelationship.setItemsCanFocus(false);
        setSupportActionBar(createToolBar("NBSZ - SECTION B"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(donorNumber != null && ! donorNumber.isEmpty() && holder.sufferedFromNightSweats != null){
            item = Donor.findByDonorNumber(donorNumber);
            YesNo item =  holder.sufferedFromNightSweats;
            int count = adapter.getCount();
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    sufferedFromNightSweats.setItemChecked(i, true);
                }
            }
            YesNoNA m = holder.monogamousRelationship;
            for(int i = 0; i < count; i++){
                YesNoNA current = yesNoNAArrayAdapter.getItem(i);
                if(current.equals(m)){
                    monogamousRelationship.setItemChecked(i, true);
                }
            }
            item = holder.sexWithSomeoneWithUnknownBackground;
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    sexWithSomeoneWithUnknownBackground.setItemChecked(i, true);
                }
            }
        }else if(holder.sufferedFromNightSweats != null){
            YesNo item =  holder.sufferedFromNightSweats;
            int count = adapter.getCount();
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    sufferedFromNightSweats.setItemChecked(i, true);
                }
            }
            YesNoNA m = holder.monogamousRelationship;
            for(int i = 0; i < count; i++){
                YesNoNA current = yesNoNAArrayAdapter.getItem(i);
                if(current.equals(m)){
                    monogamousRelationship.setItemChecked(i, true);
                }
            }
            item = holder.sexWithSomeoneWithUnknownBackground;
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    sexWithSomeoneWithUnknownBackground.setItemChecked(i, true);
                }
            }
        }else{
            item = new Donor();
        }
    }

    @Override
    public void onClick(View view) {
        if(validateListView(fields)){
            holder.sufferedFromNightSweats = getSufferedFromNightSweats();
            holder.sexWithSomeoneWithUnknownBackground = getSexWithSomeoneWithUnknownBackground();
            holder.monogamousRelationship = getMonogamousRelationship();
            Intent intent = new Intent(this, ConsentToDonateActivity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onBackPressed(){
        holder.sufferedFromNightSweats = getSufferedFromNightSweats();
        holder.sexWithSomeoneWithUnknownBackground = getSexWithSomeoneWithUnknownBackground();
        holder.monogamousRelationship = getMonogamousRelationship();
        Intent intent = new Intent(this, RiskAssessmentStep3Activity.class);
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

    private YesNo getSufferedFromNightSweats(){
        YesNo b = null;
        for(int i = 0; i < sufferedFromNightSweats.getCount(); i++){
            if(sufferedFromNightSweats.isItemChecked(i)){
                b = adapter.getItem(i);
            }
        }
        return b;
    }

    private YesNoNA getMonogamousRelationship(){
        YesNoNA a = null;
        for(int i = 0; i < monogamousRelationship.getCount(); i++){
            if(monogamousRelationship.isItemChecked(i)){
                a = yesNoNAArrayAdapter.getItem(i);
            }
        }
        return a;
    }

    private YesNo getSexWithSomeoneWithUnknownBackground(){
        YesNo a = null;
        for(int i = 0; i < sexWithSomeoneWithUnknownBackground.getCount(); i++){
            if(sexWithSomeoneWithUnknownBackground.isItemChecked(i)){
                a = adapter.getItem(i);
            }
        }
        return a;
    }
}
