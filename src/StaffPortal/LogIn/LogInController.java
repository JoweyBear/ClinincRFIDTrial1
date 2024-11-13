/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffPortal.LogIn;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author joena
 */
public class LogInController {
    LogInService ls;
    LogInFrame lf;
    String staffname;
    
    public LogInController(LogInFrame lf){
        this.lf = lf;
        ls = new LogInServiceImplementation(lf);
        this.lf.ntr.addActionListener((ActionEvent e) -> {
        ls.logIn();
    });
        this.lf.pass.addKeyListener(new KeyListener(){
            public void actionPerformed(KeyEvent evt){
                System.out.println("Handled by unknown class listener");
            }
            @Override
            public void keyTyped(KeyEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    ls.logIn();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
            
        });
}
}
