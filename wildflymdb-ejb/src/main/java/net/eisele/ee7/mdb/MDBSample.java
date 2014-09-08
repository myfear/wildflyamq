package net.eisele.ee7.mdb;

/**
 *
 * @author myfear
 */
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author myfear
 */
@MessageDriven(name = "MDBSample", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "JMSBridgeTargetQ")
    //,@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})
public class MDBSample implements MessageListener {

    static final Logger logger = Logger.getLogger(MDBSample.class.getName());

    @Override
    public void onMessage(Message message) {
        TextMessage tm = (TextMessage) message;
        try {
            logger.log(Level.INFO, "Received message {0}", tm.getText());
        } catch (JMSException e) {
            logger.log(Level.INFO, e.getMessage(), e);
        }
    }
}
