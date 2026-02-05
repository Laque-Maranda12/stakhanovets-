package ru.stakhanovets.admin.model;

public class Tool {
    private long id;
    private String name;
    private String status;
    private int pricePerDay;
    private int deposit;
    private int stock;

    public Tool() {}

    public Tool(long id, String name, String status, int pricePerDay, int deposit, int stock) {
        this.id = id; this.name = name; this.status = status;
        this.pricePerDay = pricePerDay; this.deposit = deposit; this.stock = stock;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(int pricePerDay) { this.pricePerDay = pricePerDay; }

    public int getDeposit() { return deposit; }
    public void setDeposit(int deposit) { this.deposit = deposit; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
