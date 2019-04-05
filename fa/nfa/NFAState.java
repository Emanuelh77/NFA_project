package fa.nfa;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import fa.State;

/*
 * NFAState class must extend fa.State abstract class.
 * If your implementation requires it,
 * you can add additional instance variables and methods to your NFAState class.
 *
 * @author Ron Lowies
 * @author Emanuel Hernandez
 */
public class NFAState extends State {
    private HashMap<Character, HashSet<NFAState>> delta;//delta
    private boolean isFinal;//remembers its type
    private boolean isVisited; //For DFS/BFS Algorithms

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    /**
     * Creates the state
     * @param name of the state
     */
    public NFAState(String name) {
        initDefault(name);
        isFinal = false;
    }

    public NFAState(String name, boolean isFinal){
        initDefault(name);
        this.isFinal = isFinal;
    }

    private void initDefault(String name ) {
        this.name = name;
        isVisited = false;
        delta = new HashMap<Character, HashSet<NFAState>  >();
    }

//    public void addTransition(char onSymb, NFAState toState) {
//    	HashSet<NFAState> temp = new HashSet<NFAState>();
//    	if(delta.containsKey(onSymb)) { //If onSymb already in set
//    	temp = delta.get(onSymb);
//    	temp.add(toState); //Add this transistion to the set available
//    	} else //otherwise start a new one
//    	{
//    		temp.add(toState);
//    	}
//        delta.put(onSymb, temp);
//        
//    }
  public void addTransition(char onSymb, NFAState toState) {
	  HashSet<NFAState> temp = delta.get(onSymb);
	  if(temp == null) {
		  temp = new HashSet<>();
	  }
	  temp.add(toState);
	  delta.put(onSymb, temp);
    
	  
  }

    public HashSet<NFAState> getTo(char symb){
        HashSet<NFAState> ret = delta.get(symb);
        if(ret == null){
//            System.err.println("ERROR: NFAState.getTo(char symb) returns null on " + symb + " from " + name);
//            System.exit(2);
            //are we supposed to return an empty hashset then???
            return null;
       }
        else {
        	return ret;
        }
    }

}
