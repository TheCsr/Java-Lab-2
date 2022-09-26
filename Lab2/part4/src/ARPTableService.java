
import java.io.*;
import java.util.*;

public class ARPTableService implements Deliverable {
    public static final int ARPTable_SERVICE_MESSAGE = 100;
    public static final int ARPTable_SERVICE_PORT = 1999;
    public Message send(Message m) throws IOException {
        String ip_address = m.getParam("ip_address");
        Hashtable<String, String> ARPTable = new Hashtable<String, String>();
        File file = new File("/home/nadir/NetProg/Lab2/part4/src/arp_table.txt");
        FileReader file_reader = null;
        try {
            file_reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(file_reader);
        String line;
        while((line=reader.readLine())!=null)
        {String[] columns = line.split(" ");
            ARPTable.put(columns[0], columns[1]);
        }
        reader.close();
        if(ARPTable.get(ip_address) != null) {
            System.out.println("Client wants to know for host" + ip_address + " who has the MAC address: " + ARPTable.get(ip_address));
        }
        else {System.out.println("IP address not found in ARP table");}
        String mac_address = ARPTable.get(ip_address); //retrieved IP address of host
        m.setParam("mac_address", mac_address);
        return m;
    }
    public static void main(String args[]) {
        ARPTableService ds = new ARPTableService();
        MessageServer ms;
        try {
            ms = new MessageServer(ARPTable_SERVICE_PORT);
        } catch(Exception e) {
            System.err.println("Could not start service " + e);
            return;
        }
        Thread msThread = new Thread(ms);
        ms.subscribe(ARPTable_SERVICE_MESSAGE, ds);
        msThread.start();
    }
}