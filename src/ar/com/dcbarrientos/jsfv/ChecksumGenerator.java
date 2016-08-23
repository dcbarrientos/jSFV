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
 * ChecksumGenerator.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 19 de ago. de 2016, 10:01:30 a. m. 
 */

package ar.com.dcbarrientos.jsfv;

import java.io.File;
import java.util.List;
import java.util.Vector;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.SwingWorker;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public abstract class ChecksumGenerator extends SwingWorker<Void, Long>{
	public static final int BUFFER_SIZE = 1024;
	
	protected Vector<File> fileList;	//Lista de archivos a procesar.
	protected String[] checksumList;	//Lista con los checksums.
	protected long acumulado;			//Cantidad de bytes acumulados por archivo
	protected long acumuladoTotal;	//Cantidad de bytes acumulados en total.
	protected int currentRow;		//Fila que está actualmente en verificación
	protected String methodName;
	protected JProgressBar progressBar;
	protected JTable tableFile;
	protected boolean canceled;
	
	@Override
	protected void process(List<Long>datos){
		progressBar.setValue(datos.get(0).intValue());
	}
	
	@Override
	protected void done(){
		if(!canceled)
			progressBar.setValue(100);
	}
	
	//@Override
	public long getBytesAcumulados(){
		return acumuladoTotal;
	}
		
	public void setFileList(File archivo){
		fileList = new Vector<File>();
		fileList.add(archivo);
	}
	
	public void setFileList(Vector<File> fileList){
		this.fileList = fileList;
		acumuladoTotal = -1;
	}	
	
	public int getCurrentRow(){
		return currentRow;
	}

	public String getChecksum(int index){
		return checksumList[index];
	}
	
	public void setMetodo(String methodName){
		this.methodName = methodName;
	}

	public void cancelar(){
		canceled = true;
	}
	
	public boolean isCancelado(){
		return canceled;
	}
}
