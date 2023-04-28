package yonam2023.stubFactoryServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yonam2023.stubFactoryServer.data.Machine;
import yonam2023.stubFactoryServer.data.MachineData;
import yonam2023.stubFactoryServer.data.MachineState;

@Service
public class MachineService {
    @Autowired
    MachineData md;

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
}
