import GreetingMessageApp.*; // The package containing generated stubs .
import org.omg.CORBA.*; // All CORBA
// needed for output to the file system .
import java.io.*;
import java.util.*;
import org.omg.CosNaming.* ;
import org.omg.CosNaming.NamingContextPackage.*;
import java.util.Properties;
 
public class GreetingMessageClient {
     
    public static NamingContextExt rootCtx;
     
    public static void list(NamingContext n, String indent) {
        try {
                final int batchSize = 1;
                BindingListHolder bList = new BindingListHolder() ;
                BindingIteratorHolder bIterator = new BindingIteratorHolder();
                n.list(batchSize, bList, bIterator) ;
                if (bList.value.length != (int) 0)
                    for (int i = 0; i < bList.value.length; i++) {
                        BindingHolder bh = new BindingHolder(bList.value[0]);
                        do {
                            String stringName = rootCtx.to_string(bh.value.binding_name);
                            System.out.println(indent + stringName) ;
                            if (bh.value.binding_type.value() == BindingType._ncontext) {
                                String _indent = "----" + indent;
 
                                NameComponent [] name = rootCtx.to_name(stringName);
                                NamingContext sub_context = NamingContextHelper.narrow(n.resolve(name));
                                list(sub_context, _indent);
                            }
                        } while (bIterator.value.next_one(bh));
                    }
                else
                    System.out.println(indent + "Nothing more in this context.") ;
        }
        catch (Exception e) {
            System.out.println("An exception occurred. " + e) ;
            e.printStackTrace();
        }
    }
 
    // Add the main method here in the next step .
    public static void main(String args[]) {
        // Put the try - catch block here in the next step .
        try { // Add the rest of the HelloStringifiedClient code
            NameComponent nc[]= new NameComponent[2];
            // Initialize the ORB
            Properties props = new Properties();
            props.put("org.omg.CORBA.ORBInitialPort", "49000");
            ORB orb = ORB.init(args, props);
             
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            rootCtx = NamingContextExtHelper.narrow(objRef);
            list(rootCtx, "---->");
             
            nc[0] = new NameComponent("Message", "Context");
            nc[1] = new NameComponent("Greeting", "Object");
             
            org.omg.CORBA.Object objRefGreetingMessage = rootCtx.resolve(nc);
            GreetingMessage GreetingMessageRef= GreetingMessageHelper.narrow(objRefGreetingMessage);
 
 
            Scanner c=new Scanner(System.in);
            System.out.println("Welcome to the greeting system:");                      
            for(;;){
              System.out.println("Enter name:");
              String name = c.nextLine();
              System.out.println("Enter address:");
              String address = c.nextLine();
               
              String output = GreetingMessageRef.message(name,address);
                       
              System.out.println(output);
                      
              System.out.println("-----------------------------------");
            }
        
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }
    }
}