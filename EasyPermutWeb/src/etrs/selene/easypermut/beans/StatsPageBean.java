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
import net.entetrs.commons.jsf.JsfUtils;

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
public class StatsPageBean implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Inject
	UtilisateurSession facadeUtilisateur;

	@Inject
	DemandePermutationSession facadeDemande;

	@Inject
	GradeSession facadeGrade;

	@Inject
	ZMRSession facadeZMR;

	Utilisateur utilisateur;

	BarChartModel graphUtilisateurs;
	PieChartModel graphDemandes;

	@PostConstruct
	public void init()
	{
		this.utilisateur = (Utilisateur)JsfUtils.getFromFlashScope("_utilisateur");
		this.createGraphUtilisateurs();
		this.createGraphDemandes();
	}

	public long nombreUtilisateurs()
	{
		return this.facadeUtilisateur.count();
	}

	public long nombreDemandes()
	{
		return this.facadeDemande.count();
	}

	private void createGraphUtilisateurs()
	{
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

	private BarChartModel initBarModel()
	{
		BarChartModel model = new BarChartModel();

		ChartSeries utilisateurs = new ChartSeries();
		utilisateurs.setLabel("Utilisateurs");

		for (Grade grade : this.facadeGrade.readAll())
		{
			utilisateurs.set(grade.getGrade(), this.facadeUtilisateur.quantiteUtilisateurParGrade(grade));
		}
		model.addSeries(utilisateurs);

		return model;

	}

	private void createGraphDemandes()
	{
		this.graphDemandes = new PieChartModel();

		for (ZMR zmr : this.facadeZMR.readAll())
		{
			this.graphDemandes.set(zmr.getLibelle(), this.facadeDemande.quantiteDemandesParZMR(zmr));
		}

		this.graphDemandes.setTitle("Nombre de demandes par ZMR");
		this.graphDemandes.setLegendPosition("e");
		this.graphDemandes.setFill(true);
		this.graphDemandes.setShowDataLabels(true);
		this.graphDemandes.setDiameter(300);
	}
}
