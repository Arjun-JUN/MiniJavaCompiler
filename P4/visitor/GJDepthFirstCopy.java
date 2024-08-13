//
// Generated by JTB 1.3.2
//

package visitor;
import syntaxtree.*;
import java.util.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */

/*-------------------------- GLOBAL CLASSES AND STRUCTS --------------------------*/
class BlockNode{
   Stmt statement;
   Vector<Integer> def = new Vector<Integer>();
   Vector<Integer> use = new Vector<Integer>();
   Vector<Integer> in = new Vector<Integer>();
   Vector<Integer> out = new Vector<Integer>();
   String label;
   BlockNode successor;
   BlockNode successorTwo;
}
class ProcedureNode{
   String label;
   int args;
   int stackSpace;
   int callArgs;
   BlockNode enterBlock;
   HashMap<String, BlockNode> labelBlock = new HashMap<String, BlockNode>();
   //Boolean spilled;
}
/*-------------------------- GLOBAL CLASSES AND STRUCTS --------------------------*/


public class GJDepthFirstCopy<R, A> implements GJVisitor<String, String> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //

/*-------------------------- GLOBAL CONSTANTS & FLAGS --------------------------*/
   Boolean PRINT = false;
   Boolean INIT  = true;
/*-------------------------- GLOBAL CONSTANTS & FLAGS --------------------------*/



/*-------------------------- GLOBAL VARS --------------------------*/
   Vector<ProcedureNode> procedureList = new Vector<ProcedureNode>();
   ProcedureNode currProcedure;
   BlockNode currentBlock;
   Boolean setLabel = false;
   String currentLabel = null;
/*-------------------------- GLOBAL VARS --------------------------*/



/*-------------------------- FUNCTIONS --------------------------*/

   /*-- DEBUG PRINT ALL PROCS --*/
   void printProcedures(){
      for(int i=0; i<procedureList.size(); i++){
         System.out.println("Procedure: " + procedureList.get(i).label);
         System.out.println("Args: " + procedureList.get(i).args);
         System.out.println("StackSpace: " + procedureList.get(i).stackSpace);
         System.out.println("CallArgs: " + procedureList.get(i).callArgs);
         //printBlocks(procedureList.get(i).enterBlock);
         System.out.println();
      }
   }
   // void printBlocks(BlockNode block){
   //    if(block == null){
   //       return;
   //    }
   //    if(block.label!=null)System.out.println("Label: " + block.label);
   //    if(block.statement != null){
   //       PRINT = true;
   //       (block.statement).f0.accept(this, null);//The Statement
   //       PRINT = false;
   //    }
   //    else{
   //       System.out.println("ENTRY BLOCK");
   //    }
   //    System.out.println();
   //    printBlocks(block.successor);
   //    printBlocks(block.successorTwo);
   // }
   /*-- DEBUG PRINT ALL PROCS --*/

/*-------------------------- FUNCTIONS --------------------------*/


   public String visit(NodeList n, String argu) {
      String _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public String visit(NodeListOptional n, String argu) {
      if ( n.present() ) {
         String _ret=null;
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this,argu);
            _count++;
         }
         return _ret;
      }
      else
         return null;
   }

   public String visit(NodeOptional n, String argu) {
      if ( n.present() )
         return n.node.accept(this,argu);
      else
         return null;
   }

   public String visit(NodeSequence n, String argu) {
      String _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public String visit(NodeToken n, String argu) { return null; }

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> "MAIN"
    * f1 -> StmtList()
    * f2 -> "END"
    * f3 -> ( Procedure() )*
    * f4 -> <EOF>
    */
   public String visit(Goal n, String argu) {
      String _ret=null;

   /* CREATING PROCEDURE NODE */
      ProcedureNode main = new ProcedureNode();
         main.label = "MAIN"; main.args = 0; main.stackSpace = 0; main.callArgs = 0;
         BlockNode enterBlock = new BlockNode();
            enterBlock.statement = null;
            enterBlock.label = null;
            enterBlock.successor = null;
            enterBlock.successorTwo = null;
         main.enterBlock = enterBlock;
      currProcedure = main;
      procedureList.add(main);
      currentBlock = enterBlock;
   /* CREATING PROCEDURE NODE */

      n.f0.accept(this, argu);//MAIN
      n.f1.accept(this, argu);//StmtList  
      n.f2.accept(this, argu);//END

      n.f3.accept(this, argu);//Procedures

      n.f4.accept(this, argu);//EOF

      printProcedures();
      
      return _ret;
   }

   /**
    * f0 -> ( ( Label() )? Stmt() )*
    */
   public String visit(StmtList n, String argu) {
      String _ret=null;
      n.f0.accept(this, "ADD_LABEL_TO_BLOCKNODE");
      return _ret;
   }

   /**
    * f0 -> Label()
    * f1 -> "["
    * f2 -> IntegerLiteral()
    * f3 -> "]"
    * f4 -> StmtExp()
    */
   public String visit(Procedure n, String argu) {
      String _ret=null;

      /* CREATING PROCEDURE NODE */
         ProcedureNode proc = new ProcedureNode();
            proc.label = n.f0.f0.toString(); proc.args = Integer.parseInt(n.f2.f0.toString()); proc.stackSpace = 0; proc.callArgs = 0;
            BlockNode enterBlock = new BlockNode();
               enterBlock.statement = null;
               enterBlock.label = null;
               enterBlock.successor = null;
               enterBlock.successorTwo = null;
            proc.enterBlock = enterBlock;
         currProcedure = proc;
         procedureList.add(proc);
         currentBlock = enterBlock;
      /* CREATING PROCEDURE NODE */

      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);

      return _ret;
   }

   /**
    * f0 -> NoOpStmt()
    *       | ErrorStmt()
    *       | CJumpStmt()
    *       | JumpStmt()
    *       | HStoreStmt()
    *       | HLoadStmt()
    *       | MoveStmt()
    *       | PrintStmt()
    */
   public String visit(Stmt n, String argu) {
      String _ret=null;

      /* CREATING BLOCK */
         BlockNode newBlock = new BlockNode();
            newBlock.statement = n;
            newBlock.label = null;
            if(setLabel){
               newBlock.label = currentLabel;
               currProcedure.labelBlock.put(currentLabel, newBlock);
               setLabel = false;
            }
            newBlock.successor = null;
            newBlock.successorTwo = null;
            currentBlock.successor = newBlock;
            currentBlock = newBlock;
      /* CREATING BLOCK */

      n.f0.accept(this, argu);//The Statement

      return _ret;
   }

   /**
    * f0 -> "NOOP"
    */
   public String visit(NoOpStmt n, String argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      
      /* PRINT */
         if(PRINT){
            System.out.println("NOOP");
         }
      /* PRINT */

      return _ret;
   }

   /**
    * f0 -> "ERROR"
    */
   public String visit(ErrorStmt n, String argu) {
      String _ret=null;
      n.f0.accept(this, argu);

      /* PRINT */
         if(PRINT){
            System.out.println("ERROR");
         }  
      /* PRINT */

      return _ret;
   }

   /**
    * f0 -> "CJUMP"
    * f1 -> Temp()
    * f2 -> Label()
    */
   public String visit(CJumpStmt n, String argu) {
      String _ret=null;
      
      /* PRINT */
         if(PRINT){
            System.out.print("CJUMP " );
         }
      /* PRINT */

      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, "CJUMP");

      /* PRINT newline */
         if(PRINT){
            System.out.println("");
         }
      /* PRINT newline */

      return _ret;
   }

   /**
    * f0 -> "JUMP"
    * f1 -> Label()
    */
   public String visit(JumpStmt n, String argu) {
      String _ret=null;
      
      /* PRINT */
         if(PRINT){
            System.out.print("JUMP ");
         }  
      /* PRINT */

      n.f0.accept(this, argu);
      n.f1.accept(this, "JUMP");

      /* PRINT newline */
         if(PRINT){
            System.out.println("");
         }
      /* PRINT newline */

      return _ret;
   }

   /**
    * f0 -> "HSTORE"
    * f1 -> Temp()
    * f2 -> IntegerLiteral()
    * f3 -> Temp()
    */
   public String visit(HStoreStmt n, String argu) {
      String _ret=null;
      
      /* PRINT */
         if(PRINT){
            System.out.print("HSTORE ");
         }
      /* PRINT */

      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);

      /* PRINT */
         if(PRINT){
            System.out.print(n.f2.f0.toString() + " ");
         }
      /* PRINT */

      n.f3.accept(this, argu);

      /* PRINT newline */
         if(PRINT){
            System.out.println("");
         }
      /* PRINT newline */

      return _ret;
   }

   /**
    * f0 -> "HLOAD"
    * f1 -> Temp()
    * f2 -> Temp()
    * f3 -> IntegerLiteral()
    */
   public String visit(HLoadStmt n, String argu) {
      String _ret=null;
      
      /* PRINT */
         if(PRINT){
            System.out.print("HLOAD ");
         }
      /* PRINT */

      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);

      /* PRINT*/
         if(PRINT){
            System.out.println(n.f3.f0.toString());
         }
      /* PRINT*/
      

      return _ret;
   }

   /**
    * f0 -> "MOVE"
    * f1 -> Temp()
    * f2 -> Exp()
    */
   public String visit(MoveStmt n, String argu) {
      String _ret=null;
      
      /* PRINT */
         if(PRINT){
            System.out.print("MOVE " );
         }
      /* PRINT */

      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);

      /* PRINT newline */
         if(PRINT){
            System.out.println("");
         }
      /* PRINT newline */

      return _ret;
   }

   /**
    * f0 -> "PRINT"
    * f1 -> SimpleExp()
    */
   public String visit(PrintStmt n, String argu) {
      String _ret=null;
      
      /* PRINT */
         if(PRINT){
            System.out.print("PRINT ");
         }
      /* PRINT */

      n.f0.accept(this, argu);
      n.f1.accept(this, argu);

      /* PRINT newline */
         if(PRINT){
            System.out.println("");
         }
      /* PRINT newline */

      return _ret;
   }

   /**
    * f0 -> Call()
    *       | HAllocate()
    *       | BinOp()
    *       | SimpleExp()
    */
   public String visit(Exp n, String argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "BEGIN"
    * f1 -> StmtList()
    * f2 -> "RETURN"
    * f3 -> SimpleExp()
    * f4 -> "END"
    */
   public String visit(StmtExp n, String argu) {
      String _ret=null;

      /* PRINT */
         if(PRINT){
            System.out.println("BEGIN ");
         }
      /* PRINT */

      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);

      /* PRINT */
         if(PRINT){
            System.out.print("RETURN ");
         }
      /* PRINT */

      n.f3.accept(this, argu);
      n.f4.accept(this, argu);

      /* PRINT */
         if(PRINT){
            System.out.println("END");
         }
      /* PRINT */
      return _ret;
   }

   /**
    * f0 -> "CALL"
    * f1 -> SimpleExp()
    * f2 -> "("
    * f3 -> ( Temp() )*
    * f4 -> ")"
    */
   public String visit(Call n, String argu) {
      String _ret=null;
      
      /* PRINT */
         if(PRINT){
            System.out.print("CALL " );
         }
      /* PRINT */

      n.f0.accept(this, argu);
      n.f1.accept(this, argu);

      /* PRINT */
         if(PRINT){
            System.out.print("(");
         }
      /* PRINT */

      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);

      /* PRINT */
         if(PRINT){
            System.out.print(")");
         }
      /* PRINT */

      

      return _ret;
   }

   /**
    * f0 -> "HALLOCATE"
    * f1 -> SimpleExp()
    */
   public String visit(HAllocate n, String argu) {
      String _ret=null;
      
      /* PRINT */
         if(PRINT){
            System.out.print("HALLOCATE ");
         }
      /* PRINT */

      n.f0.accept(this, argu);
      n.f1.accept(this, argu);

      return _ret;
   }

   /**
    * f0 -> Operator()
    * f1 -> Temp()
    * f2 -> SimpleExp()
    */
   public String visit(BinOp n, String argu) {
      String _ret=null;

      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);

      return _ret;
   }

   /**
    * f0 -> "LE"
    *       | "NE"
    *       | "PLUS"
    *       | "MINUS"
    *       | "TIMES"
    *       | "DIV"
    */
   public String visit(Operator n, String argu) {
      String _ret=null;
      n.f0.accept(this, argu);

      /* PRINT */
         if(PRINT){
            if(n.f0.which==0)System.out.print("LE " );
            if(n.f0.which==1)System.out.print("NE " );
            if(n.f0.which==2)System.out.print("PLUS " );
            if(n.f0.which==3)System.out.print("MINUS " );
            if(n.f0.which==4)System.out.print("TIMES " );
            if(n.f0.which==5)System.out.print("DIV " );
         }
      /* PRINT */

      return _ret;
   }

   /**
    * f0 -> Temp()
    *       | IntegerLiteral()
    *       | Label()
    */
   public String visit(SimpleExp n, String argu) {
      String _ret=null;
      n.f0.accept(this, "SIMPLE_EXP");
      return _ret;
   }

   /**
    * f0 -> "TEMP"
    * f1 -> IntegerLiteral()
    */
   public String visit(Temp n, String argu) {
      String _ret=null;
      
      /* PRINT */
         if(PRINT){
            System.out.print("TEMP " + n.f1.f0.toString() + " ");
         }
      /* PRINT */

      n.f0.accept(this, argu);
      n.f1.accept(this, "TEMP_NUMBER");

      
      return _ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public String visit(IntegerLiteral n, String argu) {
      String _ret=null;
      n.f0.accept(this, argu);

      /* PRINT */
         if(PRINT && argu!=null && argu.equals("SIMPLE_EXP")){
            System.out.print(n.f0.toString() + " ");
         }
      /* PRINT */
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public String visit(Label n, String argu) {
      String _ret=null;
      n.f0.accept(this, argu);

      /* LABELLING BLOCKNODE */
         if(INIT && argu!=null && argu.equals("ADD_LABEL_TO_BLOCKNODE")){
            currentLabel = n.f0.toString();
            setLabel = true;
         }
      /* LABELLING BLOCKNODE */

      /* PRINT */
         if(PRINT){
            System.out.print(n.f0.toString() + " ");
         }
      /* PRINT */

      return _ret;
   }

}
