import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.logging.SimpleFormatter;


public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {

        //configuración del logger
        try {
            //crear un FileHandler que escriba en carrito.txt
            FileHandler fh = new FileHandler("carrito.txt", true);
            //formato del log
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            //añadir el handler al LOGGER
            LOGGER.addHandler(fh);


            List<String> productos = new ArrayList<>();
            List<Double> precios = new ArrayList<>();


            // Añadir productos y precios
            productos.add("Laptop");
            precios.add(1200.0);

            productos.add("Telefono");
            precios.add(600.0);

            productos.add("Auriculares");
            precios.add(150.0);

            // Intentar calcular el total de la compra
            double totalCompra = calcularTotalCompra(productos, precios, new int[]{1, 2, 1});

            // el total de la compra debería ser (1200 + 2*600 + 150)*0.85*1.21 es decir, 2622.675 (redondeamos al centimo, es decir, 2622.68)
            // Subtotal: 2550
            // Descuento: 2550*0.15 = 382.50
            // Impuestos (Tras descuento) (2550-382.5)*0.21 = 455.17
            // Total tras impuestos = 2550-382.5+455.175 = 2622.675
            System.out.println("Total de la compra con impuestos y descuento: $" + totalCompra);
        }
        catch (IOException exc){
            exc.printStackTrace();
        }
    }


    // Calcula el total de una compra en base a los productos y cantidades
    public static double calcularTotalCompra(List<String> productos, List<Double> precios, int[] cantidades) {
        double subtotal = calcularSubtotal(productos, precios, cantidades);
        double descuento = aplicarDescuento(subtotal); // Error en descuento
        double totalConDescuento = subtotal - descuento;

        // Error lógico: no se aplica correctamente la función calcularImpuestos
        double totalConImpuestos = calcularImpuestos(totalConDescuento);
        return totalConImpuestos;
    }


    // Calcula el subtotal de la compra
    public static double calcularSubtotal(List<String> productos, List<Double> precios, int[] cantidades) {
        double subtotal = 0;
        for (int i = 0; i < productos.size(); i++) {
            // Error de control: verificar si la cantidad es mayor que cero
            LOGGER.info("Calculando precio = " + productos.get(i)+
                    " (Precio: $"+precios.get(i)+
                    " Cantidad: "+ cantidades[i]+")");
            subtotal += precios.get(i) * cantidades[i];
        }
        LOGGER.info("Subtotal: $"+subtotal);
        System.out.println("Subtotal: $" + subtotal);
        return subtotal;
    }

    // Aplica un descuento de acuerdo al subtotal
    // Aplica un 15% para compras de más de 1000 euros
    // Aplica un 10% para compras entre 500 y 1000 euros
    // No aplica ningún descuento si la compra es de menos de 500 euros
    public static double aplicarDescuento(double subtotal) {
        double descuento = 0.0;
        if (subtotal > 1000) {
            descuento = subtotal * 0.15;
            LOGGER.info("Aplicando dto del 15%: $" + descuento +
                    " el nuevo subtotal: " + (subtotal - descuento) );

        } else if (subtotal > 500) {
           descuento = subtotal * 0.10;
            LOGGER.info("Aplicando dto del 10%: $" + descuento +
                    " el nuevo subtotal: " + (subtotal - descuento));
        } else {
            LOGGER.info("No se aplica descuento");
        }
        return descuento;
    }


    // Calcula los impuestos aplicados al total con descuento
    public static double calcularImpuestos(double total) {
        final double IMPUESTO = 0.21; // Impuesto del 21%
        double impuesto = (total * IMPUESTO);
        double  totalConImpuestos = (impuesto+total);
                LOGGER.info("El total con impuestos es : $" + totalConImpuestos +
                        " (subtotal: $" + total +
                        " + Impuesto: $" + impuesto + ")");

        return totalConImpuestos; // Debe devolver total + impuestos
    }
}
