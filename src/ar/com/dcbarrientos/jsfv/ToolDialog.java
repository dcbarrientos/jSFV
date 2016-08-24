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
 * ToolDialog.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 24 de ago. de 2016, 1:10:54 p. m. 
 */

package ar.com.dcbarrientos.jsfv;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JDialog;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class ToolDialog extends JDialog{
	//private Principal principal;
	
	private static final long serialVersionUID = 1L;

	public ToolDialog(Principal principal){
		super(principal, true);
		
		initComponents();
	}
	
	private void initComponents(){
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(567, 310));
		pack();
		setLocationRelativeTo(null);
	}
	
	public boolean showDialog(){
		setVisible(true);
		return true;
	}
}
