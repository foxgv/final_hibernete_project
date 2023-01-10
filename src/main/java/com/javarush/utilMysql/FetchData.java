package com.javarush.utilMysql;

import com.javarush.MySessionFactory;
import com.javarush.dao.CityDAO;
import com.javarush.dao.CountryDAO;
import com.javarush.domain.City;
import com.javarush.domain.Country;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;


public class FetchData {

    private final SessionFactory sessionFactory = MySessionFactory.getSessionFactory();
    private final CountryDAO countryDAO = new CountryDAO(sessionFactory);
    private final CityDAO cityDAO = new CityDAO(sessionFactory);

    public List<City> fetchData() {
        Session session = sessionFactory.getCurrentSession();
        try (session) {
            List<City> allCities = new ArrayList<>();
            session.beginTransaction();
            List<Country> countries = countryDAO.getAll();

            int totalCount = cityDAO.getTotalCount();
            int step = 500;
            for (int i = 0; i < totalCount; i += step ) {
                allCities.addAll(cityDAO.getItems(i, step));
            }
            session.getTransaction().commit();
            return allCities;
        }
    }

}
