package yonam2023.stubFactoryServer.data;

import lombok.Builder;

import java.util.Random;

public class Machine {
    int id;
    String name;
    int max;
    int min;
    int current;
    int initCurrent;
    boolean state = false;


    @Builder
    public Machine(int id, String name, int max, int min, int current){
        this.id = id;
        this.name = name;
        this.max = max;
        this.min = min;
        this.current = current;
        this.initCurrent = current;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public boolean getState(){
        return state;
    }

    public void setState(boolean state){
        this.state = state;
    }
    public int getCurrent(){
        Random random = new Random();
        current = current+random.nextInt(10)-10;//값을 나중에 6으로 조정할것.
        return current;
    }

    public void setCurrent(int current){
        this.current = current;
    }

    public void setCurrentInit(){
        this.current = this.initCurrent;
    }

    public MachineState isOk(){
        if(current>max) return MachineState.OVERLOAD;
        else if(current<min) return MachineState.FAILURE;
        else return MachineState.NORMAL;
    }

}
