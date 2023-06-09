package yonam2023.stubFactoryServer.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yonam2023.stubFactoryServer.Exception.MachineNotFoundException;
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
            hs.sendGet(OperationURI+"halt/"+id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendData(JSONArray jsonArray){
        logger.info("MachineService:send data to OperationServer");
        logger.info("MachineService:Data count:"+jsonArray.size());
        try{
            hs.sendPost(OperationURI+"sensor-data-insert/",jsonArray.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<String> getMachineIdList(){
        return md.getMidList();
    }

    public int addStockToMachine(JSONObject jsonObject) throws MachineNotFoundException{
        //mid의 기계가 존재하는지 확인
        //mid의 기계가 존재하면 재고 충전 시도.
        //재고의 수량이 초과한다면 초과한 수량을 반환, 성공했다면 -1 반환
        int mid = (int) jsonObject.get("mid");
        int amount = (int) jsonObject.get("amount");
        logger.info("receive stock : "+amount+" to "+mid);
        Machine machine = md.findMachine(mid);
        if(machine == null) throw new MachineNotFoundException();//기계를 찾을 수 없음.

        int result = machine.addStock(amount);

        logger.info("Adding Resource Stock. now "+machine.getStock());

        return result;
    }
}
