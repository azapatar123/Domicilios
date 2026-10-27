package com.example.domicilios;

public class Pedido {
    String fechaEntrega;
    String numeroPedido;
    String direccionEntrega;
    String prioridad;
    String documentoCliente;
    String mensajeroAsignado;
    String estado;
    String creadoPor;

    public Pedido() {
    }

    public Pedido(String fechaEntrega, String numeroPedido, String direccionEntrega,
                  String prioridad, String documentoCliente, String mensajeroAsignado,
                  String estado, String creadoPor) {
        this.fechaEntrega = fechaEntrega;
        this.numeroPedido = numeroPedido;
        this.direccionEntrega = direccionEntrega;
        this.prioridad = prioridad;
        this.documentoCliente = documentoCliente;
        this.mensajeroAsignado = mensajeroAsignado;
        this.estado = estado;
        this.creadoPor = creadoPor;
    }

    public String getFechaEntrega() { return fechaEntrega; }
    public String getNumeroPedido() { return numeroPedido; }
    public String getDireccionEntrega() { return direccionEntrega; }
    public String getPrioridad() { return prioridad; }
    public String getDocumentoCliente() { return documentoCliente; }
    public String getMensajeroAsignado() { return mensajeroAsignado; }
    public String getEstado() { return estado; }
    public String getCreadoPor() { return creadoPor; }

    public void setFechaEntrega(String fechaEntrega) { this.fechaEntrega = fechaEntrega; }
    public void setNumeroPedido(String numeroPedido) { this.numeroPedido = numeroPedido; }
    public void setDireccionEntrega(String direccionEntrega) { this.direccionEntrega = direccionEntrega; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }
    public void setDocumentoCliente(String documentoCliente) { this.documentoCliente = documentoCliente; }
    public void setMensajeroAsignado(String mensajeroAsignado) { this.mensajeroAsignado = mensajeroAsignado; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setCreadoPor(String creadoPor) { this.creadoPor = creadoPor; }
}