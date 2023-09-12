package edu.vsu.putinpa;

import edu.vsu.putinpa.model.Client;
import edu.vsu.putinpa.repository.ClientRepository;
import edu.vsu.putinpa.repository.impl.ClientRepositoryInMemory;
import edu.vsu.putinpa.service.ClientService;
import edu.vsu.putinpa.service.impl.ClientServiceImpl;

import java.util.Collection;

public class BankAccountApplication {
    public static void main(String[] args) {
        ClientRepository clientRepository = ClientRepositoryInMemory.init();
        ClientService clientService = ClientServiceImpl.init(clientRepository);

        clientService.register("Pavel");
        clientService.register("Alexander");

        Collection<Client> all = clientService.getAll();
        System.out.printf("Total: %d%n", all.size());
        all.forEach(client -> System.out.printf("%s %s%n", client.getUuid(), client.getName()));
    }
}
