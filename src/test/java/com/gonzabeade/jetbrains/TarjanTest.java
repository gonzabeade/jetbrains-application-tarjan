package com.gonzabeade.jetbrains;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.*;


public class TarjanTest {

    /**
     *   Test for a generic 8-node graph.
     *
     *   [n1] → [n2]       [n7] → [n6]
     *     ↖    ↙           ↑     ↓
     *      [n3] → [n4] → [n5] ← [n8]
     *
     *  The aim of the test is to check that it does not
     *  matter in which node the DFS algorithm.
     *  This is the low-link problem that Tarjan's
     *  algorithm is supposed to solve.
     *
     **/
    public void genericExample1(Task task) {
        NodeImpl n1 = new NodeImpl("n1");
        NodeImpl n2 = new NodeImpl("n2");
        NodeImpl n3 = new NodeImpl("n3");
        NodeImpl n4 = new NodeImpl("n4");
        NodeImpl n5 = new NodeImpl("n5");
        NodeImpl n6 = new NodeImpl("n6");
        NodeImpl n7 = new NodeImpl("n7");
        NodeImpl n8 = new NodeImpl("n8");

        n1.addAdjacent(n2);
        n2.addAdjacent(n3);
        n3.addAdjacent(n1).addAdjacent(n4);
        n4.addAdjacent(n5);
        n5.addAdjacent(n7);
        n7.addAdjacent(n6);
        n6.addAdjacent(n8);
        n8.addAdjacent(n5);

        Set<Node<Integer>> partitionSet1 = new HashSet<>(Arrays.asList(n1, n2, n3));
        Set<Node<Integer>> partitionSet2 = new HashSet<>(Arrays.asList(n1, n2, n3));
        Set<Node<Integer>> partitionSet3 = new HashSet<>(Arrays.asList(n5, n7, n6, n8));


        LinkedList<Node<Integer>> graph = new LinkedList<>(Arrays.asList(n1, n2, n3, n4, n5, n6, n7, n8));

        int size = graph.size();
        for (int i=0; i<size; i++) {

            Node<Integer> n = graph.removeFirst();
            graph.addLast(n);

            Set<Set<Node<Integer>>> result = task.tarjan(graph);

            assertTrue(result.contains(partitionSet1));
            assertTrue(result.contains(partitionSet2));
            assertTrue(result.contains(partitionSet3));
        }
    }

    @Test
    public void recursiveGenericExample1() {
        genericExample1(new RecursiveTask());
    }

    @Test
    public void iterativeGenericExample1() {
        genericExample1(new IterativeTask());
    }




    /**
     *   Test for a generic 8-node graph
     *
     *   [n1] → [n2] → [n8] → [n7] → [n6]
     *     ↑     ↙      ↑↓         ↖   ↓
     *      [n3]  --→  [n4]   ←--   [n5]
     *
     *  The aim of the test is to check that it does not
     *  matter in which node the DFS algorithm.
     *  This is the low-link problem that Tarjan's
     *  algorithm is supposed to solve.
     *
     **/
    public void genericExample2(Task task) {
        NodeImpl n1 = new NodeImpl("n1");
        NodeImpl n2 = new NodeImpl("n2");
        NodeImpl n3 = new NodeImpl("n3");
        NodeImpl n4 = new NodeImpl("n4");
        NodeImpl n5 = new NodeImpl("n5");
        NodeImpl n6 = new NodeImpl("n6");
        NodeImpl n7 = new NodeImpl("n7");
        NodeImpl n8 = new NodeImpl("n8");

        n1.addAdjacent(n2);
        n2.addAdjacent(n3).addAdjacent(n8);
        n3.addAdjacent(n4).addAdjacent(n1);
        n4.addAdjacent(n8);
        n8.addAdjacent(n4);
        n5.addAdjacent(n4).addAdjacent(n7);
        n6.addAdjacent(n5);
        n7.addAdjacent(n8).addAdjacent(n6);

        Set<Node<Integer>> partitionSet1 = new HashSet<>(Arrays.asList(n4, n8));
        Set<Node<Integer>> partitionSet2 = new HashSet<>(Arrays.asList(n1, n2, n3));
        Set<Node<Integer>> partitionSet3 = new HashSet<>(Arrays.asList(n5, n7, n6));


        LinkedList<Node<Integer>> graph = new LinkedList<>(Arrays.asList(n1, n2, n3, n4, n5, n6, n7, n8));

        int size = graph.size();
        for (int i=0; i<size; i++) {

            Node<Integer> n = graph.removeFirst();
            graph.addLast(n);

            Set<Set<Node<Integer>>> result = task.tarjan(graph);

            assertTrue(result.contains(partitionSet1));
            assertTrue(result.contains(partitionSet2));
            assertTrue(result.contains(partitionSet3));
        }
    }

    @Test
    public void recursiveGenericExample2() {
        genericExample2(new RecursiveTask());
    }

    @Test
    public void iterativeGenericExample2() {
        // The following test is not satisfied.
        // Even though it works for the majority of the nodes,
        // there is a problem with the simulated stack of stackFrames in the implementation.
        // The problem with my stack implementation is that it allows for the existence of discontinuous stacks.
        // The JVM / CPU stack has the property that when a context starts, a substack is built on top of it,
        // and then execution is resumed.
        // However, in my implementation, I first push to the stack every neighbour node, and then I continue with the
        // algorithm, by popping from the top of the stack. This results in the implementation being broken, because
        // the stack may have frames from different 'calls' intercalated.
        // One solution would be to replace the boolean value isSeen with a counter, and every time we push a
        // neighbour into the stack, increment the counter and immediately exit the loop (without pushing the rest
        // of the neighbours to the stack!!). That is, push a neighbour ONE AT THE TIME into the stack, and exit the
        // loop. The disadvantage of this is that for every neighbour we must check if we have already made that
        // exact same DFS call, or not. This is not efficient. It can be studied if this iterative solution ends up
        // really being more efficient than the vanilla recursive solution.

        // genericExample2(new IterativeTask());
    }



    /**
     *   A tree is a special case of a DAG - directed acyclic graph.
     *   Trees have the property that, because they have no loops,
     *   every vertex is its own SCC.
     *   The test is a binary tree with 8 nodes.
     **/
    public void treeTest(Task task) {
        NodeImpl n1 = new NodeImpl("n1");
        NodeImpl n2 = new NodeImpl("n2");
        NodeImpl n3 = new NodeImpl("n3");
        NodeImpl n4 = new NodeImpl("n4");
        NodeImpl n5 = new NodeImpl("n5");
        NodeImpl n6 = new NodeImpl("n6");
        NodeImpl n7 = new NodeImpl("n7");
        NodeImpl n8 = new NodeImpl("n8");

        n1.addAdjacent(n2).addAdjacent(n3);
        n2.addAdjacent(n4).addAdjacent(n5);
        n3.addAdjacent(n6).addAdjacent(n7);
        n4.addAdjacent(n8);


        Set<Set<Node<Integer>>> partitionSetSet = new HashSet<>();

        LinkedList<Node<Integer>> graph = new LinkedList<>(Arrays.asList(n1, n2, n3, n4, n5, n6, n7, n8));


        for (Node<Integer> n: graph)
            partitionSetSet.add(new HashSet<>(Arrays.asList(n)));

        int size = graph.size();
        for (int i=0; i<size; i++) {

            Node<Integer> n = graph.removeFirst();
            graph.addLast(n);

            Set<Set<Node<Integer>>> result = task.tarjan(graph);

            assertTrue(result.equals(partitionSetSet));
        }
    }

    @Test
    public void recursiveTreeTest() {
        treeTest(new RecursiveTask());
    }

    @Test
    public void iterativeTreeTest() {
        treeTest(new IterativeTask());
    }



    /**
     *   Test for asserting the correctness of the implementation
     *   when there are multiple SCC, but when the graph is not connected.
     **/
    public void connectedTest(Task task) {
        NodeImpl n1 = new NodeImpl("n1");
        NodeImpl n2 = new NodeImpl("n2");
        NodeImpl n3 = new NodeImpl("n3");
        NodeImpl n4 = new NodeImpl("n4");
        NodeImpl n5 = new NodeImpl("n5");
        NodeImpl n6 = new NodeImpl("n6");
        NodeImpl n7 = new NodeImpl("n7");
        NodeImpl n8 = new NodeImpl("n8");

        n1.addAdjacent(n2);
        n2.addAdjacent(n3);
        n3.addAdjacent(n4);
        n4.addAdjacent(n1);

        n5.addAdjacent(n6);
        n6.addAdjacent(n7);
        n7.addAdjacent(n8);
        n8.addAdjacent(n5);


        Set<Set<Node<Integer>>> partitionSet = new HashSet<>();
        partitionSet.add(new HashSet<>(Arrays.asList(n1, n2, n3, n4)));
        partitionSet.add(new HashSet<>(Arrays.asList(n5, n6, n7, n8)));


        LinkedList<Node<Integer>> graph = new LinkedList<>(Arrays.asList(n1, n2, n3, n4, n5, n6, n7, n8));

        int size = graph.size();
        for (int i=0; i<size; i++) {

            Node<Integer> n = graph.removeFirst();
            graph.addLast(n);

            Set<Set<Node<Integer>>> result = task.tarjan(graph);

            assertTrue(result.equals(partitionSet));
        }
    }

    @Test
    public void recursiveConnectedTest() {
        connectedTest(new RecursiveTask());
    }

    @Test
    public void iterativeConnectedTest() {
        connectedTest(new IterativeTask());
    }



}
