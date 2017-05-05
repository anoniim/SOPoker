package net.solvetheriddle.sopoker.data.model;


import java.util.List;

public class Response {

    List<User> items;

    public List<User> getItems() {
        return items;
    }

    public void setItems(final List<User> items) {
        this.items = items;
    }
}
