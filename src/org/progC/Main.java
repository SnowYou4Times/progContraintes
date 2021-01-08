package org.progC;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        int nbNodes = 6; // can't be greater than 9 while using brute method (Else StackOverflowError occurs)
        int domainSize = 15; // if nbNodes = 9, domainSize max = 3
        double density = 0; // the higher the number, the less link between node there is (0 to 1)
        double constraintHardness = 0.5; // the higher this number, the stricter constraints will be (0 to 1)

        // activate to see information during process
        // WARNING : Don't set to true on a large scale graph, else there is a StackOverflowError
        boolean isDebugOn = false;

        //solver1.findSolution();

        int[] solutionFound = null;
        int nbTimeExecuted = 0;
        long totalElapsedTime = 0;
        int nbSolutionFound = 0;

        while (nbSolutionFound < 10) {
            CSPGenerator generator = new CSPGenerator(nbNodes, domainSize, density, constraintHardness);
            CSP generatedCSP = generator.generateCSP();
            //System.out.println(generatedCSP.toString());

            //CSPSolverBrutForce solver1 = new CSPSolverBrutForce(generatedCSP, nbNodes, domainSize, isDebugOn);
            CSPSolverBacktracking solver2 = new CSPSolverBacktracking(generatedCSP, nbNodes, domainSize, isDebugOn);
            System.out.println("Starting Backtracking search :");
            long start = System.nanoTime();
            solutionFound = solver2.backtrackingSearch();
            System.out.println(Arrays.toString(solutionFound));
            totalElapsedTime += (System.nanoTime() - start);
            nbTimeExecuted++;
            System.out.println("Exec time = "+ (System.nanoTime() - start)+"nano");
            if (solutionFound != null) { nbSolutionFound++; }

        }

        System.out.println("NbTimeExecuted = "+nbTimeExecuted+"\nTotalElapsedTime = "+totalElapsedTime+"\nAverage = "+(totalElapsedTime/nbTimeExecuted));
    }
}
