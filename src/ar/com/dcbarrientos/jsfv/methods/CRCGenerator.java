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
 * CRCGenerator.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 19 de ago. de 2016, 9:33:34 a. m. 
 */

package ar.com.dcbarrientos.jsfv.methods;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.CRC32;

import javax.swing.JProgressBar;
import javax.swing.JTable;

import ar.com.dcbarrientos.jsfv.ChecksumGenerator;
import ar.com.dcbarrientos.jsfv.Constantes;


/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class CRCGenerator extends ChecksumGenerator{
	public CRCGenerator(JProgressBar progressBar){
		this.progressBar = progressBar;
		this.tableFile = null;
		this.currentRow = -1;
		this.canceled = false;
	}
	
	public CRCGenerator(JProgressBar progressBar, JTable tableFile){
		this.progressBar = progressBar;
		this.tableFile = tableFile;
		this.currentRow = -1;
	}

	@Override
	protected Void doInBackground(){
		checksumList = new String[fileList.size()];
		acumuladoTotal = 0;
		FileInputStream input = null;
		int indice = 0;
		while(indice < fileList.size() && !canceled){
			try{
				input = new FileInputStream(fileList.elementAt(indice));
				byte[] buffer = new byte[BUFFER_SIZE];
				int read;
				long fileSize = fileList.elementAt(indice).length();
				acumulado = 0;
				
				CRC32 metodo = new CRC32();
				
				while((read = input.read(buffer)) != -1 && !canceled){
					metodo.update(buffer, 0, read);
					acumulado += read;
					acumuladoTotal += read;
					publish(acumulado * 100 / fileSize);
				}
				
				checksumList[indice] = String.format("%08X", metodo.getValue());
				input.close();
			}catch(FileNotFoundException e){
				checksumList[indice] = Constantes.SFV_FILE_NOT_FOUND;
			}catch(IOException e){
				//TODO manejar este error
				e.printStackTrace();
			}
			publish(new Long(100));
			currentRow++;
			
			if(tableFile != null && !canceled){
				tableFile.setValueAt(checksumList[indice], indice, Constantes.COLUMN_CHECKSUM);
			}
			indice ++;
		}
		
		if(canceled && input != null){
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
