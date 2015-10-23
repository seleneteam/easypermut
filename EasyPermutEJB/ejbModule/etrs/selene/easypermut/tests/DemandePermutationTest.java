package etrs.selene.easypermut.tests;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import etrs.selene.easypermut.model.entities.DemandePermutation;
import etrs.selene.easypermut.model.entities.Poste;
import etrs.selene.easypermut.model.entities.Unite;
import etrs.selene.easypermut.model.entities.Utilisateur;
import etrs.selene.easypermut.model.entities.Ville;
import etrs.selene.easypermut.model.entities.ZMR;

public class DemandePermutationTest
{
	
	@Test
	public void test()
	{
		DemandePermutation demandePermutation = new DemandePermutation();
		ZMR zmr = new ZMR();
		zmr.setLibelle("nomZMR");
		Ville ville = new Ville();
		ville.setNom("nomVille");
		ville.setZmr(zmr);
		Unite unite = new Unite();
		unite.setLibelle("nomUnite");
		unite.setVille(ville);
		Poste poste = new Poste();
		poste.setLibelle("nomPoste");
		poste.setUnite(unite);
		
		demandePermutation.setPoste(poste);
		demandePermutation.setUnite(unite);
		demandePermutation.setUtilisateurCreateur(new Utilisateur());
		demandePermutation.setVille(ville);
		demandePermutation.setZmr(zmr);
		demandePermutation.setDateCreation(new Date());

		assertEquals(new Date().getTime(), demandePermutation.getDateCreation().getTime());
		assertEquals("nomUnite", demandePermutation.getUnite().getLibelle());
		assertEquals("nomVille", demandePermutation.getPoste().getUnite().getVille().getNom());
	}
	
}
