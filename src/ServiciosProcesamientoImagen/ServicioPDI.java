/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiciosProcesamientoImagen;

import Dominios.Imagen;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

/**
 *
 * @author Usuario
 */
public class ServicioPDI extends Imagen {

    private int x, y, valorPixelEscalaGris, ValorPixelRojo, ValorPixelAzul, ValorPixelVerde;
    private int[][] CanalVerde, CanalAzul, CanalRojo, ImagenEscalaDeGrises, ImagenBinaria;
    private BufferedImage BuferImagenReemplazado = null;
    private Color colorAuxiliar;

    public void InicializarComponentes(Imagen _imagen) {
        Imagen imagen = _imagen;
        setTamañoColumnas(imagen.getBuferDeImagenOriginal().getWidth());
        setTamañoFilas(imagen.getBuferDeImagenOriginal().getHeight());

        //Inicialización de la imagen
        BuferImagenReemplazado = _imagen.getBuferDeImagenOriginal();
        CanalRojo = new int[getTamañoFilas()][getTamañoColumnas()];
        CanalVerde = new int[getTamañoFilas()][getTamañoColumnas()];
        CanalAzul = new int[getTamañoFilas()][getTamañoColumnas()];

        for (y = 0; y < getTamañoFilas(); y++) {
            for (x = 0; x < getTamañoColumnas(); x++) {
                colorAuxiliar = new Color(BuferImagenReemplazado.getRGB(x, y));

                ValorPixelRojo = colorAuxiliar.getRed();
                ValorPixelVerde = colorAuxiliar.getGreen();
                ValorPixelAzul = colorAuxiliar.getBlue();

                CanalRojo[y][x] = ValorPixelRojo;
                CanalVerde[y][x] = ValorPixelVerde;
                CanalAzul[y][x] = ValorPixelAzul;
            }
        }
    }

    public BufferedImage AlterarBrillo(int Intensidad) {
        int CanalRojoCopy[][] = CanalRojo;
        int CanalVerdeCopy[][] = CanalVerde;
        int CanalAzulCopy[][] = CanalAzul;

        for (y = 0; y < getTamañoFilas(); y++) {
            for (x = 0; x < getTamañoColumnas(); x++) {

                int valorBrilloRojo = CanalRojoCopy[y][x] + Intensidad;
                int valorBrilloVerde = CanalVerdeCopy[y][x] + Intensidad;
                int valorBrilloAzul = CanalAzulCopy[y][x] + Intensidad;

                CanalRojoCopy[y][x]
                        = valorBrilloRojo > 255
                                ? 255 : valorBrilloRojo < 0
                                        ? 0 : valorBrilloRojo;

                CanalVerdeCopy[y][x]
                        = valorBrilloVerde > 255
                                ? 255 : valorBrilloVerde < 0
                                        ? 0 : valorBrilloVerde;

                CanalAzulCopy[y][x]
                        = valorBrilloAzul > 255
                                ? 255 : valorBrilloAzul < 0
                                        ? 0 : valorBrilloAzul;
                
                BuferImagenReemplazado.setRGB(x, y, new Color(CanalRojoCopy[y][x], CanalVerdeCopy[y][x], CanalAzulCopy[y][x]).getRGB());
            }
        }
        return BuferImagenReemplazado;
    }

}
