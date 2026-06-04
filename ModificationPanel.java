
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ModificationPanel extends JPanel {

    public ModificationPanel() {
        JTextField id = new JTextField();
        JTextField first = new JTextField();
        JTextField last = new JTextField();
        JButton search = new JButton("Search");
        JButton update = new JButton("Update");

        setLayout(new GridLayout(5,2));
        add(new JLabel("Investor ID")); add(id);
        add(search); add(new JLabel());
        add(new JLabel("First Name")); add(first);
        add(new JLabel("Last Name")); add(last);
        add(new JLabel()); add(update);

        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection c = DatabaseConnection.getInstance().getConnection();
                    PreparedStatement ps = c.prepareStatement(
                        "SELECT * FROM Investor WHERE InvestorID=?");
                    ps.setInt(1,Integer.parseInt(id.getText()));
                    ResultSet rs = ps.executeQuery();

                    if(rs.next()) {
                        first.setText(rs.getString("FirstName"));
                        last.setText(rs.getString("LastName"));
                    }
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
            }
        });

        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection c = DatabaseConnection.getInstance().getConnection();
                    PreparedStatement ps = c.prepareStatement(
                        "UPDATE Investor SET FirstName=?, LastName=? WHERE InvestorID=?");
                    ps.setString(1, first.getText());
                    ps.setString(2, last.getText());
                    ps.setInt(3, Integer.parseInt(id.getText()));
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Investor updated.");
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
            }
        });
    }
}
