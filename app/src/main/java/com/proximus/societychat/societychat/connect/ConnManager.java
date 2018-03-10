package com.proximus.societychat.societychat.connect;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.proximus.societychat.societychat.data.DManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ray on 6/3/18.
 */

public class ConnManager
{
    public static class check_login_status
    {
        public check_login_status(Context context)
        {
            HbookConnect hb = new HbookConnect(context, "http://192.168.43.182:8000/");
            hb.add(
                    Request.Method.POST,
                    "http://192.168.43.182:8000/checklogin",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("1"))
                                onResult(true);
                            else onResult(false);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log(error.getMessage());
                            onResult(false);
                        }
                    },
                    null
            );
        }
        public void onResult(boolean logged)
        {

        }
    }
    public static class login{
        private DManager dm;
            public login(final Context context, String id, String origin)
            {
                dm = new DManager(context, "db1.db", null, 1);
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_token", id);
                HbookConnect hb = new HbookConnect(context, origin);
                hb.add(
                        Request.Method.POST,
                        origin+"signIn",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                onResult(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if(error.getMessage()!=null)
                                Log.d("HBOOKSCHOOL", error.getMessage());
                            }
                        },
                        params
                );
            }
            public void onResult(String response){}
    }
    public static void Log(String s)
    {
        Log.d("HBOOKSCHOOL", "cm_+_ "+s);
    }
}
