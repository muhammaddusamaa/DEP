import java.rmi.Naming;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RMIClient {
    private ResourceManager resourceManager;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RMIClient().buildGUI());
    }

    private void buildGUI() {
        JFrame frame = new JFrame("Distributed Resource Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel sharePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel resourceNameLabel = new JLabel("Resource Name:");
        JTextField resourceNameField = new JTextField(20);
        JLabel resourceDataLabel = new JLabel("Resource Data:");
        JTextField resourceDataField = new JTextField(20);
        JButton shareButton = new JButton("Share Resource");

        gbc.gridx = 0;
        gbc.gridy = 0;
        sharePanel.add(resourceNameLabel, gbc);
        gbc.gridx = 1;
        sharePanel.add(resourceNameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        sharePanel.add(resourceDataLabel, gbc);
        gbc.gridx = 1;
        sharePanel.add(resourceDataField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        sharePanel.add(shareButton, gbc);

        JPanel viewPanel = new JPanel(new BorderLayout());
        JTextArea resourceListArea = new JTextArea(15, 30);
        resourceListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resourceListArea);
        JButton refreshButton = new JButton("Refresh Resources");
        viewPanel.add(scrollPane, BorderLayout.CENTER);
        viewPanel.add(refreshButton, BorderLayout.SOUTH);

        JPanel logPanel = new JPanel(new BorderLayout());
        JTextArea logArea = new JTextArea(10, 30);
        logArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logArea);
        logPanel.add(logScrollPane, BorderLayout.CENTER);

        tabbedPane.addTab("Share Resource", sharePanel);
        tabbedPane.addTab("View Resources", viewPanel);
        tabbedPane.addTab("Activity Logs", logPanel);

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);

        try {
            resourceManager = (ResourceManager) Naming.lookup("rmi://localhost/ResourceManager");

            shareButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String name = resourceNameField.getText();
                        String data = resourceDataField.getText();
                        if (!name.isEmpty() && !data.isEmpty()) {
                            resourceManager.shareResource(name, data);
                            logArea.append("Resource shared: " + name + "\n");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Please enter both resource name and data.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        logArea.append("Error: Unable to share resource.\n");
                    }
                }
            });

            refreshButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        List<String> resources = resourceManager.getAllResources();
                        resourceListArea.setText("");
                        for (String resource : resources) {
                            resourceListArea.append(resource + "\n");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        logArea.append("Error: Unable to refresh resources.\n");
                    }
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
            logArea.append("Error: Could not connect to server.\n");
        }
    }
}
