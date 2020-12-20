package graph.exeptions;

import graph.common.Edge;
import graph.common.Vertex;

public class EdgeIsNotExistException extends RuntimeException {
    public EdgeIsNotExistException(Edge<?> edge) {
        super("Edge " + edge.toString() + " is not exist in graph");
    }

    public EdgeIsNotExistException(Vertex from, Vertex to) {
        super("Edge between vertexes " + from.toString() + " and " + to.toString() + " is not exist in graph");
    }
}
