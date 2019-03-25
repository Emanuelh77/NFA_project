package fa.nfa;
import java.util.HashMap;
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
    private HashMap<Character,NFAState> delta;//delta
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
        delta = new HashMap<Character, NFAState>();
    }

    public void addTransition(char onSymb, NFAState toState) {
        delta.put(onSymb, toState);
    }

    public NFAState getTo(char symb){
        NFAState ret = delta.get(symb);
        if(ret == null){
//            System.err.println("ERROR: NFAState.getTo(char symb) returns null on " + symb + " from " + name);
//            System.exit(2);
            //are we supposed to return an empty hashset then???
            return new HashSet<NFAState>();
       }
        else return delta.get(symb);
    }

}
