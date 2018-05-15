package cn.inphase.service;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener {

	@EventListener
	public void listener1(MyEvent event) {
		System.out.println("监听到自定义事件");
		System.out.println("事件内容为" + event.getEventContent());

	}

	@EventListener
	public void phoneListener2(PhoneEvent event) {
		System.out.println("收到电话了");
		System.out.println("是否拦截:" + event.isBlock());

	}

	@EventListener
	public void phoneListener2(String event) {
		System.out.println("你的意思是：" + event);

	}

	// 监听spring初始化完成事件
	@EventListener
	public void FirstEventListener(ContextRefreshedEvent event) {
		// 防止多个容器启动执行多次，只在父容器初始化后执行
		if (event.getApplicationContext().getParent() == null) {
			System.out.println("spring初始化完成！！！");
		}
	}

}
