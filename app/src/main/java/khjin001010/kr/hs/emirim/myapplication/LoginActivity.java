package khjin001010.kr.hs.emirim.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class LoginActivity extends AppCompatActivity {
    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AlertDialog.Builder(LoginActivity.this)
                .setTitle("알람 팝업")
                .setMessage("내용")
                .setNeutralButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setContentView(R.layout.activity_write);
                    }
                }).show();

        text1 = (TextView) findViewById(R.id.text1);
        Intent intent = getIntent();
        text1.setText("Device IP : " + getDeviceIP());

        if (false == isConnected()) {
            Toast.makeText(this, "네트워크가 연결되지 않았습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (getNetworkType()) {
            case ConnectivityManager.TYPE_WIFI:
                Toast.makeText(this, "WIFI에 연결 됐습니다.", Toast.LENGTH_SHORT).show();

                HttpMgrThread httpThread = new HttpMgrThread();
                httpThread.start();
                break;
            case ConnectivityManager.TYPE_MOBILE:
                Toast.makeText(this, "모바일 네트워크에 연결됐습니다.", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        /* HttpMgrThread httpMgrThread = new HttpMgrThread();
        httpMgrThread.start();*/
    }

    private String getDeviceIP() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();

                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetWork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetWork != null && activeNetWork.isConnectedOrConnecting();

        return isConnected;
    }

    private int getNetworkType() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork.getType();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //바깥 레이어 클릭 시 닫힘X
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

}
