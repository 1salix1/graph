package graph.exeptions;

import graph.common.Edge;

public class EdgeIsExistException extends RuntimeException{
    public EdgeIsExistException(Edge<?> edge) {
        super("Edge " + edge.toString() + " is exist in graph");
    }
}
