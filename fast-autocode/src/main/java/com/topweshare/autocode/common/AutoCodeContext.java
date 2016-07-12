package com.topweshare.autocode.common;

import com.topweshare.autocode.entity.BackUser;
import org.durcframework.core.UserContext;

public enum AutoCodeContext {
	INSTANCE;

	public static AutoCodeContext getInstance() {
		return INSTANCE;
	}

	public BackUser getUser() {
		return UserContext.getInstance().getUser();
	}

	public void setUser(BackUser backUser) {
		UserContext.getInstance().setUser(backUser);
	}

	public boolean isAdmin() {
		return "admin".equals(getUser().getUsername());
	}
}
