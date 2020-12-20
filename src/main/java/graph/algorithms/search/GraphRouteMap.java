package graph.algorithms.search;

import graph.common.Vertex;
import graph.exeptions.RouteContainsStepException;
import graph.exeptions.RouteNotContainsStepException;

import java.util.*;

public class GraphRouteMap<V extends Vertex> {
    // Первый элемент - вершина, на которую пришли
    // Второй элемент - вершина, из которой пришли/
    private Map<V, V> routeMap;

    public GraphRouteMap() {
        routeMap = new HashMap<>();
    }

    public void putStep(V target, V parent){
        if(routeMap.containsKey(target)){
            throw new RouteContainsStepException(target);
        }
        routeMap.put(target, parent);
    }

    /**
     * Получить маршрут от начала обхода графа до заданной вершины
     * @param to Заданная вершина
     * @return Список вершин, образующих маршрут.
     * @throws RouteNotContainsStepException Если маршрут не найден
     */
    public List<V> getRoute(V to){
        if(!routeMap.containsKey(to)){
            throw new RouteNotContainsStepException(to);
        }
        List<V> route = new ArrayList<>();
        V currentVertexOnRoute = to;
        while(currentVertexOnRoute != null){
            route.add(currentVertexOnRoute);
            currentVertexOnRoute = routeMap.get(currentVertexOnRoute);
        }
        Collections.reverse(route);
        return route;
    }
}
