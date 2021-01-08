package org.progC;

import java.util.ArrayList;
import java.util.List;

public class AC3Algorithm {

    private CSP csp;
    private List<AgendaElement> arcsList;
    private List<AgendaElement> agenda;

    public AC3Algorithm(CSP csp) {
        this.csp = csp;
        this.arcsList = new ArrayList<>();
        this.agenda = new ArrayList<>();
    }

    public void addAllArcsToArcsList() {
        for(NodeCouple nc : csp.getGraph()) {
            for(int[] constraint : nc.getConstraints()) {
                AgendaElement elem = new AgendaElement(nc.getFirstNode(), nc.getSecondNode(), constraint);
                arcsList.add(elem);
                int[] reverseConstraint = new int[2];
                reverseConstraint[0] = constraint[1];
                reverseConstraint[1] = constraint[0];
                AgendaElement reversedElem = new AgendaElement(nc.getSecondNode(), nc.getFirstNode(), reverseConstraint);
                arcsList.add(reversedElem);
            }
        }
    }

    public void addAllArcsToAgenda() {
        this.agenda.addAll(arcsList);
    }

    public void useAC3Algorithm() {
        //TODO : FINISH THE ALGORITHM
        while (agenda.size() > 0) {
            AgendaElement elem = agenda.get(0);
            boolean domainHasChanged = false;
            boolean hasAPossibleValue = false;
            for (Integer firstDomainValue : elem.getFirstConcernedNode().getDomain()) {
                for (Integer secondDomainValue : elem.getSecondConcernedNode().getDomain()) {
                    if (firstDomainValue == elem.getValuesTuple()[0] && secondDomainValue == elem.getValuesTuple()[1]) {
                        hasAPossibleValue = true;

                        // left here, still not finished
                    }
                }
            }

            agenda.remove(0);
        }
    }

    public CSP getSimplifiedCSP() {

        addAllArcsToArcsList();
        addAllArcsToAgenda();
        useAC3Algorithm();
        return this.csp;
    }
}

class AgendaElement {

    private final Node firstConcernedNode;
    private final Node secondConcernedNode;
    private final int[] valuesTuple;

    public AgendaElement(Node firstConcernedNode, Node secondConcernedNode, int[] valuesTuple) {
        this.firstConcernedNode = firstConcernedNode;
        this.secondConcernedNode = secondConcernedNode;
        this.valuesTuple = valuesTuple;
    }

    public Node getFirstConcernedNode() {
        return firstConcernedNode;
    }

    public Node getSecondConcernedNode() {
        return secondConcernedNode;
    }

    public int[] getValuesTuple() {
        return valuesTuple;
    }
}