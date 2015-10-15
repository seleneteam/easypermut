package net.entetrs.commons.logs;

import java.util.Formatter;

import org.apache.commons.logging.Log;

/**
 * classe utilitaire pour la production de logs.
 * 
 * @author francois.robin
 *
 */

public final class LogUtils {

	/**
	 * niveaux le logs supportés.
	 *
	 */
	public static enum LogLevel {
		ERROR, WARN, INFO, DEBUG
	};

	private LogUtils() {
		// protection du constructeur pour cette classe utilitaire.
	}

	/**
	 * formate le message avec les paramètres et génère le log.
	 * 
	 * @param log
	 *            instance du log d'écriture
	 * @param level
	 *            niveau de log du message
	 * @param format
	 *            chaine de formatage {@link Formatter}
	 * @param objects
	 *            liste des paramètres à injecter dans la chaine de formatage.
	 */
	public final static void logFormat(Log log, LogLevel level, String format, Object... objects) {
		// NOTE : les messages sont construits volontairement après chaque
		// "if is...Enabled"
		// pour ne pas construire les chaines si jamais le log n'est pas actif.
		// NE PAS TENTER DE FACTORISER EN SORTANT CETTE INSTRUCTION, sinon cela
		// serait contre-
		// productif.

		switch (level) {
		case INFO:
			if (log.isInfoEnabled()) {
				String message = String.format(format, objects);
				log.info(message);
			}
			break;
		case WARN:
			if (log.isWarnEnabled()) {
				String message = String.format(format, objects);
				log.warn(message);
			}
			break;
		case ERROR:
			if (log.isErrorEnabled()) {
				String message = String.format(format, objects);
				log.error(message);
			}
			break;
		case DEBUG:
			if (log.isDebugEnabled()) {
				String message = String.format(format, objects);
				log.debug(message);
			}
			break;
		}
	}
	
	/**
	 *  formate le message avec les paramètres et génère le log en niveau INFO automatiquement.
	 * 
	 * @param log
	 *            instance du log d'écriture
	 * @param format
	 *            chaine de formatage {@link Formatter}
	 * @param objects
	 *            liste des paramètres à injecter dans la chaine de formatage.
	 */
	public final static void logFormat(Log log, String format, Object... objects) {
	   logFormat(log, LogLevel.INFO, format, objects);	
	}

	public final static void logException(final Log log, final LogLevel level, final Throwable pException, String format, Object... objects) {
		switch (level) {
		case INFO:
			if (log.isInfoEnabled()) {
				String message = String.format(format, objects);
				log.info(message, pException);
			}
			break;
		case WARN:
			if (log.isWarnEnabled()) {
				String message = String.format(format, objects);
				log.warn(message, pException);
			}
			break;
		case ERROR:
			if (log.isErrorEnabled()) {
				String message = String.format(format, objects);
				log.error(message, pException);
			}
			break;
		case DEBUG:
			if (log.isDebugEnabled()) {
				String message = String.format(format, objects);
				log.debug(message, pException);
			}
			break;
		}
	}
}
