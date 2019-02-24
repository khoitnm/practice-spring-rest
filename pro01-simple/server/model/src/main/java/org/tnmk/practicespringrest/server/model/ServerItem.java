package org.tnmk.practicespringrest.server.model;

/**
 * On production code, just name it `Item` is enough.
 * In this practice project, we work with both client & server app in the same repository, so I use the name {@link ServerItem} to distinguish with {@link ClientItem} just to make it a little bit easier to understand.
 */
public class ServerItem {
    private String id;
    private String name;
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
