
# 🛒 Shopping Cart Microservices

**Prueba técnica: arquitectura de microservicios usando Spring Boot, JWT, Feign, SQLite y Swagger**

---

## 📂 Estructura del Proyecto

```
shopping-cart/
│
├── common/               # Módulo común (seguridad, excepciones, response)
├── auth-service/         # Microservicio de autenticación y gestión de usuarios
├── product-service/      # Microservicio de productos
├── order-service/        # Microservicio de órdenes
├── payment-service/      # Microservicio de pagos
├── pom.xml               # Parent POM con módulos y gestión de dependencias
└── run-all.sh            # Script opcional para iniciar todos los servicios
```

---

## 🎯 Arquitectura y Decisiones

- **Microservicios Spring Boot** → Cada dominio tiene su propio servicio.
- **JWT + RSA** → Seguridad robusta, incluye claim `userId` para evitar consultas innecesarias.
- **SQLite + Flyway** → Persistencia liviana para testing rápido.
- **OpenFeign** → Comunicación entre microservicios con propagación automática de tokens.
- **Swagger** → Documentación y testing directo.

---

## 🔑 Seguridad

- **Módulo `common`**:  
  - `JwtService` / `JwtServiceImpl` — generación y validación de JWT con claims custom.
  - `JwtAuthenticationFilter` — autentica cada request entrante.
  - `SecurityConfig` — define rutas públicas y protegidas.

- **Flujo del token:**
  1. Usuario hace **login** (`/api/auth/login`)
  2. JWT con `userId` se propaga automáticamente mediante interceptor Feign
  3. Filtros extraen y validan token en cada microservicio

---

## 🔗 Intercomunicación

- **ProductClient** — usado en Order.
- **OrderClient** — usado en Payment.
- **FeignClientInterceptor** — copia token del header HTTP a la request Feign.

---

## 📑 Endpoints Clave

| Servicio         | Endpoint                              | Descripción                       |
| ---------------- | ------------------------------------- | --------------------------------- |
| **Auth**         | `POST /api/auth/login`                | Autentica y retorna JWT           |
| **Auth**         | `POST /api/users`                     | Registra nuevo usuario            |
| **Product**      | `GET /api/products`                   | Lista todos los productos         |
| **Product**      | `GET /api/products/{id}`              | Detalle producto                  |
| **Product**      | `GET /api/products/internal/{id}`     | Interno: sin `ApiResponse` wrapper|
| **Order**        | `POST /api/orders`                    | Crear orden (token requerido)     |
| **Order**        | `GET /api/orders`                     | Listar mis órdenes                |
| **Order**        | `GET /api/orders/{id}`                | Obtener orden por ID              |
| **Payment**      | `POST /api/payments`                  | Crear pago de orden               |
| **Payment**      | `GET /api/payments`                   | Listar pagos                      |
| **Payment**      | `GET /api/payments/{id}`              | Obtener pago por ID               |

---

## 🔑 Flujo de prueba

1️⃣ **Login**
```bash
curl -X POST http://localhost:8081/api/auth/login   -H 'Content-Type: application/json'   -d '{ "username": "admin", "password": "admin123" }'
```

2️⃣ **Guardar el JWT en Insomnia**
```javascript
const jsonData = response.body;
insomnia.setEnvironmentVariable('token_prueba', jsonData.data.token);
```

3️⃣ **Usar variable `{{ _.token_prueba }}` en todos los requests.**

---

## 🗂️ Swagger UI

- Auth: `http://localhost:8081/swagger-ui/index.html`
- Product: `http://localhost:8082/swagger-ui/index.html`
- Order: `http://localhost:8083/swagger-ui/index.html`
- Payment: `http://localhost:8084/swagger-ui/index.html`

---

## ⚙️ Migraciones

Cada microservicio usa **Flyway** con scripts en `src/main/resources/db/migration`.

---

## ✅ Cómo correr

```bash
mvn -pl common clean install

# En terminales separadas
mvn -pl auth-service spring-boot:run
mvn -pl product-service spring-boot:run
mvn -pl order-service spring-boot:run
mvn -pl payment-service spring-boot:run
```

---

## 🚀 Resultado

Prueba técnica completa:
✅ Autenticación robusta  
✅ Microservicios independientes  
✅ Comunicación segura  
✅ Persistencia SQLite  
✅ Documentación Swagger  
✅ Scripts de prueba Insomnia