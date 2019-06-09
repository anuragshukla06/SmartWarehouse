package com.example.smartwarehouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WarehouseListAdapter extends ArrayAdapter<WarehouseEntry> {

    int daysInput;

    public WarehouseListAdapter(Context context, ArrayList<WarehouseEntry> warehouseEntries, int daysInput) {
        super(context, 0, warehouseEntries);
        this.daysInput = daysInput;
    }


    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.warehouse_entry_layout, parent, false);
        }

        TextView nameTextView = v.findViewById(R.id.nameTextView);
        TextView addressTextView = v.findViewById(R.id.addressTextView);
        TextView fruitTextView = v.findViewById(R.id.fruitTextView);
        TextView priceTextView = v.findViewById(R.id.priceTextView);
        ImageView thumbnailImageView = v.findViewById(R.id.thumbnail);

        WarehouseEntry entry = getItem(position);
        nameTextView.setText(entry.getName());
        addressTextView.setText(entry.getAddress());

        int total = entry.getNumber()*daysInput;
        String totalString = "Price: Rs." + String.valueOf(total);
        priceTextView.setText(totalString);

        return v;

    }
}
