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
public class CollectSite extends Model implements Serializable {

    @Column(name = "server_id")
    @Expose
    @SerializedName("id")
    public Long serverId;

    @Expose
    @SerializedName("name")
    @Column
    public String name;

    @Expose
    @Column(name = "code")
    public String code;

    @Expose
    @Column(name = "centre")
    public Centre centre;

    @Column
    public Integer active = 0;

    public CollectSite(){
        super();
    }

    public static List<CollectSite> getAll(){
        return new Select()
                .from(CollectSite.class)
                .orderBy("name ASC")
                .execute();
    }

    public static CollectSite findByActive(){
        return new Select()
                .from(CollectSite.class)
                .where("active = ?", 1)
                .executeSingle();
    }

    public static List<CollectSite> findByCentre(Centre centre){
        return new Select()
                .from(CollectSite.class)
                .where("centre = ?", centre.getId())
                .orderBy("name ASC")
                .execute();
    }

    public static List<CollectSite> findByNameLike(String name){
        return new Select()
                .from(CollectSite.class)
                .where("name LIKE ?", new String[]{'%' + name + '%'})
                .execute();

    }

    public static CollectSite findById(Long serverId){
        return new Select()
                .from(CollectSite.class)
                .where("server_id = ?", serverId)
                .executeSingle();
    }

    public static CollectSite fromJSON(JSONObject object){
        CollectSite item = new CollectSite();
        try{
            JSONObject centre = object.getJSONObject("centre");
            item.centre = Centre.findById(centre.getLong("id"));
            item.code = object.getString("code");
            item.name = object.getString("name");
            item.serverId = object.getLong("id");
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
        return item;
    }

    public static ArrayList<CollectSite> fromJSON(JSONArray array){
        ArrayList<CollectSite> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject object;
            try{
                object = array.getJSONObject(i);
            }catch (JSONException ex){
                ex.printStackTrace();
                continue;
            }
            CollectSite item = fromJSON(object);
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
