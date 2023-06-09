package yonam2023.stubFactoryServer.service;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class HttpService {
    private final String USER_AGENT = "Mozilla/5.0";
    public void sendPost(String targetUrl, JSONObject jsonObject) throws Exception {

        URL url = new URL(targetUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("POST"); // HTTP POST 메소드 설정
        con.setRequestProperty("Content-Type","application/json;utf-8");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setDoOutput(true); // POST 파라미터 전달을 위한 설정

        // Send post request
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(jsonObject.toString());
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // print result
        System.out.println("HTTP 응답 코드 : " + responseCode);
        System.out.println("HTTP body : " + response.toString());

    }

    public void sendPost(String targetUrl, String string) throws Exception {

        URL url = new URL(targetUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("POST"); // HTTP POST 메소드 설정
        con.setRequestProperty("Content-Type","application/json;utf-8");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setDoOutput(true); // POST 파라미터 전달을 위한 설정

        // Send post request
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.write(string.getBytes(StandardCharsets.UTF_8));
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // print result
        System.out.println("HTTP 응답 코드 : " + responseCode);
        System.out.println("HTTP body : " + response.toString());

    }

    public String sendGet(String targetUrl) throws Exception {

        URL url = new URL(targetUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET"); // HTTP GET 메소드 설정
        con.setRequestProperty("Content-Type","application/json;utf-8");
        con.setRequestProperty("User-Agent", USER_AGENT);

        // Send post request

        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // print result
        System.out.println("HTTP 응답 코드 : " + responseCode);
        System.out.println("HTTP body : " + response.toString());
        return response.toString();
    }
}
