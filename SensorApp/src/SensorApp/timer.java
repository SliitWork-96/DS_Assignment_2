package SensorApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class timer {
	
		  public static void main(String[] args) {
			  
		    TimerTask task = new TimerTask() {
		      
		    @Override
		    public void run() {
		        // task to run goes here
		    	Random randomGenerator=new Random();
		    	
		    	  
		    	try {
		    		 
		    		//get the sensor details as JSON every 10 seconds
					 JSONArray jsonar = (JSONArray) getFireDetails();
					
					 String id = null;
					 String Sensorid = null;
					 int floor, room;
					 String[] arr={"active", "deactive"};
					 
					 for (int i = 0; i < jsonar.length(); i++) {
						 
						 //get the one JSON object
						 JSONObject album = jsonar.getJSONObject(i);
						 
						 id = album.getString("_id");
						 Sensorid = album.getString("SensorID");
						 floor = album.getInt("FloorNo");
						 room = album.getInt("roomNo");
						 
						 int co2 = randomGenerator.nextInt(10) + 1; // set the random number between 0-10
						 int smoke = randomGenerator.nextInt(10) + 1;// set the random number between 0-10
						 int idx = randomGenerator.nextInt(arr.length); 
						 String status = (arr[idx]);                   // set the status as random
						 
						 //call update sensor to update the details  every 10 seconds.
						 updateSensor(id, co2, smoke,status,Sensorid,floor,room);
					 }
					
					
					
				} catch (JSONException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	  
		      }
		    };
		    
		    Timer timer = new Timer();
		    long delay = 0;
		    long intevalPeriod = 1 * 10000; // set the time interval as 10 seconds
		    // schedules the task to be run in an interval 
		    timer.scheduleAtFixedRate(task, delay,intevalPeriod);
		  } // end of main
		  
		  
		  
		  public static Object getFireDetails() throws JSONException, IOException {
			  	
			     //create a URL object with the target URI string that accepts the JSON data via HTTP GET method
			     URL url= new URL("http://localhost:5000/sensor/");
				
			     //invoke the openConnection method to get the HttpURLConnection object
				 HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
				
				 conn.setRequestMethod("GET");//send a GET request
				 
				 conn.connect();//Open a connection stream to the corresponding API
				 
				 int responsecode = conn.getResponseCode();
				 
				 BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				 String inputLine;
				 StringBuffer response = new StringBuffer();
				 while ((inputLine = in .readLine()) != null) {
				      response.append(inputLine);
				 } in .close();
				 // print result
				 System.out.println("\nJSON data in string format");
				 System.out.println(response);
				 
				 // get the JSON response
				 JSONArray jsonar = new JSONArray(response.toString());
				 
				 return jsonar; // return the response as JSON array
				 
		  }
		  
		  
		  // this method update the rest API every 10 seconds
		  
		  private static void updateSensor(String id, int co2, int smoke, String status,String sensorId, int floor, int room) throws IOException {
				
			  //creating a custom JSON String
			    final String POST_PARAMS = "{\n" + "\"FloorNo\": "+floor+",\r\n" +
			            "    \"roomNo\": "+room+",\r\n" +
			            "    \"Co2Level\": "+co2+",\r\n" +
			            "    \"smokeLevel\": "+smoke+",\r\n" +
			            "    \"status\": \""+status+"\",\r\n" +
			            "    \"SensorID\": \""+sensorId+"\"" + "\n}";
			    
			    
			    System.out.println(POST_PARAMS);
			    //create a URL object with the target URI string that accepts the JSON data via HTTP PUT method
			    URL obj = new URL("http://localhost:5000/sensor/update/"+id);
			    
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
		
}
