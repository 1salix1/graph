package graph.impl.defaultImpl;

import graph.common.Vertex;

import java.util.Objects;

public class DefaultVertex implements Vertex {
    private final int id;
    private final String name;

    public DefaultVertex(int id) {
        this.id = id;
        this.name = null;
    }

    public DefaultVertex(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name == null ? Integer.toString(id) : name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultVertex)) return false;
        DefaultVertex that = (DefaultVertex) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
