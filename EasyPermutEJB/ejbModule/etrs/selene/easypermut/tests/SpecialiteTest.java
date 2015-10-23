package etrs.selene.easypermut.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import etrs.selene.easypermut.model.entities.Specialite;

public class SpecialiteTest
{
	
	@Test
	public void test()
	{
		Specialite specialite = new Specialite();
		specialite.setLibelle("nomSpecialite");
		specialite.setNumeroSpe("8230");

		assertEquals("nomSpecialite", specialite.getLibelle());
		assertEquals("8230", specialite.getNumeroSpe());
	}
}
