/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Jakob
 */
@Entity
@Table(name = "post")
public class Post implements Serializable {
    
    @Column(name="message")
    private String message;
    
    @Column(name="date")
    private String date;
    
    @ManyToOne(targetEntity=User.class)
    private Collection user;

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Post() {
    }
    
    public String getMessage() {
        return message;
    }
    
    public Post(String message,Date date, User user){
        this.message = message;
        this.date = date.toString();
        this.user = (Collection) user;
    }
    
    public Post(String message, User user){
        this.message = message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date.toString();
    }
    
    public Collection getUser() {
        return user;
    }

    public void setUser(Collection user) {
        this.user = user;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

}