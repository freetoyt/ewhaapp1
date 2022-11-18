package com.gms.app.ewhabarcode.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.gms.app.ewhabarcode.R;
import com.gms.app.ewhabarcode.domain.CustomerProductVO;

import java.util.ArrayList;

public class CustomerProductAdaptor extends ArrayAdapter {

    private ArrayList<CustomerProductVO> itemList;
    private Context context;
    private int rowResourceId;

    public CustomerProductAdaptor(Context context, int textViewResourceId, ArrayList<CustomerProductVO> itemList) {
        super(context, textViewResourceId, itemList);
        //ListView의 row정보를 저장하는 ArrayList.

        this.itemList = itemList;
        this.context = context;
        //Log.d("CustomerProductAdaptor", "CustomerProductAdaptor: " + itemList.size());
        // list_row.xml의 id. getView 메쏘드에서 추가될 item의 position에 해당 view를 inflate하기 위해 저장
        this.rowResourceId = textViewResourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(this.rowResourceId, null);

        }

        // 그려지게될 ListView의 현재 row 위치(position) item 정보를 얻어온다.
        CustomerProductVO item = itemList.get(position);
        if (item != null) {

            // 선택된 row의 데이터를 표시한다. 표시될 view는 list_row.xml의 각 항목을 이용하여 표시한다.

            TextView tv_mProductNm = (TextView) v.findViewById(R.id.tv_mProductNm);
            TextView tv_bottleCount = (TextView) v.findViewById(R.id.tv_bottleCount);


            tv_mProductNm.setText(item.getProductNm());
            tv_bottleCount.setText(item.getBottleCount());

        }
        return v;
    }
}
