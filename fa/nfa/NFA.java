package fa.nfa;

import fa.State;
import fa.dfa.DFA;
import fa.dfa.DFAState;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/*
 * NFA class must implement fa.nfa.NFAInterface interface.
 * Make sure to implement all methods inherited from those interfaces.
 * You have to add instance variables representing NFA elements.
 * You can also write additional methods which must be private,
 * i.e., only helper methods.
 *
 * @author Ron Lowies
 */
public class NFA implements NFAInterface {

    private Set<NFAState> states,eClosure;
    private NFAState start;
    private Queue<Set<NFAState>> queue;

    @Override
    public DFA getDFA() {
        queue = new LinkedList<>();
        queue.add(eClosure(start)); //Start the queue with the first state
        DFA dfa = new DFA();        //Create a dfa
        while(!queue.isEmpty()) {   //Iterate over queue elements
           for(NFAState nState : queue.remove()) { //Each element is a set containing states
                if(dfa.getStartState() == null) {  //Add start state
                    dfa.addStartState(nState.getName());
                }
                dfa.addState(nState.getName());
                /*TODO: Implement NFA methods to construct states needed for transition table
                 * For example: if NFA has states S,M,F
                 * has transitions: SaS SaM SbS MaM MaF MbM MbF
                 * and E(S) = { S }
                 * dfa states found through transistion table are
                 * { S }
                 * { S, M }
                 * { S, M, F}
                 * Need to be used to create the DFA.
                 */
           }
        }
        return null;
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        return null;
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
        if(s == null) {
           return eClosure;
        }
        if(!s.isVisited()) {
            eClosure.add(s);
            s.setVisited(true);
        }

        return eClosure(s.getTo('e'));
    }

    @Override
    public void addStartState(String name) {

    }

    @Override
    public void addState(String name) {

    }

    @Override
    public void addFinalState(String name) {

    }

    @Override
    public void addTransition(String fromState, char onSymb, String toState) {

    }

    @Override
    public Set<? extends State> getStates() {
        return null;
    }

    @Override
    public Set<? extends State> getFinalStates() {
        return null;
    }

    @Override
    public State getStartState() {
        return null;
    }

    @Override
    public Set<Character> getABC() {
        return null;
    }
}
