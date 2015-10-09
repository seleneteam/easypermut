package etrs.selene.easypermut.model.commons;

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
	 * TODO : à rédiger
	 * 
	 * @param uuid
	 * @return
	 */
	public final static byte[] convertToBytes(UUID uuid) {
		byte[] buffer = new byte[16];
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		byteBuffer.putLong(uuid.getMostSignificantBits());
		byteBuffer.putLong(uuid.getLeastSignificantBits());
		return buffer;
	}

	/***
	 * TODO : à rédiger
	 * 
	 * @param buffer
	 * @return
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
