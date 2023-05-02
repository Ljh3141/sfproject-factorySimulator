package yonam2023.stubFactoryServer.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yonam2023.stubFactoryServer.data.Machine;
import yonam2023.stubFactoryServer.data.MachineData;
import yonam2023.stubFactoryServer.data.MachineState;

import java.util.ArrayList;
import java.util.List;

@Service
public class MachineService {
    @Autowired
    MachineData md;

    @Autowired
    HttpService hs;

    private final String OperationURI = "http://localhost:8080/machine/";

    private Logger logger = LoggerFactory.getLogger(MachineService.class);

    public Machine findMachine(int id){
        return md.findMachine(id);
    }

    public MachineState runMachine(int id){
        Machine machine = md.findMachine(id);
        if(machine==null) return MachineState.NOTFOUND;
        else if(!machine.getState()) {
            machine.setCurrentInit();
            machine.setState(true);
            return MachineState.TURNON;
        }else{
            return MachineState.RUN;
        }
    }
    public MachineState stopMachine(int id){
        Machine machine = md.findMachine(id);
        if(machine==null) return MachineState.NOTFOUND;
        else if(machine.getState()) {
            machine.setState(false);
            return MachineState.TURNOFF;
        }else{
            return MachineState.STOP;
        }
    }

    public void fatalMachine(int id){
        logger.error("Warnning!! Machine "+id+" is Emergency STOP!!");
        Machine machine = md.findMachine(id);
        machine.setState(false);
        try{
            hs.sendGet(OperationURI+"fatalOccur/"+id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendData(JSONArray jsonArray){
        logger.info("MachineService:send data to OperationServer");
        logger.info("MachineService:Data count:"+jsonArray.size());
        try{
            hs.sendPost(OperationURI+"insertData/",jsonArray.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<String> getMachineIdList(){
        return md.getMidList();
    }
}
