import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Cuenta {
    // Atributos de instancia
    private String codCuenta;
    private double saldo;
    private String nombreCuentaHabiente;
    private String fechaCreacion;
    private int cantDepositosRealizados;
    private int cantRetirosExitososRealizados;
    
    // Atributo de clase
    private static int cantCuentasCreadas = 0;
    private static final String PREFIJO_CODIGO = "cta-";
    
    // Constructores
    public Cuenta(String nombreCuentaHabiente, double pSaldo) {
        this.nombreCuentaHabiente = nombreCuentaHabiente;
        this.saldo = pSaldo;
        this.cantDepositosRealizados = 0;
        this.cantRetirosExitososRealizados = 0;
        this.fechaCreacion = obtenerFechaActual();
        this.codCuenta = generarCodigoCuenta();
        cantCuentasCreadas++;
    }
    
    public Cuenta(double pSaldo) {
        this("Sin nombre", pSaldo);
    }
    
    // Métodos de instancia
    public void setNombreCuentaHabiente(String pNombreCuentaHabiente) {
        this.nombreCuentaHabiente = pNombreCuentaHabiente;
    }
    
    public String getCodCuenta() {
        return codCuenta;
    }
    
    public double getSaldo() {
        return saldo;
    }
    
    public double depositar(double monto) {
        if (monto > 0) {
            saldo += monto;
            cantDepositosRealizados++;
        }
        return saldo;
    }
    
    public double retirar(double monto) {
        if (validarRetiro(monto)) {
            saldo -= monto;
            cantRetirosExitososRealizados++;
        }
        return saldo;
    }
    
    private boolean validarRetiro(double monto) {
        return monto > 0 && monto <= saldo;
    }
    
    // Método de clase
    public static int getCantCuentasCreadas() {
        return cantCuentasCreadas;
    }
    
    @Override
    public String toString() {
        return String.format("Cuenta [Código: %s, Nombre: %s, Saldo: %.2f, " +
                           "Fecha Creación: %s, Depósitos: %d, Retiros Exitosos: %d]",
                           codCuenta, nombreCuentaHabiente, saldo, fechaCreacion,
                           cantDepositosRealizados, cantRetirosExitososRealizados);
    }
    
    // Métodos auxiliares privados
    private String generarCodigoCuenta() {
        return PREFIJO_CODIGO + (cantCuentasCreadas + 1);
    }
    
    private String obtenerFechaActual() {
        Date fecha = new Date(System.currentTimeMillis());
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatoFecha.format(fecha);
    }
    
    public String getNombreCuentaHabiente() {
    return nombreCuentaHabiente;
    }
}