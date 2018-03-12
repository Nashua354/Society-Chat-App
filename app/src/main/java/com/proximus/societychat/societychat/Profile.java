package com.proximus.societychat.societychat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.proximus.societychat.societychat.data.DManager;
import com.proximus.societychat.societychat.mDrawable.MDrawable;

import java.util.Random;
import java.util.Vector;

public class Profile extends AppCompatActivity {
ImageView pimage;
DManager dm;
TextView tv_email, name, dob, phno, desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tv_email = (TextView)findViewById(R.id.profile_emailtv);
        name = (TextView)findViewById(R.id.profile_nametv);
        dob = (TextView)findViewById(R.id.profile_dobtv);
        phno = (TextView)findViewById(R.id.profile_phnotv);
        desc = (TextView)findViewById(R.id.profile_decvtv);
        pimage = (ImageView)findViewById(R.id.profile_image);
        final DManager dm = new DManager(this, "db_main.db", null, 1);
        this.dm = dm;
        tv_email.setText(dm.get_setting("account_email"));
        get_profile(dm.get_setting("account_pic_url"));

        FloatingActionButton fb = (FloatingActionButton)findViewById(R.id.profile_fab),
        fbdet = (FloatingActionButton)findViewById(R.id.profile_fabdet);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MDrawable.AlertDialogForm(Profile.this){
                    @Override
                    public void onSubmit(Vector<String> values) {
                        Intent i = new Intent(Profile.this, Splash.class);
                        i.putExtra("logout", "yes");
                        startActivity(i);
                    }
                }.build("Sure logout?", "Logout").show();
            }
        });
        fbdet=(FloatingActionButton)findViewById(R.id.profile_fabdet);
        fbdet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MDrawable.AlertDialogForm(Profile.this){
                    @Override
                    public void onSubmit(Vector<String> values) {
                        dm.save_setting("account_display_name", values.get(0));
                        dm.save_setting("account_dob", values.get(1));
                        if(!dm.get_setting("account_phone").equals(values.get(2)))
                        {
                            change_phone(values.get(2));
                        }
                        dm.save_setting("account_desc", values.get(3));
                        DManager.Log(values.get(0)+" "+dm.get_setting("account_display_name"));
                        updateVals();
                    }
                }
                        .addItem(InputType.TYPE_CLASS_TEXT, "Name :", dm.get_setting("account_display_name"))
                        .addItem(InputType.TYPE_CLASS_DATETIME, "DOB :", dm.get_setting("account_dob"))
                        .addItem(InputType.TYPE_CLASS_PHONE, "Phone No :", dm.get_setting("account_phone"))
                        .addItem(InputType.TYPE_CLASS_TEXT, "Description...", dm.get_setting("account_desc"))
                        .build("Update", "Submit").show();
            }
        });
        updateVals();

    }
    private void updateVals(){
        DManager dm = new DManager(this, "db_main.db", null, 1);
        name.setText(dm.get_setting("account_display_name"));
        dob.setText(dm.get_setting("account_dob"));
        phno.setText(dm.get_setting("account_phone"));
        desc.setText(dm.get_setting("account_desc"));

    }
    private void get_profile(String url)
    {
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(
                new ImageRequest(
                        url,
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                pimage.setImageBitmap(response);
                            }
                        },
                        0, 0, null,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                DManager.Log(error.getMessage());
                            }
                        }
                )
        );
    }
    private void change_phone(final String newNumber)
    {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            new  MDrawable.AlertDialogForm(this){

            }.build("Please allow to sms permissions to verify your number by changing the settings", "Ok").show();
            return;
        }
        /*Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

        if (cursor.moveToFirst()) { // must check the result to prevent exception
            do {
                String msgData = "";
                for(int idx=0;idx<cursor.getColumnCount();idx++)
                {
                    msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx);
                }
                // use msgData
                DManager.Log(msgData);
            } while (cursor.moveToNext());
        } else {
            // empty box, no SMS
        }*/

        final SmsManager manager = SmsManager.getDefault();
        Random rnd = new Random();
        final int r = rnd.nextInt(9000)+1000;
        manager.sendTextMessage(newNumber, null, "Please enter this verification code:"+r ,null, null);
        MDrawable.AlertDialogForm form = new MDrawable.AlertDialogForm(this){
            @Override
            public void onSubmit(Vector<String> values) {
                if (values.get(0)!=null)
                {
                    if(values.get(0).equals(""+r))
                    {
                        dm.save_setting("account_phone", newNumber);
                    }
                }
            }
        };
        form.addItem(InputType.TYPE_CLASS_NUMBER, "Enter the verification code sent, while we try to read it ourself");
        form.build("Verify number", "Verify").show();


    }
}
