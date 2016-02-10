package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

import entity.impl.TicketImpl;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class HtmlTemplateRenderer {
    private static final String PATH = "C:\\Users\\Владелец\\IdeaProjects\\Bileton\\src\\main\\webapp\\ticketsPdf";
    private static final String TEMPLATE = "ticket.vsl";

    private HtmlTemplateRenderer(){}

    public static List<String> render(List<TicketImpl> tickets) {
        VelocityContext context = new VelocityContext();
        List<String> fileNames = new LinkedList<>();
        for(TicketImpl ticket:tickets) {
            String FILENAME = ticket.getBarcode()+".html";
            fileNames.add(FILENAME);
            context.put("img", "1412317350.jpg");
            context.put("play", ticket.getPlay());
            context.put("date", ticket.getDate());
            context.put("time", ticket.getTime());
            context.put("barcode", ticket.getBarcode());
            context.put("theatre", ticket.getTheatre());
            context.put("troupe", ticket.getTroupe());
            context.put("actors", ticket.getActors());
            context.put("price", ticket.getPrice());
            context.put("row", ticket.getRow());
            context.put("place", ticket.getPlace());

            VelocityEngine velocityEngine = new VelocityEngine();

            velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            StringWriter writer = new StringWriter();
            velocityEngine.mergeTemplate(TEMPLATE, "utf-8", context, writer);
            File file = new File(PATH, FILENAME);
            System.out.println(file);
            try (FileOutputStream fop = new FileOutputStream(file)) {
                if (!file.exists()) {
                    file.createNewFile();
                }

                byte[] contentInBytes = writer.toString().getBytes();
                fop.write(contentInBytes);

                fop.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileNames;
        /*
         String mailMessage = writer.toString();
         mailSender.sendMail(mailMessage, .........)
         */
    }
}
