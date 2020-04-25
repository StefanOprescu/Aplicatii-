package me.networking;

public class GetAngajatRequest implements Request {
    private String username;
    private String parola;

    public GetAngajatRequest(String username, String parola) {
        this.username = username;
        this.parola = parola;
    }

    public String getUsername() {
        return username;
    }

    public String getParola() {
        return parola;
    }
}
