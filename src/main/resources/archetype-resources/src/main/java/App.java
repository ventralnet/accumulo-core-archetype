package my.test;

import org.apache.accumulo.core.client.AccumuloException;
import org.apache.accumulo.core.client.AccumuloSecurityException;
import org.apache.accumulo.core.client.Connector;
import org.apache.accumulo.core.client.Instance;
import org.apache.accumulo.core.client.TableExistsException;
import org.apache.accumulo.core.client.ZooKeeperInstance;

public class App {
    
    private static String instanceName = "accumulo";
    
    //UserId and password to connect to accumulo
    private static String userId = "root";
    private static byte[] password = "mypassword".getBytes();
    
    //comma separated list of zookeepers
    private static String zookeepers = "localhost";
    
    
    private static String tablename = "test_table";
    
    public static void main(String args[]) throws Exception{
        
        //Connect to accumulo (replace with your connection information)
        Connector connector = connectToAccumulo();
        
        //Create a test table if it doesn't already exist.
        createTableIfNotExist(connector, "test_table");
        
    }
    
    private static void createTableIfNotExist(final Connector connector, final String tablename) throws AccumuloException, AccumuloSecurityException, TableExistsException {
        if (!connector.tableOperations().exists(tablename)) {
            connector.tableOperations().create(tablename);
        }
    }
    
    private static Connector connectToAccumulo() throws AccumuloException, AccumuloSecurityException {
        Instance instance = new ZooKeeperInstance(instanceName, zookeepers);
        return instance.getConnector(userId,password);
    }
}


