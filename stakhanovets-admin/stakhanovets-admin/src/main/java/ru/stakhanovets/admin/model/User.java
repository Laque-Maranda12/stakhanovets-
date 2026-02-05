package ru.stakhanovets.admin.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private long id;
    private String fullName;
    private String email;
    private boolean active = true;
    private List<String> roles = new ArrayList<>();

    public User() {}

    public User(long id, String fullName, String email, boolean active, List<String> roles) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.active = active;
        this.roles = roles != null ? roles : new ArrayList<>();
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }

    public String rolesAsText() {
        return roles == null ? "" : String.join(", ", roles);
    }
}
