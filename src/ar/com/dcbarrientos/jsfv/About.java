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
import java.util.ResourceBundle;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
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
	
	public About(Principal principal, ResourceBundle resource){
		super(principal, true);
		this.resource = resource;
		initComponents();
	}
	
	private void initComponents(){
		setTitle("About...");
		setPreferredSize(new Dimension(450, 200));
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setMaximumSize(new Dimension(90, 90));
		lblNewLabel.setMinimumSize(new Dimension(90, 90));
		lblNewLabel.setIcon(new ImageIcon(About.class.getResource("/ar/com/dcbarrientos/jsfv/images/logo.png")));
		
		lblNewLabel_1 = new JLabel(Principal.APP_NAME);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		lblNewLabel_2 = new JLabel("Version: " + Principal.VERSION);
		
		lblNewLabel_3 = new JLabel("Copyright (c) 2016 Diego Barrientos");
		
		btnAccept = new JButton("Accept");
		btnAccept.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				dispose();
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1)
						.addComponent(lblNewLabel_2)
						.addComponent(lblNewLabel_3))
					.addContainerGap(282, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(335, Short.MAX_VALUE)
					.addComponent(btnAccept)
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
					.addPreferredGap(ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
					.addComponent(btnAccept)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
		pack();
		setLocationRelativeTo(null);
	}
	
	public void showDialog(){
		setVisible(true);
	}
}
