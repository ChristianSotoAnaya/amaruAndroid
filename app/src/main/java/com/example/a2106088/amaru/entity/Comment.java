package com.example.a2106088.amaru.entity;


/**
 * Created by 2106088 on 9/6/17.
 */

public class Comment  implements java.io.Serializable{

    private String contenido;
    private long GroupId;
    private String usuario;
    private String fecha;
    private long id;

    public Comment() {
    }

    public Comment(String contenido, long Groupid, String usuario, String fecha,long id) {
        this.setContenido(contenido);
        this.setGroupId(Groupid);
        this.setUsuario(usuario);
        this.setFecha(fecha);
        this.setId(id);
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public long getGroupId() {
        return GroupId;
    }

    public void setGroupId(long groupId) {
        GroupId = groupId;
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