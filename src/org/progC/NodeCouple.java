package org.progC;

import java.util.ArrayList;
import java.util.List;

public class NodeCouple {

    private final Node firstNode;
    private final Node secondNode;
    private final List<int[]> constraints;

    public NodeCouple(Node firstNode, Node secondNode) {
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.constraints = new ArrayList<>();
        generateConstraints();
        //System.out.println(toString());
    }

    private void generateConstraints() {
        for(int i : firstNode.getDomain()) {
            for(int j : secondNode.getDomain()) {
                int[] duo = new int[2];
                duo[0] = i;
                duo[1] = j;
                constraints.add(duo);
            }
        }
    }

    public void remoteSetNumberOfConstraints(int nbOfConstraintToRemove) {
        for(int i = nbOfConstraintToRemove; i > 0; i--) {
            int randomInt = (int) (Math.random() * (constraints.size()));
            if (randomInt >= 0 && randomInt < constraints.size()) {
                constraints.remove(randomInt);
            }
            else {
                System.out.println("RANDOM INT OUT OF BOUND");
            }
        }
    }

    public Node getFirstNode() {
        return firstNode;
    }

    public Node getSecondNode() {
        return secondNode;
    }

    public List<int[]> getConstraints() {
        return constraints;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("N" + this.firstNode.getNodeID() + " --- N" + this.secondNode.getNodeID());
        string.append(" :");
        for (int[] constraint : constraints) {
            string.append(" (").append(constraint[0]).append("/").append(constraint[1]).append(")");
        }
        return string.toString();
    }
}
