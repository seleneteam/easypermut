package etrs.selene.easypermut.export;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import etrs.selene.easypermut.model.entities.DemandePermutation;
import etrs.selene.easypermut.model.entities.Utilisateur;
import etrs.selene.easypermut.model.sessions.DemandePermutationSession;
import etrs.selene.easypermut.model.sessions.UtilisateurSession;

/**
 * Servlet permettant d'exporter les demandes concernant l'utilisateur.
 */
@WebServlet("/ExportServlet")
public class ExportServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private DemandePermutationSession facadeDemandePermutation;
	
	@Inject
	private UtilisateurSession facadeUtilisateur;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExportServlet()
	{
		
	}
	
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException
	{
		String utilisateur_id = request.getParameter("id");
		
		if (utilisateur_id == null)
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		else
		{
			Utilisateur utilisateur = this.facadeUtilisateur.read(utilisateur_id);
			if (utilisateur == null)
			{
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
			else
			{
				response.setContentType("text/csv");
				PrintWriter pw = response.getWriter();
				
				List<DemandePermutation> lst = this.facadeDemandePermutation.listeFiltre(utilisateur);
				if (lst.size() == 0)
				{
					System.out.printf("Aucune donnée à exporter pour %s", utilisateur.getNom());
				}
				else
				{
					pw.println("Créateur,ZMR souhaité,ville souhaité,unité souhaité,Poste souhaité,Mail");
					for (DemandePermutation demandePermutation : lst)
					{
						pw.printf("%s,%s,%s,%s,%s\n", demandePermutation.getUtilisateurCreateur(), demandePermutation.getZmr(), demandePermutation.getVille(), demandePermutation.getUnite(), demandePermutation.getPoste());
					}
					pw.close();
					System.out.printf("Export effectué pour %s", utilisateur.getNom());
				}
				
			}
		}
		
	}
	
}
