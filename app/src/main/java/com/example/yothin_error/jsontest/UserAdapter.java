package com.example.yothin_error.jsontest;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Yothin_Error on 8/2/2560.
 */
public class UserAdapter extends BaseAdapter {
    private static Activity activity;
    private static LayoutInflater inflater;
    ArrayList<UserList> mUserList;

    public UserAdapter(Activity activity, ArrayList<UserList> mProductList) {
        this.mUserList = mProductList;
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mUserList.size();
    }

    @Override
    public Object getItem(int i) {
        return mUserList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mUserList.get(i).getAuthorities_id();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = view;

        v = inflater.inflate(R.layout.list_item_user, null);
        TextView tvAut_id = (TextView) v.findViewById(R.id.tvAut_id);
        TextView tvAut_code = (TextView) v.findViewById(R.id.tvAut_code);
        TextView tvAut_pass = (TextView) v.findViewById(R.id.tvAut_pass);
        TextView tvAut_name = (TextView) v.findViewById(R.id.tvAut_name);
        TextView tvAut_lname = (TextView) v.findViewById(R.id.tvAut_lname);
        TextView tvAut_address = (TextView) v.findViewById(R.id.tvAut_address);
        TextView tvAut_tel = (TextView) v.findViewById(R.id.tvAut_tel);
        TextView tvAut_datein = (TextView) v.findViewById(R.id.tvAut_datein);
        TextView tvAut_status = (TextView) v.findViewById(R.id.tvAut_status);

        UserList userList = mUserList.get(position);
        tvAut_id.setText(userList.getAuthorities_id()+"");
        tvAut_code.setText(userList.getAuthorities_code()+"");
        tvAut_pass.setText(userList.getAuthorities_pass()+"");
        tvAut_name.setText(userList.getAuthorities_name()+"");
        tvAut_lname.setText(userList.getAuthorities_lastname()+"");
        tvAut_address.setText(userList.getAuthorities_address()+"");
        tvAut_tel.setText(userList.getAuthorities_telephone()+"");
        tvAut_datein.setText(userList.getAuthorities_datein()+"");
        tvAut_status.setText(userList.getAuthorities_status()+"");


        return v;
    }
}
