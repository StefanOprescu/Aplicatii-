package me.networking;

import me.model.Angajat;

public class LoginRequest implements Request {
    private Angajat user;

    public LoginRequest(Angajat user) { this.user = user; }

    public Angajat getUser() { return user; }
}
