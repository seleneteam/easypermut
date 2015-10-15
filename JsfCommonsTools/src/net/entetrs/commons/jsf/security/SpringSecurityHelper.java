package net.entetrs.commons.jsf.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Classe utilitaire de lancement des fonctionnalités de login et logout de
 * Spring Security.
 * 
 * @author RBN
 */
public class SpringSecurityHelper
{

	private static final String SPRING_SECURITY_LOGIN = "/j_spring_security_check";
	private static final String SPRING_SECURITY_LOGOUT = "/j_spring_security_logout";

	private SpringSecurityHelper()
	{
		// protection du constructeur
	}

	private static RequestDispatcher getDispatcher(ExternalContext ctx, String url)
	{
		return ((ServletRequest) ctx.getRequest()).getRequestDispatcher(url);
	}

	/**
	 * méthode interne pour lancer un "forward" vers une URL depuis un contexte.
	 * JSF.
	 *
	 * @param url
	 * @throws ServletException
	 * @throws IOException
	 */
	private static void doForward(String url) throws ServletException, IOException
	{
		ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
		RequestDispatcher dispatcher = SpringSecurityHelper.getDispatcher(ctx, url);
		dispatcher.forward((ServletRequest) ctx.getRequest(), (ServletResponse) ctx.getResponse());
		FacesContext.getCurrentInstance().responseComplete();
	}

	/**
	 * lance la fonction de "login" auprès du framework Spring Security.
	 *
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public static void login() throws IOException, ServletException
	{
		SpringSecurityHelper.doForward(SPRING_SECURITY_LOGIN);
	}

	/**
	 * lance la fonction de "logout" auprès du framework Spring Security.
	 *
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void logout() throws ServletException, IOException
	{
		SpringSecurityHelper.doForward(SPRING_SECURITY_LOGOUT);
	}

	/**
	 * retourne le login de l'utilisateur.
	 * 
	 * @return le login de l'utilisateur
	 */
	public static String getUserLogin()
	{
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	/**
	 * permet de savoir si l'utilisateur est dans un rôle passé en paramètre.
	 *
	 * @param role
	 * @return true si l'utilisateur possède le rôle, false sinon.
	 */
	public static boolean isInRole(String role)
	{

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		for (GrantedAuthority authority : authentication.getAuthorities())
		{
			if (authority.getAuthority().equalsIgnoreCase(role)) { return true; }
		}
		return false;
	}

	/**
	 * retourne la liste complète des rôles.
	 * 
	 * @return la liste des rôles de l'application
	 */
	public static List<String> getRoles()
	{
		List<String> roles = new ArrayList<>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		for (GrantedAuthority authority : authentication.getAuthorities())
		{
			roles.add(authority.getAuthority());
		}
		return roles;
	}

}
