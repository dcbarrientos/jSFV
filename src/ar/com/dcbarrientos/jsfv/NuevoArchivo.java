/*
 *  Copyright (C) 2016 Diego Barrientos <dc_barrientos@yahoo.com.ar>
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/** 
 * NuevoArchivo.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 18 de ago. de 2016, 5:00:06 p. m. 
 */

package ar.com.dcbarrientos.jsfv;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ResourceBundle;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;

import ar.com.dcbarrientos.jsfv.tables.NuevoArchivoTableModel;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class NuevoArchivo extends JDialog{
	private static final long serialVersionUID = 1L;
	
	private JLabel lblChooseFiles;
	private JScrollPane scrollPane;
	private JButton btnAddFiles;
	private JButton btnAddFolder;
	private JTable table;
	private JButton btnAccept;
	private JButton btnCancel;

	private Principal principal;
	private ResourceBundle resource;
	private NuevoArchivoTableModel tableModel;
	private File[] archivos;
	private boolean isOk;
	private JButton btnDeleteFile;
	private JButton btnEmptyList;
	private JLabel lblMethod;
	private JComboBox<String> cbListMethods;

	private String[] listMethods = {Constantes.NAME_SFV, Constantes.NAME_MD5, Constantes.NAME_SHA1,
									Constantes.NAME_SHA256, Constantes.NAME_SHA384, Constantes.NAME_SHA512};
	
	public NuevoArchivo(Principal principal, ResourceBundle resource){
		super(principal, true);
		
		this.principal = principal;
		this.resource = resource;
		
		initComponents();
	}
	
	private void initComponents(){
		setMinimumSize(new Dimension(410, 370));
		setTitle(resource.getString("NuevoArchivo.title"));
		lblChooseFiles = new JLabel(resource.getString("NuevoArchivo.lblChooseFiles"));
		
		scrollPane = new JScrollPane();
		
		btnAddFiles = new JButton("");
		btnAddFiles.setToolTipText(resource.getString("NuevoArchivo.btnAddFiles.tooltip"));
		btnAddFiles.setIcon(new ImageIcon(NuevoArchivo.class.getResource("/ar/com/dcbarrientos/jsfv/images/File-New-icon.png")));
		btnAddFiles.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				loadFiles();
			}
		});
		
		btnAddFolder = new JButton("");
		btnAddFolder.setToolTipText(resource.getString("NuevoArchivo.btnAddFolder.tooltip"));
		btnAddFolder.setIcon(new ImageIcon(NuevoArchivo.class.getResource("/ar/com/dcbarrientos/jsfv/images/Folder-Add-icon.png")));
		btnAddFolder.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				loadFolder();
			}
		});
		
		btnAccept = new JButton(resource.getString("NuevoArchivo.btnAccept"));
		btnAccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				isOk = true;
				dispose();
			}
		});
		
		btnCancel = new JButton(resource.getString("NuevoArchivo.btnCancel"));
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				isOk = false;
				dispose();
			}
		});
		
		btnDeleteFile = new JButton("");
		btnDeleteFile.setIcon(new ImageIcon(NuevoArchivo.class.getResource("/ar/com/dcbarrientos/jsfv/images/File-Delete-icon.png")));
		btnDeleteFile.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				deleteFile();
			}
		});
		
		btnEmptyList = new JButton("");
		btnEmptyList.setIcon(new ImageIcon(NuevoArchivo.class.getResource("/ar/com/dcbarrientos/jsfv/images/trash-empty-icon.png")));
		btnEmptyList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				emptyList();
			}
		});
		
		lblMethod = new JLabel(resource.getString("NuevoArchivo.lblMethod"));
		
		cbListMethods = new JComboBox<String>(listMethods);
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblChooseFiles, GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(btnDeleteFile)
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(btnAddFolder)
												.addComponent(btnAddFiles))
											.addComponent(btnEmptyList))))))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(btnAccept)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnCancel))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblMethod)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbListMethods, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblChooseFiles)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnAddFiles)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAddFolder)
							.addGap(18)
							.addComponent(btnDeleteFile)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnEmptyList))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMethod)
						.addComponent(cbListMethods, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCancel)
						.addComponent(btnAccept))
					.addContainerGap())
		);
		
		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableModel = new NuevoArchivoTableModel();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		String[] headers = {resource.getString("NuevoArchivo.header1"), resource.getString("NuevoArchivo.header2"), resource.getString("NuevoArchivo.header3")};
		tableModel.setColumnIdentifiers(headers);

		table.setModel(tableModel);
		
		scrollPane.setViewportView(table);
		getContentPane().setLayout(groupLayout);
		pack();
		setLocationRelativeTo(null);
	}
	
	private void loadFiles(){
		JFileChooser fc = new JFileChooser();
		fc.setMultiSelectionEnabled(true);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		if(fc.showOpenDialog(principal) == JFileChooser.APPROVE_OPTION){
			archivos = fc.getSelectedFiles();
			for(File elemento: archivos){
				tableModel.addRow(elemento);
			}
		}
	}
		
	private void loadFolder(){
		JFileChooser fc = new JFileChooser();
		fc.setMultiSelectionEnabled(false);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		if(fc.showOpenDialog(principal) == JFileChooser.APPROVE_OPTION){
			File folder = fc.getSelectedFile();
			File[] archivos = folder.listFiles();
			for(File elemento: archivos){
				if(elemento.isFile())
					tableModel.addRow(elemento);
			}
		}
	}
	
	public void deleteFile(){
		if(table.getSelectedRow() >= 0){
			tableModel.removeAt(table.getSelectedRow());
		}
	}
	
	public void emptyList(){
		tableModel.removeAll();
	}
	
	public File[] getDatos(){
		return tableModel.getDatos();
	}
	
	public boolean showDialog(){
		setVisible(true);
		
		return isOk;
	}
	
	public int getMetodo(){
		return cbListMethods.getSelectedIndex();
	}
}
