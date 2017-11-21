package zw.org.nbsz.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.JsonArray;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tasu on 5/7/17.
 */
@Table(name = "city")
public class Centre extends Model implements Serializable{
    @Expose
    @Column(name = "COD_CENTRE")
    public String name;

    @Expose
    @SerializedName("id")
    @Column(name = "server_id")
    public Long server_id;

    @Expose
    @Column(name = "DES_CENTRE")
    public String description;

    @Expose
    @Column(name = "COD_AREA")
    public String area;

    @Expose
    @Column(name = "COD_COMPLEX")
    public String complex;

    @Expose
    @Column(name = "DES_LOGO")
    public String logo;

    @Expose
    @Column(name = "DES_LABEL")
    public String label;

    @Expose
    @Column(name = "DES_SIGNATURE1")
    public String signature1;

    @Expose
    @Column(name = "DES_SIGNATURE2")
    public String signature2;

    public Centre(){
        super();
    }

    public static List<Centre> getAll(){
        return new Select()
                .from(Centre.class)
                .orderBy("COD_CENTRE ASC")
                .execute();
    }

    public static Centre findById(Long id){
        return new Select()
                .from(Centre.class)
                .where("server_id = ?", id)
                .executeSingle();
    }

    public static Centre findByName(String name){
        return new Select()
                .from(Centre.class)
                .where("COD_CENTRE = ?", name)
                .executeSingle();
    }

    @Override
    public String toString(){
        return name;
    }

    public static Centre fromJSON(JSONObject object){
        Centre c = new Centre();
        try{
            c.area = object.getString("area");
            c.complex = object.getString("complex");
            c.description = object.getString("description");
            c.label = object.getString("label");
            c.name = object.getString("name");
            c.signature1 = object.getString("signature1");
            c.signature2 = object.getString("signature2");
            c.server_id = object.getLong("id");
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
        return c;
    }

    public static ArrayList<Centre> fromJSON(JSONArray array){
        ArrayList<Centre> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject object = null;
            try{
               object = array.getJSONObject(i);
            }catch (JSONException ex){
                ex.printStackTrace();
                continue;
            }
            Centre item = fromJSON(object);
            if(item != null)
                list.add(item);
        }
        return list;
    }
}
