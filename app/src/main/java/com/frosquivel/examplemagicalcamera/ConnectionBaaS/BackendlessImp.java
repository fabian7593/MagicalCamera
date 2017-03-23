package com.frosquivel.examplemagicalcamera.ConnectionBaaS;

import android.content.Context;
import android.view.View;

import com.backendless.Backendless;
import com.backendless.IDataStore;
import com.backendless.async.callback.BackendlessCallback;
import com.frosquivel.examplemagicalcamera.R;
import com.frosquivel.examplemagicalcamera.Utils.Utils;

import static weborb.util.ThreadContext.context;

/**
 * Created by Fabian on 19/03/2017.
 * This class connect the app with backendless (please skip the key secret lol, just kiding ;))
 */

public class BackendlessImp {

    public static final String APPLICATION_ID = "88D2DA83-EE38-BACB-FFE1-DB95B70EA400";
    public static final String SECRET_KEY = "DFDB97D7-40F5-EABE-FF43-8FD53AC9D900";
    public static final String VERSION = "v1";

    public static void backendlessInit(Context context) {
        if (BackendlessImp.APPLICATION_ID.equals("") || BackendlessImp.SECRET_KEY.equals("") || BackendlessImp.VERSION.equals("")) {
            return;
        }

        com.backendless.Backendless.initApp(context, BackendlessImp.APPLICATION_ID, BackendlessImp.SECRET_KEY, BackendlessImp.VERSION);
    }
}
