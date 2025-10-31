package com.example.inmobiliarialucero.modelo;

public class Contrato {
    private int idContrato;
    private String fechaInicio;
    private String fechaFin;
    private double montoAlquiler;
    private int idInquilino;
    private int idInmueble;

    // Constructor, getters y setters;
    public Contrato(){
    }

    public Contrato(int idContrato, String fechaInicio, String fechaFin, double montoAlquiler, int idInquilino, int idInmueble) {
        this.idContrato = idContrato;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.montoAlquiler = montoAlquiler;
        this.idInquilino = idInquilino;
        this.idInmueble = idInmueble;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getMontoAlquiler() {
        return montoAlquiler;
    }

    public void setMontoAlquiler(double montoAlquiler) {
        this.montoAlquiler = montoAlquiler;
    }

    public int getIdInquilino() {
        return idInquilino;
    }

    public void setIdInquilino(int idInquilino) {
        this.idInquilino = idInquilino;
    }

    public int getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(int idInmueble) {
        this.idInmueble = idInmueble;
    }
}
