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
import java.util.Vector;

import javax.swing.SwingWorker;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public abstract class ChecksumGenerator extends SwingWorker<Void, Long>{
	public abstract long getBytesAcumulados();
	public abstract void setFileList(Vector<File> fileList);
	public abstract int getCurrentRow();
	public abstract String getChecksum(int index);
}
