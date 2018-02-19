package zw.org.nbsz.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.*;
import zw.org.nbsz.business.domain.util.DonateDefer;
import zw.org.nbsz.business.domain.util.YesNo;
import zw.org.nbsz.business.rest.PushPullService;
import zw.org.nbsz.business.rest.PushService;
import zw.org.nbsz.business.util.AppUtil;
import zw.org.nbsz.business.util.DateUtil;
import zw.org.nbsz.business.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NurseFinalActivity extends BaseActivity implements View.OnClickListener {

    private CheckBox finalized;
    private Button save;
    private ListView incentive;
    private Donor holder;
    private Counsellor counsellor;
    private String donorNumber;
    private Donor item;
    private ArrayAdapter<Incentive> adapter;
    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_final);
        finalized = (CheckBox) findViewById(R.id.acknowledge);
        incentive = (ListView) findViewById(R.id.incentive);
        save = (Button) findViewById(R.id.btn_save);
        save.setOnClickListener(this);
        Intent intent = getIntent();
        holder = (Donor) intent.getSerializableExtra("holder");
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNumber = intent.getStringExtra("donorNumber");
        id = intent.getLongExtra("id", 0L);
        setSupportActionBar(createToolBar("NBSZ - Save Donor Details"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        adapter = new ArrayAdapter<>(this, R.layout.check_box_item, Incentive.getAll());
        incentive.setAdapter(adapter);
        incentive.setItemsCanFocus(false);
        incentive.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        adapter.notifyDataSetChanged();
        if(donorNumber != null && ! donorNumber.isEmpty()){
            item = Donor.findByDonorNumber(donorNumber);
        }
        if(item != null){
            item = Donor.findByDonorNumber(donorNumber);
            item.donorNumber = donorNumber;
        } else if (holder.incentives != null) {
            ArrayList<Long> incentives = holder.incentives;
            int count = adapter.getCount();
            for(int i = 0; i < count; i++){
                Incentive m = adapter.getItem(i);
                if(incentives.contains(m.serverId)){
                    incentive.setItemChecked(i, true);
                }
            }
        } else {
            item = new Donor();
            item.isNew = 1;
            item.donorNumber = holder.donorNumber;
        }
    }

    @Override
    public void onClick(View view) {
        if(finalized.isChecked()){
            saveFinalStage();

        }else{
            AppUtil.createShortNotification(this, "Please mark form as finalized before saving it");
        }

    }

    @Override
    public void onBackPressed(){
        holder.incentives = getIncentives();
        if(holder.donateDefer.equals(DonateDefer.DEFER)){
            Intent intent = new Intent(this, DeferStep1.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            intent.putExtra("id", id);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(this, NurseStep2Activity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            intent.putExtra("id", id);
            startActivity(intent);
            finish();
        }

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

    //Old save method
    public void save(){
        if(counsellor != null){
            counsellor.save();
            item.counsellor = counsellor;
        }
        /*item.donateDefer = holder.donateDefer;
        if(holder.cityId != null){
            item.city = Centre.findById(holder.cityId);
        }
        if(holder.maritalStatusId != null){
            item.maritalStatus = MaritalStatus.findById(holder.maritalStatusId);
        }
        if(holder.professionId != null){
            item.profession = Profession.findById(holder.professionId);
        }
        if(holder.donationTypeId != null){
            item.donationType = DonationType.findById(holder.donationTypeId);
        }
        item.collectSite = CollectSite.findByActive();
        if(holder.gender != null){
            item.gender = holder.gender;
        }

        if(holder.donateDefer != null && holder.donateDefer.equals(DonateDefer.DEFER)){
            if(holder.defferedReasonId != null){
                item.deferredReason = DeferredReason.findById(holder.defferedReasonId);
            }
            if(holder.deferNotes != null){
                item.deferNotes = holder.deferNotes;
            }
            item.deferPeriod = holder.deferPeriod;
            item.deferDate = DateUtil.getStringFromDate(new Date());
            item.accepted = "T";
        }else{
            item.accepted = "A";
        }
        if(holder.dateOfBirth != null){
            item.dateOfBirth = holder.dateOfBirth;
            item.dob = DateUtil.formatDateRest(holder.dateOfBirth);
        }*/
        if(holder.userId != null){
            item.bledBy = User.findById(holder.userId);
        }
        /*if(holder.email != null){
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
        if(holder.idNumber != null){
            item.idNumber = holder.idNumber;
        }
        if(holder.surname != null){
            item.surname = holder.surname;
        }
        if(holder.firstName != null){
            item.firstName = holder.firstName;
        }*/
        /*item.entryDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        item.entryTime = sdf.format(System.currentTimeMillis());
        item.entry = DateUtil.getStringFromDate(new Date());
        item.pushed = 1;
        item.save();*/
        if(holder.donateDefer.equals(DonateDefer.DONATE)){
            Donation d = new Donation();
            d.date = new Date();
            d.donationType = item.donationType;
            d.person = item;
            d.timDonation = item.entryTime;
            d.donationNumber = holder.donationNumber;
            if(holder.dateOfBirth != null){
                d.donorAge = DateUtil.getYears(holder.dateOfBirth);
            }
            d.city = CollectSite.findByActive().centre;
            d.save();
        }
        Offer offer = new Offer();
        offer.offerDate = new Date();
        offer.person = item;
        offer.centre = CollectSite.findByActive().centre;
        offer.collectSite = CollectSite.findByActive();
        if(holder.donationTypeId != null){
            offer.donationType = item.donationType;
            offer.donationKind = "D";
            offer.donationNumber = holder.donationNumber;
        }
        offer.checkUp = "A";
        offer.directed = "N";
        if(holder.donateDefer != null && holder.donateDefer.equals(DonateDefer.DEFER)){
            if(holder.defferedReasonId != null){
                offer.defferredReason = DeferredReason.findById(holder.defferedReasonId);
            }
            offer.deferDate = new Date();
        }
        if(holder.dateOfBirth != null){
            offer.donorAge = DateUtil.getYears(holder.dateOfBirth);
        }
        offer.phlebotomy = "Y";
        if(holder.userId != null){
            offer.user = User.findById(holder.userId);
        }
        offer.pulse = this.holder.pulse;
        //offer.offerTime = sdf.format(Long.valueOf(System.currentTimeMillis()));
        offer.save();
        for(int i = 0; i < getIncentives().size(); i++){
            OfferIncentiveContract contract = new OfferIncentiveContract();
            contract.incentive = Incentive.findById((getIncentives().get(i)));
            contract.offer = offer;
            contract.save();
        }
        DonationStats stats = new DonationStats();
        /*stats.victimOfSexualAbuse = holder.victimOfSexualAbuse;
        stats.sufferedFromNightSweats = holder.sufferedFromNightSweats;
        stats.contactWithPersonWithHepatitisB = holder.contactWithPersonWithHepatitisB;
        stats.sufferedFromSTD = holder.sufferedFromSTD;
        stats.trueForSexPartner = holder.trueForSexPartner;
        stats.exchangedMoneyForSex = holder.exchangedMoneyForSex;
        stats.sexWithSomeoneWithUnknownBackground = holder.sexWithSomeoneWithUnknownBackground;
        stats.injectedWithIllegalDrugs = holder.injectedWithIllegalDrugs;
        stats.beenTattooedOrPierced = holder.beenTattooedOrPierced;
        stats.accidentalExposureToBlood = holder.accidentalExposureToBlood;
        stats.contactWithPersonWithYellowJaundice = holder.contactWithPersonWithYellowJaundice;
        stats.beenTestedForHiv = holder.beenTestedForHiv;
        stats.hivTest = holder.hivTest;
        stats.receivedBloodTransfusion = holder.receivedBloodTransfusion;
        stats.beenIll = holder.beenIll;
        stats.injection = holder.injection;
        stats.takenAntibiotics = holder.takenAntibiotics;
        stats.beenToDentist = holder.beenToDentist;
        stats.chronicMedicalCondition = holder.chronicMedicalCondition;
        stats.diabetes = holder.diabetes;
        stats.cancer = holder.cancer;
        stats.lungDisease = holder.lungDisease;
        stats.rheumaticFever = holder.rheumaticFever;
        stats.dangerousOccupation = holder.dangerousOccupation;
        stats.mealOrSnack = holder.mealOrSnack;
        stats.beenToMalariaArea = holder.beenToMalariaArea;
        stats.refusedToDonate = holder.refusedToDonate;
        stats.feelingWellToday = holder.feelingWellToday;
        stats.reasonForTesting = holder.reasonForTesting;
        stats.weight = holder.weight;
        stats.copperSulphate = holder.copperSulphate;
        stats.packType = holder.packType;
        stats.hamocue = holder.hamocue;
        stats.bloodPressure = holder.bloodPressure;
        stats.entry = DateUtil.getStringFromDate(new Date());
        if(holder.pregnant != null){
            stats.pregnant = holder.pregnant;
        }
        if(holder.breastFeeding != null){
            stats.breastFeeding = holder.breastFeeding;
        }
        stats.person = item;
        stats.save();*/
        List<Donation> donations = new ArrayList<>();
        for(Donation m : Donation.findByDonor(item)){
            m.donationDate = DateUtil.getStringFromDate(m.date);
            donations.add(m);
        }
        item.donations = donations;
        List<Offer> offers = new ArrayList<>();
        for(Offer m : Offer.findByDonor(item)){
            m.offer = DateUtil.getStringFromDate(m.offerDate);
            if(m.deferDate != null){
                m.defer = DateUtil.getStringFromDate(m.deferDate);
            }
            m.incentives = Incentive.findByOffer(m);
            offers.add(m);
        }
        item.offers = offers;
        item.donationStats = DonationStats.findByDonor(item);
        item.specialNotes = SpecialNotes.findByDonor(item);
        item.genderValue = item.gender.getName();
        item.requestType = "POST_DONOR";
        String result = sendMessage(AppUtil.createGson().toJson(item));
        item.localId = result;
        item.save();
        for(Donation m : Donation.findByDonor(item)){
            m.delete();
        }

        for(Offer m : Offer.findByDonor(item)){
            for(OfferIncentiveContract o : OfferIncentiveContract.findByOffer(m)){
                o.delete();
            }
            m.delete();
        }
        for(DonorSpecialNotesContract m : DonorSpecialNotesContract.findByDonor(item)){
            m.delete();
        }
    }

    public void saveFinalStage(){
        progressDialog = ProgressDialog.show(this, "Please wait...", "Syncing with server", true);
        Thread mThread = new Thread(){
            @Override
            public void run(){
                Donor current = Donor.findById(id);
                List<DonationStats> list = DonationStats.findByDonor(current);
                DonationStats stats = list.get(0);
                stats.weight = holder.weight;
                stats.copperSulphate = holder.copperSulphate;
                stats.packType = holder.packType;
                stats.bloodPressure = holder.bloodPressure;
                stats.reasonForTesting = holder.reasonForTesting;
                stats.hamocue = holder.hamocue;
                stats.entry = DateUtil.getStringFromDate(new Date());
                if(holder.pregnant != null){
                    stats.pregnant = holder.pregnant;
                }
                if(holder.breastFeeding != null){
                    stats.breastFeeding = holder.breastFeeding;
                }
                stats.save();
                Donation d = null;
                if(current.donateDefer.equals(DonateDefer.DONATE)){
                    d = new Donation();
                    d.date = new Date();
                    d.donationType = current.donationType;
                    d.person = current;
                    d.timDonation = current.entryTime;
                    d.donationNumber = holder.donationNumber;
                    if(holder.dateOfBirth != null){
                        d.donorAge = DateUtil.getYears(holder.dateOfBirth);
                    }
                    d.city = CollectSite.findByActive().centre;
                    d.save();
                }
                Offer offer = new Offer();
                offer.offerDate = new Date();
                offer.person = current;
                offer.centre = CollectSite.findByActive().centre;
                offer.collectSite = CollectSite.findByActive();
                if(current.donationType != null){
                    offer.donationType = current.donationType;
                    offer.donationKind = "D";
                    offer.donationNumber = holder.donationNumber;
                }
                offer.checkUp = "A";
                offer.directed = "N";
                if(current.donateDefer != null && current.donateDefer.equals(DonateDefer.DEFER)){
                    if(current.defferedReasonId != null){
                        offer.defferredReason = DeferredReason.findById(holder.defferedReasonId);
                    }
                    offer.deferDate = new Date();
                }
                if(current.dateOfBirth != null){
                    offer.donorAge = DateUtil.getYears(current.dateOfBirth);
                }
                offer.phlebotomy = "Y";
                if(holder.userId != null){
                    offer.user = User.findById(holder.userId);
                }
                offer.pulse = holder.pulse;
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                offer.offerTime = sdf.format(Long.valueOf(System.currentTimeMillis()));
                offer.save();
                for(int i = 0; i < getIncentives().size(); i++){
                    OfferIncentiveContract contract = new OfferIncentiveContract();
                    contract.incentive = Incentive.findById((getIncentives().get(i)));
                    contract.offer = offer;
                    contract.save();
                }
                if(holder.userId != null){
                    current.bledBy = User.findById(holder.userId);
                }
                if(current.donateDefer != null && current.donateDefer.equals(DonateDefer.DEFER)){
                    if(holder.defferedReasonId != null){
                        current.deferredReason = DeferredReason.findById(holder.defferedReasonId);
                    }
                    if(holder.deferNotes != null){
                        current.deferNotes = holder.deferNotes;
                    }
                    current.deferPeriod = holder.deferPeriod;
                    current.deferDate = DateUtil.getStringFromDate(new Date());
                    current.accepted = "T";
                }else{
                    current.accepted = "A";
                }
                current.pushed = 3;
                current.save();
                current.genderValue = current.gender.getName();
                sendMessage(AppUtil.createGson().toJson(current));
                result = sendMessage(AppUtil.createGson().toJson(stats));
                if(d != null){
                    d.donationDate = DateUtil.getStringFromDate(d.date);
                }
                result = sendMessage(AppUtil.createGson().toJson(d));
                offer.offer = DateUtil.getStringFromDate(offer.offerDate);
                if(offer.deferDate != null){
                    offer.defer = DateUtil.getStringFromDate(offer.deferDate);
                }
                offer.incentives = Incentive.findByOffer(offer);
                result = sendMessage(AppUtil.createGson().toJson(offer));
                delete();
                sendRequestForTodayDonations();
                progressDialog.dismiss();
                AppUtil.createShortNotification(getApplicationContext(), "Donor successfully saved!");
                Intent intent = new Intent(getApplicationContext(), DonatedBloodActivity.class);
                startActivity(intent);
                finish();
            }
        };
        mThread.start();
    }

    @Override
    public void syncAppData() {
        if (AppUtil.isNetworkAvailable(context)) {
            progressDialog = ProgressDialog.show(this, "Please wait", "Syncing with Server...", true);
            progressDialog.setCancelable(false);
            Intent intent = new Intent(this, PushService.class);
            startService(intent);
        } else {
            AppUtil.createShortNotification(this, "No Internet, Check Connectivity!");
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (bundle != null) {
                int resultCode = bundle.getInt(PushService.RESULT);
                if (resultCode == RESULT_OK) {
                    createNotificationDataSync("Sync Success", "Application Data Updated");
                    AppUtil.createShortNotification(context, "Application Data Updated");
                } else {
                    createNotificationDataSync("Sync Fail", "Incomplete Application Data");
                    AppUtil.createShortNotification(context, "Incomplete Application Data");
                }
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(PushService.NOTIFICATION));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    private ArrayList<Long> getIncentives(){
        ArrayList<Long> a = new ArrayList<>();
        for(int i = 0; i < incentive.getCount(); i++){
            if(incentive.isItemChecked(i)){
                a.add(adapter.getItem(i).serverId);
            }else{
                a.remove(adapter.getItem(i));
            }
        }
        return a;
    }
}
