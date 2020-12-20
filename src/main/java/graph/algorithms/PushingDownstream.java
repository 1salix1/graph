package graph.algorithms;

import graph.algorithms.dto.SplitedEdges;
import graph.common.Graph;
import graph.common.Vertex;
import graph.common.flow.FlowEdge;
import graph.common.flow.FlowGraph;
import graph.exeptions.VertexIsNotExistException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PushingDownstream {
    public static <V extends Vertex, E extends FlowEdge<V>, G extends FlowGraph<V, E>> int findMaxFlow(V source, V sink, G graph){
        if(!graph.contains(source) && !graph.contains(sink)){
            throw new VertexIsNotExistException(source, sink);
        }

        // У каждой вершины определим уровень ноль
        Map<V, Integer> levelMap = graph.getVertexes()
                .stream()
                .collect(Collectors.toMap(vertex -> vertex, vertex -> 0));
        // У начальной вершины определим уровень равный количеству вершин
        levelMap.put(source, graph.getVertexes().size());
        // Создаём очередь, где хранятся все вершины с исбыточным потоком
        Queue<V> vertexesWithExcessFlow = new LinkedList<>();
        // В рёбрах, примыкающим к начальной вершине сделаем поток равный ёмкости
        // И сложим эти вершины в очередь
        graph.getOutgoingEdges(source).forEach(edge -> {
            edge.setFlow(edge.getCapacity());
            vertexesWithExcessFlow.offer(edge.getTo());
        });

        while (!vertexesWithExcessFlow.isEmpty()){
            V currentVertex = vertexesWithExcessFlow.peek();
            // Если избыточный поток у вершины не больше нуля или эта вершина - приёмник, то не рассматриваем её
            if(graph.getExcessFlow(currentVertex) <= 0 || currentVertex.equals(sink)){
                vertexesWithExcessFlow.poll();
                continue;
            }

            // Поиск соседних вершин, уровень которых меньше текущей вершины
            Set<E> lowLevelEdges = getAvailableEdges(graph, currentVertex)
                    .filter(edge -> levelMap.get(currentVertex) > levelMap.get(GraphUtils.getOppositeVertex(edge, currentVertex)))
                    .collect(Collectors.toSet());

            // Если таких вершин не нашлось, повышаем уровень текущей вершины (relabel)
            if(lowLevelEdges.isEmpty()){
                V vertexWithMinLevel = getAvailableEdges(graph, currentVertex)
                        .map(edge -> GraphUtils.getOppositeVertex(edge, currentVertex))
                        .min(Comparator.comparingInt(levelMap::get))
                        .orElseThrow(() -> new RuntimeException("Что-то пошло не так"));
                levelMap.put(currentVertex, levelMap.get(vertexWithMinLevel) + 1);
            }
            // Иначе - проталкиваем поток (push)
            else{
                for (E edge : lowLevelEdges) {
                    V oppositeCurrentVertex = GraphUtils.getOppositeVertex(edge, currentVertex);
                    int flowDelta = Math.min(
                            graph.getExcessFlow(currentVertex),
                            graph.getFreeFlow(currentVertex, oppositeCurrentVertex)
                    );
                    // Добавляем поток к вершине и заносим вершину в очередь с избыточными потоками
                    graph.addFlow(currentVertex, oppositeCurrentVertex, flowDelta);
                    vertexesWithExcessFlow.offer(oppositeCurrentVertex);
                }
            }
        }

        return graph.getOutputFlow(source);
    }

    public static <V extends Vertex, E extends FlowEdge<V>, G extends FlowGraph<V, E>> Stream<E> getAvailableEdges(G graph, V vertex){
        return graph.getBoundEdges(vertex)
                .stream()
                .filter(edge -> {
                    V oppositeCurrentVertex = GraphUtils.getOppositeVertex(edge, vertex);
                    return graph.getFreeFlow(vertex, oppositeCurrentVertex) > 0;
                });
    }
}
