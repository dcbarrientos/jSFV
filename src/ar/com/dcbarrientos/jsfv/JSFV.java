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

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.SwingUtilities;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class JSFV {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Locale locale = Locale.getDefault();
				ResourceBundle resource = ResourceBundle.getBundle("ar.com.dcbarrientos.jsfv.resource.JSFV", locale);
				
				Principal principal = new Principal(resource);
				principal.setVisible(true);
			}
		});
	}

}
