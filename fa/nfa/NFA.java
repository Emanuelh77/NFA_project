package fa.nfa;

import fa.State;
import fa.dfa.DFA;
import fa.dfa.DFAState;

import java.util.*;

import com.sun.xml.internal.ws.api.pipe.NextAction;

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
    private LinkedList<Set<NFAState>> queue;
    private Set<Character> Sigma;

    public NFA() {
        states = new LinkedHashSet<>();
        eClosure = new HashSet<>();
        Sigma = new HashSet<Character>();			//alphabet
    }

    @Override
    public DFA getDFA() {
//        queue = new LinkedList<>();
//        //queue.add(eClosure(start)); //Start the queue with the first state
//        for(NFAState st : states) { //Fill queue
//            Set<NFAState> dSet = new LinkedHashSet<>();
//            dSet.add(st);
//            queue.add(dSet);
//        }
//
//
//        DFA dfa = new DFA();        //Create a dfa
//        while(!queue.isEmpty()) {   //Iterate over queue elements
//           for(NFAState nState : queue.remove()) { //Each element is a set containing states
//
//                	if(nState.getName().equals(start.getName())) { //Make sure the DFA doesn't write the start state twice.
//                		dfa.addStartState(getStartState().getName());
//                	} else {
//                		dfa.addState(nState.getName());
//                	}
//
//                /*TODO: Implement NFA methods to construct states needed for transition table
//                 * For example: if NFA has states S,M,F
//                 * has transitions: SaS SaM SbS MaM MaF MbM MbF
//                 * and E(S) = { S }
//                 * dfa states found through transistion table areDFAState.
//                 * { S }
//                 * { S, M }
//                 * { S, M, F}
//                 * Need to be used to create the DFA.
//                 */
//           }
    	//Create the DFA
    	DFA dfa = new DFA();
    	//This map hold the states and their names.
    	Map<Set<NFAState>, String> seenStateName = new HashMap<>();
    	//Place holders for state names.
    	String currStateName, nextStateName = null;
    	boolean isFinal = false;
    	HashSet<NFAState> comeUpState = new HashSet<NFAState>();
    	//The starting state
        Set<NFAState> dfaState = eClosure(start);
        //Initialize queue
        queue = new LinkedList<>();
        queue.add(dfaState);

        comeUpState.add(start);
        comeUpState.addAll(dfaState);

        for (NFAState state: comeUpState){
            if(state.isFinal()){
                isFinal = true;
            }
        } if (isFinal == true){
            dfa.addFinalState(comeUpState.toString());
        }


        while(!queue.isEmpty()){
            Set<NFAState> currState = queue.remove();   //Select state from queue
            currStateName = currState.toString();		//get name
            seenStateName.put(currState, currStateName);//Store
            for(Character symb: Sigma){					//For each alphabet symbol
                isFinal = false;
                HashSet<NFAState> nfaSetStates = new HashSet<NFAState>();
                for(NFAState state: currState) {
                    HashSet<NFAState> next = state.getTo(symb); //set the next
                    nextStateName = next.toString();
                    seenStateName.put(next, nextStateName);
                    if (next != null) {
                        for (NFAState nextMove : next) {
                            eClosure = nfaSetStates;
                            eClosure(nextMove);
                            if (nextMove.isFinal()) {
                                isFinal = true;
                            }
                            nfaSetStates.add(nextMove);
                        }
                    }
                }
                String setName = nfaSetStates.toString();
                 if(isFinal){
                    if(!seenStateName.containsKey(nfaSetStates) && !queue.contains(nfaSetStates)){
                        dfa.addFinalState(setName);
                    }
                    dfa.addTransition(currStateName, symb, setName);
                 } else if (!contains(dfa.getStates(), currStateName)) {
                     dfa.addState(setName);
                 }
                 dfa.addTransition(currStateName, symb, setName);
                 if(!seenStateName.containsKey(nfaSetStates) && !queue.contains(nfaSetStates)){
                     queue.addFirst(nfaSetStates);
                 }
            }

        }
        return dfa;

//                    nextStateName = next.toString();
//                    seenStateName.put(next, nextStateName);
//                    Set<NFAState> nextEpsilon = new HashSet<>();
//                    for(NFAState nextSingle: next){
//                        nextEpsilon.addAll(eClosure(nextSingle));  //Find the closure of each next
//                            //Store name
//                        isFinal = nextSingle.isFinal();
//                    }
//
//                }
//
//
//                if(dfa.getStartState() == null) { //Start state
//                	dfa.addStartState(currStateName);
//                	dfa.addTransition(currStateName, symb, nextStateName);
//                } else if (!contains(dfa.getStates() , currStateName)) { //All states
//                	dfa.addState(currStateName);
//                	dfa.addTransition(currStateName, symb, nextStateName);
//                	if(isFinal) {
//                		dfa.addFinalState(currStateName);
//                	}

                //TODO find the next NFAState to add to the queue.

    }

    private boolean contains(Set<DFAState> s, String SName) {
    	
    	for(DFAState dfastate : s) {
    		if(dfastate.getName().equals(SName)); {
    			return true;
    		}
    	}
    	
		return false ;
	}
    
// private DFAState getFinal(Set<NFAState> s2) {
//    	
//    	for(DFAState dfastate : s2) {
//    		if(dfastate.isFinal()); {
//    			return dfastate;
//    		}
//    	}
//    	
//		return null;
//	}

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
        } else {
        	eClosure.add(s);
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
        return start;
    }

    @Override
    public Set<Character> getABC() {
        return Sigma;
    }
}
