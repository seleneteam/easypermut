package etrs.selene.easypermut.export;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
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

		if (utilisateur_id != null)
		{
			Utilisateur utilisateur = this.facadeUtilisateur.read(utilisateur_id);
			
			response.setContentType("text/csv");
			response.addHeader("Content-Disposition", "attachment; filename=export.csv");
			PrintWriter pw = response.getWriter();
			
			List<DemandePermutation> lst = this.facadeDemandePermutation.listeFiltre(utilisateur);
			pw.println("Createur,ZMR souhaite,Ville souhaite,Unite souhaite,Poste souhaite,Mail");
			for (DemandePermutation demandePermutation : lst)
			{
				pw.printf("%s,%s,%s,%s,%s,%s\n", demandePermutation.getUtilisateurCreateur(), demandePermutation.getZmr(), demandePermutation.getVille(), demandePermutation.getUnite(), demandePermutation.getPoste(), demandePermutation.getUtilisateurCreateur().getMail());
			}
			pw.close();
			System.out.println("Export effectué");
		}
		else
		{
			RequestDispatcher rd = request.getRequestDispatcher("connexion.xhtml");
			rd.forward(request, response);
		}
		
	}
	
}
