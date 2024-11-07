package taki.handson;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import java.util.concurrent.atomic.AtomicInteger;

public final class Pool extends GenericObjectPool<Connection> {

    public final AtomicInteger borrowCount = new AtomicInteger(0);
    public final AtomicInteger returnCount = new AtomicInteger(0);
    public final AtomicInteger invalidateCount = new AtomicInteger(0);
    public final AtomicInteger evictCount = new AtomicInteger(0);

    public Pool(PooledObjectFactory<Connection> factory, GenericObjectPoolConfig<Connection> config) {
        super(factory, config);
    }

    @Override
    public Connection borrowObject() throws Exception {
        borrowCount.incrementAndGet();
        return super.borrowObject();
    }

    @Override
    public void returnObject(Connection obj) {
        returnCount.incrementAndGet();
        super.returnObject(obj);
    }

    @Override
    public void invalidateObject(Connection obj) throws Exception {
        invalidateCount.incrementAndGet();
        super.invalidateObject(obj);
    }

    @Override
    public void evict() throws Exception {
        evictCount.incrementAndGet();
        super.evict();
    }

}
