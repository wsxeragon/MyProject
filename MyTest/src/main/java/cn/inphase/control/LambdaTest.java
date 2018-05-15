package cn.inphase.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.Test;

import cn.inphase.domain.Info;

public class LambdaTest {

	@Test
	public void test() {
		List features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
		features.forEach(n -> System.out.println(n));

		new Thread(() -> System.out.println("123")).start();

		List tax = Arrays.asList(100.0, 200.0, 300.0, 400.0, 500.0);
		tax.stream().map((cost) -> (double) cost + .12 * (double) cost).forEach(y -> System.out.println(y));

		Optional total = tax.stream().reduce((a, b) -> (double) a + (double) b);
		System.out.println(total);

		List<Object> newTax = (List<Object>) tax.stream().filter(x -> (double) x > 300).collect(Collectors.toList());
		newTax.forEach(System.out::println);

		List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.", "Canada");
		String G7Countries = G7.stream().map(x -> x.toUpperCase()).collect(Collectors.joining(", "));
		int count = (int) G7.stream().map(x -> x.toUpperCase()).count();
		System.out.println(G7Countries);
		System.out.println(count);
	}

	@Test
	public void test1() {

		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(6);
		list.add(9);
		list.add(11);
		list.add(12);
		list.forEach((x) -> System.out.println(x));
		Map<Boolean, List<Integer>> collectGroup = list.stream().collect(Collectors.groupingBy(x -> x % 2 == 0));
		System.out.println(collectGroup);
		Map<Object, Integer> map = list.stream().collect(Collectors.toMap(Function.identity(), x -> x));
		System.out.println(map);
		String newStr = list.stream().map(x -> "" + x).collect(Collectors.joining("--", "!", "!"));
		System.out.println(newStr);
		List<Integer> resultList = list.stream().collect(new Collector<Integer, filterThree, List<Integer>>() {

			// 将元素添加到结果容器
			@Override
			public BiConsumer<filterThree, Integer> accumulator() {
				// TODO Auto-generated method stub
				return filterThree::doSomething;
			}

			@Override
			public Set<Characteristics> characteristics() {
				// TODO Auto-generated method stub
				Set<Collector.Characteristics> CH_NOID = Collections.emptySet();
				return CH_NOID;
			}

			// 将两个结果容器合并为一个结果容器
			@Override
			public BinaryOperator<filterThree> combiner() {
				// TODO Auto-generated method stub
				return filterThree::doCombine;
			}

			// 对结果容器作相应的变换
			@Override
			public Function<filterThree, List<Integer>> finisher() {
				// TODO Auto-generated method stub
				return filterThree::toValue;
			}

			// 创建新的结果集
			@Override
			public Supplier<filterThree> supplier() {
				// TODO Auto-generated method stub
				return () -> new filterThree();
			}
		});
		System.out.println(resultList);

		List<Info> list1 = new ArrayList<>();
		list1.add(new Info("qq", 200));
		list1.add(new Info("qq", 404));
		list1.add(new Info("qq", 200));
		list1.add(new Info("qq", 200));
		boolean flag = list1.stream().anyMatch((x) -> x.getCode() == 404);
		System.out.println(flag);

	}

	@Test
	public void test2() {
		int a = '1';
		char c = '1';
		System.out.println(a);// 49
		System.out.println(c);// 1
		System.out.println((int) c);// 49
	}
}

// 自定义容器
// 实现 筛选出3的倍数
class filterThree {

	// 用于保存结果
	List<Integer> list = new ArrayList<>();

	public filterThree() {
	}

	// 来一个元素该怎么办
	// 接受一个元素，处理元素，生成结果
	public filterThree doSomething(int y) {
		if (y % 3 == 0) {
			list.add(y);
		}
		return this;
	}

	// 来一个结果怎么办
	// 接收一个结果(上面生成的结果或者本身生成的结果)， 生成新结果
	public filterThree doCombine(filterThree z) {
		this.list.addAll(z.list);
		return this;
	}

	// 最终结果怎么办
	// 输出
	public List<Integer> toValue() {
		return list;
	}

}
