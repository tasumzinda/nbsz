package zw.org.nbsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import zw.org.nbsz.business.util.AppUtil;

public class RiskAssessmentStep2Activity extends BaseActivity implements View.OnClickListener {

    private HListView illegalDrugs;
    private HListView exchangedMoneyForSex;
    private HListView trueForSexPartner;
    private HListView sufferedFromSTD;
    private HListView contactWithPersonWithYellowJaundice;
    private Button next;
    private Donor holder;
    private Counsellor counsellor;
    private ArrayAdapter<YesNo> adapter;
    private HListView[] fields;
    private String donorNumber;
    private Donor item;
    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_assessment_step2);
        Intent intent = getIntent();
        holder = (Donor) intent.getSerializableExtra("holder");
        Log.d("Donor", AppUtil.createGson().toJson(holder));
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNumber = intent.getStringExtra("donorNumber");
        id = intent.getLongExtra("id", 0L);
        exchangedMoneyForSex = (HListView) findViewById(R.id.exchanged_money_for_sex);
        illegalDrugs = (HListView) findViewById(R.id.illegal_drugs);
        trueForSexPartner = (HListView) findViewById(R.id.true_for_sex_partner);
        sufferedFromSTD = (HListView) findViewById(R.id.suffered_from_std);
        contactWithPersonWithYellowJaundice = (HListView) findViewById(R.id.yellow_jaundice);
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        fields = new HListView[]{exchangedMoneyForSex, sufferedFromSTD, illegalDrugs, trueForSexPartner, contactWithPersonWithYellowJaundice};
        adapter = new ArrayAdapter<>(this, R.layout.check_box_item, YesNo.values());
        exchangedMoneyForSex.setAdapter(adapter);
        exchangedMoneyForSex.setItemsCanFocus(false);
        exchangedMoneyForSex.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        illegalDrugs.setAdapter(adapter);
        illegalDrugs.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        illegalDrugs.setItemsCanFocus(false);
        trueForSexPartner.setAdapter(adapter);
        trueForSexPartner.setItemsCanFocus(false);
        trueForSexPartner.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        sufferedFromSTD.setAdapter(adapter);
        sufferedFromSTD.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        sufferedFromSTD.setItemsCanFocus(false);
        contactWithPersonWithYellowJaundice.setAdapter(adapter);
        contactWithPersonWithYellowJaundice.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        contactWithPersonWithYellowJaundice.setItemsCanFocus(false);
        adapter.notifyDataSetChanged();
        setSupportActionBar(createToolBar("NBSZ - SECTION B"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(donorNumber != null && ! donorNumber.isEmpty() && holder.exchangedMoneyForSex != null){
            item = Donor.findByDonorNumber(donorNumber);
            YesNo item =  holder.exchangedMoneyForSex;
            int count = adapter.getCount();
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    exchangedMoneyForSex.setItemChecked(i, true);
                }
            }
            item = holder.injectedWithIllegalDrugs;
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    illegalDrugs.setItemChecked(i, true);
                }
            }
            item = holder.trueForSexPartner;
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    trueForSexPartner.setItemChecked(i, true);
                }
            }
            item = holder.sufferedFromSTD;
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    sufferedFromSTD.setItemChecked(i, true);
                }
            }
            item = holder.contactWithPersonWithYellowJaundice;
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    contactWithPersonWithYellowJaundice.setItemChecked(i, true);
                }
            }
        }else if(holder.exchangedMoneyForSex != null){
            YesNo item =  holder.exchangedMoneyForSex;
            int count = adapter.getCount();
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    exchangedMoneyForSex.setItemChecked(i, true);
                }
            }
            item = holder.injectedWithIllegalDrugs;
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    illegalDrugs.setItemChecked(i, true);
                }
            }
            item = holder.trueForSexPartner;
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    trueForSexPartner.setItemChecked(i, true);
                }
            }
            item = holder.sufferedFromSTD;
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    sufferedFromSTD.setItemChecked(i, true);
                }
            }
            item = holder.contactWithPersonWithYellowJaundice;
            for(int i = 0; i < count; i++){
                YesNo current = adapter.getItem(i);
                if(current.equals(item)){
                    contactWithPersonWithYellowJaundice.setItemChecked(i, true);
                }
            }
        }else {
            item = new Donor();
        }
    }

    @Override
    public void onClick(View view) {
        if(validateListView(fields)){
            holder.exchangedMoneyForSex = getExchangedMoneyForSex();
            holder.injectedWithIllegalDrugs = getIllegalDrugs();
            holder.trueForSexPartner = getTrueForSexPartner();
            holder.sufferedFromSTD = getSufferedFromSTD();
            holder.contactWithPersonWithYellowJaundice = getContactWithPersonWithYellowJaundice();
            Intent intent = new Intent(this, RiskAssessmentStep3Activity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            intent.putExtra("id", id);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onBackPressed(){
        holder.exchangedMoneyForSex = getExchangedMoneyForSex();
        holder.injection= getIllegalDrugs();
        holder.trueForSexPartner = getTrueForSexPartner();
        holder.sufferedFromSTD = getSufferedFromSTD();
        holder.contactWithPersonWithYellowJaundice = getContactWithPersonWithYellowJaundice();
        Intent intent;
        if(holder.beenTestedForHiv.equals(YesNo.YES)){
            intent = new Intent(this, ReasonForTestingActivity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            intent.putExtra("id", id);
            startActivity(intent);
            finish();
        }else{
            intent = new Intent(this, RiskAssessmentActivity.class);
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

    private YesNo getExchangedMoneyForSex(){
        YesNo b = null;
        for(int i = 0; i < exchangedMoneyForSex.getCount(); i++){
            if(exchangedMoneyForSex.isItemChecked(i)){
                b = adapter.getItem(i);
            }
        }
        return b;
    }

    private YesNo getIllegalDrugs(){
        YesNo a = null;
        for(int i = 0; i < illegalDrugs.getCount(); i++){
            if(illegalDrugs.isItemChecked(i)){
                a = adapter.getItem(i);
            }
        }
        return a;
    }

    private YesNo getTrueForSexPartner(){
        YesNo a = null;
        for(int i = 0; i < trueForSexPartner.getCount(); i++){
            if(trueForSexPartner.isItemChecked(i)){
                a = adapter.getItem(i);
            }
        }
        return a;
    }

    private YesNo getSufferedFromSTD(){
        YesNo a = null;
        for(int i = 0; i < sufferedFromSTD.getCount(); i++){
            if(sufferedFromSTD.isItemChecked(i)){
                a = adapter.getItem(i);
            }
        }
        return a;
    }

    private YesNo getContactWithPersonWithYellowJaundice(){
        YesNo a = null;
        for(int i = 0; i < contactWithPersonWithYellowJaundice.getCount(); i++){
            if(contactWithPersonWithYellowJaundice.isItemChecked(i)){
                a = adapter.getItem(i);
            }
        }
        return a;
    }
}
