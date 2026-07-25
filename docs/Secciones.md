## Secciones

### `POST /api/v1/secciones`

**Body:**

```json
{
  "name": "001",
  "nrc": "12345",
  "cupos": 40,
  "cuposDisponibles": 15,
  "est": "EST",
  "periodoInicio": "2025-08-12T00:00:00",
  "periodoFin": "2025-12-19T23:59:00",
  "centroId": 1,
  "materiaId": 1,
  "profesorId": 1,
  "calendarioId": 1
}
```

| Campo | Tipo | Requerido | Descripcion |
|-------|------|-----------|-------------|
| `name` | string | Si | Numero de seccion |
| `nrc` | string | Si | NRC (unico por calendario) |
| `cupos` | int | Si | Cupos totales |
| `cuposDisponibles` | int | Si | Cupos disponibles |
| `est` | string | No | Estado (ej: "EST") |
| `periodoInicio` | datetime | No | Inicio del periodo |
| `periodoFin` | datetime | No | Fin del periodo |
| `centroId` | long | Si | ID del centro |
| `materiaId` | long | Si | ID de la materia |
| `profesorId` | long | No | ID del profesor |
| `calendarioId` | long | Si | ID del calendario |

**Respuesta 201:**

```json
{
  "name": "001",
  "nrc": "12345",
  "cupos": 40,
  "cuposDisponibles": 15,
  "est": "EST",
  "periodoInicio": "2025-08-12T00:00:00",
  "periodoFin": "2025-12-19T23:59:00",
  "centroId": 1,
  "materiaId": 1,
  "profesorId": 1,
  "calendarioId": 1,
  "id": 1,
  "centro": { "name": "CUCEI", "siiauId": "CUCEI", "id": 1 },
  "materia": { "name": "POO", "creditos": 8, "clave": "TC1024", "id": 1 },
  "profesor": { "name": "Juan Perez", "id": 1 },
  "calendario": { "name": "2025-1", "siiauId": "20251", "id": 1 },
  "clases": []
}
```

### `GET /api/v1/secciones`

Lista todas las secciones.

**Query params:** `nrc`, `centroId`, `materiaId`, `profesorId`, `calendarioId`, `search`, `skip`, `limit`

### `GET /api/v1/secciones/{id}`

Obtiene una seccion por ID con todas sus relaciones.

### `PUT /api/v1/secciones/{id}`

Actualiza una seccion completa.

### `PATCH /api/v1/secciones/{id}`

Actualiza parcialmente una seccion.

### `DELETE /api/v1/secciones/{id}`

Elimina una seccion. **Respuesta 204.**
