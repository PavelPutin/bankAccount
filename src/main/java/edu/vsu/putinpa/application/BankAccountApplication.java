package edu.vsu.putinpa.application;

import edu.vsu.putinpa.application.controller.MvpDemoController;
import edu.vsu.putinpa.application.service.AccountsService;
import edu.vsu.putinpa.infrastructure.di.AnnotationContext;
import edu.vsu.putinpa.infrastructure.di.api.AutoInjected;
import edu.vsu.putinpa.infrastructure.di.api.Component;

@Component(name = "application")
public class BankAccountApplication {
    private final MvpDemoController mvpDemoController;

    @AutoInjected
    public BankAccountApplication(MvpDemoController mvpDemoController) {
        this.mvpDemoController = mvpDemoController;
    }

    public void start() {
        mvpDemoController.start();
    }

    public static void main(String[] args) throws Exception {
        AnnotationContext context = new AnnotationContext("edu.vsu.putinpa");
        context.getComponent("application", BankAccountApplication.class).start();
    }
}
