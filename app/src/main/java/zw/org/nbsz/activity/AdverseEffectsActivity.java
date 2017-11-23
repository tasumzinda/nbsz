package zw.org.nbsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.Donor;
import zw.org.nbsz.business.domain.SpecialNotes;
import zw.org.nbsz.business.util.AppUtil;
import zw.org.nbsz.business.util.Log;

import java.util.ArrayList;

public class AdverseEffectsActivity extends BaseActivity implements View.OnClickListener{

    private ListView specialNotes;
    private ArrayAdapter<SpecialNotes> adapter;
    private Button save;
    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adverse_effects);
        specialNotes = (ListView) findViewById(R.id.list);
        adapter = new ArrayAdapter<>(this, R.layout.check_box_item, SpecialNotes.getAll());
        specialNotes.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        specialNotes.setItemsCanFocus(false);
        specialNotes.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        save = (Button) findViewById(R.id.btn_save);
        save.setOnClickListener(this);
        Intent intent = getIntent();
        id = intent.getLongExtra("id", 0L);
        setSupportActionBar(createToolBar("NBSZ-ADVERSE EFFECTS"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, DonorReviewActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
        finish();
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
}
