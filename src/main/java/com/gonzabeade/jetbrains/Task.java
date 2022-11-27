package com.gonzabeade.jetbrains;

import java.util.List;
import java.util.Set;

public interface Task {

    /**
     * Calculates the strongly connected components of a directed acyclic graph using Tarjan's algorithm
     *
     * <a href="https://en.wikipedia.org/wiki/Tarjan%27s_strongly_connected_components_algorithm">Reference</a>
     *
     * @param graph a directed acyclic graph
     * @return a list of strongly connected components of the specified graph
     */
    <T> Set<Set<Node<T>>> tarjan(List<Node<T>> graph);

}
