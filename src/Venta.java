import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private Cliente cliente;
    private List<Pajaro> lineasDeVenta;
    private LocalDate fecha;

    public Venta(Cliente cliente) {
        this.cliente = cliente;
        this.fecha = LocalDate.now();
        this.lineasDeVenta = new ArrayList<>();
    }

    public Cliente getCliente() { return cliente; }
    public LocalDate getFecha() { return fecha; }
    public List<Pajaro> getLineasDeVenta() { return lineasDeVenta; }

    public void addPajaro(Pajaro p) {
        lineasDeVenta.add(p);
    }

    public double calcularTotal() {
        double total = 0.0;
        for (Pajaro p : lineasDeVenta) {
            total += p.getPrecio();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Venta {fecha=").append(fecha)
                .append(", cliente=").append(cliente.getNombre())
                .append(" (DNI ").append(cliente.getDni()).append(")")
                .append(", total=").append(String.format("%.2f", calcularTotal())).append("€");
        sb.append(", items=");
        for (int i = 0; i < lineasDeVenta.size(); i++) {
            Pajaro p = lineasDeVenta.get(i);
            sb.append(p.getEspecie()).append("-" ).append(p.getColor())
                    .append("(").append(String.format("%.2f", p.getPrecio())).append("€)");
            if (i < lineasDeVenta.size() - 1) sb.append(", ");
        }
        sb.append("}");
        return sb.toString();
    }
}