/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DekstopClient;

import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author UDILUCH
 */
public interface FireSensor extends java.rmi.Remote{
    
        //get the initial temparature
	public String getSensor() throws java.rmi.RemoteException, IOException, JSONException;
        
        // set the update status
        public void updateStatus() throws java.rmi.RemoteException, IOException, JSONException;
        
        //get the updated status
        public String Getupdate() throws java.rmi.RemoteException, IOException, JSONException;

	//Expose remote method for registration of the server object's listner list
	public void addTemperatureListener(SensorListner listener )throws java.rmi.RemoteException;
	
	//remote method to unregister from the server object
	public void removeTemperatureListener(SensorListner listener )throws java.rmi.RemoteException;
        
        //add sensor details
        public String addSensorDetails(String id, int floorno, int roomNo)throws java.rmi.RemoteException, IOException, JSONException;
        
        //update the sensor details
        public String UpdateSensorDetails(String Sid, int floorno, int roomNo, String id,int co2, int smoke, String status)throws java.rmi.RemoteException, IOException, JSONException;

}
