/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conversor;

/**
 *
 * @author jeison
 */
public class Conversor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
 private static final String SIMBOLO_ROMANO[] = 
   { "M", "CM", "D", "CD", "C","XC", "L", "XL", "X", "IX", "V", "IV", "I" };

 /**
  * VALOR_ROMANO_ARABIGO[]: arrreglo que almacena valores arabigos que
  * corresponden a cada símbolo romano
  */
 private static final int VALOR_ROMANO_ARABIGO[] = { 1000, 900, 500, 400,
   100, 90, 50, 40, 10, 9, 5, 4, 1, 0 };

 /**
  * Longitud de los arreglos SIMBOLO_ROMANO[]y VALOR_ROMANO_ARABIGO[]
  */
 private static final int n = 13; 

 
 private static String tomarValorRomano(int cifra) {
  
  if (cifra == 0){
   return "";
  } 
  int i;//indice posición del valor o simbolo romano
  
  /**
   * busco el simbolo que le corresponde
   */
  for (i = 0; i < n; i++){
   if (cifra >= VALOR_ROMANO_ARABIGO[i]){
    break;
   } 
  }
  int v = VALOR_ROMANO_ARABIGO[i];
  String valorRomano = SIMBOLO_ROMANO[i];

  /**
   * Se ve si es un simbolo prohibido y si lo es se busca el siguiente que
   * no lo es
   */
  for (; ((v < cifra) && ((SIMBOLO_ROMANO[i] == "V")
    || (SIMBOLO_ROMANO[i] == "L") || (SIMBOLO_ROMANO[i] == "D") 
    || (SIMBOLO_ROMANO[i].length() > 1))); i++);
  while (v < cifra) {
   v = v + VALOR_ROMANO_ARABIGO[i];
   valorRomano = valorRomano + SIMBOLO_ROMANO[i];
  }
  return valorRomano;
 }

 public static String aRomano(int nArabigo) {
  
  /**
   * Se verifica el rango y si es erroneo retorna vacio
   */
  if (nArabigo > 3999 || nArabigo < 1) {
   return null;
  }

  /**
   * Se convierte el número a Romano
   */
  String resultado = "";// acumulador
  int base = 10; // posicion del número a convertir
  do {
   int cifra = nArabigo % base;
   resultado = tomarValorRomano(cifra) + resultado;
   nArabigo = nArabigo - cifra;
   base = base * 10;
  } while (nArabigo != 0);
  return resultado;
 }

 private static int estadoSiguiente(int edoActual, char caracter) {
  
  /**
   * Se verifica el caracter y se busca su correspondiente indice en el
   * automata Se inicializa a 7 para representar que el caracter es
   * cualquier valor diferente del Alfabeto valido
   */
  int posAlfabeto = 7;
  switch (caracter) {
  case 'I':
   posAlfabeto = 0;
   break;
  case 'V':
   posAlfabeto = 1;
   break;
  case 'X':
   posAlfabeto = 2;
   break;
  case 'L':
   posAlfabeto = 3;
   break;
  case 'C':
   posAlfabeto = 4;
   break;
  case 'D':
   posAlfabeto = 5;
   break;
  case 'M':
   posAlfabeto = 6;
  }
  
  /**
   * sintaxi: Automata de aceptación de la cadena de los números romanos
   * Aceptación>0 y Rechazo = -1, Alfabeto = {I V X L C D M} Estados =
   * 0..19
   */
  int[][] sintaxi = 
               // I   V   X   L   C   D   M, OTRO /*estado*/    
              {{ 1,  4,  5,  8,  9, 12, 14, -1}, /* (0) */
               { 2,  3,  3, -1, -1, -1, -1, -1}, /* (1) */ 
               { 3, -1, -1, -1, -1, -1, -1, -1}, /* (2) */
               {-1, -1, -1, -1, -1, -1, -1, -1}, /* (3) */
               {16, -1, -1, -1, -1, -1, -1, -1}, /* (4) */
               { 1,  4,  6,  7,  7, -1, -1, -1}, /* (5) */ 
               { 1,  4,  7, -1, -1, -1, -1, -1}, /* (6) */  
               { 1,  4, -1, -1, -1, -1, -1, -1}, /* (7) */  
               { 1,  4, 18, -1, -1, -1, -1, -1}, /* (8) */  
               { 1,  4,  5,  8, 10, 11, 19, -1}, /* (9) */  
               { 1,  4,  5,  8, 11, -1, -1, -1}, /* (9) */  
               { 1,  4,  5,  8, -1, -1, -1, -1}, /* (11)*/  
               { 1,  4,  5,  8, 13, -1, -1, -1}, /* (12)*/  
               { 1,  4,  5,  8, 10, -1, -1, -1}, /* (13)*/  
               { 1,  4,  5,  8,  9, 12, 15, -1}, /* (14)*/ 
               { 1,  4,  5,  8,  9, 12, 16, -1}, /* (15)*/  
               { 1,  4,  5,  8,  9, 12, -1, -1}, /* (16)*/  
               { 2, -1, -1, -1, -1, -1, -1, -1}, /* (17)*/  
               { 1,  4,  6, -1, -1, -1, -1, -1}, /* (18)*/ 
               { 1,  4,  5,  8, -1, -1, -1, -1}};/* (19)*/  
 
  /**
   * retorna el estado siguiente en el automata
   */
  return sintaxi[edoActual][posAlfabeto];
 }

 private static boolean validaRomano(String nRomano) {
  int edo = 0; //se inicializa el estado
  
  /**
   * Se recorre el automata y se verifica que es correcta la sintaxi del
   * romano
   */
  for (int i = 0; edo >= 0 && i < nRomano.length(); i++) {
   edo = estadoSiguiente(edo, nRomano.charAt(i));
  }
  
  /**
   * se retorna la condición de aceptación o rechazo
   */
  return ((edo < 0) ? false : true);
 }

 public static int aArabigo(String nRomano) {
  
  /**
   * Se verifica la sintaxi correcta de la cadena que se recive
   */
  if (!validaRomano(nRomano)) {
   return -1;
  }
  int valorArabigo = 0; // acumulador
  int anterior; // indice del simbolo anterior en la cadena
  int actual; // indice del simbolo actual en la cdena
  /**
   * Se toma el valor arbigo actual del final de la cadena que luego sera
   * el valor anterior en la cadena
   */
  for (actual = 0; actual < n; actual++)
   if (SIMBOLO_ROMANO[actual].compareTo(nRomano.charAt(nRomano
     .length() - 1)
     + "") == 0) {
    valorArabigo = VALOR_ROMANO_ARABIGO[actual];
    break;
   }
  anterior = actual;
  
  /**
   * Se convierte el romano a arabigo recorriendo desde el final hacia
   * adelante la cadena, caracer por caracter Sumando su valor si el valor
   * del caracter anterior es mayor o igual al valor del caracter actual y
   * restando si el valor anterior es menor
   */
  for (int i = nRomano.length() - 2, j = 0; i >= 0; i--) {
   for (actual = 0; actual < n; actual++) {
    if (SIMBOLO_ROMANO[actual].compareTo(nRomano.charAt(i) + "") == 0) {
     break;
    }
   }
   if (VALOR_ROMANO_ARABIGO[actual] < VALOR_ROMANO_ARABIGO[anterior]) {
    valorArabigo = valorArabigo - VALOR_ROMANO_ARABIGO[actual];
   } else {
    valorArabigo = valorArabigo + VALOR_ROMANO_ARABIGO[actual];
   }
   anterior = actual;
  }
  return valorArabigo;
 }
}
    }
    

