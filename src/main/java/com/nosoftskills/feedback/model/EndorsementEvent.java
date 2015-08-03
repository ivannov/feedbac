package com.nosoftskills.feedback.model;

import javax.persistence.*;

import java.util.*;

@Entity
public class EndorsementEvent extends FeedbackEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Version
	private int version;

	@Column(nullable = false)
	private int quantity = 50;

	@OneToMany
	private List<Category> categories = new ArrayList<>();

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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public List<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(final List<Category> category) {
		this.categories = category;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		EndorsementEvent that = (EndorsementEvent) o;
		return Objects.equals(quantity, that.quantity) &&
				Objects.equals(categories, that.categories);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), quantity, categories);
	}

	@Override
	public String toString() {
		return "EndorsementEvent{" +
				"forEmployee=" + getForEmployee() +
				", reporter=" + getReporter() +
				", argumentation='" + getArgumentation() + '\'' +
				", selfComment='" + getSelfComment() + '\'' +
				", createdAt=" + getCreatedAt() +
				", quantity=" + quantity +
				", categories=" + categories +
				'}';
	}
}