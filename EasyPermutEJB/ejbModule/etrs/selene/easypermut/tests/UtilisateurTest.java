package etrs.selene.easypermut.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import etrs.selene.easypermut.model.entities.Grade;
import etrs.selene.easypermut.model.entities.Poste;
import etrs.selene.easypermut.model.entities.Specialite;
import etrs.selene.easypermut.model.entities.Utilisateur;

public class UtilisateurTest
{
	
	@Test
	public void test()
	{
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setEstInteresse(false);
		utilisateur.setEstValide(true);
		utilisateur.setGrade(new Grade());
		utilisateur.setIdentifiantAnudef("identifiant");
		utilisateur.setInformationsValide(true);
		utilisateur.setMail("random@mail.fr");
		utilisateur.setNia("AL12345");
		utilisateur.setNom("bob");
		utilisateur.setPoste(new Poste());
		utilisateur.setPrenom("dupont");
		utilisateur.setSpecialite(new Specialite());

		assertEquals("identifiant", utilisateur.getIdentifiantAnudef());
		assertEquals(true, utilisateur.getInformationsValide());
		assertEquals("dupont", utilisateur.getPrenom());
	}
	
}
