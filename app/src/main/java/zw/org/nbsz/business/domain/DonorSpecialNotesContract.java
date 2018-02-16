package zw.org.nbsz.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by tasu on 5/11/17.
 */
@Table(name = "donor_special_notes")
public class DonorSpecialNotesContract extends Model{

    @Expose
    @Column(name = "donor")
    public Donor donor;

    @Expose
    @Column(name = "special_notes")
    public SpecialNotes specialNotes;

    public DonorSpecialNotesContract(){
        super();
    }

    public static List<DonorSpecialNotesContract> findByDonor(Donor item){
        return new Select()
                .from(DonorSpecialNotesContract.class)
                .where("donor = ?", item.getId())
                .execute();
    }

    public static List<DonorSpecialNotesContract> getAll(){
        return new Select()
                .from(DonorSpecialNotesContract.class)
                .execute();
    }
}
