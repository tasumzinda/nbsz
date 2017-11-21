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
@Table(name = "bank_staff")
public class BankStaff extends Model implements Serializable {

    @Expose
    @SerializedName("id")
    @Column(name = "server_id")
    public Long serverId;

    @Expose
    @Column(name = "COD_BANKSTAFF")
    public String code;

    @Expose
    @Column(name = "DES_BANKSTAFF")
    public String name;

    @Expose
    @Column(name = "ID_CENTRE")
    public Centre centre;

    public BankStaff(){
        super();
    }

    public static BankStaff findById(Long serverId){
        return new Select()
                .from(BankStaff.class)
                .where("server_id = ?", serverId)
                .executeSingle();
    }

    public static BankStaff fromJSON(JSONObject object){
        BankStaff item = new BankStaff();
        try{
            item.serverId = object.getLong("id");
            item.code = object.getString("code");
            item.name = object.getString("name");
            JSONObject centre = object.getJSONObject("centre");
            item.centre = Centre.findById(centre.getLong("id"));
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
        return item;
    }

    public static ArrayList<BankStaff> fromJSON(JSONArray array){
        ArrayList<BankStaff> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject object = null;
            try{
                object = array.getJSONObject(i);
            }catch (JSONException ex){
                ex.printStackTrace();
                continue;
            }
            BankStaff item = fromJSON(object);
            if(item != null)
                list.add(item);
        }
        return list;
    }

    public String toString(){
        return name;
    }
}
