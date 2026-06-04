
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class QueryPanel extends JPanel {

    public QueryPanel() {
        setLayout(new BorderLayout());

        JButton run = new JButton("Run Query");
        JTable table = new JTable();
        add(run, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        run.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection c = DatabaseConnection.getInstance().getConnection();
                    Statement st = c.createStatement();
                    ResultSet rs = st.executeQuery(
                        "SELECT InvestorID, FirstName, LastName FROM Investor");

                    DefaultTableModel model = new DefaultTableModel();
                    model.addColumn("InvestorID");
                    model.addColumn("FirstName");
                    model.addColumn("LastName");

                    while(rs.next()) {
                        model.addRow(new Object[]{
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3)
                        });
                    }

                    table.setModel(model);

                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
            }
        });
    }
}
