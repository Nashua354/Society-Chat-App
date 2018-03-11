package com.proximus.societychat.societychat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.proximus.societychat.societychat.data.DManager;
import com.proximus.societychat.societychat.mDrawable.MDrawable;

import java.util.Vector;

public class Profile extends AppCompatActivity {
ImageView pimage;
TextView tv_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tv_email = (TextView)findViewById(R.id.profile_emailtv);
        pimage = (ImageView)findViewById(R.id.profile_image);
        DManager dm = new DManager(this, "db_main.db", null, 1);
        tv_email.setText(dm.get_setting("account_email"));
        get_profile(dm.get_setting("account_pic_url"));

        FloatingActionButton fb = (FloatingActionButton)findViewById(R.id.profile_fab);
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
}
