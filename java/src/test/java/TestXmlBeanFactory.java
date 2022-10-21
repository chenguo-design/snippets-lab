import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class TestXmlBeanFactory {

    @SuppressWarnings("deprecation")
    @Test
    public void test(){
        XmlBeanFactory bf = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));

    }
}
