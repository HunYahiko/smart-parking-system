package com.utm.stanislav.parkingapp.web.controller.booking;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserPrincipalSecurityContextFactory.class)
@interface WithMockCustomUserPrincipal {
}
