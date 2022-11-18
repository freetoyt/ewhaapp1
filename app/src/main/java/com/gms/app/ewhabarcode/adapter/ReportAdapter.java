package com.gms.app.ewhabarcode.adapter;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.gms.app.ewhabarcode.R;
import com.gms.app.ewhabarcode.RequestHttpURLConnection;

import java.net.URLEncoder;
import java.util.ArrayList;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private ArrayList<ReportData> arrayList;
    private String shared = "file";
    private HttpAsyncReport AsyncReport;
    Context context;
    private String userId = "";

    public ReportAdapter(ArrayList<ReportData> arrayList) {
        this.arrayList = arrayList;
    }


    @Override
    public ReportAdapter.ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_list,parent,false);

        ReportViewHolder holder = new ReportViewHolder(view);
        final SharedPreferences sharedPreferences = (view.getContext()).getSharedPreferences(shared,0);
        userId = sharedPreferences.getString("id", "");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ReportAdapter.ReportViewHolder holder, int position) {

        holder.tv_reportCustomerNm.setText(arrayList.get(position).getTv_reportCustomerNm());
        holder.tv_workReportSeq.setText(arrayList.get(position).getTv_workReportSeq());
        holder.tv_reportProductNm.setText(arrayList.get(position).getTv_reportProductNm());
        holder.tv_reportProductCapa.setText(arrayList.get(position).getTv_reportProductCapa());
        holder.tv_reportBottleWork.setText(arrayList.get(position).getTv_reportBottleWork());
        holder.tv_reportProductCount.setText(Integer.toString( arrayList.get(position).getTv_reportProductCount()));

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curNmae = holder.tv_reportCustomerNm.getText().toString();

            }
        });

        holder.btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String host = (v.getContext()).getString(R.string.host_name)+ (v.getContext()).getString(R.string.api_deleteWorkBottleNew);
                String param = holder.tv_workReportSeq.getText().toString();
                String strUrl = host+ URLEncoder.encode(param)+"&userId="+userId;
                AsyncReport = new HttpAsyncReport(strUrl,null, holder);
                context = v.getContext();
                AlertDialog.Builder ad = new AlertDialog.Builder(v.getContext());
                ad.setMessage("삭제하겠습니까?");

                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        AsyncReport.execute();

                    }
                });

                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();


                /*
                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences(shared,0);
                String value = sharedPreferences.getString(holder.tv_bottleBarCd.getText().toString(), "");

                //info 정보 띄우기
                BottleInfoDialog bottleInfo = new BottleInfoDialog(view.getContext());
                // 커스텀 다이얼로그를 호출한다.
                // 커스텀 다이얼로그의 결과를 출력할 TextView를 매개변수로 같이 넘겨준다.
                bottleInfo.callFunction(value);
                 */
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public void remove(int position) {
        try{
            arrayList.remove(position);
            notifyItemRemoved(position);
        }catch(IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder{

        protected TextView tv_reportCustomerNm;
        protected TextView tv_workReportSeq;
        protected TextView tv_reportProductNm;
        protected TextView tv_reportProductCapa;
        protected TextView tv_reportBottleWork;
        protected TextView tv_reportProductCount;
        protected Button btn_info;


        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tv_reportCustomerNm = (TextView) itemView.findViewById(R.id.tv_reportCustomerNm);
            this.tv_workReportSeq = (TextView) itemView.findViewById(R.id.tv_workReportSeq);
            this.tv_reportProductNm = (TextView) itemView.findViewById(R.id.tv_reportProductNm);
            this.tv_reportProductCapa = (TextView) itemView.findViewById(R.id.tv_reportProductCapa);
            this.tv_reportBottleWork = (TextView) itemView.findViewById(R.id.tv_reportBottleWork);
            this.tv_reportProductCount = (TextView)itemView.findViewById(R.id.tv_reportProductCount);
            this.btn_info = (Button)itemView.findViewById(R.id.btn_info);
        }
    }

    public class HttpAsyncReport extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;
        private ReportViewHolder holder;
        public HttpAsyncReport(String url, ContentValues values,ReportViewHolder holder) {

            this.url = url;
            this.values = values;
            this.holder = holder;
        }
        @Override
        protected String doInBackground(Void... params) {

            Log.d(" doInBackground","url="+url);
            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(" onPostExecute","s="+s);

            try {
                if(s != null && s.length() > 0 && s.equals("success")) {
                    remove(holder.getAdapterPosition());
                    Toast.makeText(context, String.format("\"%s에 %s를\"삭제하였습니다.", holder.tv_reportCustomerNm.getText(), holder.tv_reportProductNm.getText(), holder.tv_reportProductCapa.getText()), Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
