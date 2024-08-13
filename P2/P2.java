import syntaxtree.*;
import visitor.*;

public class P2 {
   public static void main(String [] args) {
      try {
         Node root = new MiniJavaParser(System.in).Goal();
         GJDepthFirst temp = new GJDepthFirst();
         root.accept(temp,null); // Your assignment part is invoked here.
         System.out.println("Program type checked successfully");
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
} 