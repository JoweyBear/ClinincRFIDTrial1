/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClinicHub.Resources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author joena
 */
public class ResourcesController {
    ResourcesFrame rf;
    ResourcesService rs;
    
    public ResourcesController(ResourcesFrame rf){
        this.rf = rf;
        this.rf.buttonListener(new ButtonEvent());
        rs = new ResourceServiceImplementation(rf);
    }
    
    class ButtonEvent implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == rf.rcrd){
                rs.save();
            }
            
        }
        
    }
    
}
