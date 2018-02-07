package zw.org.nbsz.business.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.*;
import zw.org.nbsz.activity.ConsentToUpdateDetailsActivity;
import zw.org.nbsz.business.domain.Centre;
import zw.org.nbsz.business.domain.CollectSite;
import zw.org.nbsz.business.domain.User;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by tasu on 5/4/17.
 */
public class AppUtil {

    public static String LOGGED_IN = "LOGGED_IN";
    public static String USERNAME = "USERNAME";
    public static String PASSWORD = "PASSWORD";
    public static String BASE_URL = "http://196.2.73.10:8084/nbsz-mobile/rest/mobile/";
    //public static String BASE_URL = "http://192.168.1.140:8080";
    public static String NAME = "NAME";
    private static AppUtil appInstance;
    private static Context mContext;
    private static Gson gson;
    private RequestQueue requestQueue;
    public static String DATE_FORMAT = "yyyy-MM-dd";
    public static MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private AppUtil(Context context) {
        mContext = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized AppUtil getInstance(Context context) {
        if (appInstance == null) {
            appInstance = new AppUtil(context);
        }
        return appInstance;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static void createLongNotification(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void createShortNotification(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void createSnackBarShort(View view, String mgs) {
        Snackbar.make(view, mgs, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    public static void createSnackBarLong(View view, String mgs) {
        Snackbar.make(view, mgs, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public static Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().setDateFormat("dd/MM/yyyy").serializeNulls().create();
        return gson;
    }

    public static void savePreferences(Context context, String key, Boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void savePreferences(Context context, String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void savePreferences(Context context, String key, Long value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static boolean isLoggedIn(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(LOGGED_IN, Boolean.FALSE);
    }

    public static String getUsername(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(USERNAME, "USERNAME");
    }

    public static String getPassword(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(PASSWORD, "PASSWORD");
    }

    public static String getBaseUrl(Context context){
        return BASE_URL;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mContext);
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

    public static void removePreferences(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        //sharedPreferences.edit().clear().commit();
        sharedPreferences.edit().remove("LOGGED_IN").commit();
    }

    public static HttpUrl getCentresUrl(Context context) {
        return HttpUrl.parse(getBaseUrl(context).concat("static/centre")).newBuilder()
                .build();
    }

    public static HttpUrl getProfessionsUrl(Context context) {
        return HttpUrl.parse(getBaseUrl(context).concat("static/profession")).newBuilder()
                .build();
    }

    public static HttpUrl getMaritalStatusUrl(Context context) {
        return HttpUrl.parse(getBaseUrl(context).concat("static/marital-status")).newBuilder()
                .build();
    }

    public static HttpUrl getDeferredReasonUrl(Context context) {
        return HttpUrl.parse(getBaseUrl(context).concat("static/deferred-reason")).newBuilder()
                .build();
    }

    public static HttpUrl getBankStaffUrl(Context context){
        return HttpUrl.parse(getBaseUrl(context).concat("static/bank-staff")).newBuilder().build();
    }

    public static HttpUrl getCollectSiteUrl(Context context){
        return HttpUrl.parse(getBaseUrl(context).concat("static/collect-site")).newBuilder()
                .setQueryParameter("id", String.valueOf(User.getLoggedIn().centre.server_id))
                .build();
    }

    public static HttpUrl getDonationTypeUrl(Context context){
        return HttpUrl.parse(getBaseUrl(context).concat("static/donation-type")).newBuilder().build();
    }

    public static HttpUrl getDonorTypeUrl(Context context){
        return HttpUrl.parse(getBaseUrl(context).concat("static/donor-type")).newBuilder().build();
    }

    public static HttpUrl getIncentiveUrl(Context context){
        return HttpUrl.parse(getBaseUrl(context).concat("static/incentive")).newBuilder().build();
    }

    public static HttpUrl getSpecialNotesUrl(Context context){
        return HttpUrl.parse(getBaseUrl(context).concat("static/special-notes")).newBuilder().build();
    }

    public static HttpUrl getUserUrl(Context context){
        return HttpUrl.parse(getBaseUrl(context).concat("static/user")).newBuilder()
                .setQueryParameter("id", String.valueOf(User.getLoggedIn().centre.server_id))
                .build();
    }

    /*public static HttpUrl getPushDonorUrl(Context context, Long id){
        return HttpUrl.parse(getBaseUrl(context).concat("form/donor")).newBuilder()
                .setQueryParameter("id", String.valueOf(id))
                .build();
    }*/

    public static HttpUrl getPushDonorUrl(Context context, Long id){
        return HttpUrl.parse(getBaseUrl(context)).newBuilder().build();
    }

    public static HttpUrl getPushDonationUrl(Context context, Long id){
        return HttpUrl.parse(getBaseUrl(context).concat("form/donation")).newBuilder()
                .setQueryParameter("id", String.valueOf(id))
                .build();
    }

    public static HttpUrl getPushOfferUrl(Context context, Long id){
        return HttpUrl.parse(getBaseUrl(context).concat("form/offer")).newBuilder()
                .setQueryParameter("id", String.valueOf(id))
                .build();
    }

    public static HttpUrl getPushDonationStatsUrl(Context context, Long id){
        return HttpUrl.parse(getBaseUrl(context).concat("form/donation-stats")).newBuilder()
                .setQueryParameter("id", String.valueOf(id))
                .build();
    }
    
    public static HttpUrl getDonorUrl(Context context){
        return HttpUrl.parse(getBaseUrl(context).concat("form/get-by-collect-site")).newBuilder()
                .setQueryParameter("id", String.valueOf(CollectSite.findByActive().serverId))
                .build();
    }

    public static OkHttpClient createAuthenticationData(OkHttpClient client, final Context context) {
        client.setAuthenticator(new Authenticator() {
            @Override
            public com.squareup.okhttp.Request authenticate(Proxy proxy, Response response) {
                String credential = Credentials.basic(AppUtil.getUsername(context), AppUtil.getPassword(context));
                return response.request().newBuilder()
                        .header("Authorization", credential)
                        .build();
            }

            @Override
            public com.squareup.okhttp.Request authenticateProxy(Proxy proxy, Response response) {
                return null; // Null indicates no attempt to authenticate.
            }
        });
        return client;
    }

    public static OkHttpClient getUnsafeOkHttpClient(OkHttpClient client) {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();


            client.setSslSocketFactory(sslSocketFactory);
            client.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return client;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static OkHttpClient connectionSettings(OkHttpClient client) {
        client.setConnectTimeout(3, TimeUnit.MINUTES);
        client.setReadTimeout(15, TimeUnit.MINUTES);
        client.setWriteTimeout(30, TimeUnit.SECONDS);
        return client;
    }

    public static String run(HttpUrl httpUrl, Context context) throws IOException {
        String result = "";
        OkHttpClient client = new OkHttpClient();
        client = AppUtil.connectionSettings(client);
        client = AppUtil.createAuthenticationData(client, context);
        client = AppUtil.getUnsafeOkHttpClient(client);
        Response response = null;
        try {
            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
                    .url(httpUrl)
                    .build();

            response = client.newCall(request).execute();

            if (AppUtil.responseCount(response) >= 3) {
                return "authentication_error";
            }

            result = response.body().string();
        } catch (SocketTimeoutException e) {
            result = "Server Unavailable - Try Again Later";
        }
        return result;

    }

    public static int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }

    public static String getStringDate(Date date) {
        if (date == null) {
            return "";
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(AppUtil.DATE_FORMAT);
            return sdf.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getResponeBody(OkHttpClient client, HttpUrl httpUrl, String json) {
        Response response = null;
        String result = "";
        try {
            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
                    .url(httpUrl)
                    .post(AppUtil.getPostBody(json))
                    .build();

            response = client.newCall(request).execute();

            if (AppUtil.responseCount(response) >= 3) {
                return "authentication_error";
            }
            result = response.body().string();
        } catch (SocketTimeoutException e) {
            result = "Server Unavailable - Try Again Later";
        } catch (IOException e) {
            e.printStackTrace();
            result = e.getMessage();
        }

        return result;
    }

    public static RequestBody getPostBody(String json) {
        RequestBody body = RequestBody.create(JSON, json);
        return body;
    }

    public void loginReset(){

    }
    public static String encryptSHA1(String paramString) {
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-1");
            byte[] arrayOfByte = localMessageDigest.digest(paramString.getBytes());
            StringBuffer localStringBuffer = new StringBuffer();

            for (int i = 0; i < arrayOfByte.length; i++) {
                int j = arrayOfByte[i];
                String str = Integer.toHexString(0xFF & j);
                str = "0" + str;
                localStringBuffer.append(str);
            }
            return localStringBuffer.toString().toUpperCase();
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return "";
    }


}
