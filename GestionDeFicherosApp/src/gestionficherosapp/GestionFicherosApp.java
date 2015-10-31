// ***********************************
// EDICIÓN POR: SERGIO CONDE FERREIRA
// 2º DAM SEMI
// ***********************************
package gestionficherosapp;

import gestionficheros.MainGUI;

public class GestionFicherosApp {

	public static void main(String[] args) {
		GestionFicherosImpl getFicherosImpl = new GestionFicherosImpl();
		new MainGUI(getFicherosImpl).setVisible(true);
	}

}
