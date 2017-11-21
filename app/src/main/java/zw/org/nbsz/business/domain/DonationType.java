package zw.org.nbsz.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
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
 * Created by tasu on 6/29/17.
 */
public class DonationType extends Model implements Serializable {

    @Expose
    @SerializedName("id")
    @Column(name = "server_id")
    public Long serverId;

    @Expose
    @Column(name = "name")
    public String name;

    @Expose
    @Column(name = "srt")
    public String srtDonationType;

    public DonationType(){
        super();
    }

    public static List<DonationType> getAll(){
        return new Select()
                .from(DonationType.class)
                .execute();
    }

    public static DonationType findById(Long serverId){
        return new Select()
                .from(DonationType.class)
                .where("server_id = ?", serverId)
                .executeSingle();
    }

    public static DonationType fromJSON(JSONObject object){
        DonationType item = new DonationType();
        try{
            item.name = object.getString("name");
            item.serverId = object.getLong("id");
            item.srtDonationType = object.getString("srtDonationType");
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
        return item;
    }

    public static ArrayList<DonationType> fromJSON(JSONArray array){
        ArrayList<DonationType> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject object;
            try{
                object = array.getJSONObject(i);
            }catch (JSONException ex){
                ex.printStackTrace();
                continue;
            }
            DonationType item = fromJSON(object);
            if(item != null)
                list.add(item);
        }
        return list;
    }

    @Override
    public String toString() {
        return name;
    }
}
