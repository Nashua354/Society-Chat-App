package com.proximus.societychat.societychat.mDrawable;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.proximus.societychat.societychat.R;
import java.util.Map;
import java.util.Vector;

/**
 * Created by ray on 8/2/18.
 */

public class MDrawable
{
    public static LinearLayout getSimpleClickable(Context context, String string, View.OnClickListener ls){
        LayoutInflater li=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout lin=(LinearLayout) li.inflate(R.layout.___simple_clickable,null);
        TextView textView = (TextView) lin.findViewById(R.id.simpleclblTv);
        LinearLayout clickable = (LinearLayout)lin.findViewById(R.id.simpleclblll);
        textView.setText(string);
        //lin.setClickable(true);
        clickable.setOnClickListener(ls);
        return lin;
    }
    public static LinearLayout getFormTextItem(Context context, String Label, int type)
    {
        LayoutInflater li=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout lin=(LinearLayout) li.inflate(R.layout.___alert_dialof_form_item,null);
        TextView textView = (TextView) lin.findViewById(R.id.___alertdfitl);
        EditText et = (EditText)lin.findViewById(R.id.___alertdfitet);
        et.setInputType(type);
        textView.setText(Label);
        return lin;
    }
    public static LinearLayout getFormTextItem(Context context, String Label, int type, String def_val)
    {
        LayoutInflater li=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout lin=(LinearLayout) li.inflate(R.layout.___alert_dialof_form_item,null);
        TextView textView = (TextView) lin.findViewById(R.id.___alertdfitl);
        EditText et = (EditText)lin.findViewById(R.id.___alertdfitet);
        et.setInputType(type);
        textView.setText(Label);
        et.setText(def_val);
        return lin;
    }

    public static LinearLayout getButton(Context context, String Label, View.OnClickListener clickListener)
    {
        LayoutInflater li=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout lin=(LinearLayout) li.inflate(R.layout.___simple_button,null);
        Button bt = (Button) lin.findViewById(R.id.___simple_button_button);
        bt.setText(Label);
        bt.setOnClickListener(clickListener);
        return lin;
    }
    public static class AlertDialogForm{
        public AlertDialog.Builder builder;
        LinearLayout lin;
        Vector<LinearLayout> ll;
        Context context;
        public AlertDialogForm(Context context){
            this.context = context;
            ll = new Vector<LinearLayout>();
            builder = new AlertDialog.Builder(context);
            //builder.setTitle(title);
        }
        public AlertDialogForm addItem(int type, String label){
            return addItem(type,label, "");
        }
        public AlertDialogForm addItem(int type, String label, String def_val)
        {
            LinearLayout l = MDrawable.getFormTextItem(context, label, type, def_val);
            Log.d("HBOOKSCHOOOL--", l.toString());
            ll.add(ll.size(), l);
            return this;
        }
        public AlertDialogForm addItem(LinearLayout component)
        {
            ll.add(ll.size(), component);
            return this;
        }
        public AlertDialogForm build(String title, String btName)
        {
            LayoutInflater li=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            lin=(LinearLayout) li.inflate(R.layout.___alert_dialog_form,null);
            LinearLayout list = (LinearLayout)lin.findViewById(R.id.___alertdfll);
            TextView ttle = (TextView)lin.findViewById(R.id.___alertdftitle);
            ttle.setText(title);
            for(int i=0; i<ll.size();i++)
            {
                list.addView(ll.get(i));
            }
            LinearLayout bt = MDrawable.getButton(context, btName, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Vector<String> values = new Vector<String>();
                    for(int i=0;i<ll.size();i++)
                    {
                        LinearLayout l = ll.get(i);
                        EditText value= (EditText)l.findViewById(R.id.___alertdfitet);
                        values.add(value.getText().toString());
                    }
                    onSubmit(values);
                    close();
                }
            });
            list.addView(bt);
            builder.setView(lin);
            return this;

        }
        public void onSubmit(Vector<String> values){}
        AlertDialog al;
        public void close()
        {
            al.dismiss();
        }
        public void show()
        {
            al = builder.show();
        }
    }
}
