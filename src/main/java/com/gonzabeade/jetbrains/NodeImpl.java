package com.gonzabeade.jetbrains;

import java.util.ArrayList;
import java.util.List;

public class NodeImpl implements Node<Integer> {

    private static final int INITIAL_CAPACITY = 5;
    private final List<Node<Integer>> adjacents = new ArrayList<>(INITIAL_CAPACITY);
    private final String label;

    public NodeImpl(String label) {
        this.label = label;
    }

    public NodeImpl addAdjacent(Node<Integer> node) {
        adjacents.add(node);
        return this;
    }

    @Override
    public List<Node<Integer>> adjacents() {
        return adjacents;
    }

    @Override
    public Integer getPayload() {
        return 0;
    }

    @Override
    public String toString() {
        return label;
    }
}