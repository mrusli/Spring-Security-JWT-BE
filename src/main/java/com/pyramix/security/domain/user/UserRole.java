package com.pyramix.security.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.pyramix.security.domain.common.IdBasedObject;
import com.pyramix.security.domain.common.SchemaUtil;

@Entity
@Table(name = "auth_user_role", schema = SchemaUtil.SCHEMA_COMMON)
public class UserRole extends IdBasedObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1747969498107204070L;

	//	`user_role` varchar(255) DEFAULT NULL,
	@Column(name="user_role")
	private String role_name;
	
	//	`enabled` char(1) DEFAULT NULL,
	@Column(name="enabled")
	@Type(type = "true_false")
	private boolean enabled;

	@Override
	public String toString() {
		return "UserRole [getId()=" + getId() + ", getCreateDate()=" + getCreateDate() + ", getLastEditDate()="
				+ getLastEditDate() + ", getRole_name()=" + getRole_name() + ", isEnabled()=" + isEnabled() + "]";
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
}
