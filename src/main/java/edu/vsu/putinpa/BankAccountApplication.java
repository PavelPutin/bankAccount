package edu.vsu.putinpa;

import edu.vsu.putinpa.controller.CliInteractiveController;
import edu.vsu.putinpa.repository.ClientRepository;
import edu.vsu.putinpa.repository.impl.ClientRepositoryInMemory;
import edu.vsu.putinpa.service.ClientService;
import edu.vsu.putinpa.service.impl.ClientServiceImpl;

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
