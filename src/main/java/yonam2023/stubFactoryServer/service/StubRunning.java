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


    public void off(){
        state = false;
    }
}
