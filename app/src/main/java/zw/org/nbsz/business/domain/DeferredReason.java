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
 * Created by tasu on 5/24/17.
 */
@Table(name = "defferred_reason")
public class DeferredReason extends Model implements Serializable {

    @Expose
    @SerializedName("id")
    @Column(name = "server_id")
    public Long server_id;

    @Expose
    @Column(name = "COD_DEFFERREDREASON")
    public String code;

    @Expose
    @Column(name = "DES_DEFFERRED_REASON")
    public String name;

    public DeferredReason() {
        super();
    }

    public static List<DeferredReason> getAll(){
        return new Select()
                .from(DeferredReason.class)
                .execute();
    }

    public static DeferredReason findById(Long id){
        return new Select()
                .from(DeferredReason.class)
                .where("server_id = ?", id)
                .executeSingle();
    }

    public static DeferredReason fromJSON(JSONObject object){
        DeferredReason item = new DeferredReason();
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

    public static ArrayList<DeferredReason> fromJSON(JSONArray array){
        ArrayList<DeferredReason> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject object = null;
            try{
                object = array.getJSONObject(i);
            }catch (JSONException ex){
                ex.printStackTrace();
                continue;
            }
            DeferredReason item = fromJSON(object);
            if(item != null){
                list.add(item);
            }
        }
        return list;
    }

    @Override
    public String toString(){
        return name;
    }
}
