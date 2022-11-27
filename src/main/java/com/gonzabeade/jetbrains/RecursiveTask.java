package com.gonzabeade.jetbrains;

import java.util.*;

public class RecursiveTask implements Task {


    private static class TarjanHelper<T> {

        private final HashMap<Node<T>, Integer> dfsValues = new HashMap<>();
        private final HashMap<Node<T>, Integer> lowLinkValues = new HashMap<>();
        private final Set<Node<T>> isValid = new HashSet<>();
        private final Stack<Node<T>> nodeStack = new Stack<>();
        Set<Set<Node<T>>> result = new HashSet<>();

        private int dfsCounter = 0;

        public boolean isNodeVisited(Node<T> node) {
            return dfsValues.containsKey(node);
        }

        public void tarjanTraverse(Node<T> node) {

            // Is this the first time I have seen this node?
            if (!dfsValues.containsKey(node)) {
                dfsValues.put(node, dfsCounter);
                lowLinkValues.put(node, dfsCounter);
                isValid.add(node);
                nodeStack.push(node);
                dfsCounter++;
            }

            // Iterate through all of my neighbours and recursively call the method if necessary
            for (Node<T> neighbour : node.adjacents()) {
                // Has the algorithm already seen this neighbour?
                if (dfsValues.containsKey(neighbour)) {
                    if (isValid.contains(neighbour)) {
                        // Minimize the low link value using the visited neighbour's dfs value
                        int min = lowLinkValues.get(node) > dfsValues.get(neighbour) ? dfsValues.get(neighbour) : lowLinkValues.get(node);
                        lowLinkValues.put(node, min);
                    }
                } else {
                    this.tarjanTraverse(neighbour);

                    // Minimize my low link value with that of my neighbour, after another the Tarjan call finished
                    int min = lowLinkValues.get(node) > lowLinkValues.get(neighbour) ? lowLinkValues.get(neighbour) : lowLinkValues.get(node);
                    lowLinkValues.put(node, min);
                }
            }

            // I have already finished visiting all my neighbours.
            // It is time to check if I am the root of one SCC
            if (lowLinkValues.get(node) == dfsValues.get(node)) {

                // Pop elements from the stack to form a new set of elements in the partition.
                Set<Node<T>> segment = new HashSet<>();
                Node<T> top;
                do {
                    top = nodeStack.pop();
                    segment.add(top);
                    isValid.remove(top);
                } while (top != node);

                result.add(segment);
            }

        }

        public Set<Set<Node<T>>> close() {
            return result;
        }
    }

    @Override
    public <T> Set<Set<Node<T>>> tarjan(List<Node<T>> graph) {

        TarjanHelper<T> tarjanHelper = new TarjanHelper<>();

        // It may happen that not every node is reachable from the first node of the graph
        // Try every node, and the worst thing that may happen is that it has already been
        // traversed in some other call
        for (Node<T> initNode : graph)
            if (!tarjanHelper.isNodeVisited(initNode))
                tarjanHelper.tarjanTraverse(initNode);

        return tarjanHelper.close();
    }

}