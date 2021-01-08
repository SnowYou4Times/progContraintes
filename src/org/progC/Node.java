package org.progC;

public class Node {

    private final int nodeID;
    private final int domainSize;
    private final int[] domain;

    public Node(int nodeID, int domainSize) {
        this.nodeID = nodeID;
        this.domainSize = domainSize;
        this.domain = new int[domainSize];
        for(int i = 0; i < domainSize; i++) {
            domain[i] = i+1;
        }
        //System.out.println(toString());
    }

    public int getNodeID() {
        return nodeID;
    }

    public int getDomainSize() {
        return domainSize;
    }

    public int[] getDomain() {
        return domain;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("Node ").append(nodeID);
        string.append(" {");
        for(int domainElem : domain) {
            if (domainElem == domain.length) {
                string.append(domainElem);
            }
            else {
                string.append(domainElem).append(",");
            }
        }
        string.append("} (").append(domainSize).append(")");
        return string.toString();
    }
}
