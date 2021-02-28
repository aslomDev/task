package com.task.service;

import com.google.gson.Gson;
import com.task.payload.ApiResponse;
import com.task.payload.ReqTest;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

@Service
public class TestService {


    public ApiResponse sms(ReqTest reqTest){
        reqTest.getNumber().replaceAll("[^0-9]", ""); // удалить все кроме цифр
        if (reqTest.getNumber().startsWith("998")){
            if (reqTest.getNumber().length() == 12){
                try {
                    URL targetUrl = new URL("http://localhost:8080/sms/get");
                    HttpURLConnection httpConnection =
                            (HttpURLConnection) Objects.requireNonNull(targetUrl).openConnection();

                    httpConnection.setDoOutput(true);
                    httpConnection.setRequestMethod("POST");
                    httpConnection.setRequestProperty("Accept-Charset", "UTF-8");
                    httpConnection.setRequestProperty("Content-Type", "application/json");

                    OutputStream outputStream = httpConnection.getOutputStream();
                    Gson gson = new Gson();
                    String request = gson.toJson(reqTest);
                    outputStream.write(request.getBytes());
                    outputStream.flush();

                    if (httpConnection.getResponseCode() != 200) {
                        if (httpConnection.getResponseCode() == 400) {
                            InputStreamReader inputStreamReader = new InputStreamReader((httpConnection.getErrorStream()));
                            BufferedReader responseBuffer = new BufferedReader(inputStreamReader);
                            String output;
                            while ((output = responseBuffer.readLine()) != null) {

                                //ERROR RESPONSE CODE = 202  return true;
                                System.out.println(output);
                                return new ApiResponse("Nomer notug'ri!", false);
                            }
                        }
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
