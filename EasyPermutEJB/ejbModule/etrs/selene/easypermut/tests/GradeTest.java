package etrs.selene.easypermut.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import etrs.selene.easypermut.model.entities.Grade;

public class GradeTest
{
	
	@Test
	public void test()
	{
		Grade grade = new Grade();
		grade.setGrade("sergent");
		
		assertEquals("sergent", grade.getGrade());
	}
	
}
