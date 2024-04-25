package main.org.si3ri.convert.model;

public record Currency(double USD, double EUR, double BRL, double COP, double ARS, double MXN) {
    @Override
    public String toString() {
        return "Valor actual de las divisas: [USD=" + USD + ", EUR=" + EUR + ", BRL=" + BRL + ", COP=" + COP + ", ARS=" + ARS + ", MXN=" + MXN + "]";
    }
}