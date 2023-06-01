package yonam2023.stubFactoryServer.data;

import java.util.List;


public class MachineIterator{
    List<Machine> mlist;

    int index;


    public MachineIterator(List<Machine> list){
        this.mlist = list;
        index = 0;
    }

    public Machine next(){
        return mlist.get(index++);
    }

    public boolean hasNext(){
        return index<mlist.size();
    }
}