package com.gonzabeade.jetbrains;


import java.util.*;

public class IterativeTask implements Task {

    private static class TarjanStats {
        private int dfn;
        private int lowLink;

        public TarjanStats(int dfn) {
            this.dfn = dfn;
            this.lowLink = dfn;
        }

        public int getDfn() {
            return dfn;
        }

        public int getLowLink() {
            return lowLink;
        }

        public void setLowLink(int lowLink) {
            this.lowLink = lowLink;
        }
    }
    private static class TarjanStackFrame<T> {
        private Node<T> node;
        private Node<T> callerNode;
        private boolean seen;

        public TarjanStackFrame(Node<T> node, Node<T> callerNode) {
            this.node = node;
            this.callerNode = callerNode;
        }

        public Node<T> getNode() {
            return node;
        }

        public void setNode(Node<T> node) {
            this.node = node;
        }

        public Node<T> getCallerNode() {
            return callerNode;
        }

        public void setCallerNode(Node<T> callerNode) {
            this.callerNode = callerNode;
        }

        public boolean isSeen() {
            return seen;
        }

        public void setSeen(boolean seen) {
            this.seen = seen;
        }
    }

    /**
     * Calculates the strongly connected components of a directed acyclic graph using Tarjan's algorithm
     *
     * <a href="https://en.wikipedia.org/wiki/Tarjan%27s_strongly_connected_components_algorithm">Reference</a>
     *
     * @param graph a directed acyclic graph
     * @return a list of strongly connected components of the specified graph
     */
    @Override
    public <T> Set<Set<Node<T>>> tarjan(List<Node<T>> graph) {

        Set<Set<Node<T>>> result = new HashSet<>();
        Set<Node<T>> isValid = new HashSet<>();
        Stack<Node<T>> nodeStack = new Stack<>();
        Stack<TarjanStackFrame<T>> frameStack = new Stack<>();
        HashMap<Node, TarjanStats> visited = new HashMap<>();


        int dfn = 0;
        for (Node<T> initNode: graph)
            if (!visited.containsKey(initNode)) {
                // Initialize data structures
                frameStack.push(new TarjanStackFrame<>(initNode, null));
                nodeStack.push(initNode);
                isValid.add(initNode);
                while (!frameStack.isEmpty()) {
                    TarjanStackFrame<T> tarjanStackFrame = frameStack.peek();
                    Node<T> node = tarjanStackFrame.getNode();
                    Node<T> callerNode = tarjanStackFrame.getCallerNode();

                    if (!tarjanStackFrame.isSeen()) {  // If I am not coming back from a call

                        if (visited.containsKey(node)) {
                            frameStack.pop();
                            continue; // Phantom in the stack trace. It has already been analyzed.
                        }

                        // This is my first execution
                        visited.put(node, new TarjanStats(dfn++));
                        isValid.add(node);
                        for (Node<T> neighbour : node.adjacents())
                            if (visited.containsKey(neighbour)) {
                                if (isValid.contains(neighbour))
                                    visited.get(node).setLowLink(visited.get(node).getLowLink() > visited.get(neighbour).getDfn() ? visited.get(neighbour).getDfn() : visited.get(node).getLowLink());
                            } else {
                                nodeStack.push(neighbour);
                                frameStack.push(new TarjanStackFrame<>(neighbour, node));
                            }
                        tarjanStackFrame.setSeen(true);
                    } else {
                        // I have invoked someone. I am coming back from DFS
                        // Minimize the caller low-link with my low-link
                        if (callerNode != null) {
                            int min = visited.get(callerNode).getLowLink() > visited.get(node).getLowLink() ? visited.get(node).getLowLink() : visited.get(callerNode).getLowLink();
                            visited.get(callerNode).setLowLink(min);
                        }


                        if (visited.get(node).getLowLink() == visited.get(node).getDfn()) {
                            Set<Node<T>> segment = new HashSet<>();

                            Node<T> top;
                            do {
                                top = nodeStack.pop();
                                if (isValid.contains(top)) segment.add(top);
                                isValid.remove(top);
                            } while (top != node);

                            result.add(segment);
                        }

                        frameStack.pop();
                    }
                }
            }



        return result;
    }

}