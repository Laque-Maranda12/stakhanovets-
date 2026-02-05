package ru.stakhanovets.admin.model;

public class Repair {
    private long id;
    private String toolName;
    private String createdAt;
    private int cost;
    private String status;

    public Repair() {}

    public Repair(long id, String toolName, String createdAt, int cost, String status) {
        this.id = id; this.toolName = toolName; this.createdAt = createdAt;
        this.cost = cost; this.status = status;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getToolName() { return toolName; }
    public void setToolName(String toolName) { this.toolName = toolName; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public int getCost() { return cost; }
    public void setCost(int cost) { this.cost = cost; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
