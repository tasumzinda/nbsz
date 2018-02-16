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
import zw.org.nbsz.business.domain.Donor;
import zw.org.nbsz.business.domain.SpecialNotes;
import zw.org.nbsz.business.domain.User;
import zw.org.nbsz.business.domain.util.DonateDefer;
import zw.org.nbsz.business.domain.util.PackType;
import zw.org.nbsz.business.domain.util.PassFail;
import zw.org.nbsz.business.util.AppUtil;
import zw.org.nbsz.business.util.Log;

import java.util.ArrayList;

public class NurseStep2Activity extends BaseActivity implements View.OnClickListener {

    private ListView hamocue;
    private ListView bledBy;
    private Button next;
    private Donor holder;
    private Counsellor counsellor;
    private String donorNumber;
    private Donor item;
    private ArrayAdapter<PassFail> passFailArrayAdapter;
    private ArrayAdapter<User> userArrayAdapter;
    private TextView hamocueLabel;
    @BindView(R.id.donor_number)
    EditText donationNumber;
    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_step2);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        holder = (Donor) intent.getSerializableExtra("holder");
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNumber = intent.getStringExtra("donorNumber");
        id = intent.getLongExtra("id", 0L);
        hamocue = (ListView) findViewById(R.id.hamocue);
        hamocueLabel = (TextView) findViewById(R.id.hamocue_label);
        bledBy = (ListView) findViewById(R.id.bled_by);
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        donationNumber.setOnClickListener(this);
        passFailArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, PassFail.values());
        hamocue.setAdapter(passFailArrayAdapter);
        hamocue.setItemsCanFocus(false);
        hamocue.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        passFailArrayAdapter.notifyDataSetChanged();
        userArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, User.getActive());
        bledBy.setAdapter(userArrayAdapter);
        bledBy.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        bledBy.setItemsCanFocus(false);
        userArrayAdapter.notifyDataSetChanged();
        if(holder.copperSulphate != null && holder.copperSulphate.equals(PassFail.PASS)){
            hamocue.setVisibility(View.GONE);
            hamocueLabel.setVisibility(View.GONE);
        }
        if(donorNumber != null && ! donorNumber.isEmpty() && holder.specialNotes != null){
            item = Donor.findByDonorNumber(donorNumber);
            int count = userArrayAdapter.getCount();
            if(holder.userId != null){
                User user = User.findById(holder.userId);
                for(int i = 0; i < count; i++){
                    User current = userArrayAdapter.getItem(i);
                    if(current.name.equals(user.name)){
                        bledBy.setItemChecked(i, true);
                    }
                }
            }
            PassFail p = holder.hamocue;
            count = passFailArrayAdapter.getCount();
            for(int k = 0; k < count; k++){
                PassFail current = passFailArrayAdapter.getItem(k);
                if(current.equals(p)){
                    hamocue.setItemChecked(k, true);
                }
            }

            if(holder.donationNumber != null){
                donationNumber.setText(String.valueOf(holder.donationNumber));
            }
        }else if(holder.userId != null){
            if(holder.userId != null){
                int count = userArrayAdapter.getCount();
                User user = User.findById(holder.userId);
                for(int i = 0; i < count; i++){
                    User current = userArrayAdapter.getItem(i);
                    if(current.serverId.equals(user.serverId)){
                        bledBy.setItemChecked(i, true);
                    }
                }
            }


            PassFail p = holder.hamocue;
            int count = passFailArrayAdapter.getCount();
            for(int k = 0; k < count; k++){
                PassFail current = passFailArrayAdapter.getItem(k);
                if(current.equals(p)){
                    hamocue.setItemChecked(k, true);
                }
            }

            if(holder.donationNumber != null){
                donationNumber.setText(String.valueOf(holder.donationNumber));
            }
        }else {
            item = new Donor();
        }
        setSupportActionBar(createToolBar("NBSZ - FOR OFFICIAL USE ONLY"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == donationNumber.getId()){
            IntentIntegrator scanner = new IntentIntegrator(this);
            scanner.initiateScan();
        }
        if(view.getId() == next.getId()){
            if(getUser() == null || (hamocue.getVisibility() == View.VISIBLE && getHamocue() == null)){
                AppUtil.createShortNotification(this, "Sorry, this response is required");
            }else{
                holder.hamocue = getHamocue();
                holder.userId = getUser().serverId;
                //holder.specialNotes = getSpecialNotes();
                if( ! donationNumber.getText().toString().isEmpty()){
                    holder.donationNumber = donationNumber.getText().toString();
                }
                Intent intent = new Intent(this, NurseFinalActivity.class);
                intent.putExtra("holder", holder);
                intent.putExtra("counsellor", counsellor);
                intent.putExtra("donorNumber", donorNumber);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }
        }

    }

    @Override
    public void onBackPressed(){
        holder.hamocue = getHamocue();
        if(getUser() != null){
            holder.userId = getUser().serverId;
        }
        if( ! donationNumber.getText().toString().isEmpty()){
            holder.donationNumber = donationNumber.getText().toString();
        }
        //holder.specialNotes = getSpecialNotes();
        if(holder.donateDefer != null && holder.donateDefer.equals(DonateDefer.DEFER)){
            Intent intent = new Intent(this, DeferStep1.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            intent.putExtra("id", id);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(this, NurseStep1Activity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            intent.putExtra("id", id);
            startActivity(intent);
            finish();
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(scanningResult != null){
            String scanContent = scanningResult.getContents();
            donationNumber.setText(scanContent);
        }else{
            AppUtil.createShortNotification(this, "No data retrieved");
        }
    }

    public boolean onOptionsItemSelected(MenuItem  item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private PassFail getHamocue(){
        PassFail item = null;
        for(int i = 0; i < hamocue.getCount(); i++){
            if(hamocue.isItemChecked(i)){
                item = passFailArrayAdapter.getItem(i);
            }
        }
        return item;
    }

    private  User getUser(){
        User item = null;
        for(int i = 0; i < bledBy.getCount(); i++){
            if(bledBy.isItemChecked(i)){
                item = userArrayAdapter.getItem(i);
            }
        }
        return item;
    }
}
