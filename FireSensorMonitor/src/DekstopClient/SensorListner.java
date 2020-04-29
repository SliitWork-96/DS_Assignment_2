/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DekstopClient;

//this is callback interface
//this is also remote interface of the client
public interface SensorListner extends java.rmi.Remote{
    
    //This will expose the remote method which will give the changed Sensor deytails
    public void SensorChanged(String object) throws 	java.rmi.RemoteException;
}
