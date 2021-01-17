/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ng.tellerassist.tellerassist.tags;

import ng.tellerassist.tellerassist.serviceDefinition.TransactionServiceDefinition;
import java.time.LocalDateTime;
import javax.inject.Inject;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import static javax.servlet.jsp.tagext.Tag.SKIP_BODY;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

@Configurable
public class AvgSpeedDisplayHandler extends RequestContextAwareTag {

    PageContext pageCxt;
    @Inject
    private TransactionServiceDefinition serviceDefinition;
//   @Override
//    public void doTag() throws JspException {
//        JspWriter out = getJspContext().getOut();
//        try {
//            out.write(serviceDefinition.getCurrentAvgSpeed().toString());
//            // TODO: insert code to write html before writing the body content.
//            // e.g.:
//            //
//            // out.println("<strong>" + attribute_1 + "</strong>");
//            // out.println("    <blockquote>");
//
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

        pageCxt = this.pageContext;
        JspWriter out = this.pageContext.getOut();
        try {
            if (serviceDefinition == null) {
                WebApplicationContext wac = getRequestContext().getWebApplicationContext();
                AutowireCapableBeanFactory acbf = wac.getAutowireCapableBeanFactory();
                acbf.autowireBean(this);
                out.write(serviceDefinition.getCurrentAvgSpeed(LocalDateTime.now()).toString());
            }

        } catch (java.io.IOException ex) {
            throw new JspException("Error in AvgSpeedDisplayHandler tag", ex);
        }

        return SKIP_BODY;
    }
}
