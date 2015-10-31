// ***********************************
// EDICIÓN POR: SERGIO CONDE FERREIRA
// 2º DAM SEMI
// ***********************************
package gestionficherosapp;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import gestionficheros.FormatoVistas;
import gestionficheros.GestionFicheros;
import gestionficheros.GestionFicherosException;
import gestionficheros.TipoOrden;

public class GestionFicherosImpl implements GestionFicheros{
	private File carpetaDeTrabajo=null;
	private Object[][] contenido;
	private int filas = 0;
	private int columnas = 3;
	private FormatoVistas formatoVistas=FormatoVistas.NOMBRES;
	private TipoOrden ordenado=TipoOrden.DESORDENADO;
	
	public GestionFicherosImpl(){
		carpetaDeTrabajo=File.listRoots()[0];
		actualiza();
	}
	
	private void actualiza(){
		
		String[] ficheros = carpetaDeTrabajo.list(); // Obtener los nombres
		// Calcular el número de filas necesario
		filas = ficheros.length / columnas;
		if (filas*columnas < ficheros.length){
			filas++; // Si hay resto necesitamos una fila más
		}
		
		// Dimensionar la matriz contenido según los resultados
		
		contenido = new String[filas][columnas];
		// Rellenar contenido con los nombres obtenidos
		for (int i = 0; i < columnas; i++){
			for (int j = 0; j < filas; j++){
				int ind = j * columnas + i;
				if (ind < ficheros.length){
					contenido[j][i] = ficheros[ind];
				}else{
					contenido[j][i] = "";
				}
			}
		}
		
			
	}
	
	@Override
	public void arriba() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void creaCarpeta(String arg0) throws GestionFicherosException{
		
		// Se crea el objeto file
		File file = new File (carpetaDeTrabajo, arg0);

		// CONTROL DE EXCEPCIONES:
		if(carpetaDeTrabajo.canWrite()){ // Si se puede escribir
			if(!file.exists()){ // Si el archivo no existe:
				file.mkdir(); // Se crea
			}else{
				throw new GestionFicherosException("El archivo ya existe");
			}
		}else{
			throw new GestionFicherosException("No dispone de permisos");
		}	
		actualiza();
	}
	
	@Override
	public void creaFichero(String arg0) throws GestionFicherosException{
		
		// Se crea el objeto file
		File file = new File (carpetaDeTrabajo,arg0);
		// CONTROL DE EXCEPCIONES:
		if(carpetaDeTrabajo.canWrite()){     
			if(!file.exists()){ // Si no existe
				try{
					file.createNewFile(); // Se crea
				}catch (IOException e){ // Si no puede crearlo
					throw new GestionFicherosException("No se pudo crear el archivo");
				}
			}else{ // Si existe
				throw new GestionFicherosException("El archivo ya existe");
			}
		}else{ // Si no se tienen permisos
			throw new GestionFicherosException("No dispone de permisos");
		}
		actualiza();
			
	}
	
	@Override
	public void elimina(String arg0) throws GestionFicherosException {
		
		// Se crea el objeto file
		File file = new File (carpetaDeTrabajo, arg0);
		
		// CONTROL DE EXCEPCIONES:
		if(carpetaDeTrabajo.canWrite()){ // Si se puede escribir
			if(file.exists()){ // Si existe
				file.delete(); // Se borra
			}else{ // Si no existe
				throw new GestionFicherosException("El archivo no existe");
			}
		}else{ // Si no se tienen permisos
			throw new GestionFicherosException("No dispone de permisos");
		}
		actualiza();
		
	}
	
	@Override
	public void entraA(String arg0) throws GestionFicherosException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getColumnas() {
		return columnas;
	}
	
	@Override
	public Object[][] getContenido() {
		return contenido;
	}
	
	@Override
	public String getDireccionCarpeta() {
		return carpetaDeTrabajo.getAbsolutePath();
	}
	
	@Override
	public String getEspacioDisponibleCarpetaTrabajo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getEspacioTotalCarpetaTrabajo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getFilas() {
		return filas;
	}
	
	@Override
	public FormatoVistas getFormatoContenido() {
		return formatoVistas;
	}
	
	@Override
	public String getInformacion(String arg0) throws GestionFicherosException {
		
		// String para concatenar información, en este caso mas strings
		StringBuilder strBuilder=new StringBuilder();
		// Crear el objeto file
		File file = new File (carpetaDeTrabajo,arg0);
		
		// Comprobar si existe el archivo
		if (file.exists() && file.canRead()){ // Si existe y tiene permisos de lectura:

			// Título
			strBuilder.append("INFORMACIÓN DEL SISTEMA");
			strBuilder.append("\n\n");
		
			// Nombre
			strBuilder.append("Nombre: ");
			strBuilder.append(file);
			strBuilder.append("\n\n");
		
			// Tipo (Fichero o directorio)
			if(file.isDirectory()){ // Si es carpeta:
				strBuilder.append("Tipo: Carpeta");
				strBuilder.append("\n");
				strBuilder.append("Nº de elementos: ");
				strBuilder.append(file.listFiles().length); // Contador de elementos
				strBuilder.append(", ");
				strBuilder.append("Espacio libre: ");
				strBuilder.append(file.getFreeSpace()); // Espacio libre
				strBuilder.append(" bytes");
				strBuilder.append(", ");
				strBuilder.append("Espacio Total: ");
				strBuilder.append(file.getTotalSpace()); // Espacio total
				strBuilder.append(" bytes");
				strBuilder.append(", ");
				strBuilder.append("Espacio disponible: ");
				strBuilder.append(file.getUsableSpace()); // Espacio disponible
				strBuilder.append(" bytes");
				strBuilder.append("\n\n");
			}else if(file.isFile()){ // Si es un archivo
				strBuilder.append("Tipo: Archivo");
				strBuilder.append("\n");
				strBuilder.append("Tamaño: ");
				long tamanyo = file.length();
				String strTamanyo = String.format("%,d", tamanyo); // Tamaño del archivo
				strBuilder.append(strTamanyo);
				strBuilder.append(" bytes");
				strBuilder.append("\n\n");
			}
		
			// Fecha de la última modificación
			Date ultimaModf = new Date (file.lastModified());
			// Formato para la fecha
			SimpleDateFormat FormatoFecha = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			strBuilder.append("Última modificación: ");
			strBuilder.append(FormatoFecha.format(ultimaModf));
			strBuilder.append("\n\n");
		
			// Oculto
			if(file.isHidden()){
				strBuilder.append("El archivo/carpeta: Está oculto");
				strBuilder.append("\n\n");
			}else{
				strBuilder.append("El archivo/carpeta: Es visible");
				strBuilder.append("\n\n");
			}
		
			// Ubicación
			String absolutePath = file.getAbsolutePath();
			strBuilder.append("Ubicación: ");
			strBuilder.append(absolutePath);
			strBuilder.append("\n\n");
		
		}else{ // Si no existe o no se tienen permisos:
			throw new GestionFicherosException("ERROR: No dispone de permisos o el fichero no existe");
		}
		
			// Devuelve:
			return strBuilder.toString();
	}
	
	@Override
	public boolean getMostrarOcultos() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String getNombreCarpeta() {
		return carpetaDeTrabajo.getName();
	}
	
	@Override
	public TipoOrden getOrdenado() {
		return ordenado;
	}
	
	@Override
	public String[] getTituloColumnas() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public long getUltimaModificacion(String arg0) throws GestionFicherosException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String nomRaiz(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int numRaices() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void renombra(String arg0, String arg1) throws GestionFicherosException {
		
		// Se crean ambos objetos File
		File file = new File (carpetaDeTrabajo, arg0);
		File newFileName = new File (carpetaDeTrabajo, arg1);
		
		// CONTROL DE EXCEPCIONES:
		if(carpetaDeTrabajo.canWrite()){ // Si se puede escribir
			if(file.exists()){ // Si existe
				file.renameTo(newFileName); // Renombralo
			}else{ // Si no existe
				throw new GestionFicherosException("El archivo no existe");
			}
		}else{ // Si no se tienen permisos
			throw new GestionFicherosException("No dispone de permisos");
		}
		actualiza();			
		
	}
	
	@Override
	public boolean sePuedeEjecutar(String arg0) throws GestionFicherosException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean sePuedeEscribir(String arg0) throws GestionFicherosException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean sePuedeLeer(String arg0) throws GestionFicherosException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void setColumnas(int arg0) {
		columnas = arg0;
	}
	
	@Override
	public void setDirCarpeta(String arg0) throws GestionFicherosException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setFormatoContenido(FormatoVistas arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setMostrarOcultos(boolean arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setOrdenado(TipoOrden arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setSePuedeEjecutar(String arg0, boolean arg1) throws GestionFicherosException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setSePuedeEscribir(String arg0, boolean arg1) throws GestionFicherosException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setSePuedeLeer(String arg0, boolean arg1) throws GestionFicherosException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setUltimaModificacion(String arg0, long arg1) throws GestionFicherosException {
		// TODO Auto-generated method stub
		
	}
}
