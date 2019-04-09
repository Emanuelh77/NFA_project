package fa.nfa;

import fa.State;
import fa.dfa.DFA;
import fa.dfa.DFAState;

import java.util.*;

import javax.print.attribute.HashAttributeSet;


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

    private HashSet<NFAState> states, eClosure;
    private NFAState start;
    private LinkedList<HashSet<NFAState>> queue;
    private Set<Character> Sigma;

    public NFA() {
        states = new LinkedHashSet<>();
        eClosure = new LinkedHashSet<>();
        Sigma = new LinkedHashSet<Character>();			//alphabet
    }

    @Override
    public DFA getDFA() {
        //Create the DFA
        DFA dfa = new DFA();
        //This set holds the sets.
        Set<Set<NFAState>> seenStateName = new HashSet<Set<NFAState>>();
        //Place holders for state names.
        String currStateName, nextStateName = null;
        boolean isFinal = false;
        HashSet<NFAState> comeUpState = new HashSet<NFAState>();
        //The starting state
        Set<NFAState> dfaState = eClosure(start);

        comeUpState.add(start);
        comeUpState.addAll(dfaState);
        //Initialize queue
        queue = new LinkedList<>();
        queue.add(comeUpState);



        for(NFAState st : comeUpState) {
            isFinal = st.isFinal();
        }
        if (isFinal == true) {
            dfa.addFinalState(comeUpState.toString());
        }
        if(dfa.getStartState() == null) {
            dfa.addStartState(comeUpState.toString());
        }

        while(!queue.isEmpty()){
            HashSet<NFAState> currState = queue.remove();   //Select state from queue
            currStateName = currState.toString();		//get name
            seenStateName.add(currState);
            for(Character symb: Sigma){					//For each alphabet symbol
                isFinal = false;
                HashSet<NFAState> nfaSetStates = new HashSet<NFAState>();
                for(NFAState state: currState) {
                    state.setVisited(true);
                    HashSet<NFAState> next = state.getTo(symb); //set the next
                    seenStateName.add(next);

                    if (next != null ) {
                        for (NFAState nextMove : next) {
                            this.eClosure = nfaSetStates;

                            eClosure(nextMove);
                            if (nextMove.isFinal()) {
                                isFinal = true;
                            }
                            nfaSetStates.add(nextMove);
                        }
                    }

                }
                nextStateName = nfaSetStates.toString();

//                if(dfa.getStartState() == null) {
//                	dfa.addStartState(setName);
//                }

                if(isFinal){
                    if(!seenStateName.contains(nfaSetStates) && !queue.contains(nfaSetStates)){
                        dfa.addFinalState(nextStateName);
                    }
                    dfa.addTransition(currStateName, symb, nextStateName);
                } else if (!seenStateName.contains(nfaSetStates) && !queue.contains(nfaSetStates)) {
                    dfa.addState(nextStateName);
                }

                dfa.addTransition(currStateName, symb, nextStateName);

                if(!seenStateName.contains(nfaSetStates) && !queue.contains(nfaSetStates)){
                    queue.add(nfaSetStates);

                }
            }

        }
        return dfa;

    }

    private boolean contains(Set<DFAState> s, String SName) {
    	
    	for(DFAState dfastate : s) {
    		if(dfastate.getName().equals(SName)); {
    			return true;
    		}
    	}
    	
		return false;
    }

	@Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
    	
   
        return from.getTo(onSymb);
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
    	
        if(!s.getTo('e').equals(new HashSet<NFAState>())) {
        	for (NFAState st : s.getTo('e')) {
                if (!this.eClosure.contains(st)) {
                    this.eClosure.add(st);
                    eClosure(st);
                }
            }
        } else {
        	this.eClosure.add(s);
        }

		return this.eClosure;
    }

    @Override
    public void addStartState(String name) {
        start = new NFAState(name);
        if(start!=null) {
        	helper(start);
        } else {
        	addState(name);
        }
        
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
        if(!Sigma.contains(onSymb) && onSymb != 'e'){
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
        return this.start;
    }

    @Override
    public Set<Character> getABC() {
        return Sigma;
    }
}
