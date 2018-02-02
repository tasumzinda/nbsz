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
import zw.org.nbsz.business.domain.util.*;
import zw.org.nbsz.business.util.DateUtil;
import zw.org.nbsz.business.util.Log;

import java.io.Serializable;
import java.util.*;

/**
 * Created by tasu on 6/5/17.
 */
@Table(name = "person")
public class Donor extends Model implements Serializable {

    @Expose
    @Column(name = "server_id")
    @SerializedName("id")
    public Long server_id;

    public Long professionId;
    @Expose
    @Column(name = "profession")
    public Profession profession;

    public Long maritalStatusId;
    @Expose
    @Column(name = "marital_status")
    public MaritalStatus maritalStatus;

    public Long cityId;
    @Expose
    @Column(name = "city")
    public Centre city;

    @Expose
    @Column(name = "first_name")
    public String firstName;

    @Expose
    @Column(name = "surname")
    public String surname;

    @Expose
    @Column(name = "id_number")
    public String idNumber;

    @Expose
    @SerializedName("gender")
    public String genderValue;

    @Column(name = "gender")
    public Gender gender;

    @Column(name = "date_of_birth")
    public Date dateOfBirth;

    @Expose
    @SerializedName("dob")
    @Column(name = "dob")
    public String dob;

    @Expose
    @Column(name = "defer_date")
    public String deferDate;

    @Expose
    @Column(name = "home_telephone")
    public String homeTelephone;

    @Expose
    @Column(name = "work_telephone")
    public String workTelephone;

    @Expose
    @Column(name = "cellphoneNumber")
    public String cellphoneNumber;

    @Expose
    @Column(name = "residentialAddress")
    public String residentialAddress;

    @Expose
    @Column(name = "email")
    public String email;

    public YesNo consentToUpdate;

    public String bloodPressure;

    @Expose
    @Column(name = "donor_number")
    public String donorNumber;

    @Expose
    @Column(name = "bled_by")
    public User bledBy;

    public Long userId;

    public Double weight;

    @Expose
    @Column(name = "counsellor")
    public Counsellor counsellor;

    public DonateDefer donateDefer;

    public YesNo feelingWellToday;

    public YesNo refusedToDonate;

    public YesNo beenToMalariaArea;

    public YesNo mealOrSnack;

    public YesNo dangerousOccupation;

    public YesNo rheumaticFever;

    public YesNo lungDisease;

    public YesNo cancer;

    public YesNo diabetes;

    public YesNo chronicMedicalCondition;

    public YesNo beenToDentist;

    public YesNo takenAntibiotics;

    public YesNo injection;

    public YesNo beenIll;

    public YesNo receivedBloodTransfusion;

    @Column
    @Expose
    public Integer deferPeriod;

    public YesNo hivTest;

    public YesNo beenTestedForHiv;

    public YesNo contactWithPersonWithYellowJaundice;

    public YesNo accidentalExposureToBlood;

    public YesNo beenTattooedOrPierced;

    public YesNo injectedWithIllegalDrugs;

    public YesNo sexWithSomeoneWithUnknownBackground;

    public YesNo exchangedMoneyForSex;

    public YesNo trueForSexPartner;

    public YesNo sufferedFromSTD;

    public YesNo contactWithPersonWithHepatitisB;

    public YesNo sufferedFromNightSweats;

    public PassFail copperSulphate;

    public PassFail hamocue;

    public PackType packType;

    public ReasonForTesting reasonForTesting;

    public YesNo victimOfSexualAbuse;

    public YesNo pregnant;

    public YesNo breastFeeding;

    public Long defferedReasonId;
    @Expose
    @Column(name = "reason_for_deferring")
    public DeferredReason deferredReason;

    @Expose
    @Column(name = "collect_site")
    public CollectSite collectSite;

    public Long donationTypeId;

    @Expose
    @Column(name = "donation_type")
    public DonationType donationType;

    @Column(name = "entry_date")
    public Date entryDate;

    @Expose
    @Column(name = "entry")
    public String entry;

    @Expose
    @SerializedName("timeEntry")
    @Column(name = "entry_time")
    public String entryTime;

    public String donationNumber;

    @Column
    public Integer pushed = 0;

    @Column
    public Integer isNew = 0;

    @Expose
    @Column(name = "defer_notes")
    public String deferNotes;

    /*@Expose
    @Column
    public Integer suspicious = 0;*/

    @Expose
    @Column(name = "blood_group")
    public String bloodGroup;

    public Integer pulse;

    @Column
    @Expose
    public String accepted;

    public ArrayList<Long> incentives;

    @Column
    @Expose
    public Integer numberOfDonations;



    public List<SpecialNotes> specialNotes;

    public Donor(){
        super();
    }

    public static List<Donor> getAll(){
        return new Select()
                .from(Donor.class)
                .execute();
    }

    public static Donor findById(Long id){
        return new Select()
                .from(Donor.class)
                .where("Id = ?", id)
                .executeSingle();
    }

    public static Donor findByServerId(Long id){
        return new Select()
                .from(Donor.class)
                .where("server_id = ?", id)
                .executeSingle();
    }

    public static Donor findByDonorNumber(String donorNumber){
        return new Select()
                .from(Donor.class)
                .where("donor_number = ?", donorNumber)
                .executeSingle();
    }

    public static Donor findByNationalId(String nationalId){
        return new Select()
                .from(Donor.class)
                .where("id_number = ?", nationalId)
                .executeSingle();
    }


    public static List<Donor> findTodayDonations(String entry){
        return new Select()
                .from(Donor.class)
                .where("entry = ?", entry)
                .execute();
    }

    public static List<Donor> findByFirstNameAndLastNameAndDateOfBirth(String firstName, String surname, String dob){
        return new Select()
                .from(Donor.class)
                .where("first_name = ?", firstName)
                .and("surname = ?", surname)
                .and("dob = ?", dob)
                .execute();
    }

    public static List<Donor> findByLastNameAndDateOfBirth(String surname, String dob){
        return new Select()
                .from(Donor.class)
                .and("surname = ?", surname)
                .and("dob = ?", dob)
                .execute();
    }

    public static List<Donor> findByPushed(){
        return new Select()
                .from(Donor.class)
                .where("pushed = ?", 1)
                .execute();
    }
    public static Donor fromJSON(JSONObject object){
        Donor item = new Donor();
        try{
            item.firstName = object.getString("firstName").toUpperCase().trim();
            item.surname = object.getString("surname").toUpperCase().trim();
            item.idNumber = object.getString("idNumber");
            if( ! object.isNull("numberOfDonations")){
                item.numberOfDonations = object.getInt("numberOfDonations");
            }

            if(object.getString("gender").equals("M") || object.getString("gender").equals("F")){
                item.gender = Gender.valueOf(object.getString("gender"));
            }
            if( ! object.isNull("dob")){
                item.dateOfBirth = DateUtil.getDateFromString(object.getString("dob"));
                item.dob = DateUtil.formatDate(item.dateOfBirth);
            }

            if( ! object.isNull("deferDate")){
                item.deferDate = DateUtil.getStringFromDate(DateUtil.getDateFromString(object.getString("deferDate")));
            }
            if( ! object.isNull("entry")){
                item.entryDate = DateUtil.getDateFromString(object.getString("entry"));
                item.entry = DateUtil.getStringFromDate(item.entryDate);
            }
            if( ! object.isNull("deferNotes")){
                item.deferNotes = object.getString("deferNotes");
            }
            if( ! object.isNull("deferPeriod")){
                item.deferPeriod = object.getInt("deferPeriod");
            }



            if( ! object.isNull("profession")){
                JSONObject profession = object.getJSONObject("profession");
                item.profession = Profession.findById(profession.getLong("id"));
            }
            if( ! object.isNull("maritalStatus")){
                JSONObject maritalStatus = object.getJSONObject("maritalStatus");
                item.maritalStatus = MaritalStatus.findById(maritalStatus.getLong("id"));
            }
            if(! object.isNull("city")){
                JSONObject city = object.getJSONObject("city");
                item.city = Centre.findById(city.getLong("id"));
            }
            item.residentialAddress = object.getString("residentialAddress");
            item.homeTelephone = object.getString("homeTelephone");
            item.workTelephone = object.getString("workTelephone");
            item.cellphoneNumber = object.getString("cellphoneNumber");
            item.email = object.getString("email");
            if( ! object.isNull("counsellor")){
                JSONObject counsellor = object.getJSONObject("counsellor");
                Counsellor c = new Counsellor();
                c.name = counsellor.getString("name");
                c.address = counsellor.getString("address");
                c.phoneNumber = counsellor.getString("phoneNumber");
                c.code = counsellor.getString("code");
                c.serverId = counsellor.getLong("id");
                Counsellor duplicate = Counsellor.findById(c.serverId);
                if(duplicate == null){
                    c.save();
                }
                Log.d("Saved counsellor", c.name);
                item.counsellor = c;
            }
            if( ! object.isNull("deferredReason")){
                JSONObject deferredReason = object.getJSONObject("deferredReason");
                item.deferredReason = DeferredReason.findById(deferredReason.getLong("id"));
            }
            if( ! object.isNull("collectSite")){
                JSONObject collectSite = object.getJSONObject("collectSite");
                item.collectSite = CollectSite.findById(collectSite.getLong("id"));
            }
            if( ! object.isNull("donationType")){
                JSONObject donationType = object.getJSONObject("donationType");
                item.donationType = DonationType.findById(donationType.getLong("id"));
            }
            item.server_id = object.getLong("id");
            item.donorNumber = object.getString("donorNumber");
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
        return item;
    }

    public static ArrayList<Donor> fromJSON(JSONArray array){
        ArrayList<Donor> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject object = null;
            try{
                object = array.getJSONObject(i);
            }catch (JSONException ex){
                ex.printStackTrace();
                continue;
            }
            Donor item = fromJSON(object);
            if(item != null)
                list.add(item);
        }
        return list;
    }

    public String toString(){
        return firstName + " " + surname;
    }

}
