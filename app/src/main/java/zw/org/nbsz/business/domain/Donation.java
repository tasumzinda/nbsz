package zw.org.nbsz.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by tasu on 7/13/17.
 */
@Table(name = "donation")
public class Donation extends Model {

    @Expose
    @SerializedName("id")
    @Column(name = "server_id")
    public Long serverId;

    @Expose
    @Column(name = "donation_number")
    public String donationNumber;

    //@Expose
    @Column(name = "date")
    public Date date;

    @Expose
    public String donationDate;

    @Expose
    @Column(name = "start_time")
    public String timStart;

    @Expose
    @Column(name = "donation_time")
    public String timDonation;

    @Expose
    @Column(name = "donation_type")
    public DonationType donationType;

    @Expose
    @Column(name = "person")
    public Donor person;

    @Expose
    @Column(name = "donor_age")
    public Integer donorAge;

    @Column
    @Expose
    public Centre city;


    public Donation(){
        super();
    }

    public static List<Donation> getAll(){
        return new Select()
                .from(Donation.class)
                .execute();
    }

    public static List<Donation> findByDonor(Donor donor){
        return new Select()
                .from(Donation.class)
                .where("person = ?", donor.getId())
                .execute();
    }
}
