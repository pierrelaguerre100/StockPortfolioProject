
import javax.swing.*;

public class MainGUI extends JFrame {
    public MainGUI() {
        setTitle("Stock Portfolio Management System");
        setSize(1200,800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Data Insertion", new InsertionPanel());
        tabs.addTab("Data Modification", new ModificationPanel());
        tabs.addTab("Data Query", new QueryPanel());

        add(tabs);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainGUI();
    }
}
