package yonam2023.stubFactoryServer.data;

import org.springframework.stereotype.Component;
import yonam2023.stubFactoryServer.service.MachineService;

import java.util.*;

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
                .stock(0)
                .maxStock(1000)
                .useResource("설탕")
                .useRate(10)
                .build());
        machineSet.add(Machine.builder()
                .mid(1011)
                .name("temp")
                .max(150)
                .min(50)
                .current(100)
                .stock(0)
                .maxStock(1000)
                .useResource("설탕")
                .useRate(15)
                .build());
        machineSet.add(Machine.builder()
                .mid(1012)
                .name("counter can")
                .max(150)
                .min(50)
                .current(100)
                .stock(0)
                .maxStock(1000)
                .useResource("설탕")
                .useRate(20)
                .build());
        machineSet.add(Machine.builder()
                .mid(1013)
                .name("temp")
                .max(150)
                .min(50)
                .current(100)
                .stock(0)
                .maxStock(1000)
                .useResource("설탕")
                .useRate(30)
                .build());
        machineSet.add(Machine.builder()
                .mid(1014)
                .name("counter liq")
                .max(150)
                .min(50)
                .current(100)
                .stock(0)
                .maxStock(1000)
                .useResource("설탕")
                .useRate(35)
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

    public ArrayList<String> getMidList(){
        ArrayList<String> midList = new ArrayList<>();
        Iterator<Machine> iterator = machineSet.iterator();

        while(iterator.hasNext()) {
            Machine m = iterator.next();
            midList.add(Integer.toString(m.getMid()));
        }
        return midList;
    }

    public Iterator<Machine> getIterator(){
        return machineSet.iterator();
    }

}
