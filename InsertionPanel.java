
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class InsertionPanel extends JPanel {

    public InsertionPanel() {
        setLayout(new BorderLayout());
        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Investor", investorPanel());
        add(tabs);
    }

    private JPanel investorPanel() {
        JPanel p = new JPanel(new GridLayout(5,2));

        JTextField first = new JTextField();
        JTextField last = new JTextField();
        JTextField email = new JTextField();
        JTextField phone = new JTextField();
        JButton insert = new JButton("Insert");

        p.add(new JLabel("First Name")); p.add(first);
        p.add(new JLabel("Last Name")); p.add(last);
        p.add(new JLabel("Email")); p.add(email);
        p.add(new JLabel("Phone")); p.add(phone);
        p.add(new JLabel()); p.add(insert);

        insert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection c = DatabaseConnection.getInstance().getConnection();
                    String sql = "INSERT INTO Investor(FirstName,LastName,Email,Phone) VALUES(?,?,?,?)";
                    PreparedStatement ps = c.prepareStatement(sql);
                    ps.setString(1, first.getText());
                    ps.setString(2, last.getText());
                    ps.setString(3, email.getText());
                    ps.setString(4, phone.getText());
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Investor inserted.");
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
            }
        });

        return p;
    }
}
