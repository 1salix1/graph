package graph.algorithms;

import graph.algorithms.dto.EdgeWithDirection;
import graph.algorithms.search.BreadthSearch;
import graph.algorithms.search.DepthSearch;
import graph.common.Graph;
import graph.common.Vertex;
import graph.common.flow.FlowEdge;
import graph.common.flow.FlowGraph;
import graph.exeptions.VertexIsNotExistException;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dinits {
    public static <V extends Vertex, E extends FlowEdge<V>, G extends FlowGraph<V, E>> int findMaxFlow(V source, V sink, G graph){
        if(!graph.contains(source) && !graph.contains(sink)){
            throw new VertexIsNotExistException(source, sink);
        }

        Map<V, Integer> levelMap = new HashMap<>();
        // Флаг того, что найден хотя бы один блокирующий поток
        boolean blockingStreamFound;

        do {
            blockingStreamFound = false;
            levelMap.clear();
            // Определяются уровни для вершин
            BreadthSearch.search(
                    graph,
                    source,
                    vertex -> false,
                    (from, to) -> graph.getFreeFlow(from, to) > 0,
                    (from, to) -> {
                        if(!levelMap.containsKey(from)){
                            levelMap.put(from, 0);
                        }
                        levelMap.put(to, levelMap.get(from) + 1);
                    }
            );

            // Поиск блокирующего потока
            List<V> searchResult;
            do {
                searchResult = BreadthSearch.search(
                        graph,
                        source,
                        vertex -> vertex.equals(sink),
                        (from, to) -> {
                            if(!levelMap.containsKey(from) || !levelMap.containsKey(to)){
                                return false;
                            }
                            if(Math.abs(levelMap.get(from) - levelMap.get(to)) != 1){
                                return false;
                            }
                            E edge = graph.getEdge(from, to);
                            return edge.getCapacity() - edge.getFlow() > 0;
                        }
                );
                if(!searchResult.isEmpty()){
                    blockingStreamFound = true;
                    List<EdgeWithDirection<E>> edgesRoute = GraphUtils.getEdgesFromVertexRoute(graph, searchResult);
                    // Поиск минимального потока среди всех рёбер
                    int minFlow = edgesRoute.stream()
                            .map(EdgeWithDirection::getEdge)
                            .min(Comparator.comparingInt(FlowEdge::getFlowInvers))
                            .orElseThrow(() -> new RuntimeException("Неизвестная ошибка"))
                            .getFlowInvers();
                    // Изменение потоков в рёбрах на размер минимального потока
                    edgesRoute.forEach(edgeWithDirection -> {
                        if (edgeWithDirection.isDirect()) {
                            edgeWithDirection.getEdge().addFlow(minFlow);
                        }
                        else {
                            edgeWithDirection.getEdge().subFlow(minFlow);
                        }
                    });
                }
            } while(!searchResult.isEmpty());

        } while (blockingStreamFound);

        return graph.getOutputFlow(source);
    }
}
