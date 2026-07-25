# SIIAPI-Spring

API REST para gestionar informacion academica universitaria: centros, calendarios, materias, secciones, profesores, clases, edificios y aulas. Integra con SIIAU para sincronizar datos academicos.

## Requisitos

- Java 21+ (probado con JDK 25)
- PostgreSQL 15+
- Maven (incluido via `mvnw`, no requiere instalacion)

## Estructura del proyecto

```
src/main/java/dev/cucei/siiapi/
├── config/             # Seguridad, CORS
├── common/             # Excepciones, paginacion
├── modules/
│   ├── calendario/     # Calendarios academicos
│   ├── centro/         # Centros universitarios
│   ├── materia/        # Materias/asignaturas
│   ├── profesor/       # Profesores
│   ├── seccion/        # Secciones (NRC)
│   ├── clase/          # Clases (horarios)
│   ├── edificio/       # Edificios
│   ├── aula/           # Aulas
│   └── sync/           # Sincronizacion SIIAU
└── info/               # Endpoint de informacion de la API
```

---

## Desarrollo

### 1. Clonar e instalar dependencias

```bash
git clone <repo-url>
cd SIIAPI-Spring
```

No necesitas instalar Maven. El wrapper (`mvnw`) se encarga de todo.

### 2. Configurar base de datos

Crea una base de datos PostgreSQL:

```sql
CREATE DATABASE siiapi;
```

### 3. Configurar variables de entorno

El profile `dev` esta activo por defecto. Configura la conexion en `src/main/resources/application-dev.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/siiapi
    username: postgres
    password: postgres
```

O usa variables de entorno:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/siiapi
export DB_USERNAME=postgres
export DB_PASSWORD=postgres
```

### 4. Generar API Key

Para endpoints de escritura necesitas una API Key:

```bash
# Generar hash SHA-256 de tu key
echo -n "mi-api-key-secreta" | sha256sum
# Copia el hash generado

# Configurarlo
export API_KEYS_HASHED="el-hash-que-copiaste"
```

### 5. Ejecutar

```bash
# Compilar
./mvnw compile

# Ejecutar en modo desarrollo
./mvnw spring-boot:run

# O ejecutar el JAR
./mvnw package -DskipTests
java -jar target/siiapi-2.0.0.jar
```

La API estara disponible en `http://localhost:8080`.

### 6. Probar

```bash
# Info de la API
curl http://localhost:8080/

# Listar centros
curl http://localhost:8080/api/v1/centros

# Crear un centro (requiere API Key)
curl -X POST http://localhost:8080/api/v1/centros \
  -H "Content-Type: application/json" \
  -H "X-API-Key: mi-api-key-secreta" \
  -d '{"name": "CUCEI", "siiauId": "CUCEI"}'
```

---

## Produccion

### 1. Variables de entorno requeridas

```bash
# Base de datos
export DB_URL=jdbc:postgresql://tu-host:5432/siiapi
export DB_USERNAME=tu-usuario
export DB-password=tu-password

# Seguridad
export API_KEYS_HASHED="hash1,hash2,hash3"  # Lista separada por comas
```

### 2. Empaquetar

```bash
./mvnw package -DskipTests
```

Esto genera `target/siiapi-2.0.0.jar`.

### 3. Ejecutar

```bash
java -jar target/siiapi-2.0.0.jar --spring.profiles.active=prod
```

O con Docker:

```bash
docker build -t siiapi .
docker run -p 8080:8080 \
  -e DB_URL=jdbc:postgresql://db-host:5432/siiapi \
  -e DB_USERNAME=postgres \
  -e DB_PASSWORD=secreto \
  -e API_KEYS_HASHED="hash1,hash2" \
  siiapi
```

### 4. Configuracion del profile `prod`

En `application-prod.yml` se desactiva `show-sql` y la DB se configura por variables de entorno. Hibernate crea las tablas automaticamente con `ddl-auto: update`.

> **Nota:** Para produccion se recomienda usar migraciones Flyway en lugar de `ddl-auto: update`.

---

## Autenticacion

| Metodo | Requiere API Key |
|--------|------------------|
| GET | No |
| POST | Si |
| PUT | Si |
| PATCH | Si |
| DELETE | Si |
| OPTIONS | No |

Enviar la key en el header:

```
X-API-Key: tu-api-key
```

La key se hashea con SHA-256 y se compara contra la lista en `API_KEYS_HASHED`.

---

## Endpoints

Ver `docs/README.md` para documentacion completa de cada endpoint.
