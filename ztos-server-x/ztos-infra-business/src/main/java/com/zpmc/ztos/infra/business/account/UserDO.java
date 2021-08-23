package com.zpmc.ztos.infra.business.account;

import com.zpmc.ztos.infra.business.base.AbstractEntity;

import javax.persistence.*;
import java.util.Objects;


@MappedSuperclass
@Table(name = "dummy")
public class UserDO extends AbstractEntity {

	private static final long serialVersionUID = 5673334451789813703L;
	public UserDO(){};
	public UserDO(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserDO dummy = (UserDO) o;
		return Objects.equals(id, dummy.id) && Objects.equals(firstName, dummy.firstName) && Objects.equals(lastName, dummy.lastName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName, lastName);
	}

	@Override
	public String toString() {
		return "Dummy{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				"} " + super.toString();
	}
}
