package app;

import java.io.IOException;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;


public class Main {

    public static void main(String[] args) {

        // Enviroment variables med afsender email adresse, SendGrid API nøgle og SendGrid template id
        String FROM_EMAIL_ADDRESS = System.getenv("FROM_EMAIL_ADDRESS");
        String SENDGRID_API_KEY = System.getenv("SENDGRID_API_KEY");
        String SENDGRID_TEMPLATE_ID = System.getenv("SENDGRID_TEMPLATE_ID");

        Email from = new Email(FROM_EMAIL_ADDRESS);
        from.setName("Byggemarked");

        Mail mail = new Mail();
        mail.setFrom(from);
        mail.templateId = SENDGRID_TEMPLATE_ID;

        /* Erstat kunde@gmail.com, name, email og zip med egne værdier ****/
        /* I test-fasen - brug din egen email, så du kan modtage beskeden */
        Personalization personalization = new Personalization();
        personalization.addTo(new Email("anders@and.com"));
        personalization.addDynamicTemplateData("name", "Anders And");
        personalization.addDynamicTemplateData("email", "anders@and.dk");
        personalization.addDynamicTemplateData("zip", "2100");
        mail.addPersonalization(personalization);

        mail.addCategory("carportapp");

        // Klargør request
        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            // Send request (dvs. send email) og modtag response
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());

        } catch (IOException ex) {
            System.out.println("Error sending mail");
        }

    }

}