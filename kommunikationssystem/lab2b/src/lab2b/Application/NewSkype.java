/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab2b.Application;

import lab2b.Application.KeyboardListener;
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import lab2b.Controller.CallController;
import lab2b.Controller.CallController.Signal;
import lab2b.Network.UDPListener;

/**
 *
 * @author Anders
 */
public class NewSkype {
    private CallController callController;
    private ServerSocket serverSocket;
    private Boolean inSession;
    private DatagramSocket ds;
    
    public NewSkype(int port) throws IOException{
        callController = new CallController();
        //serverSocket = new ServerSocket(5678);
        inSession = false;
        ds = new DatagramSocket(port);
    }
    
    public void start(){
        Thread t = new Thread(new KeyboardListener(this));
        t.start();
        Thread t2 = new Thread(new UDPListener(ds,this));
        t2.start();
    }
    
    public Boolean isInSession(){
        return inSession;
    }
    
    public void handleInput(String message) throws UnknownHostException, Exception{
        if(message.startsWith("CALL")){
            String[] strings = message.split(" ");
            if(strings.length<3){
                throw new Exception("Please enter an ip and a port");
            }
            
            byte[] data = "INITIATE_INVITE".getBytes();
            DatagramPacket p = new DatagramPacket(data,data.length);
            p.setAddress(InetAddress.getByName(strings[1]));
            p.setPort(Integer.parseInt(strings[2]));
            
            handleMessage(p,ds);
        }else if(message.startsWith("HANGUP")){
            byte[] data = "HANGUP".getBytes();
            DatagramPacket p = new DatagramPacket(data,data.length);
            handleMessage(p,null);
        }
    }
    
    public void handleMessage(DatagramPacket p,DatagramSocket s){
        String message = new String(p.getData());
        
        if(message.startsWith("INVITE")){
            callController.processNextEvent(Signal.INVITE,p,s);
        }else if(message.startsWith("INITIATE_INVITE")){
            p.setData("INVITE".getBytes());
            p.setLength("INVITE".getBytes().length);
            callController.processNextEvent(Signal.INITIATE_INVITE,p,s);
        }else if(message.startsWith("BUSY")){
            callController.processNextEvent(Signal.BUSY,p,s);
        }else if(message.startsWith("TRO")){
            callController.processNextEvent(Signal.TRO,p,s);
        }else if(message.startsWith("OK")){
            callController.processNextEvent(Signal.OK,p,s);
        }else if(message.startsWith("BYE")){
            callController.processNextEvent(Signal.BYE,p,s);
        }else if(message.startsWith("ERROR")){
            callController.processNextEvent(Signal.ERROR,p,s);
        }else if(message.startsWith("HANGUP")){
            callController.processNextEvent(Signal.REQUEST_HANGUP,p,null);
        }
        else{
            System.out.println("Okänt paket :P");
            //do nothing?
        }
    }
}
