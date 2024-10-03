import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

// This is the interface that clients use to interact with the server.
public interface ResourceManager extends Remote {
    // Method to get a resource from the server by name.
    String getResource(String name) throws RemoteException;

    // Method to share a resource with the server.
    void shareResource(String name, String data) throws RemoteException;

    // Method to get a list of all resources available on the server.
    List<String> getAllResources() throws RemoteException;
}
