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
 * StringHash.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 24 de ago. de 2016, 11:57:19 a. m. 
 */

package ar.com.dcbarrientos.jsfv;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.zip.CRC32;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class StringHash extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private JLabel lblNewLabel;
	private JTextField txtInput;
	private JButton btnGenerate;
	private JButton btnClipboard;
	private JPanel panel;
	private JPanel panel_1;
	private JCheckBox checkCRC;
	private JTextField txtCRC;
	private JPanel panel_2;
	private JCheckBox checkSHA256;
	private JTextField txtSHA256;
	private JPanel panel_3;
	private JCheckBox checkMD5;
	private JTextField txtMD5;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel panel_6;
	private JCheckBox checkSHA384;
	private JTextField txtSHA384;
	private JCheckBox checkSHA1;
	private JTextField txtSHA1;
	private JCheckBox checkSHA512;
	private JTextField txtSHA512;
	
	private ResourceBundle resource;
	
	public StringHash(ResourceBundle resource) {
		this.resource = resource;
		initComponents();
	}
	private void initComponents() {
		lblNewLabel = new JLabel(resource.getString("StringHash.lblNewLabel"));
		txtInput = new JTextField();
		txtInput.setColumns(10);
		
		btnGenerate = new JButton(resource.getString("StringHash.btnGenerate"));
		btnGenerate.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				generar();
			}
		});
		
		btnClipboard = new JButton(resource.getString("StringHash.btnClipboard"));
		btnClipboard.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				copyToClipboard();
			}
		});
		
		panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(panel, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
								.addContainerGap())
							.addComponent(lblNewLabel)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(txtInput, GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnGenerate)
								.addContainerGap()))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(btnClipboard)
							.addContainerGap())))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnGenerate))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnClipboard)
					.addGap(13))
		);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(0, 0, 0, 10));
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(2, 1, 0, 0));
		
		checkCRC = new JCheckBox(resource.getString("StringHash.checkCRC"));
		checkCRC.setSelected(true);
		panel_1.add(checkCRC);
		
		txtCRC = new JTextField();
		panel_1.add(txtCRC);
		txtCRC.setColumns(10);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(0, 0, 0, 10));
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(2, 1, 0, 0));
		
		checkSHA256 = new JCheckBox(resource.getString("StringHash.checkSHA256"));
		checkSHA256.setSelected(true);
		panel_2.add(checkSHA256);
		
		txtSHA256 = new JTextField();
		panel_2.add(txtSHA256);
		txtSHA256.setColumns(10);
		
		panel_3 = new JPanel();
		panel_3.setBorder(new EmptyBorder(0, 0, 0, 10));
		panel.add(panel_3);
		panel_3.setLayout(new GridLayout(2, 1, 0, 0));
		
		checkMD5 = new JCheckBox(resource.getString("StringHash.checkMD5"));
		checkMD5.setSelected(true);
		panel_3.add(checkMD5);
		
		txtMD5 = new JTextField();
		panel_3.add(txtMD5);
		txtMD5.setColumns(10);
		
		panel_4 = new JPanel();
		panel_4.setBorder(new EmptyBorder(0, 0, 0, 10));
		panel.add(panel_4);
		panel_4.setLayout(new GridLayout(2, 1, 0, 0));
		
		checkSHA384 = new JCheckBox(resource.getString("StringHash.checkSHA384"));
		checkSHA384.setSelected(true);
		panel_4.add(checkSHA384);
		
		txtSHA384 = new JTextField();
		panel_4.add(txtSHA384);
		txtSHA384.setColumns(10);
		
		panel_5 = new JPanel();
		panel_5.setBorder(new EmptyBorder(0, 0, 0, 10));
		panel.add(panel_5);
		panel_5.setLayout(new GridLayout(2, 1, 0, 0));
		
		checkSHA1 = new JCheckBox(resource.getString("StringHash.checkSHA1"));
		checkSHA1.setSelected(true);
		panel_5.add(checkSHA1);
		
		txtSHA1 = new JTextField();
		panel_5.add(txtSHA1);
		txtSHA1.setColumns(10);
		
		panel_6 = new JPanel();
		panel_6.setBorder(new EmptyBorder(0, 0, 0, 10));
		panel.add(panel_6);
		panel_6.setLayout(new GridLayout(2, 1, 0, 0));
		
		checkSHA512 = new JCheckBox(resource.getString("StringHash.checkSHA512"));
		checkSHA512.setSelected(true);
		panel_6.add(checkSHA512);
		
		txtSHA512 = new JTextField();
		panel_6.add(txtSHA512);
		txtSHA512.setColumns(10);
		setLayout(groupLayout);
	}
	
	private void generar(){
		if(txtInput.getText().length() > 0){
			if(checkCRC.isSelected())
				txtCRC.setText(getCRC(txtInput.getText()));
			if(checkMD5.isSelected())
				txtMD5.setText(getChecksum(txtInput.getText(), Constantes.METHOD_MD5));
			if(checkSHA1.isSelected())
				txtSHA1.setText(getChecksum(txtInput.getText(), Constantes.METHOD_SHA1));
			if(checkSHA256.isSelected())
				txtSHA256.setText(getChecksum(txtInput.getText(), Constantes.METHOD_SHA256));
			if(checkSHA384.isSelected())
				txtSHA384.setText(getChecksum(txtInput.getText(), Constantes.METHOD_SHA384));
			if(checkSHA512.isSelected())
				txtSHA512.setText(getChecksum(txtInput.getText(), Constantes.METHOD_SHA512));
		}
	}
	
	private String getCRC(String cadena){
		String checksum = "";
		CRC32 metodo = new CRC32();
		
		metodo.update(cadena.getBytes());
		checksum = String.format("%08X", metodo.getValue());
		
		return checksum.toUpperCase();
	}
	
	private String getChecksum(String cadena, int metodo){
		MessageDigest digest;
		String checksum = "";
		try {
			digest = MessageDigest.getInstance(getCheckName(metodo));
			digest.update(cadena.getBytes());
			byte[] byteResult = digest.digest();
			for(int i = 0; i < byteResult.length; i++)
				checksum += Integer.toString( (byteResult[i] & 0xff ) + 0x100, 16).substring( 1 );
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return checksum.toUpperCase();
	}
	
	private String getCheckName(int metodo){
		if(metodo == Constantes.METHOD_MD5)
			return Constantes.NAME_MD5;
		else if(metodo == Constantes.METHOD_SHA1)
			return Constantes.NAME_SHA1;
		else if(metodo == Constantes.METHOD_SHA256)
			return Constantes.NAME_SHA256;
		else if(metodo == Constantes.METHOD_SHA384)
			return Constantes.NAME_SHA384;
		else if(metodo == Constantes.METHOD_SHA512)
			return Constantes.NAME_SHA512;
		
		return null;
	}
	
	private void copyToClipboard(){
		String linea = "";
		if(txtInput.getText().length() > 0){
			linea += resource.getString("StringHash.string") + " " + txtInput.getText() + "\n";
			if(checkCRC.isSelected())
				linea += resource.getString("StringHash.checkCRC") + " " + txtCRC.getText() + "\n";
			if(checkMD5.isSelected())
				linea += resource.getString("StringHash.checkMD5") + " " +  txtMD5.getText() + "\n";
			if(checkSHA1.isSelected())
				linea += resource.getString("StringHash.checkSHA1") + " " +  txtSHA1.getText() + "\n";
			if(checkSHA256.isSelected())
				linea += resource.getString("StringHash.checkSHA256") + " " +  txtSHA256.getText() + "\n";
			if(checkSHA384.isSelected())
				linea += resource.getString("StringHash.checkSHA384") + " " +  txtSHA384.getText() + "\n";
			if(checkSHA512.isSelected())
				linea += resource.getString("StringHash.checkSHA512") + " " +  txtSHA512.getText() + "\n";
		}
		
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection string = new StringSelection(linea);
		clipboard.setContents(string, string);
	}
}
