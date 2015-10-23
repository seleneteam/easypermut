package etrs.selene.easypermut.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import etrs.selene.easypermut.model.entities.Ville;
import etrs.selene.easypermut.model.entities.ZMR;

public class VilleTest
{
	
	@Test
	public void test()
	{
		ZMR zmr = new ZMR();
		Ville ville = new Ville();
		zmr.setLibelle("nomZmr");
		ville.setNom("nomVille");
		ville.setZmr(zmr);
		
		assertEquals("nomVille", ville.getNom());
		assertEquals("nomZmr", ville.getZmr().getLibelle());
	}
	
}
