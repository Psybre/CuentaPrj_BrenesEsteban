import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrincipalCuenta {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Cuenta> cuentas = new ArrayList<>();
        int cuentaActual = -1; // índice de la cuenta seleccionada

        System.out.println("======================================");
        System.out.println("   Sistema de Gestión de Cuentas");
        System.out.println("======================================");

        boolean salir = false;
        while (!salir) {
            System.out.println("\nMenú principal");
            System.out.println("1) Crear Cuenta");
            System.out.println("2) Conocer la cantidad de Cuentas Creadas");
            System.out.println("3) Listar Cuentas");
            System.out.println("4) Seleccionar Cuenta actual");
            System.out.println("5) Depositar");
            System.out.println("6) Retirar");
            System.out.println("7) Consultar Saldo");
            System.out.println("8) Consultar Estado (toString)");
            System.out.println("9) Cambiar nombre de cuenta habiente");
            System.out.println("0) Salir");
            System.out.print("Opción: ");
            String opcion = sc.nextLine().trim();

            switch (opcion) {
                case "1": // Crear Cuenta
                    crearCuenta(sc, cuentas);
                    cuentaActual = cuentas.size() - 1;
                    break;
                    
                case "2": // Cantidad de cuentas creadas
                    System.out.println("Total de cuentas creadas: " + Cuenta.getCantCuentasCreadas());
                    break;
                    
                case "3": // Listar Cuentas
                    listarCuentas(cuentas);
                    break;
                    
                case "4": // Seleccionar Cuenta actual
                    cuentaActual = seleccionarCuenta(sc, cuentas);
                    break;
                    
                case "5": // Depositar
                    realizarDeposito(sc, cuentas, cuentaActual);
                    break;
                    
                case "6": // Retirar
                    realizarRetiro(sc, cuentas, cuentaActual);
                    break;
                    
                case "7": // Consultar Saldo
                    consultarSaldo(cuentas, cuentaActual);
                    break;
                    
                case "8": // Consultar Estado
                    consultarEstado(cuentas, cuentaActual);
                    break;
                    
                case "9": // Cambiar nombre
                    cambiarNombreCuentaHabiente(sc, cuentas, cuentaActual);
                    break;
                    
                case "0": // Salir
                    salir = true;
                    System.out.println("¡Gracias por usar el sistema!");
                    break;
                    
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
        sc.close();
    }
    
    // Métodos auxiliares
    private static void crearCuenta(Scanner sc, List<Cuenta> cuentas) {
        System.out.print("¿Desea crear cuenta con nombre? (s/n): ");
        String conNombre = sc.nextLine().trim().toLowerCase();
        
        System.out.print("Saldo inicial: ");
        double saldoInicial;
        try {
            saldoInicial = Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido. Se usará saldo inicial de 0.0");
            saldoInicial = 0.0;
        }
        
        Cuenta cuenta;
        if (conNombre.equals("s")) {
            System.out.print("Nombre del cuenta habiente: ");
            String nombre = sc.nextLine().trim();
            cuenta = new Cuenta(nombre, saldoInicial);
        } else {
            cuenta = new Cuenta(saldoInicial);
        }
        
        cuentas.add(cuenta);
        System.out.println("Cuenta creada exitosamente: " + cuenta.getCodCuenta());
    }
    
    private static void listarCuentas(List<Cuenta> cuentas) {
        if (cuentas.isEmpty()) {
            System.out.println("No hay cuentas creadas.");
            return;
        }
        
        System.out.println("Listado de cuentas:");
        System.out.println("Índice | Código      | Nombre               | Saldo");
        System.out.println("---------------------------------------------------");
        for (int i = 0; i < cuentas.size(); i++) {
            Cuenta c = cuentas.get(i);
            System.out.printf("  %d    | %-10s | %-20s | %.2f%n",
                i, c.getCodCuenta(), c.getNombreCuentaHabiente(), c.getSaldo());
        }
    }
    
    private static int seleccionarCuenta(Scanner sc, List<Cuenta> cuentas) {
        if (cuentas.isEmpty()) {
            System.out.println("No hay cuentas creadas.");
            return -1;
        }
        
        listarCuentas(cuentas);
        System.out.print("Seleccione el índice de la cuenta: ");
        try {
            int indice = Integer.parseInt(sc.nextLine().trim());
            if (indice >= 0 && indice < cuentas.size()) {
                System.out.println("Cuenta seleccionada: " + cuentas.get(indice).getCodCuenta());
                return indice;
            } else {
                System.out.println("Índice fuera de rango.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Índice inválido.");
        }
        return -1;
    }
    
    private static void realizarDeposito(Scanner sc, List<Cuenta> cuentas, int cuentaActual) {
        if (cuentaActual == -1 || cuentas.isEmpty()) {
            System.out.println("Debe seleccionar una cuenta primero.");
            return;
        }
        
        System.out.print("Monto a depositar: ");
        try {
            double monto = Double.parseDouble(sc.nextLine().trim());
            Cuenta cuenta = cuentas.get(cuentaActual);
            double nuevoSaldo = cuenta.depositar(monto);
            System.out.printf("Depósito realizado. Nuevo saldo: %.2f%n", nuevoSaldo);
        } catch (NumberFormatException e) {
            System.out.println("Monto inválido.");
        }
    }
    
    private static void realizarRetiro(Scanner sc, List<Cuenta> cuentas, int cuentaActual) {
        if (cuentaActual == -1 || cuentas.isEmpty()) {
            System.out.println("Debe seleccionar una cuenta primero.");
            return;
        }
        
        System.out.print("Monto a retirar: ");
        try {
            double monto = Double.parseDouble(sc.nextLine().trim());
            Cuenta cuenta = cuentas.get(cuentaActual);
            double nuevoSaldo = cuenta.retirar(monto);
            if (nuevoSaldo == cuenta.getSaldo()) {
                System.out.println("Fondos insuficientes para realizar el retiro.");
            } else {
                System.out.printf("Retiro realizado. Nuevo saldo: %.2f%n", nuevoSaldo);
            }
        } catch (NumberFormatException e) {
            System.out.println("Monto inválido.");
        }
    }
    
    private static void consultarSaldo(List<Cuenta> cuentas, int cuentaActual) {
        if (cuentaActual == -1 || cuentas.isEmpty()) {
            System.out.println("Debe seleccionar una cuenta primero.");
            return;
        }
        
        Cuenta cuenta = cuentas.get(cuentaActual);
        System.out.printf("Saldo actual: %.2f%n", cuenta.getSaldo());
    }
    
    private static void consultarEstado(List<Cuenta> cuentas, int cuentaActual) {
        if (cuentaActual == -1 || cuentas.isEmpty()) {
            System.out.println("Debe seleccionar una cuenta primero.");
            return;
        }
        
        Cuenta cuenta = cuentas.get(cuentaActual);
        System.out.println(cuenta.toString());
    }
    
    private static void cambiarNombreCuentaHabiente(Scanner sc, List<Cuenta> cuentas, int cuentaActual) {
        if (cuentaActual == -1 || cuentas.isEmpty()) {
            System.out.println("Debe seleccionar una cuenta primero.");
            return;
        }
        
        System.out.print("Nuevo nombre del cuenta habiente: ");
        String nuevoNombre = sc.nextLine().trim();
        cuentas.get(cuentaActual).setNombreCuentaHabiente(nuevoNombre);
        System.out.println("Nombre actualizado correctamente.");
    }
}