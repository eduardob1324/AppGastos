package com.eba.appgastos.dtos;

import java.math.BigDecimal;

public class GastoFijoDto {
    private Long id;
    private String nombre;
    private BigDecimal monto;
    private BigDecimal montoAbonado;
    private char isServicio;
    private char pagado;
    private Long fechaLimitePago;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public char getPagado() {
        return pagado;
    }

    public void setPagado(char pagado) {
        this.pagado = pagado;
    }

    public Long getFechaLimitePago() {
        return fechaLimitePago;
    }

    public void setFechaLimitePago(Long fechaLimitePago) {
        this.fechaLimitePago = fechaLimitePago;
    }

    public BigDecimal getMontoAbonado() {
        return montoAbonado;
    }

    public void setMontoAbonado(BigDecimal montoAbonado) {
        this.montoAbonado = montoAbonado;
    }

    public char getIsServicio() {
        return isServicio;
    }

    public void setIsServicio(char isServicio) {
        this.isServicio = isServicio;
    }

    @Override
    public String toString() {
        return "GastoFijoDto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", monto=" + monto +
                ", montoAbonado=" + montoAbonado +
                ", isServicio=" + isServicio +
                ", pagado=" + pagado +
                ", fechaLimitePago=" + fechaLimitePago +
                '}';
    }
}
