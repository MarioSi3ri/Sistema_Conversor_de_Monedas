# 💱 Sistema Conversor de Monedas

Aplicación de consola desarrollada en **Java** para consultar tasas de cambio en tiempo real y realizar conversiones entre seis divisas mediante la API de [ExchangeRate-API](https://www.exchangerate-api.com/).

El proyecto fue desarrollado como parte del programa **ONE - Oracle Next Education**, en colaboración con **Alura LATAM y Oracle**.

## 📌 ¿De qué trata?

El sistema permite seleccionar una moneda de origen, una moneda de destino e ingresar un monto para obtener su conversión utilizando las tasas de cambio más recientes disponibles en la API.

Las divisas disponibles son:

| Opción | Código | Moneda |
|:------:|:------:|--------|
| 1 | `USD` | Dólar estadounidense |
| 2 | `EUR` | Euro |
| 3 | `BRL` | Real brasileño |
| 4 | `COP` | Peso colombiano |
| 5 | `ARS` | Peso argentino |
| 6 | `MXN` | Peso mexicano |

### ✨ Funcionalidades principales

- Consulta de tasas de cambio en tiempo real.
- Conversión entre seis tipos de divisas.
- Menú interactivo para seleccionar las monedas.
- Validación de monedas de origen y destino.
- Validación de cantidades numéricas y no negativas.
- Límite de tres intentos para entradas inválidas.
- Prevención de conversiones entre la misma moneda.
- Manejo de errores de conexión y respuestas inválidas de la API.
- Opción para realizar varias conversiones durante la misma ejecución.

## 🛠️ Tecnologías utilizadas

- **Java 17 o superior**
  - `HttpClient` para realizar solicitudes HTTP.
  - `Scanner` para la interacción con el usuario.
  - `record` para representar las tasas de conversión.
  - Expresiones `switch` y bloques de texto.
- **Gson 2.10.1** para deserializar la respuesta JSON de la API.
- **ExchangeRate-API** como fuente de tasas de cambio.
- **IntelliJ IDEA** como entorno de desarrollo recomendado.

## 📁 Estructura del proyecto

```text
Sistema_Conversor_de_Monedas/
├── src/
│   └── main/
│       ├── org/si3ri/convert/
│       │   ├── BackendCurrencyConverter.java
│       │   ├── exception/
│       │   │   └── QueryCurrencyException.java
│       │   ├── model/
│       │   │   ├── Currency.java
│       │   │   └── CurrencyTypeAPI.java
│       │   └── service/
│       │       └── QueryCurrencyChange.java
│       └── test/
│           └── org/si3ri/convert/
│               └── BackendCurrencyConverterTests.java
├── CountryCurrencyConverter.iml
└── README.md
```

### Componentes principales

- **`BackendCurrencyConverter`**: punto de entrada de la aplicación y controlador del flujo de interacción con el usuario.
- **`QueryCurrencyChange`**: construye la solicitud HTTP, consulta ExchangeRate-API y procesa la respuesta JSON.
- **`CurrencyTypeAPI`**: modelo auxiliar que representa la estructura de la respuesta de la API.
- **`Currency`**: `record` que almacena las tasas de conversión de las seis divisas soportadas.
- **`QueryCurrencyException`**: excepción personalizada para errores en la consulta de tasas.

## ⚙️ Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- **JDK 17 o superior**.
- **Git**.
- Una conexión activa a Internet.
- La dependencia **Gson 2.10.1**.

Puedes verificar la versión de Java con:

```bash
java -version
javac -version
```

> **Nota:** El repositorio no incluye actualmente un archivo `pom.xml` ni `build.gradle`, por lo que la dependencia Gson debe estar disponible manualmente o configurarse desde el IDE.

## 🚀 Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/MarioSi3ri/Sistema_Conversor_de_Monedas.git
cd Sistema_Conversor_de_Monedas
```

### 2. Configurar Gson

Descarga el archivo `gson-2.10.1.jar` desde el repositorio oficial de Gson o desde Maven Central.

En IntelliJ IDEA:

1. Abre el proyecto.
2. Ve a **File → Project Structure → Libraries**.
3. Agrega el archivo `gson-2.10.1.jar`.
4. Verifica que la biblioteca esté asociada al módulo del proyecto.
5. Configura un JDK 17 o superior para el proyecto.

El archivo `CountryCurrencyConverter.iml` ya referencia una biblioteca Gson ubicada en una carpeta superior al proyecto:

```text
../gson-2.10.1.jar
```

Si la dependencia está en otra ubicación, actualiza la configuración del módulo o agrega el `.jar` manualmente desde IntelliJ IDEA.

## ▶️ Cómo ejecutar la aplicación

### Opción recomendada: IntelliJ IDEA

1. Abre el proyecto en IntelliJ IDEA.
2. Confirma que Gson esté configurado como dependencia.
3. Abre el archivo:

   ```text
   src/main/org/si3ri/convert/BackendCurrencyConverter.java
   ```

4. Ejecuta el método `main`.
5. Sigue las instrucciones que aparecen en la consola.

### Ejecución desde la terminal

Desde la raíz del proyecto, compila los archivos Java indicando la ubicación de Gson:

#### Linux o macOS

```bash
mkdir -p out
javac -cp gson-2.10.1.jar -d out $(find src/main/org -name "*.java")
java -cp "out:gson-2.10.1.jar" main.org.si3ri.convert.BackendCurrencyConverter
```

#### Windows PowerShell

```powershell
New-Item -ItemType Directory -Force out
javac -cp "gson-2.10.1.jar" -d out (Get-ChildItem -Recurse -Filter *.java src/main/org | ForEach-Object { $_.FullName })
java -cp "out;gson-2.10.1.jar" main.org.si3ri.convert.BackendCurrencyConverter
```

> Si el archivo `.jar` se encuentra en otra ruta, reemplaza `gson-2.10.1.jar` por la ruta correspondiente.

## 🧭 Cómo usarlo

1. Inicia la aplicación.
2. El sistema consultará y mostrará las tasas disponibles.
3. Selecciona la moneda de origen ingresando un número del `1` al `6`.
4. Selecciona la moneda de destino.
5. Ingresa la cantidad que deseas convertir.
6. Consulta el resultado mostrado en la consola.
7. Escribe `S` para realizar otra conversión o cualquier otra tecla para finalizar.

### Ejemplo de flujo

```text
Ingrese el número de la moneda de origen: 1
Ingrese el número de la moneda de destino: 2
Ingrese la cantidad de USD a convertir en EUR: 100

CONVERSIÓN: 100.0 USD equivale/n a [resultado] EUR

¿Desea realizar otra conversión? (Ingrese S para SÍ o cualquier otra tecla para salir):
```

El valor exacto depende de la tasa publicada por ExchangeRate-API en el momento de la consulta.

## 🌐 API utilizada

La aplicación consulta el endpoint de ExchangeRate-API con una estructura equivalente a:

```text
https://v6.exchangerate-api.com/v6/<API_KEY>/latest/<MONEDA_BASE>
```

La moneda de origen se utiliza como moneda base para obtener las tasas de conversión hacia las demás divisas.

### 🔐 Recomendación de seguridad

Actualmente, la URL de consulta y la clave de la API están definidas directamente en `QueryCurrencyChange.java`. Para un proyecto de producción, se recomienda:

- No publicar claves reales dentro del código fuente.
- Utilizar variables de entorno o un archivo de configuración fuera del control de versiones.
- Revocar y regenerar la clave si fue expuesta públicamente.
- Agregar archivos con secretos al `.gitignore`.

Una alternativa sería leer la clave desde una variable de entorno:

```bash
export EXCHANGE_RATE_API_KEY="tu_clave"
```

Y utilizarla desde Java mediante `System.getenv("EXCHANGE_RATE_API_KEY")`.

## 🧪 Pruebas

El repositorio incluye la clase:

```text
src/main/test/org/si3ri/convert/BackendCurrencyConverterTests.java
```

Actualmente funciona como estructura inicial para pruebas automatizadas. No se incluye un framework de testing configurado en el proyecto, por lo que las pruebas pueden ampliarse posteriormente utilizando **JUnit 5** y un sistema de construcción como Maven o Gradle.

## 🧩 Posibles mejoras

- Incorporar Maven o Gradle para gestionar dependencias y facilitar la compilación.
- Migrar la clave de API a variables de entorno.
- Añadir pruebas unitarias con JUnit.
- Separar la lógica de entrada/salida de la lógica de conversión.
- Utilizar `BigDecimal` para cálculos monetarios con mayor precisión.
- Añadir más monedas y permitir tasas configurables.
- Implementar caché de tasas para reducir solicitudes a la API.
- Mejorar la presentación de los resultados y el formato decimal.
- Agregar logs y mensajes de error más detallados.

## 👨‍💻 Autor

**Mario Si3ri**

- GitHub: [@MarioSi3ri](https://github.com/MarioSi3ri)

## 📄 Licencia

Este proyecto no incluye actualmente un archivo de licencia. Si deseas reutilizarlo o distribuirlo, consulta al autor y considera agregar una licencia de código abierto al repositorio.

---

⭐ Si este proyecto te resulta útil, considera darle una estrella al repositorio.
