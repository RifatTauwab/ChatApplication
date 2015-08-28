package chat;

import chat.ChatFrame;
import java.io.*;
import java.net.*;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class SocketClient implements Runnable{
    
    public int port;
    public String serverAddr;
    public Socket socket;
    public ChatFrame ui;
    public ObjectInputStream In;
    public ObjectOutputStream Out;
   
    
    public SocketClient(ChatFrame frame) throws IOException{
        ui = frame; this.serverAddr = ui.serverAddr; this.port = ui.port;
        socket = new Socket("localhost", 6066);
            
        Out = new ObjectOutputStream(socket.getOutputStream());
        Out.flush();
        In = new ObjectInputStream(socket.getInputStream());
        
        
    }

    @Override
    public void run() {
        //boolean keepRunning = true;
        while(true){
            try {
                Message msg = (Message) In.readObject();
                //System.out.println("Incoming : "+msg.toString());
                
                if(msg.type.equals("message")){
                    if(msg.recipient.equals(ui.username)){
                        ui.jTextArea1.append(msg.sender +" >> Me :: " + msg.content + "\n");
                    }
                    
                    else{
                        ui.jTextArea1.append(msg.sender +" >> "+ msg.recipient +" :: " + msg.content + "\n");
                    }
                                            
                    
                }
                else if(msg.type.equals("login")){
                    if(msg.content.equals("TRUE")){
                        ui.jButton2.setEnabled(false);                         
                        ui.jButton4.setEnabled(true); 
                        ui.jTextArea1.append("[SERVER] : Login Successful\n");
                        ui.jTextField3.setEnabled(false); ui.jPasswordField1.setEnabled(false);
                        ui.model.addElement(msg.recipient+"(me)");
                    }
                    else{
                        ui.jTextArea1.append("[SERVER] : Login Failed\n");
                    }
                }
                
                else if(msg.type.equals("join")){
                    ui.jTextArea1.append(msg.content);
                  
                   
                }
                else if(msg.type.equals("AddUser")){
                    if(!msg.content.equals(ui.username)){
                        boolean exists = false;
                        for(int i = 0; i < ui.model.getSize(); i++){
                            if(ui.model.getElementAt(i).equals(msg.content)){
                                exists = true; break;
                            }
                        }
                        if(!exists){ ui.model.addElement(msg.content); 
                       
                        //ui.jTextArea1.append(msg.content);
                        }
                    }
                }
                
                
                
                
                else{
                    ui.jTextArea1.append("[SERVER] : Unknown message type\n");
                }
            }
            catch(Exception ex) {
                //keepRunning = false;
                ui.jTextArea1.append("[Application] : Server Down Or Connection failure\n");
                //ui.jButton1.setEnabled(true); 
                ui.jButton4.setEnabled(false); 
                
                for(int i = 1; i < ui.model.size(); i++){
                    ui.model.removeElementAt(i);
                }
                
                ui.clientThread.stop();
                
                System.out.println("Exception");
                ex.printStackTrace();
            }
        }
    }
    
    public void send(Message msg){
        try {
            Out.writeObject(msg);
            Out.flush();
            //System.out.println("Outgoing : "+msg.toString());
            
           
        } 
        catch (IOException ex) {
            System.out.println("Exception");
        }
    }
    
    public void closeThread(Thread t){
        t = null;
    }
}
