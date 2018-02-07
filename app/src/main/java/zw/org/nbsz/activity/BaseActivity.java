package zw.org.nbsz.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.*;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import com.android.volley.*;
import it.sephiroth.android.library.widget.HListView;
import zw.org.nbsz.R;
import zw.org.nbsz.business.domain.*;
import zw.org.nbsz.business.rest.DownloadDonor;
import zw.org.nbsz.business.rest.PushPullService;
import zw.org.nbsz.business.util.AppUtil;
import zw.org.nbsz.business.util.DateUtil;
import zw.org.nbsz.business.util.Log;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseActivity extends AppCompatActivity {

    public Context context = this;
    public Menu menu;
    public Toolbar toolbar;
    ProgressDialog progressDialog;


    public Toolbar createToolBar(String title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        return toolbar;
    }

    public void lockScreenOrientation() {
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    public void unlockScreenOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    public void updateLabel(Date date, EditText editText) {
        editText.setText(DateUtil.getStringFromDate(date));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        this.menu = menu;
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent intent = null;

        if (android.R.id.home == id) {
            onBackPressed();
        }

        /*if (id == R.id.action_home) {
            intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;
        }*/
        if (id == R.id.action_refresh) {
            syncAppData();
            return true;
        }
        if (id == R.id.action_list) {
            intent = new Intent(context, DonorListActivity.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item_down = menu.findItem(R.id.action_refresh);
        item_down.setVisible(true);
        return true;
    }

    public boolean validate(EditText[] fields) {
        boolean isValid = true;
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getText().toString().isEmpty()) {
                fields[i].setError(getResources().getString(R.string.required_field_error));
                isValid = false;
            } else {
                fields[i].setError(null);
            }
        }
        return isValid;
    }

    public boolean validateListView(HListView[] fields) {
        boolean isValid = true;
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getCheckedItemCount() == 0) {
                AppUtil.createShortNotification(this, "Sorry, response is required");
                isValid = false;
            }
        }
        return isValid;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(PushPullService.NOTIFICATION));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }


    public void onExit() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).setNegativeButton("no", null).show();
    }


    public boolean checkDateFormat(String text) {
        if (text == null || !text.matches("([0-9]{1,2})/([0-9]{1,2})/([0-9]{4})"))
            return false;
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        df.setLenient(false);
        try {
            df.parse(text);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

    public boolean validateStrings(String name) {
        if (name.trim().matches("^([a-zA-Z])+")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateNationalId(String nationalId) {
        if (nationalId.trim().matches("([0-9]{2})-([0-9]{6,7})([A-Z])([0-9]{2})")) {
            return true;
        } else {
            return false;
        }
    }

    public void syncAppData() {
        if (AppUtil.isNetworkAvailable(context)) {
            progressDialog = ProgressDialog.show(this, "Please wait", "Syncing with Server...", true);
            progressDialog.setCancelable(false);
            Intent intent = new Intent(this, PushPullService.class);
            startService(intent);
        } else {
            AppUtil.createShortNotification(this, "No Internet, Check Connectivity!");
        }
    }

    public void downloadDonors() {
        if (AppUtil.isNetworkAvailable(context)) {
            progressDialog = ProgressDialog.show(this, "Please wait", "Downloading donor data...", true);
            progressDialog.setCancelable(false);
            Intent intent = new Intent(this, DownloadDonor.class);
            startService(intent);
        } else {
            AppUtil.createShortNotification(this, "No Internet, Check Connectivity!");
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            updateView();
            if (bundle != null) {
                int resultCode = bundle.getInt(PushPullService.RESULT);
                if (resultCode == RESULT_OK) {
                    createNotificationDataSync("Sync Success", "Application Data Updated");
                    AppUtil.createShortNotification(context, "Application Data Updated");
                } else {
                    createNotificationDataSync("Sync Fail", "Incomplete Application Data");
                    AppUtil.createShortNotification(context, "Incomplete Application Data");
                }
            }
        }
    };

    public void createNotificationDataSync(String title, String msg) {
        Notification notification = new Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(msg).setSmallIcon(R.mipmap.logo)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    public void updateView() {

    }

    public void logout() {
        AppUtil.removePreferences(this);
        User user = User.getLoggedIn();
        user.logged_in = 0;
        user.save();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void handleVolleyError(VolleyError error){
        if(error instanceof TimeoutError || error instanceof NoConnectionError){
            AppUtil.createLongNotification(getApplicationContext(), "Connection to server has failed");
        }else if(error instanceof AuthFailureError){
            AppUtil.createLongNotification(getApplicationContext(),"Authentication from server failed");
        }else if(error instanceof NetworkError){
            AppUtil.createLongNotification(getApplicationContext(), "Network error");
        }else if(error instanceof ServerError){
            AppUtil.createLongNotification(getApplicationContext(), "An internal error occurred at the server");
        }else if(error instanceof ParseError){
            AppUtil.createLongNotification(getApplicationContext(), "Error. Failed to parse the network response");
        }
    }

    public void sendMessage(final String msg) {

        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //Replace below IP with the IP of that device in which server socket open.
                    //If you change port then change the port number in the server side code also.
                    InetAddress address = InetAddress.getByName("192.168.43.143");
                    Socket s = new Socket(address, 8080);
                    OutputStream out = s.getOutputStream();
                    PrintWriter output = new PrintWriter(out);
                    output.println(msg);
                    //output.write("\r\n");
                    output.flush();
                    BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    final String st = input.readLine();
                    Log.d("Response", "Test: " + st);
                    /*handler.post(new Runnable() {
                        @Override
                        public void run() {

                            String s = mTextViewReplyFromServer.getText().toString();
                            if (st.trim().length() != 0)
                                mTextViewReplyFromServer.setText(s + "\nFrom Server : " + st);
                        }
                    });*/

                    output.close();
                    out.close();
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}