package etrs.selene.easypermut.beans;

import java.io.Serializable;
import java.time.LocalDate;
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
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;
import etrs.selene.easypermut.model.entities.DemandePermutation;
import etrs.selene.easypermut.model.entities.Grade;
import etrs.selene.easypermut.model.entities.Utilisateur;
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
    BarChartModel graphUtilisateursParGrade;

    /**
     * Graphique des demandes en fonction des ZMR.
     */
    PieChartModel graphDemandesParZMR;

    /**
     * Graphique des demandes en fonction des Spécialitées.
     */
    PieChartModel graphDemandesParSpe;

    /**
     * Utilisateurs par date d'inscription.
     */
    LineChartModel graphUtilisateursParDate;

    /**
     * Méthode de post-construction. Récupère l'utilisateur flashé et cré les
     * graphiques.
     */
    @PostConstruct
    public void init() {
        this.utilisateur = this.sessionUtilisateur.getUtilisateur();
        this.creerGraphUtilisateursParGrade();
        this.creerGraphDemandesParZMR();
        this.creerGraphDemandesParSpe();
        this.creerGraphUtilisateursParDate();
    }

    /**
     * Retourne le nombre d'utilisateurs.
     *
     * @return le nombre d'utilisateurs.
     */
    public long getNombreUtilisateurs() {
        return this.facadeUtilisateur.count();
    }

    /**
     * Retourne le nombre de demandes de permuations.
     *
     * @return le nombre de demandes.
     */
    public long getNombreDemandes() {
        return this.facadeDemande.count();
    }

    /**
     * Cré le grahique des utilisateurs par grade.
     */
    private void creerGraphUtilisateursParGrade() {
        this.graphUtilisateursParGrade = this.initialiserBarModel();

        this.graphUtilisateursParGrade.setTitle("Nombre d'utilisateurs par grade");
        this.graphUtilisateursParGrade.setLegendPosition("ne");

        Axis xAxis = this.graphUtilisateursParGrade.getAxis(AxisType.X);
        xAxis.setLabel("Grade");

        Axis yAxis = this.graphUtilisateursParGrade.getAxis(AxisType.Y);
        yAxis.setLabel("Utilisateurs");
        yAxis.setMin(0);
        yAxis.setMax(this.facadeUtilisateur.quantiteUtilisateurTotal());
    }

    /**
     * Remplit le grahique des utilisateurs par grade.
     *
     * @return
     */
    private BarChartModel initialiserBarModel() {
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
    private void creerGraphDemandesParZMR() {
        this.graphDemandesParZMR = new PieChartModel();

        for (DemandePermutation demande : this.facadeDemande.readAll()) {
            this.graphDemandesParZMR.set(demande.getZmr().getLibelle(), this.facadeDemande.quantiteDemandesParZMR(demande.getZmr()));
        }

        this.graphDemandesParZMR.setTitle("Nombre de demandes par ZMR");
        this.graphDemandesParZMR.setLegendPosition("e");
        this.graphDemandesParZMR.setShowDataLabels(true);
        this.graphDemandesParZMR.setDiameter(300);
    }

    /**
     * Cré et remplit de graphique des demandes par Specilite.
     */
    private void creerGraphDemandesParSpe() {
        this.graphDemandesParSpe = new PieChartModel();

        for (DemandePermutation demande : this.facadeDemande.readAll()) {
            this.graphDemandesParSpe.set(demande.getUtilisateurCreateur().getSpecialite().getLibelle(), this.facadeDemande.quantiteDemandesParSpe(demande.getUtilisateurCreateur().getSpecialite()));
        }

        this.graphDemandesParSpe.setTitle("Nombre de demandes par spécilités");
        this.graphDemandesParSpe.setLegendPosition("e");
        this.graphDemandesParSpe.setShowDataLabels(true);
        this.graphDemandesParSpe.setDiameter(300);
    }

    private void creerGraphUtilisateursParDate() {
        graphUtilisateursParDate = new LineChartModel();

        Long nbUtilisateursTotal = this.facadeUtilisateur.count();

        LineChartSeries utilisateursTotal = new LineChartSeries();
        utilisateursTotal.setLabel("Utilisateurs");

        for (Utilisateur utilisateur : this.facadeUtilisateur.readAll()) {
            utilisateursTotal.set(utilisateur.getDateInscription(), nbUtilisateursTotal);
        }

        graphUtilisateursParDate.addSeries(utilisateursTotal);

        graphUtilisateursParDate.setTitle("Nombre d'utilisateurs par dates d'inscription");
        graphUtilisateursParDate.setZoom(true);
        graphUtilisateursParDate.getAxis(AxisType.Y).setLabel("Nombre d'utilisateurs");
        DateAxis axis = new DateAxis("Dates d'inscription");
        axis.setTickAngle(-50);
        axis.setMax(LocalDate.now().toString());
        axis.setMax("2015-10-22");
        axis.setTickFormat("%#d %b %y");
        graphUtilisateursParDate.getAxes().put(AxisType.X, axis);
    }

}
