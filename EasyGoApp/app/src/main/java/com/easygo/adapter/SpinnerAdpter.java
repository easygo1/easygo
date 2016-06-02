package com.easygo.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easygo.beans.others.SpinnerListItem;

import java.util.List;

/**
 * Created by zzia on 2016/6/1.
 */
public class SpinnerAdpter extends BaseAdapter {
    private Context context;
    private List<SpinnerListItem> myList;

    public SpinnerAdpter(Context context, List<SpinnerListItem> myList) {
        this.context = context;
        this.myList = myList;
    }

    public int getCount() {
        return myList.size();
    }
    public Object getItem(int position) {
        return myList.get(position);
    }
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        SpinnerListItem myListItem = myList.get(position);
        return new MyAdapterView(this.context, myListItem );
    }


    class MyAdapterView extends LinearLayout {
        public static final String LOG_TAG = "MyAdapterView";

        public MyAdapterView(Context context, SpinnerListItem myListItem ) {
            super(context);
            this.setOrientation(HORIZONTAL);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200, LayoutParams.MATCH_PARENT);
            params.setMargins(1, 2, 1, 2);

            TextView name = new TextView( context );
            name.setText( myListItem.getName() );
            name.setTextSize(20);
            name.setGravity(Gravity.CENTER);
            addView( name, params);

            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(200, LayoutParams.MATCH_PARENT);
            params2.setMargins(1, 1, 1, 1);

            TextView pcode = new TextView(context);
            pcode.setText(myListItem.getPcode());
            pcode.setTextSize(20);
            addView( pcode, params2);
            pcode.setVisibility(GONE);

        }

    }
}
