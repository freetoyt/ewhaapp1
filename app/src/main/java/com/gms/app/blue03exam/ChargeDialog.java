package com.gms.app.blue03exam;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChargeDialog {
    private Context context;
    String[] items;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    ListView listView ;


    String bottleType = "";
    String buttonType = "";
    String bottles;
    String customerId="";
    String userId = "";
    String host ="";


    public ChargeDialog(Context context, String bType) {
        this.context = context;
        this.buttonType = bType;

        host = context.getString(R.string.host_name);

    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(String bottle , String id ){

        bottles= bottle;
        userId = id;

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);


        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.charge_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final TextView title = (TextView) dlg.findViewById(R.id.title);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);

        final RadioGroup rg_gender = dlg.findViewById(R.id.rg_gender);
        final RadioButton rb_man = dlg.findViewById(R.id.rb_man);
        final RadioButton rb_woman = dlg.findViewById(R.id.rb_woman);

        title.setText(buttonType);
        // Add Data to listView


        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_man) {
                    Toast.makeText(context,"실병 라디오버튼", Toast.LENGTH_SHORT).show();
                    bottleType = "F";
                }else if(checkedId == R.id.rb_woman){
                    Toast.makeText(context,"공병 라디오버튼", Toast.LENGTH_SHORT).show();
                    bottleType = "E";
                }
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // '확인' 버튼 클릭시 메인 액티비티에서 설정한 main_label에
                // 커스텀 다이얼로그에서 입력한 메시지를 대입한다.
                //main_label.setText(message.getText().toString()+"--"+str_result);

                Toast.makeText(context, String.format("\"%s=%s\" 을 입력하였습니다.", bottleType, buttonType), Toast.LENGTH_SHORT).show();

                // 서버 전송
                new HttpAsyncTask().execute(host+"api/controlAction.do?userId="+userId+"&bottles="+bottles+"&customerNm="+customerId+"&bottleType="+bottleType+"&bottleWorkCd="+buttonType);

                //MainActivity List 제거
                MainActivity.clearArrayList();

                // 커스텀 다이얼로그를 종료한다.
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



    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        private final String TAG = HttpAsyncTask.class.getSimpleName();
        // int REQUEST_CODE =
        // OkHttp 클라이언트
        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {
            //List<CustomerSimpleVO> customerList = new ArrayList<>();
            String strUrl = params[0];
            String result= "";
            try {
                // 요청
                Request request = new Request.Builder()
                        .url(strUrl)
                        .build();
                // 응답
                Response response = client.newCall(request).execute();
                result = response.body().string();
                Log.d(TAG, "response.body().string(): " + result);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }
    }
}
