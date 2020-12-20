package graph.impl.flowImpl;

import graph.common.flow.FlowEdge;
import graph.common.Vertex;
import graph.exeptions.FlowOutOfCapacityException;
import graph.impl.defaultImpl.DefaultEdge;

import java.util.Objects;

public class DefaultFlowEdge<V extends Vertex> extends DefaultEdge<V> implements FlowEdge<V> {
    private final int capacity;
    private int flow;

    public DefaultFlowEdge(V from, V to, int capacity) {
        super(from, to);
        this.capacity = capacity;
        this.flow = 0;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getFlow() {
        return flow;
    }

    @Override
    public int getFlowInvers() {
        return capacity - flow;
    }

    @Override
    public void setFlow(int flow) {
        if(Math.abs(flow) > capacity){
            throw new FlowOutOfCapacityException(flow, capacity);
        }
        this.flow = flow;
    }

    @Override
    public void addFlow(int flowIncrease) {
        if(Math.abs(flowIncrease + flow) > capacity){
            throw new FlowOutOfCapacityException(flowIncrease + flow, capacity);
        }
        this.flow = flowIncrease + flow;
    }

    @Override
    public void subFlow(int flowReduction) {
        if(Math.abs(flow - flowReduction) < 0){
            throw new FlowOutOfCapacityException(flow - flowReduction, capacity);
        }
        this.flow = flow - flowReduction;
    }

    @Override
    public String toString() {
        return getFrom().toString() + " ---" + flow + "/" + capacity + "---> " + getTo().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultFlowEdge)) return false;
        if (!super.equals(o)) return false;
        DefaultFlowEdge<?> that = (DefaultFlowEdge<?>) o;
        return capacity == that.capacity &&
                flow == that.flow;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), capacity, flow);
    }
}
