package net.entetrs.commons.uuid;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * classe qui convertit des UUID vers un tableau de bytes et inversement.
 * 
 * @author francois.robin
 * 
 */

public final class UUIDUtils {

	private UUIDUtils() {
		// protection du constructeur pour cette classe "utilitaire"
	}

	/**
	 * convertit un UUID en tableau d'octets (16 octets = 128 bits)
	 * 
	 * @param uuid
	 * 		uuid à obtenir sous forme de byte[]
	 * @return
	 * 		tableau d'octets contenant la réprésentation de l'UUID
	 */
	public final static byte[] convertToBytes(UUID uuid) {
		byte[] buffer = new byte[16];
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		byteBuffer.putLong(uuid.getMostSignificantBits());
		byteBuffer.putLong(uuid.getLeastSignificantBits());
		return buffer;
	}

	/***
	 * convertit un tableau d'octets (16 octets = 128 bits) en UUID.
	 * 
	 * @param buffer
	 * 		tableau d'octets à convertir en UUID
	 * @return
	 * 		instance du UUID.
	 */
	public final static UUID convertFromBytes(byte[] buffer) {
		if (buffer.length == 16) {
			ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
			UUID uuid = new UUID(byteBuffer.getLong(), byteBuffer.getLong());
			return uuid;
		} else {
			return null;
		}
	}
}
