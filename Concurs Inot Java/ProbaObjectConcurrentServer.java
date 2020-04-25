package me.server;

import me.networking.ProbaClientObjectWorker;
import me.service.IService;

import java.net.Socket;

public class ProbaObjectConcurrentServer extends AbsConcurrentServer {
    private IService service;
    public ProbaObjectConcurrentServer(int port, IService service) {
        super(port);
        this.service = service;
        System.out.println("Chat- ChatObjectConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ProbaClientObjectWorker worker = new ProbaClientObjectWorker(service, client);
        Thread t = new Thread(worker);
        return t;
    }
}
