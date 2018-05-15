package cn.inphase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.inphase.dao.PersonDao;
import cn.inphase.domain.Person;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonDao personDao;

	@Override
	public boolean insert(Person person) {
		return personDao.insert(person) == 1;
	}

}
