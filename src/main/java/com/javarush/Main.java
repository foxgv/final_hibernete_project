package com.javarush;

import com.javarush.domain.City;
import com.javarush.redis.CityCountry;
import com.javarush.utilRedis.PrepareRedisClient;
import com.javarush.utilRedis.PushToRedis;
import com.javarush.utilRedis.TestRedisData;
import com.javarush.utilMysql.FetchData;
import com.javarush.utilMysql.TestMysqlData;
import com.javarush.utilMysql.TransformData;
import org.hibernate.SessionFactory;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        SessionFactory sessionFactory = MySessionFactory.getSessionFactory();
        FetchData fetchData = new FetchData();
        TransformData transformData = new TransformData();
        TestMysqlData testMysqlData = new TestMysqlData();
        PushToRedis pushToRedis = new PushToRedis();
        TestRedisData testRedisData = new TestRedisData();

        List<City> cityList = fetchData.fetchData();
        List<CityCountry> preparedData = transformData.transformData(cityList);

        pushToRedis.pushToRedis(preparedData);

        //закроем текущую сессию, чтоб точно делать запрос к БД, а не вытянуть данные из кэша
        sessionFactory.getCurrentSession().close();

        //выбираем случайных 10 id городов
        //так как мы не делали обработку невалидных ситуаций, используем существующие в БД id
        List<Integer> ids = List.of(13, 4070, 2203, 8, 561, 68, 1267, 3851, 258, 32);

        long startRedis = System.currentTimeMillis();
        testRedisData.testRedisData(ids);
        long stopRedis = System.currentTimeMillis();

        long startMysql = System.currentTimeMillis();
        testMysqlData.testMysqlData(ids);
        long stopMysql = System.currentTimeMillis();

        System.out.printf("%s:\t%d ms\n", "Redis", (stopRedis - startRedis));
        System.out.printf("%s:\t%d ms\n", "MySQL", (stopMysql - startMysql));

        MySessionFactory.shutdownSessionFactory();
        PrepareRedisClient.shutdownRedisClient();
    }

}
