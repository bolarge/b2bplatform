package com.duplo.b2bplatform.domain.onboarding;

import com.duplo.b2bplatform.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="users")
public class User extends BaseEntity {
	@Enumerated(EnumType.STRING)
	private Title title;
	private String email;
	private String firstName;
	private String lastName;
	@Digits(fraction = 0, integer = 11)
	private String mobilePhone;
	@Enumerated(EnumType.STRING)
	private EmploymentType employmentType = EmploymentType.STAFF;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	private Business business;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Role.class)
	@JoinTable(name="user_role",
			joinColumns=@JoinColumn(name="user_id"),
			inverseJoinColumns=@JoinColumn(name="role_id"))
	private Collection<Role> roles = new HashSet<>();

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	private Department department;

	public User(Title title, String email, String firstName, String lastName, String mobilePhone, EmploymentType employmentType) {
		this.title = title;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobilePhone = mobilePhone;
		this.employmentType = employmentType;
	}

	@JsonIgnore
	public void addRole(String roleName) {
		if(this.roles == null) {
			this.roles = new HashSet<>();
		}
		Role role = new Role();
		role.setName(roleName);
		this.roles.add(role);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof User user)) return false;
		if (!super.equals(o)) return false;
		return Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getBusiness(), user.getBusiness());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getEmail(), getBusiness());
	}
}
