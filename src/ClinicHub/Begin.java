/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClinicHub;

import ClinicHub.Main.*;
import Util.GlobalVar;
import com.fazecast.jSerialComm.SerialPort;
import javax.swing.JOptionPane;


/**
 *
 * @author joena
 */
public class Begin {
    public static void main(String[] args){
    try{
            String[] list = getPort();
            Object selectedValue = JOptionPane.showInputDialog(null, "Choose RFID USB Port", "USB Commmunication Port", JOptionPane.INFORMATION_MESSAGE, null, list, list[0]);
            if(selectedValue == null){
                System.exit(0);
            }
            String port = (String) selectedValue;
            GlobalVar.port = SerialPort.getCommPort(port);
            MainPanel mp = new MainPanel();
            MainFrame mf =  new MainFrame(mp);
            new MainframeController(mp);
            mf.setVisible(true);
    }
    
    catch(ArrayIndexOutOfBoundsException e){
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to "
                               + "Continue Without the RFID Scanner?","Device Not Found",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION)
            {
                MainPanel mp = new MainPanel();
                MainFrame mf =  new MainFrame(mp);
                new MainframeController(mp);
                mf.setVisible(true);
            }
            else
            {
                System.exit(0);
            }  
        }
    
    }
    static String[] getPort(){
        SerialPort[] ports = SerialPort.getCommPorts();
        String[] portNames = new String[ports.length];
        
        for (int i = 0; i < ports.length; i++) {
            portNames[i] = ports[i].getSystemPortName();
            
            System.out.println(portNames);
        }
        
        return portNames;
    }
}
