package etrs.selene.easypermut.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import etrs.selene.easypermut.model.entities.Unite;
import etrs.selene.easypermut.model.entities.Ville;
import etrs.selene.easypermut.model.entities.ZMR;

public class UniteTest
{
	
	@Test
	public void test()
	{
		ZMR zmr = new ZMR();
		Ville ville = new Ville();
		Unite unite = new Unite();
		zmr.setLibelle("nomZmr");
		ville.setNom("nomVille");
		ville.setZmr(zmr);
		unite.setLibelle("nomUnite");
		unite.setVille(ville);

		assertEquals("nomUnite", unite.getLibelle());
		assertEquals("nomVille", unite.getVille().getNom());
		assertEquals("nomZmr", unite.getVille().getZmr().getLibelle());
	}
	
}
