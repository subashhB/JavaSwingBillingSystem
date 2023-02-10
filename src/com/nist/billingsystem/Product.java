package com.nist.billingsystem;

import java.util.List;

public interface Product {
	public void addProduct(InventoryDAO inventoryDAO);
	public void editProduct(InventoryDAO inventoryDAO);
	public void deleteProduct(int id);
	public List<InventoryDAO> searchProduct(String name);
	public List<InventoryDAO> getProducts();
}
