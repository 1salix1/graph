package graph.algorithms.search;

import graph.common.Edge;
import graph.common.Graph;
import graph.common.Vertex;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BreadthSearch {
    /**
     * Поиск в ширину
     * @param graph граф
     * @param startVertex Стартовая вершина
     * @param searchPredicate Условие поиска
     * @param jumpPredicate Возможен ли переход от отдной вершины к другой
     * @param <V> Вершина
     * @param <E> Ребро
     * @return Список вершин, образующих маршрут от стартовой вершины к найденой
     */
    public static <V extends Vertex, E extends Edge<V>> List<V> search(
            Graph<V, E> graph,
            V startVertex,
            Predicate<V> searchPredicate,
            BiPredicate<V, V> jumpPredicate
    ){
        return search(graph, startVertex, searchPredicate, jumpPredicate, null);
    }

    /**
     * Поиск в ширину
     * @param graph граф
     * @param startVertex Стартовая вершина
     * @param searchPredicate Условие поиска
     * @param jumpPredicate Возможен ли переход от отдной вершины к другой
     * @param findCallback Вызывается, когда нашлась очередная вершина. Принимает (vertexFrom, vertexTo)
     * @param <V> Вершина
     * @param <E> Ребро
     * @return Список вершин, образующих маршрут от стартовой вершины к найденой
     */
    public static <V extends Vertex, E extends Edge<V>> List<V> search(
            Graph<V, E> graph,
            V startVertex,
            Predicate<V> searchPredicate,
            BiPredicate<V, V> jumpPredicate,
            BiConsumer<V, V> findCallback
    ){
        GraphRouteMap<V> graphRouteMap = new GraphRouteMap<>();
        MarkedVertexSet<V> markedVertexSet = new MarkedVertexSet<>(graph);
        Queue<V> queue = new LinkedList<>();

        //Красим стартовую вершину и кладём её в очередь
        markedVertexSet.paintVertex(startVertex);
        queue.offer(startVertex);

        while (!queue.isEmpty()){
            V currentVertex = queue.poll();
            // Если текущая вершина удовлетворяет условию, то мы нашли искомую вершину
            if(searchPredicate.test(currentVertex)){
                return graphRouteMap.getRoute(currentVertex);
            }
            //Ищем соседние с текущей, неокрашенные вершины
            Set<V> unpaintedVertexes = markedVertexSet.getUnpaintedVertexes(
                    graph.getBoundVertexes(currentVertex)
            );
            // Проверяем переходы, идущие к этим вершинам, что бы они удовлетворяли заданному условию
            unpaintedVertexes = unpaintedVertexes
                    .stream()
                    .filter(vertex -> jumpPredicate.test(currentVertex, vertex))
                    .collect(Collectors.toSet());
            // Если нашли, то красим эти вершины,  толкаем в очередь и кладём в карту маршрута
            if(!unpaintedVertexes.isEmpty()){
                markedVertexSet.paintVertexes(unpaintedVertexes);
                unpaintedVertexes.forEach(queue::offer);
                unpaintedVertexes.forEach(vertex -> graphRouteMap.putStep(vertex, currentVertex));
                // Вызываем callback. Вершина, от которой пришли; вершина которую нашли
                if (findCallback != null){
                    unpaintedVertexes.forEach(vertex -> findCallback.accept(currentVertex, vertex));
                }
            }
        }
        return new ArrayList<>();
    }
}
