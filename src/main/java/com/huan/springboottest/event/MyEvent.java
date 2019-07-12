package com.huan.springboottest.event;

import org.springframework.context.ApplicationEvent;

/**
 * @Author: Huan
 * @CreateTime: 2019-07-12 09:12
 */
public class MyEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public MyEvent(Object source) {
        super(source);
    }
}
