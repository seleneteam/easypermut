package etrs.selene.easypermut.export;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import net.entetrs.commons.jsf.JsfUtils;
import etrs.selene.easypermut.model.entities.DemandePermutation;

public final class CsvWriter
{
	public static void genererCsv(final List<DemandePermutation> setDemandePermutations)
	{
		try (PrintWriter pw = new PrintWriter("#{resource['docs:export.csv']}"))
		{
			pw.println("Createur,Interessé,zmr,ville,unité,poste");
			for (DemandePermutation demandePermutation : setDemandePermutations)
			{
				pw.printf("%s,%s,%s,%s,%s,%s\n", demandePermutation.getUtilisateurCreateur(), demandePermutation.getUtilisateurInteresse(), demandePermutation.getZmr(), demandePermutation.getVille(), demandePermutation.getUnite(), demandePermutation.getPoste());
				System.out.printf("Export de %s", demandePermutation.getId());
			}
			JsfUtils.sendMessage("Export effectué !");
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
