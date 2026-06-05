
import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {

    private JTabbedPane mainTabs;

    public MainGUI() {

        initializeFrame();

        createComponents();

        addComponents();

        setVisible(true);
    }

    private void initializeFrame() {

        setTitle("Stock Portfolio Management System");

        setSize(1200, 800);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
    }

    private void createComponents() {

        mainTabs = new JTabbedPane();

        mainTabs.addTab(
                "Data Insertion",
                new InsertionPanel());

        mainTabs.addTab(
                "Data Modification",
                new ModificationPanel());

        mainTabs.addTab(
                "Data Query",
                new QueryPanel());
    }

    private void addComponents() {

        add(
                mainTabs,
                BorderLayout.CENTER);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                new Runnable() {

                    @Override
                    public void run() {

                        new MainGUI();

                    }

                });
    }
}