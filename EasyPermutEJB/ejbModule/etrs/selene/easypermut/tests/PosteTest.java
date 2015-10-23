package etrs.selene.easypermut.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import etrs.selene.easypermut.model.entities.Poste;
import etrs.selene.easypermut.model.entities.Unite;
import etrs.selene.easypermut.model.entities.Ville;
import etrs.selene.easypermut.model.entities.ZMR;

public class PosteTest
{
	
	@Test
	public void test()
	{
		ZMR zmr = new ZMR();
		Ville ville = new Ville();
		Unite unite = new Unite();
		Poste poste = new Poste();
		zmr.setLibelle("nomZmr");
		ville.setNom("nomVille");
		ville.setZmr(zmr);
		unite.setLibelle("nomUnite");
		unite.setVille(ville);
		poste.setLibelle("nomPoste");
		poste.setUnite(unite);

		assertEquals("nomPoste", poste.getLibelle());
		assertEquals("nomUnite", poste.getUnite().getLibelle());
		assertEquals("nomVille", poste.getUnite().getVille().getNom());
		assertEquals("nomZmr", poste.getUnite().getVille().getZmr().getLibelle());
	}
	
}
