/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominios;

import java.awt.image.BufferedImage;

/**
 *
 * @author Usuario
 */
public class Imagen {
    private String NombreImagen;
    private String TipoExtension;
    private int TamañoFilas;
    private int TamañoColumnas;
    private BufferedImage BuferDeImagenOriginal;
    
    public Imagen(){
        
    }
    
    public void setNombreImagen(String nombreImagen){
        if(nombreImagen.equals("")){
            return;
        }
        
        NombreImagen = nombreImagen;
    } 
    
    public String getNombreImagen(){
        return NombreImagen;
    }

    public String getTipoExtension() {
        return TipoExtension;
    }

    public int getTamañoFilas() {
        return TamañoFilas;
    }

    public int getTamañoColumnas() {
        return TamañoColumnas;
    }

    public BufferedImage getBuferDeImagenOriginal() {
        return BuferDeImagenOriginal;
    }

    public void setTipoExtension(String TipoExtension) {
        this.TipoExtension = TipoExtension;
    }

    public void setTamañoFilas(int TamañoFilas) {
        this.TamañoFilas = TamañoFilas;
    }

    public void setTamañoColumnas(int TamañoColumnas) {
        this.TamañoColumnas = TamañoColumnas;
    }

    public void setBuferDeImagenOriginal(BufferedImage BuferDeImagenOriginal) {
        this.BuferDeImagenOriginal = BuferDeImagenOriginal;
    }
    
    
}
