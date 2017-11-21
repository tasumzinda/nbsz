package zw.org.nbsz.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.Donor;
import zw.org.nbsz.business.util.AppUtil;
import zw.org.nbsz.business.util.Log;

import java.util.ArrayList;

/**
 * Created by tasu on 9/1/17.
 */
public class DonorAdapter extends ArrayAdapter<Donor> {

    private Context context;
    private ArrayList<Donor> list;
    TextView name;
    TextView dob;
    TextView address;
    TextView donorNumber;

    public DonorAdapter(Context context, ArrayList<Donor> list){
        super(context, R.layout.list_view_item, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        View newView = convertView;
        if(newView == null){
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            newView = layoutInflater.inflate(R.layout.list_view_item, null);
        }
        name = (TextView) newView.findViewById(R.id.adapter_name);
        dob = (TextView) newView.findViewById(R.id.dob);
        address = (TextView) newView.findViewById(R.id.address);
        donorNumber = (TextView) newView.findViewById(R.id.donorNumber);
        Donor donor = list.get(pos);
        Log.d("Donor", AppUtil.createGson().toJson(donor));
        name.setText(pos + 1 + "." + donor.toString());
        dob.setText(donor.dob != null ? donor.dob : "");
        donorNumber.setText(donor.donorNumber != null ? donor.donorNumber : "");
        address.setText(donor.residentialAddress != null ? donor.residentialAddress : "" + " " + donor.city.name != null ? donor.city.name : "");
        return newView;
    }

    public void onDataSetChanged(ArrayList<Donor> list){
        list.clear();
        list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public Donor getItem(int position) {
        return list.get(position);
    }

    //3
    @Override
    public long getItemId(int position) {
        return position;
    }
}
