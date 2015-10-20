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

import org.primefaces.model.StreamedContent;

import etrs.selene.easypermut.model.entities.DemandePermutation;
import etrs.selene.easypermut.model.sessions.DemandePermutationSession;

/**
 * Servlet implementation class ExportServlet
 */
@WebServlet("/ExportServlet")
public class ExportServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private DemandePermutationSession facadeDemandePermutation;
	
	private StreamedContent file;
	
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
		response.setContentType("text/csv");
		PrintWriter pw = response.getWriter();
		
		List<DemandePermutation> lst = this.facadeDemandePermutation.readAll();
		for (DemandePermutation demandePermutation : lst)
		{
			pw.printf("%s,%s,%s,%s,%s,%s\n", demandePermutation.getUtilisateurCreateur(), demandePermutation.getUtilisateurInteresse(), demandePermutation.getZmr(), demandePermutation.getVille(), demandePermutation.getUnite(), demandePermutation.getPoste());
		}
		pw.close();
		System.out.println("Export effectué");
	}
	
}
