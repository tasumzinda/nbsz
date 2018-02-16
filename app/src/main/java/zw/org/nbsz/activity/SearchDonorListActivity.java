package zw.org.nbsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import zw.org.nbsz.R;
import zw.org.nbsz.adapter.DonorAdapter;
import zw.org.nbsz.business.domain.Donor;
import zw.org.nbsz.business.util.AppUtil;
import zw.org.nbsz.business.util.DateUtil;
import zw.org.nbsz.business.util.Log;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tasu on 9/5/17.
 */
public class SearchDonorListActivity extends BaseActivity implements AdapterView.OnItemClickListener{

   private DonorAdapter donorAdapter;
   private String firstName;
   private String surname;
   private String dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_list_view);
        Intent intent = getIntent();
        firstName = intent.getStringExtra("firstName");
        surname = intent.getStringExtra("surname");
        dob = intent.getStringExtra("dob");
        ArrayList<Donor> list = new ArrayList<>();
        if(firstName.isEmpty()){
            list = (ArrayList<Donor>) Donor.findByLastNameAndDateOfBirth(surname, dob);
        }else{
            list = (ArrayList<Donor>) Donor.findByFirstNameAndLastNameAndDateOfBirth(firstName, surname, dob);
        }

        donorAdapter = (new DonorAdapter(this, list));
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setEmptyView(findViewById(R.id.empty));
        listView.setAdapter(donorAdapter);
        donorAdapter.onDataSetChanged((ArrayList<Donor>) Donor.getAll());
        setSupportActionBar(createToolBar("Donors"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Donor donor = (Donor) parent.getAdapter().getItem(position);
        if(donor.isNew == 1){
            AppUtil.createShortNotification(this, "Can not perfom any operation on a new donor until the record is sent to server!");
        }else{
            Intent intent = new Intent(SearchDonorListActivity.this, DonorDetailsActivity.class);
            intent.putExtra("donorNumber", donor.donorNumber);
            intent.putExtra("localId", donor.localId);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, SearchDonorActivity.class);
        intent.putExtra("firstName", firstName);
        intent.putExtra("surname", surname);
        intent.putExtra("dob", dob);
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

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }
}
