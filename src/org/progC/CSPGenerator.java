package org.progC;

import java.util.ArrayList;
import java.util.List;

public class CSPGenerator {


    private final int nbVariable; // nb de Node
    private final int domainSize; // {1,2,3} = 3
    private final double density; //rapport entre nombre d'arc / nb arc si le CSP était complet
    private final double constraintHardness; // taux de couple conservé entre tous les couples possibles entre deux nodes
    // 1 et 2 de domaine 3 = (1,1)(1,2)(1,3) (2,1)(2,2)(2,3) (3,1)(3,2)(3,3)
    private final List<Node> nodeList;
    private final List<NodeCouple> nodeCoupleList;

    public CSPGenerator(int nbVariable, int domainSize, double density, double constraintHardness) {
        this.nbVariable = nbVariable;
        this.domainSize = domainSize;
        this.density = density;
        this.constraintHardness = constraintHardness;
        this.nodeList = new ArrayList<>();
        this.nodeCoupleList = new ArrayList<>();
    }

    public CSP generateCSP() {
        CSP generatedCSP;
        for(int nbNewNode = nbVariable; nbNewNode > 0; nbNewNode--) {
            Node node = new Node(nbNewNode, domainSize);
            nodeList.add(node);
        }
        int maxDensity = 0;
        for (int i = (nbVariable - 1); i > 0; i--) {
            maxDensity += i;
        }
        for (int n = 0; n < nodeList.size(); n++) {
            for (int n2 = n + 1; n2 < nodeList.size(); n2++) {
                NodeCouple nodeCouple = new NodeCouple(nodeList.get(n), nodeList.get(n2));
                nodeCoupleList.add(nodeCouple);
            }
        }
        generatedCSP = new CSP(nodeCoupleList);
        int nbArcsToKeep = (int) (maxDensity*density);
        generatedCSP.removeSetNumberOfCouple(nbArcsToKeep);
        generatedCSP.remoteSetNumberOfConstraints(constraintHardness);
        return generatedCSP;
    }

    public int getNbVariable() {
        return nbVariable;
    }

    public int getDomainSize() {
        return domainSize;
    }

    public double getDensity() {
        return density;
    }

    public double getConstraintHardness() {
        return constraintHardness;
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    public List<NodeCouple> getNodeCoupleList() {
        return nodeCoupleList;
    }
}
