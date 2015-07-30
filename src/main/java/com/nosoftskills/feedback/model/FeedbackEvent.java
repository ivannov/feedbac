package com.nosoftskills.feedback.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class FeedbackEvent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Version
    private int version;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Employee forEmployee;

    @ManyToOne
    private Employee reporter;

    @Column(length = 3000)
    private String argumentation;

    @Column(length = 3000)
    private String selfComment;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

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

    public Employee getForEmployee() {
        return this.forEmployee;
    }

    public void setForEmployee(final Employee forEmployee) {
        this.forEmployee = forEmployee;
    }

    public Employee getReporter() {
        return this.reporter;
    }

    public void setReporter(final Employee reporter) {
        this.reporter = reporter;
    }

    public String getArgumentation() {
        return argumentation;
    }

    public void setArgumentation(String argumentation) {
        this.argumentation = argumentation;
    }

    public String getSelfComment() {
        return selfComment;
    }

    public void setSelfComment(String selfComment) {
        this.selfComment = selfComment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedbackEvent that = (FeedbackEvent) o;
        return Objects.equals(forEmployee, that.forEmployee) &&
                Objects.equals(reporter, that.reporter) &&
                Objects.equals(argumentation, that.argumentation) &&
                Objects.equals(selfComment, that.selfComment) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(forEmployee, reporter, argumentation, selfComment, createdAt);
    }

    @Override
    public String toString() {
        return "FeedbackEvent{" +
                "forEmployee=" + forEmployee +
                ", reporter=" + reporter +
                ", argumentation='" + argumentation + '\'' +
                ", selfComment='" + selfComment + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}