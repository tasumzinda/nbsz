package zw.org.nbsz.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.*;
import zw.org.nbsz.business.domain.util.DonateDefer;
import zw.org.nbsz.business.util.AppUtil;
import zw.org.nbsz.business.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DonorFinalActivity extends BaseActivity implements View.OnClickListener {

    private Button next;
    private Donor holder;
    private Counsellor counsellor;
    private String donorNumber;
    private Donor item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_final);
        Intent intent = getIntent();
        holder = (Donor) intent.getSerializableExtra("holder");
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNumber = intent.getStringExtra("donorNumber");
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        setSupportActionBar(createToolBar("Consent To Donate"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(donorNumber != null && ! donorNumber.isEmpty()){
            item = Donor.findByDonorNumber(donorNumber);
        }else {
            item = new Donor();
        }
    }

    @Override
    public void onClick(View view) {
        new SendDataAsync().execute();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, DeclarationStep7Activity.class);
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

    public void saveDonorStage1(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(holder.dateOfBirth != null){
                    item.dateOfBirth = holder.dateOfBirth;
                    item.dob = DateUtil.formatDate(holder.dateOfBirth);
                }
                if(holder.idNumber != null){
                    item.idNumber = holder.idNumber;
                }
                if(holder.surname != null){
                    item.surname = holder.surname;
                }
                if(holder.firstName != null){
                    item.firstName = holder.firstName;
                }
                item.collectSite = CollectSite.findByActive();
                if(holder.gender != null){
                    item.gender = holder.gender;
                }
                if(holder.cityId != null){
                    item.city = Centre.findById(holder.cityId);
                }
                if(holder.maritalStatusId != null){
                    item.maritalStatus = MaritalStatus.findById(holder.maritalStatusId);
                }
                if(holder.professionId != null){
                    item.profession = Profession.findById(holder.professionId);
                }
                if(holder.email != null){
                    item.email = holder.email;
                }
                if(holder.cellphoneNumber != null){
                    item.cellphoneNumber = holder.cellphoneNumber;
                }
                if(holder.workTelephone != null){
                    item.workTelephone = holder.workTelephone;
                }
                if(holder.homeTelephone != null){
                    item.homeTelephone = holder.homeTelephone;
                }
                if(holder.residentialAddress != null){
                    item.residentialAddress = holder.residentialAddress;
                }
                if(counsellor != null){
                    counsellor.save();
                    item.counsellor = counsellor;
                }
                item.entryDate = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                item.entryTime = sdf.format(System.currentTimeMillis());
                item.entry = DateUtil.getStringFromDate(new Date());
                item.pushed = 1;
                item.save();
                DonationStats stats = new DonationStats();
                stats.feelingWellToday = holder.feelingWellToday;
                stats.refusedToDonate = holder.refusedToDonate;
                stats.beenToMalariaArea = holder.beenToMalariaArea;
                stats.mealOrSnack = holder.mealOrSnack;
                stats.dangerousOccupation = holder.dangerousOccupation;
                stats.rheumaticFever = holder.rheumaticFever;
                stats.chronicMedicalCondition = holder.chronicMedicalCondition;
                stats.diabetes = holder.diabetes;
                stats.cancer = holder.cancer;
                stats.lungDisease = holder.lungDisease;
                stats.takenAntibiotics = holder.takenAntibiotics;
                stats.beenToDentist = holder.beenToDentist;
                stats.beenIll = holder.beenIll;
                stats.injection = holder.injection;
                stats.receivedBloodTransfusion = holder.receivedBloodTransfusion;
                stats.person = item;
                stats.save();
                item.genderValue = item.gender.getName();
                zw.org.nbsz.business.util.Log.d("Current", AppUtil.createGson().toJson(item));
                String result = sendMessage(AppUtil.createGson().toJson(item));
                item.localId = result;
                item.save();
                zw.org.nbsz.business.util.Log.d("Current", AppUtil.createGson().toJson(item));
                result = sendMessage(AppUtil.createGson().toJson(stats));
                stats.localId = result;
                stats.save();
                delete();
                sendRequestForTodayDonations();
                Intent intent = new Intent(getApplicationContext(), DonatedBloodActivity.class);
                //Intent intent = new Intent(getApplicationContext(), UserVerificationActivity.class);
                intent.putExtra("holder", holder);
                intent.putExtra("counsellor", counsellor);
                intent.putExtra("donorNumber", donorNumber);
                startActivity(intent);
                finish();
            }
        });
        thread.start();
        try{
            thread.join();
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }

    }

    private class SendDataAsync extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = ProgressDialog.show(DonorFinalActivity.this, "Please wait...", "Syncing wth server", true);
            progressDialog.setCancelable(false);
        }
        @Override
        protected Void doInBackground(Void... voids) {
            if(holder.dateOfBirth != null){
                item.dateOfBirth = holder.dateOfBirth;
                item.dob = DateUtil.formatDate(holder.dateOfBirth);
            }
            if(holder.idNumber != null){
                item.idNumber = holder.idNumber;
            }
            if(holder.surname != null){
                item.surname = holder.surname;
            }
            if(holder.firstName != null){
                item.firstName = holder.firstName;
            }
            item.collectSite = CollectSite.findByActive();
            if(holder.gender != null){
                item.gender = holder.gender;
            }
            if(holder.cityId != null){
                item.city = Centre.findById(holder.cityId);
            }
            if(holder.maritalStatusId != null){
                item.maritalStatus = MaritalStatus.findById(holder.maritalStatusId);
            }
            if(holder.professionId != null){
                item.profession = Profession.findById(holder.professionId);
            }
            if(holder.email != null){
                item.email = holder.email;
            }
            if(holder.cellphoneNumber != null){
                item.cellphoneNumber = holder.cellphoneNumber;
            }
            if(holder.workTelephone != null){
                item.workTelephone = holder.workTelephone;
            }
            if(holder.homeTelephone != null){
                item.homeTelephone = holder.homeTelephone;
            }
            if(holder.residentialAddress != null){
                item.residentialAddress = holder.residentialAddress;
            }
            if(counsellor != null){
                counsellor.save();
                item.counsellor = counsellor;
            }
            item.entryDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            item.entryTime = sdf.format(System.currentTimeMillis());
            item.entry = DateUtil.getStringFromDate(new Date());
            item.pushed = 1;
            item.save();
            DonationStats stats = new DonationStats();
            stats.feelingWellToday = holder.feelingWellToday;
            stats.refusedToDonate = holder.refusedToDonate;
            stats.beenToMalariaArea = holder.beenToMalariaArea;
            stats.mealOrSnack = holder.mealOrSnack;
            stats.dangerousOccupation = holder.dangerousOccupation;
            stats.rheumaticFever = holder.rheumaticFever;
            stats.chronicMedicalCondition = holder.chronicMedicalCondition;
            stats.diabetes = holder.diabetes;
            stats.cancer = holder.cancer;
            stats.lungDisease = holder.lungDisease;
            stats.takenAntibiotics = holder.takenAntibiotics;
            stats.beenToDentist = holder.beenToDentist;
            stats.beenIll = holder.beenIll;
            stats.injection = holder.injection;
            stats.receivedBloodTransfusion = holder.receivedBloodTransfusion;
            stats.person = item;
            stats.save();
            item.genderValue = item.gender.getName();
            zw.org.nbsz.business.util.Log.d("Current", AppUtil.createGson().toJson(item));
            String result = sendMessage(AppUtil.createGson().toJson(item));
            item.localId = result;
            item.save();
            zw.org.nbsz.business.util.Log.d("Current", AppUtil.createGson().toJson(item));
            result = sendMessage(AppUtil.createGson().toJson(stats));
            stats.localId = result;
            stats.save();
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
            /*Intent intent = new Intent(getApplicationContext(), DonatedBloodActivity.class);
            //Intent intent = new Intent(getApplicationContext(), UserVerificationActivity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            startActivity(intent);
            finish();*/
        }
    }
}
