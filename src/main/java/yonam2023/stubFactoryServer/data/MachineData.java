package yonam2023.stubFactoryServer.data;

import org.springframework.stereotype.Component;
import yonam2023.stubFactoryServer.service.MachineService;

import java.util.*;

@Component
public class MachineData {
    //Set<Machine> machineSet = new HashSet<Machine>();

    List<Machine> machineList = new ArrayList<>();

    public MachineData(){
        machineList.add(Machine.builder()
                .machineId(1010)
                .name("counter label")
                .max(150)
                .min(50)
                .current(100)
                .stock(0)
                .maxStock(1000)
                .resourceType("설탕")
                .useRate(10)
                .build());
        machineList.add(Machine.builder()
                .machineId(1011)
                .name("temp")
                .max(150)
                .min(50)
                .current(100)
                .stock(0)
                .maxStock(1000)
                .resourceType("캔")
                .useRate(15)
                .output(true)
                .productType("monster")
                .build());
        machineList.add(Machine.builder()
                .machineId(1012)
                .name("counter can")
                .max(150)
                .min(50)
                .current(100)
                .stock(0)
                .maxStock(1000)
                .resourceType("설탕")
                .useRate(20)
                .build());
        machineList.add(Machine.builder()
                .machineId(1013)
                .name("temp")
                .max(150)
                .min(50)
                .current(100)
                .stock(0)
                .maxStock(1000)
                .resourceType("설탕")
                .useRate(30)
                .build());
        machineList.add(Machine.builder()
                .machineId(1014)
                .name("counter liq")
                .max(150)
                .min(50)
                .current(100)
                .stock(0)
                .maxStock(1000)
                .resourceType("설탕")
                .useRate(35)
                .build());
    }

    public Machine findMachine(int id){
        MachineIterator iterator = new MachineIterator(machineList);
        while(iterator.hasNext()){
            Machine m = iterator.next();
            if(m.getMachineId()==id){
                return m;
            }
        }
        return null;
    }

    public ArrayList<String> getMidList(){
        ArrayList<String> midList = new ArrayList<>();
        MachineIterator iterator = new MachineIterator(machineList);

        while(iterator.hasNext()) {
            Machine m = iterator.next();
            midList.add(Integer.toString(m.getMachineId()));
        }
        return midList;
    }

    public MachineIterator getIterator(){
        return new MachineIterator(machineList);
    }

}
