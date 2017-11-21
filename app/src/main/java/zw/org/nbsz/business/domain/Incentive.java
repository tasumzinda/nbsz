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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tasu on 9/18/17.
 */
@Table(name = "incentive")
public class Incentive extends Model implements Serializable {

    @Expose
    @SerializedName("id")
    @Column(name = "server_id")
    public Long serverId;

    @Expose
    @Column
    public String name;

    public Incentive(){
        super();
    }

    public static Incentive findById(Long id){
        return new Select()
                .from(Incentive.class)
                .where("server_id = ?", id)
                .executeSingle();
    }

    public static List<Incentive> getAll(){
        return new Select()
                .from(Incentive.class)
                .execute();
    }

    public static Incentive fromJSON(JSONObject object){
        Incentive item = new Incentive();
        try{
            item.name = object.getString("name");
            item.serverId = object.getLong("id");
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
        return item;
    }

    public static ArrayList<Incentive> fromJSON(JSONArray array){
        ArrayList<Incentive> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject object = null;
            try{
                object = array.getJSONObject(i);
            }catch (JSONException ex){
                ex.printStackTrace();
                continue;
            }
            Incentive item = fromJSON(object);
            if(item != null)
                list.add(item);
        }
        return list;
    }

    public static List<Incentive> findByOffer(Offer offer){
        return new Select()
                .from(Incentive.class)
                .innerJoin(OfferIncentiveContract.class)
                .on("offer_incentive.incentive_id = incentive.server_id")
                .where("offer_incentive.offer_id = ?", offer.getId())
                .execute();
    }

    public String toString(){
        return name;
    }
}
