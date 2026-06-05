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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import javax.swing.table.DefaultTableModel;

public class QueryPanel extends JPanel {

    private JTextArea statusArea;

    public QueryPanel() {

        setLayout(new BorderLayout());

        statusArea =
                new JTextArea(4,40);

        statusArea.setEditable(false);

        JTabbedPane tabs =
                new JTabbedPane();

        tabs.addTab(
                "Transactions By Investor",
                createQuery1Panel());

        tabs.addTab(
                "Top N Stocks By Volume",
                createQuery2Panel());

        add(
                tabs,
                BorderLayout.CENTER);

        add(
                new JScrollPane(statusArea),
                BorderLayout.SOUTH);
    }

    private JPanel createQuery1Panel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout());

        JPanel filters =
                new JPanel(
                        new GridLayout(4,2,5,5));

        JComboBox<String> cboInvestor =
                new JComboBox<String>();

        JComboBox<String> cboTradeType =
                new JComboBox<String>();

        JComboBox<String> cboSort =
                new JComboBox<String>();

        loadInvestors(cboInvestor);

        cboTradeType.addItem("All");
        cboTradeType.addItem("BUY");
        cboTradeType.addItem("SELL");

        cboSort.addItem(
                "Date Newest First");

        cboSort.addItem(
                "Date Oldest First");

        cboSort.addItem(
                "Quantity High-Low");

        JButton btnRun =
                new JButton(
                        "Run Query");

        filters.add(
                new JLabel("Investor"));
        filters.add(cboInvestor);

        filters.add(
                new JLabel("Trade Type"));
        filters.add(cboTradeType);

        filters.add(
                new JLabel("Sort"));
        filters.add(cboSort);

        filters.add(new JLabel());
        filters.add(btnRun);

        String[] columns = {

                "Trade Date",
                "Trade Type",
                "Ticker Symbol",
                "Company Name",
                "Industry",
                "Quantity",
                "Price Per Share"

        };

        DefaultTableModel model =
                new DefaultTableModel(
                        columns,
                        0);

        JTable table =
                new JTable(model);

        btnRun.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e) {

                        runTransactionQuery(
                                cboInvestor,
                                cboTradeType,
                                cboSort,
                                model);

                    }

                });

        panel.add(
                filters,
                BorderLayout.NORTH);

        panel.add(
                new JScrollPane(table),
                BorderLayout.CENTER);

        return panel;
    }

    private JPanel createQuery2Panel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout());

        JPanel filters =
                new JPanel(
                        new GridLayout(3,2,5,5));

        JComboBox<String> cboIndustry =
                new JComboBox<String>();

        JComboBox<String> cboTopN =
                new JComboBox<String>();

        loadIndustries(cboIndustry);

        cboTopN.addItem("5");
        cboTopN.addItem("15");
        cboTopN.addItem("25");
        cboTopN.addItem("All");

        JButton btnRun =
                new JButton(
                        "Run Query");

        filters.add(
                new JLabel("Industry"));
        filters.add(cboIndustry);

        filters.add(
                new JLabel("Top N"));
        filters.add(cboTopN);

        filters.add(new JLabel());
        filters.add(btnRun);

        String[] columns = {

                "Rank",
                "Ticker Symbol",
                "Company Name",
                "Industry",
                "Total Shares Traded",
                "Number Of Transactions"

        };

        DefaultTableModel model =
                new DefaultTableModel(
                        columns,
                        0);

        JTable table =
                new JTable(model);

        btnRun.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e) {

                        runVolumeQuery(
                                cboIndustry,
                                cboTopN,
                                model);

                    }

                });

        panel.add(
                filters,
                BorderLayout.NORTH);

        panel.add(
                new JScrollPane(table),
                BorderLayout.CENTER);

        return panel;
    }
        
        private void loadInvestors(
                JComboBox<String> cboInvestor) {

            try {

                cboInvestor.removeAllItems();

                Connection conn =
                        DatabaseConnection.getConnection();

                String sql =
                        "SELECT InvestorID, " +
                        "FirstName, LastName " +
                        "FROM Investor " +
                        "ORDER BY LastName";

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery();

                while(rs.next()) {

                    cboInvestor.addItem(
                            rs.getInt("InvestorID")
                            + " - "
                            + rs.getString("FirstName")
                            + " "
                            + rs.getString("LastName"));

                }

            } catch(Exception ex) {

                statusArea.setText(
                        ex.getMessage());

            }
        }

        private void loadIndustries(
                JComboBox<String> cboIndustry) {

            try {

                cboIndustry.removeAllItems();

                cboIndustry.addItem("All");

                Connection conn =
                        DatabaseConnection.getConnection();

                String sql =
                        "SELECT DISTINCT Industry " +
                        "FROM Company " +
                        "ORDER BY Industry";

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery();

                while(rs.next()) {

                    cboIndustry.addItem(
                            rs.getString(
                                    "Industry"));

                }

            } catch(Exception ex) {

                statusArea.setText(
                        ex.getMessage());

            }
        }

        private void runTransactionQuery(
                JComboBox<String> cboInvestor,
                JComboBox<String> cboTradeType,
                JComboBox<String> cboSort,
                DefaultTableModel model) {

            try {

                model.setRowCount(0);

                String investor =
                        cboInvestor
                                .getSelectedItem()
                                .toString();

                int investorID =
                        Integer.parseInt(
                                investor.split("-")[0]
                                        .trim());

                String tradeType =
                        cboTradeType
                                .getSelectedItem()
                                .toString();

                String sort =
                        cboSort
                                .getSelectedItem()
                                .toString();

                Connection conn =
                        DatabaseConnection.getConnection();

                String sql =
                        "SELECT " +
                        "t.TradeDate, " +
                        "t.TradeType, " +
                        "s.TickerSymbol, " +
                        "c.CompanyName, " +
                        "c.Industry, " +
                        "t.Quantity, " +
                        "t.PricePerShare " +
                        "FROM Investor i " +
                        "JOIN BrokerageAccount b " +
                        "ON i.InvestorID=b.InvestorID " +
                        "JOIN TradeTransaction t " +
                        "ON b.AccountID=t.AccountID " +
                        "JOIN Stock s " +
                        "ON t.StockID=s.StockID " +
                        "JOIN Company c " +
                        "ON s.CompanyID=c.CompanyID " +
                        "WHERE i.InvestorID=? ";

                if(!tradeType.equals("All")) {

                    sql +=
                        "AND t.TradeType=? ";

                }

                if(sort.equals(
                        "Date Newest First")) {

                    sql +=
                        "ORDER BY t.TradeDate DESC";

                }
                else if(sort.equals(
                        "Date Oldest First")) {

                    sql +=
                        "ORDER BY t.TradeDate ASC";

                }
                else {

                    sql +=
                        "ORDER BY t.Quantity DESC";

                }

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                stmt.setInt(
                        1,
                        investorID);

                if(!tradeType.equals("All")) {

                    stmt.setString(
                            2,
                            tradeType);

                }

                ResultSet rs =
                        stmt.executeQuery();

                while(rs.next()) {

                    model.addRow(
                            new Object[] {

                                    rs.getDate(
                                            "TradeDate"),

                                    rs.getString(
                                            "TradeType"),

                                    rs.getString(
                                            "TickerSymbol"),

                                    rs.getString(
                                            "CompanyName"),

                                    rs.getString(
                                            "Industry"),

                                    rs.getInt(
                                            "Quantity"),

                                    rs.getDouble(
                                            "PricePerShare")

                            });

                }

                statusArea.setText(
                        "Transaction query completed successfully.");

            } catch(Exception ex) {

                statusArea.setText(
                        ex.getMessage());

            }
        }

        private void runVolumeQuery(
                JComboBox<String> cboIndustry,
                JComboBox<String> cboTopN,
                DefaultTableModel model) {

            try {

                model.setRowCount(0);

                String industry =
                        cboIndustry
                                .getSelectedItem()
                                .toString();

                String topN =
                        cboTopN
                                .getSelectedItem()
                                .toString();

                Connection conn =
                        DatabaseConnection.getConnection();

                String sql =
                        "SELECT " +
                        "s.TickerSymbol, " +
                        "c.CompanyName, " +
                        "c.Industry, " +
                        "SUM(t.Quantity) AS TotalShares, " +
                        "COUNT(*) AS NumTransactions " +
                        "FROM Stock s " +
                        "JOIN TradeTransaction t " +
                        "ON s.StockID=t.StockID " +
                        "JOIN Company c " +
                        "ON s.CompanyID=c.CompanyID ";

                if(!industry.equals("All")) {

                    sql +=
                        "WHERE c.Industry=? ";

                }

                sql +=
                    "GROUP BY " +
                    "s.StockID, " +
                    "c.CompanyName, " +
                    "c.Industry " +
                    "ORDER BY TotalShares DESC";

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                if(!industry.equals("All")) {

                    stmt.setString(
                            1,
                            industry);

                }

                ResultSet rs =
                        stmt.executeQuery();

                int rank = 1;

                while(rs.next()) {

                    if(!topN.equals("All")) {

                        int limit =
                                Integer.parseInt(
                                        topN);

                        if(rank > limit) {

                            break;

                        }
                    }

                    model.addRow(
                            new Object[] {

                                    rank,

                                    rs.getString(
                                            "TickerSymbol"),

                                    rs.getString(
                                            "CompanyName"),

                                    rs.getString(
                                            "Industry"),

                                    rs.getInt(
                                            "TotalShares"),

                                    rs.getInt(
                                            "NumTransactions")

                            });

                    rank++;

                }

                statusArea.setText(
                        "Volume query completed successfully.");

            } catch(Exception ex) {

                statusArea.setText(
                        ex.getMessage());

            }
        }
    }