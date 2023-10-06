package edu.vsu.putinpa.application;

import edu.vsu.putinpa.application.service.AccountsService;
import edu.vsu.putinpa.infrastructure.di.AnnotationContext;

public class BankAccountApplication {
    public static void main(String[] args) throws Exception {
        AnnotationContext context = new AnnotationContext("edu.vsu.putinpa");
        AccountsService service = context.getComponent("AccountsServiceImpl", AccountsService.class);
        System.out.println(service);
    }
}
