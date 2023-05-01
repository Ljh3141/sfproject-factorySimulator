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
                .mid(1010)
                .name("counter label")
                .max(150)
                .min(50)
                .current(100)
                .build());
        machineSet.add(Machine.builder()
                .mid(1011)
                .name("temp")
                .max(150)
                .min(50)
                .current(100)
                .build());
        machineSet.add(Machine.builder()
                .mid(1012)
                .name("counter can")
                .max(150)
                .min(50)
                .current(100)
                .build());
        machineSet.add(Machine.builder()
                .mid(1013)
                .name("temp")
                .max(150)
                .min(50)
                .current(100)
                .build());
        machineSet.add(Machine.builder()
                .mid(1014)
                .name("counter liq")
                .max(150)
                .min(50)
                .current(100)
                .build());
    }

    public Machine findMachine(int id){
        Iterator<Machine> iterator = machineSet.iterator();
        while(iterator.hasNext()){
            Machine m = iterator.next();
            if(m.getMid()==id){
                return m;
            }
        }
        return null;
    }

    public Iterator<Machine> getIterator(){
        return machineSet.iterator();
    }

}
