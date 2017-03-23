package com.frosquivel.examplemagicalcamera.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.frosquivel.examplemagicalcamera.ConnectionBaaS.BackendlessImp;
import com.frosquivel.examplemagicalcamera.R;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fabian on 19/03/2017.
 * This class permit that the user save a comment for improve the app and the library
 */

public class FeedbacksActivity extends AppCompatActivity {

    private Button btnSendFeedBack;
    private EditText txtMessage;
    private Spinner spFeedBackCategory;
    private Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        activity =  this;

        btnSendFeedBack = (Button) findViewById(R.id.btnSendFeedBack);
        txtMessage = (EditText) findViewById(R.id.txtMessage);
        spFeedBackCategory = (Spinner) findViewById(R.id.spFeedBackCategory);

        btnSendFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtMessage.getText().toString().equals("") && spFeedBackCategory.getSelectedItem().toString() != ""){
                    saveNewContact();
                }else{
                    Toast.makeText(FeedbacksActivity.this,getString(R.string.feedback_not_save),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void saveNewContact()
    {
        HashMap contact = new HashMap();
        contact.put( "FeedBackMessage", txtMessage.getText().toString());
        contact.put( "FeedBackType", spFeedBackCategory.getSelectedItem().toString());

        Backendless.Persistence.of( "FeedBacks" ).save( contact, new AsyncCallback<Map>() {
            public void handleResponse( Map response )
            {
                if(response != null){
                    Toast.makeText(FeedbacksActivity.this, activity.getString(R.string.feedback_save_message),Toast.LENGTH_LONG).show();
                }
            }

            public void handleFault( BackendlessFault fault )
            {
                if(fault != null){
                    Toast.makeText(FeedbacksActivity.this, fault.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
