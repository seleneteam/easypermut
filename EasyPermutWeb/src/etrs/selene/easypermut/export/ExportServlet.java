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

/**
 * Servlet permettant d'exporter les demandes concernant l'utilisateur.
 */
@WebServlet("/ExportServlet")
public class ExportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    private DemandePermutationSession facadeDemandePermutation;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExportServlet() {

    }

    /**
     * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        String utilisateur_id = request.getParameter("id");

        if (utilisateur_id != null) {

            response.setContentType("text/csv");
            response.addHeader("Content-Disposition", "attachment; filename=export.csv");
            PrintWriter pw = response.getWriter();

            List<DemandePermutation> lst = this.facadeDemandePermutation.readAll();
            pw.println("Grade,Createur,Specialite,ZMR,Ville,Unite,Poste,ZMR souhaite,Ville souhaite,Unite souhaite,Poste souhaite,Mail");
            for (DemandePermutation demandePermutation : lst) {
                Utilisateur utilisateur = demandePermutation.getUtilisateurCreateur();
                pw.printf("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n", utilisateur.getGrade(), utilisateur, utilisateur.getSpecialite(), utilisateur.getPoste().getUnite().getVille().getZmr(), utilisateur.getPoste().getUnite().getVille(), utilisateur.getPoste().getUnite(), utilisateur.getPoste(), demandePermutation.getZmr(), demandePermutation.getVille(), demandePermutation.getUnite(), demandePermutation.getPoste(), utilisateur.getMail());
            }
            pw.close();
            System.out.println("Export effectué");
        } else {
            RequestDispatcher rd = request.getRequestDispatcher("connexion.xhtml");
            rd.forward(request, response);
        }

    }

}
