package com.gugawag.rpc.banco;

import java.io.Serializable;

public class Conta implements Serializable {
    private String numero;
    private Double saldo;

    public Conta() {}

    public Conta(String numero, Double saldo) {
        this.numero = numero;
        this.saldo = saldo;
    }

    public Conta(String numero) {
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}
