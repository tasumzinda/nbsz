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
 * Created by tasu on 5/29/17.
 */
@Table(name = "marital_status")
public class MaritalStatus extends Model implements Serializable {

    @Expose
    @SerializedName("id")
    @Column(name = "server_id")
    public Long server_id;

    @Expose
    @Column(name = "COD_MARITALSTATUS")
    public String code;

    @Expose
    @Column(name = "DES_MARITALSTATUS")
    public String name;

    public MaritalStatus() {
        super();
    }

    public static List<MaritalStatus> getAll(){
        return new Select()
                .from(MaritalStatus.class)
                .execute();
    }

    public static MaritalStatus findById(Long id){
        return new Select()
                .from(MaritalStatus.class)
                .where("server_id = ?", id)
                .executeSingle();
    }

    public static MaritalStatus fromJSON(JSONObject object){
        MaritalStatus item = new MaritalStatus();
        try{
            item.name = object.getString("name");
            item.code = object.getString("code");
            item.server_id = object.getLong("id");
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
        return item;
    }

    public static ArrayList<MaritalStatus> fromJSON(JSONArray array){
        ArrayList<MaritalStatus> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject object = null;
            try{
                object = array.getJSONObject(i);
            }catch (JSONException ex){
                ex.printStackTrace();
                continue;
            }
            MaritalStatus item = fromJSON(object);
            if(item != null)
                list.add(item);
        }
        return list;
    }

    public String toString(){
        return name;
    }
}
