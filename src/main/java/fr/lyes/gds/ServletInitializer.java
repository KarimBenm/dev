package fr.lyes.gds;

import org.springframework.boot.web.servlet.support.*;
import org.springframework.boot.builder.*;

public class ServletInitializer extends SpringBootServletInitializer
{
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(new Class[] { GdsApplication.class });
    }
}