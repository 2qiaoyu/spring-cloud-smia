package com.joham.organization.events.source;

import com.joham.organization.events.models.OrganizationChangeModel;
import com.joham.organization.utils.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author joham
 */
@Component
public class SimpleSourceBean {

    @Autowired
    private Source source;

    private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);

    public void publishOrgChange(String action, String orgId) {
        logger.info("Sending Kafka message {} for Organization Id: {}", action, orgId);
        OrganizationChangeModel change = new OrganizationChangeModel(
                OrganizationChangeModel.class.getTypeName(),
                action,
                orgId,
                UserContext.getCorrelationId());

        source.output().send(MessageBuilder.withPayload(change).build());
    }
}
