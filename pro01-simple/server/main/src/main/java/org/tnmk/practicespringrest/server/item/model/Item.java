package org.tnmk.practicespringrest.server.item.model;

import javax.persistence.*;

/**
 * On production code, just name it `Item` is enough.
 * In this practice project, we work with both client & server app01 in the same repository, so I use the name {@link Item} to distinguish with {@link ClientItem} just to make it a little bit easier to understand.
 */
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column
    private Integer id;
    private String name;
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
