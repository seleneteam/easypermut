package etrs.selene.easypermut.model.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import etrs.selene.easypermut.model.commons.AbstractEntity;

/**
 * Classe représentant l'entitée Utilisateur.
 *
 * @author SGT Mora Leo
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "UTILISATEUR")
public class Utilisateur extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identifiant Anudef de l'utilisateur.
     */
    @Column(name = "identifiant_anudef")
    @Length(min = 1)
    @NotNull
    @Getter
    @Setter
    String identifiantAnudef;

    /**
     * Nom de l'utilisateur.
     */
    @Column(name = "nom")
    @Getter
    @Setter
    String nom;

    /**
     * Prenom de l'utiisateur.
     */
    @Column(name = "prenom")
    @Getter
    @Setter
    String prenom;

    /**
     * NIA de l'utilisateur.
     */
    @Column(name = "nia")
    @Getter
    @Setter
    String nia;

    /**
     * Mail de l'utilisateur.
     */
    @Column(name = "mail")
    @Email
    @Getter
    @Setter
    String mail;

    /**
     * Grade de l'utilisateur.
     */
    @ManyToOne
    @JoinColumn(name = "grade_id")
    @Getter
    @Setter
    Grade grade;

    /**
     * Specialite de l'utilisateur.
     */
    @ManyToOne
    @JoinColumn(name = "specialite_id")
    @Getter
    @Setter
    Specialite specialite;

    /**
     * Poste occupé par l'utilisateur.
     */
    @OneToOne
    @JoinColumn(name = "poste_id")
    @Getter
    @Setter
    Poste poste;

    /**
     * Definit si l'utilisateur peut se connecter a l'application ou non. false
     * si les données de l'utilisateur on été importées. true si il doit
     * attendre l'importation de ces données.
     */
    @Column(name = "valide")
    @Getter
    @Setter
    Boolean estValide;

    /**
     * Definit si l'utilisateur est deja en cours de transaction pour une
     * permutation.
     */
    @Column(name = "interesse")
    @Getter
    @Setter
    Boolean estInteresse;

    /**
     * Definit si l'utilisateur est deja en cours de transaction pour une
     * permutation.
     */
    @Column(name = "informations_valides")
    @Getter
    @Setter
    Boolean informationsValide;
}
