package graph.common.flow;

import graph.common.Edge;
import graph.common.Vertex;

public interface FlowEdge<V extends Vertex> extends Edge<V> {
    int getCapacity();
    int getFlow();
    int getFlowInvers();

    void setFlow(int flow);

    void addFlow(int flowIncrease);
    void subFlow(int flowReduction);
}
