import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class GestorClientes {
    private final List<Cliente> clientes = new ArrayList<>();


    public boolean altaCliente(Cliente c) {
        if (buscarClientePorDNI(c.getDni()) != null) return false; // DNI único
        clientes.add(c);
        return true;
    }

    public boolean bajaCliente(String dni) {
        Cliente c = buscarClientePorDNI(dni);
        if (c != null) return clientes.remove(c);
        return false;
    }

    public boolean modificarCliente(String dni, String nuevoNombre, String nuevoTelefono, String nuevoEmail) {
        Cliente c = buscarClientePorDNI(dni);
        if (c == null) return false;
        if (nuevoNombre != null && !nuevoNombre.isBlank()) c.setNombre(nuevoNombre);
        if (nuevoTelefono != null && !nuevoTelefono.isBlank()) c.setTelefono(nuevoTelefono);
        if (nuevoEmail != null && !nuevoEmail.isBlank()) c.setEmail(nuevoEmail);
        return true;
    }

    public Cliente buscarClientePorDNI(String dni) {
        for (Cliente c : clientes) {
            if (c.getDni().equalsIgnoreCase(dni)) return c;
        }
        return null;
    }

    public void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        for (Cliente c : clientes) {
            System.out.println(c);
        }
    }

    public List<Cliente> getClientes() { return clientes; }


    public void menuClientes(Scanner sc) {
        int opcion;
        do {
            System.out.println("\n=== GESTIÓN DE CLIENTES ===");
            System.out.println("1. Alta");
            System.out.println("2. Baja");
            System.out.println("3. Modificación");
            System.out.println("4. Búsqueda por DNI");
            System.out.println("5. Listado");
            System.out.println("6. Ordenar por nombre");
            System.out.println("7. Volver");
            System.out.print("Opción: ");
            opcion = leerEntero(sc);

            switch (opcion) {
                case 1 -> altaClienteMenu(sc);
                case 2 -> bajaClienteMenu(sc);
                case 3 -> modificarClienteMenu(sc);
                case 4 -> buscarClienteMenu(sc);
                case 5 -> listarClientes();
                case 6 -> ordenarPorNombre();
                case 7 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 7);
    }

    private void altaClienteMenu(Scanner sc) {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine().trim();
        System.out.print("DNI: ");
        String dni = sc.nextLine().trim();
        System.out.print("Teléfono: ");
        String tel = sc.nextLine().trim();
        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        if (nombre.isBlank() || dni.isBlank()) {
            System.out.println("Nombre y DNI son obligatorios.");
            return;
        }
        boolean ok = altaCliente(new Cliente(nombre, dni, tel, email));
        System.out.println(ok ? "Cliente dado de alta." : "Ya existe un cliente con ese DNI.");
    }

    private void bajaClienteMenu(Scanner sc) {
        System.out.print("DNI del cliente a eliminar: ");
        String dni = sc.nextLine().trim();
        boolean ok = bajaCliente(dni);
        System.out.println(ok ? "Cliente eliminado." : "No existe un cliente con ese DNI.");
    }

    private void modificarClienteMenu(Scanner sc) {
        System.out.print("DNI del cliente a modificar: ");
        String dni = sc.nextLine().trim();
        Cliente c = buscarClientePorDNI(dni);
        if (c == null) {
            System.out.println("No existe un cliente con ese DNI.");
            return;
        }
        System.out.println("Deja en blanco para mantener el valor actual.");
        System.out.println("Actual: " + c);
        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Nuevo teléfono: ");
        String tel = sc.nextLine();
        System.out.print("Nuevo email: ");
        String email = sc.nextLine();
        boolean ok = modificarCliente(dni, nombre, tel, email);
        System.out.println(ok ? "Cliente modificado." : "No se pudo modificar.");
    }

    private void buscarClienteMenu(Scanner sc) {
        System.out.print("DNI a buscar: ");
        String dni = sc.nextLine().trim();
        Cliente c = buscarClientePorDNI(dni);
        System.out.println(c != null ? c : "No encontrado.");
    }

    private void ordenarPorNombre() {
        Collections.sort(clientes, Comparator.comparing(Cliente::getNombre, String.CASE_INSENSITIVE_ORDER));
        System.out.println("Clientes ordenados por nombre.");
    }


    public static int leerEntero(Scanner sc) {
        while (true) {
            String s = sc.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.print("Introduce un número válido: ");
            }
        }
    }

    public static double leerDouble(Scanner sc) {
        while (true) {
            String s = sc.nextLine().trim();
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.print("Introduce un número (p.ej. 12.5): ");
            }
        }
    }
}