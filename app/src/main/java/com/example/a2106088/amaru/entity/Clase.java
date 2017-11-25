package com.example.a2106088.amaru.entity;


public class Clase  implements java.io.Serializable{
    private long idgrupo;
    private String fecha;
    private String hour;
    private String place;
    private long idclase;
    private String nombregrupo;
    private int numinscritos;
    private String usuario;

    public Clase(long idgrupo,String fecha,String hour,String place,long idclase,String nombregrupo,int numinscritos, String usuario){
        this.idgrupo=idgrupo;
        this.fecha=fecha;
        this.hour=hour;
        this.place=place;
        this.idclase=idclase;
        this.nombregrupo=nombregrupo;
        this.numinscritos=numinscritos;
        this.usuario = usuario;
    }

    public  Clase(){}


    public boolean equals1( Clase b){
        boolean res=false;
        if (this.idgrupo==b.getIdgrupo() && this.fecha.equals(b.getFecha()) && this.hour.equals(b.getHour())){
            res=true;
        }
        return res;
    }
    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    //@Column(name = "idgrupo", nullable = false)
    public long getIdgrupo() {
        return idgrupo;
    }

    public void setIdgrupo(long idgrupo) {
        this.idgrupo = idgrupo;
    }


    public long getIdclase() { return this.idclase; }

    public void setIdclase(long idclase) {
        this.idclase = idclase;
    }

    public String getNombregrupo() {
        return this.nombregrupo;
    }

    public void setNombregrupo(String nombregrupo) {
        this.nombregrupo = nombregrupo;
    }

    public int getNuminscritos() {
        return numinscritos;
    }


    public void setNuminscritos(int numinscritos) {
        this.numinscritos = numinscritos;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}


