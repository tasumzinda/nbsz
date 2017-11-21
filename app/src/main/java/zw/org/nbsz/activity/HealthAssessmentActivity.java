package zw.org.nbsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import it.sephiroth.android.library.widget.HListView;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.Counsellor;
import zw.org.nbsz.business.domain.Donor;
import zw.org.nbsz.business.domain.util.YesNo;
import zw.org.nbsz.business.util.Log;

public class HealthAssessmentActivity extends BaseActivity implements View.OnClickListener {

    private HListView feelingWell;
    private HListView refusedToDonate;
    private HListView beenToMalariaArea;
    private HListView mealOrSnack;
    private HListView dangerousOccupation;
    private Button next;
    private Donor holder;
    private String donorNumber;
    private Donor item;
    private Counsellor counsellor;
    private ArrayAdapter<YesNo> yesNoArrayAdapter;
    private HListView[] fields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_assessment);
        Intent intent = getIntent();
        holder = (Donor) intent.getSerializableExtra("holder");
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNumber = intent.getStringExtra("donorNumber");
        feelingWell = (HListView) findViewById(R.id.feeling_well_today);
        refusedToDonate = (HListView) findViewById(R.id.refused_to_donate);
        beenToMalariaArea = (HListView) findViewById(R.id.been_to_a_malaria_area);
        mealOrSnack = (HListView) findViewById(R.id.meal_or_snack);
        dangerousOccupation = (HListView) findViewById(R.id.dangerous_occupation);
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        fields = new HListView[]{feelingWell, refusedToDonate, beenToMalariaArea, mealOrSnack, dangerousOccupation};
        setSupportActionBar(createToolBar("NBSZ - SECTION A"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        yesNoArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, YesNo.values());
        refusedToDonate.setAdapter(yesNoArrayAdapter);
        refusedToDonate.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        refusedToDonate.setItemsCanFocus(false);
        beenToMalariaArea.setAdapter(yesNoArrayAdapter);
        beenToMalariaArea.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        beenToMalariaArea.setItemsCanFocus(false);
        mealOrSnack.setAdapter(yesNoArrayAdapter);
        mealOrSnack.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mealOrSnack.setItemsCanFocus(false);
        dangerousOccupation.setAdapter(yesNoArrayAdapter);
        dangerousOccupation.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        dangerousOccupation.setItemsCanFocus(false);
        feelingWell.setAdapter(yesNoArrayAdapter);
        feelingWell.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        feelingWell.setItemsCanFocus(false);
        yesNoArrayAdapter.notifyDataSetChanged();
        if(donorNumber != null && ! donorNumber.isEmpty() && holder.feelingWellToday != null){
            item = Donor.findByDonorNumber(donorNumber);
            YesNo item =  holder.feelingWellToday;
            int count = yesNoArrayAdapter.getCount();
            for(int i = 0; i < count; i++){
                YesNo current = yesNoArrayAdapter.getItem(i);
                if(current.equals(item)){
                    feelingWell.setItemChecked(i, true);
                }
            }
            item = holder.refusedToDonate;
            for(int i = 0; i < count; i++){
                YesNo current = yesNoArrayAdapter.getItem(i);
                if(current.equals(item)){
                    refusedToDonate.setItemChecked(i, true);
                }
            }
            item = holder.beenToMalariaArea;
            for(int i = 0; i < count; i++){
                YesNo current = yesNoArrayAdapter.getItem(i);
                if(current.equals(item)){
                    beenToMalariaArea.setItemChecked(i, true);
                }
            }
            item = holder.mealOrSnack;
            for(int i = 0; i < count; i++){
                YesNo current = yesNoArrayAdapter.getItem(i);
                if(current.equals(item)){
                    mealOrSnack.setItemChecked(i, true);
                }
            }
            item = holder.dangerousOccupation;
            for(int i = 0; i < count; i++){
                YesNo current = yesNoArrayAdapter.getItem(i);
                if(current.equals(item)){
                    dangerousOccupation.setItemChecked(i, true);
                }
            }
        }else if(holder.feelingWellToday != null){
            YesNo item =  holder.feelingWellToday;
            int count = yesNoArrayAdapter.getCount();
            for(int i = 0; i < count; i++){
                YesNo current = yesNoArrayAdapter.getItem(i);
                if(current.equals(item)){
                    feelingWell.setItemChecked(i, true);
                }
            }
            item = holder.refusedToDonate;
            for(int i = 0; i < count; i++){
                YesNo current = yesNoArrayAdapter.getItem(i);
                if(current.equals(item)){
                    refusedToDonate.setItemChecked(i, true);
                }
            }
            item = holder.beenToMalariaArea;
            for(int i = 0; i < count; i++){
                YesNo current = yesNoArrayAdapter.getItem(i);
                if(current.equals(item)){
                    beenToMalariaArea.setItemChecked(i, true);
                }
            }
            item = holder.mealOrSnack;
            for(int i = 0; i < count; i++){
                YesNo current = yesNoArrayAdapter.getItem(i);
                if(current.equals(item)){
                    mealOrSnack.setItemChecked(i, true);
                }
            }
            item = holder.dangerousOccupation;
            for(int i = 0; i < count; i++){
                YesNo current = yesNoArrayAdapter.getItem(i);
                if(current.equals(item)){
                    dangerousOccupation.setItemChecked(i, true);
                }
            }
        }else{
            item = new Donor();
        }
    }

    @Override
    public void onClick(View view) {
        if(validateListView(fields)){
            Intent intent = new Intent(this, HealthAssessmentStep2Activity.class);
            if(holder == null){
                holder = Donor.findByDonorNumber(donorNumber);
            }
            holder.feelingWellToday = getFeelingWellToday();
            holder.refusedToDonate = getRefusedToDonate();
            holder.beenToMalariaArea = getBeenToMalariaArea();
            holder.mealOrSnack = getMealOrSnack();
            holder.dangerousOccupation = getDangerousOccupation();
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent;
        if(holder == null){
            holder = Donor.findByDonorNumber(donorNumber);
        }
        holder.feelingWellToday = getFeelingWellToday();
        holder.refusedToDonate = getRefusedToDonate();
        holder.beenToMalariaArea = getBeenToMalariaArea();
        holder.mealOrSnack = getMealOrSnack();
        holder.dangerousOccupation = getDangerousOccupation();
        if(holder.consentToUpdate != null && holder.consentToUpdate.equals(YesNo.NO)){
            intent = new Intent(this, ConsentToUpdateDetailsActivity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            startActivity(intent);
            finish();
        }else{
            intent = new Intent(this, CounsellorDetailsActivity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            startActivity(intent);
            finish();
        }

    }

    private YesNo getFeelingWellToday(){
        YesNo b = null;
        for(int i = 0; i < feelingWell.getCount(); i++){
            if(feelingWell.isItemChecked(i)){
                b = yesNoArrayAdapter.getItem(i);
            }
        }
        return b;
    }

    private YesNo getRefusedToDonate(){
        YesNo a = null;
        for(int i = 0; i < refusedToDonate.getCount(); i++){
            if(refusedToDonate.isItemChecked(i)){
                a = yesNoArrayAdapter.getItem(i);
            }
        }
        return a;
    }

    private YesNo getBeenToMalariaArea(){
        YesNo a = null;
        for(int i = 0; i < beenToMalariaArea.getCount(); i++){
            if(beenToMalariaArea.isItemChecked(i)){
                a = yesNoArrayAdapter.getItem(i);
            }
        }
        return a;
    }

    private YesNo getMealOrSnack(){
        YesNo a = null;
        for(int i = 0; i < mealOrSnack.getCount(); i++){
            if(mealOrSnack.isItemChecked(i)){
                a = yesNoArrayAdapter.getItem(i);
            }
        }
        return a;
    }

    private YesNo getDangerousOccupation(){
        YesNo a = null;
        for(int i = 0; i < dangerousOccupation.getCount(); i++){
            if(dangerousOccupation.isItemChecked(i)){
                a = yesNoArrayAdapter.getItem(i);
            }
        }
        return a;
    }
}
