package visitor;
import syntaxtree.*;
import java.util.*;

public class ProcedureNode{
   String   label;
   int      args;
   int      stackSpace;//spilled temps: DONE ; (10 for)CALL: DONE ; (8 for)Proc : DONE ; spilled args: DONE
   int      callArgs;

   Boolean  DEBUG_SPILL = false;
/*-------------------------------------------------------------------------------*/
   HashMap<Integer,Integer>   liveStart = new HashMap<Integer,Integer>();
   HashMap<Integer,Integer>   liveEnd   = new HashMap<Integer,Integer>();
   Vector<Interval>       liveIntervals = new Vector<Interval>();
/*-------------------------------------------------------------------------------*/
   PriorityQueue<Interval>    active    = new PriorityQueue<Interval>( new Comparator<Interval>(){
         public int compare(Interval i1, Interval i2){
            return i2.end - i1.end;
         }
      });
/*-------------------------------------------------------------------------------*/ 
   HashMap<Integer,String>    register  = new HashMap<Integer,String>();
   HashMap<Integer,Integer>   stackLocation    = new HashMap<Integer,Integer>();
/*-------------------------------------------------------------------------------*/
   Queue<String> freeRegs = new LinkedList<>(Arrays.asList("s0","s1","s2","s3","s4","s5","s6","s7","t0","t1","t2","t3","t4","t5","t6","t7","t8","t9"));
/*-------------------------------------------------------------------------------*/
   BlockNode enterBlock;
   Vector<BlockNode>             blockList  = new Vector<BlockNode>();
   HashMap<String, BlockNode>    labelBlock = new HashMap<String, BlockNode>();
/*-------------------------------------------------------------------------------*/
   Boolean isSpilled = false;
/*-------------------------------------------------------------------------------*/

   void GetLiveIntervals(){
      liveIntervals.clear();

      for(Map.Entry<Integer,Integer> entry : liveEnd.entrySet()){
         Interval inter = new Interval();
         inter.temp  = entry.getKey();
         inter.start = 0;
         if(inter.temp<4)liveStart.put(entry.getKey(),0);
         else inter.start = liveStart.get(entry.getKey());
         inter.end   = entry.getValue();
         if(inter.temp >=4 && inter.temp < args){
            register.put(inter.temp,"SPILLEDARG");
            stackLocation.put(inter.temp,inter.temp-4);
         }
         else{
            liveIntervals.add(inter);
         }
      }
      Collections.sort(liveIntervals, new Comparator<Interval>(){
         public int compare(Interval i1, Interval i2){
            return i1.start - i2.start;
         }
      });
   }
   
   public void LinearScan(){
      active.clear();
      for(int index = 0 ; index < liveIntervals.size() ; index++){
         Interval interval = liveIntervals.get(index);
         ExpireOldIntervals(interval);
         if(active.size() == 18){
            SpillAtInterval(interval);
         }
         else{
            String reg = freeRegs.remove();
            register.put(interval.temp,reg);
            active.add(interval);
         }
      }
   }

   void ExpireOldIntervals(Interval interval){
      PriorityQueue<Interval> temp = new PriorityQueue<Interval>(active);
      for(Interval j : active){
         if(j.end >= interval.start){
            continue;
         }
         temp.remove(j);
         freeRegs.add(register.get(j.temp));
      }
      active.clear();
      active.addAll(temp);
   }

   void SpillAtInterval(Interval interval){
      Interval spill = active.peek();
      if(DEBUG_SPILL){
         System.out.println("& & Spilling at interval: "+interval.temp);
         System.out.println("& & Spilling at spill   : "+spill.temp);
      }
      stackSpace++;
      isSpilled = true;
      if(spill.end > interval.end){
         register.put(interval.temp,register.get(spill.temp));
         register.put(spill.temp,"SPILLEDARG");
         
         active.remove(spill);
         active.add(interval);
      }
      else{
         register.put(interval.temp,"SPILLEDARG");
      }
   }
}