package zw.org.nbsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.Counsellor;
import zw.org.nbsz.business.domain.DonationType;
import zw.org.nbsz.business.domain.Donor;
import zw.org.nbsz.business.domain.util.DonateDefer;
import zw.org.nbsz.business.domain.util.YesNo;
import zw.org.nbsz.business.util.AppUtil;

public class DeclarationFinalActivity extends BaseActivity implements View.OnClickListener {

    private ListView donate;
    private Button next;
    private Donor holder;
    private Counsellor counsellor;
    private ArrayAdapter<DonateDefer> adapter;
    private String donorNumber;
    private Donor item;
    @BindView(R.id.donation_type)
    Spinner donationType;
    private ArrayAdapter<DonationType> donationTypeArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declaration_final);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        holder = (Donor) intent.getSerializableExtra("holder");
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNumber = intent.getStringExtra("donorNumber");
        donate = (ListView) findViewById(R.id.list);
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        adapter = new ArrayAdapter<>(this, R.layout.check_box_item, DonateDefer.values());
        donate.setAdapter(adapter);
        donate.setItemsCanFocus(false);
        donate.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        donationTypeArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, DonationType.getAll());
        donationTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        donationType.setAdapter(donationTypeArrayAdapter);
        donationTypeArrayAdapter.notifyDataSetChanged();
        if(donorNumber != null && ! donorNumber.isEmpty() && holder.donateDefer != null){
            item = Donor.findByDonorNumber(donorNumber);
            for(int i = 0; i < adapter.getCount(); i++){
                DonateDefer item = adapter.getItem(i);
                if(item.equals(holder.donateDefer)){
                    donate.setItemChecked(i, true);
                }
            }
            int i = 0;
            for(DonationType d : DonationType.getAll()){
                if(holder.donationTypeId != null && holder.donationTypeId.equals(((DonationType) donationType.getSelectedItem()).serverId)){
                    donationType.setSelection(i, true);
                    break;
                }
                i++;
            }
        }else if(holder.donateDefer != null){
            for(int i = 0; i < adapter.getCount(); i++){
                DonateDefer item = adapter.getItem(i);
                if(item.equals(holder.donateDefer)){
                    donate.setItemChecked(i, true);
                }
            }

            int i = 0;
            for(DonationType d : DonationType.getAll()){
                if(holder.donationTypeId != null && holder.donationTypeId.equals(((DonationType) donationType.getSelectedItem()).serverId)){
                    donationType.setSelection(i, true);
                    break;
                }
                i++;
            }
        }else {
            item = new Donor();
        }
        setSupportActionBar(createToolBar("NSBZ - Consent To Donate"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        if(donate.getCheckedItemCount() != 0){
            holder.donateDefer = getCheckedItem();
            holder.donationTypeId = ((DonationType) donationType.getSelectedItem()).serverId;
            if(holder.donateDefer.equals(DonateDefer.DEFER)){
                Intent intent = new Intent(this, DeferStep1.class);
                intent.putExtra("holder", holder);
                intent.putExtra("counsellor", counsellor);
                intent.putExtra("donorNumber", donorNumber);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(this, NurseStep1Activity.class);
                intent.putExtra("holder", holder);
                intent.putExtra("counsellor", counsellor);
                intent.putExtra("donorNumber", donorNumber);
                startActivity(intent);
                finish();
            }

        }else{
            AppUtil.createShortNotification(this, "Sorry, this response is required");
        }

    }

    public void onBackPressed(){
        holder.donationTypeId = ((DonationType) donationType.getSelectedItem()).serverId;
        if(holder.feelingWellToday.equals(YesNo.NO) || holder.refusedToDonate.equals(YesNo.YES) || holder.beenToMalariaArea.equals(YesNo.YES)
                || holder.mealOrSnack.equals(YesNo.NO) || holder.dangerousOccupation.equals(YesNo.YES) || holder.rheumaticFever.equals(YesNo.YES)
                || holder.lungDisease.equals(YesNo.YES) || holder.cancer.equals(YesNo.YES) || holder.diabetes.equals(YesNo.YES)
                || holder.chronicMedicalCondition.equals(YesNo.YES) || holder.beenToDentist.equals(YesNo.YES) || holder.takenAntibiotics.equals(YesNo.YES)
                || holder.receivedBloodTransfusion.equals(YesNo.YES)
                || holder.hivTest.equals(YesNo.YES) || holder.beenTestedForHiv.equals(YesNo.YES) || holder.contactWithPersonWithYellowJaundice.equals(YesNo.YES)
                || holder.accidentalExposureToBlood.equals(YesNo.YES) || holder.beenTattooedOrPierced.equals(YesNo.YES) || holder.sexWithSomeoneWithUnknownBackground.equals(YesNo.YES)
                || holder.exchangedMoneyForSex.equals(YesNo.YES) || holder.trueForSexPartner.equals(YesNo.YES) || holder.sufferedFromSTD.equals(YesNo.YES) || holder.contactWithPersonWithHepatitisB.equals(YesNo.YES)
                || holder.sufferedFromNightSweats.equals(YesNo.YES) || holder.victimOfSexualAbuse.equals(YesNo.YES)) {
            Intent intent = new Intent(this, AcknowledgeResponsesActivity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(this, RiskAssessmentStep6Activity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            startActivity(intent);
            finish();
        }
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

    public DonateDefer getCheckedItem(){
        DonateDefer item = null;
        for(int i = 0; i < donate.getCount(); i++){
            if(donate.isItemChecked(i)){
                item = adapter.getItem(i);
            }
        }
        return item;
    }
}
