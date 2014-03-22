package de.zlvp.control.impl;

import java.util.List;

import de.zlvp.control.ZlvpController;
import de.zlvp.control.dao.ZlvpDao;
import de.zlvp.model.Jahr;

public class ZlvpControllerImpl implements ZlvpController {

	private ZlvpDao dao;

	@Override
	public List<Jahr> getJahre() {
		return dao.findAllOrderByBezeichnungDesc();
	}

	@Override
	public Jahr getJahr(long id) {
		return null;
	}

	public ZlvpDao getDao() {
		return dao;
	}

	public void setDao(ZlvpDao dao) {
		this.dao = dao;
	}

}
