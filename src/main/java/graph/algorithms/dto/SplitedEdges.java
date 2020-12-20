package graph.algorithms.dto;

import graph.common.Edge;

import java.util.Objects;
import java.util.Set;

public class SplitedEdges <E extends Edge<?>> {
    Set<E> edgesToVertex;
    Set<E> edgesFromVertex;

    public SplitedEdges(Set<E> edgesToVertex, Set<E> edgesFromVertex) {
        this.edgesToVertex = edgesToVertex;
        this.edgesFromVertex = edgesFromVertex;
    }

    public Set<E> getEdgesToVertex() {
        return edgesToVertex;
    }

    public void setEdgesToVertex(Set<E> edgesToVertex) {
        this.edgesToVertex = edgesToVertex;
    }

    public Set<E> getEdgesFromVertex() {
        return edgesFromVertex;
    }

    public void setEdgesFromVertex(Set<E> edgesFromVertex) {
        this.edgesFromVertex = edgesFromVertex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SplitedEdges)) return false;
        SplitedEdges<?> that = (SplitedEdges<?>) o;
        return Objects.equals(edgesToVertex, that.edgesToVertex) &&
                Objects.equals(edgesFromVertex, that.edgesFromVertex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(edgesToVertex, edgesFromVertex);
    }
}
