package com.eba.appgastos.dtos;

import java.math.BigDecimal;

public class AhorroDto {
    private Long id;
    private String nombre;
    private Long fechaInicio;
    private BigDecimal montoAhorrado;
    private BigDecimal montoMeta;

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

    public Long getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public BigDecimal getMontoMeta() {
        return montoMeta;
    }

    public void setMontoMeta(BigDecimal montoMeta) {
        this.montoMeta = montoMeta;
    }

    public BigDecimal getMontoAhorrado() {
        return montoAhorrado;
    }

    public void setMontoAhorrado(BigDecimal montoAhorrado) {
        this.montoAhorrado = montoAhorrado;
    }

    @Override
    public String toString() {
        return "AhorroDto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", montoAhorrado=" + montoAhorrado +
                ", montoMeta=" + montoMeta +
                '}';
    }
}
