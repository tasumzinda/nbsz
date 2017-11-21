package zw.org.nbsz.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

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

    }



}
