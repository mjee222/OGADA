package com.ogada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class menu03_ListAdapter extends BaseAdapter {

    private TextView CountryNameTextView, PassportNameTextView;


    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<menu03ListItem> listViewItemList = new ArrayList<menu03ListItem>();

    // ListViewAdapter의 생성자
    public menu03_ListAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_menu03_list_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        CountryNameTextView = (TextView) convertView.findViewById(R.id.menu03_item_CountryName);
        PassportNameTextView = (TextView) convertView.findViewById(R.id.menu03_item_PassportName);

        menu03ListItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        CountryNameTextView.setText(listViewItem.getCountryNameStr());
        PassportNameTextView.setText(listViewItem.getPassportNameStr());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수.
    public void addItem(String CountryName, String PassportName){
        menu03ListItem item = new menu03ListItem();

        item.setCountryNameStr(CountryName);
        item.setPassportNameStr(PassportName);

        listViewItemList.add(item);
    }
}
