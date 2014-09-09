/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.eisele.ee7.mdb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
//import javax.transaction.Transactional;

/**
 *
 * @author myfear
 */
@Stateless
public class MessageSender {

    @Inject
    private Instance<JMSContext> jmsCtx;
    //JMSContext context;
    //HQ212051: Invalid concurrent session usage.
    //https://issues.jboss.org/browse/WFLY-3338

    //http://blog.c2b2.co.uk/2014/01/connecting-jboss-wildfly-7-to-activemq.html
    //http://java.dzone.com/articles/connecting-jboss-wildfly-7
    //https://developer.jboss.org/wiki/HowToUseOutOfProcessActiveMQWithWildFly
    static final Logger logger = Logger.getLogger(MessageSender.class.getName());

    @Resource(mappedName = "java:/queue/JMSBridgeSourceQ")
    Queue queue;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void sendMessage(String message) {
        // context.createProducer().send(queue, message);
        jmsCtx.get().createProducer().setDisableMessageID(true).setDisableMessageTimestamp(true).send(queue, message);
        logger.log(Level.INFO, "Message Send!");
    }

}
