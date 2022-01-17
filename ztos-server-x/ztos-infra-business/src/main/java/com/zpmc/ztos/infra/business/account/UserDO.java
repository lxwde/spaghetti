package com.zpmc.ztos.infra.business.account;

import com.google.common.base.Objects;
import com.zpmc.ztos.infra.business.base.AbstractEntity;
import org.geolatte.geom.Geometry;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserDO extends AbstractEntity {

	private static final long serialVersionUID = 5673334451789813703L;
	public UserDO(){};
	public UserDO(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "location", columnDefinition="SDO_GEOMETRY")
	private Geometry location;

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

	public Geometry getLocation() {
		return location;
	}

	public void setLocation(Geometry location) {
		this.location = location;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserDO userDO = (UserDO) o;
		return Objects.equal(id, userDO.id) && Objects.equal(firstName, userDO.firstName) && Objects.equal(lastName, userDO.lastName) && Objects.equal(location, userDO.location);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id, firstName, lastName, location);
	}

	@Override
	public String toString() {
		return "UserDO{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", location=" + location +
				"} " + super.toString();
	}
}
