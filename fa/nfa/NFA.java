package fa.nfa;

import fa.State;
import fa.dfa.DFA;

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

    @Override
    public DFA getDFA() {
        return null;
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        return null;
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
        return null;
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
