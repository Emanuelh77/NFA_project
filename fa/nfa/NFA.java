package fa.nfa;

import fa.State;
import fa.dfa.DFA;
import fa.dfa.DFAState;

import java.util.*;

/*
 * NFA class must implement fa.nfa.NFAInterface interface.
 * Make sure to implement all methods inherited from those interfaces.
 * You have to add instance variables representing NFA elements.
 * You can also write additional methods which must be private,
 * i.e., only helper methods.
 *
 * @author Ron Lowies
 * @author Emanuel Hernandez
 */
public class NFA implements NFAInterface {

    private Set<NFAState> states,eClosure;
    private NFAState start;
    private Queue<Set<NFAState>> queue;
    private Set<Character> Sigma;

    public NFA() {
        states = new LinkedHashSet<>();
        eClosure = new LinkedHashSet<>();
        Sigma = new HashSet<Character>();			//alphabet
    }

    @Override
    public DFA getDFA() {
        queue = new LinkedList<>();
        //queue.add(eClosure(start)); //Start the queue with the first state
        for(NFAState st : states) { //Fill queue
            Set<NFAState> dSet = new LinkedHashSet<>();
            dSet.add(st);
            queue.add(dSet);
        }


        DFA dfa = new DFA();        //Create a dfa
        while(!queue.isEmpty()) {   //Iterate over queue elements
           for(NFAState nState : queue.remove()) { //Each element is a set containing states
                
                	if(nState.getName().equals(start.getName())) { //Make sure the DFA doesn't write the start state twice.
                		dfa.addStartState(getStartState().getName());
                	} else {
                		dfa.addState(nState.getName());
                	}
                
                /*TODO: Implement NFA methods to construct states needed for transition table
                 * For example: if NFA has states S,M,F
                 * has transitions: SaS SaM SbS MaM MaF MbM MbF
                 * and E(S) = { S }
                 * dfa states found through transistion table areDFAState.
                 * { S }
                 * { S, M }
                 * { S, M, F}
                 * Need to be used to create the DFA.
                 */
           }
        }
        return dfa;
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
    	
   
        return from.getTo(onSymb);
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
    	
        if(s.getTo('e') != null) {
        	for (NFAState st : s.getTo('e')) {
        		eClosure.add(st);
        		eClosure(st);
        	}
        }
		return eClosure;
    }

    @Override
    public void addStartState(String name) {
        start = new NFAState(name);
        helper(start);
    }

    @Override
    public void addState(String name) {
        NFAState stateToAdd = new NFAState(name);
        helper(stateToAdd);
    }

    @Override
    public void addFinalState(String name) {
        NFAState finalState = new NFAState(name, true);
        helper(finalState);
    }
    
    /**
     * Checks if the given state is already in the list
     * Appends to list if it is not already there.
     * 
     * @param x state to check
     */
    private void helper(NFAState x) {
    	if(!states.contains(x)) {
            states.add(x);
        }
    }

    @Override
    public void addTransition(String fromState, char onSymb, String toState) {
        getState(fromState).addTransition(onSymb, getState(toState));
        if(Sigma.contains(onSymb) && onSymb != 'e'){
            Sigma.add(onSymb);
        }
    }

    //we needed this method to be able to grab instead of the string, the NFAState itself
    private NFAState getState(String name){
        NFAState stateToGet = null;
        for(NFAState state : states){
            if(state.getName().equals(name)) {
                stateToGet = state;
                break;
            }
        }
        return stateToGet;
    }

    @Override
    public Set<? extends State> getStates() {
        return states;
    }

    @Override
    public Set<? extends State> getFinalStates() {
        Set<NFAState> finalStates = new HashSet<NFAState>();
        for (NFAState nfa_state: states){
            if (nfa_state.isFinal()){
                finalStates.add(nfa_state);
            }
        }
        return finalStates;
    }

    @Override
    public State getStartState() {
        return start;
    }

    @Override
    public Set<Character> getABC() {
        return Sigma;
    }
}
