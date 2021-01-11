// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2020 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
// end::copyright[]
package io.openliberty.guides.sociallogin.logout;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.ibm.websphere.security.social.UserProfileManager;

@RequestScoped
public class GitHubLogout implements ILogout {
	
	@Inject
	@ConfigProperty(name="github.client.id")
	private String clientId;
	
	@Inject
	@ConfigProperty(name="github.client.secret")
	private String clientSecret;
	

    public Response logout() {
    	
    	final String UNAUTHORIZE_URL = 
    			"https://api.github.com/applications/{client_id}/grant" ;
    	
    	final String ACCESS_TOKEN = UserProfileManager
    			.getUserProfile()
    			.getAccessToken();
    	
    	Map<String,String> requestBody = new HashMap<>();
    	requestBody.put("access_token", ACCESS_TOKEN);
    	
    	final String AUTH = clientId + ":" + clientSecret;
    	
    	final byte[] ENCODED_AUTH_STREAM = Base64.getEncoder()
    			.encode(AUTH.getBytes(StandardCharsets.ISO_8859_1));
    	
    	final String ENCODED_AUTH = new String(ENCODED_AUTH_STREAM);
    	
    	return ClientBuilder
    			.newClient()
    			.target(UNAUTHORIZE_URL)
    			.resolveTemplate("client_id", clientId )
    			.request()
    			.header("AUTHORIZATION", "Basic " + ENCODED_AUTH)
    			.method("DELETE", Entity.json( requestBody ) );
    	
    }
}
