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
 * Created by tasu on 5/11/17.
 */
@Table(name = "special_notes")
public class SpecialNotes extends Model implements Serializable{

    @Expose
    @Column(name = "name")
    public String name;

    @Expose
    @SerializedName("id")
    @Column(name = "server_id")
    public Long serverId;

    public SpecialNotes(){

    }

    public static List<SpecialNotes> getAll(){
        return new Select()
                .from(SpecialNotes.class)
                .execute();
    }

    public static SpecialNotes findById(Long id){
        return new Select()
                .from(SpecialNotes.class)
                .where("server_id = ?", id)
                .executeSingle();
    }

    @Override
    public String toString() {
        return name;
    }

    public static List<SpecialNotes> findByDonor(Donor item){
        return new Select()
                .from(SpecialNotes.class)
                .innerJoin(DonorSpecialNotesContract.class)
                .on("donor_special_notes.special_notes = special_notes.id")
                .where("donor_special_notes.donor =?", item.getId())
                .execute();
    }

    public static SpecialNotes fromJSON(JSONObject object){
        SpecialNotes item = new SpecialNotes();
        try{
            item.name = object.getString("name");
            item.serverId = object.getLong("id");
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
        return item;
    }

    public static ArrayList<SpecialNotes> fromJSON(JSONArray array){
        ArrayList<SpecialNotes> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject object = null;
            try{
                object = array.getJSONObject(i);
            }catch (JSONException ex){
                ex.printStackTrace();
                continue;
            }
            SpecialNotes item = fromJSON(object);
            if(item != null)
                list.add(item);
        }
        return list;
    }
}
