package backend;

import java.io.Serializable;

public class RemoteCar implements Serializable {
    private int id;
    private String name;
    private boolean isActive;

    public RemoteCar() {}
    public RemoteCar(int id, String name, boolean isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    @Override
    public String toString() {
        return "RemoteCar{" + "id=" + id + ", name='" + name + '\'' + ", isActive=" + isActive + '}';
    }

    public void changeActive() {
        isActive = !isActive;
    }
}
