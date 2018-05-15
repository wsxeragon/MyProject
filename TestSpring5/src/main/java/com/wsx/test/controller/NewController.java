package com.wsx.test.controller;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.wsx.test.domain.Person2;
import com.wsx.test.service.PersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class NewController {

	@Autowired
	private PersonService personService;

	@Resource(name = "p1")
	Person2 p1;
	@Resource(name = "p2")
	Person2 p2;

	@GetMapping("/test1")
	public Flux<Person2> test1() {
		// return personService.getUsers();
		return Flux.fromIterable(Arrays.asList(p1, p2));
	}

	@GetMapping("/test2")
	public Mono<Person2> test2(int id) {
		// return personService.getPersonById(1);
		List<Person2> ps = Arrays.asList(p1, p2);
		return Mono.justOrEmpty(ps.stream().filter(p -> p.getId() == id).findFirst().orElse(null));
	}

	// ?????????????????
	@GetMapping("/test3")
	public Mono<ServerResponse> test3() {
		return ServerResponse.ok().body(Mono.just(p1), Person2.class);
	}

	@GetMapping("/hello_world")
	public Mono<String> sayHelloWorld() {
		return Mono.just("Hello World");
	}

	@GetMapping("/randomNumbers")
	public Flux<ServerSentEvent<Integer>> randomNumbers() {
		return Flux.interval(Duration.ofSeconds(1)).map(x -> {
			System.out.println(x);
			return x;
		}).map(x -> ServerSentEvent.<Integer>builder().event("test").id("123").data(Integer.valueOf("" + x)).build());
	}

	@Test
	public void test2() {
		Flux<String> flux1 = Flux.just("a", "b", "c", "");
		Flux<String> flux2 = flux1.map(x -> x.equals("") ? "" : x.toUpperCase());
		flux2.toStream().forEach(a -> System.out.println(a));
		// 此处是个包含null的optional
		Optional<String> o1 = flux2.toStream().filter(x -> x.equals("d")).findFirst();
		o1.ifPresent(x -> {
			System.out.println(x);
		});
		String s1 = o1.orElseGet(() -> {
			return "无值";
		});
		System.out.println(s1);
	}

}
