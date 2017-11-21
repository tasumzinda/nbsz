package zw.org.nbsz.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tasu on 5/24/17.
 */
@Table(name = "donor_type")
public class DonorType extends Model implements Serializable {

    @Expose
    @Column(name = "ID_DONORTYPE")
    public Long id;

    @Expose
    @Column(name = "COD_DONORTYPE")
    public String code;

    @Expose
    @Column(name = "DES_DONORTYPE")
    public String name;

    public DonorType() {
    }

    public static List<DonorType> getAll(){
        return new Select()
                .from(DonorType.class)
                .execute();
    }

    public static DonorType findById(Long id){
        return new Select()
                .from(DonorType.class)
                .where("ID_DONORTYPE = ?", id)
                .executeSingle();
    }

    public static DonorType fromJSON(JSONObject object){
        DonorType item = new DonorType();
        try{
            item.code = object.getString("code");
            item.name = object.getString("name");
            item.id = object.getLong("id");
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
        return item;
    }

    public static ArrayList<DonorType> fromJSON(JSONArray array){
        ArrayList<DonorType> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject object = null;
            try{
                object = array.getJSONObject(i);
            }catch (JSONException ex){
                ex.printStackTrace();
                continue;
            }
            DonorType item = fromJSON(object);
            if(item != null)
                list.add(item);
        }
        return list;
    }

}
