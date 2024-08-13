package visitor;
import syntaxtree.*;
import java.util.*;

public class BlockNode{
   Stmt statement;
   Set<Integer> def = new HashSet<Integer>();
   Set<Integer> use = new HashSet<Integer>();
   Set<Integer> in  = new HashSet<Integer>();
   Set<Integer> out = new HashSet<Integer>();
   
   String      label;
   BlockNode   successor;
   BlockNode   successorTwo;
}