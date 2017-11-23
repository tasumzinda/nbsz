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

    private HListView contactWithPersonWithHepatitisB;
    private HListView sufferedFromNightSweats;
    private HListView sexWithSomeoneWithUnknownBackground;
    private Button next;
    private Donor holder;
    private Counsellor counsellor;
    private ArrayAdapter<YesNo> adapter;
    private HListView[] fields;
    private String donorNumber;
    private Donor item;
    private ArrayAdapter<YesNo> yesNoArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_assessment_step6);
        Intent intent = getIntent();
        holder = (Donor) intent.getSerializableExtra("holder");
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNumber = intent.getStringExtra("donorNumber");
        sufferedFromNightSweats = (HListView) findViewById(R.id.suffered_from_night_sweats);
        contactWithPersonWithHepatitisB = (HListView) findViewById(R.id.contact_hepatitisB);
        sexWithSomeoneWithUnknownBackground = (HListView) findViewById(R.id.casual_sex);
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        fields = new HListView[]{sufferedFromNightSweats, contactWithPersonWithHepatitisB, sexWithSomeoneWithUnknownBackground};
        adapter = new ArrayAdapter<>(this, R.layout.check_box_item, YesNo.values());
        sufferedFromNightSweats.setAdapter(adapter);
        sufferedFromNightSweats.setItemsCanFocus(false);
        sufferedFromNightSweats.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        sexWithSomeoneWithUnknownBackground.setAdapter(adapter);
        sexWithSomeoneWithUnknownBackground.setItemsCanFocus(false);
        sexWithSomeoneWithUnknownBackground.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter.notifyDataSetChanged();
        yesNoArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, YesNo.values());
        contactWithPersonWithHepatitisB.setAdapter(yesNoArrayAdapter);
        contactWithPersonWithHepatitisB.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        contactWithPersonWithHepatitisB.setItemsCanFocus(false);
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
            YesNo m = holder.contactWithPersonWithHepatitisB;
            for(int i = 0; i < count; i++){
                YesNo current = yesNoArrayAdapter.getItem(i);
                if(current.equals(m)){
                    contactWithPersonWithHepatitisB.setItemChecked(i, true);
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
            YesNo m = holder.contactWithPersonWithHepatitisB;
            for(int i = 0; i < count; i++){
                YesNo current = yesNoArrayAdapter.getItem(i);
                if(current.equals(m)){
                    contactWithPersonWithHepatitisB.setItemChecked(i, true);
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
            holder.contactWithPersonWithHepatitisB = getContactWithPersonWithHepatitisB();
            if(holder.feelingWellToday.equals(YesNo.NO) || holder.refusedToDonate.equals(YesNo.YES) || holder.beenToMalariaArea.equals(YesNo.YES)
                    || holder.mealOrSnack.equals(YesNo.NO) || holder.dangerousOccupation.equals(YesNo.YES) || holder.rheumaticFever.equals(YesNo.YES)
                    || holder.lungDisease.equals(YesNo.YES) || holder.cancer.equals(YesNo.YES) || holder.diabetes.equals(YesNo.YES)
                    || holder.chronicMedicalCondition.equals(YesNo.YES) || holder.beenToDentist.equals(YesNo.YES) || holder.takenAntibiotics.equals(YesNo.YES)
                    || holder.receivedBloodTransfusion.equals(YesNo.YES)
                    || holder.hivTest.equals(YesNo.YES) || holder.beenTestedForHiv.equals(YesNo.YES) || holder.contactWithPersonWithYellowJaundice.equals(YesNo.YES)
                    || holder.accidentalExposureToBlood.equals(YesNo.YES) || holder.beenTattooedOrPierced.equals(YesNo.YES) || holder.sexWithSomeoneWithUnknownBackground.equals(YesNo.YES)
                    || holder.exchangedMoneyForSex.equals(YesNo.YES) || holder.trueForSexPartner.equals(YesNo.YES) || holder.sufferedFromSTD.equals(YesNo.YES) || holder.contactWithPersonWithHepatitisB.equals(YesNo.YES)
                    || holder.sufferedFromNightSweats.equals(YesNo.YES) || holder.victimOfSexualAbuse.equals(YesNo.YES)){
                Intent intent = new Intent(this, AcknowledgeResponsesActivity.class);
                intent.putExtra("holder", holder);
                intent.putExtra("counsellor", counsellor);
                intent.putExtra("donorNumber", donorNumber);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(this, DeclarationFinalActivity.class);
                intent.putExtra("holder", holder);
                intent.putExtra("counsellor", counsellor);
                intent.putExtra("donorNumber", donorNumber);
                startActivity(intent);
                finish();
            }
        }

    }

    @Override
    public void onBackPressed(){
        holder.sufferedFromNightSweats = getSufferedFromNightSweats();
        holder.sexWithSomeoneWithUnknownBackground = getSexWithSomeoneWithUnknownBackground();
        holder.contactWithPersonWithHepatitisB = getContactWithPersonWithHepatitisB();
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

    private YesNo getContactWithPersonWithHepatitisB(){
        YesNo a = null;
        for(int i = 0; i < contactWithPersonWithHepatitisB.getCount(); i++){
            if(contactWithPersonWithHepatitisB.isItemChecked(i)){
                a = yesNoArrayAdapter.getItem(i);
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
