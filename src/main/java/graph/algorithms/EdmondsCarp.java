package graph.algorithms;

import graph.algorithms.dto.EdgeWithDirection;
import graph.algorithms.search.BreadthSearch;
import graph.common.Graph;
import graph.common.Vertex;
import graph.common.flow.FlowEdge;
import graph.common.flow.FlowGraph;
import graph.exeptions.VertexIsNotExistException;

import java.util.Comparator;
import java.util.List;

public class EdmondsCarp {
    public static <V extends Vertex, E extends FlowEdge<V>, G extends FlowGraph<V, E>> int findMaxFlow(V source, V sink, G graph){
        if(!graph.contains(source) && !graph.contains(sink)){
            throw new VertexIsNotExistException(source, sink);
        }

        List<V> searchResult;
        do {
            searchResult = BreadthSearch.search(
                    graph,
                    source,
                    vertex -> vertex.equals(sink),
                    (from, to) -> graph.getFreeFlow(from, to) > 0
            );

            if(!searchResult.isEmpty()){
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

        } while (!searchResult.isEmpty());

        return graph.getOutputFlow(source);
    }
}
