package fr.istic.miage.sir.tp_redis;

import redis.clients.jedis.Jedis;

public class IncrTest {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost");
		System.out.println(jedis.get("counter"));
		jedis.incr("counter");
		System.out.println(jedis.get("counter"));
	}

}