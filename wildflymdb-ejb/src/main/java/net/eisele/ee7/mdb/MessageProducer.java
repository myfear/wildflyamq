package net.eisele.ee7.mdb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.jms.CompletionListener;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.transaction.Transactional;
import static net.eisele.ee7.mdb.MDBSample.logger;

/**
 *
 * @author myfear
 */
@Singleton
@Startup
public class MessageProducer {

    @Inject
    private Instance<JMSContext> jmsCtx;
    //JMSContext context;
    //HQ212051: Invalid concurrent session usage.
    //https://issues.jboss.org/browse/WFLY-3338
    
    //http://blog.c2b2.co.uk/2014/01/connecting-jboss-wildfly-7-to-activemq.html
    //http://java.dzone.com/articles/connecting-jboss-wildfly-7
    
    //https://developer.jboss.org/wiki/HowToUseOutOfProcessActiveMQWithWildFly

    static final Logger logger = Logger.getLogger(MessageProducer.class.getName());

    @Resource(mappedName = "java:/queue/JMSBridgeSourceQ")
    Queue queue;

    @Transactional
    public void sendMessage(String message) {
        // context.createProducer().send(queue, message);
        jmsCtx.get().createProducer().setDisableMessageID(true).setDisableMessageTimestamp(true).send(queue, message);
    }

    @Schedule(hour = "*", minute = "*", second = "*/30", info = "Every 30 second timer")
    public void pingMessage() {
        logger.log(Level.INFO, "sending message from producer");
        sendMessage("Its time!");
    }

}
