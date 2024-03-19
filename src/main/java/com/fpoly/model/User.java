package com.fpoly.model;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
	@Id
	private String username;
	@Column(unique = true)
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private String phoneNumber;
	private Boolean status;
	
	@ManyToOne @JoinColumn(name = "vip")
	private Vip vip;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<ThongTinDatSan> listThongTinDatSan;
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<DanhGia> listDanhGia;
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<HoaDon> listHoaDon;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "username"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	public User(String username, String email, String password, Set<Role> roles) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.status = true;
		this.roles = roles;
		this.vip = new Vip(0);
		this.firstName = "";
		this.lastName = "";
		this.phoneNumber = "";
	}

	public Boolean checkRole() {
		if(this.roles != null) {
			for(Role role: this.roles) {
				if(role.getRoleName().equals("ADMIN")) {
					return true;
				}
			}
		}
		return false;
	}
}
