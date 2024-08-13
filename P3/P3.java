import syntaxtree.*;
import visitor.*;

public class P3 {
   public static void main(String [] args) {
      try {
         Node root = new MiniJavaParser(System.in).Goal();
         GJDepthFirstCopy temp = new GJDepthFirstCopy();
         root.accept(temp,null); // Your assignment part is invoked here.
         //System.out.println("MICRO IR SUCCESSFULLY GENERATED");
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
} 