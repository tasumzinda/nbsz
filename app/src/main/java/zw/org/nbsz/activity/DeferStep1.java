package zw.org.nbsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.Counsellor;
import zw.org.nbsz.business.domain.DeferredReason;
import zw.org.nbsz.business.domain.Donor;
import zw.org.nbsz.business.util.AppUtil;

public class DeferStep1 extends BaseActivity implements View.OnClickListener {

    private EditText deferPeriod;
    private ListView reasonForDeferring;
    private Button next;
    private Donor holder;
    private Counsellor counsellor;
    private ArrayAdapter<DeferredReason> adapter;
    private String donorNumber;
    private Donor item;
    private EditText deferNotes;
    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defer_step1);
        Intent intent = getIntent();
        id = intent.getLongExtra("id", 0L);
        holder = Donor.findById(id);
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNumber = intent.getStringExtra("donorNumber");
        deferPeriod = (EditText) findViewById(R.id.defer_period);
        reasonForDeferring = (ListView) findViewById(R.id.reason);
        deferNotes = (EditText) findViewById(R.id.note);
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        adapter = new ArrayAdapter<>(this, R.layout.check_box_item, DeferredReason.getAll());
        reasonForDeferring.setAdapter(adapter);
        reasonForDeferring.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        reasonForDeferring.setItemsCanFocus(false);
        if(donorNumber != null && ! donorNumber.isEmpty() && holder.deferPeriod != null){
            item = Donor.findByDonorNumber(donorNumber);
            deferPeriod.setText(String.valueOf(holder.deferPeriod));
            if(holder.defferedReasonId != null){
                DeferredReason item = DeferredReason.findById(holder.defferedReasonId);
                for(int i = 0; i < adapter.getCount(); i++){
                    DeferredReason current = adapter.getItem(i);
                    if(item.name.equals(current.name)){
                        reasonForDeferring.setItemChecked(i, true);
                    }
                }
            }

        }else if(holder.deferPeriod != null){
            deferPeriod.setText(String.valueOf(holder.deferPeriod));
            deferNotes.setText(holder.deferNotes);
            if(holder.defferedReasonId != null){
                DeferredReason item = DeferredReason.findById(holder.defferedReasonId);
                for(int i = 0; i < adapter.getCount(); i++){
                    DeferredReason current = adapter.getItem(i);
                    if(item.name.equals(current.name)){
                        reasonForDeferring.setItemChecked(i, true);
                    }
                }
            }

        }else {
            item = new Donor();
        }
        setSupportActionBar(createToolBar("Consent To Donate"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        if(validate()){
            Intent intent = new Intent(getApplicationContext(), NurseFinalActivity.class);
            holder.deferPeriod = Integer.parseInt(deferPeriod.getText().toString());
            holder.defferedReasonId = getDefferredReason().server_id;
            holder.deferNotes = deferNotes.getText().toString();
            intent.putExtra("holder", holder);
            intent.putExtra("counsellor", counsellor);
            intent.putExtra("donorNumber", donorNumber);
            intent.putExtra("id", id);
            startActivity(intent);
            finish();
        }
    }

    public boolean validate(){
        boolean isValid = true;
        if(deferPeriod.getText().toString().isEmpty()){
            deferPeriod.setError(getResources().getString(R.string.required_field_error));
            isValid = false;
        }else{
            deferPeriod.setError(null);
        }
        if(reasonForDeferring.getCheckedItemCount() == 0){
            AppUtil.createShortNotification(this, "Sorry, this response is required");
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, DeclarationFinalActivity.class);
        if( ! deferPeriod.getText().toString().isEmpty()){
            holder.deferPeriod = Integer.parseInt(deferPeriod.getText().toString());
        }
        if(getDefferredReason() != null){
            holder.defferedReasonId = getDefferredReason().server_id;
        }
        holder.deferNotes = deferNotes.getText().toString();
        intent.putExtra("holder", holder);
        intent.putExtra("counsellor", counsellor);
        intent.putExtra("donorNumber", donorNumber);
        intent.putExtra("id", id);
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

    private DeferredReason getDefferredReason(){
        DeferredReason item = null;
        for(int i = 0; i < reasonForDeferring.getCount(); i++){
            if(reasonForDeferring.isItemChecked(i))
                item = adapter.getItem(i);
        }
        return item;
    }
}
