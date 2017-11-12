package com.example.a2106088.amaru.entity;

import java.util.List;




/**
 * Created by 2107262 on 9/6/17.
 */
public class Group {

    private long id;

    private String nombre;

    private String instructor;

    private List<Comment> comments;

    private String description;

    private String category;

    private Double rate;

    private int totalVotes;

    private String image;

    private List<Clase> clases;

    public Group(long id,String nombre,String instructor,List<Comment> comments,String description,String category, Double rate,int totalVotes,String image,List<Clase> clases) {
        this.id=id;
        this.nombre=nombre;
        this.instructor=instructor;
        this.comments=comments;
        this.description=description;
        this.category=category;
        this.rate=rate;
        this.totalVotes=totalVotes;
        this.image=image;
        this.clases=clases;
    }

    public Group() {
    }

    public String toString(){
        return "id: "+id +"name: " +nombre+ "instructor: "+instructor +"clases: " + clases.size();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }


    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public List<Clase> getClases() {
        return clases;
    }

    public void setClases(List<Clase> clases) {
        this.clases = clases;
    }
}
