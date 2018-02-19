package zw.org.nbsz.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.squareup.okhttp.HttpUrl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.*;
import zw.org.nbsz.business.domain.util.Gender;
import zw.org.nbsz.business.util.AppUtil;
import zw.org.nbsz.business.util.DateUtil;
import zw.org.nbsz.business.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.*;

public class SearchDonorActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.donor_number)
    EditText donorNumber;
    @BindView(R.id.btn_save)
    Button next;
    @BindView(R.id.id_number)
    EditText idNumber;
    @BindView(R.id.first_name)
    EditText firstName;
    @BindView(R.id.surname)
    EditText surname;
    @BindView(R.id.dob)
    EditText dateOfBirth;
    private DatePickerDialog dialog;
    @BindView(R.id.acknowledge)
    RadioGroup acknowledge;
    private RadioButton selected;
    private String name;
    private String lastName;
    private String dob;
    String outcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_donor);
        ButterKnife.bind(this);
        next.setOnClickListener(this);
        Intent intent = getIntent();
        name = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("surname");
        dob = intent.getStringExtra("dob");
        setSupportActionBar(createToolBar("NBSZ - Search For Donor"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        acknowledge.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedItem = acknowledge.getCheckedRadioButtonId();
                selected = (RadioButton) findViewById(selectedItem);
                if(selected != null){
                    if(selected.getText().equals("Donor Number")){
                        idNumber.setVisibility(View.GONE);
                        firstName.setVisibility(View.GONE);
                        surname.setVisibility(View.GONE);
                        dateOfBirth.setVisibility(View.GONE);
                        donorNumber.setVisibility(View.VISIBLE);
                    }else if(selected.getText().equals("ID Number")){
                        firstName.setVisibility(View.GONE);
                        surname.setVisibility(View.GONE);
                        dateOfBirth.setVisibility(View.GONE);
                        donorNumber.setVisibility(View.GONE);
                        idNumber.setVisibility(View.VISIBLE);
                    }else if(selected.getText().equals("Name, Surname And Date Of Birth")){
                        idNumber.setVisibility(View.GONE);
                        donorNumber.setVisibility(View.GONE);
                        firstName.setVisibility(View.VISIBLE);
                        surname.setVisibility(View.VISIBLE);
                        dateOfBirth.setVisibility(View.VISIBLE);
                        firstName.setText(name != null ? name : "");
                        surname.setText(lastName != null ? lastName : "");
                        if(dob != null){
                            Date date = DateUtil.getFromString(dob);
                            String data = DateUtil.formatDateRest(date);
                            dateOfBirth.setText(data);
                        }

                    }
                }
            }
        });
        dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), dateOfBirth);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        dateOfBirth.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == dateOfBirth.getId()){
            dialog.show();
        }
        if(view.getId() == next.getId()){
            Donor item;
            if(validate()){
                if(selected.getText().equals("Donor Number")){
                    item = Donor.findByDonorNumber(donorNumber.getText().toString());
                    if(item != null){
                        if(item.isNew == 1){
                            AppUtil.createShortNotification(this, "Please send this donor to the server first before searching for him/her");
                        }else {
                            Intent intent = new Intent(SearchDonorActivity.this, DonorDetailsActivity.class);
                            intent.putExtra("donorNumber", item.donorNumber);
                            startActivity(intent);
                            finish();
                        }

                    }else if(item == null){
                        outcome = sendDonorNumberRequest(donorNumber, item);
                        if(outcome == null || outcome.equals("Not found")){
                            sendDonorNumberRequestRemote(donorNumber);
                        }
                    }
                }
                if(selected.getText().equals("ID Number")){
                    item = Donor.findByNationalId(idNumber.getText().toString());
                    if(item != null){
                        if(item.isNew == 1){
                            AppUtil.createShortNotification(this, "Please send this donor to the server first before searching for him/her");
                        }else {
                            Intent intent = new Intent(SearchDonorActivity.this, DonorDetailsActivity.class);
                            intent.putExtra("donorNumber", item.donorNumber);
                            startActivity(intent);
                            finish();
                        }
                    }else if(item == null){
                        outcome = sendIdNumberRequest(idNumber, item);
                        if(outcome == null || outcome.equals("Not found")){
                            sendIdNumberRequestRemote(idNumber);
                        }
                    }
                }
                if(selected.getText().equals("Name, Surname And Date Of Birth")){
                    String firstName1 = firstName.getText().toString().trim();
                    String surname1 = surname.getText().toString().trim();
                    Date date = DateUtil.getDateFromString(dateOfBirth.getText().toString());
                    String dob = DateUtil.formatDate(date);
                    List<Donor> items = new ArrayList<>();
                    if(firstName1.isEmpty()){
                        items = Donor.findByLastNameAndDateOfBirth(surname1.toUpperCase(), dob);
                    }else{
                        items = Donor.findByFirstNameAndLastNameAndDateOfBirth(firstName1.toUpperCase(), surname1.toUpperCase(), dob);
                    }
                    if(items.size() > 0){
                        Log.d("Test", "Inside");
                        Intent intent = new Intent(context, SearchDonorListActivity.class);
                        intent.putExtra("firstName",firstName1.toUpperCase());
                        intent.putExtra("surname", surname1.toUpperCase());
                        intent.putExtra("dob", dob);
                        startActivity(intent);
                        finish();
                    }else if(items.size() == 0){
                        outcome = sendNameDobRequest(firstName1, surname1, dob, items);
                        if(outcome == null || outcome.equals("Not found")){
                            fetchRemote(this, firstName1, surname1, dob);
                        }
                    }
                }
            }

        }


    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, DonatedBloodActivity.class);
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
        if(selected == null){
            AppUtil.createShortNotification(this, "Please select a search filter first");
            isValid = false;
        }
        if(selected != null){
            if(selected.getText().equals("Name, Surname And Date Of Birth")){
                if(surname.getText().toString().isEmpty()){
                    surname.setError(getResources().getString(R.string.required_field_error));
                    isValid = false;
                }else{
                    surname.setError(null);
                }
                String date = dateOfBirth.getText().toString();
                if(date.isEmpty()){
                    dateOfBirth.setError(getResources().getString(R.string.required_field_error));
                    isValid = false;
                }else{
                    dateOfBirth.setError(null);
                }
                if( ! date.isEmpty()){
                    if( ! checkDateFormat(date)){
                        dateOfBirth.setError(getResources().getString(R.string.date_format_error));
                        isValid = false;
                    }else{
                        dateOfBirth.setError(null);
                    }
                }
            }

            if(selected.getText().equals("ID Number")){
                if(idNumber.getText().toString().isEmpty()){
                    idNumber.setError(getResources().getString(R.string.required_field_error));
                    isValid = false;
                }else{
                    idNumber.setError(null);
                }
            }
            if(selected.getText().equals("Donor Number")){
                if(donorNumber.getText().toString().isEmpty()){
                    donorNumber.setError(getResources().getString(R.string.required_field_error));
                    isValid = false;
                }else{
                    donorNumber.setError(null);
                }
            }
        }

        return isValid;
    }

    public void fetchRemote(final Context context, final String name, final String surname, final String dob) {
        String url = "";
        if(name.isEmpty()){
            url = AppUtil.getBaseUrl(context) + "form/get-by-surname-and-dob?&surname="+surname.trim()+"&dob="+dob;
        }else{
            url = AppUtil.getBaseUrl(context) + "form/get-by-details?firstName="+name.trim()+"&surname="+surname.trim()+"&dob="+dob;
        }

        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int count = response.length();
                        int saved = 0;
                        if(response.length() > 0){
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject object = response.getJSONObject(i);
                                    Donor item = new Donor();
                                    item.firstName = object.getString("firstName").toUpperCase().trim();
                                    item.surname = object.getString("surname").toUpperCase().trim();
                                    item.idNumber = object.getString("idNumber");
                                    if( ! object.isNull("numberOfDonations")){
                                        item.numberOfDonations = object.getInt("numberOfDonations");
                                    }
                                    if(object.getString("gender").equals("M") || object.getString("gender").equals("F")){
                                        item.gender = Gender.valueOf(object.getString("gender"));
                                    }
                                    if( ! object.isNull("dob")){
                                        item.dateOfBirth = DateUtil.getFromString(object.getString("dob"));
                                        item.dob = DateUtil.formatDate(item.dateOfBirth);
                                    }

                                    if( ! object.isNull("deferDate")){
                                        item.deferDate = DateUtil.getStringFromDate(DateUtil.getDateFromString(object.getString("deferDate")));
                                    }
                                    if( ! object.isNull("entry")){
                                        item.entryDate = DateUtil.getDateFromString(object.getString("entry"));
                                        item.entry = DateUtil.getStringFromDate(item.entryDate);
                                    }
                                    if( ! object.isNull("deferNotes")){
                                        item.deferNotes = object.getString("deferNotes");
                                    }
                                    if( ! object.isNull("deferPeriod")){
                                        item.deferPeriod = object.getInt("deferPeriod");
                                    }

                                    if( ! object.isNull("profession")){
                                        JSONObject profession = object.getJSONObject("profession");
                                        item.profession = Profession.findById(profession.getLong("id"));
                                    }
                                    if( ! object.isNull("maritalStatus")){
                                        JSONObject maritalStatus = object.getJSONObject("maritalStatus");
                                        item.maritalStatus = MaritalStatus.findById(maritalStatus.getLong("id"));
                                    }
                                    if(! object.isNull("city")){
                                        JSONObject city = object.getJSONObject("city");
                                        item.city = Centre.findById(city.getLong("id"));
                                    }
                                    item.residentialAddress = object.getString("residentialAddress");
                                    item.homeTelephone = object.getString("homeTelephone");
                                    item.workTelephone = object.getString("workTelephone");
                                    item.cellphoneNumber = object.getString("cellphoneNumber");
                                    item.email = object.getString("email");
                                    if( ! object.isNull("counsellor")){
                                        JSONObject counsellor = object.getJSONObject("counsellor");
                                        Counsellor c = new Counsellor();
                                        c.name = counsellor.getString("name");
                                        c.address = counsellor.getString("address");
                                        c.phoneNumber = counsellor.getString("phoneNumber");
                                        c.code = counsellor.getString("code");
                                        c.serverId = counsellor.getLong("id");
                                        Counsellor duplicate = Counsellor.findById(c.serverId);
                                        if(duplicate == null){
                                            c.save();
                                        }
                                        item.counsellor = c;
                                    }
                                    if( ! object.isNull("deferredReason")){
                                        JSONObject deferredReason = object.getJSONObject("deferredReason");
                                        item.deferredReason = DeferredReason.findById(deferredReason.getLong("id"));
                                    }
                                    if( ! object.isNull("collectSite")){
                                        JSONObject collectSite = object.getJSONObject("collectSite");
                                        item.collectSite = CollectSite.findById(collectSite.getLong("id"));
                                    }
                                    if( ! object.isNull("donationType")){
                                        JSONObject donationType = object.getJSONObject("donationType");
                                        item.donationType = DonationType.findById(donationType.getLong("id"));
                                    }
                                    item.server_id = object.getLong("id");
                                    item.donorNumber = object.getString("donorNumber");
                                    Donor duplicate = Donor.findByServerId(item.server_id);
                                    if(duplicate == null){
                                        item.save();
                                    }
                                    saved++;
                                    if(saved == count){
                                        Intent intent = new Intent(context, SearchDonorListActivity.class);
                                        intent.putExtra("firstName",name.trim().toUpperCase());
                                        intent.putExtra("surname", surname.trim().toUpperCase());
                                        intent.putExtra("dob", dob);
                                        startActivity(intent);
                                    }
                                } catch (JSONException exc) {
                                }
                            }


                            finish();
                        }else{
                            AppUtil.createShortNotification(context, "A donor with those details does not exist");
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleVolleyError(error);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(
                        "Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", AppUtil.getUsername(context), AppUtil.getPassword(context)).getBytes(), Base64.DEFAULT)));
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        //request.setRetryPolicy(new DefaultRetryPolicy(5000, 3, 2.0F));
        AppUtil.getInstance(context).addToRequestQueue(request);
    }

}
