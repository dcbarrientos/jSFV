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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ResourceBundle;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;

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
	private JPanel panel;

	public NuevoArchivo(Principal principal, ResourceBundle resource){
		super(principal, true);
		
		this.principal = principal;
		this.resource = resource;
		
		initComponents();
	}
	
	private void initComponents(){
		setMinimumSize(new Dimension(500, 370));
		setTitle(resource.getString("NuevoArchivo.title"));
		lblChooseFiles = new JLabel(resource.getString("NuevoArchivo.lblChooseFiles"));
		
		scrollPane = new JScrollPane();
		
		btnAccept = new JButton(resource.getString("NuevoArchivo.btnAccept"));
		btnAccept.setIcon(new ImageIcon(NuevoArchivo.class.getResource("/ar/com/dcbarrientos/jsfv/images/Ok-icon.png")));
		btnAccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				isOk = true;
				dispose();
			}
		});
		
		btnCancel = new JButton(resource.getString("NuevoArchivo.btnCancel"));
		btnCancel.setIcon(new ImageIcon(NuevoArchivo.class.getResource("/ar/com/dcbarrientos/jsfv/images/Cancel-icon.png")));
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				isOk = false;
				dispose();
			}
		});
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnAccept)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnCancel))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(20)
							.addComponent(lblChooseFiles, GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)))
					.addContainerGap())
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(6)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblChooseFiles)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCancel)
						.addComponent(btnAccept))
					.addContainerGap())
		);
		
		btnAddFiles = new JButton(resource.getString("NuevoArchivo.btnAddFiles"));
		panel.add(btnAddFiles);
		btnAddFiles.setContentAreaFilled(false);
		btnAddFiles.setBorderPainted(false);
		btnAddFiles.setToolTipText(resource.getString("NuevoArchivo.btnAddFiles.tooltip"));
		btnAddFiles.setIcon(new ImageIcon(NuevoArchivo.class.getResource("/ar/com/dcbarrientos/jsfv/images/File-New-icon.png")));
		
		btnAddFolder = new JButton(resource.getString("NuevoArchivo.btnAddFolder"));
		panel.add(btnAddFolder);
		btnAddFolder.setBorderPainted(false);
		btnAddFolder.setContentAreaFilled(false);
		btnAddFolder.setToolTipText(resource.getString("NuevoArchivo.btnAddFolder.tooltip"));
		btnAddFolder.setIcon(new ImageIcon(NuevoArchivo.class.getResource("/ar/com/dcbarrientos/jsfv/images/Folder-Add-icon.png")));
		
		btnDeleteFile = new JButton(resource.getString("NuevoArchivo.btnDeleteFile"));
		panel.add(btnDeleteFile);
		btnDeleteFile.setBorderPainted(false);
		btnDeleteFile.setContentAreaFilled(false);
		btnDeleteFile.setIcon(new ImageIcon(NuevoArchivo.class.getResource("/ar/com/dcbarrientos/jsfv/images/File-Delete-icon.png")));
		
		btnEmptyList = new JButton(resource.getString("NuevoArchivo.btnEmptyList"));
		panel.add(btnEmptyList);
		btnEmptyList.setBorderPainted(false);
		btnEmptyList.setContentAreaFilled(false);
		btnEmptyList.setIcon(new ImageIcon(NuevoArchivo.class.getResource("/ar/com/dcbarrientos/jsfv/images/trash-empty-icon.png")));
		btnEmptyList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				emptyList();
			}
		});
		btnDeleteFile.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				deleteFile();
			}
		});
		btnAddFolder.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				loadFolder();
			}
		});
		btnAddFiles.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				loadFiles();
			}
		});
		
		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		FontMetrics fontMetrics = table.getFontMetrics(table.getFont());
		
		tableModel = new NuevoArchivoTableModel(fontMetrics);
		String[] headers = {resource.getString("NuevoArchivo.header1"), resource.getString("NuevoArchivo.header2"), resource.getString("NuevoArchivo.header3")};
		tableModel.setColumnIdentifiers(headers);

		table.setModel(tableModel);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		table.getColumnModel().getColumn(NuevoArchivoTableModel.COLUMN_SIZE).setCellRenderer(rightRenderer);

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
			setTableColumnSize(tableModel.getColumnsSize());
			
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
			setTableColumnSize(tableModel.getColumnsSize());
		}
	}
	
	public void deleteFile(){
		int[] filas = table.getSelectedRows();
		for(int i = 0; i < filas.length; i++){
			tableModel.removeAt(filas[i] - i);
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
	
	private void setTableColumnSize(int[] sizes){
		for(int i = 0; i < sizes.length; i++)
			table.getColumnModel().getColumn(i).setPreferredWidth(sizes[i] + Constantes.COLUMN_CONSTANT);
	}

}
