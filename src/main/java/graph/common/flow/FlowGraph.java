package graph.common.flow;

import graph.common.Graph;
import graph.common.Vertex;

import java.util.Set;

public interface FlowGraph <V extends Vertex, E extends FlowEdge<V>> extends Graph<V, E> {
    /**
     * Избыточный поток = выпадающий поток - впадающий поток
     * @param vertex Вершина, для которой смотрим поток
     * @return Велечина потока
     */
    int getExcessFlow(V vertex);
    /**
     * Получить поток, который ещё можно отправить от from к to
     * @param from Вершина, от которой идёт поток
     * @param to Вершина, к которой идёт поток
     * @return Велечина потока
     */
    int getFreeFlow(V from, V to);
    /**
     * Вернуть входящий поток в вершину
     * @param vertex Вершина
     * @return Количество потока
     */
    int getInputFlow(V vertex);
    /**
     * Вернуть исходящий поток из вершины
     * @param vertex Вершина
     * @return Количество потока
     */
    int getOutputFlow(V vertex);

    /**
     * Добавить поток с учётом направления
     * @param from начальная вершина
     * @param to конечная вершина
     * @param flow количество потока
     */
    void addFlow(V from, V to, int flow);
    Set<E> getIncomingEdges(V vertex);
    Set<E> getOutgoingEdges(V vertex);
}
