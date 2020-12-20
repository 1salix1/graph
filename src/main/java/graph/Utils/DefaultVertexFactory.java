package graph.Utils;

import Utils.UniqueNumberGenerator;
import graph.impl.defaultImpl.DefaultVertex;

public class DefaultVertexFactory implements VertexFactory<DefaultVertex> {

    @Override
    public DefaultVertex createVertex() {
        return new DefaultVertex(
                UniqueNumberGenerator.getInstance().getNumber()
        );
    }

    @Override
    public DefaultVertex createVertex(String name) {
        return new DefaultVertex(
                UniqueNumberGenerator.getInstance().getNumber(),
                name
        );
    }
}
