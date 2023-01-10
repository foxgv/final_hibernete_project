package com.javarush.utilMysql;

import com.javarush.MySessionFactory;
import com.javarush.dao.CityDAO;
import com.javarush.domain.City;
import com.javarush.domain.CountryLanguage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Set;

public class TestMysqlData {
    SessionFactory sessionFactory = MySessionFactory.getSessionFactory();

    public void testMysqlData(List<Integer> ids) {
        Session session = sessionFactory.getCurrentSession();
        CityDAO cityDAO = new CityDAO(sessionFactory);
        try (session) {
            session.beginTransaction();
            for (Integer id : ids) {
                City city = cityDAO.getById(id);
                Set<CountryLanguage> languages = city.getCountry().getLanguages();
            }
            session.getTransaction().commit();
        }
    }
}
