// The package containing our stubs .
import GreetingMessageApp.*;
// All CORBA applications need these classes .
import org.omg.CORBA.*;
// needed for output to the file system .
import java.io.*;
import org.omg.CosNaming.* ;
import org.omg.CosNaming.NamingContextPackage.*;
import java.util.Properties;
 
public class GreetingMessageServer {
 public static void main(String args[]) {
  try {
   
   NameComponent nc[] = new NameComponent[1];
   // Initialize the ORB
   Properties props = new Properties();
   props.put("org.omg.CORBA.ORBInitialPort", "49000");
   ORB orb = ORB.init(args, props);
   // Instantiate the HelloServant on the server
   GreetingMessageServant GreetingMessageRef = new GreetingMessageServant();
   // Connect the HelloServant to the orb
   orb.connect(GreetingMessageRef);
    
    org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            System.out.println("Found NameService.");
            NamingContext rootCtx = NamingContextHelper.narrow(objRef);
     
    nc[0] = new NameComponent("Message", "Context");
            NamingContext GreetingMessageCtx = rootCtx.bind_new_context(nc);
            System.out.println("Context 'Message' added to Name Space.");
 
   nc[0] = new NameComponent("Greeting", "Object");
            //NameComponent path[] = {nc};
            //Binding the name to an object that is stored in the Naming Context
            GreetingMessageCtx.rebind(nc, GreetingMessageRef);
            System.out.println("Object 'Greeting' added to Message Context.");
    
   nc[0] = new NameComponent("Exit", "Object");
            //NameComponent path[] = {nc};
            //Binding the name to an object that is stored in the Naming Context
            GreetingMessageCtx.rebind(nc, GreetingMessageRef);
            System.out.println("Object 'Exit' added to Message Context.");
    
    
   orb.run();
  } catch (Exception e) {
   System.err.println("ERROR : " + e);
   e.printStackTrace(System.out);
  }
 }
}