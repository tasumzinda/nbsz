package zw.org.nbsz.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import org.w3c.dom.Text;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.Donor;
import zw.org.nbsz.business.util.DateUtil;
import zw.org.nbsz.business.util.Log;

import java.util.ArrayList;

public class DonorReviewActivity extends BaseActivity {

    private TextView surname;
    private TextView firstName;
    private TextView idNumber;
    private TextView donorNumber;
    private TextView dateOfBirth;
    private TextView sex;
    private TextView occupation;
    private TextView maritalStatus;
    private TextView lastDonation;
    private TextView residentialAddress;
    private Long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_review);
        setSupportActionBar(createToolBar("Donors"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        surname = (TextView) findViewById(R.id.surname);
        firstName = (TextView) findViewById(R.id.first_name);
        idNumber = (TextView) findViewById(R.id.id_number);
        donorNumber = (TextView) findViewById(R.id.donor_number);
        dateOfBirth = (TextView) findViewById(R.id.date_of_birth);
        sex = (TextView) findViewById(R.id.gender);
        occupation = (TextView) findViewById(R.id.occupation);
        maritalStatus = (TextView) findViewById(R.id.marital_status);
        lastDonation = (TextView) findViewById(R.id.last_donation);
        residentialAddress = (TextView) findViewById(R.id.residential_address);
        Intent intent = getIntent();
        id = intent.getLongExtra("id", 1L);
        Donor item = Donor.findById(id);
        surname.setText(item.surname);
        firstName.setText(item.firstName);
        idNumber.setText(item.idNumber);
        donorNumber.setText(item.donorNumber);
        dateOfBirth.setText(item.dob);
        sex.setText(item.gender != null ? item.gender.getName() : "");
        occupation.setText(item.profession != null ? item.profession.name : "");
        maritalStatus.setText(item.maritalStatus != null ? item.maritalStatus.name : "");
        lastDonation.setText(item.entry);
        residentialAddress.setText(item.residentialAddress);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, DonorListActivity.class);
        //intent.putExtra("donorNumber", donorNumber);
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
