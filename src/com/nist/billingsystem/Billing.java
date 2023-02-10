package com.nist.billingsystem;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Billing extends JFrame {

	private JPanel contentPane;
	private JTable productTable;
	private JTextField textFieldSearch;
	private JTextField textFieldProductName;
	private JTextField textFieldQuantity;
	
	
	Product productops = new ProductImpl();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Billing frame = new Billing();
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
	public Billing() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 637, 539);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("System");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Add items");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Inventory().setVisible(true);
			}
		});
		mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		mnNewMenu.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		productTable = new JTable();
		productTable.setBounds(10, 98, 603, 137);
		String[] columnName = {"ID","Product Name", "In Stock", "Price per Piece", "Category"};
		DefaultTableModel tableModel = new DefaultTableModel(columnName,0);
		productTable.setModel(tableModel);
		loadTableData();
		JScrollPane scrollPane = new JScrollPane(productTable);
		productTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = productTable.getSelectedRow();
				textFieldProductName.setText(tableModel.getValueAt(row, 1).toString());
			}
		});
		
		scrollPane.setBounds(10, 98, 603, 137);
		contentPane.add(scrollPane);
		
		JLabel lblTitle = new JLabel("BILLING");
		lblTitle.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblTitle.setBounds(294, 10, 66, 23);
		contentPane.add(lblTitle);
		
		JLabel lblSearch = new JLabel("Search");
		lblSearch.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblSearch.setBounds(408, 72, 56, 13);
		contentPane.add(lblSearch);
		
		textFieldSearch = new JTextField();
		textFieldSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String searchText = textFieldSearch.getText().toString();
				if(searchText == null || searchText.isEmpty()) {
					loadTableData();
				}
				else {
					searchData(searchText);
				}
			}
		});
		textFieldSearch.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		textFieldSearch.setBounds(474, 69, 139, 19);
		contentPane.add(textFieldSearch);
		textFieldSearch.setColumns(10);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblName.setBounds(10, 269, 56, 13);
		contentPane.add(lblName);
		
		textFieldProductName = new JTextField();
		textFieldProductName.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		textFieldProductName.setColumns(10);
		textFieldProductName.setBounds(76, 266, 139, 19);
		contentPane.add(textFieldProductName);
		
		JLabel lblQuantity = new JLabel("Quantity:");
		lblQuantity.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblQuantity.setBounds(10, 303, 66, 16);
		contentPane.add(lblQuantity);
		
		textFieldQuantity = new JTextField();
		textFieldQuantity.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		textFieldQuantity.setColumns(10);
		textFieldQuantity.setBounds(76, 302, 139, 19);
		contentPane.add(textFieldQuantity);
		
		JLabel lblProductsList = new JLabel("PRODUCTS LIST");
		lblProductsList.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblProductsList.setBounds(272, 49, 112, 23);
		contentPane.add(lblProductsList);
		
		JLabel lblGrandTotal = new JLabel("");
		lblGrandTotal.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblGrandTotal.setBounds(349, 434, 264, 23);
		contentPane.add(lblGrandTotal);
		
		JTextArea Bill = new JTextArea();
		Bill.setBounds(339, 245, 274, 179);
		contentPane.add(Bill);
		
		JButton btnAddToBill = new JButton("Add to Bill");
		btnAddToBill.addActionListener(new ActionListener() {
			int i=0;
			int grandTotal=0;
			public void actionPerformed(ActionEvent e) {
				int row = productTable.getSelectedRow();
				String productName = textFieldProductName.getText().toString();
				String qntity = textFieldQuantity.getText().toString();
				int AvailQty = Integer.parseInt(productTable.getValueAt(row, 2).toString());
				int quantity = Integer.parseInt(qntity);
				if(productName.trim().isEmpty() || qntity.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Insufficient Information");
				}else if(AvailQty <= quantity) {
					JOptionPane.showMessageDialog(null, "Not Enough in Stock");
				}
				else {
					i++;
					int price = Integer.parseInt(productTable.getValueAt(row, 3).toString());
					int total = quantity*price;
					grandTotal += total;
					
					if(i==1) {
						Bill.setText(Bill.getText()+"      ==========Bill==========\n"+"NUM  PRODUCT  PRICE  QUANTITY  TOTAL\n"+i+"        "+productName+"      "+price+"           "+quantity+"              "+total);
					}
					else {
						
						Bill.setText(Bill.getText()+"\n"+i+"        "+productName+"      "+price+"           "+quantity+"              "+total);
					}
					updateAfterSales(quantity);
					lblGrandTotal.setText("Total:"+ grandTotal);
					productTable.clearSelection();
				}
				
			}
		});
		btnAddToBill.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnAddToBill.setBounds(10, 361, 123, 21);
		contentPane.add(btnAddToBill);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldProductName.setText("");
				textFieldQuantity.setText("");
			}
		});
		btnClear.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnClear.setBounds(143, 361, 103, 21);
		contentPane.add(btnClear);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				productTable.removeAll();
				loadTableData();
			}
		});
		btnRefresh.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnRefresh.setBounds(10, 67, 103, 21);
		contentPane.add(btnRefresh);
		
		
	}
	public void loadTableData() {
		productTable.clearSelection();
		List<InventoryDAO> productList = productops.getProducts();
		DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();
		tableModel.setRowCount(0);
		for(InventoryDAO p:productList) {
			tableModel.addRow(new Object[] {
				p.getProductId(),p.getProductName(),p.getCountInStock(),p.getProductPrice(),p.getProductCategory()	
			});
		}
	}
	public void updateAfterSales(int soldQuantity) {
		Product productops = new ProductImpl();
		InventoryDAO product = new InventoryDAO();
		int row = productTable.getSelectedRow();
		int id = Integer.parseInt(productTable.getValueAt(row, 0).toString());
		String productName = productTable.getValueAt(row, 1).toString();
		int stock = Integer.parseInt(productTable.getValueAt(row,2).toString());
		stock= stock-soldQuantity;
		int price = Integer.parseInt(productTable.getValueAt(row, 3).toString());
		String category = productTable.getValueAt(row, 4).toString();
		product.setProductId(id);
		product.setProductName(productName);
		product.setCountInStock(stock);
		product.setProductPrice(price);
		product.setProductPrice(price);
		product.setProductCategory(category);		
		productops.editProduct(product);
		loadTableData();
	}
	
	public void searchData(String name) {
		productTable.clearSelection();
		List<InventoryDAO> productList = productops.searchProduct(name);
		DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();
		tableModel.setRowCount(0);
		for(InventoryDAO p:productList) {
			tableModel.addRow(new Object[] {
				p.getProductId(),p.getProductName(),p.getCountInStock(),p.getProductPrice(),p.getProductCategory()	
			});
		}
	}
}
