package yonam2023.stubFactoryServer.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yonam2023.stubFactoryServer.data.*;

@Component
public class StubRunning extends Thread{

    private final String USER_AGENT = "Mozilla/5.0";
    private boolean state = false;
    private boolean paused = false;
    private Logger logger = LoggerFactory.getLogger(StubRunning.class);

    @Autowired
    MachineData md;

    @Autowired
    MachineService ms;

    @Override
    public void run(){
        state = true;
        while(state){
            while(paused&&state){
                try {
                    Thread.sleep(1000);
                    logger.info("Factory:Factory is paused");
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(!state) {
                //공장이 정지상태이면 즉시 중단
                logger.info("Factory:Factory Shutdown while pause");
                break;
            }
            MachineIterator iterator = md.getIterator();
            JSONArray jsonArray = new JSONArray();
            logger.info("Factory:Factory is going on...");
            while (iterator.hasNext()){
                Machine machine = iterator.next();
                if(machine.getState()){
                    //기계가 작동중이면 메시지 전송
                    MachineState mcs = machine.isOk();
                    if(mcs == MachineState.EXHAUSTED){
                        //재료 고갈. 기계가 작동할 수 없음.
                        ms.fatalMachine(machine.getMachineId());
                        continue;
                    }
                    MachineSensorData msd = MachineSensorData.builder()
                            .machineId(machine.getMachineId())
                            .value(machine.callCurrent())
                            .used(machine.used())
                            .stock(machine.getStock())
                            .build();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("machineId", msd.getMachineId());
                    jsonObject.put("value", msd.getValue());
                    jsonObject.put("used", msd.getUsed());
                    jsonObject.put("stock", msd.getStock());
                    //소비 데이터 및 자원 여분량 표기.
                    jsonArray.add(jsonObject);
                    logger.info("Machine "+machine.getMachineId()+" used "+msd.getUsed()+"/"+machine.getStock()+" "+machine.getUseResource()+" now value "+msd.getValue());

                    mcs = machine.isOk();
                    if(mcs== MachineState.OVERLOAD || mcs == MachineState.FAILURE){
                        //기계가 작동했으나 기계의 상태가 정상 범주를 벗어남.
                        ms.fatalMachine(machine.getMachineId());
                    }
                }
            }
            if(!jsonArray.isEmpty()){
                //운영 서버로 값을 전송할것.
                ms.sendData(jsonArray);
            }
            try {
                Thread.sleep(1000);
            }catch (Exception e) {
                System.out.println(e);
            }
        }
        logger.info("Factory:Factory is Shutting down...");
    }


    public void off(){
        state = false;
    }

    public boolean getFactoryState(){
        return state;
    }

    public void pauseFactory(){
        this.paused=true;
    }
    public void resumeFactory(){
        this.paused=false;
    }

    public boolean getPause(){
        return paused;
    }
}
