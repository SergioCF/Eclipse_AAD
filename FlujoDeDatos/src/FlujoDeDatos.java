import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FlujoDeDatos{
	
	// Objetos File
	static File file1 = new File ("file1.txt");
	static File file2 = new File ("file2.txt");
	static File file3 = new File ("file3.txt");
	// String a buscar en file3
	static String buscarPalabra = "Body"; // Cambiar para buscar otra palabra	
	// Tipo de busqueda
	static boolean primera_aparicion = true; // Cambiar a False para ultima aparicion

	// Main Funcion (llama a las funciones y controla errores)
	public static void main(String[] args) throws IOException{
		
		// Funcion1: Control excepciones
		if(file1.exists() && file2.exists()){
			if(file1.canRead() && file2.canRead()){
				contentCompare(file1, file2);
			}else{
				System.err.println("Imposible de leer, no dispone de permisos");
			}
		}else{
			System.err.println("Los archivos no existen");
		}
		
		// Funcion2: Control excepciones
		if(file1.exists()){
			if(file1.canRead()){
				if(buscarPalabra == null){
					System.err.println("No existe la palabra");
				}else{ 
					wordSearch(file3, buscarPalabra, primera_aparicion);
				}
			}else{
				System.err.println("Imposible de leer, no dispone de permisos");
			}
		}else{
			System.err.println("Los archivos no existen");
		}
		
	}
	
	// Funcion1 contentCompare ( Se comparan dos archivos )
	static boolean contentCompare(File file1, File file2) throws IOException{		
		
		// String para comparar las lineas
		String str1 = null;
		String str2 = null;
		// Variable para guardar y mostrar la comparacion
		boolean comparacion = true;	
		
		// Objetos FileReader y BufferedReader
		FileReader fr1 = new FileReader(file1);
		FileReader fr2 = new FileReader(file2);
		BufferedReader br1 = new BufferedReader(fr1);
		BufferedReader br2 = new BufferedReader(fr2);
		
		// Lee fichero 2 hasta que fichero 1 devuelve null
		while ((str1=br1.readLine()) != null){
			str2=br2.readLine(); // Se introduce en str2 el contenido de file2
			// Se comparan los "str (Strings) " que tendran el contenido de file1 y file2
			if (!str1.equals(str2)){
				System.out.println("Lineas distintas encontradas:");
				System.out.println("Linea de file1 distinta: "+str1);
				System.out.println("Linea de file2 distinta: "+str2);
				comparacion = false;					
			}			
		}
				
		// Se muestra si son iguales o no mediante True o False
		System.out.print("Son las lineas iguales?: ");
		System.out.println(comparacion);
		
		br1.close();
		br2.close();
		fr1.close();
		fr2.close();
		
		return comparacion;
		
	}

	// Funcion2 wordSearch
	static int wordSearch(File file3, String buscarPalabra, boolean primera_aparicion) throws IOException{
		
		// Palabra encontrada (True/False)
		boolean palabraEncontrada=false;
		// Objeto FileReader y BufferReader
		FileReader fr3 = new FileReader(file3);
		BufferedReader br3 = new BufferedReader(fr3);
		// Strings
		String str1 = null;
		// Contador
		int count = 0;
		
		// Para buscar la primera aparicion:
		if(primera_aparicion == true){
			// Se lee file3 hasta que devuelva null
			while ((str1=br3.readLine()) != null){
				count ++;
				// Si se encuentra la palabra:
				if(str1.contains(buscarPalabra)){;
					palabraEncontrada=true;
				}	
				// Si se encuentra la palabra, se muestra y rompemos el proceso
				if (palabraEncontrada == true){
					System.out.print("La primera aparicion de: " +buscarPalabra +" esta en ->");
					break;
				}								
			}			
		}
		
		// Para buscar la ultima aparicion:
		else if(primera_aparicion == false){
			// Se lee file3 hasta que devuelva null
			while ((str1=br3.readLine()) != null){
				// Si se encuentra la palabra:
				if(str1.contains(buscarPalabra)){;
					palabraEncontrada=true;
				}	
				// Aumentamos en 1 el contador hasta que acabe de encontrar coincidencias
				if (palabraEncontrada == true){
					count ++;
				}								
			}
			// Se muestra el resultado de la ultima aparicion
			System.out.print("La ultima aparicion de " +buscarPalabra +" esta en ->");			
		}
			
		br3.close();
		fr3.close();
		
		// Se muestra la linea de la coincidencia
		System.out.print(" Linea: " + count);
		return count;
	}

}//180