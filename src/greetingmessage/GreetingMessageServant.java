import GreetingMessageApp.*;
import org.omg.CORBA.*;
import java.io.*;
import java.net.*;
 
class GreetingMessageServant extends _GreetingMessageImplBase{
 
 
     public String message(String name, String address) {
         
        String output = "Hello "+name+" from "+address;
        return output;
    }
           
  }