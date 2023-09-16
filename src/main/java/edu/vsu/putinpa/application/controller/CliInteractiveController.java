package edu.vsu.putinpa.application.controller;

import edu.vsu.putinpa.application.exception.SingletonInstanceNotInitializedException;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.service.ClientService;

import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

public class CliInteractiveController {
    private static CliInteractiveController INSTANCE;
    private final Scanner in;
    private final PrintStream out;
    private final ClientService clientService;

    private CliInteractiveController(Scanner in, PrintStream out, ClientService clientService) {
        this.in = in;
        this.out = out;
        this.clientService = clientService;
    }

    public synchronized static CliInteractiveController init(Scanner in, PrintStream out, ClientService clientService) {
        INSTANCE = new CliInteractiveController(in, out, clientService);
        return INSTANCE;
    }

    public static CliInteractiveController getInstance() {
        if (INSTANCE == null) {
            throw new SingletonInstanceNotInitializedException(
                    "Class '%s' instance not initialized yet. You should invoke method 'init'."
                            .formatted(CliInteractiveController.class)
            );
        }

        return INSTANCE;
    }

    private final Map<String, Consumer<Scanner>> commands;

    {
        commands = new HashMap<>();
        commands.put("post client", this::registerNewClient);
        commands.put("get client", this::showAllClients);
    }

    public void start() {
        out.print("Enter command: ");
        while (in.hasNextLine()) {
            String next = in.nextLine();

            if (next.equals("stop")) {
                break;
            }

            if (!commands.containsKey(next)) {
                System.out.println("Command not found");
            } else {
                commands.get(next).accept(in);
            }
            out.print("Enter command: ");
        }

        in.close();
        out.close();
    }

    public void registerNewClient(Scanner scanner) {
        out.print("Enter name: ");
        // TODO: добавить валидацию на hasNext
        String argument = scanner.nextLine();
        // TODO: добавить валидацию на пустое имя
        String name = argument;
        clientService.register(name);
    }

    private void showAllClients(Scanner scanner) {
        Collection<Client> clients = clientService.getAll();
        if (clients.isEmpty()) {
            System.out.println("There are not users");
        } else {
            clients.forEach(client -> System.out.printf("%s %s%n", client.getUuid(), client.getName()));
        }
    }
}
