package capstone.sda.com.literatures.Utils;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Literatures extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
            //    .name("literatures.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
