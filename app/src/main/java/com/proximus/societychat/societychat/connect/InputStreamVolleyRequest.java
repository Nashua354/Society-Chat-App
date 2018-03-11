package com.proximus.societychat.societychat.connect;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ray on 11/3/18.
 */

public class InputStreamVolleyRequest extends Request<byte[]> {
    private final Response.Listener<byte[]> mListener;
    private Map<String, String> mParams;

    //create a static map for directly accessing headers
    public Map<String, String> responseHeaders ;

    public InputStreamVolleyRequest(int method, String mUrl ,Response.Listener<byte[]> listener,
                                    Response.ErrorListener errorListener, HashMap<String, String> params) {
        super(method, mUrl, errorListener);
        // this request would never use cache.
        setShouldCache(false);
        mListener = listener;
        mParams=params;
    }

    @Override
    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return mParams;
    };


    @Override
    protected void deliverResponse(byte[] response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {

        //Initialise local responseHeaders map with response headers received
        responseHeaders = response.headers;

        //Pass the response data here
        return Response.success( response.data, HttpHeaderParser.parseCacheHeaders(response));
    }

}
/*
String mUrl= <YOUR_URL>;
InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.GET, mUrl,
        new Response.Listener<byte[]>() {
             @Override
             public void onResponse(byte[] response) {
           // TODO handle the response
            try {
            if (response!=null) {

              FileOutputStream outputStream;
              String name=<FILE_NAME_WITH_EXTENSION e.g reference.txt>;
                outputStream = openFileOutput(name, Context.MODE_PRIVATE);
                outputStream.write(response);
                outputStream.close();
                Toast.makeText(this, "Download complete.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
            e.printStackTrace();
        }
  }
} ,new Response.ErrorListener() {

  @Override
  public void onErrorResponse(VolleyError error) {
    // TODO handle the error
    error.printStackTrace();
  }
}, null);
          RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack());
          mRequestQueue.add(request);
*/