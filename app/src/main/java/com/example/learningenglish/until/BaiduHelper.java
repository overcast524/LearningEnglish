package com.example.learningenglish.until;

import android.util.Log;

import com.example.learningenglish.listener.CallBackListener;
import com.example.learningenglish.object.JsonAccess;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BaiduHelper {

    private static final String TAG = "BaiduHelper";

    public static final String API_KEY = "DSUvwoQknlNCMfYOgpRYstoQ";

    public static final String SECRET_KEY = "vOLtglUFdHBiOOuctg4c9kRA28oC5i0A";

    public static final String GRANT_TYPE = "client_credentials";

    public static final String URL_GET_ASSESS_TOKEN = "https://aip.baidubce.com/oauth/2.0/token";

    public static String assessToken;

    //----------------------------------------------------------

    public static final String URL_GET_OCR = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic";
    //public static final String URL_GET_OCR = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic";

    public static void getAssessToken() {
        RequestBody formBody = new FormBody.Builder()
                .add("grant_type", GRANT_TYPE)
                .add("client_id", API_KEY)
                .add("client_secret", SECRET_KEY)
                .build();
        Request request = new Request.Builder()
                .post(formBody)
                .url(URL_GET_ASSESS_TOKEN)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Gson resultGson = new Gson();
                        JsonAccess jsonAccess = resultGson.fromJson(response.body().string(), JsonAccess.class);
                        assessToken = jsonAccess.getAccess_token();
                        Log.d(TAG, assessToken);
                    }
                });
    }

    public static void analysePicture(String imgUrl, final CallBackListener callBackListener) {
        RequestBody formBody = new FormBody.Builder()
                .add("image", imgUrl)
                .add("detect_direction", "true")
                .add("language_type", "ENG")
                .build();
        Request request = new Request.Builder()
                .post(formBody)
                .url(URL_GET_OCR + "?access_token=" + assessToken)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        callBackListener.onFailure(call, e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        callBackListener.onResponse(call, response);
                    }
                });
    }

}
