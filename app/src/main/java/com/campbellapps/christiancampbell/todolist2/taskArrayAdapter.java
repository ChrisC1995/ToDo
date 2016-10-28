package com.campbellapps.christiancampbell.todolist2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by christiancampbell on 10/25/16.
 */

public class taskArrayAdapter extends ArrayAdapter<groceryListItem> {

    private SimpleDateFormat formatter;

    public taskArrayAdapter(Context context, ArrayList<groceryListItem> items){
        super(context, 0, items);

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        groceryListItem groc = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_grocery_list_item, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.grocery_list_item_title);
        TextView info = (TextView) convertView.findViewById(R.id.grocery_list_item_info);
        TextView date = (TextView) convertView.findViewById(R.id.grocery_list_item_date);
        TextView category = (TextView) convertView.findViewById(R.id.grocery_list_item_category);
        TextView currentDate = (TextView) convertView.findViewById(R.id.grocery_list_item_current_date);
        formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm aaa", Locale.getDefault());






        Date date2 = new Date();
        title.setText(groc.getTitle());
        info.setText(groc.getText());
        date.setText("Due Date: " + groc.getDate());
        category.setText("Category: " + groc.getCategory().toLowerCase());
        currentDate.setText("Date Last Modified:" + (formatter.format(date2)));


        return convertView;
    }



}