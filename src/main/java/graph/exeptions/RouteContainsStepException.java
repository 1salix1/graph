package graph.exeptions;

import graph.common.Vertex;

public class RouteContainsStepException extends RuntimeException{
    public RouteContainsStepException(Vertex vertex) {
        super("Route already contain vertex " + vertex.getId());
    }
}
