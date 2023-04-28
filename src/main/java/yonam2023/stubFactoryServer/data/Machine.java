package yonam2023.stubFactoryServer.data;

import lombok.Builder;

public class Machine {
    int id;
    String name;
    int max;
    int min;
    int current;
    boolean state = false;

    @Builder
    public Machine(int id, String name, int max, int min, int current){
        this.id = id;
        this.name = name;
        this.max = max;
        this.min = min;
        this.current = current;
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
        return current;
    }

    public void setCurrent(int current){
        this.current = current;
    }
}
