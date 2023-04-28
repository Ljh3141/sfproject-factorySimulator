package yonam2023.stubFactoryServer.data;

import org.springframework.stereotype.Component;
import yonam2023.stubFactoryServer.service.MachineService;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Component
public class MachineData {
    Set<Machine> machineSet = new HashSet<Machine>();

    public MachineData(){
        machineSet.add(Machine.builder()
                .id(0)
                .name("counter")
                .max(150)
                .min(50)
                .current(100)
                .build());
        machineSet.add(Machine.builder()
                .id(1)
                .name("temp")
                .max(150)
                .min(50)
                .current(100)
                .build());
    }

    public Machine findMachine(int id){
        Iterator<Machine> iterator = machineSet.iterator();
        while(iterator.hasNext()){
            Machine m = iterator.next();
            if(m.getId()==id){
                return m;
            }
        }
        return null;
    }

    public Iterator<Machine> getIterator(){
        return machineSet.iterator();
    }

}
