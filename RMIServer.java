import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

// The main server application that registers the ResourceManagerImpl on the RMI registry.
public class RMIServer {
    public static void main(String[] args) {
        try {
            // Create an instance of the resource manager implementation.
            ResourceManagerImpl resourceManager = new ResourceManagerImpl();

            // Start the RMI registry on port 1099.
            LocateRegistry.createRegistry(1099);

            // Bind the resource manager to the registry so clients can access it.
            Naming.rebind("ResourceManager", resourceManager);

            System.out.println("RMI Server is ready and waiting for client connections...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
