package entity;

import utils.Socket;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Listeners {
    private HashMap<Integer, Long> listeners;
    private int count;
    Long curTime;
    Long expireTime;

    public Listeners(){
        listeners = new HashMap<>();
        count = 0;
    }

    public int getCount(){
        return count;
    }

    public void addListener(int port, int duration){
        curTime = System.currentTimeMillis();
        expireTime = curTime + duration * 60 * 1000;
        listeners.put(port, expireTime);
        System.out.printf("--- Listener added at port No. %d for %d minute(s).%n", port, duration);
        count += 1;
    }

    public void deleteListener(int port){
        listeners.remove(port);
        System.out.printf("--- Listener deleted from port No. %d%n", port);
        count -= 1;
    }

    public Set<Integer> getListenerList(){
        curTime = System.currentTimeMillis();
        System.out.println(curTime);
        listeners.forEach((key, value) -> {if (value <= curTime) deleteListener(key);});
        return listeners.keySet();
    }

    public void broadcast(String s, Socket socket,InetAddress clientAddress){
        System.out.println("Broadcasting.");
        int clientPortNumber;
        byte[] buffer = new byte[s.length()];
        int index = 0;
        for(byte b: s.getBytes()){
            buffer[index++] = b;
        }
        Set<Integer> validListeners = getListenerList();
        Iterator<Integer> namesIterator = validListeners.iterator();
        while(namesIterator.hasNext()) {
            clientPortNumber = namesIterator.next();
            try {
                System.out.printf("Broadcasting to port %d%n", clientPortNumber);
                socket.send(buffer,clientAddress,clientPortNumber);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}

