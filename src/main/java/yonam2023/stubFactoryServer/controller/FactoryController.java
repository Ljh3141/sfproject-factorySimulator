package yonam2023.stubFactoryServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import yonam2023.stubFactoryServer.data.Machine;
import yonam2023.stubFactoryServer.data.MachineState;
import yonam2023.stubFactoryServer.service.MachineService;
import yonam2023.stubFactoryServer.service.StubRunning;

@Controller
public class FactoryController {

    @Autowired
    MachineService ms;

    @Autowired
    StubRunning st;

    @GetMapping("/turnOn")
    public @ResponseBody String turnOnFactory(){
        st.start();
        return "hello sir";
    }
    @GetMapping("/turnOff")
    public @ResponseBody String turnOffFactory(){
        st.off();
        return "good day";
    }

    @GetMapping("/isMcExist/{McId}")
    public @ResponseBody String isMachineExist(@PathVariable("McId")int id){
        //check MachineData and MachineExist
        if(ms.findMachine(id)==null) return "false";
        return "true";
    }

    @GetMapping("/checkMcState/{McId}")
    public @ResponseBody String checkMachineState(@PathVariable("McId")int id){
        //check and return Machine state
        //if not exist, send something different
        Machine m = ms.findMachine(id);
        if(m==null){
            return "machine not found";
        }

        return String.valueOf(m.getState());
    }

    @GetMapping("/runMachine/{McId}")
    @ResponseBody
    public String runMachineGet(@PathVariable("McId")int id){
        MachineState mstate = ms.runMachine(id);
        if(mstate == MachineState.NOTFOUND) {
            return "machine not found : "+ id;
        }else if(mstate == MachineState.RUN){
            return "machine already run : "+id;
        }else {
            return "machine is now running on : "+id;
        }
    }
    @GetMapping("/stopMachine/{McId}")
    @ResponseBody
    public String stopMachineGet(@PathVariable("McId")int id){
        MachineState mstate = ms.stopMachine(id);
        if(mstate == MachineState.NOTFOUND) {
            return "machine not found : "+ id;
        }else if(mstate == MachineState.STOP){
            return "machine already stop : "+id;
        }else {
            return "machine is now turnning off : "+id;
        }
    }
}
