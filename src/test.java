

class test {

    static double ecarttype (double sommefepsiloncarre , double sommefepsilon, double N) {
        /*System.out.println ("ArgSigma sommefepsiloncarre = "+sommefepsiloncarre);
         System.out.println ("ArgSigma sommefepsilon = "+sommefepsilon);
         System.out.println ("Membre1 = "+(sommefepsiloncarre/(nbsubInt*N)) );
         System.out.println ("Membre2 = "+Math.pow((sommefepsilon/(nbsubInt*N)),2));
         System.out.println ("Variance =" + ((sommefepsiloncarre/(nbsubInt*N)) - Math.pow((sommefepsilon/(nbsubInt*N)),2))); */
        System.out.println ("1er terme : "+(sommefepsiloncarre/N));
        System.out.println ("Moyenne : "+(sommefepsilon/N));
        double variance = (sommefepsiloncarre/N)- Math.pow((sommefepsilon/N),2);
        return Math.pow (variance , 0.5);
        //return Math.sqrt ((sommefepsiloncarre/(N)) - Math.pow((sommefepsilon/(N)),2)) ;
    }





public static void main (String [] args) {
    double resultat = ecarttype ((12*1+18*2*2+26*3*3+10*4*4+6*5*5+2*6*6),(12+18*2+3*26+4*10+5*6+6*2) ,80 );
    System.out.println (""+resultat);
}
    




}