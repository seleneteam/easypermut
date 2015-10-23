package etrs.selene.easypermut.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import etrs.selene.easypermut.model.entities.ZMR;

public class ZMRTest
{

	@Test
	public void test()
	{
		ZMR zmr = new ZMR();
		zmr.setLibelle("test");
		assertEquals("test", zmr.getLibelle());
	}

}
