/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DekstopClient;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author UDILUCH
 */
public class SensorMonitor extends UnicastRemoteObject implements SensorListner{

    static String reading, changed = null;
    static FireSensor sensor;
    
    public SensorMonitor() throws RemoteException {
    }
    
 
    
    
    public static void main(String[] args) throws RemoteException, IOException, JSONException, NotBoundException {
	try {
            String registration = "rmi://localhost:1099/FireSensor";

            Remote remoteService = Naming.lookup(registration);

            //getting the reference of remote server interface
             sensor = (FireSensor) remoteService;
			
            //This is blocking call 
            reading = sensor.getSensor();
           
            SensorMonitor monitor = new SensorMonitor();
            
            //client object to register to the server
            sensor.addTemperatureListener(monitor);
	
         } catch (MalformedURLException mue) {
	}
    }
    
    // call RMI server to add details of the sensor
    public static String addDetails(String id, int floor, int room) throws IOException, RemoteException, JSONException, NotBoundException{
        String registration = "rmi://localhost:1099/FireSensor";
        Remote remoteService = Naming.lookup(registration);
        sensor = (FireSensor) remoteService;
        String result =  sensor.addSensorDetails(id, floor, room);
        
        return  result;
    }
    
    // call RMI server to Update details of the sensor
     public static String UpdateDetails(String Sensorid, int floor, int room, String id, int co2, int smoke, String status) throws IOException, RemoteException, JSONException, NotBoundException{
        String registration = "rmi://localhost:1099/FireSensor";
        Remote remoteService = Naming.lookup(registration);
        sensor = (FireSensor) remoteService;
        String result =  sensor.UpdateSensorDetails(Sensorid, floor, room, id, co2,smoke, status);
        
        return  result;
    }
    
   //implement the callback method at the client side
    public void SensorChanged(String object) throws RemoteException {
       
      changed = object;
      MonitorApp app = new MonitorApp();
        try {
            
            app.showAlert(object);// call showAtlert method to show alerts
            
        } catch (JSONException ex) {
            Logger.getLogger(SensorMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //get the update of the sensor deatils
    public String getUpdate() throws NotBoundException, MalformedURLException, RemoteException, IOException, JSONException{
        String registration = "rmi://localhost:1099/FireSensor";
        Remote remoteService = Naming.lookup(registration);
        sensor = (FireSensor) remoteService;
        return sensor.Getupdate();
    }
    
}
