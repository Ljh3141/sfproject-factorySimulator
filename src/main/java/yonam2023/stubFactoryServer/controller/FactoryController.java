package yonam2023.stubFactoryServer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import yonam2023.stubFactoryServer.Exception.MachineNotFoundException;
import yonam2023.stubFactoryServer.data.Machine;
import yonam2023.stubFactoryServer.data.MachineState;
import yonam2023.stubFactoryServer.service.MachineService;
import yonam2023.stubFactoryServer.service.StubRunning;

import java.util.ArrayList;

@Controller
public class FactoryController {

    @Autowired
    MachineService ms;

    @Autowired
    StubRunning st;

    private Logger logger = LoggerFactory.getLogger(FactoryController.class);

    @GetMapping("/cntCheck")
    public @ResponseBody String connectCheck(){
        return "true";
    }

    @GetMapping("/turnOn")
    public @ResponseBody String turnOnFactory(){
        if(st.getFactoryState()&&st.getPause()){
            st.resumeFactory();
            return "true";
        }
        else if(st.getFactoryState()){
            return "factory is already activated";
        }
        st.start();
        return "true";
    }
    @GetMapping("/pause")
    public @ResponseBody String pauseFactory(){
        if(st.getPause()){
            return "factory is already paused";
        }
        st.pauseFactory();
        return "true";
    }

    @GetMapping("/chkOperation")
    public  @ResponseBody String checkOperationState(){
        if(st.getFactoryState()&&!st.getPause()){
            return "true";
        }
        return "false";
    }

    @GetMapping("/shutdown")
    public @ResponseBody String shutdownFactory(){
        if(!st.getFactoryState()){
            return "factory is not activated";
        }
        st.off();
        return "factory successfully shut down";
    }

    @GetMapping("/isMcExist/{mid}")
    public @ResponseBody String isMachineExist(@PathVariable("mid")int mid){
        //check MachineData and MachineExist
        if(ms.findMachine(mid)==null) return "false";
        return "true";
    }

    @GetMapping("/checkMcState/{mid}")
    public @ResponseBody String checkMachineState(@PathVariable("mid")int mid){
        //check and return Machine state
        //if not exist, send something different
        Machine m = ms.findMachine(mid);
        if(m==null){
            return "machine not found";
        }
        return String.valueOf(m.getState());
    }

    @GetMapping("/runMachine/{mid}")
    @ResponseBody
    public String runMachineGet(@PathVariable("mid")int mid){
        MachineState mstate = ms.runMachine(mid);
        if(mstate == MachineState.NOTFOUND) {
            return "machine not found : "+ mid;
        }else if(mstate == MachineState.RUN){
            return "machine already running";
        }else {
            return "true";
        }
    }
    @GetMapping("/stopMachine/{mid}")
    @ResponseBody
    public String stopMachineGet(@PathVariable("mid")int mid){
        MachineState mstate = ms.stopMachine(mid);
        if(mstate == MachineState.NOTFOUND) {
            return "machine not found : "+ mid;
        }else if(mstate == MachineState.STOP){
            return "machine already stop : "+mid;
        }else {
            return "true";
        }
    }
    @GetMapping("/serverNotActive")
    @ResponseBody
    public String serverNotActiveGet(){
        return "Server Not Active";
    }

    @GetMapping("/getMachineIdList")
    @ResponseBody
    public String getMachineIdList(){
        ArrayList<String> midList = ms.getMachineIdList();

        return midList.toString();
    }

    @GetMapping("/getMachineData/{mid}")
    @ResponseBody
    public String getMachineData(@PathVariable("mid") int mid){
        //기계 데이터를 반환하는 코드.
        //최대 재고량, 현재 재고량, 사용률 등을 반환함.
        //RESTful 로 통신. JSON 파싱 형태로 전달 할것.
        Machine m = ms.findMachine(mid);
        if(m==null){
            return "false";
        }
        ObjectMapper om = new ObjectMapper();
        String result;
        try{result = om.writeValueAsString(m);}
        catch(Exception e){
            e.printStackTrace();
            return "false";
        }
        return result;
    }

    @PostMapping("/addStock")
    @ResponseBody
    public String addStock(@RequestBody JSONObject jsonObject){
        logger.info(jsonObject.toString());
        int result;
        //데이터 수령 확인됨.
        try{
            result = ms.addStockToMachine(jsonObject);
        }catch (MachineNotFoundException me){
            logger.info("There is no Machine  match with id "+jsonObject.get("mid"));
            return "Machine Not Found";
        }
        return ""+result;
    }

}
