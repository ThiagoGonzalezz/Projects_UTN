int funcion(int avion);    
                              $da->$$

void funcion(int marcos); //DOBLE DECLARACIÓN

int funcion(int algo){ 
   int contador;
   while(contador < 0){
      contador ++;
   }
   return contador;
}

#fsaf#ff#
int funcion(int algo_){   //DOBLE DEFINICIÓN
    return algo_;
}

void insertar(char i1, char i2); 

char insertar(char c1, char c2){  //DEFINIDA CON OTRO TIPO
   return 'c';
}

int sumar()   //ERROR SINTACTICO
  return;};

double calcular(int calc); 

double calcular(int calc1, int calc2){  //DEFINIDA CON MÁS PARÁMETROS
    return calc1+calc2;
}

calcular(765);  //BIEN INVOCADA

int auxCalc = 7;     

calcular(auxCalc, 877); //CANTIDAD DE PARAMETROS ERRONEA

calcular(); //CANTIDAD DE PARAMETROS ERRONEA

calcular(noDecla); //PARAMETRO NO DECLARADO

calcular(auxCalc); //BIEN INVOCADA

calcular("string"); //PARAMETRO DE DISTINTO TIPO

multiplicar(7,8); //FUNCION NO DECLARADA PREVIAMENTE

int jorge(int arreglo[1],float *bizcocho){
   int  *castillo = a[1]+b;
   return castillo;
}

int vaca;

int p=1,o[10], *r = &vaca;

int restar(int as, int ad){
   int ta = as+ad;
   return ta;
}

int testSentencias() { 
   int acumulador = 0;
   if(1){
      acumulador++;
   }

   while(1){
      acumulador++;
   }
   
   int index;

   for(index = 0; index < 10 ; index++){
      acumulador++;
   }

   return 1;
}

int 700 = vaca; //NO CUMPLE RESTRICCIONES SINTACTICAS

int main(){
   char caracter1='a', caracter2 = 'b';
   int variable_main = 0, variable_main_b, variable_main_c = 1,*puntero,h[5];
   int f = variable_main + variable_main_b;
   float l = 1.45;
   
   char amo = int hola; //error sintatico

   variable_main+l;//Error: operación entre int y float
   asd+variable_main;//Error: La variable no fue declarada previamente.
   
   int variable_main_c = 8; // Error: doble declaracion
   float variable_main_c = 1.45;//Error:la variable ya fue definida con otro tipo de dato
   int *antartida;
   float elefante = "e"; // Error: tipos incompatibles
   double amanecer;
   int amanecer;
   y´ddsg´sdf // Error: lexico
   asdfr + caracter1;
}

   int vaca, serpiente = 3, zapallo;
   int river, boca, casla
   int sdf;