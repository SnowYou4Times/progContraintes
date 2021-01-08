package org.progC;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CSP {

    private final List<NodeCouple> graph;

    public CSP(List<NodeCouple> graph) {
        this.graph = graph;
    }

    public void removeSetNumberOfCouple(int nbOfCoupleToRemove) {
        for(int i = nbOfCoupleToRemove; i > 0; i--) {
            int randomInt = (int) (Math.random() * (graph.size()));
            // System.out.println("randomInt = "+randomInt+" / nbCoupleToRemove = "+nbOfCoupleToRemove+" / graph.size = "+(graph.size()));
            if (randomInt >= 0 && randomInt < graph.size()) {
                graph.remove(randomInt);
            }
            else {
                System.out.println("RANDOM INT OUT OF BOUND");
            }
        }
    }

    public void remoteSetNumberOfConstraints(double constraintHardness) {
        for(NodeCouple nodeCouple : graph) {
            int nbConstraints = nodeCouple.getFirstNode().getDomainSize() * nodeCouple.getSecondNode().getDomainSize();
            int nbConstraintsToRemove = (int) (nbConstraints * constraintHardness);
            nodeCouple.remoteSetNumberOfConstraints(nbConstraintsToRemove);
        }
    }

    public List<NodeCouple> getGraph() {
        return graph;
    }

    public List<Node> getAllNodes() {
        List<Node> nodes = new ArrayList<>();
        for (NodeCouple nc : graph) {
            if(!nodes.contains(nc.getFirstNode())) { nodes.add(nc.getFirstNode()); }
            if(!nodes.contains(nc.getSecondNode())) { nodes.add(nc.getSecondNode()); }
        }
        return nodes.stream()
                .sorted(Comparator.comparingInt(Node::getNodeID))
                .collect(Collectors.toList());
    }

    public List<NodeCouple> getAllCoupleFrom(int nodeID) {
        List<NodeCouple> nodeCouples = new ArrayList<>();
        for (NodeCouple nc : graph) {
            if (nc.getFirstNode().getNodeID() == nodeID) { nodeCouples.add(nc); }
        }
        return nodeCouples;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("- - - - -Graph- - - - -\n");
        for(NodeCouple nodeCouple : graph) {
            string.append(nodeCouple.getFirstNode().toString());
            string.append("\n");
            string.append(nodeCouple.getSecondNode().toString());
            string.append("\n");
            string.append(nodeCouple.toString());
            string.append("\n- - - - -\n");
        }
        string.append("- - - - END - - - -\n\n");
        return string.toString();
    }
}
