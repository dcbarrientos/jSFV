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
 * About.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 22 de ago. de 2016, 5:16:18 p. m. 
 */

package ar.com.dcbarrientos.jsfv;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class About extends JDialog{
	private static final long serialVersionUID = 1L;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JButton btnAccept;

	ResourceBundle resource;
	private JTabbedPane tabbedPane;
	private JTextArea txtChangelog;
	private JScrollPane scrollChangelog;
	private JTextArea txtLicense;
	private JScrollPane scrollLicense;
	
	public About(Principal principal, ResourceBundle resource){
		super(principal, true);
		this.resource = resource;
		initComponents();
	}
	
	private void initComponents(){
		setTitle(resource.getString("About.title"));
		setPreferredSize(new Dimension(700, 450));
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setMaximumSize(new Dimension(90, 90));
		lblNewLabel.setMinimumSize(new Dimension(90, 90));
		lblNewLabel.setIcon(new ImageIcon(About.class.getResource("/ar/com/dcbarrientos/jsfv/images/logo.png")));
		
		lblNewLabel_1 = new JLabel(Principal.APP_NAME);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		lblNewLabel_2 = new JLabel("Version: " + Principal.VERSION);
		
		lblNewLabel_3 = new JLabel("Copyright (c) 2016 Diego Barrientos");
		
		btnAccept = new JButton(resource.getString("About.btnAccept"));
		btnAccept.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				dispose();
			}
		});
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1)
								.addComponent(lblNewLabel_2)
								.addComponent(lblNewLabel_3)))
						.addComponent(btnAccept, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(36)
							.addComponent(lblNewLabel_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_2)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_3)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
					.addGap(11)
					.addComponent(btnAccept)
					.addContainerGap())
		);
		
		txtChangelog = new JTextArea();
		txtChangelog.setFont(new Font("Courier New", Font.PLAIN, 13));
		scrollChangelog = new JScrollPane();
		scrollChangelog.setViewportView(txtChangelog);
		loadTextFile("Changelog.txt", txtChangelog);
		
		txtLicense = new JTextArea();
		txtLicense.setFont(new Font("Courier New", Font.PLAIN, 13));
		scrollLicense = new JScrollPane();
		scrollLicense.setViewportView(txtLicense);
		loadTextFile("LICENSE", txtLicense);
		
		tabbedPane.addTab(resource.getString("About.changelogTab.title"), null, scrollChangelog, null);
		tabbedPane.addTab(resource.getString("About.licenseTab.title"), null, scrollLicense, null);
		getContentPane().setLayout(groupLayout);
		pack();
		setLocationRelativeTo(null);
	}
	
	public void showDialog(){
		setVisible(true);
	}
	
	private void loadTextFile(String fileName, JTextArea control){
		BufferedReader buffer;
		try {
			buffer = new BufferedReader(new FileReader(fileName));
			
			control.read(buffer, null);
			buffer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
