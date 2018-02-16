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
    @Column(name = "contact_with_person_with_hepatitisB")
    public YesNo contactWithPersonWithHepatitisB;

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

    @Expose
    public String requestType = "POST_STATISTICS";

    @Expose
    @Column
    public String localId;

    public DonationStats(){
        super();
    }

    public static List<DonationStats> findByDonor(Donor donor){
        return new Select()
                .from(DonationStats.class)
                .where("person = ?", donor.getId())
                .execute();
    }

    public static DonationStats findByLocalId(String localId){
        return new Select()
                .from(DonationStats.class)
                .where("localId = ?", localId)
                .executeSingle();
    }

    public static List<DonationStats> getAll(){
        return new Select()
                .from(DonationStats.class)
                .execute();
    }

    public static DonationStats fromJSON(JSONObject object){
        DonationStats item = new DonationStats();
        try{
            if( ! object.isNull("person")){
                JSONObject person = object.getJSONObject("person");
                item.person = Donor.findByLocalId(person.getString("localId"));
            }
            if( ! object.isNull("id")){
                item.server_id = object.getLong("id");
            }
            if( ! object.isNull("feelingWellToday")){
                item.feelingWellToday = YesNo.valueOf(object.getString("feelingWellToday"));
            }
            if( ! object.isNull("refusedToDonate")){
                item.refusedToDonate = YesNo.valueOf(object.getString("refusedToDonate"));
            }
            if( ! object.isNull("beenToMalariaArea")){
                item.beenToMalariaArea = YesNo.valueOf(object.getString("beenToMalariaArea"));
            }
            if( ! object.isNull("mealOrSnack")){
                item.mealOrSnack = YesNo.valueOf(object.getString("mealOrSnack"));
            }
            if( ! object.isNull("dangerousOccupation")){
                item.dangerousOccupation = YesNo.valueOf(object.getString("dangerousOccupation"));
            }
            if( ! object.isNull("rheumaticFever")){
                item.rheumaticFever = YesNo.valueOf(object.getString("rheumaticFever"));
            }
            if( ! object.isNull("lungDisease")){
                item.lungDisease = YesNo.valueOf(object.getString("lungDisease"));
            }
            if( ! object.isNull("cancer")){
                item.cancer = YesNo.valueOf(object.getString("cancer"));
            }
            if( ! object.isNull("diabetes")){
                item.diabetes = YesNo.valueOf(object.getString("diabetes"));
            }
            if( ! object.isNull("chronicMedicalCondition")){
                item.chronicMedicalCondition = YesNo.valueOf(object.getString("chronicMedicalCondition"));
            }
            if( ! object.isNull("beenToDentist")){
                item.beenToDentist = YesNo.valueOf(object.getString("beenToDentist"));
            }
            if( ! object.isNull("takenAntibiotics")){
                item.takenAntibiotics = YesNo.valueOf(object.getString("takenAntibiotics"));
            }
            if( ! object.isNull("injection")){
                item.injection = YesNo.valueOf(object.getString("injection"));
            }
            if( ! object.isNull("beenIll")){
                item.beenIll = YesNo.valueOf(object.getString("beenIll"));
            }
            if( ! object.isNull("receivedBloodTransfusion")){
                item.receivedBloodTransfusion = YesNo.valueOf(object.getString("receivedBloodTransfusion"));
            }
            if( ! object.isNull("hivTest")){
                item.hivTest = YesNo.valueOf(object.getString("hivTest"));
            }
            if( ! object.isNull("beenTestedForHiv")){
                item.beenTestedForHiv = YesNo.valueOf(object.getString("beenTestedForHiv"));
            }
            if( ! object.isNull("contactWithPersonWithYellowJaundice")){
                item.contactWithPersonWithYellowJaundice = YesNo.valueOf(object.getString("contactWithPersonWithYellowJaundice"));
            }
            if( ! object.isNull("accidentalExposureToBlood")){
                item.accidentalExposureToBlood = YesNo.valueOf(object.getString("accidentalExposureToBlood"));
            }
            if( ! object.isNull("beenTattooedOrPierced")){
                item.beenTattooedOrPierced = YesNo.valueOf(object.getString("beenTattooedOrPierced"));
            }
            if( ! object.isNull("injectedWithIllegalDrugs")){
                item.injectedWithIllegalDrugs = YesNo.valueOf(object.getString("injectedWithIllegalDrugs"));
            }
            if( ! object.isNull("sexWithSomeoneWithUnknownBackground")){
                item.sexWithSomeoneWithUnknownBackground = YesNo.valueOf(object.getString("sexWithSomeoneWithUnknownBackground"));
            }
            if( ! object.isNull("exchangedMoneyForSex")){
                item.exchangedMoneyForSex = YesNo.valueOf(object.getString("exchangedMoneyForSex"));
            }
            if( ! object.isNull("trueForSexPartner")){
                item.trueForSexPartner = YesNo.valueOf(object.getString("trueForSexPartner"));
            }
            if( ! object.isNull("sufferedFromSTD")){
                item.sufferedFromSTD = YesNo.valueOf(object.getString("sufferedFromSTD"));
            }
            if( ! object.isNull("contactWithPersonWithHepatitisB")){
                item.contactWithPersonWithHepatitisB = YesNo.valueOf(object.getString("contactWithPersonWithHepatitisB"));
            }
            if( ! object.isNull("sufferedFromNightSweats")){
                item.sufferedFromNightSweats = YesNo.valueOf(object.getString("sufferedFromNightSweats"));
            }
            if( ! object.isNull("victimOfSexualAbuse")){
                item.victimOfSexualAbuse = YesNo.valueOf(object.getString("victimOfSexualAbuse"));
            }
            if( ! object.isNull("pregnant")){
                item.pregnant = YesNo.valueOf(object.getString("pregnant"));
            }
            if( ! object.isNull("breastFeeding")){
                item.breastFeeding = YesNo.valueOf(object.getString("breastFeeding"));
            }
            if( ! object.isNull("copperSulphate")){
                item.copperSulphate = PassFail.valueOf(object.getString("copperSulphate"));
            }
            if( ! object.isNull("hamocue")){
                item.hamocue = PassFail.valueOf(object.getString("hamocue"));
            }
            if( ! object.isNull("packType")){
                item.packType = PackType.valueOf(object.getString("packType"));
            }
            if( ! object.isNull("weight")){
                item.weight = object.getDouble("weight");
            }
            if( ! object.isNull("bloodPressure")){
                item.bloodPressure = object.getString("bloodPressure");
            }
            if( ! object.isNull("entry")){
                item.entry = object.getString("entry");
            }
            if( ! object.isNull("reasonForTesting")){
                item.reasonForTesting = ReasonForTesting.valueOf(object.getString("reasonForTesting"));
            }
            if( ! object.isNull("localId")){
                item.localId = object.getString("localId");
            }
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
        return item;
    }

    public static ArrayList<DonationStats> fromJSON(JSONArray array){
        ArrayList<DonationStats> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject object = null;
            try{
                object = array.getJSONObject(i);
            }catch (JSONException ex){
                ex.printStackTrace();
                continue;
            }
            DonationStats item = fromJSON(object);
            if(item != null)
                list.add(item);
        }
        return list;
    }
}
