CREATE DATABASE StockPortfolioDB;
USE StockPortfolioDB;

CREATE TABLE Investor (
    InvestorID INT AUTO_INCREMENT,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Email VARCHAR(100) NOT NULL,
    Phone VARCHAR(20),
    PRIMARY KEY (InvestorID)
);

CREATE TABLE BrokerageAccount (
    AccountID INT AUTO_INCREMENT,
    AccountType VARCHAR(50) NOT NULL,
    BrokerName VARCHAR(100) NOT NULL,
    Balance DECIMAL(12,2) NOT NULL,
    InvestorID INT,
    PRIMARY KEY (AccountID)
);

CREATE TABLE Company (
    CompanyID INT AUTO_INCREMENT,
    CompanyName VARCHAR(100) NOT NULL,
    Industry VARCHAR(100) NOT NULL,
    Headquarters VARCHAR(100),
    FoundedYear INT,
    PRIMARY KEY (CompanyID)
);

CREATE TABLE Stock (
    StockID INT AUTO_INCREMENT,
    TickerSymbol VARCHAR(10) NOT NULL,
    ExchangeName VARCHAR(50) NOT NULL,
    CurrentPrice DECIMAL(10,2) NOT NULL,
    CompanyID INT,
    PRIMARY KEY (StockID)
);

CREATE TABLE TradeTransaction (
    TransactionID INT AUTO_INCREMENT,
    TradeDate DATE NOT NULL,
    TradeType VARCHAR(10) NOT NULL,
    Quantity INT NOT NULL,
    PricePerShare DECIMAL(10,2) NOT NULL,
    AccountID INT,
    StockID INT,
    PRIMARY KEY (TransactionID)
);

ALTER TABLE BrokerageAccount
ADD CONSTRAINT FK_BrokerageAccount_Investor
FOREIGN KEY (InvestorID)
REFERENCES Investor(InvestorID);

ALTER TABLE Stock
ADD CONSTRAINT FK_Stock_Company
FOREIGN KEY (CompanyID)
REFERENCES Company(CompanyID);

ALTER TABLE TradeTransaction
ADD CONSTRAINT FK_Transaction_Account
FOREIGN KEY (AccountID)
REFERENCES BrokerageAccount(AccountID);

ALTER TABLE TradeTransaction
ADD CONSTRAINT FK_Transaction_Stock
FOREIGN KEY (StockID)
REFERENCES Stock(StockID);

INSERT INTO Investor
(FirstName, LastName, Email, Phone)
VALUES
('John', 'Smith', 'john@gmail.com', '555-1111'),
('Sarah', 'Johnson', 'sarah@gmail.com', '555-2222');

INSERT INTO BrokerageAccount
(AccountType, BrokerName, Balance, InvestorID)
VALUES
('Individual', 'Fidelity', 10000.00, 1),
('Retirement', 'Charles Schwab', 25000.00, 2);

INSERT INTO Company
(CompanyName, Industry, Headquarters, FoundedYear)
VALUES
('Apple', 'Technology', 'California', 1976),
('Tesla', 'Automotive', 'Texas', 2003),
('Microsoft', 'Technology', 'Washington', 1975);

INSERT INTO Stock
(TickerSymbol, ExchangeName, CurrentPrice, CompanyID)
VALUES
('AAPL', 'NASDAQ', 215.50, 1),
('TSLA', 'NASDAQ', 320.25, 2),
('MSFT', 'NASDAQ', 485.75, 3);

INSERT INTO TradeTransaction
(TradeDate, TradeType, Quantity, PricePerShare, AccountID, StockID)
VALUES
('2026-06-01', 'BUY', 10, 215.50, 1, 1),
('2026-06-02', 'BUY', 5, 320.25, 2, 2),
('2026-06-03', 'SELL', 2, 485.75, 1, 3);

SELECT * FROM Investor;
SELECT * FROM BrokerageAccount;
SELECT * FROM Company;
SELECT * FROM Stock;
SELECT * FROM TradeTransaction;
