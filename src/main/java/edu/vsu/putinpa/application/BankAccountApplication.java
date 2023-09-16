package edu.vsu.putinpa.application;

import edu.vsu.putinpa.application.controller.CliInteractiveController;
import edu.vsu.putinpa.application.repository.ClientRepository;
import edu.vsu.putinpa.application.repository.impl.ClientRepositoryInMemory;
import edu.vsu.putinpa.application.service.ClientService;
import edu.vsu.putinpa.application.service.impl.ClientServiceImpl;

import java.util.Scanner;

public class BankAccountApplication {
    public static void main(String[] args) {
        ClientRepository clientRepository = ClientRepositoryInMemory.init();
        ClientService clientService = ClientServiceImpl.init(clientRepository);
        CliInteractiveController cliInteractiveController = CliInteractiveController.init(
                new Scanner(System.in),
                System.out,
                clientService
        );

        cliInteractiveController.start();
    }
}
