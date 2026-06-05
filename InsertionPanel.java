
import java.awt.BorderLayout;
import java.awt.GridLayout;
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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertionPanel extends JPanel {

    private JTextArea statusArea;

    private JTable investorTable;
    private JTable companyTable;
    private JTable stockTable;

    public InsertionPanel() {

        setLayout(new BorderLayout());

        statusArea = new JTextArea(4, 40);
        statusArea.setEditable(false);

        JTabbedPane tabs = new JTabbedPane();

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
                        new BorderLayout());

        JPanel form =
                new JPanel(
                        new GridLayout(5, 2, 5, 5));

        JTextField txtFirstName =
                new JTextField();

        JTextField txtLastName =
                new JTextField();

        JTextField txtEmail =
                new JTextField();

        JTextField txtPhone =
                new JTextField();

        JButton btnInsert =
                new JButton(
                        "Insert Investor");

        form.add(
                new JLabel("First Name"));
        form.add(txtFirstName);

        form.add(
                new JLabel("Last Name"));
        form.add(txtLastName);

        form.add(
                new JLabel("Email"));
        form.add(txtEmail);

        form.add(
                new JLabel("Phone"));
        form.add(txtPhone);

        form.add(new JLabel());
        form.add(btnInsert);

        String[] columns = {
                "Investor ID",
                "First Name",
                "Last Name",
                "Email",
                "Phone"
        };

        DefaultTableModel model =
                new DefaultTableModel(
                        columns,
                        0);

        investorTable =
                new JTable(model);

        btnInsert.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e) {

                        insertInvestor(
                                txtFirstName,
                                txtLastName,
                                txtEmail,
                                txtPhone);

                    }

                });

        panel.add(
                form,
                BorderLayout.NORTH);

        panel.add(
                new JScrollPane(
                        investorTable),
                BorderLayout.CENTER);

        refreshInvestorTable();

        return panel;
    }

    private JPanel createCompanyPanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout());

        JPanel form =
                new JPanel(
                        new GridLayout(5, 2, 5, 5));

        JTextField txtCompanyName =
                new JTextField();

        JTextField txtIndustry =
                new JTextField();

        JTextField txtHeadquarters =
                new JTextField();

        JTextField txtFoundedYear =
                new JTextField();

        JButton btnInsert =
                new JButton(
                        "Insert Company");

        form.add(
                new JLabel("Company Name"));
        form.add(txtCompanyName);

        form.add(
                new JLabel("Industry"));
        form.add(txtIndustry);

        form.add(
                new JLabel("Headquarters"));
        form.add(txtHeadquarters);

        form.add(
                new JLabel("Founded Year"));
        form.add(txtFoundedYear);

        form.add(new JLabel());
        form.add(btnInsert);

        String[] columns = {
                "Company ID",
                "Company Name",
                "Industry",
                "Headquarters",
                "Founded Year"
        };

        DefaultTableModel model =
                new DefaultTableModel(
                        columns,
                        0);

        companyTable =
                new JTable(model);

        btnInsert.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e) {

                        insertCompany(
                                txtCompanyName,
                                txtIndustry,
                                txtHeadquarters,
                                txtFoundedYear);

                    }

                });

        panel.add(
                form,
                BorderLayout.NORTH);

        panel.add(
                new JScrollPane(
                        companyTable),
                BorderLayout.CENTER);

        refreshCompanyTable();

        return panel;
    }
        private JPanel createStockPanel() {

            JPanel panel =
                    new JPanel(
                            new BorderLayout());

            JPanel form =
                    new JPanel(
                            new GridLayout(5, 2, 5, 5));

            JTextField txtTickerSymbol =
                    new JTextField();

            JTextField txtExchangeName =
                    new JTextField();

            JTextField txtCurrentPrice =
                    new JTextField();

            JComboBox<String> cboCompany =
                    new JComboBox<String>();

            loadCompanies(cboCompany);

            JButton btnInsert =
                    new JButton(
                            "Insert Stock");

            form.add(
                    new JLabel("Ticker Symbol"));
            form.add(txtTickerSymbol);

            form.add(
                    new JLabel("Exchange Name"));
            form.add(txtExchangeName);

            form.add(
                    new JLabel("Current Price"));
            form.add(txtCurrentPrice);

            form.add(
                    new JLabel("Company"));
            form.add(cboCompany);

            form.add(new JLabel());
            form.add(btnInsert);

            String[] columns = {
                    "Stock ID",
                    "Ticker Symbol",
                    "Exchange Name",
                    "Current Price",
                    "Company ID"
            };

            DefaultTableModel model =
                    new DefaultTableModel(
                            columns,
                            0);

            stockTable =
                    new JTable(model);

            btnInsert.addActionListener(
                    new ActionListener() {

                        @Override
                        public void actionPerformed(
                                ActionEvent e) {

                            insertStock(
                                    txtTickerSymbol,
                                    txtExchangeName,
                                    txtCurrentPrice,
                                    cboCompany);

                        }

                    });

            panel.add(
                    form,
                    BorderLayout.NORTH);

            panel.add(
                    new JScrollPane(
                            stockTable),
                    BorderLayout.CENTER);

            refreshStockTable();

            return panel;
        }

        private void insertInvestor(
                JTextField txtFirstName,
                JTextField txtLastName,
                JTextField txtEmail,
                JTextField txtPhone) {

            try {

                if (txtFirstName.getText().trim().isEmpty()
                        || txtLastName.getText().trim().isEmpty()
                        || txtEmail.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Required fields missing.");

                    return;
                }

                Connection conn =
                        DatabaseConnection.getConnection();

                String sql =
                        "INSERT INTO Investor " +
                        "(FirstName,LastName,Email,Phone) " +
                        "VALUES(?,?,?,?)";

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

                stmt.executeUpdate();

                statusArea.setText(
                        "Investor inserted successfully.");

                refreshInvestorTable();

            } catch (Exception ex) {

                statusArea.setText(
                        ex.getMessage());

            }
        }

        private void insertCompany(
                JTextField txtCompanyName,
                JTextField txtIndustry,
                JTextField txtHeadquarters,
                JTextField txtFoundedYear) {

            try {

                Connection conn =
                        DatabaseConnection.getConnection();

                String sql =
                        "INSERT INTO Company " +
                        "(CompanyName,Industry," +
                        "Headquarters,FoundedYear) " +
                        "VALUES(?,?,?,?)";

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

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

                stmt.executeUpdate();

                statusArea.setText(
                        "Company inserted successfully.");

                refreshCompanyTable();

            } catch (Exception ex) {

                statusArea.setText(
                        ex.getMessage());

            }
        }

        private void insertStock(
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
                                selected.split("-")[0].trim());

                String sql =
                        "INSERT INTO Stock " +
                        "(TickerSymbol," +
                        "ExchangeName," +
                        "CurrentPrice," +
                        "CompanyID) " +
                        "VALUES(?,?,?,?)";

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

                stmt.executeUpdate();

                statusArea.setText(
                        "Stock inserted successfully.");

                refreshStockTable();

            } catch (Exception ex) {

                statusArea.setText(
                        ex.getMessage());

            }
        }

        private void loadCompanies(
                JComboBox<String> cboCompany) {

            try {

                Connection conn =
                        DatabaseConnection.getConnection();

                String sql =
                        "SELECT CompanyID, CompanyName " +
                        "FROM Company";

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery();

                while (rs.next()) {

                    cboCompany.addItem(
                            rs.getInt("CompanyID")
                            + " - "
                            + rs.getString("CompanyName"));

                }

            } catch (Exception ex) {

                statusArea.setText(
                        ex.getMessage());

            }
        }

        private void refreshInvestorTable() {

            try {

                DefaultTableModel model =
                        (DefaultTableModel)
                                investorTable.getModel();

                model.setRowCount(0);

                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(
                                "SELECT * FROM Investor");

                ResultSet rs =
                        stmt.executeQuery();

                while (rs.next()) {

                    model.addRow(
                            new Object[] {
                                    rs.getInt("InvestorID"),
                                    rs.getString("FirstName"),
                                    rs.getString("LastName"),
                                    rs.getString("Email"),
                                    rs.getString("Phone")
                            });

                }

            } catch (Exception ex) {

                statusArea.setText(
                        ex.getMessage());

            }
        }

        private void refreshCompanyTable() {

            try {

                DefaultTableModel model =
                        (DefaultTableModel)
                                companyTable.getModel();

                model.setRowCount(0);

                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(
                                "SELECT * FROM Company");

                ResultSet rs =
                        stmt.executeQuery();

                while (rs.next()) {

                    model.addRow(
                            new Object[] {
                                    rs.getInt("CompanyID"),
                                    rs.getString("CompanyName"),
                                    rs.getString("Industry"),
                                    rs.getString("Headquarters"),
                                    rs.getInt("FoundedYear")
                            });

                }

            } catch (Exception ex) {

                statusArea.setText(
                        ex.getMessage());

            }
        }

        private void refreshStockTable() {

            try {

                DefaultTableModel model =
                        (DefaultTableModel)
                                stockTable.getModel();

                model.setRowCount(0);

                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(
                                "SELECT * FROM Stock");

                ResultSet rs =
                        stmt.executeQuery();

                while (rs.next()) {

                    model.addRow(
                            new Object[] {
                                    rs.getInt("StockID"),
                                    rs.getString("TickerSymbol"),
                                    rs.getString("ExchangeName"),
                                    rs.getDouble("CurrentPrice"),
                                    rs.getInt("CompanyID")
                            });

                }

            } catch (Exception ex) {

                statusArea.setText(
                        ex.getMessage());

            }
        }
    }