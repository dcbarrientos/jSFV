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
 * TableRenderer.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 17 de ago. de 2016, 8:02:27 p. m. 
 */

package ar.com.dcbarrientos.jsfv.tables;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class ArchivosTableRenderer extends DefaultTableCellRenderer{
	private static final long serialVersionUID = 1L;
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		if(value instanceof JLabel) {
			JLabel elemento = (JLabel)value;
			elemento.setOpaque(true);
			fillColor(table, elemento, isSelected);
			return elemento;
		}else{
			System.out.println("paso");
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}
	}
	
	public void fillColor(JTable table, JLabel label, boolean isSelected){
		if(isSelected){
			label.setBackground(table.getSelectionBackground());
			label.setForeground(table.getSelectionForeground());
		}else{
			label.setBackground(table.getBackground());
			label.setForeground(table.getForeground());
		}
	}
}
