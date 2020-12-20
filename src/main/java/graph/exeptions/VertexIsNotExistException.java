package graph.exeptions;

import graph.common.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class VertexIsNotExistException extends RuntimeException{
    public VertexIsNotExistException(Vertex vertex) {
        super("Vertex " + vertex.getId() + " is not exist in graph");
    }

    public VertexIsNotExistException(Vertex... vertexes) {
        super("Vertexes " +
                Arrays.stream(vertexes).map(Objects::toString).collect(Collectors.joining(", ")) +
                " is not exist in graph"
        );
    }
}
