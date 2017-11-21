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
import zw.org.nbsz.business.util.Log;

public class HealthAssessmentStep2Activity extends BaseActivity implements View.OnClickListener {

    private HListView rheumaticFever;
    private HListView lungDisease;
    private HListView cancer;
    private HListView diabetes;
    private HListView chronicMedicalCondition;
    private Button next;
    private Donor holder;
    private Counsellor counsellor;
    private ArrayAdapter<YesNo> yesNoArrayAdapter;
    private HListView[] fields;
    private String donorNumber;
    private Donor item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_assessment_step2);
        Intent intent = getIntent();
        holder = (Donor) intent.getSerializableExtra("holder");
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNumber = intent.getStringExtra("donorNumber");
        rheumaticFever = (HListView) findViewById(R.id.rheumatic_fever);
        lungDisease = (HListView) findViewById(R.id.lung_disease);
        cancer = (HListView) findViewById(R.id.cancer);
        diabetes = (HListView) findViewById(R.id.diabetes);
        chronicMedicalCondition = (HListView) findViewById(R.id.chronic_medical_condition);
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        fields = new HListView[]{rheumaticFever, lungDisease, cancer, diabetes, chronicMedicalCondition};
        yesNoArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, YesNo.values());
        rheumaticFever.setAdapter(yesNoArrayAdapter);
        rheumaticFever.setItemsCanFocus(false);
        rheumaticFever.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lungDisease.setAdapter(yesNoArrayAdapter);
        lungDisease.setItemsCanFocus(false);
        lungDisease.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        cancer.setAdapter(yesNoArrayAdapter);
        cancer.setItemsCanFocus(false);
        cancer.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        diabetes.setAdapter(yesNoArrayAdapter);
        diabetes.setItemsCanFocus(false);
        diabetes.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        chronicMedicalCondition.setAdapter(yesNoArrayAdapter);
        chronicMedicalCondition.setItemsCanFocus(false);
        chronicMedicalCondition.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        yesNoArrayAdapter.notifyDataSetChanged();
        if(donorNumber != null && ! donorNumber.isEmpty() && holder.rheumaticFever != null){
            item = Donor.findByDonorNumber(donorNumber);
            YesNo item =  holder.rheumaticFever;
            int count = yesNoArrayAdapter.getCount();
            for(int i = 0; i < count; i++){
                YesNo current = yesNoArrayAdapter.getItem(i);
                if(current.equals(item)){
                    rheumaticFever.setItemChecked(i, true);
                }
            }
            item = holder.lungDisease;
            for(int i = 0; i < count; i++){
                YesNo current = yesNoArrayAdapter.getItem(i);
                if(current.equals(item)){
                    lungDisease.setItemChecked(i, true);
                }
            }
            item = holder.cancer;
            for(int i = 0; i < count; i++){
                YesNo current = yesNoArrayAdapter.getItem(i);
                if(current.equals(item)){
                    cancer.setItemChecked(i, true);
                }
            }
            item = holder.diabetes;
            for(int i = 0; i < count; i++){
                YesNo current = yesNoArrayAdapter.getItem(i);
                if(current.equals(item)){
                    diabetes.setItemChecked(i, true);
                }
            }
            item = holder.chronicMedicalCondition;
            for(int i = 0; i < count; i++){
                YesNo current = yesNoArrayAdapter.getItem(i);
                if(current.equals(item)){
                    chronicMedicalCondition.setItemChecked(i, true);
                }
            }
        }else if(holder.rheumaticFever != null){
            YesNo item =  holder.rheumaticFever;
            int count = yesNoArrayAdapter.getCount();
            for(int i = 0; i < count; i++){
                YesNo current = yesNoArrayAdapter.getItem(i);
                if(current.equals(item)){
                    rheumaticFever.setItemChecked(i, true);
                }
            }
            item = holder.lungDisease;
            for(int i = 0; i < count; i++){
                YesNo current = yesNoArrayAdapter.getItem(i);
                if(current.equals(item)){
                    lungDisease.setItemChecked(i, true);
                }
            }
            item = holder.cancer;
            for(int i = 0; i < count; i++){
                YesNo current = yesNoArrayAdapter.getItem(i);
                if(current.equals(item)){
                    cancer.setItemChecked(i, true);
                }
            }
            item = holder.diabetes;
            for(int i = 0; i < count; i++){
                YesNo current = yesNoArrayAdapter.getItem(i);
                if(current.equals(item)){
                    diabetes.setItemChecked(i, true);
                }
            }
            item = holder.chronicMedicalCondition;
            for(int i = 0; i < count; i++){
                YesNo current = yesNoArrayAdapter.getItem(i);
                if(current.equals(item)){
                    chronicMedicalCondition.setItemChecked(i, true);
                }
            }
        }else{
            item = new Donor();
        }
        setSupportActionBar(createToolBar("NBSZ - SECTION A"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        if(validateListView(fields)){
            holder.rheumaticFever = getRheumaticFever();
            holder.lungDisease = getLungDisease();
            holder.cancer = getCancer();
            holder.diabetes = getDiabetes();
            holder.chronicMedicalCondition = getChronicMedicalCondition();
            Intent intent = new Intent(this, HealthAssessmentStep3Activity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onBackPressed(){
        holder.rheumaticFever = getRheumaticFever();
        holder.lungDisease = getLungDisease();
        holder.cancer = getCancer();
        holder.diabetes = getDiabetes();
        holder.chronicMedicalCondition = getChronicMedicalCondition();
        Intent intent = new Intent(this, HealthAssessmentActivity.class);
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

    private YesNo getRheumaticFever(){
        YesNo b = null;
        for(int i = 0; i < rheumaticFever.getCount(); i++){
            if(rheumaticFever.isItemChecked(i)){
                b = yesNoArrayAdapter.getItem(i);
            }
        }
        return b;
    }

    private YesNo getLungDisease(){
        YesNo a = null;
        for(int i = 0; i < lungDisease.getCount(); i++){
            if(lungDisease.isItemChecked(i)){
                a = yesNoArrayAdapter.getItem(i);
            }
        }
        return a;
    }

    private YesNo getCancer(){
        YesNo a = null;
        for(int i = 0; i < cancer.getCount(); i++){
            if(cancer.isItemChecked(i)){
                a = yesNoArrayAdapter.getItem(i);
            }
        }
        return a;
    }

    private YesNo getDiabetes(){
        YesNo a = null;
        for(int i = 0; i < diabetes.getCount(); i++){
            if(diabetes.isItemChecked(i)){
                a = yesNoArrayAdapter.getItem(i);
            }
        }
        return a;
    }

    private YesNo getChronicMedicalCondition(){
        YesNo a = null;
        for(int i = 0; i < chronicMedicalCondition.getCount(); i++){
            if(chronicMedicalCondition.isItemChecked(i)){
                a = yesNoArrayAdapter.getItem(i);
            }
        }
        return a;
    }
}
