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
 * VerifyFile.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 24 de ago. de 2016, 4:02:16 p. m. 
 */

package ar.com.dcbarrientos.jsfv;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import ar.com.dcbarrientos.jsfv.methods.CRCGenerator;
import ar.com.dcbarrientos.jsfv.methods.MD5Generator;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Color;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class VerifyFile extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel lblSelectFile;
	private JTextField txtInput;
	private JButton btnBrowse;
	private JLabel lblChecksum;
	private JTextField txtChecksum;
	private JButton btnPaste;
	private JButton btnVerify;
	private JLabel lblMethod;
	private JComboBox<String> cbMethod;
	
	ResourceBundle resource;
	ChecksumGenerator checksum;
	
	private String[] listMethods = Constantes.getMethodsByName();
	private JLabel lblNewChecksum;
	//private JLabel lblNewChecksumResult;
	private JTextField txtChecksumResutl;
	
	private JButton btnCopy;
	private JProgressBar progressBar;
	
	public VerifyFile(ResourceBundle resource){
		this.resource = resource;
		
		initComponents();
	}
	
	private void initComponents(){
		
		lblSelectFile = new JLabel(resource.getString("VerifyFile.lblSelectFile"));
		
		txtInput = new JTextField();
		txtInput.setColumns(10);
		
		btnBrowse = new JButton(resource.getString("VerifyFile.btnBrowse"));
		btnBrowse.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				browse();
			}
		});
		
		lblChecksum = new JLabel(resource.getString("VerifyFile.lblChecksum"));
		
		txtChecksum = new JTextField();
		txtChecksum.setColumns(10);
		
		btnPaste = new JButton(resource.getString("VerifyFile.btnPaste"));
		btnPaste.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				paste();
			}
		});
		
		btnVerify = new JButton(resource.getString("VerifyFile.btnVerify"));
		btnVerify.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				verificar();
			}
		});
		
		lblMethod = new JLabel(resource.getString("VerifyFile.lblMethod"));
		
		cbMethod = new JComboBox<String>(listMethods);
		
		lblNewChecksum = new JLabel(resource.getString("VerifyFile.lblNewChecksum"));
		
		txtChecksumResutl = new JTextField("");
		txtChecksumResutl.setEditable(false);
		
		btnCopy = new JButton(resource.getString("VerifyFile.btnCopy"));
		btnCopy.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				copiar();
			}
		});
		
		progressBar = new JProgressBar();
		progressBar.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(progressBar.getValue() == 100 && checksum.isDone()){
					txtChecksumResutl.setText(checksum.getChecksum(0));
					btnVerify.setText(resource.getString("VerifyFile.btnVerify"));
					if(txtChecksum.getText().length() > 0 ){
						if(txtChecksumResutl.getText().equalsIgnoreCase(txtChecksum.getText())){
							txtChecksumResutl.setForeground(Color.GREEN);
							JOptionPane.showMessageDialog(null, resource.getString("VerifyFile.verifyOK"), resource.getString("VerifyFile.title"), JOptionPane.INFORMATION_MESSAGE);
						}else{
							txtChecksumResutl.setForeground(Color.RED);
							JOptionPane.showMessageDialog(null, resource.getString("VerifyFile.verifyError"), resource.getString("VerifyFile.title"), JOptionPane.ERROR_MESSAGE);
						}
					}else{
						txtChecksumResutl.setForeground(Color.BLACK);
					}
				}
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSelectFile, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
						.addComponent(lblChecksum, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(txtInput, GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnBrowse, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(txtChecksumResutl, GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblMethod)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cbMethod, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE))
								.addComponent(txtChecksum, GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnPaste, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnVerify, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnCopy, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)))
						.addComponent(progressBar, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
						.addComponent(lblNewChecksum))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblSelectFile)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBrowse))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblChecksum)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtChecksum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnPaste))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMethod)
						.addComponent(cbMethod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewChecksum)
					.addGap(1)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtChecksumResutl)
						.addComponent(btnCopy))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnVerify)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(73, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}
	
	private void browse(){
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
			txtInput.setText(fc.getSelectedFile().getPath());
		}
	}
	
	private void paste(){
		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable t = cb.getContents(this);
		if(t != null){			
			try {
				txtChecksum.setText((String)t.getTransferData(DataFlavor.stringFlavor));
				cbMethod.setSelectedIndex(Constantes.getMethodIndexByLength(txtChecksum.getText().length()));
			} catch (UnsupportedFlavorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void verificar(){
		if(btnVerify.getText().equals(resource.getString("VerifyFile.btnVerify"))){
			txtChecksumResutl.setText("");
			btnVerify.setText(resource.getString("VerifyFile.btnCancel"));
			if(cbMethod.getSelectedIndex() == Constantes.METHOD_SFV)
				checksum = new CRCGenerator(progressBar);
			else{
				checksum = new MD5Generator(progressBar);
				checksum.setMetodo((String)Constantes.METHODS[cbMethod.getSelectedIndex()][Constantes.METHOD_NAME]);
			}
			
			if(checksum != null){
				checksum.setFileList(new File(txtInput.getText()));
				checksum.execute();
			}
		}else{
			btnVerify.setText(resource.getString("VerifyFile.btnVerify"));
			checksum.cancelar();
		}
		
	}
	
	private void copiar(){
		if(txtChecksumResutl.getText().length() > 0){
			Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
			StringSelection ss = new StringSelection(txtChecksumResutl.getText());
			cb.setContents(ss, ss);
		}
	}
	
	public void setInputFile(File file){
		txtInput.setText(file.getPath());
	}
	
	public void setMethodIndex(int index){
		cbMethod.setSelectedIndex(index);
	}
}
