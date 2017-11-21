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
 * Created by tasu on 9/15/17.
 */
@Table(name = "offer")
public class Offer extends Model {

    @Expose
    @SerializedName("id")
    @Column(name = "server_id")
    public Long serverId;

    @Column(name = "offer_date")
    public Date offerDate;

    @Expose
    public String offer;

    @Expose
    @Column(name = "user")
    public User user;

    @Expose
    @Column(name = "person")
    public Donor person;

    @Expose
    @Column(name = "collect_site")
    public CollectSite collectSite;

    @Expose
    @Column(name = "donation_type")
    public DonationType donationType;

    @Expose
    @Column(name = "donation_kind")
    public String donationKind;

    @Expose
    @Column(name = "check_up")
    public String checkUp;

    @Expose
    @Column(name = "phlebotomy")
    public String phlebotomy;

    @Expose
    @Column(name = "donation_number")
    public String donationNumber;

    @Expose
    @Column(name = "directed")
    public String directed;

    @Expose
    @Column(name = "deferred_reason")
    public DeferredReason defferredReason;

    @Expose
    @Column(name = "centre")
    public Centre centre;


    @Column(name = "defer_date")
    public Date deferDate;

    @Expose
    public String defer;

    @Expose
    @Column(name = "donor_age")
    public Integer donorAge;

    @Column(name="offer_time")
    @Expose
    public String offerTime;

    @Column
    @Expose
    public Integer pulse;

    @Expose
    public List<Incentive> incentives;

    public Offer(){
        super();
    }

    public static List<Offer> findByDonor(Donor donor){
        return new Select()
                .from(Offer.class)
                .where("person = ?", donor.getId())
                .execute();
    }

    public static List<Offer> getAll(){
        return new Select()
                .from(Offer.class)
                .execute();
    }
}
