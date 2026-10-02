package controller;

import model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.ArrayList;

public class Authentication {

    public static User logInAuthentication(ArrayList<User> users, String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                try {
                    Algorithm algorithm = Algorithm.HMAC256(password);
                    DecodedJWT jwt = JWT.require(algorithm)
                            .withIssuer("auth0")
                            .build()
                            .verify(user.getToken());
                    return user;
                } catch (SignatureVerificationException e) {
                    System.out.println("incorrect password");
                    return null;
                }
            }
        }
        return null;
    }

    public static User signUpAuthentication(ArrayList<User> users, String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)) return null;
        }
        Algorithm algorithm = Algorithm.HMAC256(password);
        String token = JWT.create()
                .withIssuer("auth0")
                .sign(algorithm);
        return new User(username, token);
    }
}