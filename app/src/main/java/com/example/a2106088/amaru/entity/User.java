package com.example.a2106088.amaru.entity;

import java.util.List;




/**
 * @author Santiago Carrillo
 * 8/21/17.
 */

public class User implements java.io.Serializable{

    private String nombre;

    private String lastname;

    private String image;

    private String phone;

    private String password;

    private String email;

    private String description;

    private String type;

    private String username;

    private Double rate;

    private int totalVotes;

    private List<Clase> clases;

    private int cupo;


    public User() {
    }

    public User(String nombre, String lastname, String image, String phone, String password, String email, String description, String type, String username, Double rate, int totalVotes, List<Clase> clases, int cupo)

    {
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.lastname = lastname;
        this.setPhone(phone);
        this.image = image;
        this.setDescription(description);
        this.setType(type);
        this.username = username;
        this.rate = rate;
        this.totalVotes = totalVotes;
        this.setClases(clases);
        this.setCupo(cupo);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }


    @Override
    public String toString() {
        return "User{"  + ", email='" + email + '\'' + ", password='" + password + '\'' + ", firstname='"
                + getNombre() + "username " + getUsername() + "phone " + getPhone() + "description " + getDescription() + "password " + getPassword() + '\'' + '}';
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public List<Clase> getClases() {
        return clases;
    }

    public void setClases(List<Clase> clases) {
        this.clases = clases;
    }


    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }
}
