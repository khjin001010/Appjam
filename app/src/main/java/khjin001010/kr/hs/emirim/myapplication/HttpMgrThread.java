package khjin001010.kr.hs.emirim.myapplication;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpMgrThread extends Thread{
    public HttpMgrThread(){
    }

    @Override
    public void run(){
        reqHttp();
    }

    private void reqHttp() {
        URL url = null;
        try{
            url = new URL("http://www.naver.com");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.connect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
