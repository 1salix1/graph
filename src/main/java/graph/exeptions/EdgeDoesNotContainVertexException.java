package graph.exeptions;

import graph.common.Edge;
import graph.common.Vertex;

public class EdgeDoesNotContainVertexException extends RuntimeException{
    public EdgeDoesNotContainVertexException(Edge<?> edge, Vertex vertex) {
        super("Edge " + edge.toString() + " does not contain vertex " + vertex.toString());
    }
}
