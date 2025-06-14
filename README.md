# 🛒 Shopping Cart Microservices

**Prueba técnica: arquitectura de microservicios moderna con Spring Boot 3.5, Java 21, JWT RSA, Feign, SQLite, Swagger, Dev Container y Docker Compose.**

---

## 📚 Descripción

Este proyecto implementa un **carrito de compras distribuido en microservicios**. Cada servicio está aislado, comunica de forma segura con JWT firmado por clave privada RSA, usa **SQLite** como base de datos ligera y ofrece documentación interactiva Swagger.  
Además, cuenta con un **Dev Container** y scripts para ejecución fácil.

---

## 📦 Arquitectura

| Microservicio      | Descripción                                                   | Swagger URL                                |
|--------------------|---------------------------------------------------------------|--------------------------------------------|
| **Auth Service**   | Autenticación y gestión de usuarios, emisión de JWT RSA       | `http://localhost:8081/swagger-ui/index.html` |
| **Product Service**| Proxy a FakeStore API + endpoints internos optimizados        | `http://localhost:8082/swagger-ui/index.html` |
| **Order Service**  | Gestión de órdenes, integración con productos y pagos         | `http://localhost:8083/swagger-ui/index.html` |
| **Payment Service**| Simulación de pagos, verificación de órdenes vía Feign Client | `http://localhost:8084/swagger-ui/index.html` |

---

## 🔑 Seguridad JWT con RSA

- ✅ **private.pem**: Firmado de JWT en `auth-service`
- ✅ **public.pem**: Validación en todos los microservicios (`common` módulo)
- ✅ Claim extra `userId` incluido en el token → reduce consultas DB
- ✅ Stateless: no se almacena sesión, solo se verifica firma.

**Flujo:**  
Usuario se loguea → recibe JWT firmado → Feign Client propaga token entre microservicios → cada filtro verifica la firma.

---

## 🔗 Intercomunicación

- **ProductClient**: `Order Service` consume productos.
- **OrderClient**: `Payment Service` verifica órdenes.
- **FeignClientInterceptor**: copia automáticamente el JWT del request original a cada llamada Feign.

```ascii
[Client] ──> [Auth Service] ──> JWT
[Client] ──> [Product Service]
[Client] ──> [Order Service] ──> [Product Service]
[Client] ──> [Payment Service] ──> [Order Service]
````

---

## 📑 Endpoints Clave

| Servicio    | Endpoint                          | Descripción                        |
| ----------- | --------------------------------- | ---------------------------------- |
| **Auth**    | `POST /api/auth/login`            | Autentica usuario, devuelve JWT    |
| **Auth**    | `POST /api/users`                 | Registra usuario nuevo             |
| **Product** | `GET /api/products`               | Lista todos los productos          |
| **Product** | `GET /api/products/{id}`          | Detalle producto                   |
| **Product** | `GET /api/products/internal/{id}` | Interno: sin `ApiResponse` wrapper |
| **Order**   | `POST /api/orders`                | Crear orden (token requerido)      |
| **Order**   | `GET /api/orders`                 | Listar mis órdenes                 |
| **Order**   | `GET /api/orders/{id}`            | Obtener orden por ID               |
| **Payment** | `POST /api/payments`              | Crear pago de orden                |
| **Payment** | `GET /api/payments`               | Listar pagos                       |
| **Payment** | `GET /api/payments/{id}`          | Obtener pago por ID                |

---

## ⚙️ Requisitos

* **Java 21**
* **Spring Boot 3.5**
* **Maven 3.9+**
* **Docker & Docker Compose**
* **Visual Studio Code con Dev Containers**

---

## 🗂️ Estructura del Proyecto

```
shopping-cart/
├── common/               # Módulo común (seguridad, DTOs, excepciones)
├── auth-service/         # Microservicio de autenticación
├── product-service/      # Microservicio de productos
├── order-service/        # Microservicio de órdenes
├── payment-service/      # Microservicio de pagos
├── run-all.sh            # Script para correr todo en local
├── docker-compose.yml    # Orquestación de contenedores (en progreso)
├── .devcontainer/        # Configuración de Dev Container para VS Code
└── Insomnia_2025-06-14.json  # Colección de pruebas Insomnia
```

---

## ⚡ Instalación y ejecución

### 🧩 Clonar el repositorio

```bash
git clone <repo-url>
cd shopping-cart
```

### 🔁 Compilar y correr todos los servicios

```bash
./run-all.sh
```

### 🐳 Orquestar con Docker Compose (en progreso)

```bash
docker-compose up --build
```

> **Nota:** El `docker-compose.yml` estára listo para extenderse con redes, volúmenes y variables de entorno.

---

## 🔬 Guía para pruebas

1️⃣ Importa `Insomnia_2025-06-14.json` en Insomnia.
2️⃣ Ejecuta `/api/auth/login`.
3️⃣ El `afterResponse` guarda el JWT en `{{ _.token_prueba }}` automáticamente.
4️⃣ Todos los endpoints usan esta variable por defecto.
5️⃣ Verifica y testea con Swagger en cada servicio.

---

## 📁 Migraciones y DB

* Cada microservicio tiene su **propio archivo SQLite** (`auth.db`, `order.db`).
* Flyway gestiona la versión de esquema en `src/main/resources/db/migration`.

---

## 📚 Documentación Swagger

| Servicio | URL                                           |
| -------- | --------------------------------------------- |
| Auth     | `http://localhost:8081/swagger-ui/index.html` |
| Product  | `http://localhost:8082/swagger-ui/index.html` |
| Order    | `http://localhost:8083/swagger-ui/index.html` |
| Payment  | `http://localhost:8084/swagger-ui/index.html` |

---

## ✅ Justificación Técnica

| Decisión                      | Motivo                                               |
| ----------------------------- | ---------------------------------------------------- |
| **Java 21 + Spring Boot 3.5** | Características modernas, soporte a nuevas APIs.     |
| **JWT con RSA**               | Seguridad robusta sin compartir secretos.            |
| **Feign + Interceptor**       | Propagación de token sin duplicar lógica.            |
| **SQLite + Flyway**           | Simplicidad, portabilidad y control de esquema.      |
| **Dev Container**             | Consistencia de entorno entre developers.            |
| **Insomnia + Swagger**        | QA rápido y validación de endpoints REST.            |
| **Docker Compose**            | Preparado para ejecución en contenedores fácilmente. |

---

## 🐳 Dev Container

* Listo para abrir en VS Code con **Reopen in Container**.
* Incluye JDK, Maven y dependencias preconfiguradas.

---

## ✅ Resultado

* 🔑 Autenticación segura con JWT RSA.
* 🧩 Microservicios independientes y desacoplados.
* 🔗 Comunicación robusta usando Feign.
* 🗂️ SQLite para pruebas rápidas.
* 📑 Swagger y Insomnia para documentación y pruebas.
* 🐳 Preparado para contenedores y despliegue futuro.

---

## 🏁 Ejecutar todo con script

```bash
./run-all.sh
```

---

## 🚀 Autor

Desarrollado con 💙, ☕ y ⚙️ por `SNAVEN10`.

---

### 🎉 ¡Happy coding! 🔨🤖🔧

---