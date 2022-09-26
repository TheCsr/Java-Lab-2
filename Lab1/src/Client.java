/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thecsr
 */
// Online Java Compiler
// Use this editor to write, compile and run your Java code online
import java.io.*;
import java.net.*;
// Client class

public class Client {

    private final String ip;
    private final String command;
    private final int port;
    private String result;

    public String getResult() {
        return result;
    }

    public String setResult(String result) {
        this.result = result;
        return null;
    }

    public Client(String command, String ip, int port) {
        this.ip = ip;
        this.command = command;
        this.port = port;
    }

    // driver code
    public void clientSide() throws IOException {
        // establish a connection by providing host and port
        // number

        Socket socket = new Socket(ip, port); // writing to server
        PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true);

        // reading from server
        BufferedReader in
                = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));

        // sending the user input to server
        out.println(command);
        System.out.println("Hello");
        out.flush();
        StringBuilder outi = new StringBuilder();
        String line;
        while (!(line = in.readLine()).equals("")) {
            System.out.println(line);
            outi.append(line).append("\n");
        }
        setResult(outi.toString());

    }
}
