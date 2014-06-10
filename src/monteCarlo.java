
 
 
 
 /**
  * Classe de calcul 
 * @author Aline Goudenhooft
 * @author Paul Rivière
 * @version 1.0
 *
 */
class monteCarlo {
 	
 		//DECLARATION DES VARIABLES GLOBALES
     double h=0.001;
     double nbsub;
     int N = 20000;     //N: nombre de tirages par intervalle
     integrale parent;
     int nbsubInt;
     double resultatIntegrale;
     double sommeEcarttypes;

     
     
     //CONSTRUCTEUR
     public monteCarlo (integrale in) {
         parent=in;
         }
     
     
     //FONCTIONS DE CALCUL
     
     double calculIntegrale () {
         
         //DECLARATIONS
         nbsub = (Math.abs (parent.borneXa - parent.borneXde))/h;
         nbsubInt = (int) nbsub;
         System.out.println ("ENTREE DANS CALCUL INTEGRALE");
         System.out.println ("nbSubInt = "+nbsubInt);
         resultatIntegrale = 0;
         sommeEcarttypes = 0;
         
   

         
         /***Boucle pour chaque intervalle***/
         for (double a = Math.min (parent.borneXde , parent.borneXa) ;
                a < Math.max (parent.borneXa, parent.borneXde) ;
                a = a+h) {
             
             //Initialisation
             double sommefepilonIPartiel=0;
             double sommefepsilonICarrePartiel=0;
             
             
             //Calcul de d et c
             double d = parent.borneYa (a);
             double c = parent.borneYde (a);
             
             
             //Boucle pour chaque nombre aléatoire
             for (int i = 0 ; i<N ; i++) {
                 //Tirage des nombres aléatoires
                 double xi = a + (a+h-a)*Math.random();
                 double yi = c + (d-c)*Math.random();
                 //Calcul du f(epsilonI)
                 double fepsiloni = parent.f (xi, yi);
                 //Somme partielle (pour l'intervalle)
                 sommefepilonIPartiel = sommefepilonIPartiel + fepsiloni;
                 sommefepsilonICarrePartiel = sommefepsilonICarrePartiel + fepsiloni*fepsiloni;
             }
             
             
             //Calcul de l'aire pour l'intervalle considérée
             double aire = (d-c)*(a+h-a)*sommefepilonIPartiel/N;
             double ecarttypePartiel = ecarttype (sommefepsilonICarrePartiel, sommefepilonIPartiel);
             sommeEcarttypes = sommeEcarttypes + ecarttypePartiel;
             resultatIntegrale = resultatIntegrale + aire;
         }
         
         
         return resultatIntegrale;
     }

     double calcul5pc () {
         double resultat = (parent.borneXa-parent.borneXde)*1.96* (sommeEcarttypes/nbsub)/Math.sqrt (N);
         return resultat;
         
     }
     
     double calcul1pc () {
         double resultat = (parent.borneXa-parent.borneXde)*2.57* (sommeEcarttypes/nbsub)/Math.sqrt (N);
         return resultat;
     }
     
     
     
     
     /***FONCTION AUXILIAIRE DE CALCUL ****/
     double ecarttype (double sommefepsiloncarre , double sommefepsilon) {
         double variance = (sommefepsiloncarre/N)- Math.pow((sommefepsilon/N),2);
         return Math.pow (variance , 0.5);
     }
 	
 }
