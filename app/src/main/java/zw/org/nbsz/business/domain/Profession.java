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
@Table(name = "profession")
public class Profession extends Model implements Serializable {

    @Expose
    @SerializedName("id")
    @Column(name = "server_id")
    public Long server_id;

    @Expose
    @Column(name = "code")
    public String code;

    @Expose
    @Column(name = "name")
    public String name;

    public Profession() {
        super();
    }

    public static List<Profession> getAll(){
        return new Select()
                .from(Profession.class)
                .execute();
    }

    public static Profession findById(Long id){
        return new Select()
                .from(Profession.class)
                .where("server_id = ?", id)
                .executeSingle();
    }

    public static Profession fromJSON(JSONObject item){
        Profession p = new Profession();
        try{
            p.name = item.getString("name");
            p.code = item.getString("code");
            p.server_id = item.getLong("id");
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
        return p;
    }

    public static ArrayList<Profession> fromJSON(JSONArray array){
        ArrayList<Profession> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject object = null;
            try{
                object = array.getJSONObject(i);
            }catch (JSONException ex){
                ex.printStackTrace();
                continue;
            }
            Profession item = fromJSON(object);
            if(item != null){
                list.add(item);
            }

        }
        return list;
    }

    @Override
    public String toString() {
        return name;
    }
}
