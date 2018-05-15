package com.wsx.test.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wsx.test.domain.Person2;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class PersonService {
	private final List<Person2> ps = Arrays.asList(new Person2(1, "www", 19), new Person2(2, "eee", 20));

	public Mono<Person2> getPersonById(int id) {
		return Mono.justOrEmpty(ps.stream().filter(p -> {
			return p.getId() == id;
		}).findFirst().orElse(null));
	}

	public Flux<Person2> getUsers() {
		return Flux.fromIterable(ps);
	}
}
