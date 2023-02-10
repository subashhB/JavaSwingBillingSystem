package com.nist.billingsystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductImpl implements Product{
	PreparedStatement ps = null;
	@Override
	public void addProduct(InventoryDAO inventoryDAO) {
		String sql = "INSERT INTO products(productName, countInStock, productPrice, productCategory) VALUES(?,?,?,?)";
		try {
			ps = DatabaseConnectivity.getConnection().prepareStatement(sql);
			ps.setString(1, inventoryDAO.getProductName());
			ps.setInt(2, inventoryDAO.getCountInStock());
			ps.setInt(3, inventoryDAO.getProductPrice());
			ps.setString(4, inventoryDAO.getProductCategory());
			ps.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void editProduct(InventoryDAO inventoryDAO) {
		String sql = "UPDATE products SET productName=?, countInStock=?, productPrice=?, productCategory=? WHERE productID =?";
		try {
			ps = DatabaseConnectivity.getConnection().prepareStatement(sql);
			ps.setString(1, inventoryDAO.getProductName());
			ps.setInt(2, inventoryDAO.getCountInStock());
			ps.setInt(3, inventoryDAO.getProductPrice());
			ps.setString(4, inventoryDAO.getProductCategory());
			ps.setInt(5, inventoryDAO.getProductId());
			ps.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteProduct(int id) {
		String sql = "DELETE FROM products WHERE productID = ?";
		try {
			ps = DatabaseConnectivity.getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public List<InventoryDAO> searchProduct(String name) {
		List<InventoryDAO> productList = new ArrayList<>();
		String sql = "SELECT * FROM products WHERE productName like ?";
		try {
			ps = DatabaseConnectivity.getConnection().prepareStatement(sql);
			ps.setString(1, "%"+name+"%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				InventoryDAO product = new InventoryDAO();
				product.setProductId(rs.getInt("productID"));
				product.setProductName(rs.getString("productName"));
				product.setCountInStock(rs.getInt("countInStock"));
				product.setProductPrice(rs.getInt("productPrice"));
				product.setProductCategory(rs.getString("productCategory"));
				productList.add(product);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productList;
		
	}

	@Override
	public List<InventoryDAO> getProducts() {
		List<InventoryDAO> productList = new ArrayList<>();
		String sql = "SELECT * FROM products";
		try {
			ps = DatabaseConnectivity.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				InventoryDAO product = new InventoryDAO();
				product.setProductId(rs.getInt("productID"));
				product.setProductName(rs.getString("productName"));
				product.setCountInStock(rs.getInt("countInStock"));
				product.setProductPrice(rs.getInt("productPrice"));
				product.setProductCategory(rs.getString("productCategory"));
				productList.add(product);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productList;
	}
	

}
