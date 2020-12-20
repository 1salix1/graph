package graph.exeptions;

import graph.common.Vertex;

public class RouteNotContainsStepException extends RuntimeException{
    public RouteNotContainsStepException(Vertex vertex) {
        super("Route does not contain vertex " + vertex.getId());
    }
}
