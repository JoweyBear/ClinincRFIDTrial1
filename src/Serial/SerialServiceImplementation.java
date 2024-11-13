/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Serial;

import Util.GlobalVar;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.fazecast.jSerialComm.SerialPort;
import javax.swing.JOptionPane;

/**
 *
 * @author joena
 */
public class SerialServiceImplementation implements SerialService {

    @Override
    public void writeToSerial(String msg) {
        try {
            GlobalVar.port.writeBytes(msg.getBytes(), msg.length()); 
                        System.out.println(msg);
        JOptionPane.showMessageDialog(null,"Message Sent!");
        } catch (Exception ex) {
            Logger.getLogger(SerialServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}
   
