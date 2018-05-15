package cn.inphase.service;

import org.springframework.context.ApplicationEvent;

public class MyEvent extends ApplicationEvent {

	private String eventContent;

	public MyEvent(Object source) {
		super(source);
	}

	public String getEventContent() {
		return eventContent;
	}

	public void setEventContent(String eventContent) {
		this.eventContent = eventContent;
	}

}
