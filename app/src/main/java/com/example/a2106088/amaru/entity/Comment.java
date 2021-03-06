package com.example.a2106088.amaru.entity;


/**
 * Created by 2106088 on 9/6/17.
 */

public class Comment  implements java.io.Serializable{

    private String contenido;
    private long groupId;
    private String usuario;
    private String fecha;
    private long id;

    public Comment() {
    }

    public Comment(String contenido, long Groupid, String usuario, String fecha,long id) {
        this.contenido=contenido;
        this.groupId=Groupid;
        this.usuario=usuario;
        this.fecha=fecha;
        this.id=id;

    }


    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}