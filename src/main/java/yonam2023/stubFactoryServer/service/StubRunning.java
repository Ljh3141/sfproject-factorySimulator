package yonam2023.stubFactoryServer.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yonam2023.stubFactoryServer.data.Machine;
import yonam2023.stubFactoryServer.data.MachineData;
import yonam2023.stubFactoryServer.data.MachineState;

import java.util.Iterator;

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
            while(paused){
                try {
                    Thread.sleep(1000);
                    logger.info("Factory:Factory is paused");
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Iterator<Machine> iterator = md.getIterator();
            logger.info("Factory:Factory is going on...");
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
