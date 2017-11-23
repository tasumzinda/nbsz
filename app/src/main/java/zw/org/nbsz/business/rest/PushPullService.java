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
import zw.org.nbsz.business.domain.Donor;
import zw.org.nbsz.business.domain.DonationType;
import zw.org.nbsz.business.util.AppUtil;
import zw.org.nbsz.business.util.DateUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by tasu on 5/31/17.
 */
public class PushPullService extends IntentService {

    public static final String NOTIFICATION = "zw.org.nbsz";
    private Context context = this;
    public static final String RESULT = "result";

    public PushPullService(){
        super("PushPullService");
    }

    @Override
    protected void onHandleIntent(Intent intent){
        int result = Activity.RESULT_OK;
        for(HttpUrl url : getUrls()){
            try{
                if(url.equals(AppUtil.getCentresUrl(context))){
                    loadCentres(AppUtil.run(url, context));
                }
            }catch (IOException ex){
                ex.printStackTrace();
                result = Activity.RESULT_CANCELED;
            }
            try{
                if(url.equals(AppUtil.getProfessionsUrl(context))){
                    loadProfessions(AppUtil.run(url, context));
                }
            }catch (IOException ex){
                ex.printStackTrace();
                result = Activity.RESULT_CANCELED;
            }
            try{
                if(url.equals(AppUtil.getMaritalStatusUrl(context))){
                    loadMaritalStatus(AppUtil.run(url, context));
                }
            }catch (IOException ex){
                ex.printStackTrace();
                result = Activity.RESULT_CANCELED;
            }
            try{
                if(url.equals(AppUtil.getDeferredReasonUrl(context))){
                    loadDeferredReasons(AppUtil.run(url, context));
                }
            }catch (IOException ex){
                ex.printStackTrace();
                result = Activity.RESULT_CANCELED;
            }
            try{
                if(url.equals(AppUtil.getBankStaffUrl(context))){
                    loadBankStaff(AppUtil.run(url, context));
                }
            }catch (IOException ex){
                ex.printStackTrace();
                result = Activity.RESULT_CANCELED;
            }
            try{
                if(url.equals(AppUtil.getCollectSiteUrl(context))){
                    loadCollectSites(AppUtil.run(url, context));
                }
            }catch (IOException ex){
                ex.printStackTrace();
                result = Activity.RESULT_CANCELED;
            }
            try{
                if(url.equals(AppUtil.getDonationTypeUrl(context))){
                    loadDonationTypes(AppUtil.run(url, context));
                }
            }catch (IOException ex){
                ex.printStackTrace();
                result = Activity.RESULT_CANCELED;
            }
            try{
                if(url.equals(AppUtil.getDonorTypeUrl(context))){
                    loadDonorTypes(AppUtil.run(url, context));
                }
            }catch (IOException ex){
                ex.printStackTrace();
                result = Activity.RESULT_CANCELED;
            }
            try{
                if(url.equals(AppUtil.getUserUrl(context))){
                    loadUsers(AppUtil.run(url, context));
                }
            }catch (IOException ex){
                ex.printStackTrace();
                result = Activity.RESULT_CANCELED;
            }
            try{
                if(url.equals(AppUtil.getIncentiveUrl(context))){
                    loadIncentives(AppUtil.run(url, context));
                }
            }catch (IOException ex){
                ex.printStackTrace();
                result = Activity.RESULT_CANCELED;
            }
        }
        try{
            for(Donor item : Donor.findByPushed()){
                Donor res = save(run(AppUtil.getPushDonorUrl(context, item.server_id), item), item);
                for(Donation donation : Donation.findByDonor(item)){
                    zw.org.nbsz.business.util.Log.d("Donation", AppUtil.createGson().toJson(donation));
                    donation.person = res;
                    donation.save();
                    zw.org.nbsz.business.util.Log.d("Saved Donation", AppUtil.createGson().toJson(donation));
                   /* Log.d("Donation", res.donorNumber);
                    Long out = Long.parseLong(run(AppUtil.getPushDonationUrl(context, donation.serverId), donation)) ;
                    if(out == 1L){
                        donation.delete();
                        Log.d("Deleted donation", donation.person.firstName);
                    }*/
                }
                for(DonationStats d : DonationStats.findByDonor(item)){
                    zw.org.nbsz.business.util.Log.d("Stats", AppUtil.createGson().toJson(d));
                    d.person = res;
                    d.save();
                    zw.org.nbsz.business.util.Log.d("Saved Stats", AppUtil.createGson().toJson(d));
                    /*Long out = Long.parseLong(run(AppUtil.getPushDonationStatsUrl(context, d.server_id), d)) ;
                    if(out == 1L){
                        d.delete();
                        Log.d("Deleted stats", d.person.firstName);
                    }*/
                }
                if(res != null){
                    item.delete();
                    Log.d("Deleted stats", AppUtil.createGson().toJson(item));
                }

            }
            for(Donation donation : Donation.getAll()){
                Long out = Long.parseLong(run(AppUtil.getPushDonationUrl(context, donation.serverId), donation)) ;
                if(out == 1L){
                    donation.delete();
                    Log.d("Deleted donation", donation.person.firstName);
                }
            }
            for(DonationStats d : DonationStats.getAll()){
                zw.org.nbsz.business.util.Log.d("Stats1", AppUtil.createGson().toJson(d));
                Long out = Long.parseLong(run(AppUtil.getPushDonationStatsUrl(context, d.server_id), d)) ;
                if(out == 1L){
                    d.delete();
                    Log.d("Deleted stats", d.person.firstName);
                }
            }
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

    public List<HttpUrl> getUrls(){
        List<HttpUrl> list = new ArrayList<>();
        list.add(AppUtil.getCentresUrl(context));
        list.add(AppUtil.getProfessionsUrl(context));
        list.add(AppUtil.getMaritalStatusUrl(context));
        list.add(AppUtil.getDeferredReasonUrl(context));
        list.add(AppUtil.getBankStaffUrl(context));
        list.add(AppUtil.getCollectSiteUrl(context));
        list.add(AppUtil.getDonationTypeUrl(context));
        list.add(AppUtil.getDonorTypeUrl(context));
        list.add(AppUtil.getUserUrl(context));
        list.add(AppUtil.getIncentiveUrl(context));
        return list;
    }

    public String loadCentres(String data){
        String msg = "Centres synced";
        try{
            JSONArray array = new JSONArray(data);
            ArrayList<Centre> list = Centre.fromJSON(array);
            for(Centre item : list){
                Centre duplicateCheck = Centre.findById(item.server_id);
                if(duplicateCheck == null){
                    item.save();
                    Log.d("Saved centre", item.name);
                }
            }
        }catch (JSONException ex){
            ex.printStackTrace();
            msg = "Centres sync failed";
        }
        return msg;
    }

    public String loadProfessions(String data){
        String msg = "Professions synced";
        try{
            JSONArray array = new JSONArray(data);
            ArrayList<Profession> list = Profession.fromJSON(array);
            for(Profession item : list){
                Profession duplicateCheck = Profession.findById(item.server_id);
                if(duplicateCheck == null){
                    item.save();
                    Log.d("Saved profession", item.name);
                }
            }
        }catch(JSONException ex){
            ex.printStackTrace();
            msg = "Professions sync failed";
        }
        return msg;
    }

    public String loadMaritalStatus(String data){
        String msg = "Marital Status synced";
        try{
            JSONArray array = new JSONArray(data);
            ArrayList<MaritalStatus> list = MaritalStatus.fromJSON(array);
            for(MaritalStatus item : list){
                MaritalStatus duplicate = MaritalStatus.findById(item.server_id);
                if(duplicate == null){
                    item.save();
                    Log.d("Saved Marital Status", item.name);
                }
            }
        }catch (JSONException ex){
            ex.printStackTrace();
            msg = "Marital status sync failed";
        }
        return msg;
    }

    public String loadDeferredReasons(String data){
        String msg = "Deferred Reason synced";
        try{
            JSONArray array = new JSONArray(data);
            ArrayList<DeferredReason> list = DeferredReason.fromJSON(array);
            for(DeferredReason item : list){
                DeferredReason duplicate = DeferredReason.findById(item.server_id);
                if(duplicate == null){
                    item.save();
                    Log.d("Saved Deferred Reason", item.name);
                }
            }
        }catch (JSONException ex){
            ex.printStackTrace();
            msg = "Deferred Reason sync failed";
        }
        return msg;
    }

    public String loadBankStaff(String data){
        String msg = "Bank staff synced";
        try{
            JSONArray array = new JSONArray(data);
            ArrayList<BankStaff> list = BankStaff.fromJSON(array);
            for(BankStaff item : list){
                BankStaff duplicate = BankStaff.findById(item.serverId);
                if(duplicate == null){
                    item.save();
                    Log.d("Saved Bank staff", item.name);
                }
            }
        }catch (JSONException ex){
            ex.printStackTrace();
            msg = "Bank staff sync failed";
        }
        return msg;
    }

    public String loadCollectSites(String data){
        String msg = "Collect sites synced";
        try{
            JSONArray array = new JSONArray(data);
            ArrayList<CollectSite> list = CollectSite.fromJSON(array);
            for(CollectSite item : list){
                CollectSite duplicate = CollectSite.findById(item.serverId);
                if(duplicate == null){
                    item.save();
                    Log.d("Saved Collect Site", item.name);
                }
            }
        }catch (JSONException ex){
            ex.printStackTrace();
            msg = "Collect site sync failed";
        }
        return msg;
    }

    public String loadDonationTypes(String data){
        String msg = "Donation type synced";
        try{
            JSONArray array = new JSONArray(data);
            ArrayList<DonationType> list = DonationType.fromJSON(array);
            for(DonationType item : list){
                DonationType duplicate = DonationType.findById(item.serverId);
                if(duplicate == null){
                    item.save();
                    Log.d("Saved Donation type", item.name);
                }
            }
        }catch (JSONException ex){
            ex.printStackTrace();
            msg = "Donation type sync failed";
        }
        return msg;
    }

    public String loadDonorTypes(String data){
        String msg = "Donor type synced";
        try{
            JSONArray array = new JSONArray(data);
            ArrayList<DonorType> list = DonorType.fromJSON(array);
            for(DonorType item : list){
                DonorType duplicate = DonorType.findById(item.id);
                if(duplicate == null){
                    item.save();
                    Log.d("Saved Donor type", item.name);
                }
            }
        }catch (JSONException ex){
            ex.printStackTrace();
            msg = "Donor type sync failed";
        }
        return msg;
    }

    public String loadUsers(String data){
        String msg = "Users synced";
        try{
            JSONArray array = new JSONArray(data);
            ArrayList<User> list = User.fromJSON(array);
            for(User item : list){
                User duplicate = User.findById(item.serverId);
                if(duplicate == null){
                    item.save();
                    Log.d("Saved user", item.name);
                }
            }
        }catch (JSONException ex){
            ex.printStackTrace();
            msg = "User sync failed";
        }
        return msg;
    }

    public String loadIncentives(String data){
        String msg = "Incentives synced";
        try{
            JSONArray array = new JSONArray(data);
            ArrayList<Incentive> list = Incentive.fromJSON(array);
            for(Incentive item : list){
                Incentive duplicate = Incentive.findById(item.serverId);
                if(duplicate == null){
                    item.save();
                    Log.d("Saved incentive", item.name);
                }
            }
        }catch (JSONException ex){
            ex.printStackTrace();
            msg = "Incentive sync failed";
        }
        return msg;
    }

    public String loadDonors(String data){
        String msg = "Donor synced";
        try{
            JSONArray array = new JSONArray(data);
            ArrayList<Donor> list = Donor.fromJSON(array);
            for(Donor item : list){
                Donor duplicate = Donor.findByDonorNumber(item.donorNumber);
                if(duplicate == null){
                    item.save();
                    Log.d("Donor", item.donorNumber);
                }
            }
        }catch (JSONException ex){
            ex.printStackTrace();
            msg = "Donor sync failed";
        }
        return msg;
    }

    public Donor save(String data, Donor item){
        Donor fromServer = null;
        try{
            JSONObject object = new JSONObject(data);
            /*item.donorNumber = object.getString("donorNumber");
            item.server_id = object.getLong("id");
            item.isNew = 0;
            item.pushed = 0;*/
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
        //form.entry = DateUtil.getStringFromDate(form.entryDate);
        if(form.isNew == 1){
            form.server_id = null;
        }
        String json =  AppUtil.createGson().toJson(form);
        return AppUtil.getResponeBody(client, httpUrl, json);
    }

    public Donation save(String data, Donation item){
        try{
            Log.d("Result", data);
            Long id = Long.valueOf(data);
            item.serverId = id;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return item;
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

    private String run(HttpUrl httpUrl, DonationStats form) {
        OkHttpClient client = new OkHttpClient();
        client = AppUtil.connectionSettings(client);
        client = AppUtil.getUnsafeOkHttpClient(client);
        client = AppUtil.createAuthenticationData(client, context);
        String json =  AppUtil.createGson().toJson(form);
        return AppUtil.getResponeBody(client, httpUrl, json);
    }
}
