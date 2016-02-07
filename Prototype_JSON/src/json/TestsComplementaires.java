package json;

public class TestsComplementaires {
	/**
	 * Displays the results of a bunch of tests where the input is not correct.
	 */
	public static void main(String[] args) {
		System.out.println("TESTS DE FROMJSON()");
		
		
		
		System.out.println("\n\n------------\n\n");
		
		
		
		System.out.println("Avec un fichier introuvable :");
		System.out.println(Test.testFromJson("test/fichierIntrouvable.json"));
		System.out.println("Observation : Le test s'arrête avant d'atteindre le gson.");
		
		
		
		System.out.println("\n\n------------\n\n");
		
		
		
		System.out.println("Avec un fichier vide :");
		System.out.println(Test.testFromJson("test/fichierVide.json"));
		System.out.println("Observation : gson.fromJson() renvoie null.");
		
		
		
		System.out.println("\n\n------------\n\n");
		
		
		
		System.out.println("Avec un fichier qui ne contient pas tous les champs");
		System.out.println(Test.testFromJson("test/fichierIncomplet.json"));
		System.out.println("Observation : les champs non renseignés sont initialisés à leur valeur\n"
				+ "par défaut (0 pour les nombres, false pour les boolean, null pour les objets, ...)");
		System.out.println("Attention aux objets initialisés à null. Par exemple, il y aurait\n"
				+ "NullPointerException si j'essayais de créer un objet Livraison à cette étape.");
		
		
		
		System.out.println("\n\n------------\n\n");
		
		
		
		System.out.println("Avec un fichier contenant des champs qui n'apparaissent pas dans la classe à parser");
		System.out.println(Test.testFromJson("test/fichierTropComplet.json"));
		System.out.println("Observation : les champs renseignés non-présents dans la classe sont ignorés.");
		
		
		
		System.out.println("\n\n------------\n\n");
		
		
		
		System.out.println("Selon les deux tests précédents, on conclut que si l'on essaie de parser\n"
				+ "un objet d'après une json string qui correspond à une classe différente, nous\n"
				+ "avons un résultat, mais il est inexacte : seuls les champs avec le même nom\n"
				+ "sont initialisés. Il faut faire attention à ça !");
		
		
		
		System.out.println("\n\n------------\n\n");
		
		
		
		System.out.println("Avec un fichier qui a les bons champs, mais les mauvais types de données dans les champs");
		try {
			System.out.println(Test.testFromJson("test/fichierAvecMauvaisTypes.json"));
		} catch(com.google.gson.JsonSyntaxException e) {
			//e.printStackTrace();
			System.out.println("Observation : le test lance une exception.");
		}
		
		
		
		System.out.println("\n\n------------\n\n");
		
		
		
		System.out.println("Avec un fichier mal formaté :");
		try {
			System.out.println(Test.testFromJson("test/fichierMalFormate.json"));
		} catch(com.google.gson.JsonSyntaxException e) {
			//e.printStackTrace();
			System.out.println("Observation : le test lance une exception.");
		}
	}
}
