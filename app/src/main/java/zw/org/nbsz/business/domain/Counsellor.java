package zw.org.nbsz.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tasu on 5/8/17.
 */
@Table(name = "counsellor")
public class Counsellor extends Model implements Serializable {

    @Expose
    @Column(name = "server_id")
    @SerializedName("id")
    public Long serverId;

    @Expose
    @Column(name = "name")
    public String name;

    @Expose
    @Column(name = "address")
    public String address;

    @Expose
    @Column(name = "phone_number")
    public String phoneNumber;

    @Expose
    @Column(name = "COD_COUNSELLOR")
    public String code;

    public Counsellor(){
        super();
    }

    public static Counsellor findById(Long id){
        return new Select()
                .from(Counsellor.class)
                .where("server_id = ?", id)
                .executeSingle();
    }

    public static List<Counsellor> getAll(){
        return new Select()
                .from(Counsellor.class)
                .execute();
    }
}
