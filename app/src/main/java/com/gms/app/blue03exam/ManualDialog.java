package com.gms.app.blue03exam;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ManualDialog {
    private Context context;
    String[] items;
    MainData mainData;
    private String shared = "file";
    private static ArrayList<MainData> arrayList;
    private static MainAdapter mainAdapter;


    public ManualDialog(Context context) {
        this.context = context;

    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(ArrayList<MainData> arrayList1, MainAdapter mainAdapter1 ){

        arrayList = arrayList1;
        mainAdapter = mainAdapter1;
        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.manual_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final TextView title = (TextView) dlg.findViewById(R.id.title);
        final EditText barCd = (EditText) dlg.findViewById(R.id.barCd);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);


        title.setText("수동입력");


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, String.format("\"%s\" 을 입력하였습니다.", barCd.getText().toString()), Toast.LENGTH_SHORT).show();
                String str_BarCd = barCd.getText().toString();

                //MainActivity List 등록
                //MainActivity.insertList(str_BarCd);
                String url =context.getString(R.string.host_name)+"api/bottleDetail.do?bottleBarCd="+str_BarCd;//AA315923";

                // AsyncTask를 통해 HttpURLConnection 수행.
                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();



                // 커스텀 다이얼로그를.종료한다.
                dlg.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "취소 했습니다.", Toast.LENGTH_SHORT).show();

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
    }



    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            Log.i("MainActivity doInBackground","rseult="+result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("ManualDialog onPostExecute","s="+s);
            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            //tv_result.setText(s);
            String bottleBarCd="";
            String bottleId="";
            String productNm="";
            //final Button btn_info = MainActivity.findViewById(R.id.btn_info);
            try {
                JSONObject jsonObject = new JSONObject(s);
                bottleId = jsonObject.getString("bottleId");
                bottleBarCd = jsonObject.getString("bottleBarCd");
                productNm = jsonObject.getString("productNm");
                Log.i("ManualDialog onPostExecute","tv_bottleBarCd="+bottleBarCd+ "productNm ="+productNm);

                SharedPreferences sharedPreferences = context.getSharedPreferences(shared, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(bottleBarCd,s);
                editor.commit();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //tv_result.setText(bottleBarCd+" "+s);
            mainData = new MainData(bottleId,bottleBarCd,productNm,null);
            //tv_result.setText(bottleBarCd+" "+s);
            arrayList.add(mainData);
            mainAdapter.notifyDataSetChanged();
        }
    }

}
