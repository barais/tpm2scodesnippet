package p1;

import java.util.HashMap;
import java.util.Random;

import com.sun.jna.Library;
import com.sun.jna.Native;


public class TestJNA {

        public interface ITestJNA extends Library {

                String getCpuUsage();

              

        }
 volatile static String bar = "foo";
        public static String foo(){
        	bar.length();
        	
        	return bar;
        }
        
        public static void main(String[] args) {
        	System.setProperty("jna.library.path", "/home/barais/workspaces/tpsM2/testJNA/src");
        	ITestJNA INSTANCE = (ITestJNA) Native.loadLibrary("callbackjna",
                    ITestJNA.class, new HashMap());
        	long start = System.currentTimeMillis();
        	
        	for (int i=0; i<10000000; i++){
        		  INSTANCE.getCpuUsage();
        	}
        	System.err.println(System.currentTimeMillis() - start);
          	 start = System.currentTimeMillis();
              
          	 
          	 long toto=new Random().nextLong();
        	for (int i=0; i<10000000; i++){
        		  
        		toto += foo().length();
        	}
        	System.err.println(System.currentTimeMillis() - start);
          	
      	}
      

        

}