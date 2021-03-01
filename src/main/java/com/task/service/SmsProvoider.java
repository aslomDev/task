package com.task.service;

import com.google.gson.Gson;
import com.task.payload.ApiResponse;
import com.task.payload.ReqActivate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

@Service
public class SmsProvoider {


    public ApiResponse sendToPravoider(ReqActivate activate){
      String number = activate.getPhone().replaceAll("[^0-9]", ""); // удалить все кроме цифр
        if (number.startsWith("998")){
            if (number.length() == 12){
                activate.setPhone(number);
                try {
                    URL targetUrl = new URL("https://sms-provaider.herokuapp.com/sms/get");
                    HttpURLConnection httpConnection =
                            (HttpURLConnection) Objects.requireNonNull(targetUrl).openConnection();

                    httpConnection.setDoOutput(true);
                    httpConnection.setRequestMethod("POST");
                    httpConnection.setRequestProperty("Accept-Charset", "UTF-8");
                    httpConnection.setRequestProperty("Content-Type", "application/json");

                    OutputStream outputStream = httpConnection.getOutputStream();
                    Gson gson = new Gson();
                    String request = gson.toJson(activate);
                    outputStream.write(request.getBytes());
                    outputStream.flush();

                    if (httpConnection.getResponseCode() != 200) {
                                return new ApiResponse("Nomer notug'ri!", false);
                    }
                    httpConnection.disconnect();
                    return new ApiResponse("SUCCESS", true);
                } catch(IOException e){
                    System.out.println("error " + e);
                }
            }
            return new ApiResponse("ERROR", false);
        }

        return new ApiResponse("ERROR", false);
    }


}
