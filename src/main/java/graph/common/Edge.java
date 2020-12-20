package graph.common;

public interface Edge<V extends Vertex> {
    V getFrom();
    V getTo();
}
