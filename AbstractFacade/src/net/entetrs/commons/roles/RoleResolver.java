package net.entetrs.commons.roles;

@FunctionalInterface
public interface RoleResolver {
	
	boolean isInRole(String role);

}
