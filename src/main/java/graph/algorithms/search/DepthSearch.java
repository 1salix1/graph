package graph.algorithms.search;

import graph.common.Edge;
import graph.common.Graph;
import graph.common.Vertex;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class DepthSearch {
    /**
     * Поиск в глубину
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
     * Поиск в глубину
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
        Stack<V> stack = new Stack<>();

        //Красим стартовую вершину и кладём её в стек
        markedVertexSet.paintVertex(startVertex);
        stack.push(startVertex);

        while (!stack.isEmpty()){
            V currentVertex = stack.peek();
            // Если текущая вершина удовлетворяет условию, то мы нашли искомую вершину
            if(searchPredicate.test(currentVertex)){
                return graphRouteMap.getRoute(currentVertex);
            }
            // Ищем любую соседнюю с текущей, неокрашенную вершину.
            // Если нашли, то красим эти вершины и толкаем в стек
            Optional<V> unpaintedVertex = markedVertexSet.getFirstUnpaintedVertex(
                    graph.getBoundVertexes(currentVertex)
            );
            if (unpaintedVertex.isPresent()) {
                V vertex = unpaintedVertex.get();
                // Проверяем, возможно ли перейти к этой вершине, что бы она удовлетворяла заданному условию
                if(jumpPredicate.test(currentVertex, vertex)){
                    markedVertexSet.paintVertex(vertex);
                    stack.push(vertex);
                    graphRouteMap.putStep(vertex, currentVertex);
                    // Вызываем callback. Вершина, от которой пришли; вершина которую нашли
                    if (findCallback != null){
                        findCallback.accept(currentVertex, vertex);
                    }
                }
            }
            // Если не нашли - достаём вершину из стека
            else{
                stack.pop();
            }
        }
        return new ArrayList<>();
    }
}
