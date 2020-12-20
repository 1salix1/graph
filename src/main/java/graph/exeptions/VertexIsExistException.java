package graph.exeptions;

import graph.common.Vertex;

public class VertexIsExistException extends RuntimeException{
    public VertexIsExistException(Vertex vertex) {
        super("Vertex " + vertex.getId() + " is exist in graph");
    }
}
