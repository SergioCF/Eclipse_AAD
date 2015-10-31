import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
	
	// Strings de acciones
	static String archivoLeer = null;
	static String archivoCrear = null;
	static String carpetaCrear = null;
	// Se crea el buffer de lectura de bytes
	static byte [] leerBuffer = null;
	// Scanner de consola
	static Scanner in = new Scanner(System.in);
	
	// Funcion Main
	public static void main(String[] args) throws IOException {		
		abrirFichero();
	}

	// Funcion para leer el fichero
	public static void abrirFichero() throws IOException {
		
		// Se pide la ruta del archivo a tratar
		System.out.println("Escribe la ruta completa del archivo a copiar:");
		// Se guarda la ruta escrita por consola como String
		archivoLeer = in.next().toString();	
		
		// Se crean los objetos File y Path para el control de excepciones y lectura de bytes
		File fileEntrada = new File (archivoLeer);
		Path path = Paths.get(archivoLeer);					
		
		// CONTROL DE EXCEPCIONES:
		if(fileEntrada.exists()){ // Si existe
			if(fileEntrada.canRead() && fileEntrada.isFile()){ // Si se puede leer y es archivo
				leerBuffer = Files.readAllBytes(path);
				crearFichero(); // Se llama al siguiente metodo de creacion de fichero
			}else{
				System.err.println("No dispone de permisos");
				abrirFichero(); // Se vuelve al principio llamando a abrirFichero
			}
		}else{	
			System.err.println("El fichero no existe o no se puede leer");
			abrirFichero();	// Se vuelve al principio llamando a abrirFichero	
		} 
	}
	
	// Funcion para crear el fichero
	public static void crearFichero() throws IOException {
		// Se pide la ruta donde copiaremos el fichero, junto con su extension
		System.out.println("Escribe la ruta completa donde quieres copiar el archivo:");
		// Se guarda la ruta escrita por consola como String
		archivoCrear = in.next().toString();	
		
		// Se necesita el fichero y la carpeta de salida para controlar las excepciones
		File fileSalida = new File(archivoCrear);			
		File carpetaSalida = new File (fileSalida.getParent());
		
		// CONTROL DE EXCEPCIONES
		// Se comprueba la carpeta de destino
		if(carpetaSalida.exists()&&carpetaSalida.canRead()&&carpetaSalida.canWrite()){			
			if(fileSalida.exists()){
				System.out.println("El archivo ya existe");
				crearFichero(); // Se vuelve a pedir
			}else{
				fileSalida.createNewFile(); // Se crea el archivo
				System.out.println("Archivo creado");
				copiaBytes(); // Se llama a copiaBytes
			}
		}else{
			System.out.println("Error en el directorio: Comprueba los permisos y si existe");
			crearFichero(); // Se vuelve a pedir
		}
	}
	
	// Funcion para copiar bytes
	public static void copiaBytes(){
		
		FileOutputStream fo = null;		
		Path path = Paths.get(archivoLeer);		
		
		try{
			fo = new FileOutputStream(archivoCrear);
		}catch(FileNotFoundException e){			
			e.printStackTrace();
		}
		
		try{
			byte[] buffer = Files.readAllBytes(path);
			System.out.println("Bytes: "+buffer);
			fo.write(buffer);
			fo.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}