package zw.org.nbsz.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tasu on 9/18/17.
 */
@Table(name = "offer_incentive")
public class OfferIncentiveContract extends Model implements Serializable {

    @Expose
    @Column(name = "incentive_id")
    public Incentive incentive;

    @Expose
    @Column(name = "offer_id")
    public Offer offer;

    public OfferIncentiveContract(){
        super();
    }

    public static List<OfferIncentiveContract> findByOffer(Offer offer){
        return new Select()
                .from(OfferIncentiveContract.class)
                .where("offer_id = ?", offer.getId())
                .execute();
    }
}
