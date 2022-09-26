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
public class executeCmd {
    
    private String result;
    private String command;


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public executeCmd(String command) {
        this.command = command;
    }
    
    
    public void doExecute() throws IOException, InterruptedException{
        System.out.println("Entered");
        
        String homeDirectory = System.getProperty("user.home");

        //Run macro on target
        ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", command);
        pb.directory(new File(homeDirectory));
        pb.redirectErrorStream(true);
        Process process = pb.start();
        //Read output
        StringBuilder out = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        String previous = null;
        while ((line = br.readLine()) != null) {
            if (!line.equals(previous)) {
                previous = line;
                out.append(line).append('\n');
            }
        }
        setResult(out.toString());
       
        
    }
    
}
