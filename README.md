<h1 align="center">SpringMotors</h1>

<p align="center">
  Plataforma web para la gestión de un concesionario: vehículos, repuestos, proveedores y clientes.<br/>
  <sub>Web platform to manage a car dealership: vehicles, spare parts, suppliers and customers.</sub>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/platform-Web-38BDF8?style=flat" alt="Web"/>
  <img src="https://img.shields.io/badge/type-Backend%20%2B%20Views-6DB33F?style=flat" alt="Backend + Views"/>
  <img src="https://img.shields.io/badge/year-2025-495057?style=flat" alt="2025"/>
</p>

<p align="center">
  <a href="#español">Español</a> · <a href="#english">English</a>
</p>

<p align="center">
  <img src="https://drey.is-a.dev/img/projects/project1/project1-2.jpg" alt="SpringMotors home" width="90%"/>
</p>

## Tech stack

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=flat&logo=openjdk&logoColor=white" alt="Java 21"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-3.4-6DB33F?style=flat&logo=springboot&logoColor=white" alt="Spring Boot 3.4"/>
  <img src="https://img.shields.io/badge/Spring%20Security-Auth-6DB33F?style=flat&logo=springsecurity&logoColor=white" alt="Spring Security"/>
  <img src="https://img.shields.io/badge/Spring%20Data%20JPA-ORM-6DB33F?style=flat&logo=spring&logoColor=white" alt="Spring Data JPA"/>
  <img src="https://img.shields.io/badge/Thymeleaf-Views-005F0F?style=flat&logo=thymeleaf&logoColor=white" alt="Thymeleaf"/>
  <br/>
  <img src="https://img.shields.io/badge/PostgreSQL-Neon-336791?style=flat&logo=postgresql&logoColor=white" alt="PostgreSQL Neon"/>
  <img src="https://img.shields.io/badge/H2-Testing-4479A1?style=flat" alt="H2"/>
  <img src="https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=html5&logoColor=white" alt="HTML5"/>
  <img src="https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=css3&logoColor=white" alt="CSS3"/>
  <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=javascript&logoColor=black" alt="JavaScript"/>
  <img src="https://img.shields.io/badge/Maven-Build-C71A36?style=flat&logo=apachemaven&logoColor=white" alt="Maven"/>
</p>

## Screenshots

<table>
  <tr>
    <td width="50%" align="center">
      <img src="https://drey.is-a.dev/img/projects/project1/project1-1.png" alt="Login"/>
      <br/><b>Login</b><br/><sub>Inicio de sesión · Sign in</sub>
    </td>
    <td width="50%" align="center">
      <img src="https://drey.is-a.dev/img/projects/project1/project1-4.png" alt="Admin panel"/>
      <br/><b>Panel de gestión · Admin panel</b><br/><sub>Usuarios, vehículos, repuestos, proveedores y fabricantes</sub>
    </td>
  </tr>
  <tr>
    <td width="50%" align="center">
      <img src="https://drey.is-a.dev/img/projects/project1/project1-6.png" alt="Vehicle catalog"/>
      <br/><b>Catálogo de vehículos · Vehicle catalog</b><br/><sub>Filtros por marca, modelo, precio, año y estado</sub>
    </td>
    <td width="50%" align="center">
      <img src="https://drey.is-a.dev/img/projects/project1/project1-5.png" alt="Vehicle management"/>
      <br/><b>Gestión de vehículos · Vehicle management</b><br/><sub>CRUD con búsqueda e imágenes</sub>
    </td>
  </tr>
</table>

---

## Español

> SpringMotors centraliza el inventario de un concesionario (vehículos y repuestos) y la gestión de proveedores, fabricantes y clientes desde un panel exclusivo, con control de roles y sesiones.

### Funcionalidades

| Área | Descripción |
|---|---|
| **Vehículos** | Registro, edición y eliminación con imágenes, precio, kilometraje y estado (nuevo, como nuevo, usado). |
| **Repuestos** | Catálogo de repuestos por marca, tipo, proveedor y estado de disponibilidad. |
| **Catálogo público** | Búsqueda con filtros para vehículos y repuestos. |
| **Proveedores y fabricantes** | Administración de proveedores, fabricantes/marcas y clientes. |
| **Panel de control** | Panel exclusivo para administradores con acceso a cada módulo. |
| **Seguridad** | Spring Security con manejo de sesiones, roles y permisos. |

### Cómo ejecutarlo

1. Requisitos: **JDK 21**. Maven no es necesario, el proyecto incluye `mvnw`.
2. Configura la base de datos en `src/main/resources/application.properties`:
   - **PostgreSQL / Neon:** `spring.datasource.url`, `username` y `password`.
   - **H2 (pruebas):** descomenta el bloque de H2 y comenta el de PostgreSQL. La consola queda en `/h2-console`.
3. Ejecuta:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Abre `http://localhost:8080`.

> [!TIP]
> Las tablas se crean solas (`ddl-auto=update`). Para producción usa variables de entorno en lugar de dejar credenciales en el archivo.

### Estructura

```
src/main/java/        Controladores, entidades, repositorios, servicios y seguridad
src/main/resources/
  templates/          Vistas Thymeleaf
  static/             CSS, JavaScript e imágenes
  application.properties
```

---

## English

> SpringMotors centralizes a dealership's inventory (vehicles and spare parts) and the management of suppliers, manufacturers and customers from a dedicated panel, with role and session control.

### Features

| Area | Description |
|---|---|
| **Vehicles** | Create, edit and delete vehicles with images, price, mileage and condition (new, like new, used). |
| **Spare parts** | Spare-part catalog by brand, type, supplier and availability. |
| **Public catalog** | Filtered search for vehicles and spare parts. |
| **Suppliers and manufacturers** | Management of suppliers, manufacturers/brands and customers. |
| **Control panel** | Admin-only panel with access to every module. |
| **Security** | Spring Security with session handling, roles and permissions. |

### Getting started

1. Requirements: **JDK 21**. Maven is not needed, the project ships with `mvnw`.
2. Set up the database in `src/main/resources/application.properties`:
   - **PostgreSQL / Neon:** `spring.datasource.url`, `username` and `password`.
   - **H2 (testing):** uncomment the H2 block and comment the PostgreSQL one. The console is at `/h2-console`.
3. Run:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Open `http://localhost:8080`.

> [!TIP]
> Tables are created automatically (`ddl-auto=update`). In production, use environment variables instead of keeping credentials in the file.

---

## Autor · Author

Desarrollado por **Andrey Mantilla Leon** · Ingeniería de Sistemas. Portafolio: [drey.is-a.dev](https://drey.is-a.dev)
