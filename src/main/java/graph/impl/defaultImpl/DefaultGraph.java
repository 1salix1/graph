package graph.impl.defaultImpl;

import graph.common.Edge;
import graph.common.Graph;
import graph.common.Vertex;
import graph.exeptions.EdgeIsExistException;
import graph.exeptions.EdgeIsNotExistException;
import graph.exeptions.VertexIsExistException;
import graph.exeptions.VertexIsNotExistException;

import java.util.*;
import java.util.stream.Collectors;

public class DefaultGraph<V extends Vertex, E extends Edge<V>> implements Graph<V, E> {
    private Map<V, Set<E>> graphStorage;

    public DefaultGraph() {
        graphStorage = new HashMap<>();
    }

    @Override
    public Set<V> getVertexes(){
        return new HashSet<>(graphStorage.keySet());
    }

    @Override
    public void putVertex(V vertex) {
        graphStorage.put(vertex, new HashSet<>());
    }

    @Override
    public void removeVertex(V vertex) {
        graphStorage.remove(vertex);
    }

    @Override
    public void addVertex(V vertex) throws VertexIsExistException {
        if(contains(vertex)){
            throw new VertexIsExistException(vertex);
        }
        graphStorage.put(vertex, new HashSet<>());
    }

    @Override
    public void deleteVertex(V vertex) throws VertexIsNotExistException {
        if(!contains(vertex)){
            throw new VertexIsNotExistException(vertex);
        }
        graphStorage.remove(vertex);
    }

    @Override
    public void putEdge(E edge) {
        if(!contains(edge.getFrom())){
            graphStorage.put(edge.getFrom(), new HashSet<>());
        }
        if(!contains(edge.getTo())){
            graphStorage.put(edge.getTo(), new HashSet<>());
        }
        addEdgeIfNotExist(edge);
    }

    @Override
    public void removeEdge(E edge) {
        removeEdgeIfNotExist(edge);
    }

    @Override
    public void addEdge(E edge) throws VertexIsNotExistException {
        if(!contains(edge.getFrom())){
            throw new VertexIsNotExistException(edge.getFrom());
        }
        if(!contains(edge.getTo())){
            throw new VertexIsNotExistException(edge.getTo());
        }

        if(!addEdgeIfNotExist(edge)){
            throw new EdgeIsExistException(edge);
        }
    }

    @Override
    public void deleteEdge(E edge) {
        if(removeEdgeIfNotExist(edge)){
            throw new EdgeIsNotExistException(edge);
        }
    }

    private boolean addEdgeIfNotExist(E edge){
        if(contains(edge)){
            return false;
        }
        graphStorage.get(edge.getFrom()).add(edge);
        graphStorage.get(edge.getTo()).add(edge);
        return true;
    }

    private boolean removeEdgeIfNotExist(E edge){
        if(!contains(edge)){
            return false;
        }
        graphStorage.get(edge.getFrom()).remove(edge);
        graphStorage.get(edge.getTo()).remove(edge);
        return true;
    }

    @Override
    public boolean contains(V vertex){
        return graphStorage.containsKey(vertex);
    }

    @Override
    public boolean contains(E edge){
        return contains(edge.getFrom()) &&
                contains(edge.getTo()) &&
                (graphStorage.get(edge.getFrom()).contains(edge) && graphStorage.get(edge.getTo()).contains(edge));
    }

    @Override
    public Set<V> getBoundVertexes(V sourceVertex){
        Set<E> edges = graphStorage.get(sourceVertex);
        if (edges != null){
            //Выбираем противоположную вершину, относительно связанной
            return edges.stream()
                    .map(edge -> edge.getFrom().equals(sourceVertex) ? edge.getTo() : edge.getFrom())
                    .collect(Collectors.toSet());
        }
        return null;
    }

    @Override
    public Set<E> getBoundEdges(V sourceVertex){
        return graphStorage.get(sourceVertex);
    }

    @Override
    public E getEdge(V from, V to){
        if(!contains(from)){
            throw new VertexIsNotExistException(from);
        }
        if(!contains(to)){
            throw new VertexIsNotExistException(to);
        }
        return graphStorage.get(from)
                .stream()
                .filter(edge -> edge.getFrom().equals(to) || edge.getTo().equals(to))
                .findFirst()
                .orElseThrow(() -> new EdgeIsNotExistException(from, to));
    }

    @Override
    public String toString() {
        String vertexes = graphStorage
                .keySet()
                .stream()
                .map(Objects::toString)
                .collect(Collectors.joining("\n\t"));
        String edges = graphStorage
                .entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().stream())
                .distinct()
                .map(Objects::toString)
                .collect(Collectors.joining("\n\t"));
        return "Vertexes: \n\t" + vertexes + "\n" + "Edges: \n\t" + edges;
    }
}
