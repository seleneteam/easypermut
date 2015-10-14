package etrs.selene.easypermut.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import etrs.selene.easypermut.model.commons.GeneratedUUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "grade")
public class Grade implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedUUID
	@Column(columnDefinition = "VARCHAR(36)", name = "id")
	String id;

	@Column(name = "grade")
	String grade;
	
}
