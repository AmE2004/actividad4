import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBook {

    // HashMap donde se guardan los contactos
    private HashMap<String, String> contacts;
    private final String fileName = "contacts.txt";

    public AddressBook() {
        contacts = new HashMap<>();
        load();
    }

    // Cargar contactos desde el archivo
    public void load() {
        File file = new File(fileName);

        if (!file.exists()) {
            return; // si no existe, no hace nada
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length == 2) {
                    contacts.put(data[0], data[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar contactos");
        }
    }

    // Guardar contactos en el archivo
    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar contactos");
        }
    }

    // Mostrar contactos
    public void list() {
        System.out.println("\nContactos:");

        if (contacts.isEmpty()) {
            System.out.println("No hay contactos guardados");
            return;
        }

        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    // Crear contacto
    public void create(Scanner scanner) {
        System.out.print("Ingresa el número: ");
        String number = scanner.nextLine();

        if (contacts.containsKey(number)) {
            System.out.println("Ese número ya existe");
            return;
        }

        System.out.print("Ingresa el nombre: ");
        String name = scanner.nextLine();

        contacts.put(number, name);
        save();
        System.out.println("Contacto agregado");
    }

    // Borrar contacto
    public void delete(Scanner scanner) {
        System.out.print("Ingresa el número a eliminar: ");
        String number = scanner.nextLine();

        if (contacts.remove(number) != null) {
            save();
            System.out.println("Contacto eliminado");
        } else {
            System.out.println("Número no encontrado");
        }
    }

    // Menú interactivo
    public void menu() {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n--- AGENDA TELEFÓNICA ---");
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Eliminar contacto");
            System.out.println("4. Salir");
            System.out.print("Elige una opción: ");

            option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1:
                    list();
                    break;
                case 2:
                    create(scanner);
                    break;
                case 3:
                    delete(scanner);
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida");
            }

        } while (option != 4);
    }

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook();
        addressBook.menu();
    }

}
