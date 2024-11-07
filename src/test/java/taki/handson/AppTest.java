package taki.handson;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

class AppTest {

    @Test
    void testValidationsOnEvict() throws Exception {
        Factory factory = new Factory();

        GenericObjectPoolConfig<Connection> cfg = new GenericObjectPoolConfig<Connection>();
        cfg.setTestWhileIdle(true);
        cfg.setTestOnReturn(true);
        cfg.setMinEvictableIdleTime(Duration.ofMillis(60000));
        cfg.setTimeBetweenEvictionRuns(Duration.ofMillis(30000));
        cfg.setNumTestsPerEvictionRun(-1);

        Pool pool = new Pool(factory, cfg);
        Connection conn = pool.borrowObject();
        Connection conn2 = pool.borrowObject();
        pool.returnObject(conn);

        assertEquals(2, pool.borrowCount.get());
        assertEquals(1, pool.returnCount.get());
        assertEquals(0, pool.evictCount.get());
        assertEquals(0, pool.invalidateCount.get());
        assertEquals(2, factory.makeCounter.get());
        assertEquals(1, factory.validateCounter.get());

        for (int i = 0; i < 19; i++) {
            pool.evict();
        }
        assertEquals(2, pool.borrowCount.get());
        assertEquals(1, pool.returnCount.get());
        assertEquals(19, pool.evictCount.get());
        assertEquals(0, pool.invalidateCount.get());
        assertEquals(2, factory.makeCounter.get());
        assertEquals(20, factory.validateCounter.get());

        for (int i = 0; i < 23; i++) {
            pool.evict();
        }
        assertEquals(2, pool.borrowCount.get());
        assertEquals(1, pool.returnCount.get());
        assertEquals(42, pool.evictCount.get());
        assertEquals(0, pool.invalidateCount.get());
        assertEquals(2, factory.makeCounter.get());
        assertEquals(1, pool.getNumIdle());

        assertEquals(43, factory.validateCounter.get());

        pool.returnObject(conn2);
        assertEquals(44, factory.validateCounter.get());

        for (int i = 0; i < 5; i++) {
            pool.evict();
        }
        assertEquals(2, pool.borrowCount.get());
        assertEquals(2, pool.returnCount.get());
        assertEquals(47, pool.evictCount.get());
        assertEquals(0, pool.invalidateCount.get());
        assertEquals(2, factory.makeCounter.get());
        assertEquals(2, pool.getNumIdle());

        assertEquals(54, factory.validateCounter.get());
    }

    @Test
    void testValidationsOnByIdleTime() throws Exception {
        Factory factory = new Factory();

        GenericObjectPoolConfig<Connection> cfg = new GenericObjectPoolConfig<Connection>();
        cfg.setTestWhileIdle(true);
        cfg.setMinEvictableIdleTime(Duration.ofMillis(10));
        cfg.setTimeBetweenEvictionRuns(Duration.ofMillis(999));
        cfg.setNumTestsPerEvictionRun(-1);

        Pool pool = new Pool(factory, cfg);
        Connection conn = pool.borrowObject();
        Connection conn2 = pool.borrowObject();
        pool.returnObject(conn);

        Thread.sleep(5000);
        assertEquals(2, pool.borrowCount.get());
        assertEquals(1, pool.returnCount.get());
        assertEquals(5, pool.evictCount.get());
        assertEquals(0, pool.invalidateCount.get());
        assertEquals(2, factory.makeCounter.get());
        assertEquals(0, pool.getNumIdle());
        assertEquals(1, pool.getNumActive());
        assertEquals(0, factory.validateCounter.get());

    }
}
