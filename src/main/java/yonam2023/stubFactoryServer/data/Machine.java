package yonam2023.stubFactoryServer.data;

import lombok.Builder;

import java.util.Random;

public class Machine {
    int machineId;
    String name;
    int max;
    int min;
    int current;
    int initCurrent;
    boolean state = false;
    String resourceType;
    //소비 자원 유형
    int maxStock;
    //자원 최대량. 이 이상으로 저장할 수 없음. 자원 부족 표시 기준값
    int stock;
    //재고량
    int useRate;
    //use rate의 동적인 변화는 고려할것.
    //출고 기계인가?
    boolean output;

    String productType;


    @Builder
    public Machine(int machineId, String name, int max, int min, int current, String resourceType, int maxStock, int stock, int useRate, boolean output, String productType){
        this.machineId = machineId;
        this.name = name;
        this.max = max;
        this.min = min;
        this.current = current;
        this.initCurrent = current;
        this.resourceType = resourceType;
        this.maxStock = maxStock;
        this.stock = stock;
        this.useRate = useRate;
        this.output = output;
        this.productType = productType;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    public int getMachineId() {
        return machineId;
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

    //ObjectMapper는 getter를 사용하므로 출력할 모든 데이터의 getter가 필요하다.
    public String getName() {
        return name;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public int getInitCurrent() {
        return initCurrent;
    }

    public int getCurrent() {
        return current;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public void setMaxStock(int maxStock) {
        this.maxStock = maxStock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setUseRate(int useRate) {
        this.useRate = useRate;
    }

    public boolean isState() {
        return state;
    }

    public String getResourceType() {
        return resourceType;
    }

    public int getMaxStock() {
        return maxStock;
    }

    public int getStock() {
        return stock;
    }

    public int addStock(int amount){
        stock +=amount;
        if(stock >maxStock){
            int result = stock - maxStock;
            stock = maxStock;
            return result;
        }
        return 0;
    }

    public int getUseRate() {
        return useRate;
    }

    public boolean getState(){
        return state;
    }

    public void setState(boolean state){
        this.state = state;
    }
    public int callCurrent(){
        Random random = new Random();
        current = current+random.nextInt(10)-8;//값을 나중에 6으로 조정할것.
        return current;
    }

    public void setCurrent(int current){
        this.current = current;
    }

    public void setCurrentInit(){
        this.current = this.initCurrent;
    }

    public boolean isOutput() { return this.output; }

    public String getProductType() { return this.productType; }

    public int used(){
        stock-=useRate;
        return useRate;
    }

    public MachineState isOk(){
        if(stock-useRate<0) return MachineState.EXHAUSTED;
        if(current>max) return MachineState.OVERLOAD;
        else if(current<min) return MachineState.FAILURE;
        else return MachineState.NORMAL;
    }
}
