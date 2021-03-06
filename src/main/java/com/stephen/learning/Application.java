package com.stephen.learning;

import com.stephen.learning.view.UserView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jack
 */
@SpringBootApplication
public class Application extends AbstractJavaFxApplicationSupport {
    public static void main(String[] args) {
        //launch(Application.class, BootstrapView.class, args);
        launch(Application.class, UserView.class, args);

    }
}
