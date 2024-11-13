/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClinicHub.Main;


import StaffPortal.Student.StudentModel;
import Serial.*;
import Util.*;
import com.fazecast.jSerialComm.SerialPort;
import java.awt.Color;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author joena
 */

public class MainframeServiceImplementation implements MainframeService {
    MainPanel mp;
    MainframeDAO dao =  new MainframeDAOImplementation();
    SerialService ss = new SerialServiceImplementation();
    String serialInput = "";
    ExecutorService pollingExecutor = Executors.newSingleThreadExecutor();
    
    public MainframeServiceImplementation(MainPanel mp){
        this.mp = mp;
        if(GlobalVar.port == null){
            this.mp.stts.setText("Disconnected");
            this.mp.stts.setForeground(Color.RED); 
        }else{
            connectArduino();
            isDeviceReady();  
        }
    }
    
    @Override
    public boolean connectArduino() {
        System.out.println("connectArduino");
        boolean connectionStatus = false;

        try {
            // Open the serial port
            GlobalVar.port.openPort();
            GlobalVar.port.setBaudRate(9600);
            GlobalVar.port.setNumDataBits(8);
            GlobalVar.port.setNumStopBits(1);
            GlobalVar.port.setParity(SerialPort.NO_PARITY);

            // Set the connection status
            connectionStatus = true;
            System.out.println("Arduino connected successfully.");

            // Start polling for data in a new thread
            startPolling();

        } catch (Exception ex) {
            System.out.println("Error connecting to Arduino: " + ex.toString());
        }

        return connectionStatus;
    }
    @Override
    public void disconnectArduino() {
        if (GlobalVar.port != null) {
            try {
                System.out.println("disconnectArduino()");
                GlobalVar.port.closePort(); // Close the port safely
                pollingExecutor.shutdownNow(); // Stop polling when disconnected
            } catch (Exception ex) {
                System.out.println("Error disconnecting: " + ex.toString());
            }
        }
    }

    private void RFIDMatchesDB() {
        if (dao.RFIDMatch(serialInput)) {
            StudentModel student = dao.getStudentInformation(serialInput);
            if (student != null) {
                GlobalVar.activeStudent = student;
                JOptionPane.showMessageDialog(null, "Welcome " + GlobalVar.activeStudent.getFname() 
                                              + " " + GlobalVar.activeStudent.getLname() + "!",
                                              "Please proceed with your purpose.", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Student information not found.", 
                                              "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean isDeviceReady() {
        boolean status = false;
        ExecutorService executor2 = Executors.newSingleThreadExecutor();
        executor2.submit(() -> {
            while (!serialInput.equals("***********")) {
                try {
                    this.mp.stts.setText("Connected...");
                    this.mp.stts.setForeground(Color.WHITE);
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainframeServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("String is: " + serialInput);
            }
            System.out.println("Ready");
            shutDownExecutor(executor2);
        });
        return status;
    }

    private void shutDownExecutor(ExecutorService executor){
        try {
            System.out.println("Attempting to shutdown executor");
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("Tasks interrupted");
        } finally {
            if (!executor.isTerminated()) {
                System.err.println("Cancelling non-finished tasks");
            }
            executor.shutdownNow();
            System.out.println("Shutdown finished");
            this.mp.stts.setText("Ready");
            this.mp.stts.setForeground(Color.GREEN);
        }
    }

    private void startPolling() {
    pollingExecutor.submit(() -> {
        while (GlobalVar.port.isOpen()) {
            pollDataFromArduino();
            try {
                Thread.sleep(100); // Adjust delay as needed
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    });
}
    public void pollDataFromArduino() {
        try {
            if (GlobalVar.port.bytesAvailable() > 0) {
                byte[] buffer = new byte[13];  // Adjust based on expected RFID data length
                int numBytes = GlobalVar.port.readBytes(buffer, buffer.length);
                serialInput = new String(buffer, 0, numBytes);
                System.out.println("Received RFID Data: " + serialInput);

                // Process the RFID data
                RFIDMatchesDB();
            }
        } catch (Exception ex) {
            System.out.println("Error reading data from Arduino: " + ex.toString());
        }
    }
}
