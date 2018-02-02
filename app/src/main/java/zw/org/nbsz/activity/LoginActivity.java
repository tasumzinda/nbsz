package zw.org.nbsz.activity;

import android.app.ProgressDialog;
import android.content.*;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.Centre;
import zw.org.nbsz.business.domain.Donor;
import zw.org.nbsz.business.domain.User;
import zw.org.nbsz.business.rest.PushPullService;
import zw.org.nbsz.business.util.AppUtil;
import zw.org.nbsz.business.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tasu on 7/12/17.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private EditText userNameField;
    private EditText passwordField;
    private EditText urlField;
    private Button button;
    private EditText[] fields;
    ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);
        if(AppUtil.isLoggedIn(this)){
            if(Donor.getAll().size() == 0){
                Intent intent = new Intent(LoginActivity.this, SelectUserActivity.class);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(LoginActivity.this, DonatedBloodActivity.class);
                startActivity(intent);
                finish();
            }

        }
        userNameField = (EditText) findViewById(R.id.username);
        passwordField = (EditText) findViewById(R.id.password);
        urlField = (EditText) findViewById(R.id.url);
        urlField.setText(AppUtil.getBaseUrl(this));
        urlField.setEnabled(false);
        button = (Button) findViewById(R.id.login);
        button.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        fields = new EditText[] {userNameField, passwordField, urlField};

    }
     private void loginRemote(){
        String URL = urlField.getText().toString()+ "login/get-user?userName=" + userNameField.getText().toString();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.GET, URL, null, new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        boolean hasLoggedIn = sharedPreferences.contains(AppUtil.LOGGED_IN);
                        if(! hasLoggedIn){
                            progressDialog.setTitle("Signing in....please wait");
                            progressDialog.setCancelable(false);
                            try{
                                JSONObject object = response.getJSONObject("centre");
                                Centre centre = Centre.fromJSON(object);
                                if(Centre.findById(centre.server_id) == null){
                                    centre.save();
                                }
                            }catch (JSONException ex){
                                ex.printStackTrace();
                            }
                            User user = User.fromJSON(response);
                            User current = User.findById(user.serverId);

                            if(current == null){
                                user.logged_in = 1;
                                user.save();
                            }else{
                                current.logged_in = 1;
                                current.save();
                            }
                            AppUtil.savePreferences(getApplicationContext(), AppUtil.LOGGED_IN, Boolean.TRUE);
                            AppUtil.savePreferences(getApplicationContext(), AppUtil.USERNAME, userNameField.getText().toString());
                            AppUtil.savePreferences(getApplicationContext(), AppUtil.PASSWORD, passwordField.getText().toString());
                            AppUtil.savePreferences(getApplicationContext(), AppUtil.BASE_URL, urlField.getText().toString());
                            progressDialog.hide();
                            Intent intent = new Intent(LoginActivity.this, SelectUserActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppUtil.createShortNotification(getApplicationContext(), "Incorrect username or password");
                    }
                }){

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put(
                        "Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", userNameField.getText().toString(), passwordField.getText().toString()).getBytes(), Base64.DEFAULT)));
                params.put("Content-Type", "application/json; charset=UTF-8");

                return params;
            }
        };
        AppUtil.getInstance(getApplicationContext()).getRequestQueue().add(jsObjRequest);
    }


    public void onClick(View view){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if(view.getId() == button.getId()){
            if(validate(fields)){
                if(AppUtil.isNetworkAvailable(getApplicationContext())){
                    loginRemote();
                }else if(sharedPreferences.contains("USERNAME")){
                    if(AppUtil.getUsername(this).equals(userNameField.getText().toString()) && AppUtil.getPassword(this).equals(passwordField.getText().toString())){
                        AppUtil.savePreferences(getApplicationContext(), AppUtil.LOGGED_IN, Boolean.TRUE);
                        Intent intent = new Intent(context, SelectUserActivity.class);
                        startActivity(intent);
                    }else{
                        AppUtil.createShortNotification(getApplicationContext(), "Wrong username or password");
                    }

                }else{
                    AppUtil.createShortNotification(getApplicationContext(), "No internet connection");
                }
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onBackPressed(){
        onExit();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        }
    };

    @Override
    public void onResume(){
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(PushPullService.NOTIFICATION));
    }

    @Override
    public void onPause(){
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    public void onStop(){
        super.onStop();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem refresh = menu.findItem(R.id.action_refresh);
        refresh.setVisible(false);
        MenuItem list = menu.findItem(R.id.action_list);
        list.setVisible(false);
        MenuItem logout = menu.findItem(R.id.action_logout);
        logout.setVisible(false);
        return true;
    }
}
