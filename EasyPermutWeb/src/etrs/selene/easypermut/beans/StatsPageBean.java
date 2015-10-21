package etrs.selene.easypermut.beans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
import etrs.selene.easypermut.model.entities.Grade;
import etrs.selene.easypermut.model.entities.Utilisateur;
import etrs.selene.easypermut.model.entities.ZMR;
import etrs.selene.easypermut.model.sessions.DemandePermutationSession;
import etrs.selene.easypermut.model.sessions.GradeSession;
import etrs.selene.easypermut.model.sessions.UtilisateurSession;
import etrs.selene.easypermut.model.sessions.ZMRSession;

@Named
@ViewScoped
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatsPageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * {@link UtilisateurSession}
     */
    @Inject
    UtilisateurSession facadeUtilisateur;

    /**
     * {@link DemandePermutationSession}
     */
    @Inject
    DemandePermutationSession facadeDemande;

    /**
     * {@link GradeSession}
     */
    @Inject
    GradeSession facadeGrade;

    /**
     * {@link ZMRSession}
     */
    @Inject
    ZMRSession facadeZMR;

    /**
     * {@link UtilisateurSessionBean}
     */
    @Inject
    UtilisateurSessionBean sessionUtilisateur;

    /**
     * Utilisateur consultant la page.
     */
    Utilisateur utilisateur;

    /**
     * Graphique des utilisateurs en fonction des grades.
     */
    BarChartModel graphUtilisateurs;

    /**
     * Graphique des demandes en fonction des ZMR.
     */
    PieChartModel graphDemandes;

    /**
     * Méthode de post-construction. Récupère l'utilisateur flashé et cré les
     * graphiques.
     */
    @PostConstruct
    public void init() {
        this.utilisateur = this.sessionUtilisateur.getUtilisateur();
        this.createGraphUtilisateurs();
        this.createGraphDemandes();
    }

    /**
     * Retourne le nombre d'utilisateurs.
     *
     * @return le nombre d'utilisateurs.
     */
    public long nombreUtilisateurs() {
        return this.facadeUtilisateur.count();
    }

    /**
     * Retourne le nombre de demandes de permuations.
     *
     * @return le nombre de demandes.
     */
    public long nombreDemandes() {
        return this.facadeDemande.count();
    }

    /**
     * Cré le grahique des utilisateurs par grade.
     */
    private void createGraphUtilisateurs() {
        this.graphUtilisateurs = this.initBarModel();

        this.graphUtilisateurs.setTitle("Nombre d'utilisateurs par grade");
        this.graphUtilisateurs.setLegendPosition("ne");

        Axis xAxis = this.graphUtilisateurs.getAxis(AxisType.X);
        xAxis.setLabel("Grade");

        Axis yAxis = this.graphUtilisateurs.getAxis(AxisType.Y);
        yAxis.setLabel("Utilisateurs");
        yAxis.setMin(0);
        yAxis.setMax(this.facadeUtilisateur.count());
    }

    /**
     * Remplit le grahique des utilisateurs par grade.
     *
     * @return
     */
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries utilisateurs = new ChartSeries();
        utilisateurs.setLabel("Utilisateurs");

        for (Grade grade : this.facadeGrade.readAll()) {
            utilisateurs.set(grade.getGrade(), this.facadeUtilisateur.quantiteUtilisateurParGrade(grade));
        }
        model.addSeries(utilisateurs);

        return model;

    }

    /**
     * Cré et remplit de graphique des demandes par ZMR.
     */
    private void createGraphDemandes() {
        this.graphDemandes = new PieChartModel();

        for (ZMR zmr : this.facadeZMR.readAll()) {
            this.graphDemandes.set(zmr.getLibelle(), this.facadeDemande.quantiteDemandesParZMR(zmr));
        }

        this.graphDemandes.setTitle("Nombre de demandes par ZMR");
        this.graphDemandes.setLegendPosition("e");
        this.graphDemandes.setFill(true);
        this.graphDemandes.setShowDataLabels(true);
        this.graphDemandes.setDiameter(300);
    }
}
