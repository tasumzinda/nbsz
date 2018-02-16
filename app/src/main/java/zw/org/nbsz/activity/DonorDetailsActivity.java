package zw.org.nbsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.w3c.dom.Text;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.Donor;
import zw.org.nbsz.business.domain.util.Gender;
import zw.org.nbsz.business.util.AppUtil;
import zw.org.nbsz.business.util.DateUtil;
import zw.org.nbsz.business.util.StringUtils;

public class DonorDetailsActivity extends BaseActivity implements View.OnClickListener {

    private TextView firstName;
    private TextView surname;
    private TextView idNumber;
    private TextView gender;
    private TextView dateOfBirth;
    private TextView donorNum;
    private TextView lastDonation;
    private Button next;
    private TextView lastBloodDonationLabel;
    private TextView reasonLabel;
    private TextView reason;
    private TextView periodLabel;
    private TextView period;
    private TextView notesLabel;
    private TextView notes;
    private TextView bloodGroup;
    private TextView numDonations;
    private Donor donor;
    private String donorNumber;
    private String localId;
    private Donor item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_details);
        firstName = (TextView) findViewById(R.id.first_name);
        surname = (TextView) findViewById(R.id.surname);
        idNumber = (TextView) findViewById(R.id.id_number);
        gender = (TextView) findViewById(R.id.gender);
        dateOfBirth = (TextView) findViewById(R.id.date_of_birth);
        donorNum = (TextView) findViewById(R.id.donor_number);
        lastDonation = (TextView) findViewById(R.id.last_donation);
        bloodGroup = (TextView) findViewById(R.id.blood_group);
        numDonations = (TextView) findViewById(R.id.num_donations);
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        lastBloodDonationLabel = (TextView) findViewById(R.id.last_donation_label);
        reasonLabel = (TextView) findViewById(R.id.reason_label);
        reason = (TextView) findViewById(R.id.reason);
        periodLabel = (TextView) findViewById(R.id.period_label);
        period = (TextView) findViewById(R.id.defer_period);
        notesLabel = (TextView) findViewById(R.id.notes_label);
        notes = (TextView) findViewById(R.id.defer_notes);
        Intent intent = getIntent();
        donorNumber = intent.getStringExtra("donorNumber");
        localId = intent.getStringExtra("localId");
        if(donorNumber != null && ! donorNumber.isEmpty()){
            donor = Donor.findByDonorNumber(donorNumber);
        }else{
            donor = Donor.findByLocalId(localId);
        }

        firstName.setText(donor.firstName);
        surname.setText(donor.surname);
        idNumber.setText(donor.idNumber);
        donorNum.setText(donor.donorNumber);
        bloodGroup.setText(donor.bloodGroup != null ? donor.bloodGroup : "");
        numDonations.setText(donor.numberOfDonations != null ? String.valueOf(donor.numberOfDonations) : "");
        gender.setText(donor.gender != null ? donor.gender.getName() : "");
        dateOfBirth.setText(DateUtil.getStringFromDate(donor.dateOfBirth));
        int waitingPeriod = 0;
        if(donor.gender != null && donor.gender.equals(Gender.M)){
            waitingPeriod = 12;
        }else if(donor.gender != null && donor.gender.equals(Gender.F)){
            waitingPeriod = 16;
        }
        if(donor.deferDate != null){
            if(donor.entryDate != null){
                if(donor.entryDate.after(DateUtil.getDateFromString(donor.deferDate)) || donor.entryDate.equals(DateUtil.getDateFromString(donor.deferDate))){
                    int weeksToDate = DateUtil.getWeeks(donor.entryDate);
                    if(waitingPeriod < weeksToDate){
                        lastDonation.setText(DateUtil.formatDate(donor.entryDate) + " " + "ELIGIBLE");
                    }else{
                        lastDonation.setText(DateUtil.formatDate(donor.entryDate) + " " + "INELIGIBLE");
                        next.setText("Back");
                        AppUtil.createShortNotification(this, "Donor is not eligible. The donation process can not continue");
                    }
                }else {
                    int monthsToDate = DateUtil.getMonths(DateUtil.getDateFromString(donor.deferDate));
                    lastBloodDonationLabel.setText("Deferred Date");
                    if(donor.deferPeriod != null && donor.deferPeriod < monthsToDate){
                        lastDonation.setText(donor.deferDate + " " + "ELIGIBLE");
                    }else{
                        lastDonation.setText(donor.deferDate + " " + "INELIGIBLE");
                        next.setText("Back");
                        reason.setVisibility(View.VISIBLE);
                        reasonLabel.setVisibility(View.VISIBLE);
                        period.setVisibility(View.VISIBLE);
                        periodLabel.setVisibility(View.VISIBLE);
                        notesLabel.setVisibility(View.VISIBLE);
                        notes.setVisibility(View.VISIBLE);
                        reason.setText(donor.deferredReason != null ? donor.deferredReason.name : "");
                        period.setText(donor.deferPeriod != null ? donor.deferPeriod + " months" : "");
                        notes.setText(donor.deferNotes != null ? donor.deferNotes : "");
                        AppUtil.createShortNotification(this, "Donor is not eligible. The donation process can not continue");
                    }
                }
            }

        }else if(donor.entryDate != null){
            int weeksToDate = DateUtil.getWeeks(donor.entryDate);
            if(waitingPeriod < weeksToDate){
                lastDonation.setText(DateUtil.formatDate(donor.entryDate) + " " + "ELIGIBLE");
            }else{
                lastDonation.setText(DateUtil.formatDate(donor.entryDate) + " " + "INELIGIBLE");
                next.setText("Back");
                AppUtil.createShortNotification(this, "Donor is not eligible. The donation process can not continue");
            }
        }else {
            lastDonation.setText("UNKNOWN");
        }


        setSupportActionBar(createToolBar("NBSZ - Donor Profile"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        //Intent intent = new Intent(this, ConsentToUpdateDetailsActivity.class);
        if(next.getText().equals("Back")){
            Intent intent = new Intent(this, DonatedBloodActivity.class);
            intent.putExtra("donorNumber", donorNumber);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("donorNumber", donorNumber);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, SearchDonorActivity.class);
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

}
