package taki.handson;

import java.time.Duration;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");
        Factory factory = new Factory();

        GenericObjectPoolConfig<Connection> cfg = new GenericObjectPoolConfig<Connection>();
        cfg.setTestWhileIdle(true);
        cfg.setMinEvictableIdleTime(Duration.ZERO);
        
        Pool pool = new Pool(factory, cfg);
        Connection conn = pool.borrowObject();
        Connection conn2 = pool.borrowObject();
        pool.returnObject(conn);
        // pool.returnObject(conn2);

        pool.evict();
        System.out.println(factory);
    }
}
