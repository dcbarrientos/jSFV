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
 * Constantes.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 17 de ago. de 2016, 8:20:11 p. m. 
 */

package ar.com.dcbarrientos.jsfv;

import java.text.DecimalFormat;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class Constantes {
	public static final int BYTES = 0;
	public static final int KB = 1;
	public static final int MB = 2;
	public static final int GB = 3;
	
	public static final int COLUMN_NAME = 0;
	public static final int COLUMN_SIZE = 1;
	public static final int COLUMN_CHECKSUM = 2;
	public static final int COLUMN_SAVED_CHECKSUM = 3;
	
	public static final int METHOD_SFV = 0;
	public static final int METHOD_MD5 = 1;
	public static final int METHOD_SHA1 = 2;
	public static final int METHOD_SHA256 = 3;
	public static final int METHOD_SHA384 = 4;
	public static final int METHOD_SHA512 = 5;
	
	
	public static final int ACCION_NUEVO = 0;
	public static final int ACCION_VERIFICAR = 1;
	public static final int ERROR = -1;
	
	public static final int ICON_OK = 0;
	public static final int ICON_ERROR = 1;
	public static final int ICON_QUESTION = 2;
	public static final int ICON_FILE_NOT_FOUND = 3;
	
	public static final String SFV_FILE_NOT_FOUND = "nf";
	public static final int COLUMN_CONSTANT = 20;
	public static final int LEGEND_RIGHT_MARGIN = 10;
	public static final int LEGEND_LEFT_MARGIN = 30;
	
	public static final Object METHODS[][] = {  {"SFV", ".sfv", 8},
												{"MD5", ".md5", 32},
												{"SHA-1", ".sha1", 40},
												{"SHA-256", ".sha256", 64},
												{"SHA-384", ".sha384", 96},
												{"SHA-512", ".sha512", 128}};
	
	public static final int METHOD_NAME = 0;
	public static final int METHOD_EXTENSION = 1;
	public static final int METHOD_LENGTH=2;
	
	public static final int TABLE_MARGIN = 10;
	
			
	/**
	 * Cuando se le ingresa un valor en bytes devuelve su equivalente en la unidad más
	 * grande posible. 1024 bytes devolvería 1 kb.
	 * @param nro Valor en bytes para calcular su equivalente.
	 * @return Devuelve el equibalente más grande posible.
	 */	
	public static String getExtendedSize(double nro){
		double size = nro;
		int cons = 1024;
		int unidad = 0;
		
		while(size > cons){
			size /= cons;
			unidad++;
		}
	
		DecimalFormat dec = new DecimalFormat("0.00");
		if(unidad == BYTES)
			return dec.format(size).concat(" B.");
		else if(unidad == KB)
			return dec.format(size).concat(" Kb.");
		else if(unidad == MB)
			return dec.format(size).concat(" Mb.");
	
		return dec.format(size).concat(" Gb.");
	}	
	
	public static String[] getMethodsByName(){
		String[] listMethods = new String[METHODS.length];
		for(int i = 0; i < METHODS.length; i++)
			listMethods[i] = (String)METHODS[i][METHOD_NAME];
		
		return listMethods;
	}
	
	public static int getMethodIndexByLength(int length){
		for(int i = 0; i < METHODS.length; i++){
			if(length == (int)METHODS[i][METHOD_LENGTH])
				return i;
		}
		
		return ERROR;
	}
	
	public static int getMethodIndexByExtension(String extension){
		for(int i = 0; i < METHODS.length; i++){
			if(extension.equals((String)METHODS[i][METHOD_EXTENSION])){
				return i;
			}
		}
		
		return ERROR;
	}
	
	public static int getMethodIndexByName(String name){
		for(int i = 0; i < METHODS.length; i++){
			if(name.equals((String)METHODS[i][METHOD_NAME])){
				return i;
			}
		}
		
		return ERROR;
	}
	
	public static boolean isChecksumListFile(String fileName){
		String extension = fileName.substring(fileName.lastIndexOf('.'), fileName.length());
		for(int i = 0; i < METHODS.length; i++){
			if(extension.equals(METHODS[i][METHOD_EXTENSION])){
				return true;
			}
		}
		
		return false;
	}
}
