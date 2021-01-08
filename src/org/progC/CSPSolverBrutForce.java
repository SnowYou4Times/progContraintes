package org.progC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSPSolverBrutForce {

    private final int domainSize;
    private final int nbNode;
    private final CSP cspToSolve;
    private final int[] solution;
    private final boolean isDebugOn;

    CSPSolverBrutForce(CSP cspToSolve, int nbNode, int domainSize, boolean isDebugOn) {
        this.cspToSolve = cspToSolve;
        this.nbNode = nbNode;
        this.domainSize = domainSize;
        this.solution = new int[nbNode];
        this.isDebugOn = isDebugOn;
    }

    public void findSolution() {
        List<Node> allNodes = cspToSolve.getAllNodes();

        for(int i = 0; i < solution.length; i++) {
            // System.out.println("nbNode "+allNodes.size());
            solution[i] = allNodes.get(i).getDomain()[0];
        }
        boolean solutionFound = isVerifyingConstraints(solution);
        int totalLeaf = totalNbLeaf(domainSize, 1);
        if (isDebugOn) {
            System.out.println("Solution initiation : "+Arrays.toString(solution));
            System.out.println("Total leaves in the tree : "+totalLeaf);
        }
        findASolution(allNodes, solution, nbNode-1);
    }

    public void findASolution(List<Node> allNodes, int[] solution, int index) {

        //Debug mode is set in Main.java
        if (isDebugOn) {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append("Informations : index = ").append(index).append(" solution = [");
            for (int i = 0; i < nbNode; i++) {
                if (i == nbNode-1) {strBuilder.append(solution[i]).append("]"); }
                else {
                    strBuilder.append(solution[i])
                            .append("][");
                }
            }
            strBuilder.append(" allNodeSize = ").append(allNodes.size());
            System.out.println(strBuilder.toString());
        }

        if (isVerifyingConstraints(solution)) {
            System.out.print("-- Solution is : ");
            System.out.println(Arrays.toString(solution)+" --");
        }
        else {
            if (solution[index] == allNodes.get(index).getDomain()[allNodes.get(index).getDomainSize()-1]) {
                if (index != 0) {
                    findASolution(allNodes, solution, index-1);
                }
                else {
                    System.out.println("-- No solution possible --");
                }
            }
            else {
                if (isDebugOn) System.out.println("Ajout +1 a "+solution[index]);

                solution[index] = solution[index] + 1;
                if (index < nbNode-1) {
                    setSolutionOnLeftToOne(index);
                    findASolution(allNodes, solution, index+1);
                }
                else { findASolution(allNodes, solution, index); }
            }
        }
    }

    public void setSolutionOnLeftToOne(int index) {

        for (int i = 0; i < solution.length; i++) {
            if (i > index) {
               solution[i] = 1;
            }
        }
    }

    public boolean isVerifyingConstraints(int[] solution) {
        for (Node node : cspToSolve.getAllNodes()) {
            List<NodeCouple> nodeCouples = cspToSolve.getAllCoupleFrom(node.getNodeID());

            for (NodeCouple nodeCouple : nodeCouples) {

                int[] constraint = new int[2];
                constraint[0] = solution[nodeCouple.getFirstNode().getNodeID()-1];
                constraint[1] = solution[nodeCouple.getSecondNode().getNodeID()-1];
                boolean atLeastOneConstraintMatch = false;
                for (int[] constraintDuo : nodeCouple.getConstraints()) {

                    if (constraintDuo[0] == constraint[0] && constraintDuo[1] == constraint[1]) {
                        atLeastOneConstraintMatch = true;
                        break;
                    }
                    else {
                        if(isDebugOn) {
                            System.out.print("Constraint ["+constraint[0]+"]["+constraint[1]+"] different ");
                            System.out.println("ConstraintDuo ["+constraintDuo[0]+"]["+constraintDuo[1]+"]");
                            //System.out.println(nodeCouple.toString());
                        }
                    }
                }
                if (!atLeastOneConstraintMatch) return false;
            }
        }
        return true;
    }

    public int totalNbLeaf(int currentNb, int step) {
        if (step == (nbNode - 1)) {
            return nbNode;
        }
        else {
            return totalNbLeaf(currentNb * domainSize, step + 1);
        }
    }

    public int[] getSolution() {
        return solution;
    }
}
