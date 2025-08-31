import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GestorVentas {
    private final List<Venta> ventas = new ArrayList<>();

    public void nuevaVenta(Scanner sc, GestorClientes gc, GestorPajaros gp) {
        if (gc.getClientes().isEmpty()) {
            System.out.println("No hay clientes. Da de alta un cliente primero.");
            return;
        }
        if (gp.getCatalogo().isEmpty()) {
            System.out.println("No hay pájaros en catálogo.");
            return;
        }

        System.out.print("DNI del cliente: ");
        String dni = sc.nextLine().trim();
        Cliente cliente = gc.buscarClientePorDNI(dni);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        Venta venta = new Venta(cliente);
        boolean agregando = true;
        while (agregando) {
            System.out.println("\nCatálogo actual (solo con stock):");
            List<Integer> indicesDisponibles = listarCatalogoConStock(gp);
            if (indicesDisponibles.isEmpty()) {
                System.out.println("No hay stock disponible. No se pueden añadir más pájaros.");
                break;
            }
            System.out.print("Selecciona nº de línea a añadir (0 para terminar): ");
            int opcion = GestorClientes.leerEntero(sc);
            if (opcion == 0) break;
            if (opcion < 1 || opcion > indicesDisponibles.size()) {
                System.out.println("Selección inválida.");
                continue;
            }
            int idxCatalogo = indicesDisponibles.get(opcion - 1);
            Pajaro elegido = gp.getCatalogo().get(idxCatalogo);

            if (elegido.getStock() <= 0) {
                System.out.println("Sin stock para ese pájaro.");
            } else {
                elegido.setStock(elegido.getStock() - 1);
                venta.addPajaro(elegido);
                System.out.println("Añadido: " + elegido);
            }
        }

        if (venta.getLineasDeVenta().isEmpty()) {
            System.out.println("Venta cancelada: no se añadieron pájaros.");
            return;
        }
        ventas.add(venta);
        System.out.println("Venta creada correctamente. Total: " + String.format("%.2f", venta.calcularTotal()) + "€");
    }

    private List<Integer> listarCatalogoConStock(GestorPajaros gp) {
        List<Integer> indicesVisibles = new ArrayList<>();
        List<Pajaro> cat = gp.getCatalogo();
        int visible = 1;
        for (int i = 0; i < cat.size(); i++) {
            Pajaro p = cat.get(i);
            if (p.getStock() > 0) {
                System.out.println(visible + ". " + p);
                indicesVisibles.add(i);
                visible++;
            }
        }
        return indicesVisibles;
    }

    public void mostrarVentas() {
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }
        for (Venta v : ventas) {
            System.out.println(v);
        }
    }

    public void mostrarVentasPorCliente(Scanner sc) {
        System.out.print("DNI del cliente: ");
        String dni = sc.nextLine().trim();
        boolean encontrado = false;
        double totalCliente = 0.0;
        for (Venta v : ventas) {
            if (v.getCliente().getDni().equalsIgnoreCase(dni)) {
                System.out.println(v);
                totalCliente += v.calcularTotal();
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No hay ventas para ese cliente.");
        } else {
            System.out.println("Importe total de ventas del cliente: " + String.format("%.2f", totalCliente) + "€");
        }
    }


    public void mostrarTotalesPorCliente() {
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }
        Map<String, Double> acumulados = new HashMap<>();
        for (Venta v : ventas) {
            String dni = v.getCliente().getDni();
            acumulados.put(dni, acumulados.getOrDefault(dni, 0.0) + v.calcularTotal());
        }
        System.out.println("\n=== Total de ventas por cliente ===");
        for (Map.Entry<String, Double> e : acumulados.entrySet()) {
            System.out.println("DNI " + e.getKey() + ": " + String.format("%.2f", e.getValue()) + "€");
        }
    }
}
