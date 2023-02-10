package com.nist.billingsystem;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Authenticator;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Inventory extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldProductName;
	private JTextField textFieldStock;
	private JTextField textFieldPrice;
	private JTextField textFieldCategory;
	private JTable productTable;
	private JTextField textFieldSearch;

	Product productops = new ProductImpl();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inventory frame = new Inventory();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Inventory() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 637, 539);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblHeader = new JLabel("Inventory");
		lblHeader.setVerticalAlignment(SwingConstants.TOP);
		lblHeader.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblHeader.setBounds(260, 10, 83, 23);
		contentPane.add(lblHeader);
		
		JLabel lblProductId = new JLabel("Product ID: ");
		lblProductId.setVerticalAlignment(SwingConstants.TOP);
		lblProductId.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblProductId.setBounds(10, 40, 83, 23);
		contentPane.add(lblProductId);
		
		JLabel lblProductName = new JLabel("Product Name: ");
		lblProductName.setVerticalAlignment(SwingConstants.TOP);
		lblProductName.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblProductName.setBounds(10, 75, 111, 23);
		contentPane.add(lblProductName);
		
		textFieldProductName = new JTextField();
		textFieldProductName.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		textFieldProductName.setColumns(10);
		textFieldProductName.setBounds(122, 73, 164, 23);
		contentPane.add(textFieldProductName);
		
		JLabel productID = new JLabel("");
		productID.setVerticalAlignment(SwingConstants.TOP);
		productID.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		productID.setBounds(95, 40, 83, 23);
		contentPane.add(productID);
		
		JLabel lblCategory = new JLabel("Stock: ");
		lblCategory.setVerticalAlignment(SwingConstants.TOP);
		lblCategory.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblCategory.setBounds(400, 42, 47, 23);
		contentPane.add(lblCategory);
		
		textFieldStock = new JTextField();
		textFieldStock.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		textFieldStock.setColumns(10);
		textFieldStock.setBounds(449, 40, 164, 23);
		contentPane.add(textFieldStock);
		
		JLabel lblPrice = new JLabel("Price(per Piece): ");
		lblPrice.setVerticalAlignment(SwingConstants.TOP);
		lblPrice.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblPrice.setBounds(328, 75, 119, 23);
		contentPane.add(lblPrice);
		
		textFieldPrice = new JTextField();
		textFieldPrice.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		textFieldPrice.setColumns(10);
		textFieldPrice.setBounds(449, 73, 164, 23);
		contentPane.add(textFieldPrice);
		
		JLabel lblCategory_1 = new JLabel("Category: ");
		lblCategory_1.setVerticalAlignment(SwingConstants.TOP);
		lblCategory_1.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblCategory_1.setBounds(10, 110, 83, 23);
		contentPane.add(lblCategory_1);
		
		textFieldCategory = new JTextField();
		textFieldCategory.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		textFieldCategory.setColumns(10);
		textFieldCategory.setBounds(122, 108, 164, 23);
		contentPane.add(textFieldCategory);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = productID.getText().toString();
				String productName = textFieldProductName.getText();
				String stock = textFieldStock.getText();
				String price = textFieldPrice.getText();
				String category = textFieldCategory.getText();
				if(!id.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Item already exists");
				}
				else if(productName.isEmpty() || stock.isEmpty() || price.isEmpty() || category.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Missing Information");
				}
				else {
					
					
					Product productops = new ProductImpl();
					InventoryDAO inventoryDAO = new InventoryDAO();
					inventoryDAO.setProductName(productName);
					inventoryDAO.setCountInStock(Integer.parseInt(stock));
					inventoryDAO.setProductPrice(Integer.parseInt(price));
					inventoryDAO.setProductCategory(category);
					productops.addProduct(inventoryDAO);
					loadTableData();
				}
				
				
			}
		});
		btnAdd.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnAdd.setBounds(41, 154, 85, 21);
		contentPane.add(btnAdd);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = productID.getText().toString();
				String name = textFieldProductName.getText().toString();
				String stock = textFieldStock.getText().toString();
				String price = textFieldPrice.getText().toString();
				String category = textFieldCategory.getText().toString();
				if(name.isEmpty() || stock.isEmpty() || price.isEmpty() || category.isEmpty()){
					JOptionPane.showMessageDialog(null, "Insufficient Data");
				}else if(id == null || id.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Targeted Data for edit doesn't exist");
				}else {
					Product productops = new ProductImpl();
					InventoryDAO product = new InventoryDAO();
					product.setProductId(Integer.parseInt(id));
					product.setProductName(name);
					product.setCountInStock(Integer.parseInt(stock));
					product.setProductPrice(Integer.parseInt(price));
					product.setProductCategory(category);
					productops.editProduct(product);
					JOptionPane.showMessageDialog(btnEdit, "Data Successfully Edited");
					
				}
				loadTableData();
				
			}
		});
		btnEdit.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnEdit.setBounds(161, 154, 85, 21);
		contentPane.add(btnEdit);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();
				int row = productTable.getSelectedRow();
				Object id = tableModel.getValueAt(row, 0);
				int status = JOptionPane.showConfirmDialog(btnDelete, "Click Yes to Confirm", "Do you want to delete this Data?", JOptionPane.YES_NO_OPTION);
				if(status == 0) {
					productops.deleteProduct(Integer.parseInt(id.toString()));
					JOptionPane.showMessageDialog(btnDelete, "Item Deleted");
					loadTableData();
				}
				else {
					loadTableData();
				}
			}
		});
		btnDelete.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnDelete.setBounds(353, 154, 85, 21);
		contentPane.add(btnDelete);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldProductName.setText("");
				textFieldCategory.setText("");
				textFieldStock.setText("");
				textFieldPrice.setText("");
				productID.setText("");
			}
		});
		btnClear.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnClear.setBounds(473, 154, 85, 21);
		contentPane.add(btnClear);
		
		JLabel lblProductList = new JLabel("Product List");
		lblProductList.setVerticalAlignment(SwingConstants.TOP);
		lblProductList.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblProductList.setBounds(260, 196, 130, 23);
		contentPane.add(lblProductList);
		
		productTable = new JTable();
		productTable.setBounds(10, 230, 603, 177);
		String[] columnName = {"ID","Product Name", "In Stock", "Price per Piece", "Category"};
		DefaultTableModel tableModel = new DefaultTableModel(columnName,0);
		productTable.setModel(tableModel);
		loadTableData();
		JScrollPane scrollPane = new JScrollPane(productTable);
		productTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = productTable.getSelectedRow();
				productID.setText(tableModel.getValueAt(row, 0).toString());
				textFieldProductName.setText(tableModel.getValueAt(row, 1).toString());
				textFieldStock.setText(tableModel.getValueAt(row, 2).toString());
				textFieldPrice.setText(tableModel.getValueAt(row, 3).toString());
				textFieldCategory.setText(tableModel.getValueAt(row, 4).toString());
			}
		});
		
		scrollPane.setBounds(10, 260, 603, 178);
		contentPane.add(scrollPane);
		
		JButton btnDone = new JButton("Done");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Billing().setVisible(true);;
			}
		});
		btnDone.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnDone.setBounds(258, 471, 85, 21);
		contentPane.add(btnDone);
		
		JLabel lblSearch = new JLabel("Search");
		lblSearch.setVerticalAlignment(SwingConstants.TOP);
		lblSearch.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblSearch.setBounds(412, 196, 57, 23);
		contentPane.add(lblSearch);
		
		textFieldSearch = new JTextField();
		textFieldSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				productTable.clearSelection();
				String searchText = textFieldSearch.getText().toString();
				if(searchText == null || searchText.isEmpty()) {
					loadTableData();
				}else {
					searchData(searchText);
				}
			}
		});
		textFieldSearch.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		textFieldSearch.setColumns(10);
		textFieldSearch.setBounds(467, 196, 146, 23);
		contentPane.add(textFieldSearch);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadTableData();
			}
		});
		btnRefresh.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnRefresh.setBounds(10, 229, 85, 21);
		contentPane.add(btnRefresh);
	}
	public void loadTableData() {
		List<InventoryDAO> productList = productops.getProducts();
		DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();
		tableModel.setRowCount(0);
		for(InventoryDAO p: productList) {
			tableModel.addRow(new Object[] {
					p.getProductId(), p.getProductName(), p.getCountInStock(), p.getProductPrice(), p.getProductCategory()
			});
		}
	}
	public void searchData(String productName) {
		List<InventoryDAO> productList = productops.searchProduct(productName);
		DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();
		tableModel.setRowCount(0);
		for(InventoryDAO p: productList) {
			tableModel.addRow(new Object[] {
					p.getProductId(), p.getProductName(), p.getCountInStock(), p.getProductPrice(), p.getProductCategory()
			});
		}
	}
}
