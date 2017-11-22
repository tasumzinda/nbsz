package zw.org.nbsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.*;
import zw.org.nbsz.business.domain.util.YesNo;
import zw.org.nbsz.business.util.AppUtil;
import zw.org.nbsz.business.util.Log;

public class DonorAddContactDetailsActivity extends BaseActivity implements View.OnClickListener{

    private ListView occupation;
    private ListView maritalStatus;
    private EditText residentialAddress;
    private ListView city;
    private EditText homeTelephone;
    private EditText workTelephone;
    private EditText cellphoneNumber;
    private EditText email;
    private Button next;
    Donor holder;
    private Counsellor counsellor;
    private String donorNumber;
    private Donor item;
    private ArrayAdapter<Profession> occupationArrayAdapter;
    private ArrayAdapter<MaritalStatus> maritalStatusArrayAdapter;
    ArrayAdapter<Centre> cityArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_add_contact_details);
        occupation = (ListView) findViewById(R.id.occupation);
        maritalStatus = (ListView) findViewById(R.id.marital_status);
        residentialAddress = (EditText) findViewById(R.id.residential_address);
        city = (ListView) findViewById(R.id.city);
        homeTelephone = (EditText) findViewById(R.id.home_telephone);
        workTelephone = (EditText) findViewById(R.id.work_telephone);
        cellphoneNumber = (EditText) findViewById(R.id.cellphone_number);
        email = (EditText) findViewById(R.id.email);
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        Intent intent = getIntent();
        holder = (zw.org.nbsz.business.domain.Donor) intent.getSerializableExtra("holder");
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNumber = intent.getStringExtra("donorNumber");
        occupationArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, Profession.getAll());
        occupation.setAdapter(occupationArrayAdapter);
        occupationArrayAdapter.notifyDataSetChanged();
        occupation.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        occupation.setItemsCanFocus(false);
        maritalStatusArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, MaritalStatus.getAll());
        maritalStatus.setAdapter(maritalStatusArrayAdapter);
        maritalStatusArrayAdapter.notifyDataSetChanged();
        maritalStatus.setItemsCanFocus(false);
        maritalStatus.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        cityArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, Centre.getAll());
        city.setAdapter(cityArrayAdapter);
        cityArrayAdapter.notifyDataSetChanged();
        city.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        city.setItemsCanFocus(false);
        setSupportActionBar(createToolBar("NBSZ - Add Contact Details"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(donorNumber != null && ! donorNumber.isEmpty()){
            item = Donor.findByDonorNumber(donorNumber);
            //Log.d("Profession", item.profession.name);
            int count = occupationArrayAdapter.getCount();
            Profession p = item.profession;
            if(p != null){
                for(int k = 0; k < count; k++){
                    Profession current = occupationArrayAdapter.getItem(k);
                    if(current.name.equals(p.name)){
                        occupation.setItemChecked(k, true);
                    }
                }
            }

            MaritalStatus m = item.maritalStatus;
            count = maritalStatusArrayAdapter.getCount();
            if(m != null){
                for(int k = 0; k < count; k++){
                    MaritalStatus current = maritalStatusArrayAdapter.getItem(k);
                    if(current.name.equals(m.name)){
                        maritalStatus.setItemChecked(k, true);
                    }
                }
            }

            Centre c = item.city;
            count = cityArrayAdapter.getCount();
            if(c != null){
                for(int k = 0; k < count; k++){
                    Centre current = cityArrayAdapter.getItem(k);
                    if(current.name.equals(c.name)){
                        city.setItemChecked(k, true);
                    }
                }
            }

            residentialAddress.setText(item.residentialAddress);
            workTelephone.setText(item.workTelephone);
            cellphoneNumber.setText(item.cellphoneNumber);
            homeTelephone.setText(item.homeTelephone);
            email.setText(item.email);
        }else if(holder.residentialAddress != null){
            int i = 0;
            if(holder.professionId != null){
                Profession p = Profession.findById(holder.professionId);
                int count = occupationArrayAdapter.getCount();
                for(int k = 0; k < count; k++){
                    Profession current = occupationArrayAdapter.getItem(k);
                    if(current.name.equals(p.name)){
                        occupation.setItemChecked(k, true);
                    }
                }
            }
            if(holder.maritalStatusId != null){
                MaritalStatus m = MaritalStatus.findById(holder.maritalStatusId);
                int count = maritalStatusArrayAdapter.getCount();
                for(int k = 0; k < count; k++){
                    MaritalStatus current = maritalStatusArrayAdapter.getItem(k);
                    if(current.name.equals(m.name)){
                        maritalStatus.setItemChecked(k, true);
                    }
                }
            }
            if(holder.cityId != null){
                Centre d = Centre.findById(holder.cityId);
                int count = cityArrayAdapter.getCount();
                for(int k = 0; k < count; k++){
                    Centre current = cityArrayAdapter.getItem(k);
                    if(current.name.equals(d.name)){
                        city.setItemChecked(k, true);
                    }
                }
            }
            residentialAddress.setText(holder.residentialAddress);
            workTelephone.setText(holder.workTelephone);
            cellphoneNumber.setText(holder.cellphoneNumber);
            homeTelephone.setText(holder.homeTelephone);
            email.setText(holder.email);
        }else{
            item = new Donor();
        }
    }

    @Override
    public void onClick(View view) {
        if(validate()){

            if(holder == null){
                holder = Donor.findByDonorNumber(donorNumber);
            }
            holder.professionId = getProfession().server_id;
            holder.maritalStatusId = getMaritalStatus().server_id;
            holder.cityId = getCentre().server_id;
            holder.residentialAddress = residentialAddress.getText().toString();
            holder.homeTelephone = homeTelephone.getText().toString();
            holder.workTelephone = workTelephone.getText().toString();
            holder.cellphoneNumber = cellphoneNumber.getText().toString();
            holder.email = email.getText().toString();
            if(getProfession().name.startsWith("STUDENT")){
                Intent intent = new Intent(this, HealthAssessmentActivity.class);
                intent.putExtra("holder", holder);
                intent.putExtra("counsellor", counsellor);
                intent.putExtra("donorNumber", donorNumber);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(this, CounsellorDetailsActivity.class);
                intent.putExtra("holder", holder);
                intent.putExtra("counsellor", counsellor);
                intent.putExtra("donorNumber", donorNumber);
                startActivity(intent);
                finish();
            }

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
        if(holder == null){
            holder = Donor.findByDonorNumber(donorNumber);
        }
        if(getProfession() != null){
            holder.professionId = getProfession().server_id;
        }
        if(getMaritalStatus() != null){
            holder.maritalStatusId = getMaritalStatus().server_id;
        }
        if(getCentre() != null){
            holder.cityId = getCentre().server_id;
        }
        holder.residentialAddress = residentialAddress.getText().toString();
        holder.homeTelephone = homeTelephone.getText().toString();
        holder.workTelephone = workTelephone.getText().toString();
        holder.cellphoneNumber = cellphoneNumber.getText().toString();
        holder.email = email.getText().toString();
        if(holder.consentToUpdate != null && holder.consentToUpdate.equals(YesNo.YES)){
            Intent intent = new Intent(this, ConsentToUpdateDetailsActivity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(this, DonorProfileActivity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            startActivity(intent);
            finish();
        }

    }

    public boolean validate(){
        boolean isValid = true;
        if(residentialAddress.getText().toString().isEmpty()){
            residentialAddress.setError(getResources().getString(R.string.required_field_error));
            isValid = false;
        }else{
            residentialAddress.setError(null);
        }
        if(getCentre() == null){
            AppUtil.createShortNotification(this, "Sorry, please select a city");
            isValid = false;
        }else if(getMaritalStatus() == null){
            AppUtil.createShortNotification(this, "Sorry, please select marital status");
            isValid = false;
        }else if(getProfession() == null){
            AppUtil.createShortNotification(this, "Sorry, please select an occupation");
            isValid = false;
        }
        return isValid;
    }

    private Profession getProfession(){
        Profession b = null;
        for(int i = 0; i < occupation.getCount(); i++){
            if(occupation.isItemChecked(i)){
                b = occupationArrayAdapter.getItem(i);
            }
        }
        return b;
    }

    private MaritalStatus getMaritalStatus(){
        MaritalStatus b = null;
        for(int i = 0; i < maritalStatus.getCount(); i++){
            if(maritalStatus.isItemChecked(i)){
                b = maritalStatusArrayAdapter.getItem(i);
            }
        }
        return b;
    }

    private Centre getCentre(){
        Centre c = null;
        for(int i = 0; i < city.getCount(); i++){
            if(city.isItemChecked(i)){
                c = cityArrayAdapter.getItem(i);
            }
        }
        return c;
    }
}
