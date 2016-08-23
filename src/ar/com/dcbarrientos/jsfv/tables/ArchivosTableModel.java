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
 * ArchivosTableModel.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 18 de ago. de 2016, 11:38:28 a. m. 
 */

package ar.com.dcbarrientos.jsfv.tables;

import java.awt.FontMetrics;
import java.io.File;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import ar.com.dcbarrientos.jsfv.Constantes;
import ar.com.dcbarrientos.jsfv.Principal;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class ArchivosTableModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	private String[] headers;
	private Class<?>[] clases = {JLabel.class, String.class, String.class, String.class};
	private Vector<Object[]> datos;
	private Vector<File> archivos;
	private int[] columnSize = {0, 0, 0, 0};
	private long totalBytesToRead;
	private FontMetrics metrics;
	
	public ArchivosTableModel() {
		datos = new Vector<Object[]>();
		archivos = new Vector<File>();
		totalBytesToRead = 0;		
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(datos != null){
			return datos.elementAt(rowIndex)[columnIndex];
		}
		
		return null;
	}
	
	public void setValueAt(Object value, int rowIndex, int columnIndex){
		Object[] elemento = new Object[headers.length];
		elemento = datos.get(rowIndex);
		elemento[columnIndex] = value;
		datos.set(rowIndex, elemento);
		
		verifySize(value, columnIndex);
		
		fireTableDataChanged();
	}
	
	@Override
	public int getRowCount() {
		if(datos != null)
			return datos.size();
		return 0;
	}
	
	@Override
	public int getColumnCount() {
		return headers.length;
	}
	
	@Override
	public String getColumnName(int column) {
		return headers[column];
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex){
		return clases[columnIndex];
	}
	
	public void setHeaders(String[] headers){
		this.headers = headers;
		for(int i = 0; i < headers.length; i++)
			verifySize(headers[i], i);

	}
	
	public void addRow(File archivo, String checksum, int icono){
		Object[] elemento = new Object[headers.length];
		elemento[Constantes.COLUMN_NAME] = getLabel(archivo.getName(), icono);
		elemento[Constantes.COLUMN_SIZE] = Constantes.getExtendedSize(archivo.length());
		elemento[Constantes.COLUMN_SAVED_CHECKSUM] = checksum;
		
		datos.addElement(elemento);
		archivos.add(archivo);
		
		totalBytesToRead += archivo.length();
		
		verifyRowSize(elemento);
		
		fireTableDataChanged();
	}
		
	public void resetDatos(){
		datos = new Vector<Object[]>();
		archivos = new Vector<File>();
		
		fireTableDataChanged();
		
	}
	
	public void setRowIcon(int rowIndex, int icono){
		((JLabel)datos.get(rowIndex)[Constantes.COLUMN_NAME]).setIcon(getIcono(icono));
	}
	
	public String getFileName(int rowIndex){
		return ((JLabel)datos.get(rowIndex)[Constantes.COLUMN_NAME]).getText();
	}
	
	private ImageIcon getIcono(int tipo){
		if(tipo == Constantes.ICON_OK)
			return new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jsfv/images/Icon_Ok_16x16.png"));
		else if(tipo == Constantes.ICON_ERROR)
			return new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jsfv/images/Icon_Cancel_16x16.png"));
		else if(tipo == Constantes.ICON_QUESTION)
			return new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jsfv/images/Icon_Help_16x16.png"));
		else if(tipo == Constantes.ICON_FILE_NOT_FOUND)
			return new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jsfv/images/File-Not-Found_16x16.png"));
		
		return null;
	}
	
	private JLabel getLabel(String nombre, int icono){
		JLabel label = new JLabel(getIcono(icono), JLabel.LEFT);
		label.setText(nombre);
		
		return label;
	}
	
	public Vector<File> getArchivos(){
		return archivos;
	}
	
	public long getTotalBytesToRead(){
		return totalBytesToRead;
	}
	
	public String getStringFileName(int index){
		return ((JLabel)datos.get(index)[Constantes.COLUMN_NAME]).getText();
	}
	
	private void verifyRowSize(Object[] row){
		for(int i = 0; i < headers.length; i++)
			verifySize(row[i], i);
	}
	
	public void setFontMetrics(FontMetrics metrics){
		this.metrics = metrics;
	}

	private void verifySize(String valor, int index){
		if(valor != null){
			columnSize[index] = metrics.stringWidth(valor);
		}
	}
	
	private void verifySize(Object valor, int index){
		int ancho;
		
		if(valor != null){
			if(valor instanceof JLabel)
				ancho = metrics.stringWidth(((JLabel)valor).getText());
			else
				ancho = metrics.stringWidth((String)valor);
			
			if(ancho > columnSize[index]){
				columnSize[index] = ancho;
			}
		}
	}

	public int[] getColumnsSize(){
		return columnSize;
	}
}
