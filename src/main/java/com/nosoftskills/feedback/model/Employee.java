package com.nosoftskills.feedback.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Employee extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Version
    private int version;

    @ManyToOne
    private Manager manager;

    @OneToMany(mappedBy = "forEmployee", cascade = CascadeType.ALL)
    private List<FeedbackEvent> feedbacks = new ArrayList<>();

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public Manager getManager() {
        return this.manager;
    }

    public void setManager(final Manager manager) {
        this.manager = manager;
    }

    public List<FeedbackEvent> getFeedbacks() {
        return this.feedbacks;
    }

    public void setFeedbacks(final List<FeedbackEvent> feedbacks) {
        this.feedbacks = feedbacks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(manager, employee.manager) &&
                Objects.equals(feedbacks, employee.feedbacks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), manager, feedbacks);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "userName='" + getUserName() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", manager=" + manager.getUserName() +
                ", feedbacks=" + feedbacks +
                '}';
    }
}