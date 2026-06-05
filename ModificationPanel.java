
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ModificationPanel extends JPanel {

    private JTextArea statusArea;

    public ModificationPanel() {

        setLayout(new BorderLayout());

        statusArea = new JTextArea(4,40);

        statusArea.setEditable(false);

        JTabbedPane tabs =
                new JTabbedPane();

        tabs.addTab(
                "Investor",
                createInvestorPanel());

        tabs.addTab(
                "Company",
                createCompanyPanel());

        tabs.addTab(
                "Stock",
                createStockPanel());

        add(
                tabs,
                BorderLayout.CENTER);

        add(
                new JScrollPane(statusArea),
                BorderLayout.SOUTH);
    }

    private JPanel createInvestorPanel() {

        JPanel panel =
                new JPanel(
                        new GridLayout(7,2,5,5));

        JTextField txtInvestorID =
                new JTextField();

        JTextField txtFirstName =
                new JTextField();

        JTextField txtLastName =
                new JTextField();

        JTextField txtEmail =
                new JTextField();

        JTextField txtPhone =
                new JTextField();

        JButton btnSearch =
                new JButton("Search");

        JButton btnUpdate =
                new JButton("Update");

        panel.add(
                new JLabel("Investor ID"));
        panel.add(txtInvestorID);

        panel.add(btnSearch);
        panel.add(new JLabel());

        panel.add(
                new JLabel("First Name"));
        panel.add(txtFirstName);

        panel.add(
                new JLabel("Last Name"));
        panel.add(txtLastName);

        panel.add(
                new JLabel("Email"));
        panel.add(txtEmail);

        panel.add(
                new JLabel("Phone"));
        panel.add(txtPhone);

        panel.add(new JLabel());
        panel.add(btnUpdate);

        btnSearch.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e) {

                        searchInvestor(
                                txtInvestorID,
                                txtFirstName,
                                txtLastName,
                                txtEmail,
                                txtPhone);

                    }
                });

        btnUpdate.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e) {

                        updateInvestor(
                                txtInvestorID,
                                txtFirstName,
                                txtLastName,
                                txtEmail,
                                txtPhone);

                    }
                });

        return panel;
    }

    private JPanel createCompanyPanel() {

        JPanel panel =
                new JPanel(
                        new GridLayout(7,2,5,5));

        JTextField txtCompanyID =
                new JTextField();

        JTextField txtCompanyName =
                new JTextField();

        JTextField txtIndustry =
                new JTextField();

        JTextField txtHeadquarters =
                new JTextField();

        JTextField txtFoundedYear =
                new JTextField();

        JButton btnSearch =
                new JButton("Search");

        JButton btnUpdate =
                new JButton("Update");

        panel.add(
                new JLabel("Company ID"));
        panel.add(txtCompanyID);

        panel.add(btnSearch);
        panel.add(new JLabel());

        panel.add(
                new JLabel("Company Name"));
        panel.add(txtCompanyName);

        panel.add(
                new JLabel("Industry"));
        panel.add(txtIndustry);

        panel.add(
                new JLabel("Headquarters"));
        panel.add(txtHeadquarters);

        panel.add(
                new JLabel("Founded Year"));
        panel.add(txtFoundedYear);

        panel.add(new JLabel());
        panel.add(btnUpdate);

        btnSearch.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e) {

                        searchCompany(
                                txtCompanyID,
                                txtCompanyName,
                                txtIndustry,
                                txtHeadquarters,
                                txtFoundedYear);

                    }
                });

        btnUpdate.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e) {

                        updateCompany(
                                txtCompanyID,
                                txtCompanyName,
                                txtIndustry,
                                txtHeadquarters,
                                txtFoundedYear);

                    }
                });

        return panel;
    }
        private JPanel createStockPanel() {

            JPanel panel =
                    new JPanel(
                            new GridLayout(7,2,5,5));

            JTextField txtStockID =
                    new JTextField();

            JTextField txtTickerSymbol =
                    new JTextField();

            JTextField txtExchangeName =
                    new JTextField();

            JTextField txtCurrentPrice =
                    new JTextField();

            JComboBox<String> cboCompany =
                    new JComboBox<String>();

            loadCompanies(cboCompany);

            JButton btnSearch =
                    new JButton("Search");

            JButton btnUpdate =
                    new JButton("Update");

            panel.add(
                    new JLabel("Stock ID"));
            panel.add(txtStockID);

            panel.add(btnSearch);
            panel.add(new JLabel());

            panel.add(
                    new JLabel("Ticker Symbol"));
            panel.add(txtTickerSymbol);

            panel.add(
                    new JLabel("Exchange Name"));
            panel.add(txtExchangeName);

            panel.add(
                    new JLabel("Current Price"));
            panel.add(txtCurrentPrice);

            panel.add(
                    new JLabel("Company"));
            panel.add(cboCompany);

            panel.add(new JLabel());
            panel.add(btnUpdate);

            btnSearch.addActionListener(
                    new ActionListener() {

                        @Override
                        public void actionPerformed(
                                ActionEvent e) {

                            searchStock(
                                    txtStockID,
                                    txtTickerSymbol,
                                    txtExchangeName,
                                    txtCurrentPrice,
                                    cboCompany);

                        }
                    });

            btnUpdate.addActionListener(
                    new ActionListener() {

                        @Override
                        public void actionPerformed(
                                ActionEvent e) {

                            updateStock(
                                    txtStockID,
                                    txtTickerSymbol,
                                    txtExchangeName,
                                    txtCurrentPrice,
                                    cboCompany);

                        }
                    });

            return panel;
        }

        private void searchInvestor(
                JTextField txtInvestorID,
                JTextField txtFirstName,
                JTextField txtLastName,
                JTextField txtEmail,
                JTextField txtPhone) {

            try {

                Connection conn =
                        DatabaseConnection.getConnection();

                String sql =
                        "SELECT * FROM Investor " +
                        "WHERE InvestorID=?";

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                stmt.setInt(
                        1,
                        Integer.parseInt(
                                txtInvestorID.getText()));

                ResultSet rs =
                        stmt.executeQuery();

                if(rs.next()) {

                    txtFirstName.setText(
                            rs.getString("FirstName"));

                    txtLastName.setText(
                            rs.getString("LastName"));

                    txtEmail.setText(
                            rs.getString("Email"));

                    txtPhone.setText(
                            rs.getString("Phone"));

                    statusArea.setText(
                            "Investor found.");

                }
                else {

                    statusArea.setText(
                            "Investor not found.");

                }

            } catch(Exception ex) {

                statusArea.setText(
                        ex.getMessage());

            }
        }

        private void updateInvestor(
                JTextField txtInvestorID,
                JTextField txtFirstName,
                JTextField txtLastName,
                JTextField txtEmail,
                JTextField txtPhone) {

            try {

                Connection conn =
                        DatabaseConnection.getConnection();

                String sql =
                        "UPDATE Investor " +
                        "SET FirstName=?, " +
                        "LastName=?, " +
                        "Email=?, " +
                        "Phone=? " +
                        "WHERE InvestorID=?";

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                stmt.setString(
                        1,
                        txtFirstName.getText());

                stmt.setString(
                        2,
                        txtLastName.getText());

                stmt.setString(
                        3,
                        txtEmail.getText());

                stmt.setString(
                        4,
                        txtPhone.getText());

                stmt.setInt(
                        5,
                        Integer.parseInt(
                                txtInvestorID.getText()));

                stmt.executeUpdate();

                statusArea.setText(
                        "Investor updated successfully.");

                JOptionPane.showMessageDialog(
                        this,
                        "Investor updated successfully.");

            } catch(Exception ex) {

                statusArea.setText(
                        ex.getMessage());

            }
        }

        private void searchCompany(
                JTextField txtCompanyID,
                JTextField txtCompanyName,
                JTextField txtIndustry,
                JTextField txtHeadquarters,
                JTextField txtFoundedYear) {

            try {

                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(
                                "SELECT * FROM Company WHERE CompanyID=?");

                stmt.setInt(
                        1,
                        Integer.parseInt(
                                txtCompanyID.getText()));

                ResultSet rs =
                        stmt.executeQuery();

                if(rs.next()) {

                    txtCompanyName.setText(
                            rs.getString("CompanyName"));

                    txtIndustry.setText(
                            rs.getString("Industry"));

                    txtHeadquarters.setText(
                            rs.getString("Headquarters"));

                    txtFoundedYear.setText(
                            String.valueOf(
                                    rs.getInt("FoundedYear")));

                    statusArea.setText(
                            "Company found.");

                }

            } catch(Exception ex) {

                statusArea.setText(
                        ex.getMessage());

            }
        }

        private void updateCompany(
                JTextField txtCompanyID,
                JTextField txtCompanyName,
                JTextField txtIndustry,
                JTextField txtHeadquarters,
                JTextField txtFoundedYear) {

            try {

                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(
                                "UPDATE Company " +
                                "SET CompanyName=?, " +
                                "Industry=?, " +
                                "Headquarters=?, " +
                                "FoundedYear=? " +
                                "WHERE CompanyID=?");

                stmt.setString(
                        1,
                        txtCompanyName.getText());

                stmt.setString(
                        2,
                        txtIndustry.getText());

                stmt.setString(
                        3,
                        txtHeadquarters.getText());

                stmt.setInt(
                        4,
                        Integer.parseInt(
                                txtFoundedYear.getText()));

                stmt.setInt(
                        5,
                        Integer.parseInt(
                                txtCompanyID.getText()));

                stmt.executeUpdate();

                statusArea.setText(
                        "Company updated successfully.");

                JOptionPane.showMessageDialog(
                        this,
                        "Company updated successfully.");

            } catch(Exception ex) {

                statusArea.setText(
                        ex.getMessage());

            }
        }
        
        private void searchStock(
                JTextField txtStockID,
                JTextField txtTickerSymbol,
                JTextField txtExchangeName,
                JTextField txtCurrentPrice,
                JComboBox<String> cboCompany) {

            try {

                Connection conn =
                        DatabaseConnection.getConnection();

                String sql =
                        "SELECT * FROM Stock " +
                        "WHERE StockID=?";

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                stmt.setInt(
                        1,
                        Integer.parseInt(
                                txtStockID.getText()));

                ResultSet rs =
                        stmt.executeQuery();

                if(rs.next()) {

                    txtTickerSymbol.setText(
                            rs.getString(
                                    "TickerSymbol"));

                    txtExchangeName.setText(
                            rs.getString(
                                    "ExchangeName"));

                    txtCurrentPrice.setText(
                            String.valueOf(
                                    rs.getDouble(
                                            "CurrentPrice")));

                    int companyID =
                            rs.getInt(
                                    "CompanyID");

                    for(int i = 0;
                        i < cboCompany.getItemCount();
                        i++) {

                        String item =
                                cboCompany.getItemAt(i);

                        if(item.startsWith(
                                String.valueOf(
                                        companyID))) {

                            cboCompany.setSelectedIndex(i);

                            break;
                        }
                    }

                    statusArea.setText(
                            "Stock found.");

                }
                else {

                    statusArea.setText(
                            "Stock not found.");

                }

            } catch(Exception ex) {

                statusArea.setText(
                        ex.getMessage());

            }
        }

        private void updateStock(
                JTextField txtStockID,
                JTextField txtTickerSymbol,
                JTextField txtExchangeName,
                JTextField txtCurrentPrice,
                JComboBox<String> cboCompany) {

            try {

                Connection conn =
                        DatabaseConnection.getConnection();

                String selected =
                        cboCompany
                                .getSelectedItem()
                                .toString();

                int companyID =
                        Integer.parseInt(
                                selected.split("-")[0]
                                        .trim());

                String sql =
                        "UPDATE Stock " +
                        "SET TickerSymbol=?, " +
                        "ExchangeName=?, " +
                        "CurrentPrice=?, " +
                        "CompanyID=? " +
                        "WHERE StockID=?";

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                stmt.setString(
                        1,
                        txtTickerSymbol.getText());

                stmt.setString(
                        2,
                        txtExchangeName.getText());

                stmt.setDouble(
                        3,
                        Double.parseDouble(
                                txtCurrentPrice.getText()));

                stmt.setInt(
                        4,
                        companyID);

                stmt.setInt(
                        5,
                        Integer.parseInt(
                                txtStockID.getText()));

                stmt.executeUpdate();

                statusArea.setText(
                        "Stock updated successfully.");

                JOptionPane.showMessageDialog(
                        this,
                        "Stock updated successfully.");

            } catch(Exception ex) {

                statusArea.setText(
                        ex.getMessage());

            }
        }

        private void loadCompanies(
                JComboBox<String> cboCompany) {

            try {

                cboCompany.removeAllItems();

                Connection conn =
                        DatabaseConnection.getConnection();

                String sql =
                        "SELECT CompanyID, CompanyName " +
                        "FROM Company " +
                        "ORDER BY CompanyName";

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery();

                while(rs.next()) {

                    cboCompany.addItem(
                            rs.getInt("CompanyID")
                            + " - "
                            + rs.getString(
                                    "CompanyName"));
                }

            } catch(Exception ex) {

                statusArea.setText(
                        ex.getMessage());

            }
        }
    }