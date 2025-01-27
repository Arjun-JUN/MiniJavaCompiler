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
public class GJDepthFirst<R,A> implements GJVisitor<R,A> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //
/*------------------------ FLAGS ----------------------------- */
Boolean PRINT = true;
Boolean LE = false;
Boolean NE = false;
Boolean ADD = false;
Boolean SUB = false;
Boolean MUL = false;
Boolean DIV = false;
Integer simpleWhich = -1;
String reg1 = null;
String reg2 = null;
/*------------------------ FLAGS ----------------------------- */


   public R visit(NodeList n, A argu) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeListOptional n, A argu) {
      if ( n.present() ) {
         R _ret=null;
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

   public R visit(NodeOptional n, A argu) {
      if ( n.present() )
         return n.node.accept(this,argu);
      else
         return null;
   }

   public R visit(NodeSequence n, A argu) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeToken n, A argu) { return null; }

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> "MAIN"
    * f1 -> "["
    * f2 -> IntegerLiteral()
    * f3 -> "]"
    * f4 -> "["
    * f5 -> IntegerLiteral()
    * f6 -> "]"
    * f7 -> "["
    * f8 -> IntegerLiteral()
    * f9 -> "]"
    * f10 -> StmtList()
    * f11 -> "END"
    * f12 -> ( SpillInfo() )?
    * f13 -> ( Procedure() )*
    * f14 -> <EOF>
    */
   public R visit(Goal n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu); //MAIN
      if(PRINT){
         System.out.println("\t.text");
         System.out.println("\t.globl main");
         System.out.println("main:");
      }

      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);

      n.f4.accept(this, argu);
      Integer stkSize = 4*(Integer.parseInt((String)n.f5.accept(this, argu)));
      n.f6.accept(this, argu);

      n.f7.accept(this, argu);
      n.f8.accept(this, argu);
      n.f9.accept(this, argu);

      if(PRINT){
         System.out.println("\t\tsw $fp, 0($sp)");
         System.out.println("\t\tsw $ra, -4($sp)");
         System.out.println("\t\tmove $fp, $sp");
         System.out.println("\t\tsubu $sp, $sp, " + (stkSize+8));
      }

      n.f10.accept(this, argu);
      n.f11.accept(this, argu);

      n.f12.accept(this, argu);
      if(PRINT){
         System.out.println("\t\tlw $ra, -4($fp)");
         System.out.println("\t\tlw $fp, 0($fp)");
         System.out.println("\t\taddu $sp, $sp, " + (stkSize+8));
         System.out.println("\t\tj $ra");
         System.out.println("");
      }

      n.f13.accept(this, argu);

      if(PRINT){
         System.out.println("\t.text");
         System.out.println("\t.globl _halloc");
         System.out.println("_halloc:");
         System.out.println("\t\tli $v0, 9");
         System.out.println("\t\tsyscall");
         System.out.println("\t\tj $ra");
         System.out.println("\t.text");
         System.out.println("\t.globl _print");
         System.out.println("_print:");
         System.out.println("\t\tli $v0, 1");
         System.out.println("\t\tsyscall");
         System.out.println("\t\tla $a0, newl");
         System.out.println("\t\tli $v0, 4");
         System.out.println("\t\tsyscall");
         System.out.println("\t\tj $ra");
         System.out.println("\t.data");
         System.out.println("\t.align 0");
         System.out.println("newl:");
         System.out.println("\t\t.asciiz \"\\n\"");
         System.out.println("\t.data");
         System.out.println("\t.align 0");
         System.out.println("str_er:");
         System.out.println("\t\t.asciiz \" ERROR: abnormal termination\\n\"");


      }

      n.f14.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ( ( Label() )? Stmt() )*
    */
   public R visit(StmtList n, A argu) {
      R _ret=null;
      n.f0.accept(this, (A)"PRINT_LABEL");
      return _ret;
   }

   /**
    * f0 -> Label()
    * f1 -> "["
    * f2 -> IntegerLiteral()
    * f3 -> "]"
    * f4 -> "["
    * f5 -> IntegerLiteral()
    * f6 -> "]"
    * f7 -> "["
    * f8 -> IntegerLiteral()
    * f9 -> "]"
    * f10 -> StmtList()
    * f11 -> "END"
    * f12 -> ( SpillInfo() )?
    */
   public R visit(Procedure n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      if(PRINT){
         System.out.println("\t.text");
         System.out.println("\t.globl " + n.f0.f0.toString());
         System.out.println(n.f0.f0.toString() + ":");
      }

      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);

      n.f4.accept(this, argu);
      Integer stkSize = 4*(Integer.parseInt((String)n.f5.accept(this, argu)));
      n.f6.accept(this, argu);

      n.f7.accept(this, argu);
      n.f8.accept(this, argu);
      n.f9.accept(this, argu);

      if(PRINT){
         System.out.println("\t\tsw $fp, 0($sp)");
         System.out.println("\t\tsw $ra, -4($sp)");
         System.out.println("\t\tmove $fp, $sp");
         System.out.println("\t\tsubu $sp, $sp, " + (stkSize+8));
      }

      n.f10.accept(this, argu);
      n.f11.accept(this, argu);
      n.f12.accept(this, argu);

      if(PRINT){
         System.out.println("\t\tlw $ra, -4($fp)");
         System.out.println("\t\tlw $fp, 0($fp)");
         System.out.println("\t\taddu $sp, $sp, " + (stkSize+8));
         System.out.println("\t\tj $ra");
         System.out.println("");
      }
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
    *       | ALoadStmt()
    *       | AStoreStmt()
    *       | PassArgStmt()
    *       | CallStmt()
    */
   public R visit(Stmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, null);
      return _ret;
   }

   /**
    * f0 -> "NOOP"
    */
   public R visit(NoOpStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      if(PRINT){
         System.out.println("\t\tnop");
      }
      return _ret;
   }

   /**
    * f0 -> "ERROR"
    */
   public R visit(ErrorStmt n, A argu) { /* ----------------------------------------------------------------------------------- */
      R _ret=null;
      n.f0.accept(this, argu);

      if(PRINT){
         System.out.println("\t\tla $a0, str_er");
         System.out.println("\t\tsyscall");
         System.out.println("\t\tli $v0, 10");
         System.out.println("\t\tsyscall");
      }
      return _ret;
   }

   /**
    * f0 -> "CJUMP"
    * f1 -> Reg()
    * f2 -> Label()
    */
   public R visit(CJumpStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String reg = (String)
      n.f1.accept(this, argu);
      String label = (String)
      n.f2.accept(this, argu);

      if(PRINT){
         System.out.println("\t\tbeqz " + reg + ", " + label);
      }

      return _ret;
   }

   /**
    * f0 -> "JUMP"
    * f1 -> Label()
    */
   public R visit(JumpStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String label = (String)
      n.f1.accept(this, argu);

      if(PRINT){
         System.out.println("\t\tb " + label);
      }
      return _ret;
   }

   /**
    * f0 -> "HSTORE"
    * f1 -> Reg()
    * f2 -> IntegerLiteral()
    * f3 -> Reg()
    */
   public R visit(HStoreStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String reg = (String)
      n.f1.accept(this, argu);
      String offset = (String)
      n.f2.accept(this, argu);
      String reg_second = (String)
      n.f3.accept(this, argu);

      if(PRINT){
         System.out.println("\t\tsw " + reg_second + ", " + offset + "(" + reg + ")");
      }

      return _ret;
   }

   /**
    * f0 -> "HLOAD"
    * f1 -> Reg()
    * f2 -> Reg()
    * f3 -> IntegerLiteral()
    */
   public R visit(HLoadStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String reg = (String)
      n.f1.accept(this, argu);
      String reg_second = (String)
      n.f2.accept(this, argu);
      String offset = (String)
      n.f3.accept(this, argu);

      if(PRINT){
         System.out.println("\t\tlw " + reg + ", " + offset + "(" + reg_second + ")");
      }

      return _ret;
   }

   /**
    * f0 -> "MOVE"
    * f1 -> Reg()
    * f2 -> Exp()
    */
   public R visit(MoveStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String reg = (String)
      n.f1.accept(this, argu);
      String exp = (String)
      n.f2.accept(this, argu);

      if(LE){
         System.out.println("\t\tsle "+reg+", "+reg1+", "+reg2);
         reg1 = null;
         reg2 = null;
         LE = false;
      }
      else if(NE){
         System.out.println("\t\tsne "+reg+", "+reg1+", "+reg2);
         reg1 = null;
         reg2 = null;
         NE = false;
      }
      else if(ADD){
         System.out.println("\t\taddu "+reg+", "+reg1+", "+reg2);
         reg1 = null;
         reg2 = null;
         ADD = false;
      }
      else if(SUB){
         System.out.println("\t\tsubu "+reg+", "+reg1+", "+reg2);
         reg1 = null;
         reg2 = null;
         SUB = false;
      }
      else if(MUL){
         System.out.println("\t\tmul "+reg+", "+reg1+", "+reg2);
         reg1 = null;
         reg2 = null;
         MUL = false;
      }
      else if(DIV){
         System.out.println("\t\tdiv "+reg+", "+reg1+", "+reg2);
         reg1 = null;
         reg2 = null;
         DIV = false;
      }
      else if(n.f2.f0.which==2/*SimpleExp */ && simpleWhich==1/*Integer */){
         System.out.println("\t\tli "+reg+", "+exp);
      }
      else if(n.f2.f0.which==2/*SimpleExp */ && simpleWhich==2/*label */){
         System.out.println("\t\tla "+reg+", "+exp);
      }
      else if (n.f2.f0.which==2/*SimpleExp */ && simpleWhich==0/*Reg */){
         System.out.println("\t\tmove "+reg+", "+exp);
      }
      else{
         System.out.println("\t\tmove "+reg+", "+exp);
      }
      

      return _ret;
   }

   /**
    * f0 -> "PRINT"
    * f1 -> SimpleExp()
    */
   public R visit(PrintStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String sexp = (String)
      n.f1.accept(this, argu);

      if(PRINT){
         switch(simpleWhich){
            case 0:/* REG */ System.out.println("\t\tmove $a0, " + sexp); break;
            case 1:/* INT */ System.out.println("\t\tli $a0, " + sexp); break;
            case 2:/* LAB */ System.out.println("print a label???"); break;
         }
         System.out.println("\t\tjal _print");
      }

      return _ret;
   }

   /**
    * f0 -> "ALOAD"
    * f1 -> Reg()
    * f2 -> SpilledArg()
    */
   public R visit(ALoadStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String reg = (String)
      n.f1.accept(this, argu);
      String fp = (String)
      n.f2.accept(this, argu);

      if(PRINT){
         System.out.println("\t\tlw " + reg + ", " + fp);
      }

      return _ret;
   }

   /**
    * f0 -> "ASTORE"
    * f1 -> SpilledArg()
    * f2 -> Reg()
    */
   public R visit(AStoreStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String fp = (String)
      n.f1.accept(this, argu);
      String reg = (String)
      n.f2.accept(this, argu);

      if(PRINT){
         System.out.println("\t\tsw " + reg + ", " + fp);
      }

      return _ret;
   }

   /**
    * f0 -> "PASSARG"
    * f1 -> IntegerLiteral()
    * f2 -> Reg()
    */
   public R visit(PassArgStmt n, A argu) {/* ----------------------------------------------------------------------------------- */
      R _ret=null;
      n.f0.accept(this, argu);
      String offset = (String)
      n.f1.accept(this, argu);
      String reg = (String)
      n.f2.accept(this, argu);

      if(PRINT){
         System.out.println("\t\tsw " + reg + ", " + (-1)*(4+(4*(Integer.parseInt(offset)))) + "($sp)");
      }
      return _ret;
   }

   /**
    * f0 -> "CALL"
    * f1 -> SimpleExp()
    */
   public R visit(CallStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String sexp = (String)
      n.f1.accept(this, argu);

      if(PRINT){
         System.out.println("\t\tjalr " + sexp);
      }
      return _ret;
   }

   /**
    * f0 -> HAllocate()
    *       | BinOp()
    *       | SimpleExp()
    */
   public R visit(Exp n, A argu) {
      R _ret = (R) 
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "HALLOCATE"
    * f1 -> SimpleExp()
    */
   public R visit(HAllocate n, A argu) {
      R _ret=(R)"$v0";
      n.f0.accept(this, argu);
      String reg = (String)
      n.f1.accept(this, argu);

      if(PRINT){
         switch(n.f1.f0.which){
            case 0:/* REG */ System.out.println("\t\tmove $a0, " + reg); break;
            case 1:/* INT */ System.out.println("\t\tli $a0, " + reg); break;
            case 2:/* LAB */ System.out.println("load a label???"); break;
         }
         System.out.println("\t\tjal _halloc");
      }

      return _ret;
   }

   /**
    * f0 -> Operator()
    * f1 -> Reg()
    * f2 -> SimpleExp()
    */
   public R visit(BinOp n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      reg1 = (String)
      n.f1.accept(this, argu);
      reg2 = (String)
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
   public R visit(Operator n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      switch(n.f0.which){
         case 0: LE = true; break;
         case 1: NE = true; break;
         case 2: ADD = true; break;
         case 3: SUB = true; break;
         case 4: MUL = true; break;
         case 5: DIV = true; break;
      }
      return _ret;
   }

   /**
    * f0 -> "SPILLEDARG"
    * f1 -> IntegerLiteral()
    */
   public R visit(SpilledArg n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      Integer offset =(-1)*(8 + 4*(Integer.parseInt((String)n.f1.accept(this, argu))));
      
      _ret = (R) (offset+"($fp)");

      return _ret;
   }

   /**
    * f0 -> Reg()
    *       | IntegerLiteral()
    *       | Label()
    */
   public R visit(SimpleExp n, A argu) {
      R _ret = (R)
      n.f0.accept(this, argu);
      switch (n.f0.which){
         case 0: simpleWhich = 0; break;
         case 1: simpleWhich = 1; break;
         case 2: simpleWhich = 2; break;
      }
      return _ret;
   }

   /**
    * f0 -> "a0"
    *       | "a1"
    *       | "a2"
    *       | "a3"
    *       | "t0"
    *       | "t1"
    *       | "t2"
    *       | "t3"
    *       | "t4"
    *       | "t5"
    *       | "t6"
    *       | "t7"
    *       | "s0"
    *       | "s1"
    *       | "s2"
    *       | "s3"
    *       | "s4"
    *       | "s5"
    *       | "s6"
    *       | "s7"
    *       | "t8"
    *       | "t9"
    *       | "v0"
    *       | "v1"
    */
   public R visit(Reg n, A argu) {
      R _ret=(R)"$";
      n.f0.accept(this, argu);
      switch(n.f0.which){
         case 0: _ret += "a0"; break;
         case 1: _ret += "a1"; break;
         case 2: _ret += "a2"; break;
         case 3: _ret += "a3"; break;
         case 4: _ret += "t0"; break;
         case 5: _ret += "t1"; break;
         case 6: _ret += "t2"; break;
         case 7: _ret += "t3"; break;
         case 8: _ret += "t4"; break;
         case 9: _ret += "t5"; break;
         case 10: _ret += "t6"; break;
         case 11: _ret += "t7"; break;
         case 12: _ret += "s0"; break;
         case 13: _ret += "s1"; break;
         case 14: _ret += "s2"; break;
         case 15: _ret += "s3"; break;
         case 16: _ret += "s4"; break;
         case 17: _ret += "s5"; break;
         case 18: _ret += "s6"; break;
         case 19: _ret += "s7"; break;
         case 20: _ret += "t8"; break;
         case 21: _ret += "t9"; break;
         case 22: _ret += "v0"; break;
         case 23: _ret += "v1"; break;
      };
      return _ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public R visit(IntegerLiteral n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      _ret = (R) n.f0.toString();
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public R visit(Label n, A argu) {
      R _ret= (R) n.f0.tokenImage;
      n.f0.accept(this, argu);
      if((String)argu != null && argu.equals("PRINT_LABEL")){
         if(PRINT){
            System.out.println(n.f0.tokenImage + ":");
         }
      }
      return _ret;
   }

   /**
    * f0 -> "//"
    * f1 -> SpillStatus()
    */
   public R visit(SpillInfo n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> <SPILLED>
    *       | <NOTSPILLED>
    */
   public R visit(SpillStatus n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

}
