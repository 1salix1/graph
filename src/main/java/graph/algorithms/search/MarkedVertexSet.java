package graph.algorithms.search;

import graph.common.Graph;
import graph.common.Vertex;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MarkedVertexSet <V extends Vertex> {
    private Map<V, Boolean> markVertexes;

    public MarkedVertexSet(Graph<V, ?> graph) {
        markVertexes = graph.getVertexes()
                .stream()
                .collect(Collectors.toMap(vertex -> vertex, vertex -> false));
    }

    public void paintVertex(V vertex){
        markVertexes.put(vertex, true);
    }

    public void paintVertexes(Set<V> vertexes){
        vertexes.forEach(this::paintVertex);
    }
    
    public boolean isVertexPainted(V vertex){
        return markVertexes.get(vertex);
    }

    public Set<V> getUnpaintedVertexes(Set<V> vertexes){
        return vertexes.stream()
                .filter(vertex -> !isVertexPainted(vertex))
                .collect(Collectors.toSet());
    }
    
    public Optional<V> getFirstUnpaintedVertex(Set<V> vertexes){
        return getUnpaintedVertexes(vertexes)
                .stream()
                .findFirst();
    }
}
