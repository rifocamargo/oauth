package br.com.lecom.oauth.environment.user.controller;

import java.security.Principal;

import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UsersMeController {

	@Secured({ "ROLE_INFRA", "ROLE_USER" })
	@GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
	public Principal user(Principal principal) {
		return principal;
	}

}