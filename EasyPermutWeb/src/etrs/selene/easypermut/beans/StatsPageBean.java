package etrs.selene.easypermut.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
     * Graphique des demandes en fonction des Spécialités.
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
        this.creerGraphUtilisateursParMois();
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
     * Retourne le nombre de demandes de permutations.
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
        xAxis.setTickCount(14);
        xAxis.setTickAngle(60);

        Axis yAxis = this.graphUtilisateursParGrade.getAxis(AxisType.Y);
        yAxis.setLabel("Utilisateurs");
        yAxis.setMin(0);
        yAxis.setMax(this.facadeUtilisateur.calculerUtilisateurTotal());
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
            utilisateurs.set(grade.getGrade(), this.facadeUtilisateur.calculerUtilisateurParGrade(grade));
        }
        model.addSeries(utilisateurs);

        return model;

    }

    /**
     * Cré et remplit le graphique des demandes par ZMR.
     */
    private void creerGraphDemandesParZMR() {
        this.graphDemandesParZMR = new PieChartModel();

        for (DemandePermutation demande : this.facadeDemande.readAll()) {
            this.graphDemandesParZMR.set(demande.getZmr().getLibelle(), this.facadeDemande.calculerDemandesParZMR(demande.getZmr()));
        }

        this.graphDemandesParZMR.setTitle("Nombre de demandes par ZMR");
        this.graphDemandesParZMR.setLegendPosition("e");
        this.graphDemandesParZMR.setShowDataLabels(true);
        this.graphDemandesParZMR.setDiameter(300);
    }

    /**
     * Cré et remplit le graphique des demandes par Specialité.
     */
    private void creerGraphDemandesParSpe() {
        this.graphDemandesParSpe = new PieChartModel();

        for (DemandePermutation demande : this.facadeDemande.readAll()) {
            this.graphDemandesParSpe.set(demande.getUtilisateurCreateur().getSpecialite().getLibelle(), this.facadeDemande.calculerDemandesParSpe(demande.getUtilisateurCreateur().getSpecialite()));
        }

        this.graphDemandesParSpe.setTitle("Nombre de demandes par spécialités");
        this.graphDemandesParSpe.setLegendPosition("e");
        this.graphDemandesParSpe.setShowDataLabels(true);
        this.graphDemandesParSpe.setDiameter(300);
    }

    /**
     * Cré et remplit le graphique du nombre d'utilisateur par mois.
     */
    private void creerGraphUtilisateursParMois() {
        this.graphUtilisateursParDate = new LineChartModel();
        LineChartSeries serie = new LineChartSeries();
        LocalDate d = LocalDate.of(2015, 01, 01);

        while (d.isBefore(LocalDate.now())) {
            d = d.plusMonths(1);
            serie.set(d.toString(), this.facadeUtilisateur.calculerUtilisateurAvantLaDate(asDate(d)));
        }

        this.graphUtilisateursParDate.addSeries(serie);

        this.graphUtilisateursParDate.setTitle("Nombre d'utilisateurs inscrit par mois");
        this.graphUtilisateursParDate.setZoom(true);
        this.graphUtilisateursParDate.getAxis(AxisType.Y).setLabel("Nombre d'utilisateurs");

        DateAxis axis = new DateAxis("Mois");
        axis.setMax(LocalDate.now().toString());
        axis.setTickFormat("%d/%m/%y");

        this.graphUtilisateursParDate.getAxes().put(AxisType.X, axis);
    }

    /**
     * Methode de convertion d'une LocalDate en util.Date
     *
     * @param localDate
     *            La date a convertir.
     * @return une util.Date.
     */
    public static Date asDate(final LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
