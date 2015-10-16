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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import etrs.selene.easypermut.model.commons.AbstractEntity;

/**
 * Classe représentant l'entitée Utilisateur.
 *
 * @author SGT Mora Leo
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "utilisateur")
public class Utilisateur extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identifiant Anudef de l'utilisateur.
     */
    @Column(name = "identifiantAnudef")
    @Length(min = 1)
    @NotNull
    String identifiantAnudef;

    /**
     * Nom de l'utilisateur.
     */
    @Column(name = "nom")
    @Length(min = 1)
    String nom;

    /**
     * Prenom de l'utiisateur.
     */
    @Column(name = "prenom")
    @Length(min = 1)
    String prenom;

    /**
     * NIA de l'utilisateur.
     */
    @Column(name = "nia")
    @Length(min = 6, max = 8)
    String nia;

    /**
     * Mail de l'utilisateur.
     */
    @Column(name = "mail")
    @Email
    @NotNull
    String mail;

    /**
     * Grade de l'utilisateur.
     */
    @ManyToOne
    @JoinColumn(name = "grade_id")
    Grade grade;

    /**
     * Specialite de l'utilisateur.
     */
    @ManyToOne
    @JoinColumn(name = "specialite_id")
    Specialite specialite;

    /**
     * Poste occupé par l'utilisateur.
     */
    @OneToOne
    @JoinColumn(name = "poste_id")
    Poste poste;

    /**
     * Definit si l'utilisateur peut se connecter a l'application ou non. false
     * si les données de l'utilisateur on été importées. true si il doit
     * attendre l'importation de ces données.
     */
    @Column(name = "valide")
    Boolean estValide;

    /**
     * Definit si l'utilisateur est deja en cours de transaction pour une
     * permutation.
     */
    @Column(name = "interesse")
    Boolean estInteresse;
}
