package zw.org.nbsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
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
    private ListView packType;
    private ListView specialNotes;
    private Button next;
    private Donor holder;
    private Counsellor counsellor;
    private ArrayAdapter<SpecialNotes> adapter;
    private String donorNumber;
    private Donor item;
    private ArrayAdapter<PassFail> passFailArrayAdapter;
    private ArrayAdapter<PackType> packTypeArrayAdapter;
    private ArrayAdapter<User> userArrayAdapter;
    private TextView hamocueLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_step2);
        Intent intent = getIntent();
        holder = (Donor) intent.getSerializableExtra("holder");
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNumber = intent.getStringExtra("donorNumber");
        hamocue = (ListView) findViewById(R.id.hamocue);
        hamocueLabel = (TextView) findViewById(R.id.hamocue_label);
        bledBy = (ListView) findViewById(R.id.bled_by);
        packType = (ListView) findViewById(R.id.pack_type);
        specialNotes = (ListView) findViewById(R.id.list);
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        adapter = new ArrayAdapter<>(this, R.layout.check_box_item, SpecialNotes.getAll());
        passFailArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, PassFail.values());
        hamocue.setAdapter(passFailArrayAdapter);
        hamocue.setItemsCanFocus(false);
        hamocue.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        passFailArrayAdapter.notifyDataSetChanged();
        packTypeArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, PackType.values());
        packType.setAdapter(packTypeArrayAdapter);
        packType.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        packType.setItemsCanFocus(false);
        packTypeArrayAdapter.notifyDataSetChanged();
        specialNotes.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        specialNotes.setItemsCanFocus(false);
        specialNotes.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
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
            ArrayList<SpecialNotes> list =  holder.specialNotes;
            ArrayList<String> list1 = new ArrayList<>();
            for(SpecialNotes s : list){
                list1.add(s.name);
            }
            int count = adapter.getCount();
            for(int i = 0; i < count; i++){
                SpecialNotes current = adapter.getItem(i);
                if(list1.contains(current.name)){
                    specialNotes.setItemChecked(i, true);
                }
            }

            count = userArrayAdapter.getCount();
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
            PackType d = holder.packType;
            count = packTypeArrayAdapter.getCount();
            for(int k = 0; k < count; k++){
                PackType current = packTypeArrayAdapter.getItem(k);
                if(current.equals(d)){
                    packType.setItemChecked(k, true);
                }
            }
        }else if(holder.specialNotes != null){
            ArrayList<SpecialNotes> list =  holder.specialNotes;
            ArrayList<String> list1 = new ArrayList<>();
            for(SpecialNotes s : list){
                list1.add(s.name);
            }
            int count = adapter.getCount();
            for(int i = 0; i < count; i++){
                SpecialNotes current = adapter.getItem(i);
                if(list1.contains(current.name)){
                    specialNotes.setItemChecked(i, true);
                }
            }

            if(holder.userId != null){
                count = userArrayAdapter.getCount();
                User user = User.findById(holder.userId);
                for(int i = 0; i < count; i++){
                    User current = userArrayAdapter.getItem(i);
                    if(current.serverId.equals(user.serverId)){
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
            PackType d = holder.packType;
            count = packTypeArrayAdapter.getCount();
            for(int k = 0; k < count; k++){
                PackType current = packTypeArrayAdapter.getItem(k);
                if(current.equals(d)){
                    packType.setItemChecked(k, true);
                }
            }
        }else {
            item = new Donor();
        }
        setSupportActionBar(createToolBar("NBSZ - FOR OFFICIAL USE ONLY"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        if(getUser() == null || (hamocue.getVisibility() == View.VISIBLE && getHamocue() == null) || getPackType() == null){
            AppUtil.createShortNotification(this, "Sorry, this response is required");
        }else{
            holder.hamocue = getHamocue();
            holder.packType = getPackType();
            holder.userId = getUser().serverId;
            holder.specialNotes = getSpecialNotes();
            Intent intent = new Intent(this, NurseFinalActivity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onBackPressed(){
        holder.hamocue = getHamocue();
        holder.packType = getPackType();
        if(getUser() != null){
            holder.userId = getUser().serverId;
        }
        holder.specialNotes = getSpecialNotes();
        if(holder.donateDefer != null && holder.donateDefer.equals(DonateDefer.DEFER)){
            Intent intent = new Intent(this, DeferStep1.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(this, NurseStep1Activity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            startActivity(intent);
            finish();
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

    private ArrayList<SpecialNotes> getSpecialNotes(){
        ArrayList<SpecialNotes> a = new ArrayList<>();
        for(int i = 0; i < specialNotes.getCount(); i++){
            if(specialNotes.isItemChecked(i)){
                a.add(adapter.getItem(i));
            }else{
                a.remove(adapter.getItem(i));
            }
        }
        return a;
    }

    private PackType getPackType(){
        PackType item = null;
        for(int i = 0; i < packType.getCount(); i++){
            if(packType.isItemChecked(i)){
                item = packTypeArrayAdapter.getItem(i);
            }
        }
        return item;
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
