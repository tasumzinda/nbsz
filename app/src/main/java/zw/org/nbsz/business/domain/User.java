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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tasu on 7/6/17.
 */
@Table(name = "user")
public class User extends Model {

    @Expose
    @SerializedName("id")
    @Column(name = "server_id")
    public Long serverId;

    @Expose
    @Column
    public String name;

    @Expose
    @Column
    public String srt;

    @Expose
    @Column
    public String userId;

    @Expose
    @Column
    public Centre centre;

    @Column
    public Integer logged_in = 0;

    @Column(name = "active")
    public int activeToday = 0;

    public User(){
        super();
    }

    public static User findById(Long id){
        return new Select()
                .from(User.class)
                .where("server_id = ?", id)
                .executeSingle();
    }

    public static User findByName(String name){
        return new Select()
                .from(User.class)
                .where("name = ?", name)
                .executeSingle();
    }

    public static List<User> getAll(){
        return new Select()
                .from(User.class)
                .execute();
    }

    public static List<User> getActive(){
        return new Select()
                .from(User.class)
                .where("active = ?", 1)
                .execute();
    }

    public static User getLoggedIn(){
        return new Select()
                .from(User.class)
                .where("logged_in = ?", 1)
                .executeSingle();
    }

    public static User fromJSON(JSONObject object){
        User item = new User();
        try{
            item.serverId = object.getLong("id");
            item.name = object.getString("name");
            item.srt = object.getString("srt");
            if( ! object.isNull("centre")){
                JSONObject centre = object.getJSONObject("centre");
                item.centre = Centre.findById(centre.getLong("id"));
            }
            item.userId = object.getString("userId");
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
        return item;
    }

    public static ArrayList<User> fromJSON(JSONArray array){
        ArrayList<User> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject object = null;
            try{
                object = array.getJSONObject(i);
            }catch (JSONException ex){
                ex.printStackTrace();
                continue;
            }
            User item = fromJSON(object);
            if(item != null)
                list.add(item);
        }
        return list;
    }

    public String toString(){
        return name;
    }
}
