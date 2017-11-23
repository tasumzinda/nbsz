package zw.org.nbsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.Counsellor;
import zw.org.nbsz.business.domain.Donor;
import zw.org.nbsz.business.domain.util.YesNo;

public class AcknowledgeResponsesActivity extends BaseActivity implements View.OnClickListener {

    private TextView feelingWellLabel;
    private CheckBox feelingWell;
    private TextView refusedToDonateLabel;
    private CheckBox refusedToDonate;
    private TextView beenToAMalariaAreaLabel;
    private CheckBox beenToAMalariaArea;
    private TextView mealOrSnackLabel;
    private CheckBox mealOrSnack;
    private TextView dangerousOccupationLabel;
    private CheckBox dangerousOccupation;
    private TextView rheumaticFeverLabel;
    private CheckBox rheumaticFever;
    private TextView lungDiseaseLabel;
    private CheckBox lungDisease;
    private TextView cancerLabel;
    private CheckBox cancer;
    private TextView diabetesLabel;
    private CheckBox diabetes;
    private TextView chronicMedicalConditionLabel;
    private CheckBox chronicMedicalCondition;
    private TextView beenToDentistLabel;
    private CheckBox beenToDentist;
    private TextView takenAntibioticsLabel;
    private CheckBox takenAntibiotics;
    private TextView injectionLabel;
    private CheckBox injection;
    private TextView beenIllLabel;
    private CheckBox beenIll;
    private TextView receivedBloodTransfusionLabel;
    private CheckBox receivedBloodTransfusion;
    private TextView hivTestLabel;
    private CheckBox hivTest;
    private TextView beenTestedForHivLabel;
    private CheckBox beenTestedForHiv;
    private TextView contactWithPersonWithYellowJaundiceLabel;
    private CheckBox contactWithPersonWithYellowJaundice;
    private TextView accidentalExposureLabel;
    private CheckBox accidentalExposure;
    private TextView beenTattooedLabel;
    private CheckBox beenTattooed;
    private CheckBox injectedWithIllegalDrugs;
    private TextView injectedWithIllegalDrugsLabel;
    private TextView sexWithSomeoneWithUnknownBackgroundLabel;
    private CheckBox sexWithSomeoneWithUnknownBackground;
    private TextView exchangedMoneyForSexLabel;
    private CheckBox exchangedMoneyForSex;
    private TextView trueForSexPartnerLabel;
    private CheckBox trueForSexPartner;
    private TextView sufferedFromSTDLabel;
    private CheckBox sufferedFromSTD;
    private TextView monogamousRelationshipLabel;
    private CheckBox monogamousRelationship;
    private TextView sufferedFromNightSweatsLabel;
    private CheckBox sufferedFromNightSweats;
    private TextView victimOfSexualAbuseLabel;
    private CheckBox victimOfSexualAbuse;
    private Button next;
    private Donor holder;
    private Counsellor counsellor;
    private String donorNumber;
    private Donor item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acknowledge_responses);
        Intent intent = getIntent();
        holder = (Donor) intent.getSerializableExtra("holder");
        counsellor = (Counsellor) intent.getSerializableExtra("counsellor");
        donorNumber = intent.getStringExtra("donorNumber");
        feelingWell = (CheckBox) findViewById(R.id.feeling_well_today);
        feelingWellLabel = (TextView) findViewById(R.id.feeling_well_label);
        refusedToDonate = (CheckBox) findViewById(R.id.refused_to_donate);
        refusedToDonateLabel = (TextView) findViewById(R.id.refused_to_donate_label);
        beenToAMalariaArea = (CheckBox) findViewById(R.id.been_to_a_malaria_area);
        beenToAMalariaAreaLabel = (TextView) findViewById(R.id.been_to_a_malaria_area_label);
        mealOrSnack = (CheckBox) findViewById(R.id.meal_or_snack);
        mealOrSnackLabel = (TextView) findViewById(R.id.meal_or_snack_label);
        dangerousOccupation = (CheckBox) findViewById(R.id.dangerous_occupation);
        dangerousOccupationLabel = (TextView) findViewById(R.id.dangerous_occupation_label);
        rheumaticFever = (CheckBox) findViewById(R.id.rheumatic_fever);
        rheumaticFeverLabel = (TextView) findViewById(R.id.rheumatic_fever_label);
        lungDisease = (CheckBox) findViewById(R.id.lung_disease);
        lungDiseaseLabel = (TextView) findViewById(R.id.lung_disease_label);
        cancer = (CheckBox) findViewById(R.id.cancer);
        cancerLabel = (TextView) findViewById(R.id.cancer_label);
        diabetes = (CheckBox) findViewById(R.id.diabetes);
        diabetesLabel = (TextView) findViewById(R.id.diabetes_label);
        chronicMedicalCondition = (CheckBox) findViewById(R.id.chronic_medical_condition);
        chronicMedicalConditionLabel = (TextView) findViewById(R.id.chronic_medical_condition_label);
        beenToDentist = (CheckBox) findViewById(R.id.been_to_dentist);
        beenToDentistLabel = (TextView) findViewById(R.id.been_to_dentist_label);
        takenAntibiotics = (CheckBox) findViewById(R.id.taken_antibiotics);
        takenAntibioticsLabel = (TextView) findViewById(R.id.taken_antibiotics_label);
        injection = (CheckBox) findViewById(R.id.injection);
        injectionLabel = (TextView) findViewById(R.id.injection_label);
        beenIll = (CheckBox) findViewById(R.id.been_ill);
        beenIllLabel = (TextView) findViewById(R.id.been_ill_label);
        receivedBloodTransfusion = (CheckBox) findViewById(R.id.received_blood_transfusion);
        receivedBloodTransfusionLabel = (TextView) findViewById(R.id.received_blood_transfusion_label);
        hivTest = (CheckBox) findViewById(R.id.hiv_test);
        hivTestLabel = (TextView) findViewById(R.id.hiv_test_label);
        beenTestedForHiv = (CheckBox) findViewById(R.id.been_tested_for_hiv);
        beenTestedForHivLabel = (TextView) findViewById(R.id.been_tested_for_hiv_label);
        contactWithPersonWithYellowJaundiceLabel = (TextView) findViewById(R.id.contact_yellow_jaundice_label);
        contactWithPersonWithYellowJaundice = (CheckBox) findViewById(R.id.contact_yellow_jaundice);
        accidentalExposureLabel = (TextView) findViewById(R.id.accidental_exposure_label);
        accidentalExposure = (CheckBox) findViewById(R.id.accidental_exposure);
        beenTattooed = (CheckBox) findViewById(R.id.been_tattooed);
        beenTattooedLabel = (TextView) findViewById(R.id.been_tattooed_label);
        injectedWithIllegalDrugs = (CheckBox) findViewById(R.id.illegal_drugs);
        injectedWithIllegalDrugsLabel = (TextView) findViewById(R.id.illegal_drugs_label);
        sexWithSomeoneWithUnknownBackground = (CheckBox) findViewById(R.id.sex_with_unknown_person);
        sexWithSomeoneWithUnknownBackgroundLabel = (TextView) findViewById(R.id.sex_with_unknown_person_label);
        exchangedMoneyForSex = (CheckBox) findViewById(R.id.exchanged_money_for_sex);
        exchangedMoneyForSexLabel = (TextView) findViewById(R.id.exchanged_money_for_sex_label);
        trueForSexPartner = (CheckBox) findViewById(R.id.true_for_sex_partner);
        trueForSexPartnerLabel = (TextView) findViewById(R.id.true_for_sex_partner_label);
        sufferedFromSTD = (CheckBox) findViewById(R.id.suffered_from_std);
        sufferedFromSTDLabel = (TextView) findViewById(R.id.suffered_from_std_label);
        monogamousRelationship = (CheckBox) findViewById(R.id.change_in_marital_status);
        monogamousRelationshipLabel = (TextView) findViewById(R.id.change_in_marital_status_label);
        sufferedFromNightSweatsLabel = (TextView) findViewById(R.id.suffered_from_night_sweats_label);
        sufferedFromNightSweats = (CheckBox) findViewById(R.id.suffered_from_night_sweats);
        victimOfSexualAbuse = (CheckBox) findViewById(R.id.victim_of_sexual_abuse);
        victimOfSexualAbuseLabel = (TextView) findViewById(R.id.victim_of_sexual_abuse_label);
        next = (Button) findViewById(R.id.btn_save);
        next.setOnClickListener(this);
        if(donorNumber != null && ! donorNumber.isEmpty() && holder != null){
            item = Donor.findByDonorNumber(donorNumber);
            if(holder.feelingWellToday.equals(YesNo.NO)){
                feelingWell.setVisibility(View.VISIBLE);
                feelingWellLabel.setVisibility(View.VISIBLE);
            }
            if(holder.refusedToDonate.equals(YesNo.YES)){
                refusedToDonateLabel.setVisibility(View.VISIBLE);
                refusedToDonate.setVisibility(View.VISIBLE);
            }
            if(holder.beenToMalariaArea.equals(YesNo.YES)){
                beenToAMalariaAreaLabel.setVisibility(View.VISIBLE);
                beenToAMalariaArea.setVisibility(View.VISIBLE);
            }
            if(holder.mealOrSnack.equals(YesNo.NO)){
                mealOrSnackLabel.setVisibility(View.VISIBLE);
                mealOrSnack.setVisibility(View.VISIBLE);
            }
            if(holder.dangerousOccupation.equals(YesNo.YES)){
                dangerousOccupationLabel.setVisibility(View.VISIBLE);
                dangerousOccupation.setVisibility(View.VISIBLE);
            }
            if(holder.rheumaticFever.equals(YesNo.YES)){
                rheumaticFeverLabel.setVisibility(View.VISIBLE);
                rheumaticFever.setVisibility(View.VISIBLE);
            }
            if(holder.lungDisease.equals(YesNo.YES)){
                lungDiseaseLabel.setVisibility(View.VISIBLE);
                lungDisease.setVisibility(View.VISIBLE);
            }
            if(holder.cancer.equals(YesNo.YES)){
                cancerLabel.setVisibility(View.VISIBLE);
                cancer.setVisibility(View.VISIBLE);
            }
            if(holder.diabetes.equals(YesNo.YES)){
                diabetesLabel.setVisibility(View.VISIBLE);
                diabetes.setVisibility(View.VISIBLE);
            }
            if(holder.chronicMedicalCondition.equals(YesNo.YES)){
                chronicMedicalConditionLabel.setVisibility(View.VISIBLE);
                chronicMedicalCondition.setVisibility(View.VISIBLE);
            }
            if(holder.beenToDentist.equals(YesNo.YES)){
                beenToDentistLabel.setVisibility(View.VISIBLE);
                beenToDentist.setVisibility(View.VISIBLE);
            }
            if(holder.takenAntibiotics.equals(YesNo.YES)){
                takenAntibioticsLabel.setVisibility(View.VISIBLE);
                takenAntibiotics.setVisibility(View.VISIBLE);
            }
            if(holder.injection.equals(YesNo.YES)){
                injectionLabel.setVisibility(View.VISIBLE);
                injection.setVisibility(View.VISIBLE);
            }
            if(holder.beenIll.equals(YesNo.YES)){
                beenIllLabel.setVisibility(View.VISIBLE);
                beenIll.setVisibility(View.VISIBLE);
            }
            if(holder.receivedBloodTransfusion.equals(YesNo.YES)){
                receivedBloodTransfusionLabel.setVisibility(View.VISIBLE);
                receivedBloodTransfusion.setVisibility(View.VISIBLE);
            }
            if(holder.hivTest.equals(YesNo.YES)){
                hivTestLabel.setVisibility(View.VISIBLE);
                hivTest.setVisibility(View.VISIBLE);
            }
            if(holder.beenTestedForHiv.equals(YesNo.YES)){
                beenTestedForHivLabel.setVisibility(View.VISIBLE);
                beenTestedForHiv.setVisibility(View.VISIBLE);
            }
            if(holder.contactWithPersonWithYellowJaundice.equals(YesNo.YES)){
                contactWithPersonWithYellowJaundice.setVisibility(View.VISIBLE);
                contactWithPersonWithYellowJaundiceLabel.setVisibility(View.VISIBLE);
            }
            if(holder.accidentalExposureToBlood.equals(YesNo.YES)){
                accidentalExposure.setVisibility(View.VISIBLE);
                accidentalExposureLabel.setVisibility(View.VISIBLE);
            }
            if(holder.beenTattooedOrPierced.equals(YesNo.YES)){
                beenTattooedLabel.setVisibility(View.VISIBLE);
                beenTattooed.setVisibility(View.VISIBLE);
            }
            if(holder.injectedWithIllegalDrugs.equals(YesNo.YES)){
                injectedWithIllegalDrugsLabel.setVisibility(View.VISIBLE);
                injectedWithIllegalDrugs.setVisibility(View.VISIBLE);
            }
            if(holder.sexWithSomeoneWithUnknownBackground.equals(YesNo.YES)){
                sexWithSomeoneWithUnknownBackgroundLabel.setVisibility(View.VISIBLE);
                sexWithSomeoneWithUnknownBackground.setVisibility(View.VISIBLE);
            }
            if(holder.exchangedMoneyForSex.equals(YesNo.YES)){
                exchangedMoneyForSexLabel.setVisibility(View.VISIBLE);
                exchangedMoneyForSex.setVisibility(View.VISIBLE);
            }
            if(holder.trueForSexPartner.equals(YesNo.YES)){
                trueForSexPartnerLabel.setVisibility(View.VISIBLE);
                trueForSexPartner.setVisibility(View.VISIBLE);
            }
            if(holder.sufferedFromSTD.equals(YesNo.YES)){
                sufferedFromSTDLabel.setVisibility(View.VISIBLE);
                sufferedFromSTD.setVisibility(View.VISIBLE);
            }
            if(holder.contactWithPersonWithHepatitisB.equals(YesNo.YES)){
                monogamousRelationshipLabel.setVisibility(View.VISIBLE);
                monogamousRelationship.setVisibility(View.VISIBLE);
            }
            if(holder.sufferedFromNightSweats.equals(YesNo.YES)){
                sufferedFromNightSweats.setVisibility(View.VISIBLE);
                sufferedFromNightSweatsLabel.setVisibility(View.VISIBLE);
            }
            if(holder.victimOfSexualAbuse.equals(YesNo.YES)){
                victimOfSexualAbuseLabel.setVisibility(View.VISIBLE);
                victimOfSexualAbuse.setVisibility(View.VISIBLE);
            }
        }else if(holder != null){
            if(holder.feelingWellToday.equals(YesNo.NO)){
                feelingWell.setVisibility(View.VISIBLE);
                feelingWellLabel.setVisibility(View.VISIBLE);
            }
            if(holder.refusedToDonate.equals(YesNo.YES)){
                refusedToDonateLabel.setVisibility(View.VISIBLE);
                refusedToDonate.setVisibility(View.VISIBLE);
            }
            if(holder.beenToMalariaArea.equals(YesNo.YES)){
                beenToAMalariaAreaLabel.setVisibility(View.VISIBLE);
                beenToAMalariaArea.setVisibility(View.VISIBLE);
            }
            if(holder.mealOrSnack.equals(YesNo.NO)){
                mealOrSnackLabel.setVisibility(View.VISIBLE);
                mealOrSnack.setVisibility(View.VISIBLE);
            }
            if(holder.dangerousOccupation.equals(YesNo.YES)){
                dangerousOccupationLabel.setVisibility(View.VISIBLE);
                dangerousOccupation.setVisibility(View.VISIBLE);
            }
            if(holder.rheumaticFever.equals(YesNo.YES)){
                rheumaticFeverLabel.setVisibility(View.VISIBLE);
                rheumaticFever.setVisibility(View.VISIBLE);
            }
            if(holder.lungDisease.equals(YesNo.YES)){
                lungDiseaseLabel.setVisibility(View.VISIBLE);
                lungDisease.setVisibility(View.VISIBLE);
            }
            if(holder.cancer.equals(YesNo.YES)){
                cancerLabel.setVisibility(View.VISIBLE);
                cancer.setVisibility(View.VISIBLE);
            }
            if(holder.diabetes.equals(YesNo.YES)){
                diabetesLabel.setVisibility(View.VISIBLE);
                diabetes.setVisibility(View.VISIBLE);
            }
            if(holder.chronicMedicalCondition.equals(YesNo.YES)){
                chronicMedicalConditionLabel.setVisibility(View.VISIBLE);
                chronicMedicalCondition.setVisibility(View.VISIBLE);
            }
            if(holder.beenToDentist.equals(YesNo.YES)){
                beenToDentistLabel.setVisibility(View.VISIBLE);
                beenToDentist.setVisibility(View.VISIBLE);
            }
            if(holder.takenAntibiotics.equals(YesNo.YES)){
                takenAntibioticsLabel.setVisibility(View.VISIBLE);
                takenAntibiotics.setVisibility(View.VISIBLE);
            }
            if(holder.injection.equals(YesNo.YES)){
                injectionLabel.setVisibility(View.VISIBLE);
                injection.setVisibility(View.VISIBLE);
            }
            if(holder.beenIll.equals(YesNo.YES)){
                beenIllLabel.setVisibility(View.VISIBLE);
                beenIll.setVisibility(View.VISIBLE);
            }
            if(holder.receivedBloodTransfusion.equals(YesNo.YES)){
                receivedBloodTransfusionLabel.setVisibility(View.VISIBLE);
                receivedBloodTransfusion.setVisibility(View.VISIBLE);
            }
            if(holder.hivTest.equals(YesNo.YES)){
                hivTestLabel.setVisibility(View.VISIBLE);
                hivTest.setVisibility(View.VISIBLE);
            }
            if(holder.beenTestedForHiv.equals(YesNo.YES)){
                beenTestedForHivLabel.setVisibility(View.VISIBLE);
                beenTestedForHiv.setVisibility(View.VISIBLE);
            }
            if(holder.contactWithPersonWithYellowJaundice.equals(YesNo.YES)){
                contactWithPersonWithYellowJaundice.setVisibility(View.VISIBLE);
                contactWithPersonWithYellowJaundiceLabel.setVisibility(View.VISIBLE);
            }
            if(holder.accidentalExposureToBlood.equals(YesNo.YES)){
                accidentalExposure.setVisibility(View.VISIBLE);
                accidentalExposureLabel.setVisibility(View.VISIBLE);
            }
            if(holder.beenTattooedOrPierced.equals(YesNo.YES)){
                beenTattooedLabel.setVisibility(View.VISIBLE);
                beenTattooed.setVisibility(View.VISIBLE);
            }
            if(holder.injectedWithIllegalDrugs.equals(YesNo.YES)){
                injectedWithIllegalDrugsLabel.setVisibility(View.VISIBLE);
                injectedWithIllegalDrugs.setVisibility(View.VISIBLE);
            }
            if(holder.sexWithSomeoneWithUnknownBackground.equals(YesNo.YES)){
                sexWithSomeoneWithUnknownBackgroundLabel.setVisibility(View.VISIBLE);
                sexWithSomeoneWithUnknownBackground.setVisibility(View.VISIBLE);
            }
            if(holder.exchangedMoneyForSex.equals(YesNo.YES)){
                exchangedMoneyForSexLabel.setVisibility(View.VISIBLE);
                exchangedMoneyForSex.setVisibility(View.VISIBLE);
            }
            if(holder.trueForSexPartner.equals(YesNo.YES)){
                trueForSexPartnerLabel.setVisibility(View.VISIBLE);
                trueForSexPartner.setVisibility(View.VISIBLE);
            }
            if(holder.sufferedFromSTD.equals(YesNo.YES)){
                sufferedFromSTDLabel.setVisibility(View.VISIBLE);
                sufferedFromSTD.setVisibility(View.VISIBLE);
            }
            if(holder.contactWithPersonWithHepatitisB.equals(YesNo.YES)){
                monogamousRelationshipLabel.setVisibility(View.VISIBLE);
                monogamousRelationship.setVisibility(View.VISIBLE);
            }
            if(holder.sufferedFromNightSweats.equals(YesNo.YES)){
                sufferedFromNightSweats.setVisibility(View.VISIBLE);
                sufferedFromNightSweatsLabel.setVisibility(View.VISIBLE);
            }
            if(holder.victimOfSexualAbuse.equals(YesNo.YES)){
                victimOfSexualAbuseLabel.setVisibility(View.VISIBLE);
                victimOfSexualAbuse.setVisibility(View.VISIBLE);
            }
        }else {
            item = new Donor();
        }

        setSupportActionBar(createToolBar("NBSZ"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        //Intent intent = new Intent(this, NurseStep1Activity.class);
        Intent intent = new Intent(this, DeclarationFinalActivity.class);
        intent.putExtra("holder", holder);
        intent.putExtra("counsellor", counsellor);
        intent.putExtra("donorNumber", donorNumber);
        startActivity(intent);
        finish();
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, RiskAssessmentStep6Activity.class);
        intent.putExtra("holder", holder);
        intent.putExtra("counsellor", counsellor);
        intent.putExtra("donorNumber", donorNumber);
        startActivity(intent);
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
