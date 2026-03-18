package com.example.employee;

public class Employee {
    private int id;
    private String name;
    private String department;
    private double salary;
    private String designation;
    private String email;

    public Employee(int id, String name, String department, double salary,
                    String designation, String email) {
        this.id          = id;
        this.name        = name;
        this.department  = department;
        this.salary      = salary;
        this.designation = designation;
        this.email       = email;
    }

    public int    getId()          { return id; }
    public String getName()        { return name; }
    public String getDepartment()  { return department; }
    public double getSalary()      { return salary; }
    public String getDesignation() { return designation; }
    public String getEmail()       { return email; }
}
