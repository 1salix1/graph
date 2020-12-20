package graph.algorithms;

import graph.algorithms.dto.EdgeWithDirection;
import graph.algorithms.dto.SplitedEdges;
import graph.common.Edge;
import graph.common.Graph;
import graph.common.Vertex;
import graph.exeptions.EdgeDoesNotContainVertexException;

import java.util.*;
import java.util.stream.Collectors;

public class GraphUtils {

    public static <V extends Vertex, E extends Edge<V>, G extends Graph<V, E>> List<EdgeWithDirection<E>> getEdgesFromVertexRoute(G graph, List<V> vertexes){
        List<EdgeWithDirection<E>> edgesRoute = new ArrayList<>();
        for (int i = 0; i < vertexes.size() - 1; i++) {
            E edge = graph.getEdge(vertexes.get(i), vertexes.get(i + 1));
            // Флаг того, что направление ребра прямое, а не обратное
            boolean isDirect = edge.getFrom().equals(vertexes.get(i));
            edgesRoute.add(new EdgeWithDirection<>(edge, isDirect));
        }
        return edgesRoute;
    }

    public static  <V extends Vertex, E extends Edge<V>> V getOppositeVertex(E edge, V vertex){
        boolean isFromVertex = edge.getFrom().equals(vertex);
        boolean isToVertex = edge.getTo().equals(vertex);
        if(!isFromVertex && !isToVertex){
            throw new EdgeDoesNotContainVertexException(edge, vertex);
        }
        return isFromVertex ? edge.getTo() : edge.getFrom();
    }
}
