package edu.vsu.putinpa.application;

import edu.vsu.putinpa.application.service.AccountsService;
import edu.vsu.putinpa.infrastructure.di.AnnotationContext;

public class BankAccountApplication {
    public static void main(String[] args) throws Exception {
        AnnotationContext context = new AnnotationContext("edu.vsu.putinpa");
        AccountsService service = (AccountsService) context.getComponentFactory().getComponent("AccountsServiceImpl");
        System.out.println(service);
    }
}
