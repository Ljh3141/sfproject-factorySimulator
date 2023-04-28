package yonam2023.stubFactoryServer.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yonam2023.stubFactoryServer.data.Machine;
import yonam2023.stubFactoryServer.data.MachineData;
import yonam2023.stubFactoryServer.data.MachineState;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

@Component
public class StubRunning extends Thread{

    private final String USER_AGENT = "Mozilla/5.0";
    private boolean state = false;

    @Autowired
    MachineData md;

    @Autowired
    MachineService ms;

    @Override
    public void run(){
        state = true;
        while(state){
            Iterator<Machine> iterator = md.getIterator();
            System.out.println("Factory is going on...");
            while (iterator.hasNext()){
                Machine machine = iterator.next();
                if(machine.getState()){
                    //기계가 작동중이면 메시지 전송
                    System.out.println(machine.getId()+":"+machine.getCurrent());
                    MachineState mcs = machine.isOk();
                    if(mcs== MachineState.OVERLOAD || mcs == MachineState.FAILURE){
                        ms.fatalMachine(machine.getId());
                    }
                }
            }
            try {
                Thread.sleep(1000);
            }catch (Exception e) {
                System.out.println(e);
            }
        }
        System.out.println("Factory is Shutting down...");
    }
    private void sendPost(String targetUrl, JSONObject jsonObject) throws Exception {

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

    public void off(){
        state = false;
    }
}
