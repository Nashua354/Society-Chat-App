package com.proximus.societychat.societychat.mDrawable;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.proximus.societychat.societychat.R;

import java.util.Vector;

/**
 * Created by ray on 12/3/18.
 */

public class Form
{
    AlertDialog.Builder builder;
    Context context;
    Vector<Item> items;
    Vector<View> views;
    public Form(Context context){
        builder = new AlertDialog.Builder(context);
        this.context = context;
        items = new Vector<Item>();
    }
    public Form addview(int ITEM_TYPE, String val_default){
        Item item = new Item(context, ITEM_TYPE);
        item.setValue(val_default);
        items.add(items.size(), item);
        return this;
    }
    /*public void addView(View view)
    {
        views.add();
    }
*/
    public static class Item{
        Context context;
        int type;
        LinearLayout view;
        public static int
            TYPE_EDITTEXT=0;
        public Item(Context context, int type){
            this.context = context;
            setType(type);
        }
        private void setType(int Type){
            type=Type;
            LayoutInflater li=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout lin=(LinearLayout) li.inflate(R.layout.___alert_dialof_form_item,null);
            TextView textView = (TextView) lin.findViewById(R.id.___alertdfitl);
            EditText et = (EditText)lin.findViewById(R.id.___alertdfitet);
            view=lin;
        }
        public String getValue(){
            if(type==TYPE_EDITTEXT) {
                EditText et = (EditText)view.findViewById(R.id.___alertdfitet);
                return et.getText().toString();
            }
            return "";
        }
        public void setValue(String val){
            if(type==TYPE_EDITTEXT) {
                EditText et = (EditText)view.findViewById(R.id.___alertdfitet);
                et.setText(val);
            }
        }


    }
}

/*This is still being tested*/