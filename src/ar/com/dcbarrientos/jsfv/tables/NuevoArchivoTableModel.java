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
 * NuevoArchivoTableModel.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 18 de ago. de 2016, 5:33:44 p. m. 
 */

package ar.com.dcbarrientos.jsfv.tables;

import java.awt.FontMetrics;
import java.io.File;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import ar.com.dcbarrientos.jsfv.Constantes;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class NuevoArchivoTableModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	private String[] headers;
	private Vector<File> datos;
	private FontMetrics fontMetrics;
	private int[] columnSizes = {0, 0, 0};;
	
	public static final int COLUMN_NAME = 0;
	public static final int COLUMN_SIZE = 1;
	public static final int COLUMN_PATH = 2;
	
	public NuevoArchivoTableModel(FontMetrics fontMetrics){
		datos = new Vector<File>();
		
		this.fontMetrics = fontMetrics;
	}
	
	@Override
	public String getColumnName(int columnIndex){
		return headers[columnIndex];
	}
	
	@Override
	public int getRowCount() {
		return datos.size();
	}

	@Override
	public int getColumnCount() {
		return headers.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex == COLUMN_NAME)
			return datos.get(rowIndex).getName();
		else if(columnIndex == COLUMN_SIZE)
			return Constantes.getExtendedSize(datos.get(rowIndex).length());
		else if(columnIndex == COLUMN_PATH)
			return datos.get(rowIndex).getPath();
		
		return null;
	}
	
	public void setColumnIdentifiers(String[] headers){
		this.headers = headers; 
		if(datos.isEmpty()){
			verificarAncho();
		}
	}
	
	public void addRow(File archivo){
		datos.addElement(archivo);
		//todo verificar el ancho para las 3 oclumnas
		if(fontMetrics.stringWidth(archivo.getName()) > columnSizes[COLUMN_NAME]){
			columnSizes[COLUMN_NAME] = fontMetrics.stringWidth(archivo.getName());
		}
		if(fontMetrics.stringWidth(Constantes.getExtendedSize(archivo.length())) > columnSizes[COLUMN_SIZE]){
			columnSizes[COLUMN_SIZE] = fontMetrics.stringWidth(Constantes.getExtendedSize(archivo.length()));
		}
		if(fontMetrics.stringWidth(archivo.getPath()) > columnSizes[COLUMN_PATH]){
			columnSizes[COLUMN_PATH] = fontMetrics.stringWidth(archivo.getPath());
		}
		
		fireTableDataChanged();
	}
	
	public void removeAt(int rowIndex){
		datos.remove(rowIndex);
		fireTableDataChanged();
	}
	
	public File[] getDatos(){
		File[] archivos = new File[datos.size()];
		datos.toArray(archivos);
		return archivos;
	}
	
	public void setFontMetrics(FontMetrics fontMetrics){
		this.fontMetrics = fontMetrics;
	}
	
	public void removeAll(){
		datos.removeAllElements();

		fireTableDataChanged();
	}
	
	private void verificarAncho(){
		if(headers != null && headers.length > 0){
			for(int i = 0; i < headers.length; i ++){
				columnSizes[i] = fontMetrics.stringWidth(headers[i]);
			}
		}				
	}
	
	public int[] getColumnsSize(){
		return columnSizes;
	}
}
