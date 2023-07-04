package com.master.amsterdam.model;

public class URIBean {

    private final String istanbulURI;
    private final String alexandriaURI;

    public URIBean(String istanbulURI, String alexandriaURI) {
        this.istanbulURI = istanbulURI;
        this.alexandriaURI = alexandriaURI;
    }

    public String getAlexandriaURI() {
        return alexandriaURI;
    }

    public String getIstanbulURI() {
        return istanbulURI;
    }
}
