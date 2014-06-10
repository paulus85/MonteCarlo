import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

	/**
	 * Classe principale de l'application
	 * Gestion des éléments graphiques et de l'interaction utilisateur
	 * @author Aline Goudenhooft
	 * @author Paul Rivière
	 * @version 1.0
	 * 
	 */
class integrale extends Frame implements WindowListener, ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	// déclaration globale
	Choice fonctionC , borneYdeC , borneYaC;
	TextField borneXdeTF , borneXaTF;
	Button integrerB;
	Label fonctionL , borneXdeL , borneXaL , borneYdeL , borneYaL ;
    monteCarlo calculMC;
    double borneXa , borneXde ;
    TextField resultatIntegraleTF , resultat1pcTF , resultat5pcTF;
    Label resultatIntegraleL , resultat5pcL , resultat1pcL ;
    int fonctionIntegrerI , borneYdeI , borneYaI;
    
	
	// Constructeur de la classe
	public integrale () {
		setBounds (100,100,495,350);
		setTitle ("Intégrale de Monte-Carlo");
		setResizable(false);
		setVisible (true);
		setLayout (null);
		
		int largeur = (int) (getBounds().width -30)/4;
		
		// Menu déroulant fonction a integrer 
		fonctionC = new Choice();
		fonctionC.add("x^2+y^2");
		fonctionC.add("sqrt(x^2-y)");
		fonctionC.add("Arctan (x+y)");
        fonctionC.add("y/(1-2x)");
		fonctionC.add("ln (xy)");
		fonctionC.add("exp (3x*sqrt(y))");
		fonctionC.setBounds (2*largeur+25,40,2*largeur,30);
		add(fonctionC);
		
		fonctionL = new Label();
		fonctionL.setText ("Fonction à intégrer ");
		fonctionL.setBounds (10,45,2*largeur,20);
		add (fonctionL);
		
		// Bornes numériques de x 
		borneXdeL = new Label();
		borneXdeL.setText ("Bornes numériques : x varie de");
		borneXdeL.setBounds (10,90,2*largeur,20);
		add(borneXdeL);
		
		borneXdeTF = new TextField ();
		borneXdeTF.setBounds (250,90,80,20);
		add(borneXdeTF);
		
		borneXaTF = new TextField ();
		borneXaTF.setBounds (395,90,80,20);
		add(borneXaTF);
		
		borneXaL = new Label();
		borneXaL.setText ("à");
		borneXaL.setBounds (355,90,12,20);
		add(borneXaL);
		
		// Bornes fonctionnelles de y
		borneYdeL = new Label();
		borneYdeL.setText("Bornes fonctionnelles : y varie de");
		borneYdeL.setBounds (10,125,2*largeur,20);
		add (borneYdeL);
		
		borneYdeC = new Choice();
		borneYdeC.setBounds (245,120,90,30);
		borneYdeC.add ("x^2");
		borneYdeC.add ("cos(x)");
        borneYdeC.add ("sin(x)");
        borneYdeC.add ("x^3");
        borneYdeC.add ("sqrt(x)");
        borneYdeC.add ("1-x");
		add (borneYdeC);
		
		borneYaC = new Choice();
		borneYaC.setBounds (390,120,90,30);
		borneYaC.add ("x^2");
		borneYaC.add ("cos(x)");
        borneYaC.add ("sin(x)");
        borneYaC.add ("x^3");
        borneYaC.add ("sqrt(x)");
        borneYaC.add ("1-x");
		add(borneYaC);
		
		borneYaL = new Label();
		borneYaL.setText("à");
		borneYaL.setBounds (355,125,12,20);
		add (borneYaL);
		
		// Bouton Intégrer
		integrerB = new Button("Intégrer");
		integrerB.setBounds (getBounds().width/4,200,getBounds().width /2 , 30);
		integrerB.addActionListener (this);
		add (integrerB);
        
        /*****RESULTATS****/
        // GESTION GRAPHIQUE
        
        resultatIntegraleL = new Label("Résultat de l'intégrale :");
        resultatIntegraleL.setBounds (10, 250 , 2*largeur, 20);
        resultatIntegraleL.setVisible(false);
        add(resultatIntegraleL);
        
        resultatIntegraleTF = new TextField() ;
        resultatIntegraleTF.setBounds (2*largeur+10,250,2*largeur,20);
        resultatIntegraleTF.setEnabled(false);
        resultatIntegraleTF.setText (" ");
        resultatIntegraleTF.setVisible(false);
        add(resultatIntegraleTF);
        
        resultat5pcL = new Label("Erreur à 5% :");
        resultat5pcL.setBounds (10, 280 , 2*largeur, 20);
        resultat5pcL.setVisible (false);
        add(resultat5pcL);
        
        resultat5pcTF = new TextField() ;
        resultat5pcTF.setBounds (2*largeur+10,280,2*largeur,20);
        resultat5pcTF.setEnabled(false);
        resultat5pcTF.setVisible(false);
        add(resultat5pcTF);
        
        resultat1pcL = new Label("Erreur à 1% :");
        resultat1pcL.setBounds (10, 310 , 2*largeur, 20);
        resultat1pcL.setVisible(false);
        add(resultat1pcL);
        
        resultat1pcTF = new TextField() ;
        resultat1pcTF.setBounds (2*largeur+10,310,2*largeur,20);
        resultat1pcTF.setEnabled(false);
        resultat1pcTF.setVisible(false);
        add(resultat1pcTF);
        
        
        //Construction de l'objet calculMC
        calculMC = new monteCarlo(this);

		
		addWindowListener (this);
		
	}
	
	public static void main (String [] args) {
		integrale window = new integrale ();
	}
    
    /*
    //TEMPORAIRE POUR TEST : cacul de pi
    double f (double x , double y) {
        return 4*Math.sqrt(1-x*x);
    }
    
    double borneYa (double x) {
        return 1;
    }
    
    double borneYde (double x) {
        return 0;
    }*/
    
    
    /******* FONCTIONS AFFICHAGE & CALCUL ***********/
    void clicBouton() {
        
        //AFFICHAGE DES COMPOSANTS
        resultatIntegraleL.setVisible(true);
        resultatIntegraleTF.setVisible(true);
        resultat1pcL.setVisible(true);
        resultat1pcTF.setVisible(true);
        resultat5pcL.setVisible(true);
        resultat5pcTF.setVisible(true);
        
        //DEFINITION DES INDEX POUR LES MENUS DEROULANTS
        fonctionIntegrerI = fonctionC.getSelectedIndex();
        borneYdeI = borneYdeC.getSelectedIndex();
        borneYaI = borneYaC.getSelectedIndex();
        System.out.println ("Index fonction à intégrer : "+fonctionIntegrerI);
        System.out.println ("Index borne Y de : "+borneYdeI);
        System.out.println ("Index borne Y a: "+borneYaI);

        
         
        //STOCKAGE DONNEES :
                
        rentreeDonneesBorneXde ();
        rentreeDonneesBorneXa ();
        System.out.println (borneXde);
        System.out.println (borneXa);
        
        //On demande les calculs
        double resultatIntegrale = calculMC.calculIntegrale();
        double resultat5pc = calculMC.calcul5pc();
        double resultat1pc = calculMC.calcul1pc();
        
        if (!(Double.isNaN(resultatIntegrale))) {
            resultatIntegraleTF.setText(""+resultatIntegrale);
        } else {
            resultatIntegraleTF.setText("Résultat non réel");
        }
        
        if (!(Double.isNaN(resultat5pc))) {
            resultat5pcTF.setText(""+resultat5pc);
        } else {
            resultat5pcTF.setText("Résultat non réel");
        }
        
        if (!(Double.isNaN(resultat1pc))) {
            resultat1pcTF.setText(""+resultat1pc);
        } else {
            resultat1pcTF.setText("Résultat non réel");
        }
        
    }
    
   //***************FONCTIONS AUXILIAIRES ********************
    void rentreeDonneesBorneXde () {
        try { borneXde = Double.valueOf(borneXdeTF.getText()).doubleValue();
        } catch (NumberFormatException e) {
            System.out.println (e.toString());
            JOptionPane.showMessageDialog (this, "Attention : la 1ère borne de x doit être un nombre réel.", "Erreur", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    void rentreeDonneesBorneXa () {
        try { borneXa = Double.valueOf(borneXaTF.getText()).doubleValue();
        } catch (NumberFormatException e) {
            System.out.println (e.toString());
            JOptionPane.showMessageDialog (this, "Attention : la 2ème borne de x doit être un nombre réel.", "Erreur", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    double f (double x, double y) {
        switch (fonctionIntegrerI) {
            case 0 : return Math.pow (x,2.0) + Math.pow(y,2.0);
            case 1 : return Math.sqrt(Math.pow(x,2) - y);
            case 2 : return Math.atan(x+y);
            case 3 : return y/(1-2*x);
            case 4 : return Math.log(x*y);
            case 5 : return Math.exp(3*x*Math.sqrt(y));
            default : return Math.pow (x,2.0) + Math.pow(y,2.0);
        }
    }
    //Pas la peine de mettre des breaks, ils ne seront jamais atteints
    
    double borneYde (double x) {
        switch (borneYdeI) {
            case 0 : return Math.pow(x,2);
            case 1 : return Math.cos(x);
            case 2 : return Math.sin(x);
            case 3 : return Math.pow(x,3);
            case 4 : return Math.sqrt(x);
            case 5 : return 1-x;
            default : return Math.pow(x,2);
        }
    }
    
    double borneYa (double x) {
        switch (borneYaI) {
            case 0 : return Math.pow(x,2);
            case 1 : return Math.cos(x);
            case 2 : return Math.sin(x);
            case 3 : return Math.pow(x,3);
            case 4 : return Math.sqrt(x);
            case 5 : return 1-x;
            default : return Math.pow(x,2);
        }
    }
	
	//***************EVENEMENTS BOUTON ********************
	
	public void actionPerformed (ActionEvent e) {
		if (e.getSource() == integrerB) {
			System.out.println ("Pression sur Integrer");
            clicBouton();
		}	
	}
	
	//****************EVENEMENTS FENETRE ******************
	
	public void windowClosing (WindowEvent e) {
		System.out.println ("windowClosing");
		System.exit(0);
	}
	public void windowClosed (WindowEvent e) {
			System.out.println ("WindowClosed");
	}
	public void windowActivated (WindowEvent e) {
			System.out.println ("WindowActivated");
	}
	public void windowDeactivated (WindowEvent e) {
			System.out.println ("WindowDeactivated");
	}
	public void windowDeiconified (WindowEvent e) {
			System.out.println ("WindowDeiconified");
	}
	public void windowIconified (WindowEvent e) {
			System.out.println ("WindowIconified");
	}
	public void windowOpened (WindowEvent e) {
			System.out.println ("WindowOpened");
	}

}