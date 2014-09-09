package net.eisele.ee7.mdb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 * @author myfear
 */
@Singleton
@Startup
public class MessageProducer {

    @Inject
    private MessageSender sender;
    //JMSContext context;
    //HQ212051: Invalid concurrent session usage.
    //https://issues.jboss.org/browse/WFLY-3338

    //http://blog.c2b2.co.uk/2014/01/connecting-jboss-wildfly-7-to-activemq.html
    //http://java.dzone.com/articles/connecting-jboss-wildfly-7
    //https://developer.jboss.org/wiki/HowToUseOutOfProcessActiveMQWithWildFly
    static final Logger logger = Logger.getLogger(MessageProducer.class.getName());

    @Schedule(hour = "*", minute = "*", second = "*/30", info = "Every 30 second timer", persistent = false)
    public void pingMessage() {
        sender.sendMessage("Its time!");
    }

}
