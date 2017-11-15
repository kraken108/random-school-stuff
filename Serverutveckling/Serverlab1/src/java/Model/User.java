/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jakob
 */

@Entity
@Table(name = "User")
@XmlRootElement
public class User implements Serializable {
    @Column(name="name")
    private String username;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="password")
    private String password;
    @OneToMany(targetEntity=Message.class)
    private List<Message> messages;
    @ManyToOne(targetEntity=Post.class)
    private List<Post> posts;
    

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User(String username){
        this.username=username;
    }
    
    public User(){
        
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }    

    
    public Long getId() {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
    * @return the password
    */
    public String getPassword() {
        return password;
    }

    /*
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the messages
     */
    public List getMessages() {
        return messages;
    }

    /**
     * @param messages the messages to set
     */
    public void setMessages(ArrayList<Message> messages) {
        this.setMessages(messages);
    }

    /**
     * @return the posts
     */
    public List getPosts() {
        return posts;
    }

    /**
     * @param posts the posts to set
     */
    public void setPosts(ArrayList<Post> posts) {
        this.setPosts(posts);
    }

    /**
     * @param posts the posts to set
     */
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    /**
     * @param messages the messages to set
     */
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}