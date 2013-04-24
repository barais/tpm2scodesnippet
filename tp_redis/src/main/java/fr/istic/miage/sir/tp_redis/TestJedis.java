package fr.istic.miage.sir.tp_redis;

import redis.clients.jedis.Jedis;

public class TestJedis {

	public static void main(String[] args) {
		String cacheKey = "languages";
		Jedis jedis = new Jedis("localhost");
		// Adding a set as value

		jedis.sadd(cacheKey, "Java");
		jedis.sadd(cacheKey, "C#");
		jedis.sadd(cacheKey, "Python");// SADD

		// Getting all values in the set: SMEMBERS
		System.out.println("Languages: " + jedis.smembers(cacheKey));
		// Adding new values
		jedis.sadd(cacheKey, "Java");
		jedis.sadd(cacheKey, "Ruby");
		// Getting the values... it doesn't allow duplicates
		System.out.println("Languages: " + jedis.smembers(cacheKey));

	}

}