package taki.handson;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class Factory implements PooledObjectFactory<Connection> {

    public AtomicInteger activateCounter = new AtomicInteger(0);
    public AtomicInteger destroyCounter = new AtomicInteger(0);
    public AtomicInteger makeCounter = new AtomicInteger(0);
    public AtomicInteger passivateCounter = new AtomicInteger(0);
    public AtomicInteger validateCounter = new AtomicInteger(0);

    public Factory() {
    }

    @Override
    public void activateObject(PooledObject<Connection> p) throws Exception {
        activateCounter.incrementAndGet();
    }

    @Override
    public void destroyObject(PooledObject<Connection> p) throws Exception {
        destroyCounter.incrementAndGet();
    }

    @Override
    public PooledObject<Connection> makeObject() throws Exception {
        makeCounter.incrementAndGet();
        return new DefaultPooledObject<Connection>(new Connection());
    }

    @Override
    public void passivateObject(PooledObject<Connection> p) throws Exception {
    }

    @Override
    public boolean validateObject(PooledObject<Connection> p) {
        validateCounter.incrementAndGet();
        return true;
    }

    @Override
    public String toString() {
        return "Factory [activateCounter=" + activateCounter + ", destroyCounter=" + destroyCounter + ", makeCounter="
                + makeCounter + ", passivateCounter=" + passivateCounter + ", validateCounter=" + validateCounter + "]";
    }
}
