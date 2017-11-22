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
import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.*;
import zw.org.nbsz.business.domain.util.Gender;
import zw.org.nbsz.business.util.AppUtil;
import zw.org.nbsz.business.util.DateUtil;
import zw.org.nbsz.business.util.Log;
import java.util.*;

public class DonorProfileActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.first_name)
    EditText firstName;
    @BindView(R.id.surname)
    EditText surname;
    @BindView(R.id.id_number)
    EditText idNumber;
    private ListView gender;
    @BindView(R.id.date_of_birth)
    EditText dateOfBirth;
    @BindView(R.id.btn_save)
    Button next;
    private DatePickerDialog dialog;
    private EditText [] fields;
    private Donor holder;
    private Counsellor counsellor;
    private ArrayAdapter<Gender> genderArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_profile);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        holder = (Donor) intent.getSerializableExtra("holder");
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        /*firstName = (EditText) findViewById(R.id.first_name);
        surname = (EditText) findViewById(R.id.surname);
        idNumber = (EditText) findViewById(R.id.id_number);
        dateOfBirth = (EditText) findViewById(R.id.date_of_birth);
        next = (Button) findViewById(R.id.btn_save);*/
        gender = (ListView) findViewById(R.id.gender);
        dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), dateOfBirth);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        next.setOnClickListener(this);
        dateOfBirth.setOnClickListener(this);
        genderArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, Gender.values());
        gender.setAdapter(genderArrayAdapter);
        gender.setItemsCanFocus(false);
        gender.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        genderArrayAdapter.notifyDataSetChanged();
        fields = new EditText[]{firstName, surname, dateOfBirth};
        setSupportActionBar(createToolBar("NBSZ - Create Donor Profile"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(holder != null){
            firstName.setText(holder.firstName);
            surname.setText(holder.surname);
            idNumber.setText(holder.idNumber);
            if(holder.dateOfBirth != null){
                updateLabel(holder.dateOfBirth, dateOfBirth);
            }
            Gender g = holder.gender;
            for(int i = 0; i < genderArrayAdapter.getCount(); i++){
                Gender current = genderArrayAdapter.getItem(i);
                if(current.equals(g)){
                    gender.setItemChecked(i, true);
                }
            }
        }else{
            holder = new Donor();
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == dateOfBirth.getId()){
            dialog.show();
        }
        if(view.getId() == next.getId()){
            if(validate(fields)){
                if(validate()){
                    String date = dateOfBirth.getText().toString().trim();
                    String name = firstName.getText().toString().trim();
                    String lastName = surname.getText().toString().trim();
                    String dob = DateUtil.formatDate(DateUtil.getDateFromString(date));
                    List<Donor> list = Donor.findByFirstNameAndLastNameAndDateOfBirth(name.toUpperCase(), lastName.toUpperCase(), dob);
                    if(list.size() > 0){
                        Intent intent = new Intent(getApplicationContext(), SearchDonorListActivity.class);
                        intent.putExtra("firstName", name.toUpperCase());
                        intent.putExtra("surname", lastName.toUpperCase());
                        intent.putExtra("dob", dob);
                        startActivity(intent);
                        finish();
                    }else{
                        fetchRemote(this, name, lastName, dob);
                    }

                }
            }
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        holder.firstName = firstName.getText().toString();
        holder.surname = surname.getText().toString();
        if( ! idNumber.getText().toString().isEmpty()){
            holder.idNumber = idNumber.getText().toString();
        }

        if(getGender() != null){
            holder.gender = getGender();
        }
        if( ! dateOfBirth.getText().toString().isEmpty())
            holder.dateOfBirth = DateUtil.getDateFromString(dateOfBirth.getText().toString());
        intent.putExtra("holder", holder);
        intent.putExtra("counsellor", counsellor);
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

    public boolean validate(){
        boolean isValid = true;
        String date = dateOfBirth.getText().toString();
        if(! date.isEmpty()){
            if( ! checkDateFormat(date)){
                dateOfBirth.setError(getResources().getString(R.string.date_format_error));
                isValid = false;
            }else {
                dateOfBirth.setError(null);
            }
            if(checkDateFormat(date)){
                if(DateUtil.getYears(DateUtil.getDateFromString(date)) < 16){
                    dateOfBirth.setError(getResources().getString(R.string.under_age_error));
                    isValid = false;
                }else{
                    dateOfBirth.setError(null);
                }
            }
        }
        /*String name = firstName.getText().toString();
        if( ! validateStrings(name)){
            firstName.setError(getResources().getString(R.string.name_format_error));
            isValid = false;
        }else{
            firstName.setError(null);
        }*/
        String lastName = surname.getText().toString();
        if( ! validateStrings(lastName)){
            surname.setError(getResources().getString(R.string.name_format_error));
            isValid = false;
        }else{
            surname.setError(null);
        }
        if(getGender() == null){
            AppUtil.createShortNotification(this, "Sorry, please select gender before proceeding");
            isValid = false;
        }
        String id = idNumber.getText().toString();
        String dob = DateUtil.formatDate(DateUtil.getDateFromString(date));
        if( ! id.isEmpty()){
            if( ! validateNationalId(id)){
                idNumber.setError("Please enter a valid ID number");
                isValid = false;
            }else{
                idNumber.setError(null);
            }
            if(validateNationalId(id)){
                if(Donor.findByNationalId(id) != null){
                    idNumber.setError(getResources().getString(R.string.item_exists));
                    isValid = false;
                }else{
                    idNumber.setError(null);
                }
            }
        }
        return isValid;
    }

    public Gender getGender(){
        Gender g = null;
        for(int i = 0; i < gender.getCount(); i++){
            if(gender.isItemChecked(i)){
                g = genderArrayAdapter.getItem(i);
            }
        }
        return g;
    }

    public void fetchRemote(final Context context, final String name, final String surname, final String dob) {
        String url = AppUtil.getBaseUrl(context) + "form/get-by-details?firstName="+name.trim()+"&surname="+surname.trim()+"&dob="+dob;
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response", response.length() + " ");
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
                                    if(object.getString("gender").equals("M") || object.getString("gender").equals("F")){
                                        item.gender = Gender.valueOf(object.getString("gender"));
                                    }
                                    if( ! object.isNull("dob")){
                                        item.dateOfBirth = DateUtil.getDateFromString(object.getString("dob"));
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
                                        Log.d("Saved counsellor", c.name);
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
                                        Log.d("Saved item", item.donorNumber);
                                    }
                                    saved++;
                                    Log.d("Saved count: ", saved + "");
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
                            holder.firstName = name.toUpperCase();
                            holder.surname = surname.toUpperCase();
                            if( ! idNumber.getText().toString().isEmpty()){
                                holder.idNumber = idNumber.getText().toString().trim();
                            }
                            holder.gender = getGender();
                            if( ! dateOfBirth.getText().toString().isEmpty())
                                holder.dateOfBirth = DateUtil.getDateFromString(dateOfBirth.getText().toString());
                            Intent intent = new Intent(getApplicationContext(), DonorAddContactDetailsActivity.class);
                            intent.putExtra("holder", holder);
                            intent.putExtra("counsellor", counsellor);
                            startActivity(intent);
                            finish();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        holder.firstName = name.toUpperCase();
                        holder.surname = surname.toUpperCase();
                        if( ! idNumber.getText().toString().isEmpty()){
                            holder.idNumber = idNumber.getText().toString().trim();
                        }
                        holder.gender = getGender();
                        if( ! dateOfBirth.getText().toString().isEmpty())
                            holder.dateOfBirth = DateUtil.getDateFromString(dateOfBirth.getText().toString());
                        Intent intent = new Intent(getApplicationContext(), DonorAddContactDetailsActivity.class);
                        intent.putExtra("holder", holder);
                        intent.putExtra("counsellor", counsellor);
                        startActivity(intent);
                        finish();
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
        request.setRetryPolicy(new DefaultRetryPolicy(5000, 3, 2.0F));
        AppUtil.getInstance(context).addToRequestQueue(request);
    }
}
