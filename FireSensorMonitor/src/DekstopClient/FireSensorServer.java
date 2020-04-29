/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DekstopClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author UDILUCH
 */
public class FireSensorServer extends UnicastRemoteObject implements FireSensor{

    
    private ArrayList<SensorListner> list = new ArrayList<SensorListner>();
    String update = "";
    
    public FireSensorServer() throws java.rmi.RemoteException {
	super();
    }
    
    @Override
    public  String getSensor() throws RemoteException,IOException, JSONException {
        //create a URL object with the target URI string that accepts the JSON data via HTTP GET method
        URL url= new URL("http://localhost:5000/sensor/");
	
        //invoke the openConnection method to get the HttpURLConnection object
	HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
		
	conn.setRequestMethod("GET");//send a GET request
		 
	conn.connect(); //Open a connection stream to the corresponding API
		 
	int responsecode = conn.getResponseCode(); //Get the corresponding response code
		 
	BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        
        //read the data line by line from the input stream using readLine method
	while ((inputLine = in .readLine()) != null) {
            response.append(inputLine);
	} in .close();
	// print result
	System.out.println("\nJSON data in string format");
	System.out.println(response);

	return response.toString();
    }

    //Implement the remote method to register listner in the listner list
    @Override
    public void addTemperatureListener(SensorListner listener) throws RemoteException {
        list.add(listener);
    }

    //Implement the remote method to unregister listner in the listner list
    @Override
    public void removeTemperatureListener(SensorListner listener) throws RemoteException {
       list.remove(listener);
    }

    @Override
    public String addSensorDetails(String id, int floorno, int roomNo) throws RemoteException, IOException, JSONException {
        String message = null;
        
        //creating a custom JSON String
        final String POST_PARAMS = "{\n" + "\"FloorNo\": "+floorno+",\r\n" +
			            "    \"roomNo\": "+roomNo+",\r\n" +
			            "    \"SensorID\": \""+id+"\"" + "\n}";
	System.out.println(POST_PARAMS);
          
        //create a URL object with the target URI string that accepts the JSON data via HTTP POST method
        URL obj = new URL("http://localhost:5000/sensor/create/");
        
        //invoke the openConnection method to get the HttpURLConnection object
	HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        
	postConnection.setRequestMethod("POST"); //send a POST request
        
        //parameter has to be set to send the request body in JSON format
	postConnection.setRequestProperty("Content-Type", "application/json");
        
        // enable the URLConnection object's doOutput property to true
	postConnection.setDoOutput(true); 
        
        //Open the DataOutputStream object
	OutputStream os = postConnection.getOutputStream();
	os.write(POST_PARAMS.getBytes());
	os.flush();
	os.close();
	int responseCode = postConnection.getResponseCode(); //Check the response code
	System.out.println("POST Response Code :  " + responseCode);
	System.out.println("POST Response Message : " + postConnection.getResponseMessage()); //print response message
        
        //response code is OK then create the input stream to read the returned data
	if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    
            postConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            
            //read the data line by line from the input stream using readLine method
            while ((inputLine = in .readLine()) != null) {
		response.append(inputLine);
            } in .close();
            // print result
            System.out.println(response.toString());
            String inline2 = "["+ response+"]";
	
            //create json array
            JSONArray jsonar = new JSONArray(inline2);
           
            for (int i = 0; i < jsonar.length(); i++) {
                //read json array one by one using json object
		JSONObject album = jsonar.getJSONObject(i);
		message = album.getString("message");
			
            }
            
            return message; // return thr message
            
	} else {
	    System.out.println("POST NOT WORKED");
	}
            
        return message;// return thr message
    }
    
    
     @Override
    public String UpdateSensorDetails(String Sid, int floorno, int roomNo, String id,int co2, int smoke, String status) throws RemoteException, IOException, JSONException {
        String message = null;
         //creating a custom JSON String
        final String POST_PARAMS = "{\n" + "\"FloorNo\": "+floorno+",\r\n" +
			            "    \"roomNo\": "+roomNo+",\r\n" +
			            "    \"Co2Level\": "+co2+",\r\n" +
			            "    \"smokeLevel\": "+smoke+",\r\n" +
			            "    \"status\": \""+status+"\",\r\n" +
			            "    \"SensorID\": \""+Sid+"\"" + "\n}";
	System.out.println(POST_PARAMS);
        
        //create a URL object with the target URI string that accepts the JSON data via HTTP PUT method
        URL obj = new URL("http://localhost:5000/sensor/update/"+id);
        
	 //invoke the openConnection method to get the HttpURLConnection object
	HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        
	postConnection.setRequestMethod("PUT"); //send a POST request
        
        //parameter has to be set to send the request body in JSON format
	postConnection.setRequestProperty("Content-Type", "application/json");
        
        // enable the URLConnection object's doOutput property to true
	postConnection.setDoOutput(true); 
        
        //Open the DataOutputStream object
	OutputStream os = postConnection.getOutputStream();
	os.write(POST_PARAMS.getBytes());
	os.flush();
	os.close();
	int responseCode = postConnection.getResponseCode(); //Check the response code
	System.out.println("POST Response Code :  " + responseCode);
	System.out.println("POST Response Message : " + postConnection.getResponseMessage()); //print response message
        
        //response code is OK then create the input stream to read the returned data
	if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    
            postConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            
            //read the data line by line from the input stream using readLine method
            while ((inputLine = in .readLine()) != null) {
		response.append(inputLine);
            } in .close();
            // print result
            System.out.println(response.toString());
            String inline2 = "["+ response+"]";
	
            //create json array
            JSONArray jsonar = new JSONArray(inline2);
		 
                 
            for (int i = 0; i < jsonar.length(); i++) {
                 //read json array one by one using json object
		JSONObject album = jsonar.getJSONObject(i);
		message = album.getString("message");
			
            }
            
            return message; // return thr message
            
	} else {
	    System.out.println("POST NOT WORKED");
	}
            
        return message;// return thr message
    }
    
    // notify the listners
    private void notifyListeners(String result) throws IOException, JSONException{
		
	for(SensorListner Listner : list) {
            try {
		//call to the callback method
		Listner.SensorChanged(result);
            } catch (RemoteException e) {
		
		e.printStackTrace();
            }
	}

    }
    
    // update the sensor details every 5 seconds
    public void updateStatus(){
          // start timer object
          TimerTask task = new TimerTask() {
          @Override
          public void run() {
             try {
                        
                   update = getSensor(); //get the update
                   sentEmailMsg(update); // call the sentEmailMsg method to send email and messages
                   SendAlertCo2Smoke(update); // notify the client
                   
             } catch (IOException ex) {
               Logger.getLogger(FireSensorServer.class.getName()).log(Level.SEVERE, null, ex);
             } catch (JSONException ex) {
               Logger.getLogger(FireSensorServer.class.getName()).log(Level.SEVERE, null, ex);
             }
          }
        };
            
        Timer timer = new Timer();
        long delay = 0;
        long intevalPeriod = 1 * 15000; // RMI sever get upto date every 15 seconds
        // schedules the task to be run in an interval 
        timer.scheduleAtFixedRate(task, delay,intevalPeriod);
			
            
    }
    
    // send notification to client using callback method
    public void SendAlertCo2Smoke(String result) throws IOException, JSONException {
   
        notifyListeners(result); // notify listner when co2 or smoke level > 5
      
    }
    
    
    // send email, sms when co2 level or smoke level move to greater than 5
    public void sentEmailMsg(String result) throws JSONException, MalformedURLException, IOException{
               
        JSONArray jsonar = new JSONArray(result.toString());
                   
        for (int i = 0; i < jsonar.length(); i++) {
                       
            JSONObject album = jsonar.getJSONObject(i);
                        
            String email = "chamildilu@gmail.com";
            int co2   =  album.getInt("Co2Level");
            int smoke =  album.getInt("smokeLevel");
            int floor =  album.getInt("FloorNo");
            int room  =  album.getInt("roomNo");
                    
            final String POST_PARAMS = "{\n" + "\"FloorNb\": "+floor+",\r\n" +
                                       "    \"roomNo\": "+room+",\r\n" +
                                       "    \"Co2Level\": "+co2+",\r\n" +
                                       "    \"smokeLevel\": "+smoke+",\r\n" +
                                       "    \"email\": \""+email+"\"" + "\n}";
            
           
                        
            if(co2 > 5 || smoke > 5 ){
                           
              
                
                System.out.println(POST_PARAMS);
                
                //create a URL object with the target URI string that accepts the JSON data via HTTP POST method
                URL obj = new URL("http://localhost:5000/email/send/");
                
                //invoke the openConnection method to get the HttpURLConnection object
                HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
                
                postConnection.setRequestMethod("POST");//send a POST request
	
                //parameter has to be set to send the request body in JSON format
		postConnection.setRequestProperty("Content-Type", "application/json");
                
                // enable the URLConnection object's doOutput property to true
                postConnection.setDoOutput(true);
                
                //Open the DataOutputStream object
		OutputStream os = postConnection.getOutputStream();
		os.write(POST_PARAMS.getBytes());
		os.flush();
		os.close();
		int responseCode = postConnection.getResponseCode();
		System.out.println("POST Response Code :  " + responseCode);
		System.out.println("POST Response Message : " + postConnection.getResponseMessage());
                
                //response code is OK then create the input stream to read the returned data
		if (responseCode == HttpURLConnection.HTTP_OK) { //success
                    BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in .readLine()) != null) {
			 response.append(inputLine);
                    } in .close();
                    // print result
                    System.out.println(response.toString());
                } else {
                    System.out.println("POST NOT WORKED");
                }
                            
            }
            if(co2 > 5 || smoke > 5 ){
                           
                URL obj = new URL("http://localhost:5000/sms/send/");
                HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
                postConnection.setRequestMethod("POST");
			    
		postConnection.setRequestProperty("Content-Type", "application/json");
                postConnection.setDoOutput(true);
		OutputStream os = postConnection.getOutputStream();
		os.write(POST_PARAMS.getBytes());
		os.flush();
		os.close();
		int responseCode = postConnection.getResponseCode();
		System.out.println("POST Response Code :  " + responseCode);
		System.out.println("POST Response Message : " + postConnection.getResponseMessage());
		if (responseCode == HttpURLConnection.HTTP_OK) { //success
                    BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in .readLine()) != null) {
			 response.append(inputLine);
                    } in .close();
                    // print result
                    System.out.println(response.toString());
                } else {
                    System.out.println("POST NOT WORKED");
                }
            }
        }
    }
    
    // return the updated sensor details
     public String Getupdate(){
         return update;
     }
    
    public static void main(String[] args)  {
	System.out.println("Loading temperature service");
        
        
	try {
           
            FireSensorServer sensor = new FireSensorServer();
            //register the sensor sever
            Registry reg = LocateRegistry.createRegistry(1099);
            
            reg.rebind("FireSensor", sensor);
            
            
            sensor.updateStatus();
		        
			
            } catch (RemoteException re) {
		System.err.println("Remote Error - " + re);
            } catch (Exception e) {
		System.err.println("Error - " + e);
            }

    }
}
    

