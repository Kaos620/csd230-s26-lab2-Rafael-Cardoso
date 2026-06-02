package csd230.s26.lab1.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

    @Entity
    @Table(name = "orders")
    @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
    public class OrderEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private double totalAmount;
        private LocalDateTime orderDate;

    // We use a Set to prevent duplicate products in the same cart
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "order_products", // The Join Table name
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<ProductEntity> orders = new LinkedHashSet<>();

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public LocalDateTime getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(LocalDateTime orderDate) {
            this.orderDate = orderDate;
        }

        public Set<ProductEntity> getOrders() {
            return orders;
        }

        public void setOrders(Set<ProductEntity> orders) {
            this.orders = orders;
        }

        public OrderEntity(Long id, double totalAmount, LocalDateTime orderDate, Set<ProductEntity> orders) {
            this.id = id;
            this.totalAmount = totalAmount;
            this.orderDate = orderDate;
            this.orders = orders;
        }

        public OrderEntity() {
        }
    }
