package graph.algorithms.dto;

import graph.common.Edge;
import graph.common.Vertex;

import java.util.Objects;

public class EdgeWithDirection<E extends Edge<?>> {
    E edge;
    boolean isDirect;

    public EdgeWithDirection(E edge, boolean isDirect) {
        this.edge = edge;
        this.isDirect = isDirect;
    }

    public E getEdge() {
        return edge;
    }

    public boolean isDirect() {
        return isDirect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EdgeWithDirection)) return false;
        EdgeWithDirection<?> that = (EdgeWithDirection<?>) o;
        return isDirect == that.isDirect &&
                Objects.equals(edge, that.edge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(edge, isDirect);
    }
}
