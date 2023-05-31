package yonam2023.stubFactoryServer.data;

import lombok.Builder;

import java.util.Random;

public class Machine {
    int mid;
    String name;
    int max;
    int min;
    int current;
    int initCurrent;
    boolean state = false;
    String useResource;
    //소비 자원 유형
    int maxStock;
    //자원 최대량. 이 이상으로 저장할 수 없음. 자원 부족 표시 기준값
    int stock;
    //재고량
    int useRate;
    //use rate의 동적인 변화는 고려할것.


    @Builder
    public Machine(int mid, String name, int max, int min, int current, String useResource, int maxStock, int stock, int useRate){
        this.mid = mid;
        this.name = name;
        this.max = max;
        this.min = min;
        this.current = current;
        this.initCurrent = current;
        this.useResource = useResource;
        this.maxStock = maxStock;
        this.stock = stock;
        this.useRate = useRate;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }
    public int getMid() {
        return mid;
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
        current = current+random.nextInt(10)-5;//값을 나중에 6으로 조정할것.
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
