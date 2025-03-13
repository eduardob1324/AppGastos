package com.eba.appgastos.dtos;

import java.math.BigDecimal;

public class GastoDto {

    private Long id;
    private String nombre;
    private BigDecimal monto;
    private Long fecha;
    private int monthYear;
    private char tipo;
    private char tipoPago;
    private char esAhorro;
    private Long idAhorro;
    private Long idGastoFijo;

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

    public Long getFecha() {
        return fecha;
    }

    public void setFecha(Long fecha) {
        this.fecha = fecha;
    }

    public char getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(char tipoPago) {
        this.tipoPago = tipoPago;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public char getEsAhorro() {
        return esAhorro;
    }

    public void setEsAhorro(char esAhorro) {
        this.esAhorro = esAhorro;
    }

    public Long getIdAhorro() {
        return idAhorro;
    }

    public void setIdAhorro(Long idAhorro) {
        this.idAhorro = idAhorro;
    }

    public Long getIdGastoFijo() {
        return idGastoFijo;
    }

    public void setIdGastoFijo(Long idGastoFijo) {
        this.idGastoFijo = idGastoFijo;
    }

    public int getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(int monthYear) {
        this.monthYear = monthYear;
    }

    @Override
    public String toString() {
        return "GastoDto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", monto=" + monto +
                ", fecha=" + fecha +
                ", monthYear=" + monthYear +
                ", tipo=" + tipo +
                ", tipoPago=" + tipoPago +
                ", esAhorro=" + esAhorro +
                ", idAhorro=" + idAhorro +
                ", idGastoFijo=" + idGastoFijo +
                '}';
    }
}
