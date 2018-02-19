package zw.org.nbsz.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.*;
import zw.org.nbsz.business.domain.util.DonateDefer;
import zw.org.nbsz.business.domain.util.YesNo;
import zw.org.nbsz.business.util.AppUtil;
import zw.org.nbsz.business.util.DateUtil;
import zw.org.nbsz.business.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declaration_final);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        holder = (Donor) intent.getSerializableExtra("holder");
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNumber = intent.getStringExtra("donorNumber");
        id = intent.getLongExtra("id", 0L);
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
            //holder.donateDefer = getCheckedItem();
            //holder.donationTypeId = ((DonationType) donationType.getSelectedItem()).serverId;
            //saveDonorStep2();
            new SendDataAsync().execute();
            /*if(holder.donateDefer.equals(DonateDefer.DEFER)){
                Intent intent = new Intent(this, DeferStep1.class);
                intent.putExtra("holder", holder);
                intent.putExtra("counsellor", counsellor);
                intent.putExtra("donorNumber", donorNumber);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(this, NurseStep1Activity.class);
                intent.putExtra("holder", holder);
                intent.putExtra("counsellor", counsellor);
                intent.putExtra("donorNumber", donorNumber);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }*/

        }else{
            AppUtil.createShortNotification(this, "Sorry, this response is required");
        }

    }

    public void onBackPressed(){
        holder.donationTypeId = ((DonationType) donationType.getSelectedItem()).serverId;
        List<DonationStats> list = DonationStats.findByDonor(Donor.findById(id));
        DonationStats item = list.get(0);
        if(item.feelingWellToday.equals(YesNo.NO) || item.refusedToDonate.equals(YesNo.YES) || item.beenToMalariaArea.equals(YesNo.YES)
                || item.mealOrSnack.equals(YesNo.NO) || item.dangerousOccupation.equals(YesNo.YES) || item.rheumaticFever.equals(YesNo.YES)
                || item.lungDisease.equals(YesNo.YES) || item.cancer.equals(YesNo.YES) || item.diabetes.equals(YesNo.YES)
                || item.chronicMedicalCondition.equals(YesNo.YES) || item.beenToDentist.equals(YesNo.YES) || item.takenAntibiotics.equals(YesNo.YES)
                || item.receivedBloodTransfusion.equals(YesNo.YES)
                || holder.hivTest.equals(YesNo.YES) || holder.beenTestedForHiv.equals(YesNo.YES) || holder.contactWithPersonWithYellowJaundice.equals(YesNo.YES)
                || holder.accidentalExposureToBlood.equals(YesNo.YES) || holder.beenTattooedOrPierced.equals(YesNo.YES) || holder.sexWithSomeoneWithUnknownBackground.equals(YesNo.YES)
                || holder.exchangedMoneyForSex.equals(YesNo.YES) || holder.trueForSexPartner.equals(YesNo.YES) || holder.sufferedFromSTD.equals(YesNo.YES) || holder.contactWithPersonWithHepatitisB.equals(YesNo.YES)
                || holder.sufferedFromNightSweats.equals(YesNo.YES) || holder.victimOfSexualAbuse.equals(YesNo.YES)){
            Intent intent = new Intent(this, AcknowledgeResponsesActivity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            intent.putExtra("id", id);
            startActivity(intent);
            finish();
        }else{
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

    public void saveDonorStep2(){
        Thread mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Donor current = Donor.findById(id);
                List<DonationStats> list = DonationStats.findByDonor(current);
                DonationStats stats = list.get(0);
                stats.beenTestedForHiv = holder.beenTestedForHiv;
                stats.hivTest = holder.hivTest;
                stats.injectedWithIllegalDrugs = holder.injectedWithIllegalDrugs;
                stats.exchangedMoneyForSex = holder.exchangedMoneyForSex;
                stats.sufferedFromSTD = holder.sufferedFromSTD;
                stats.contactWithPersonWithHepatitisB = holder.contactWithPersonWithHepatitisB;
                stats.trueForSexPartner = holder.trueForSexPartner;
                stats.accidentalExposureToBlood = holder.accidentalExposureToBlood;
                stats.beenTattooedOrPierced = holder.beenTattooedOrPierced;
                stats.victimOfSexualAbuse = holder.victimOfSexualAbuse;
                stats.sufferedFromNightSweats = holder.sufferedFromNightSweats;
                stats.sexWithSomeoneWithUnknownBackground = holder.sexWithSomeoneWithUnknownBackground;
                stats.contactWithPersonWithYellowJaundice = holder.contactWithPersonWithYellowJaundice;
                stats.save();
                current.pushed = 2;
                current.donateDefer = getCheckedItem();
                current.donationType = (DonationType) donationType.getSelectedItem();
                current.save();
                current.genderValue = current.gender.getName();
                Log.d("Current", AppUtil.createGson().toJson(current));
                String result = sendMessage(AppUtil.createGson().toJson(current));
                //current.localId = result;
                //current.save();
                Log.d("Current", AppUtil.createGson().toJson(current));
                result = sendMessage(AppUtil.createGson().toJson(stats));
                //stats.localId = result;
                //stats.save();
                delete();
                sendRequestForTodayDonations();
                Intent intent = new Intent(getApplicationContext(), DonorListActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mThread.start();
        try{
            mThread.join();
        }catch(InterruptedException ex){
            ex.printStackTrace();
        }

    }

    private class SendDataAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = ProgressDialog.show(DeclarationFinalActivity.this, "Please wait...", "Syncing wth server", true);
            progressDialog.setCancelable(false);
        }
        @Override
        protected Void doInBackground(Void... voids) {
            Donor current = Donor.findById(id);
            List<DonationStats> list = DonationStats.findByDonor(current);
            DonationStats stats = list.get(0);
            stats.beenTestedForHiv = holder.beenTestedForHiv;
            stats.hivTest = holder.hivTest;
            stats.injectedWithIllegalDrugs = holder.injectedWithIllegalDrugs;
            stats.exchangedMoneyForSex = holder.exchangedMoneyForSex;
            stats.sufferedFromSTD = holder.sufferedFromSTD;
            stats.contactWithPersonWithHepatitisB = holder.contactWithPersonWithHepatitisB;
            stats.trueForSexPartner = holder.trueForSexPartner;
            stats.accidentalExposureToBlood = holder.accidentalExposureToBlood;
            stats.beenTattooedOrPierced = holder.beenTattooedOrPierced;
            stats.victimOfSexualAbuse = holder.victimOfSexualAbuse;
            stats.sufferedFromNightSweats = holder.sufferedFromNightSweats;
            stats.sexWithSomeoneWithUnknownBackground = holder.sexWithSomeoneWithUnknownBackground;
            stats.contactWithPersonWithYellowJaundice = holder.contactWithPersonWithYellowJaundice;
            stats.save();
            current.pushed = 2;
            current.donateDefer = getCheckedItem();
            current.donationType = (DonationType) donationType.getSelectedItem();
            current.save();
            current.genderValue = current.gender.getName();
            Log.d("Current", AppUtil.createGson().toJson(current));
            String result = sendMessage(AppUtil.createGson().toJson(current));
            //current.localId = result;
            //current.save();
            Log.d("Current", AppUtil.createGson().toJson(current));
            result = sendMessage(AppUtil.createGson().toJson(stats));
            //stats.localId = result;
            //stats.save();
            delete();
            sendRequestForTodayDonations();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(progressDialog != null){
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }
            Intent intent = new Intent(getApplicationContext(), DonorListActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
