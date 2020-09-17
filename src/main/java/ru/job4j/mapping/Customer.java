package ru.job4j.mapping;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn
    private Role role;

    public static Customer of(String name, Role role) {
        Customer customer = new Customer();
        customer.name = name;
        customer.role = role;
        return customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return id == customer.id
                && Objects.equals(name, customer.name)
                && Objects.equals(role, customer.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, role);
    }
}
