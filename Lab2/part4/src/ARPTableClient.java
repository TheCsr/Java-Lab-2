

public class ARPTableClient {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: Client host port ip_address");
        }

        String host = args[0];
        int port;
        String ip_address = args[2];
        try {
            port = Integer.parseInt(args[1]);
        } catch(Exception e) {
            port = ARPTableService.ARPTable_SERVICE_PORT;
        }
        MessageClient conn;
        try {
            conn = new MessageClient(host,port);
        } catch(Exception e) {
            System.err.println(e);
            return;
        }
        Message m = new Message();
        m.setParam("ip_address", ip_address);
        m.setType(ARPTableService.ARPTable_SERVICE_MESSAGE);
        m = conn.call(m);
        System.out.println("Corresponding MAC address:  " + m.getParam("mac_address"));
        m.setType(75);
        m = conn.call(m);
        System.out.println("Bad reply " + m);
        conn.disconnect();
    }
}
