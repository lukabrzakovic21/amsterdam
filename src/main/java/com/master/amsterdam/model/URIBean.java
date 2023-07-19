package com.master.amsterdam.model;

public class URIBean {

    private final String istanbulURI;
    private final String alexandriaURI;
    private final String milanoURI;

    public URIBean(String istanbulURI, String alexandriaURI, String milanoURI) {
        this.istanbulURI = istanbulURI;
        this.alexandriaURI = alexandriaURI;
        this.milanoURI = milanoURI;
    }

    public String getAlexandriaURI() {
        return alexandriaURI;
    }

    public String getIstanbulURI() {
        return istanbulURI;
    }

    public String getMilanoURI() {
        return milanoURI;
    }
}
