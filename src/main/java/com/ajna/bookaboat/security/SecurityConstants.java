package com.ajna.bookaboat.security;

public class SecurityConstants {

    public static final String AUTH_LOGIN_URL = "/login";


    // Signing key for HS512 algorithm
    // generated with http://www.allkeysgenerator.com/
    public static final String SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN = "token";
    public static final String SIGN_UP_URL = "/sign-up";

    private SecurityConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}
