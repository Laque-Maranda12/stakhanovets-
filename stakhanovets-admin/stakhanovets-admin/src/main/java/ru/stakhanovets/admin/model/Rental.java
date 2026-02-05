package ru.stakhanovets.admin.model;

public class Rental {
    private long id;
    private String toolName;
    private String startDate;
    private int days;
    private int sum;
    private String status;

    public Rental() {}

    public Rental(long id, String toolName, String startDate, int days, int sum, String status) {
        this.id = id; this.toolName = toolName; this.startDate = startDate;
        this.days = days; this.sum = sum; this.status = status;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getToolName() { return toolName; }
    public void setToolName(String toolName) { this.toolName = toolName; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public int getDays() { return days; }
    public void setDays(int days) { this.days = days; }

    public int getSum() { return sum; }
    public void setSum(int sum) { this.sum = sum; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
