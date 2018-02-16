package zw.org.nbsz.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import zw.org.nbsz.business.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tasu on 7/13/17.
 */
@Table(name = "donation")
public class Donation extends Model {

    @Expose
    @SerializedName("id")
    @Column(name = "server_id")
    public Long serverId;

    @Expose
    @Column(name = "donation_number")
    public String donationNumber;

    //@Expose
    @Column(name = "date")
    public Date date;

    @Expose
    public String donationDate;

    @Expose
    @Column(name = "start_time")
    public String timStart;

    @Expose
    @Column(name = "donation_time")
    public String timDonation;

    @Expose
    @Column(name = "donation_type")
    public DonationType donationType;

    @Expose
    @Column(name = "person")
    public Donor person;

    @Expose
    @Column(name = "donor_age")
    public Integer donorAge;

    @Column
    @Expose
    public Centre city;

    @Expose
    public String requestType = "POST_DONATION";

    @Expose
    @Column
    public String localId;


    public Donation(){
        super();
    }

    public static List<Donation> getAll(){
        return new Select()
                .from(Donation.class)
                .execute();
    }

    public static Donation findByLocalId(String localId){
        return new Select()
                .from(Donation.class)
                .where("localId = ?", localId)
                .executeSingle();
    }

    public static List<Donation> findByDonor(Donor donor){
        return new Select()
                .from(Donation.class)
                .where("person = ?", donor.getId())
                .execute();
    }

    public static Donation fromJSON(JSONObject object){
        Donation item = new Donation();
        try{
            item.donationNumber = object.getString("donationNumber");
            item.date = DateUtil.getDateFromString(object.getString("donationDate"));
            item.timStart = object.getString("timStart");
            if ( ! object.isNull("timDonation")){
                item.timDonation = object.getString("timDonation");
            }
            if( ! object.isNull("donationType")){
                JSONObject donationType = object.getJSONObject("donationType");
                item.donationType = DonationType.findById(donationType.getLong("id"));
            }
            if( ! object.isNull("donorAge")){
                item.donorAge = object.getInt("donorAge");
            }
            if( ! object.isNull("city")){
                JSONObject city = object.getJSONObject("city");
                item.city = Centre.findById(city.getLong("id"));
            }
            if( ! object.isNull("person")){
                JSONObject person = object.getJSONObject("person");
                item.person = Donor.findByLocalId(person.getString("localId"));
            }
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
        return item;
    }

    public static ArrayList<Donation> fromJSON(JSONArray array){
        ArrayList<Donation> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject object = null;
            try{
                object = array.getJSONObject(i);
            }catch (JSONException ex){
                ex.printStackTrace();
                continue;
            }
            Donation item = fromJSON(object);
            if(item != null)
                list.add(item);
        }
        return list;
    }
}
