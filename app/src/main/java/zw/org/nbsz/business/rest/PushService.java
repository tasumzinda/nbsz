package zw.org.nbsz.business.rest;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import zw.org.nbsz.business.domain.*;
import zw.org.nbsz.business.util.AppUtil;
import zw.org.nbsz.business.util.DateUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tasu on 9/13/17.
 */
public class PushService extends IntentService {

    public static final String NOTIFICATION = "zw.org.nbsz";
    private Context context = this;
    public static final String RESULT = "result";

    public PushService(){
        super("PushService");
    }

    @Override
    protected void onHandleIntent(Intent intent){
        int result = Activity.RESULT_OK;
        try{
            for(Donor item : Donor.findByPushed()){
                Donor res = save(run(AppUtil.getPushDonorUrl(context, item.server_id), item), item);
                /*for(Donation donation : Donation.findByDonor(item)){
                    zw.org.nbsz.business.util.Log.d("Donation", AppUtil.createGson().toJson(donation));
                    donation.person = res;
                    donation.save();
                    zw.org.nbsz.business.util.Log.d("Saved Donation", AppUtil.createGson().toJson(donation));
                }
                for(Offer offer : Offer.findByDonor(item)){
                    zw.org.nbsz.business.util.Log.d("Offer", AppUtil.createGson().toJson(offer));
                    offer.person = res;
                    offer.save();
                    zw.org.nbsz.business.util.Log.d("Saved Offer", AppUtil.createGson().toJson(offer));
                }
                for(DonationStats d : DonationStats.findByDonor(item)){
                    zw.org.nbsz.business.util.Log.d("Stats", AppUtil.createGson().toJson(d));
                    d.person = res;
                    d.save();
                    zw.org.nbsz.business.util.Log.d("Saved Stats", AppUtil.createGson().toJson(d));
                }
                if(res != null){
                    item.delete();
                    Log.d("Deleted stats", AppUtil.createGson().toJson(item));
                }*/

            }
            /*for(Donation donation : Donation.getAll()){
                Long out = Long.parseLong(run(AppUtil.getPushDonationUrl(context, donation.serverId), donation)) ;
                if(out == 1L){
                    donation.delete();
                    Log.d("Deleted donation", donation.donationDate);
                }
            }
            for(Offer offer : Offer.getAll()){
                Long out = Long.parseLong(run(AppUtil.getPushOfferUrl(context, offer.serverId), offer)) ;
                if(out == 1L){
                    for(OfferIncentiveContract m : OfferIncentiveContract.findByOffer(offer)){
                        m.delete();
                        Log.d("Deleted contract", offer.donationNumber);
                    }
                    offer.delete();
                    Log.d("Deleted offer", offer.donationNumber);
                }
            }
            for(DonationStats d : DonationStats.getAll()){
                zw.org.nbsz.business.util.Log.d("Stats1", AppUtil.createGson().toJson(d));
                Long out = Long.parseLong(run(AppUtil.getPushDonationStatsUrl(context, d.server_id), d)) ;
                if(out == 1L){
                    d.delete();
                    Log.d("Deleted stats", d.bloodPressure);
                }
            }*/
        }catch (Exception ex){
            ex.printStackTrace();
            result = Activity.RESULT_CANCELED;
        }
        publishResults(result);
    }

    private void publishResults(int result) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }

    public Donor save(String data, Donor item){
        Donor fromServer = null;
        try{
            JSONObject object = new JSONObject(data);
            fromServer = Donor.fromJSON(object);
            fromServer.isNew = 0;
            fromServer.pushed = 0;
            fromServer.save();
            Log.d("Saved donor", AppUtil.createGson().toJson(fromServer));
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return fromServer;
    }

    private String run(HttpUrl httpUrl, Donor form) {
        OkHttpClient client = new OkHttpClient();
        client = AppUtil.connectionSettings(client);
        client = AppUtil.getUnsafeOkHttpClient(client);
        client = AppUtil.createAuthenticationData(client, context);
        form.genderValue = form.gender.getName();
        form.dob = DateUtil.getStringFromDate(form.dateOfBirth);
        if(form.isNew == 1){
            form.server_id = null;
        }
        form.specialNotes = SpecialNotes.findByDonor(form);
        String json =  AppUtil.createGson().toJson(form);
        return AppUtil.getResponeBody(client, httpUrl, json);
    }

    private String run(HttpUrl httpUrl, Donation form) {
        OkHttpClient client = new OkHttpClient();
        client = AppUtil.connectionSettings(client);
        client = AppUtil.getUnsafeOkHttpClient(client);
        client = AppUtil.createAuthenticationData(client, context);
        form.donationDate = DateUtil.getStringFromDate(form.date);
        String json =  AppUtil.createGson().toJson(form);
        return AppUtil.getResponeBody(client, httpUrl, json);
    }
    private String run(HttpUrl httpUrl, Offer form) {
        OkHttpClient client = new OkHttpClient();
        client = AppUtil.connectionSettings(client);
        client = AppUtil.getUnsafeOkHttpClient(client);
        client = AppUtil.createAuthenticationData(client, context);
        form.offer = DateUtil.getStringFromDate(form.offerDate);
        if(form.deferDate != null){
            form.defer = DateUtil.getStringFromDate(form.deferDate);
        }
        form.incentives = Incentive.findByOffer(form);
        String json =  AppUtil.createGson().toJson(form);
        return AppUtil.getResponeBody(client, httpUrl, json);
    }

    private String run(HttpUrl httpUrl, DonationStats form) {
        OkHttpClient client = new OkHttpClient();
        client = AppUtil.connectionSettings(client);
        client = AppUtil.getUnsafeOkHttpClient(client);
        client = AppUtil.createAuthenticationData(client, context);
        String json =  AppUtil.createGson().toJson(form);
        return AppUtil.getResponeBody(client, httpUrl, json);
    }
}
