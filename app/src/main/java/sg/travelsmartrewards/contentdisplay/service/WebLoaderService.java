package sg.travelsmartrewards.contentdisplay.service;

import android.app.FragmentManager;
import android.app.IntentService;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import sg.travelsmartrewards.contentdisplay.MainActivity;
import sg.travelsmartrewards.contentdisplay.R;
import sg.travelsmartrewards.contentdisplay.helper.Constants;

/**
 * Created by Rayhan on 12/8/2014.
 */
public class WebLoaderService extends IntentService {

    public WebLoaderService() {
        super("WebLoaderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Intent localIntent = new Intent(Constants.ACTION_RESPONSE);
        LocalBroadcastManager.getInstance(WebLoaderService.this).sendBroadcast(
                localIntent);
    }
}
