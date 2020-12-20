package graph.exeptions;

import graph.common.Edge;

public class FlowOutOfCapacityException extends RuntimeException {
    public FlowOutOfCapacityException(int flow, int bandwidth) {
        super("Flow: " + flow + ". Capacity: " + bandwidth);
    }

}
