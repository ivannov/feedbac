package com.nosoftskills.feedback.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Entity
public class Manager extends Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Version
	private int version;

	@OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = false)
	private List<Employee> directReports = new ArrayList<>();

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

	public List<Employee> getDirectReports() {
		return this.directReports;
	}

	public void setDirectReports(final List<Employee> directReports) {
		this.directReports = directReports;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Manager manager = (Manager) o;
		return Objects.equals(directReports, manager.directReports);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), id, version, directReports);
	}

	@Override
	public String toString() {
		return super.toString();
	}
}