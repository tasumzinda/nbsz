package zw.org.nbsz.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.Counsellor;
import zw.org.nbsz.business.domain.Donor;
import zw.org.nbsz.business.domain.User;
import zw.org.nbsz.business.util.AppUtil;
import zw.org.nbsz.business.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SelectUserActivity extends BaseActivity implements View.OnClickListener{

    private ListView users;
    private Button next;
    private ArrayAdapter<User> adapter;
    private Donor holder;
    private Counsellor counsellor;
    private String donorNumber;
    private Donor item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);
        Intent intent = getIntent();
        holder = (Donor) intent.getSerializableExtra("holder");
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNumber = intent.getStringExtra("donorNumber");
        users = (ListView) findViewById(R.id.users);
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        setSupportActionBar(createToolBar("NBSZ - Select Team Members"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(savedInstanceState == null){
            syncAppData();
        }
        adapter = new ArrayAdapter<>(this, R.layout.check_box_item, User.getAll());
        users.setAdapter(adapter);
        users.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        users.setItemsCanFocus(false);
        if(User.getActive().size() > 0){
            ArrayList<User> selected = (ArrayList<User>) User.getActive();
            ArrayList<Long> selectedId = new ArrayList<>();
            for(User u : selected){
                selectedId.add(u.serverId);
            }
            int count = adapter.getCount();
            for(int i = 0; i < count; i++){
                User current = adapter.getItem(i);
                if(selectedId.contains(current.serverId)){
                    users.setItemChecked(i, true);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(getSelectedUsers().size() != 0){
            for(User i : User.getActive()){
                i.activeToday = 0;
                i.save();
            }
            for(User i : getSelectedUsers()){
                Log.d("Saved user", AppUtil.createGson().toJson(i));
                i.activeToday = 1;
                i.save();
            }
            Intent intent = new Intent(this, SelectCollectSiteActivity.class);
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            startActivity(intent);
            finish();
        }else{
            AppUtil.createShortNotification(this, "Please select team members before proceeding");
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

    public List<User> getSelectedUsers(){
        List<User> list = new ArrayList<>();
        for(int i = 0; i < users.getCount(); i++){
            if(users.isItemChecked(i)){
                list.add(adapter.getItem(i));
            }
        }
        return list;
    }

    @Override
    public void updateView(){
        adapter.clear();
        adapter.addAll(User.getAll());
        adapter.notifyDataSetChanged();
    }
}
