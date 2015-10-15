package etrs.selene.easypermut.model.commons;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import net.entetrs.commons.uuid.GeneratedUUID;

@MappedSuperclass
public class AbstractEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedUUID
	private String id;
}
