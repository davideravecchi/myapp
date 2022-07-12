package com.drave.mioporcino.database;

public class Contatto {

    private Integer idrow;
    private String localita;
    private String mienote;
    private String valutazione;
    private String link;
    private String data;
    private Integer tipologia;

    public Contatto(String l, String n,String v,String m,String d, Integer t,Integer i) {
        this.localita = l;
        this.mienote = n;
        this.valutazione = v;
        this.link = m;
        this.data = d;
        this.tipologia = t;
        this.idrow = i;
    }

    public Integer getIdrow() { return idrow; }

    public String getLocalita() { return localita; }

    public String getNote() {
        return mienote;
    }

    public String getValutazione() {
        return valutazione;
    }

    public String getLink() {
        return link;
    }

    public String getData() { return data; }

    public Integer getTipologia() { return tipologia; }
}
