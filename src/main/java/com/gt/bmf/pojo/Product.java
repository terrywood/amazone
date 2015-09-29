package com.gt.bmf.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Product implements Serializable {

	private static final long serialVersionUID = -1686320397097241613L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
	private String id;

	private String name;
	//private String link;
	private String image;
	private String tag;

    public String getId() {
        return id;
    }

        public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
