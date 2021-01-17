package ng.tellerassist.tellerassist.tags;

import ng.tellerassist.tellerassist.entity.TellerAssistBenchmarkSpeed;
import ng.tellerassist.tellerassist.serviceDefinition.TransactionServiceDefinition;
import javax.inject.Inject;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import static javax.servlet.jsp.tagext.Tag.SKIP_BODY;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

@Configurable
public class BenchmarkSpeedDisplayHandler extends RequestContextAwareTag {

    @Inject
    private TransactionServiceDefinition serviceDefinition;

//    @Override
//    public void doTag() throws JspException {
//        
//        TellerAssistBenchmarkSpeed tellerAssistBenchmark = (TellerAssistBenchmarkSpeed)this.serviceDefinition.loadEntity(TellerAssistBenchmarkSpeed.class);
//        
//        JspWriter out = getJspContext().getOut();
//        
//        try {
//            // TODO: insert code to write html before writing the body content.
//            // e.g.:
//            //
//            // out.println("<strong>" + attribute_1 + "</strong>");
//            // out.println("    <blockquote>");
//
//            out.write(tellerAssistBenchmark.getConfigValue().toString());
//            JspFragment f = getJspBody();
//            if (f != null) {
//                f.invoke(out);
//            }
//
//            // TODO: insert code to write html after writing the body content.
//            // e.g.:
//            //
//            // out.println("    </blockquote>");
//        } catch (java.io.IOException ex) {
//            throw new JspException("Error in AvgSpeedDisplayHandler tag", ex);
//        }
//    }
    @Override
    protected int doStartTagInternal() throws Exception {

        JspWriter out = this.pageContext.getOut();
        try {
            if (serviceDefinition == null) {
                WebApplicationContext wac = getRequestContext().getWebApplicationContext();
                AutowireCapableBeanFactory acbf = wac.getAutowireCapableBeanFactory();
                acbf.autowireBean(this);
                TellerAssistBenchmarkSpeed tellerAssistBenchmark = (TellerAssistBenchmarkSpeed) this.serviceDefinition.loadEntity(TellerAssistBenchmarkSpeed.class);
                out.write(tellerAssistBenchmark.getConfigValue().toString());
            }

        } catch (java.io.IOException ex) {
            throw new JspException("Error in AvgSpeedDisplayHandler tag", ex);
        }

        return SKIP_BODY;
    }

}
