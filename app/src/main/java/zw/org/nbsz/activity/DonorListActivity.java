package zw.org.nbsz.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

public class DonorListActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    DonorAdapter donorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_list_view);
        final ArrayList<Donor> list = (ArrayList<Donor>) Donor.findTodayDonations(DateUtil.getStringFromDate(new Date()));
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
        Intent intent = new Intent(DonorListActivity.this, DonorReviewActivity.class);
        intent.putExtra("id", donor.getId());
        String name = donor.firstName + " " + donor.surname;
        Log.d("Patient", AppUtil.createGson().toJson(donor));
        intent.putExtra(AppUtil.NAME, name);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, SearchDonorActivity.class);
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

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

}
