# Lambeau

A 3D container packing solver implemented in Java, using the [Extreme Points approach](https://www.cirrelt.ca/documentstravail/cirrelt-2007-41.pdf). It finds optimal or near-optimal arrangements of packages inside containers and includes a graphical interface for visualization and input.

## Requirements

- Java 21+

JavaFX is managed automatically by Gradle — no manual installation needed.

## Getting Started

```bash
git clone https://github.com/Haim5/Lambeau.git
cd Lambeau
./gradlew run
```

On Windows, use `gradlew.bat run` instead.

## Dependencies

- **JavaFX 21** — UI and 3D visualization (via Gradle plugin)
- **Swing** — additional GUI components
