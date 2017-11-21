package zw.org.nbsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.Counsellor;
import zw.org.nbsz.business.domain.DonationType;
import zw.org.nbsz.business.domain.Donor;
import zw.org.nbsz.business.domain.util.DonateDefer;
import zw.org.nbsz.business.domain.util.PassFail;
import zw.org.nbsz.business.domain.util.YesNo;
import zw.org.nbsz.business.util.AppUtil;
import zw.org.nbsz.business.util.Log;

public class NurseStep1Activity extends BaseActivity implements View.OnClickListener {

    private ListView copperSulphate;
    @BindView(R.id.donor_number)
    EditText donorNumber;
    @BindView(R.id.weight)
    EditText weight;
    @BindView(R.id.blood_group)
    EditText bloodGroup;
    @BindView(R.id.btn_save)
    Button next;
    @BindView(R.id.donation_type)
    Spinner donationType;
    private Donor holder;
    private Counsellor counsellor;
    private ArrayAdapter<PassFail> passFailArrayAdapter;
    private ArrayAdapter<DonationType> donationTypeArrayAdapter;
    private String donorNum;
    private Donor item;
    @BindView(R.id.blood_pressure_top)
    EditText bloodPressureTop;
    @BindView(R.id.blood_pressure_bottom)
    EditText bloodPressureBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_step1);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        holder = (Donor) intent.getSerializableExtra("holder");
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNum = intent.getStringExtra("donorNumber");
        copperSulphate = (ListView) findViewById(R.id.copper_sulphate);
        bloodPressureTop = (EditText) findViewById(R.id.blood_pressure_top);
        bloodPressureBottom = (EditText) findViewById(R.id.blood_pressure_bottom);
        donorNumber = (EditText) findViewById(R.id.donor_number);
        donorNumber.setOnClickListener(this);
        weight = (EditText) findViewById(R.id.weight);
        donationType = (Spinner) findViewById(R.id.donation_type);
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        passFailArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, PassFail.values());
        copperSulphate.setAdapter(passFailArrayAdapter);
        copperSulphate.setItemsCanFocus(false);
        copperSulphate.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        passFailArrayAdapter.notifyDataSetChanged();
        donationTypeArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, DonationType.getAll());
        donationTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        donationType.setAdapter(donationTypeArrayAdapter);
        donationTypeArrayAdapter.notifyDataSetChanged();
        if(donorNum != null && ! donorNum.isEmpty() && holder.weight != null){
            item = Donor.findByDonorNumber(donorNum);
            if(holder.donationNumber != null){
                donorNumber.setText(String.valueOf(holder.donationNumber));
            }
            weight.setText(String.valueOf(holder.weight));
            String pressure = holder.bloodPressure;
            String[] values = pressure.split("/");
            bloodPressureTop.setText(values[0]);
            bloodPressureBottom.setText(values[1]);
            bloodGroup.setText(item.bloodGroup);
            PassFail item =  holder.copperSulphate;
            int count = passFailArrayAdapter.getCount();
            for(int i = 0; i < count; i++){
                PassFail current = passFailArrayAdapter.getItem(i);
                if(current.equals(item)){
                    copperSulphate.setItemChecked(i, true);
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
        }else if(holder.weight != null){
            if(holder.donationNumber != null){
                donorNumber.setText(String.valueOf(holder.donationNumber));
            }
            weight.setText(String.valueOf(holder.weight));
            String pressure = holder.bloodPressure;
            String[] values = pressure.split("/");
            bloodPressureTop.setText(values[0]);
            bloodPressureBottom.setText(values[1]);
            bloodGroup.setText(holder.bloodGroup);
            PassFail item =  holder.copperSulphate;
            int count = passFailArrayAdapter.getCount();
            for(int i = 0; i < count; i++){
                PassFail current = passFailArrayAdapter.getItem(i);
                if(current.equals(item)){
                    copperSulphate.setItemChecked(i, true);
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
        setSupportActionBar(createToolBar("NBSZ - FOR OFFICIAL USE ONLY"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == donorNumber.getId()){
            IntentIntegrator scanner = new IntentIntegrator(this);
            scanner.initiateScan();
        }
        if(view.getId() == next.getId()){
            if(validate()){
                if( ! donorNumber.getText().toString().isEmpty()){
                    holder.donationNumber = donorNumber.getText().toString();
                }
                holder.weight = Double.parseDouble(weight.getText().toString());
                String pressure = bloodPressureTop.getText().toString() + "/" + bloodPressureBottom.getText().toString();
                holder.bloodPressure = pressure;
                holder.copperSulphate = getCopperSulphate();
                holder.donationTypeId = ((DonationType) donationType.getSelectedItem()).serverId;
                holder.bloodGroup = bloodGroup.getText().toString();
                Intent intent = new Intent(this, NurseStep2Activity.class);
                intent.putExtra("holder", holder);
                intent.putExtra("counsellor", counsellor);
                intent.putExtra("donorNumber", donorNum);
                startActivity(intent);
                finish();

            }
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(scanningResult != null){
            String scanContent = scanningResult.getContents();
            donorNumber.setText(scanContent);
        }else{
            AppUtil.createShortNotification(this, "No data retrieved");
        }
    }


    @Override
    public void onBackPressed(){
        if( ! donorNumber.getText().toString().isEmpty()){
            holder.donationNumber = donorNumber.getText().toString();
        }
        if(! weight.getText().toString().isEmpty()){
            holder.weight = Double.parseDouble(weight.getText().toString());
        }
        if(bloodPressureTop != null && bloodPressureBottom != null){
            String pressure = bloodPressureBottom.getText().toString() + "/" + bloodPressureTop.getText().toString();
            holder.bloodPressure = pressure;
        }
        holder.bloodGroup = bloodGroup.getText().toString();
        holder.copperSulphate = getCopperSulphate();
        holder.donationTypeId = ((DonationType) donationType.getSelectedItem()).serverId;
        Intent intent = new Intent(this, DeclarationFinalActivity.class);
        intent.putExtra("holder", holder);
        intent.putExtra("counsellor", counsellor);
        intent.putExtra("donorNumber", donorNum);
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



    public boolean validate(){
        boolean isValid = true;
        if(bloodPressureBottom.getText().toString().isEmpty()){
            bloodPressureBottom.setError(getResources().getString(R.string.required_field_error));
            isValid = false;
        }else{
            bloodPressureBottom.setError(null);
        }
        if(bloodPressureTop.getText().toString().isEmpty()){
            bloodPressureTop.setError(getResources().getString(R.string.required_field_error));
            isValid = false;
        }else{
            bloodPressureTop.setError(null);
        }
        if(weight.getText().toString().isEmpty()){
            weight.setError(getResources().getString(R.string.required_field_error));
            isValid = false;
        }else{
            weight.setError(null);
        }

        if(copperSulphate.getCheckedItemCount() == 0){
            AppUtil.createShortNotification(this, "Sorry, this response is required");
            isValid = false;
        }
        if(bloodGroup.getText().toString().isEmpty()){
            bloodGroup.setError(getResources().getString(R.string.required_field_error));
            isValid = false;
        }else{
            bloodGroup.setError(null);
        }
        return isValid;
    }

    private PassFail getCopperSulphate(){
        PassFail a = null;
        for(int i = 0; i < copperSulphate.getCount(); i++){
            if(copperSulphate.isItemChecked(i)){
                a = passFailArrayAdapter.getItem(i);
            }
        }
        return a;
    }
}
