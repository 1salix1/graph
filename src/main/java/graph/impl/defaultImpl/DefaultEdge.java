package graph.impl.defaultImpl;

import graph.common.Edge;
import graph.common.Vertex;

import java.util.Objects;

public class DefaultEdge<V extends Vertex> implements Edge<V> {
    private final V from;
    private final V to;

    public DefaultEdge(V from, V to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public V getFrom() {
        return from;
    }

    @Override
    public V getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultEdge)) return false;
        DefaultEdge<?> that = (DefaultEdge<?>) o;
        return Objects.equals(from, that.from) &&
                Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return from.toString() + " ---> " + to.toString();
    }
}
