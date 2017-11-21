package zw.org.nbsz.business.util;
import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.activeandroid.app.Application;
import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by tasu on 5/4/17.
 */
public class NSBZ extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        Configuration configuration = new Configuration.Builder(this).create();
        JodaTimeAndroid.init(this);
        ActiveAndroid.initialize(configuration);
    }

    @Override
    public void onTerminate(){
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    /*@Override
    protected void attachBaseContext(Context context){
        super.attachBaseContext(context);
        MultiDex.install(context);
    }*/
}
