#include <iostream>
#include <stdlib.h>
#include <time.h>
#include <cstring>
using namespace std;

//punto 7
struct jugada{
	int idJugada;
	char nombre[20];
	int fecha;
	int puntaje;
};
//punto 7

struct nodo{
    jugada info;
    nodo* sgte;
};

int tirar(int [5]);

void jugar(int , int [][11], int& , int []);

nodo* insertarOrdenado(nodo*& , jugada );

int main()
{	
    //punto 7
    int fecha;
    cout<<"+----------------------------------+";
    cout<<endl<<"|Bienvenido al juego de la Generala|"<<endl;
    cout<<"+----------------------------------+";
    cout<<endl<<endl<<"Ingrese la fecha (AAAAMMDD): ";
    cin>>fecha;
    //punto 7
    int cantJugadores, i=0, j, r, gano=0, mayor, ganador=0;
    cout<<endl<<"Ingrese cantidad de jugadores (3 a 5): ";
    cin>> cantJugadores;
    ///////////////////////PEDIR NOMBRES DE LOS JUGADORES ACA
    
    srand(time(NULL));

    int resultados[cantJugadores][11] = {0, 0};  // jugadores filas, jugadas columnas
    int puntaje[cantJugadores] = {0};
    
    while(gano!=1 && i<11)
    {
        cout<<endl<<"-----------------"<<endl<<"Vuelta numero: "<<i+1<<endl<<"-----------------"<<endl;
        for(j=0;j<cantJugadores;j++)
        {
            jugar(j, resultados, gano, puntaje);
        }
        i++;
    }

    cout<<endl<<"-----RESULTADOS-----"<<endl;

    for(int k=0;k<cantJugadores;k++) // Mostrar resultados (TEST)
    {
        for(int m=0;m<11;m++)
        {
            cout<<resultados[k][m]<<" ";
        }
        cout<<endl;
    }
    
    cout<<endl<<"-----PUNTAJES FINALES-----"<<endl;
    
    for(j=0;j<cantJugadores;j++)
	{
		
     for(r=0;r<11;r++)
	 {
     puntaje[j]+=resultados[j][r];
     } 
     cout<<"Cantidad de puntos de el jugador "<<j+1<<": "<<puntaje[j]<<endl;
     
     if(puntaje[j]>mayor){
      ganador=j;
      mayor=puntaje[j];
	 }
	 
	}   
    int proxId=1;
    jugada ultima, actual;
    char nombre[20];

    FILE* f = fopen("Historial","rb+");
    if(f!=NULL){
        fseek(f,-1*sizeof(jugada),SEEK_END);
        fread(&ultima,sizeof(jugada),1,f);
        proxId = ultima.idJugada+1;
        cout<<ultima.idJugada<< "\t" << ultima.nombre << "\t" << ultima.puntaje<<endl;
    }else{
        f = fopen("Historial","wb+");
    }
    fseek(f,0,SEEK_END);
	for(i=0;i<cantJugadores;i++)
	{
        actual.idJugada = proxId;
        actual.fecha = fecha;
        actual.puntaje = puntaje[i];
        cout << "Ingrese el nombre del jugador: " << i+1 << " ";
        cin >> nombre;          
        strcpy(actual.nombre, nombre);
        fwrite(&actual,sizeof(jugada),1,f);
        proxId++;
    }
    fseek(f,0,SEEK_SET);
    nodo* lista=NULL;
    while(fread(&actual,sizeof(actual),1,f)){
        insertarOrdenado(lista,actual);
    }
    nodo* aux = lista;
    for(i=0;i<3;i++){
        cout << "Puntaje en la posicion " << i+1 << ": ";
        cout << aux->info.nombre << " " << aux->info.puntaje << " " << aux->info.fecha%100 << "/" ;
        cout << aux->info.fecha%10000/100 << "/" <<aux->info.fecha/10000 << endl;
        aux = aux->sgte;
    }

    fclose(f);


    return 0;
}

int tirar(int juego[5])
{
    int i;
    for (i = 0;i < 5;i++)
    {
        juego[i] = 1 + rand() % (7 - 1);
        cout << " " << juego[i];
    };
    cout << endl;

return (1 + rand() % (4 - 1));
};

void jugar(int jugador, int partida[][11], int& gano, int puntaje[])
{
  int juego[5];
  int cantTiradas, i, j=0, max=0;
  
  cout<<endl<<"Tirada del jugador "<<jugador+1<<":"<<endl;
  cantTiradas=tirar(juego);
  cout<<"Cantidad de tiradas: "<<cantTiradas<<endl;
  
  int cant[6]= {0};
  for(i=0;i<5;i++)
  {
    cant[juego[i] -1]++;
  }

  int posMatriz=-1;

  if ((cant[0] == 5 || cant[1] == 5 || cant[2] == 5 || cant[3] == 5 || cant[4] == 5 || cant[5] == 5)&&(partida[jugador][9]==0||partida[jugador][10]==0))
    {
	    posMatriz=9;
    }
	else if (((cant[0] == 4 || cant[1] == 4 || cant[2] == 4 || cant[3] == 4 || cant[4] == 4 || cant[5] == 4))&&partida[jugador][8]==0)
    {
	    posMatriz=8;
    }
    else if ((cant[0] == 3 || cant[1] == 3 || cant[2] == 3 || cant[3] == 3 || cant[4] == 3 || cant[5] == 3) && partida[jugador][7]==0  
	        && (cant[0] == 2 || cant[1] == 2 || cant[2] == 2 || cant[3] == 2 || cant[4] == 2 || cant[5] == 2)) 
    {
		posMatriz=7;
    }
    else if ((cant[2] == 1 && cant[3] == 1 && cant[4] == 1 && ((cant[0] == 1 && cant[1] == 1) || (cant[1] == 1 && cant[5] == 1) || (cant[0] == 1 && cant[5] == 1)))&&partida[jugador][6]==0)
    {
	    posMatriz=6;
    }
    else
    {
    for(i=5;i>=0;i--)
      {
        if(cant[i]==1 && partida[jugador][i]==0)
        {
		  {
          posMatriz=i;
		  }
        }
      }
      for(i=0;i<6;i++)
      {
        if(cant[i]>=2)
        {
          if(((i+1)*cant[i])>max && partida[jugador][i]==0) //para elegir la jugada que da mas puntaje, y no la que sea de dados mas altos
		  {
		  cout<<"Hay "<<cant[i]<<" de el num "<<i+1<<endl;
          posMatriz=i;
          j=i;
          max=((i+1)*cant[i]);
		  }
        }
      }
    }

    if(posMatriz!=-1) //si hubo alguna jugada, sea una categoría o una jugada en si
    {

        
        if(cantTiradas==1)
        {	
            if(posMatriz==6)
            {
                cout << "Jugada: Escalera servida (+25)"<<endl;
                partida[jugador][posMatriz]=25;
            }
            else if(posMatriz==7)
            {
                cout << "Jugada: Full servido (+35)"<<endl;
                partida[jugador][posMatriz]=35;	
            }
            else if(posMatriz==8)
            {
                cout << "Jugada: Poker servido (+45)"<<endl;
                partida[jugador][posMatriz]=45;
            }
            else if(posMatriz==9){
                cout<<"Jugada: Generala servida, el jugador "<<jugador<<" gana la partida."<<endl;
                gano=1;
        		 for(int m=0;m<11;m++)
        		 {
            	  partida[jugador][m]=0;
        		 }	 
        		 partida[jugador][posMatriz]=370;
            }
        }
        
        else if(cantTiradas>1)
        {
            if(posMatriz==6)
            {
                cout << "Jugada: Escalera armada (+20)"<<endl;
                partida[jugador][posMatriz]=20;
            }
            else if(posMatriz==7)
            {
                cout << "Jugada: Full armada (+30)"<<endl;
                partida[jugador][posMatriz]=30;	
            }
            else if(posMatriz==8)
            {
                cout << "Jugada: Poker armada (+40)"<<endl;
                partida[jugador][posMatriz]=40;
            }
            else if(partida[jugador][9]!=0 && partida[jugador][10]==0)
            {
             if(posMatriz==9)
             { 
                cout<<"Jugada: Generala doble (+100)"<<endl;
                partida[jugador][10]=100;
             }
            }  
            else if(posMatriz==9 && partida[jugador][9]==0)
            {
                cout << "Jugada: Generala armada (+50)"<<endl;
                partida[jugador][posMatriz]=50;
            }
        }
        
        if(posMatriz==5 || posMatriz==4 || posMatriz==3 || posMatriz==2 || posMatriz==1 || posMatriz==0)
            {
                partida[jugador][posMatriz]=(posMatriz+1)*cant[posMatriz];
                cout<<"Jugada: Categoria "<<posMatriz+1<<" (+"<<(posMatriz+1)*cant[posMatriz]<<")"<<endl;
            }
            
    }
	 else
	 {
    	 cout<<"Jugada tachada"<<endl;
	 }
	 
	 
}

nodo* insertarOrdenado(nodo*& lista, jugada x){
    nodo* p = new nodo();
    p->info = x;
    if(lista==NULL||x.puntaje > lista->info.puntaje){
        p->sgte=lista;
        lista=p;
    }
    else{
        nodo* q = lista;
        while(q->sgte!=NULL && x.puntaje < q->sgte->info.puntaje){
            q=q->sgte;
        }
        p->sgte=q->sgte;
        q->sgte=p;
    }
    return p;
}
