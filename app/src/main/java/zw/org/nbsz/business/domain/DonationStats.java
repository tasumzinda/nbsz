package zw.org.nbsz.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import zw.org.nbsz.business.domain.*;
import zw.org.nbsz.business.domain.util.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tasu on 7/17/17.
 */
@Table(name = "donation_stats")
public class DonationStats extends Model implements Serializable{

    @Expose
    @Column(name = "server_id")
    @SerializedName("id")
    public Long server_id;

    @Expose
    @Column(name = "feeling_well_today")
    public YesNo feelingWellToday;

    @Expose
    @Column(name = "refused_to_donated")
    public YesNo refusedToDonate;

    @Expose
    @Column(name = "been_to_malaria_area")
    public YesNo beenToMalariaArea;

    @Expose
    @Column(name = "meal_or_snack")
    public YesNo mealOrSnack;

    @Expose
    @Column(name = "dangerous_occupation")
    public YesNo dangerousOccupation;

    @Expose
    @Column(name = "rheumatic_fever")
    public YesNo rheumaticFever;

    @Expose
    @Column(name = "lung_disease")
    public YesNo lungDisease;

    @Expose
    @Column(name = "cancer")
    public YesNo cancer;

    @Expose
    @Column(name = "disease")
    public YesNo diabetes;

    @Expose
    @Column(name = "chronic_medical_condition")
    public YesNo chronicMedicalCondition;

    @Expose
    @Column(name = "been_to_dentist")
    public YesNo beenToDentist;

    @Expose
    @Column(name = "taken_antibiotics")
    public YesNo takenAntibiotics;

    @Expose
    @Column(name = "injection")
    public YesNo injection;

    @Expose
    @Column(name = "been_ill")
    public YesNo beenIll;

    @Expose
    @Column(name = "received_blood_transfusion")
    public YesNo receivedBloodTransfusion;

    @Expose
    @Column(name = "hiv_test")
    public YesNo hivTest;

    @Expose
    @Column(name = "been_tested_for_hiv")
    public YesNo beenTestedForHiv;

    @Expose
    @Column(name = "contact_with_person_with_yellow_jaundice")
    public YesNo contactWithPersonWithYellowJaundice;

    @Expose
    @Column(name = "accidental_exposure_to_blood")
    public YesNo accidentalExposureToBlood;

    @Expose
    @Column(name = "been_tattooed_or_pierced")
    public YesNo beenTattooedOrPierced;

    @Expose
    @Column(name = "injected_with_illegal_drugs")
    public YesNo injectedWithIllegalDrugs;

    @Expose
    @Column(name = "sex_with_someone_with_unknown_background")
    public YesNo sexWithSomeoneWithUnknownBackground;

    @Expose
    @Column(name = "exchanged_money_for_sex")
    public YesNo exchangedMoneyForSex;

    @Expose
    @Column(name = "true_for_sex_partner")
    public YesNo trueForSexPartner;

    @Expose
    @Column(name = "suffered_from_STD")
    public YesNo sufferedFromSTD;

    @Expose
    @Column(name = "change_in_marital_status")
    public YesNoNA monogamousRelationship;

    @Expose
    @Column(name = "suffered_from_night_sweats")
    public YesNo sufferedFromNightSweats;

    @Expose
    @Column(name = "copper_sulphate")
    public PassFail copperSulphate;

    @Expose
    @Column(name = "hamocue")
    public PassFail hamocue;

    @Expose
    @Column(name = "pack_type")
    public PackType packType;

    @Expose
    @Column(name = "reason_for_testing")
    public ReasonForTesting reasonForTesting;

    @Expose
    @Column(name = "victim_of_sexual_abuse")
    public YesNo victimOfSexualAbuse;

    @Expose
    @Column
    public YesNo pregnant;

    @Column(name = "breast_feeding")
    @Expose
    public YesNo breastFeeding;

    public ArrayList<SpecialNotes> specialNotes;

    @Expose
    @Column(name = "weight")
    public Double weight;

    @Expose
    @Column(name = "blood_pressure")
    public String bloodPressure;

    @Expose
    @Column(name = "person")
    public Donor person;

    @Expose
    @Column(name = "entry")
    public String entry;

    public DonationStats(){
        super();
    }

    public static List<DonationStats> findByDonor(Donor donor){
        return new Select()
                .from(DonationStats.class)
                .where("person = ?", donor.getId())
                .execute();
    }

    public static List<DonationStats> getAll(){
        return new Select()
                .from(DonationStats.class)
                .execute();
    }
}
