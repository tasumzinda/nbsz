package zw.org.nbsz.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tasu on 5/11/17.
 */
@Table(name = "special_notes")
public class SpecialNotes extends Model implements Serializable{

    @Expose
    @Column(name = "name")
    public String name;

    public SpecialNotes(){

    }

    public static List<SpecialNotes> getAll(){
        return new Select()
                .from(SpecialNotes.class)
                .execute();
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
}
