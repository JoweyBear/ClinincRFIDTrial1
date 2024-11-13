/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClinicHub.Main;

/**
 *
 * @author joena
 */
public class MainframeController {
    MainPanel mp;
    MainframeService ms;
    
    public MainframeController(MainPanel mp){
        this.mp = mp;
        
        ms =  new MainframeServiceImplementation(mp);
    }
}
