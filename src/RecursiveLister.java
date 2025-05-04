import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class RecursiveLister extends JFrame implements ActionListener {

    private JLabel titleLabel;
    private JButton startButton;
    private JButton quitButton;
    private JTextArea fileListTextArea;
    private JScrollPane scrollPane;
    private JFileChooser fileChooser;

    public RecursiveLister() {
        setTitle("Recursive File Lister");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the window

        // Initialize components
        titleLabel = new JLabel("Select a Directory to List Files Recursively");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        startButton = new JButton("Start");
        quitButton = new JButton("Quit");
        fileListTextArea = new JTextArea();
        fileListTextArea.setEditable(false);
        scrollPane = new JScrollPane(fileListTextArea);
        fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // Add action listeners
        startButton.addActionListener(this);
        quitButton.addActionListener(this);

        // Set layout
        setLayout(new BorderLayout(10, 10)); // Add some spacing

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(startButton);
        buttonPanel.add(quitButton);

        // Add components to the frame
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set look and feel for a decent appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File directory = fileChooser.getSelectedFile();
                fileListTextArea.setText(""); // Clear previous listing
                listFilesRecursively(directory, 0);
            }
        } else if (e.getSource() == quitButton) {
            System.exit(0);
        }
    }

    private void listFilesRecursively(File directory, int level) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                // Add indentation based on the level of recursion
                StringBuilder indentation = new StringBuilder();
                for (int i = 0; i < level; i++) {
                    indentation.append("  ");
                }
                fileListTextArea.append(indentation.toString() + file.getName() + "\n");
                if (file.isDirectory()) {
                    listFilesRecursively(file, level + 1);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RecursiveLister::new);
    }
}