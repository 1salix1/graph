package graph.common;

import java.util.List;
import java.util.Set;

public interface Graph<V extends Vertex, E extends Edge<V>> {
    Set<V> getVertexes();

    void putVertex(V vertex);
    void removeVertex(V vertex);

    void addVertex(V vertex);
    void deleteVertex(V vertex);

    void putEdge(E edge);
    void removeEdge(E edge);

    void addEdge(E edge);
    void deleteEdge(E edge);

    boolean contains(V vertex);
    boolean contains(E edge);

    Set<V> getBoundVertexes(V sourceVertex);
    Set<E> getBoundEdges(V sourceVertex);

    E getEdge(V from, V to);
}
