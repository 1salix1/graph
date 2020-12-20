package graph.Utils;

import graph.common.Vertex;

public interface VertexFactory<V extends Vertex> {
    V createVertex();
    V createVertex(String name);
}
