package org.progC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSPSolverBacktracking {

    private final int domainSize;
    private final int nbNode;
    private final CSP cspToSolve;
    private final int[] solution;
    private final boolean isDebugOn;
    private boolean solutionHasBeenFound;

    public CSPSolverBacktracking(CSP cspToSolve, int nbNode, int domainSize, boolean isDebugOn) {
        this.cspToSolve = cspToSolve;
        this.nbNode = nbNode;
        this.domainSize = domainSize;
        this.solution = new int[nbNode];
        this.isDebugOn = isDebugOn;
        this.solutionHasBeenFound = false;
    }

    public int[] backtrackingSearch() {
        if(cspToSolve.getAllNodes().size() == solution.length) {
            Arrays.fill(solution, -1);
            backtrack(0);
            if(!areAllValuesAssigned()) {
                System.out.println("-!-!-!-! NO SOLUTION FOUND !-!-!-!-");
                System.out.println(Arrays.toString(solution));
                return null;
            }
            else {
                return solution;
            }
        }
        System.out.println("Impossible to solve...");
        System.out.println("All Nodes = "+cspToSolve.getAllNodes().size()+" / solution.length = "+solution.length);
        return null;
    }

    public int[] backtrack(int level) {
        if(solutionHasBeenFound) return null;
        if(areAllValuesAssigned()) {
            printUsefulInformation(true, level, null);
            System.out.println("+++SOLUTION FOUND :");
            System.out.println(Arrays.toString(solution));
            solutionHasBeenFound = true;
            return null;
        }
        else {
            if(isDebugOn) printUsefulInformation(true, level, null);
            Node concernedNode = getNodeFromSolutionIndex(level);
            for (Integer value : concernedNode.getDomain()) {
                if (!solutionHasBeenFound) {
                    solution[level] = value;
                    if (areConstraintsVerified(getAllCoupleWhereNodeIsConcerned(concernedNode), concernedNode)) {
                        backtrack(level+1);
                    }
                    else {
                        break;
                    }
                }
            }
        }
        return null;
    }

    /*
    1.  if allAssigned { return solution }
        else {
            for value in domain Node {
                solution[level] = value
                if constraints are fine {
                    backtrack(level +1)
                }
                else {
                    break;
                }
            }
        }
     */

    public boolean areConstraintsVerified(List<NodeCouple> nodeCouples, Node testedNode) {
        for (NodeCouple nc : nodeCouples) {
            boolean atLeastOneConstraintIsTrue = false;
            if(solution[nc.getFirstNode().getNodeID()-1] != -1 && solution[nc.getSecondNode().getNodeID()-1] != -1) {
                if (testedNode.getNodeID() == nc.getFirstNode().getNodeID()) {
                    if(isDebugOn) System.out.println("/is first node/");
                    for (int[] constraint : nc.getConstraints()) {
                        if(solution[testedNode.getNodeID()-1] == constraint[0] && solution[nc.getSecondNode().getNodeID()-1] == constraint[1]) {
                            atLeastOneConstraintIsTrue = true;
                            if(isDebugOn) System.out.println("- - constraint ("+constraint[0]+") match "+solution[testedNode.getNodeID()-1]+" - -");
                            break;
                        }
                    }
                }
                else { // our testedNode is the second Node in the couple
                    if(isDebugOn) System.out.println("/is second node/");
                    for (int[] constraint : nc.getConstraints()) {
                        if(solution[testedNode.getNodeID()-1] == constraint[1] && solution[nc.getSecondNode().getNodeID()-1] == constraint[0]) {
                            atLeastOneConstraintIsTrue = true;
                            if(isDebugOn) System.out.println("- - constraint ("+constraint[1]+") match "+solution[testedNode.getNodeID()-1]+" - -");
                            break;
                        }
                    }
                }
                if(!atLeastOneConstraintIsTrue) {
                    if(isDebugOn) System.out.println("x x Node"+testedNode.getNodeID()+" has no match for "+solution[testedNode.getNodeID()-1]+" x x");
                    return false;
                }
            }
        }
        if(isDebugOn) System.out.println("+-+-CONSTRAINTS WORKS-+-+");
        return true;
    }

    public List<NodeCouple> getAllCoupleWhereNodeIsConcerned(Node node) {
        List<NodeCouple> allConcernedNodeCouple = new ArrayList<>();
        for (NodeCouple nc : cspToSolve.getGraph()) {
            if(nc.getFirstNode().equals(node) || nc.getSecondNode().equals(node)) {
                allConcernedNodeCouple.add(nc);
            }
        }
        return allConcernedNodeCouple;
    }

    public Node getNodeFromSolutionIndex(int index) {
        return cspToSolve.getAllNodes().get(index);
    }

    public boolean areAllValuesAssigned() {
        return solution[nbNode - 1] != -1;
    }

    public void printUsefulInformation(boolean displaySolution, int currentLevel, Node currentNode) {

        if(displaySolution) System.out.println("Current solution : "+Arrays.toString(solution));
        if(currentLevel != 0) System.out.println("Current level : "+currentLevel);
        if(currentNode != null) System.out.println("Current Node : node"+currentNode.getNodeID());

    }
//    public int getIndexNextUnassignedVariable(int[] currentAssignment) {
//        for(int i = 0; i < currentAssignment.length; i++) {
//            if(currentAssignment[i] == -1) return i;
//        }
//        return -1;
//    }
}