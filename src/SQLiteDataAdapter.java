package edu.auburn;

import java.sql.*;

public class SQLiteDataAdapter implements IDataAccess {
    Connection conn = null;
    int errorCode = 0;

    public boolean connect(String path) {
        try {
            // db parameters
            String url = "jdbc:sqlite:" + path;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            errorCode = CONNECTION_OPEN_FAILED;
            return false;
        }

    }

    @Override
    public boolean disconnect() {
        return true;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        switch (errorCode) {
            case CONNECTION_OPEN_FAILED: return "Connection is not opened!";
            case PRODUCT_LOAD_FAILED: return "Cannot load the product!";
        };
        return "OK";
    }

    public ProductModel loadProduct(int productID) {
        try {
            ProductModel product = new ProductModel();

            String sql = "SELECT ProductId, Name, Price, Quantity FROM Products WHERE ProductId = " + productID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            product.mProductID = rs.getInt("ProductId");
            product.mName = rs.getString("Name");
            product.mPrice = rs.getDouble("Price");
            product.mQuantity = rs.getDouble("Quantity");
            return product;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorCode = PRODUCT_LOAD_FAILED;
            return null;
        }
    }

    public CustomerModel loadCustomer(int id) {
        try {
            CustomerModel c = new CustomerModel();

            String sql = "SELECT * FROM Customers WHERE CustomerID = " + id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            c.mCustomerID = rs.getInt("CustomerID");
            c.mName = rs.getString("Name");
            c.mPhone = rs.getString("Phone");
            c.mAddress = rs.getString("Address");
            return c;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorCode = CUSTOMER_LOAD_FAILED;
            return null;
        }
    }

    @Override
    public boolean saveCustomer(CustomerModel customer) {
        try{
            String sql = "INSERT INTO Customers(CustomerID, Name, Phone, Address) " +
                    "VALUES ( "+customer.mCustomerID+" , '"+customer.mName+"' , '"+customer.mPhone+"', '"+customer.mAddress+"' );";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    public boolean saveProduct(ProductModel product) {
        try{
            String sql = "INSERT INTO Products(ProductID, Name, Price, Quantity) VALUES ( "+product.mProductID+", '"+product.mName+"', "+product.mPrice+" , "+product.mQuantity+");";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean savePurchase(PurchaseModel purchase) {
        try{
            String sql = "INSERT INTO Purchase(PurchaseID, CustomerID, ProductID, Price, Quantity, TaxTotal, CostTotal) VALUES ("
            +purchase.mProductID+", "+purchase.mCustomerID+", "+purchase.mProductID+", "+purchase.mPrice+" , "+purchase.mQuantity+", "+purchase.mTax+", "+purchase.mTotalCost+");";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
