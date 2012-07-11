package $package;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.accumulo.core.client.AccumuloException;
import org.apache.accumulo.core.client.AccumuloSecurityException;
import org.apache.accumulo.core.client.BatchWriter;
import org.apache.accumulo.core.client.Connector;
import org.apache.accumulo.core.client.Instance;
import org.apache.accumulo.core.client.MutationsRejectedException;
import org.apache.accumulo.core.client.Scanner;
import org.apache.accumulo.core.client.TableExistsException;
import org.apache.accumulo.core.client.TableNotFoundException;
import org.apache.accumulo.core.client.ZooKeeperInstance;
import org.apache.accumulo.core.data.Key;
import org.apache.accumulo.core.data.Mutation;
import org.apache.accumulo.core.data.Range;
import org.apache.accumulo.core.data.Value;
import org.apache.accumulo.core.security.Authorizations;
import static org.apache.accumulo.core.Constants.NO_AUTHS;

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
        
        //Add an entry to the table
        addEntryToTable(connector,"rowidA","colfam","colqual","value");
        
        //Read back our entry we just added
        Map.Entry<Key, Value> result = scanForRowId(connector, "rowidA");
                
    }
    
    private static Entry<Key, Value> scanForRowId(Connector connector,
            String row) throws TableNotFoundException {
        Scanner scanner = connector.createScanner(tablename, NO_AUTHS);
        scanner.setRange(new Range(row));
        return scanner.iterator().next();
    }

    private static void addEntryToTable(final Connector connector,
            final String rowId, final String columnFamily,
            final String columnQualifier, final String value)
            throws TableNotFoundException, MutationsRejectedException {
        BatchWriter writer = connector.createBatchWriter(tablename, 1024L, 100, 10);
        try {
            Mutation mutation = new Mutation(rowId);
            mutation.put(columnFamily, columnQualifier, value);
            writer.addMutation(mutation);
        } finally {
            writer.flush();
            writer.close();

        }
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


