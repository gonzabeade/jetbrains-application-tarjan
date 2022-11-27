package com.gonzabeade.jetbrains;

import java.util.List;

/**
 * A vertex in a graph.
 * @param <T>  the type of the payload.
 */
public interface Node<T> {
    /**
     * A list of nodes adjacent to this node. The link is directional from this node to the adjacent node.
     *
     * @return list of adjacent nodes
     */
    List<Node<T>> adjacents();
    /**
     * The payload object stored in the node. Can for example be used to identify or number the node.
     *
     * @return the payload object.
     */
    T getPayload();
}
