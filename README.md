# Bomberman en Java

Juego estilo *Bomberman* para 1 o 2 jugadores, hecho en **Java** con **Swing**, sprites animados y programación orientada a objetos.

## Cómo se juega
| Acción | Jugador 1 | Jugador 2 |
|---|---|---|
| Moverse | `W` `A` `S` `D` | Flechas |
| Poner bomba | `Espacio` | `Enter` |
| Unirse a la partida | — | `B` |

- `R` reinicia la partida cuando termina el juego.
- Destruye muros con las bombas y evita a los enemigos.

## Estructura (`src/juego/`)
- `Main` — punto de entrada.
- `Jugar` — ciclo del juego, dibujo, animaciones y teclado.
- `Tablero` — mapa de paredes, muros destructibles y celdas vacías.
- `Personaje` — clase base; de ella heredan `Jugador` y `Enemigo`.
- `Bomba` — temporizador y explosión.

## Tecnologías
Java · Swing / AWT

## Ejecutar
```bash
javac -d bin src/juego/*.java
java -cp bin juego.Main
```
