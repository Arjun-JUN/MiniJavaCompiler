import syntaxtree.*;
import visitor.*;

public class P5 {
   public static void main(String [] args) {
      try {
         Node root = new MiniRAParser(System.in).Goal();

         GJDepthFirst one = new GJDepthFirst();
         root.accept(one,null); // Your assignment part is invoked here.
         //System.out.println("Program visited successfully");

      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
} 


