/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SMS;

import Serial.*;
import javax.swing.JOptionPane;

/**
 *
 * @author joena
 */
public class SMSServiceImplementation implements SMSService{
    
    SerialService ss =  new SerialServiceImplementation();
    
    @Override
    public void sendSMS(String number, String message) {
//        String messageToSend = number + "|" + message;
//        ss.writeToSerial(messageToSend);
        ss.writeToSerial(number + "|" + message);
//        System.out.println(number + message);

    }
    
}
