package io.openliberty.guides.sociallogin.logout;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;

public class FacebookLogout implements ILogout {

	@Inject
	@ConfigProperty(name="facebook.client.id")
	private String clientId;
	
	@Inject
	@ConfigProperty(name="facebook.client.secret")
	private String clientSecret;
	
	
	@Override
	public Response logout() {
		// TODO Auto-generated method stub
		return null;
	}

}
