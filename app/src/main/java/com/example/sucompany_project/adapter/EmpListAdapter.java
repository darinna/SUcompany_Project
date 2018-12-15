package com.example.sucompany_project.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sucompany_project.R;
import com.example.sucompany_project.model.EmpItem;
import com.example.sucompany_project.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class EmpListAdapter extends ArrayAdapter<EmpItem> {

    private Context mContext;
    private int mResource;
    private List<EmpItem> mEmpItemList;

    public EmpListAdapter(@NonNull Context context,
                            int resource,
                            @NonNull List<EmpItem> phoneItemList) {
        super(context, resource, phoneItemList);
        this.mContext = context;
        this.mResource = resource;
        this.mEmpItemList = phoneItemList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResource, parent, false);

        TextView titleTextView = view.findViewById(R.id.title_text_view);
        TextView numberTextView = view.findViewById(R.id.number_text_view);


        EmpItem phoneItem = mEmpItemList.get(position);
        String title = phoneItem.department;
        String number = phoneItem.name;


        titleTextView.setText(title);
        numberTextView.setText(number);

        return view;
    }
}