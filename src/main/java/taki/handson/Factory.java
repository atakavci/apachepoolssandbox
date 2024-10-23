package taki.handson;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * Hello world!
 */
public class Factory implements PooledObjectFactory<Connection> {
    public int activateCounter;
    public int destroyCounter;
    public int makeCounter;
    public int passivateCounter;
    public int validateCounter;
    public Factory() {
    }

    @Override
    public void activateObject(PooledObject<Connection> p) throws Exception {
        activateCounter++;
        // throw new UnsupportedOperationException("Unimplemented method 'activateObject'");
    }

    @Override
    public void destroyObject(PooledObject<Connection> p) throws Exception {
        destroyCounter++;
        // throw new UnsupportedOperationException("Unimplemented method 'destroyObject'");
    }

    @Override
    public PooledObject<Connection> makeObject() throws Exception {
        makeCounter++;
        return new DefaultPooledObject<Connection>(new Connection());
    }

    @Override
    public void passivateObject(PooledObject<Connection> p) throws Exception {
        // throw new UnsupportedOperationException("Unimplemented method 'passivateObject'");
    }

    @Override
    public boolean validateObject(PooledObject<Connection> p) {
        validateCounter++;
        return true;
        // throw new UnsupportedOperationException("Unimplemented method 'validateObject'");
    }
    @Override
    public String toString() {
        return "Factory [activateCounter=" + activateCounter + ", destroyCounter=" + destroyCounter + ", makeCounter="
                + makeCounter + ", passivateCounter=" + passivateCounter + ", validateCounter=" + validateCounter + "]";
    }
}
