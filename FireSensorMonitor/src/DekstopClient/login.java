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
import java.net.ProtocolException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author UDILUCH
 */
public class login {
   
   

    public static boolean SignUpPOST(String username,String password) throws MalformedURLException, ProtocolException, IOException, JSONException{
	
            boolean sucess = false;
            //creating a custom JSON String
	    final String POST_PARAMS = "{\n" +
	    		"    \"Type\": \"User\",\r\n" +
	            "    \"Username\": \""+username+"\",\r\n" +
	            "    \"Password\": \""+password+"\"" + "\n}";
	    System.out.println(POST_PARAMS);
            //create a URL object with the target URI string that accepts the JSON data via HTTP POST method
	    URL obj = new URL("http://localhost:5000/user/sign-up");
            
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
	    int responseCode = postConnection.getResponseCode();//Check the response code
	    System.out.println("POST Response Code :  " + responseCode);
	    System.out.println("POST Response Message : " + postConnection.getResponseMessage());
            
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
                    sucess = album.getBoolean("success");
			
		}
                return sucess;
                
	    } else {
	        System.out.println("POST NOT WORKED");
	    }
            
            return sucess;
    }
    
    
    public static boolean LoginPOST(String username,String password) throws MalformedURLException, ProtocolException, IOException, JSONException{
	
            boolean sucess = false;
            
            //creating a custom JSON String
	    final String POST_PARAMS = "{\n" +
	            "    \"Username\": \""+username+"\",\r\n" +
	            "    \"Password\": \""+password+"\"" + "\n}";
	    System.out.println(POST_PARAMS);
            
            //create a URL object with the target URI string that accepts the JSON data via HTTP POST method
	    URL obj = new URL("http://localhost:5000/user/sign-in");
            
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
                    sucess = album.getBoolean("success");
			
		}
                
                return sucess;
                
	    } else {
	        System.out.println("POST NOT WORKED");
	    }
            
            return sucess;
    }
	
}
