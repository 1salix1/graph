package graph.impl.flowImpl;

import graph.common.Vertex;
import graph.common.flow.FlowEdge;
import graph.common.flow.FlowGraph;
import graph.exeptions.EdgeIsNotExistException;
import graph.exeptions.VertexIsNotExistException;
import graph.impl.defaultImpl.DefaultGraph;

import java.util.Set;
import java.util.stream.Collectors;

public class DefaultFlowGraph <V extends Vertex, E extends FlowEdge<V>> extends DefaultGraph<V, E> implements FlowGraph<V, E> {

    @Override
    public int getExcessFlow(V vertex) {
        int forwardInputeFlow = getIncomingEdges(vertex).stream()
                .mapToInt(FlowEdge::getFlow)
                .sum();
        int forwardOutputFlow = getOutgoingEdges(vertex).stream()
                .mapToInt(FlowEdge::getFlow)
                .sum();
        return forwardInputeFlow - forwardOutputFlow;
    }

    @Override
    public int getFreeFlow(V from, V to) {
        E edge = getEdge(from, to);
        return edge.getFrom().equals(from) ?
                edge.getCapacity() - edge.getFlow() :
                edge.getCapacity() - edge.getFlowInvers();
    }

    @Override
    public int getInputFlow(V vertex){
        return getIncomingEdges(vertex)
                .stream()
                .mapToInt(FlowEdge::getFlow)
                .sum();
    }

    @Override
    public int getOutputFlow(V vertex){
        return getOutgoingEdges(vertex)
                .stream()
                .mapToInt(FlowEdge::getFlow)
                .sum();
    }

    @Override
    public void addFlow(V from, V to, int flow){
        E edge = getEdge(from, to);
        if (edge.getFrom().equals(from)) {
            edge.addFlow(flow);
        }
        else {
            edge.subFlow(flow);
        }
    }

    @Override
    public Set<E> getIncomingEdges(V vertex) {
        return getBoundEdges(vertex).stream()
                .filter(edge -> edge.getTo().equals(vertex))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<E> getOutgoingEdges(V vertex) {
        return getBoundEdges(vertex).stream()
                .filter(edge -> edge.getFrom().equals(vertex))
                .collect(Collectors.toSet());
    }
}
