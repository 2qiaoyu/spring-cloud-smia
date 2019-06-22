package com.joham.licenses.events;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author joham
 */
public interface CustomChannels {

    /**
     * 自定义通道
     * @return
     */
    @Input("inboundOrgChanges")
    SubscribableChannel orgs();
}
