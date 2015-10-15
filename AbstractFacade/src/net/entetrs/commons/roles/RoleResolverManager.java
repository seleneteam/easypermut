package net.entetrs.commons.roles;

public class RoleResolverManager 
{
	private static RoleResolver resolver;
	
	public static RoleResolver getResolver() 
	{
		return resolver;
	}
	
	public synchronized static void setResolver(RoleResolver resolver) 
	{
		RoleResolverManager.resolver = resolver;
	}
	
	public static boolean isInRole(String role)
	{
		return resolver.isInRole(role);
	}	
}
