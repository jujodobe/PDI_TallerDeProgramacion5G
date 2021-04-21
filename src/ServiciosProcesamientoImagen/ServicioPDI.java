/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiciosProcesamientoImagen;

import Dominios.Imagen;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Usuario
 */
public class ServicioPDI extends Imagen {

    private int x, y, valorPixelEscalaGris, ValorPixelRojo, ValorPixelAzul, ValorPixelVerde;
    private int[][] CanalVerde, CanalAzul, CanalRojo, ImagenEscalaDeGrises, ImagenBinaria,
            CanalRojoHorizontal, CanalVerdeHorizontal, CanalAzulHorizontal,
            CanalRojoVertical, CanalVerdeVertical, CanalAzulVertical;
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
        CanalRojoHorizontal = new int[getTamañoFilas()][getTamañoColumnas()];
        CanalVerdeHorizontal = new int[getTamañoFilas()][getTamañoColumnas()];
        CanalAzulHorizontal = new int[getTamañoFilas()][getTamañoColumnas()];
        CanalRojoVertical = new int[getTamañoFilas()][getTamañoColumnas()];
        CanalVerdeVertical = new int[getTamañoFilas()][getTamañoColumnas()];
        CanalAzulVertical = new int[getTamañoFilas()][getTamañoColumnas()];

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

    public BufferedImage DeteccionBordeMetodoHorizontal() {
        //P'i = | P[i + 1, j] - P[i, j] |

        for (y = 0; y < getTamañoFilas() - 1; y++) {
            for (x = 0; x < getTamañoColumnas(); x++) {

                int PixelRojo = Math.abs(CanalRojo[y + 1][x] - CanalRojo[y][x]);
                int PixelVerde = Math.abs(CanalVerde[y + 1][x] - CanalVerde[y][x]);
                int PixelAzul = Math.abs(CanalAzul[y + 1][x] - CanalAzul[y][x]);

                CanalRojoHorizontal[y][x] = PixelRojo;
                CanalVerdeHorizontal[y][x] = PixelVerde;
                CanalAzulHorizontal[y][x] = PixelAzul;

                BuferImagenReemplazado.setRGB(x, y, new Color(CanalRojoHorizontal[y][x], CanalVerdeHorizontal[y][x], CanalAzulHorizontal[y][x]).getRGB());
            }
        }
        return BuferImagenReemplazado;
    }

    public BufferedImage DeteccionBordeMetodoVertical() {
        //P'i = | P[i, j + 1] - P[i, j] |

        for (y = 0; y < getTamañoFilas(); y++) {
            for (x = 0; x < getTamañoColumnas() - 1; x++) {

                int PixelRojo = Math.abs(CanalRojo[y][x + 1] - CanalRojo[y][x]);
                int PixelVerde = Math.abs(CanalVerde[y][x + 1] - CanalVerde[y][x]);
                int PixelAzul = Math.abs(CanalAzul[y][x + 1] - CanalAzul[y][x]);

                CanalRojoVertical[y][x] = PixelRojo;
                CanalVerdeVertical[y][x] = PixelVerde;
                CanalAzulVertical[y][x] = PixelAzul;

                BuferImagenReemplazado.setRGB(x, y, new Color(CanalRojoVertical[y][x], CanalVerdeVertical[y][x], CanalAzulVertical[y][x]).getRGB());
            }
        }
        return BuferImagenReemplazado;
    }

    //Solucion alternativa 
    public BufferedImage DeteccionBordeMetodoDiagonal() {
        //P'i = | P[i+1, j + 1] - P[i, j] |

        for (y = 0; y < getTamañoFilas() - 1; y++) {
            for (x = 0; x < getTamañoColumnas() - 1; x++) {

                int PixelRojo = Math.abs(CanalRojo[y + 1][x + 1] - CanalRojo[y][x]);
                int PixelVerde = Math.abs(CanalVerde[y + 1][x + 1] - CanalVerde[y][x]);
                int PixelAzul = Math.abs(CanalAzul[y + 1][x + 1] - CanalAzul[y][x]);

                CanalRojoHorizontal[y][x] = PixelRojo;
                CanalVerdeHorizontal[y][x] = PixelVerde;
                CanalAzulHorizontal[y][x] = PixelAzul;

                BuferImagenReemplazado.setRGB(x, y, new Color(CanalRojoHorizontal[y][x], CanalVerdeHorizontal[y][x], CanalAzulHorizontal[y][x]).getRGB());
            }
        }
        return BuferImagenReemplazado;
    }

    public BufferedImage DeteccionBorde4P() {
        
        for (y = 1; y < getTamañoFilas() - 1; y++) {
            for (x = 1; x < getTamañoColumnas() - 1; x++) {
                int PixelRojo = Math.abs(CanalRojo[y - 1][x] + CanalRojo[y][x - 1] + CanalRojo[y + 1][x] + CanalRojo[y][x + 1] - 4 * CanalRojo[y][x]);
                int PixelVerde = Math.abs(CanalVerde[y - 1][x] + CanalVerde[y][x - 1] + CanalVerde[y + 1][x] + CanalVerde[y][x + 1] - 4 * CanalVerde[y][x]);
                int PixelAzul = Math.abs(CanalAzul[y - 1][x] + CanalAzul[y][x - 1] + CanalAzul[y + 1][x] + CanalAzul[y][x + 1] - 4 * CanalAzul[y][x]);
                try {
                    CanalRojoHorizontal[y][x] = PixelRojo;
                    CanalVerdeHorizontal[y][x] = PixelVerde;
                    CanalAzulHorizontal[y][x] = PixelAzul;
                    BuferImagenReemplazado.setRGB(x, y, new Color(CanalRojoHorizontal[y][x], CanalVerdeHorizontal[y][x], CanalAzulHorizontal[y][x]).getRGB());
                } catch (Exception e) {
                    System.out.println("PixelR =  " + PixelRojo + "PixelV =  " + PixelVerde + "PixelA =  " + PixelAzul);
                    return null;
                }
            }
        }

        return BuferImagenReemplazado;
    }

    public BufferedImage DeteccionBordeMixto() {
        DeteccionBordeMetodoVertical();
        DeteccionBordeMetodoHorizontal();
        for (y = 0; y < getTamañoFilas() - 1; y++) {
            for (x = 0; x < getTamañoColumnas() - 1; x++) {
                int valorPixelRojo = ValorMayor(CanalRojoHorizontal[y][x], CanalRojoVertical[y][x]);
                int valorPixelVerde = ValorMayor(CanalVerdeHorizontal[y][x], CanalVerdeVertical[y][x]);
                int valorPixelAzul = ValorMayor(CanalAzulHorizontal[y][x], CanalAzulVertical[y][x]);

                BuferImagenReemplazado.setRGB(x, y, new Color(valorPixelRojo, valorPixelVerde, valorPixelAzul).getRGB());
            }
        }
        return BuferImagenReemplazado;
    }

    private int ValorMayor(int a, int b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }
}
