package zw.org.nbsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import it.sephiroth.android.library.widget.HListView;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.Counsellor;
import zw.org.nbsz.business.domain.Donor;
import zw.org.nbsz.business.domain.util.YesNo;

public class RiskAssessmentActivity extends BaseActivity implements View.OnClickListener {

    private HListView hivTest;
    private HListView beenTestedForHiv;
    private Button next;
    private Donor holder;
    private Counsellor counsellor;
    private ArrayAdapter<YesNo> adapter;
    private HListView[] fields;
    private String donorNumber;
    private Donor item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_assessment);
        Intent intent = getIntent();
        holder = (Donor) intent.getSerializableExtra("holder");
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNumber = intent.getStringExtra("donorNumber");
        hivTest = (HListView) findViewById(R.id.hiv_test);
        beenTestedForHiv = (HListView) findViewById(R.id.been_tested_for_hiv);
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        fields = new HListView[]{hivTest, beenTestedForHiv};
        adapter = new ArrayAdapter<>(this, R.layout.check_box_item, YesNo.values());
        hivTest.setAdapter(adapter);
        hivTest.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        hivTest.setItemsCanFocus(false);
        beenTestedForHiv.setAdapter(adapter);
        beenTestedForHiv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        beenTestedForHiv.setItemsCanFocus(false);
        adapter.notifyDataSetChanged();
        setSupportActionBar(createToolBar("NBSZ - SECTION B"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(donorNumber != null && ! donorNumber.isEmpty() && holder.hivTest != null){
            item = Donor.findByDonorNumber(donorNumber);
            YesNo item =  holder.hivTest;
            int count = adapter.getCount();
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    hivTest.setItemChecked(i, true);
                }
            }
            item = holder.beenTestedForHiv;
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    beenTestedForHiv.setItemChecked(i, true);
                }
            }
        }else if(holder.hivTest != null){
            YesNo item =  holder.hivTest;
            int count = adapter.getCount();
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    hivTest.setItemChecked(i, true);
                }
            }
            item = holder.beenTestedForHiv;
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    beenTestedForHiv.setItemChecked(i, true);
                }
            }
        }else{
            item = new Donor();
        }
    }

    @Override
    public void onClick(View view) {
        if(validateListView(fields)){
            holder.hivTest = getHivTest();
            holder.beenTestedForHiv = getBeenTestedForHiv();
            Intent intent;
            if(getBeenTestedForHiv().equals(YesNo.YES)){
                intent = new Intent(this, ReasonForTestingActivity.class);
                intent.putExtra("holder", holder);
                intent.putExtra("counsellor", counsellor);
                intent.putExtra("donorNumber", donorNumber);
                startActivity(intent);
                finish();
            }else{
                intent = new Intent(this, RiskAssessmentStep2Activity.class);
                holder.reasonForTesting = null;
                intent.putExtra("holder", holder);
                intent.putExtra("counsellor", counsellor);
                intent.putExtra("donorNumber", donorNumber);
                startActivity(intent);
                finish();
            }

        }

    }

    @Override
    public void onBackPressed(){
        holder.hivTest = getHivTest();
        holder.beenTestedForHiv = getBeenTestedForHiv();
        Intent intent = new Intent(this, HealthAssessmentStep5Activity.class);
        intent.putExtra("holder", holder);
        intent.putExtra("counsellor", counsellor);
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

    private YesNo getHivTest(){
        YesNo b = null;
        for(int i = 0; i < hivTest.getCount(); i++){
            if(hivTest.isItemChecked(i)){
                b = adapter.getItem(i);
            }
        }
        return b;
    }

    private YesNo getBeenTestedForHiv(){
        YesNo a = null;
        for(int i = 0; i < beenTestedForHiv.getCount(); i++){
            if(beenTestedForHiv.isItemChecked(i)){
                a = adapter.getItem(i);
            }
        }
        return a;
    }
}
