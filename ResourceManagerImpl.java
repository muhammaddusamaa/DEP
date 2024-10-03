import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

// The server-side implementation of the ResourceManager interface.
public class ResourceManagerImpl extends UnicastRemoteObject implements ResourceManager {
    private HashMap<String, String> resources;

    // Constructor that initializes the resource map.
    protected ResourceManagerImpl() throws RemoteException {
        super();
        resources = new HashMap<>();
    }

    // Method to retrieve a resource by its name.
    @Override
    public String getResource(String name) throws RemoteException {
        return resources.getOrDefault(name, "Resource not found");
    }

    // Method to share a resource with the server.
    @Override
    public void shareResource(String name, String data) throws RemoteException {
        resources.put(name, data);
        System.out.println("Resource shared: " + name);
    }

    // Method to get a list of all resource names available on the server.
    @Override
    public List<String> getAllResources() throws RemoteException {
        return new ArrayList<>(resources.keySet());
    }
}
