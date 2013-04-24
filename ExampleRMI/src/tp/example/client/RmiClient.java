package tp.example.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import tp.example.ReceiveMessageInterface;

public class RmiClient
{
    static public void main(String args[])
    {
       ReceiveMessageInterface rmiServer;
       Registry registry;
       String serverAddress="127.0.0.1";
       String serverPort="3232";
       String text="Middleware";
       System.out.println("sending "+text+" to "+serverAddress+":"+serverPort);
       try{
           // get the “registry” 
           registry=LocateRegistry.getRegistry(
               serverAddress,
               (new Integer(serverPort)).intValue()
           );
           // look up the remote object
           rmiServer=
              (ReceiveMessageInterface)(registry.lookup("rmiServer"));
           // call the remote method
           rmiServer.receiveMessage("toto");
       }
       catch(RemoteException e){
           e.printStackTrace();
       }
       catch(NotBoundException e){
           e.printStackTrace();
       }
    }
}