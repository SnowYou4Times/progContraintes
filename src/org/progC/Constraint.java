package org.progC;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Constraint {

    private int firstNode;
    private int secondNode;
    private String constraintType;

    private static final List<String> constraintsType = Arrays.asList(">", "<", "==", ">=", "=<", "!=");

    private String _createConstraints() {
        Random rand = new Random();
        return this.firstNode + " " + constraintsType.get(rand.nextInt(constraintType.length())) + " "
                + this.secondNode;
    }

    public Constraint(int firstNode, int secondNode) {
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.constraintType = _createConstraints();
    }

    public Constraint(int firstNode, int secondNode, String constraintType) {
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.constraintType = constraintType;
    }

    public int getFirstNode() {
        return firstNode;
    }

    public void setFirstNode(int firstNode) {
        this.firstNode = firstNode;
    }

    public int getSecondNode() {
        return secondNode;
    }

    public void setSecondNode(int secondNode) {
        this.secondNode = secondNode;
    }

    public String getConstraintType() {
        return constraintType;
    }

    public void setConstraintType(String constraintType) {
        this.constraintType = constraintType;
    }

    @Override
    public String toString() {
        return "Constraint{" +
                "firstNode=" + firstNode +
                ", secondNode=" + secondNode +
                ", constraintType='" + constraintType + '\'' +
                '}';
    }
}
