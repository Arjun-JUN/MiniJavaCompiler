import syntaxtree.*;
import visitor.*;

public class P4 {
   public static void main(String [] args) {
      try {
         Node root = new microIRParser(System.in).Goal();

         FirstPass one = new FirstPass();
         root.accept(one,null); // Your assignment part is invoked here.
         //System.out.println("Program visited successfully");

         SecondPass two = new SecondPass();
         two.procedureList = one.procedureList;
         root.accept(two,null); // Your assignment part is invoked here.
         //System.out.println("Algos implemented successfully");
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
} 


