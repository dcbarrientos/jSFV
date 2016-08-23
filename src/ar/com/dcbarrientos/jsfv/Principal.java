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
 * Principal.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 17 de ago. de 2016, 1:22:30 p. m. 
 */

package ar.com.dcbarrientos.jsfv;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;

import ar.com.dcbarrientos.jsfv.methods.CRCGenerator;
import ar.com.dcbarrientos.jsfv.methods.MD5Generator;
import ar.com.dcbarrientos.jsfv.tables.ArchivosTableModel;
import ar.com.dcbarrientos.jsfv.tables.ArchivosTableRenderer;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class Principal extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public static final String APP_NAME = "JSFV: Java Simple File Verification";
	public static final String VERSION = "1.0.1"; 
	
	private JSplitPane splitPane;
	private JPanel southPanel;
	private JPanel northPanel;
	private JLabel lblFilePath;
	private JTextField txtSfvPath;
	private JButton btnBrowse;
	private JScrollPane scrollPane;
	private JTable tableArchivos;
	private JScrollPane scrollPane_1;
	private JTextArea textFileDescriptor;
	private JPanel progressPanel;
	private JButton btnStart;
	private JButton btnCancel;
	private JLabel lblCurrentFile;
	private JProgressBar progressFile;
	private JLabel lblTotalProgress;
	private JProgressBar progressTotal;
	private JMenuItem menuFileNewSfv;
	private JMenuItem menuFileOpen;
	private JSeparator separator;
	private JMenuItem menuFileExit;

	private int accion; 		//Esta variable determina si se genera un archivo nuevo o si se verifica uno ya creado.
	private int metodo; 		//Determina el método a utilizar.
	private boolean cargando;	//True cuando se esta cargando la lista de archivos.
	private ChecksumGenerator checksum = null;
	private ResourceBundle resource;
	
	private ArchivosTableModel archivosTableModel; 
	private JSeparator separator_1;
	private JMenu menuHelp;
	private JMenuItem menuHelpAbout;
	private JMenuItem menuFileSaveAs;
	private JLabel lblFileProgress;
	private JLabel lblFileProcessed;
	private JButton btnSave;
	private JButton btnNew;
	
	public Principal(ResourceBundle resource){
		super();
		this.resource = resource;
		
		initComponents();
	}
	
	private void initComponents(){
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/ar/com/dcbarrientos/jsfv/images/logo16x16.png")));		
		setMinimumSize(new Dimension(630, 460));
		setTitle(getVersion());
		
		splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		tableArchivos = new JTable();
		tableArchivos.setShowVerticalLines(false);
		tableArchivos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableArchivos.setDefaultRenderer(JLabel.class, new ArchivosTableRenderer());
		
		archivosTableModel = new ArchivosTableModel();
		String[] headers = {resource.getString("Principal.archivosTableModel.header1"), 
							resource.getString("Principal.archivosTableModel.header2"), 
							resource.getString("Principal.archivosTableModel.header3"), 
							resource.getString("Principal.archivosTableModel.header4")};
		
		archivosTableModel.setFontMetrics(tableArchivos.getFontMetrics(tableArchivos.getFont()));		
		archivosTableModel.setHeaders(headers);
		
		archivosTableModel.addTableModelListener(new TableModelListener() {			
			@Override
			public void tableChanged(TableModelEvent e) {	
				if(!cargando && archivosTableModel.getRowCount() > 0){
					if(accion == Constantes.ACCION_VERIFICAR){
						String strChecksum = (String)archivosTableModel.getValueAt(checksum.getCurrentRow() , Constantes.COLUMN_CHECKSUM);
						if(strChecksum.length() > 0){
							String strSaved = (String)archivosTableModel.getValueAt(checksum.getCurrentRow() , Constantes.COLUMN_SAVED_CHECKSUM);
							
							if(strChecksum.equals(Constantes.SFV_FILE_NOT_FOUND)){
								archivosTableModel.setRowIcon(checksum.getCurrentRow(), Constantes.ICON_FILE_NOT_FOUND);
							}else if(strChecksum.equals(strSaved)){
								archivosTableModel.setRowIcon(checksum.getCurrentRow(), Constantes.ICON_OK);
							}else{
								archivosTableModel.setRowIcon(checksum.getCurrentRow(), Constantes.ICON_ERROR);
							}
						}
					}else if(accion == Constantes.ACCION_NUEVO){
						String strChecksum = (String)archivosTableModel.getValueAt(checksum.getCurrentRow() , Constantes.COLUMN_CHECKSUM);
						if(strChecksum.equals(Constantes.SFV_FILE_NOT_FOUND)){
							archivosTableModel.setRowIcon(checksum.getCurrentRow(), Constantes.ICON_FILE_NOT_FOUND);
						}else{
							archivosTableModel.setRowIcon(checksum.getCurrentRow(), Constantes.ICON_OK);
						}
					}
					lblFileProcessed.setText((checksum.getCurrentRow() +1) + "/" + archivosTableModel.getRowCount() + " files.");
				}
			}
			
		});
		tableArchivos.setModel(archivosTableModel);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		tableArchivos.getColumnModel().getColumn(Constantes.COLUMN_SIZE).setCellRenderer(rightRenderer);
		tableArchivos.getColumnModel().getColumn(Constantes.COLUMN_CHECKSUM).setCellRenderer(rightRenderer);
		tableArchivos.getColumnModel().getColumn(Constantes.COLUMN_SAVED_CHECKSUM).setCellRenderer(rightRenderer);
		
		scrollPane.setViewportView(tableArchivos);
		
		scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);
		
		textFileDescriptor = new JTextArea();
		scrollPane_1.setViewportView(textFileDescriptor);
		
		southPanel = new JPanel();
		southPanel.setSize(new Dimension(0, 150));
		southPanel.setMinimumSize(new Dimension(10, 150));
		getContentPane().add(southPanel, BorderLayout.SOUTH);
		
		progressPanel = new JPanel();
		progressPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		btnStart = new JButton(resource.getString("Principal.btnStart"));
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				procesar();
			}
		});
		btnStart.setMaximumSize(new Dimension(63, 23));
		btnStart.setMinimumSize(new Dimension(63, 23));
		
		btnCancel = new JButton(resource.getString("Principal.btnCancel"));
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cancelar();
			}
		});
		GroupLayout gl_southPanel = new GroupLayout(southPanel);
		gl_southPanel.setHorizontalGroup(
			gl_southPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_southPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_southPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_southPanel.createSequentialGroup()
							.addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE))
						.addComponent(progressPanel, GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_southPanel.setVerticalGroup(
			gl_southPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_southPanel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(progressPanel, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_southPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCancel)
						.addComponent(btnStart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		lblCurrentFile = new JLabel(resource.getString("Principal.lblCurrentFile"));
		
		progressFile = new JProgressBar();
		progressFile.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(checksum != null){
					lblFileProgress.setText(progressFile.getValue() + "%");
					progressTotal.setValue(new Long(checksum.getBytesAcumulados() * 100 / archivosTableModel.getTotalBytesToRead()).intValue());
					if(checksum.isDone() && !checksum.isCancelado()){
						progressFile.setValue(100);
						progressTotal.setValue(100);
					}

				}
			}
		});
		
		lblTotalProgress = new JLabel(resource.getString("Principal.lblTotalProgress"));
		
		progressTotal = new JProgressBar();
		
		lblFileProgress = new JLabel("");
		
		lblFileProcessed = new JLabel("");
		GroupLayout gl_progressPanel = new GroupLayout(progressPanel);
		gl_progressPanel.setHorizontalGroup(
			gl_progressPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_progressPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_progressPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(progressFile, GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
						.addGroup(gl_progressPanel.createSequentialGroup()
							.addComponent(lblCurrentFile)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblFileProgress))
						.addGroup(gl_progressPanel.createSequentialGroup()
							.addComponent(lblTotalProgress)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblFileProcessed))
						.addComponent(progressTotal, GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_progressPanel.setVerticalGroup(
			gl_progressPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_progressPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_progressPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCurrentFile)
						.addComponent(lblFileProgress))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(progressFile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_progressPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTotalProgress)
						.addComponent(lblFileProcessed))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(progressTotal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(20, Short.MAX_VALUE))
		);
		progressPanel.setLayout(gl_progressPanel);
		southPanel.setLayout(gl_southPanel);
		
		
		northPanel = new JPanel();
		FlowLayout fl_northPanel = (FlowLayout) northPanel.getLayout();
		fl_northPanel.setAlignment(FlowLayout.LEFT);
		getContentPane().add(northPanel, BorderLayout.NORTH);
		
		btnNew = new JButton(resource.getString("Principal.btnNew"));
		btnNew.setToolTipText(resource.getString("Principal.btnNew.tooltip"));
		btnNew.setIcon(new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jsfv/images/File-New.png")));
		btnNew.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				nuevoArchivo();
			}
		});
		northPanel.add(btnNew);
		
		lblFilePath = new JLabel(resource.getString("Principal.lblFilePath"));
		northPanel.add(lblFilePath);
		
		txtSfvPath = new JTextField();
		northPanel.add(txtSfvPath);
		txtSfvPath.setColumns(30);
		
		btnBrowse = new JButton(resource.getString("Principal.btnBrowse"));
		btnBrowse.setToolTipText(resource.getString("Principal.btnBrowse.tooltip"));
		btnBrowse.setIcon(new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jsfv/images/Open-File-Icon.png")));
		btnBrowse.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				browseMouseClicked();
			}
		});
		
		btnSave = new JButton(resource.getString("Principal.btnSave"));
		btnSave.setToolTipText(resource.getString("Principal.btnSave.tooltip"));
		btnSave.setIcon(new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jsfv/images/File-Save.png")));
		btnSave.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				saveFile();
			}
		});
		northPanel.add(btnSave);
		northPanel.add(btnBrowse);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(getMenuPrincipal());
		
		pack();		
		setLocationRelativeTo(null);
	}
	
	private void browseMouseClicked(){
		FileNameExtensionFilter filter = new FileNameExtensionFilter("MD5, SFV, SHA-1", "md5", "sfv", "sha1");
		
		JFileChooser fc = new JFileChooser();
		fc.addChoosableFileFilter(filter);
		fc.setFileFilter(filter);
		if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
			txtSfvPath.setText(fc.getSelectedFile().getPath());
			accion = Constantes.ACCION_VERIFICAR;
			metodo = getFileType(fc.getSelectedFile().getPath());
			
			loadSFV();
		}		
	}
	
	private JMenuBar getMenuPrincipal(){
		JMenuBar menuBarPrincipal = new JMenuBar();
		JMenu menuFile = new JMenu(resource.getString("Principal.menuFile"));
		
		menuBarPrincipal.add(menuFile);
		
		menuFileNewSfv = new JMenuItem(resource.getString("Principal.menuFileNewSfv"));
		menuFileNewSfv.setIcon(new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jsfv/images/File-New_16x16.png")));
		menuFileNewSfv.setToolTipText(resource.getString("Principal.btnNew.tooltip"));
		menuFileNewSfv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nuevoArchivo();
			}
		});
		menuFile.add(menuFileNewSfv);
		
		separator_1 = new JSeparator();
		menuFile.add(separator_1);
		
		menuFileOpen = new JMenuItem(resource.getString("Principal.menuFileOpen"));
		menuFileOpen.setIcon(new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jsfv/images/Open-File-Icon_16x16.png")));
		menuFileOpen.setToolTipText(resource.getString("Principal.btnBrowse.tooltip"));
		menuFileOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				browseMouseClicked();
			}
		});
		menuFile.add(menuFileOpen);
		
		menuFileSaveAs = new JMenuItem(resource.getString("Principal.menuFileSaveAs"));
		menuFileSaveAs.setIcon(new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jsfv/images/File-Save_16x16.png")));
		menuFileSaveAs.setToolTipText(resource.getString("Principal.btnSave.tooltip"));
		menuFile.add(menuFileSaveAs);
		
		separator = new JSeparator();
		menuFile.add(separator);
		
		menuFileExit = new JMenuItem(resource.getString("Principal.menuFileExit"));
		menuFileExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				salir();
			}
		});
		menuFile.add(menuFileExit);
		
		menuHelp = new JMenu(resource.getString("Principal.menuHelp"));
		menuBarPrincipal.add(menuHelp);
		
		menuHelpAbout = new JMenuItem(resource.getString("Principal.menuHelpAbout"));
		menuHelpAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAbout();
			}
		});
		menuHelp.add(menuHelpAbout);
		
		return menuBarPrincipal;
	}
	
	/**
	 * Devuelve el metodo a partir de la extensión del archivo.
	 * @param fileName
	 * @return
	 */
	private int getFileType(String fileName){
		if(fileName.substring(fileName.lastIndexOf("."), fileName.length()).toLowerCase().equals(Constantes.EXTENSION_SFV)){
			return Constantes.METHOD_SFV;
		}else if(fileName.substring(fileName.lastIndexOf("."), fileName.length()).toLowerCase().equals(Constantes.EXTENSION_MD5)){
			return Constantes.METHOD_MD5;
		}else if(fileName.substring(fileName.lastIndexOf("."), fileName.length()).toLowerCase().equals(Constantes.EXTENSION_SHA1)){
			return Constantes.METHOD_SHA1;
		}
		
		return ERROR;
	}
	
	private void loadSFV(){
		BufferedReader input = null;
		File file = new File(txtSfvPath.getText());
		String linea;
		
		archivosTableModel.resetDatos();
		cargando = true;
		File tmp;
		
		checksum = null;
		progressFile.setValue(0);
		progressTotal.setValue(0);
		
		if(file.exists()){
			try {
				input = new BufferedReader(new FileReader(file));
				textFileDescriptor.setText("");
				while((linea = input.readLine()) != null){
					textFileDescriptor.append(linea + "\n");
					if(!linea.startsWith(";")){
						String fileName;
						String checksum;
						if(metodo == Constantes.METHOD_SFV){
							fileName = linea.substring(0, linea.lastIndexOf(" ")).trim();
							checksum = linea.substring(linea.lastIndexOf(" "), linea.length()).trim();
						}else{
							fileName = linea.substring(linea.indexOf(" "), linea.length()).trim();
							fileName = fileName.replace("*", "");
							checksum = linea.substring(0, linea.indexOf(" ")).trim();
						}
						fileName = Paths.get(fileName).normalize().toString();
						tmp = new File(file.getParent() + File.separator + fileName);
						
						archivosTableModel.addRow(tmp, checksum, Constantes.ICON_QUESTION);
					}
				}
				
				tableArchivos.revalidate();
				tableArchivos.repaint();
				setTableColumnSize(archivosTableModel.getColumnsSize());
				
				input.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					input.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}else{
			//TODO Mensaje de error: SFV no existe.
		}
		cargando = false;
	}
	
	private void cancelar(){
		if(checksum != null && !checksum.isDone()){
			checksum.cancelar();
		}
	}
	
	private void procesar(){
		if(checksum == null || checksum.isDone()){
			if(metodo==Constantes.METHOD_SFV)
				checksum = new CRCGenerator(progressFile, tableArchivos);
			else{
				checksum = new MD5Generator(progressFile, tableArchivos);
				if(metodo==Constantes.METHOD_MD5)
					checksum.setMetodo(Constantes.NAME_MD5);
				else if(metodo==Constantes.METHOD_SHA1)
					checksum.setMetodo(Constantes.NAME_SHA1);
				
			}
			
			if(checksum != null){
				checksum.setFileList(archivosTableModel.getArchivos());
				checksum.execute();		
			}
		}
	}
	
	private String getVersion(){
		return APP_NAME + " " + VERSION;
	}
	
	private String getSFVFileName(File archivo){
		String resultado = ""; 
		if(archivo.isDirectory())
			resultado += archivo.getPath() + File.separator + archivo.getName();
		else
			resultado += archivo.getParent() + File.separator + archivo.getParentFile().getName();
		
		if(metodo == Constantes.METHOD_SFV)
			resultado += Constantes.EXTENSION_SFV;
		else if(metodo == Constantes.METHOD_MD5)
			resultado += Constantes.EXTENSION_MD5;
		else if(metodo == Constantes.METHOD_SHA1)
			resultado += Constantes.EXTENSION_SHA1;
		
		return resultado;
	}
	
	private void nuevoArchivo(){
		NuevoArchivo nuevoArchivo = new NuevoArchivo(this, resource);
		if(nuevoArchivo.showDialog()){
			resetDatos();
			this.metodo = nuevoArchivo.getMetodo();
			this.accion = Constantes.ACCION_NUEVO;
			cargarNuevaLista(nuevoArchivo.getDatos());
			textFileDescriptor.setText("");
		}		
	}
	
	private void cargarNuevaLista(File[] archivos){
		cargando = true;
		resetDatos();
		for(File archivo: archivos){
			archivosTableModel.addRow(archivo, null, Constantes.ICON_QUESTION);
		}
		lblFileProcessed.setText("0/" + archivosTableModel.getRowCount() + " files.");
		txtSfvPath.setText(getSFVFileName(archivos[0]));
		cargando = false;
		setTableColumnSize(archivosTableModel.getColumnsSize());

	}
	
	private void resetDatos(){
		archivosTableModel.resetDatos();
		progressFile.setValue(0);
		progressTotal.setValue(0);
		lblFileProgress.setText("0%");
		checksum = null;
	}
	
	private void saveFile(){
		File archivo = new File(txtSfvPath.getText());
		String linea = "";
		
		if(checksum == null){
			if(archivosTableModel.getRowCount() > 0){
				JOptionPane.showMessageDialog(null, resource.getString("Principal.message1"), resource.getString("Prinicipal.savedialog.title"), JOptionPane.INFORMATION_MESSAGE);
			}
		}else if(checksum.isDone()){
			if(archivo != null){
				try {
					FileOutputStream writer = new FileOutputStream(archivo);
					writer.write(getComments().getBytes());
					for(int index = 0; index < tableArchivos.getRowCount(); index++){
						linea = "";
						if(metodo == Constantes.METHOD_SFV){
							linea += archivosTableModel.getStringFileName(index) + " " + archivosTableModel.getValueAt(index, Constantes.COLUMN_CHECKSUM);
						}else{
							linea += archivosTableModel.getValueAt(index, Constantes.COLUMN_CHECKSUM) + " " + archivosTableModel.getStringFileName(index);						
						}
						linea += "\n";
						writer.write(linea.getBytes());
					}
					
					writer.close();
					JOptionPane.showMessageDialog(null, resource.getString("Principal.message2"), resource.getString("Prinicipal.savedialog.title"), JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	
	private String getComments(){
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String fecha = format.format(date);
		format = new SimpleDateFormat("hh:mm:ss");
		String hora = format.format(date);
		
		String comments = String.format(resource.getString("Principal.comment"), getVersion(), fecha, hora);
		
		return comments;
	}
	
	private void salir(){
		dispose();
	}
	
	private void showAbout(){
		About about = new About(this, resource);
		about.showDialog();
	}
	
	private void setTableColumnSize(int[] sizes){
		for(int i = 0; i < sizes.length; i++)
			tableArchivos.getColumnModel().getColumn(i).setPreferredWidth(sizes[i] + Constantes.COLUMN_CONSTANT);
	}
}
