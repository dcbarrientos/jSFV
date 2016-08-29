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
 * JSFV.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 17 de ago. de 2016, 1:22:14 p. m. 
 */

package ar.com.dcbarrientos.jsfv;

import java.awt.BorderLayout;
import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.SwingUtilities;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class JSFV {
	public static int ERROR_WRONG_NUMBER_ARG=0;
	public static int ERROR_ILLEGAL_OPTION=1;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Locale locale = Locale.getDefault();
				ResourceBundle resource = ResourceBundle.getBundle("ar.com.dcbarrientos.jsfv.resource.JSFV", locale);
				
				Vector<File> archivos = new Vector<File>();
				String method = "";
				String sfvFileName = "";
				String fileName = "";
				boolean isOk = false;
				boolean isEmpty = false;
				int i = 0;
				
				if(args.length > 0){
					isEmpty = false;
					while(i < args.length){
						if(args[i].equals("-h")){
							if(args.length > 1){
								showError(ERROR_WRONG_NUMBER_ARG);
								return;
							}
							showHelp();
							return;
						}else if(args[i].equals("-v")){
							if(args.length > 1){
								showError(ERROR_WRONG_NUMBER_ARG);
								return;
							}
							Principal principal = new Principal(resource);
							System.out.println(principal.getVersion());
							return;
						}else if(args[i].equals("-l")){
							if(args.length != 2){
								showError(ERROR_WRONG_NUMBER_ARG);
								return;
							}
							sfvFileName = args[1];
							if(new File(sfvFileName).exists()){
								isOk = true;
							}
							break;
						}else if(args[i].equals("-f")){
							i++;
							if(args.length < 4 ){
								showError(ERROR_WRONG_NUMBER_ARG);
								return;
							}else{
								boolean continuar = true;
								File file;
								while(i < args.length && continuar){
									if(args[i].equals("-m")){
										continuar = false;
										i++;
										method = args[i];
									}else{
										file = new File(args[i]);
										if(!file.exists()){
											showError(ERROR_WRONG_NUMBER_ARG);
											return;
										}else{
											archivos.addElement(file);
											i++;
										}
									}
								}
							}
							isOk = true;
						}else if(args[i].equals("-m")){
							if(args.length < 4){
								showError(ERROR_WRONG_NUMBER_ARG);
								return;
							}
							i++;
							method = args[i];
							isOk = true;
						}else if(args[i].equals("-c")){
							if(args.length < 2){
								showError(ERROR_WRONG_NUMBER_ARG);
								return;
							}
							i++;
							fileName = args[i];
							isOk = true;
						}else{
							showError(ERROR_ILLEGAL_OPTION);
							return;
						}
						i++;
					}
				}else{
					isEmpty = true;
					isOk = true;
				}
				
				if(isOk){
					if(sfvFileName.length() > 0 || (archivos.size() > 0 && method.length()>0) || isEmpty){
						Principal principal = new Principal(resource);
		
						if(sfvFileName.length() > 0){
							if(Constantes.isChecksumListFile(sfvFileName)){
								principal.loadSFV(sfvFileName);
							}
						}else if(!isEmpty){ //Varios archivos con metodo
							File[] a = new File[archivos.size()];
							archivos.toArray(a);
							principal.nuevoArchivo(a, Constantes.getMethodIndexByName(method));
						}
						
						if(!isEmpty)
							principal.procesar();
						
						principal.setVisible(true);
					}else if(fileName.length() > 0){
						File file = new File(fileName);
						if(file.exists()){
							ToolDialog dialog = new ToolDialog();
							dialog.setTitle(resource.getString("VerifyFile.title"));
							VerifyFile verify = new VerifyFile(resource);
							verify.setInputFile(file);
							int index = Constantes.getMethodIndexByName(method);
							if(index >= 0){
								verify.setMethodIndex(index);
								verify.verificar();
							}
							dialog.add(verify, BorderLayout.CENTER);
							dialog.setVisible(true);
						}
					}
				}
			}
		});
	}
	
	public static void showError(int errorCode){
		if(errorCode == ERROR_ILLEGAL_OPTION)
			System.out.println("jsfv: illegal option");
		else if(errorCode == ERROR_WRONG_NUMBER_ARG)
			System.out.println("jsfv: wrong number of parameters.");
		showHelp();
	}
	
	public static void showHelp(){
		System.out.println("Usage: jsfv [-c file] [-m SFV|MD5|SHA-1|SHA-256|SHA-384|SHA-512]");
		System.out.println("       jsfv [-h]");
		System.out.println("       jsfv [-f lista -m SFV|MD5|SHA-1|SHA-256|SHA-384|SHA-512]");
		System.out.println("       jsfv [-l file.sfv|.md5|.sha-1|.sha256|.sha384|.sha512]");
		System.out.println("       jsfv [-v]");
		System.out.println("Options:");
		System.out.println("  -c file  : File to verify");
		System.out.println("  -f files : File/s to include in the new checksum file.");
		System.out.println("  -h       : Show this help.");
		System.out.println("  -l file  : Checksum file");
		System.out.println("  -m       : Specify the method to use: SFV|MD5|SHA-1|SHA-256|SHA-384|SHA-512 ");
		System.out.println("  -v       : Show version number.");
	}
}
