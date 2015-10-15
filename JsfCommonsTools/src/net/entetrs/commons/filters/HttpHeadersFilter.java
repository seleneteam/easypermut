package net.entetrs.commons.filters;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;


/**
 * filtre capable d'ajouter des headers HTTP en vue des bonnes pratiques 
 * de sécurité OWASP (testée avec l'outil ZAP).
 * 
 * Pour l'utiliser, inclure ces lignes dans web.xml
 * 
 * <pre><![CDATA[
 * 
 * <filter>
 *		<filter-name>httpHeadersFilter</filter-name>
 *		<filter-class>net.entetrs.commons.filters.HttpHeadersFilter</filter-class>
 *	</filter>
 *	<filter-mapping>
 *		<filter-name>httpHeadersFilter</filter-name>
 *		<url-pattern>/*</url-pattern>
 *		<dispatcher>FORWARD</dispatcher>
 *		<dispatcher>REQUEST</dispatcher>
 *	</filter-mapping>
 *
 *  ]]></pre>
 * 
 * @author CDT ROBIN
 *
 */
public class HttpHeadersFilter implements Filter
{
	private Properties headers = new Properties();
	

	@Override
	public void destroy()
	{
		headers = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		if (!headers.isEmpty())
		{
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			for(Entry <?,?> entry : headers.entrySet())
			{
				String headerName = (String) entry.getKey();
				String headerValue = (String) entry.getValue();
				httpResponse.setHeader(headerName, headerValue);				
			}
		}			
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException
	{
		try
		{
			headers.load(this.getClass().getResourceAsStream("headers.properties"));
		}
		catch (IOException e1)
		{
			System.err.println("/!\\ Erreur Anormale /!\\ ");
			System.err.println("Fichier headers.properties absent dans le package JsfTools : net.entetrs.commons.filters");		
		}
		
		try
		{
			Properties complement = new Properties(); 
			complement.load(config.getServletContext().getResourceAsStream("/WEB-INF/headers.properties"));
			headers.putAll(complement);
			System.out.println("Prise en compte du fichier /WEB-INF/headers.properties : OK");
		}
		catch (Exception e)
		{
			System.out.println("Fichier /WEB-INF/headers.properties absent.");
			System.out.println("Pas de compléments de filtres");
		}
		
		System.out.println("Headers HTTP configurés : ");
		
		for(Entry <?,?> entry : headers.entrySet())
		{
			String headerName = (String) entry.getKey();
			String headerValue = (String) entry.getValue();
			System.out.printf("[%s] = [%s]\n", headerName, headerValue);				
		}
		
	}

}
